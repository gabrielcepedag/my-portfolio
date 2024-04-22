package servicios;

import encapsulaciones.Producto;
import encapsulaciones.ProductoVenta;
import encapsulaciones.Ventas;
import jakarta.persistence.Query;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import io.github.cdimascio.dotenv.Dotenv;

public class GestionCockroachDB {
    private static GestionCockroachDB instance;
    public static GestionCockroachDB getInstance() throws SQLException, IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new GestionCockroachDB();
            instance.crearTablasDefault();
        }
        return instance;
    }

    public Connection getConnection() throws IOException, SQLException {
        Dotenv dotenv = Dotenv.configure().load();
        String url = dotenv.get("DATABASE_URL");
        String username = dotenv.get("DATABASE_USERNAME");
        String password = dotenv.get("DATABASE_PASSWORD");

        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(url);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setSslmode("require");

        return ds.getConnection();
    }

    private boolean tablaCreada(String consulta) throws SQLException, IOException {
        Statement stmt = null;
        ResultSet rs = null;
        boolean creada;
        Connection con = instance.getConnection();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(consulta);
            creada = rs.next();
        } finally {
            rs.close();
            stmt.close();
//            con.close();
        }

        return creada;
    }

    private void ejecutarConsulta(String consulta) throws SQLException, IOException {
        Connection con = instance.getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            stmt.execute(consulta);
        } finally {
            stmt.close();
        }
    }

    private void crearTablasDefault() throws SQLException, IOException {
        Statement stmt = null;
        ResultSet rs = null, rs2 = null, rs3 = null;

        Connection con = instance.getConnection();
        con.setAutoCommit(false);

        String select = "SELECT DISTINCT TABLE_NAME FROM information_schema. COLUMNS WHERE TABLE_NAME = 'venta'";
        String select2 = "SELECT DISTINCT TABLE_NAME FROM information_schema. COLUMNS WHERE TABLE_NAME = 'productoventa'";
        String select3 = "SELECT DISTINCT TABLE_NAME FROM information_schema. COLUMNS WHERE TABLE_NAME = 'venta_productoventa'";

        boolean createVenta = tablaCreada(select);
        boolean createProdVenta = tablaCreada(select2);
        boolean createRelacion = tablaCreada(select3);

        System.out.println("Venta: "+createVenta+" - Producto: "+createProdVenta+ " - Relacion: " +createRelacion);

        if(!createVenta) {
            String venta = "CREATE TABLE venta (venta_id INT, codigo VARCHAR(255) NOT NULL , fechaCompra VARCHAR(255) NOT NULL, " +
                    "nombreCliente VARCHAR(255) NOT NULL, monto float(24) NOT NULL, PRIMARY KEY (venta_id))";
            ejecutarConsulta(venta);
        }
        if(!createProdVenta) {
            String productoVenta = "create table productoventa (cantidad INT not null, codigo INT not null, precio float(24) not null, " +
                    "productoVenta_id INT not null, nombre varchar(255) not null, PRIMARY KEY (productoVenta_id))";
            ejecutarConsulta(productoVenta);
        }
        if(!createRelacion) {
            String relacion = "create table venta_productoVenta (venta_id int not null, productoVenta_id int not null, primary key (venta_id,productoVenta_id))";
            ejecutarConsulta(relacion);
            String foreignKey = "alter table venta_productoVenta add constraint FKPRODUCTO foreign key (productoventa_id) references productoVenta (productoventa_id)";
            ejecutarConsulta(foreignKey);
            String foreignKey2 = "alter table venta_productoVenta add constraint FKVENTA foreign key (venta_id) references venta (venta_id)";
            ejecutarConsulta(foreignKey2);
        }
    }

    public int getLastId() throws SQLException, IOException {
        int id = -1;

        Statement stmt = null;
        ResultSet rs = null;
        Connection con = instance.getConnection();
        try {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT MAX(venta_id) from venta");
            if (rs.next()){
                id = rs.getInt(1);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            con.close();
            stmt.close();
        }
        return id;
    }

    public void crearVenta(Ventas venta) throws SQLException, IOException {
        ResultSet rs = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        int idProdVenta = MainServices.lastIdProdVenta + 1;

        Connection con = instance.getConnection();
        con.setAutoCommit(false);

        ps = con.prepareStatement("insert into venta (codigo,fechaCompra,monto,nombreCliente,venta_id) values (?,?,?,?,?)");
        PreparedStatement ps2 = con.prepareStatement("insert into productoventa (cantidad,codigo,nombre,precio,productoventa_id) values (?,?,?,?,?)");
        PreparedStatement prodVenta = con.prepareStatement("insert into venta_productoventa (venta_id,productoventa_id) values (?,?)");

        try {
            ps.setString(1,venta.getCodigo());
            ps.setString(2,(venta.getFechaCompra()));
            ps.setFloat(3,venta.getMonto());
            ps.setString(4,venta.getNombreCliente());
            ps.setInt(5,venta.getId());

            ps.executeUpdate();

            for (ProductoVenta p1: venta.getListaProductos()) {
                ps2.setInt(1, p1.getCantidad());
                ps2.setInt(2, p1.getCodigo());
                ps2.setString(3, p1.getNombre());
                ps2.setFloat(4, p1.getPrecio());
                ps2.setInt(5, idProdVenta);

                prodVenta.setInt(1, venta.getId());
                prodVenta.setInt(2, idProdVenta);
                ps2.executeUpdate();
                prodVenta.executeUpdate();
                idProdVenta++;
            }
            con.createStatement().execute("COMMIT");
            MainServices.lastIdProdVenta = idProdVenta;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ps2.close();
            prodVenta.close();
            ps.close();
            con.close();
        }
    }
    public int getLastIdProdVenta() throws SQLException, IOException {
        int id = 0;

        Statement stmt = null;
        ResultSet rs = null;
        Connection con = instance.getConnection();

        try {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT MAX(productoventa_id) from productoventa");
            if (rs.next()){
                id = rs.getInt(1);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            con.close();
            stmt.close();
        }
        return id;
    }

    public List<Ventas> findAllVentas() throws SQLException, IOException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Ventas> ventas = new ArrayList<>();

        Connection con = instance.getConnection();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * from venta");

            while (rs.next()) {
                Ventas tmp = new Ventas();
                tmp.setId(rs.getInt("venta_id"));
                tmp.setCodigo(rs.getString("codigo"));
                tmp.setFechaCompra(rs.getString("fechacompra"));
                tmp.setNombreCliente(rs.getString("nombrecliente"));
                tmp.setMonto(rs.getFloat("monto"));
                ventas.add(tmp);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            con.close();
            stmt.close();
        }
        return ventas;
    }

    public Ventas buscarVentaByCodigo(String codigo) throws SQLException, IOException {
        Ventas venta = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Connection con = instance.getConnection();

        try {
            pstmt = con.prepareStatement("SELECT * from venta where codigo = ?");
            pstmt.setString(1, codigo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                venta = new Ventas();
                venta.setId(rs.getInt("venta_id"));
                venta.setCodigo(rs.getString("codigo"));
                venta.setFechaCompra(rs.getString("fechacompra"));
                venta.setNombreCliente(rs.getString("nombrecliente"));
                venta.setMonto(rs.getFloat("monto"));
            }
        }finally {
            rs.close();
            pstmt.close();
        }

        return venta;
    }
    public List<ProductoVenta> findProductoVentaById(int id) throws SQLException, IOException {
        List<ProductoVenta> productoVentas = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Connection con = instance.getConnection();

        try {
            pstmt = con.prepareStatement("select * from productoventa p left join venta_productoventa vp on vp.productoventa_id = p.productoventa_id where vp.venta_id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductoVenta p1 = new ProductoVenta();
                p1.setCodigo(rs.getInt("codigo"));
                p1.setId(rs.getInt("productoventa_id"));
                p1.setPrecio(rs.getFloat("precio"));
                p1.setCantidad(rs.getInt("cantidad"));
                p1.setNombre(rs.getString("nombre"));
                productoVentas.add(p1);
            }
        }finally {
            rs.close();
            pstmt.close();
        }
        return productoVentas;
    }
}
