package servicios;

import encapsulaciones.*;
import org.eclipse.jetty.websocket.api.Session;
import org.example.Main;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.awt.color.ProfileDataException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

public class MainServices {
    private static MainServices instance;
    public static int lastIdVentas;
    public static int lastIdProdVenta;
    private ArrayList<Producto> listaProducto = new ArrayList<>();
    private ArrayList<Usuario> listaUsuario = new ArrayList<>();
    private List<Ventas> listaVentas = new ArrayList<>();
    public static List<Session> usuariosConectados = new ArrayList<>();
    public static List<Session> dashboardConectados = new ArrayList<>();

    public static Map<Session, Integer> productosSesion = new HashMap<>();
    private CarritoCompra carritoCompra;
    private ArrayList<Producto> misProductos = new ArrayList<>();
    public Usuario loggedUser;
    public static Usuario guestUser = new Usuario("guest", "", "", "", false);

    public static MainServices getInstance() throws SQLException, IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new MainServices();
            instance.listaUsuario = new ArrayList<Usuario>();
            instance.listaProducto = new ArrayList<Producto>();
            instance.carritoCompra = new CarritoCompra();
            instance.loggedUser = MainServices.guestUser;
            instance.getListaVentas();
            System.out.println("Cargando lista de productos ya vendidos...");
            for (Ventas ventas: instance.listaVentas) {
                List<ProductoVenta> prods = ventas.getListaProductosFromDB();
                ventas.setProductoVenta(prods);
            }
            MainServices.lastIdVentas = GestionCockroachDB.getInstance().getLastId();
            MainServices.lastIdProdVenta = GestionCockroachDB.getInstance().getLastIdProdVenta();
        }
        return instance;
    }

    public Usuario getLoggedUser() {
        return this.loggedUser;
    }
    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }
    public static Usuario getGuestUser() {
        return guestUser;
    }

    public static void setGuestUser(Usuario guestUser) {
        MainServices.guestUser = guestUser;
    }
    public List<Producto> getListaProducto() {
        List<Producto> productos = new ArrayList<>();
        productos = GestionDbProducto.getInstance().findAll(-1,0);
        return productos;
    }
    public List<Producto> getListaProducto(int limit, int offset) {
        List<Producto> productos = new ArrayList<>();
        productos = GestionDbProducto.getInstance().findAll(limit,offset);
        return productos;
    }
//    public void setListaProducto(ArrayList<Producto> listaProducto) {
//        this.listaProducto = listaProducto;
//    }
    public List<Usuario> getListaUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = GestionDbUsuario.getInstance().findAll();
        return usuarios;
    }
    public List<Ventas> getListaVentas() throws SQLException, IOException, ClassNotFoundException {
        List<Ventas> ventas = new ArrayList<>();
//        ventas = GestionDbVenta.getInstance().findAll();
        if (this.listaVentas.size() > 0){
            return listaVentas;
        }else{
            ventas = GestionCockroachDB.getInstance().findAllVentas();
            this.listaVentas = ventas;
            return ventas;
        }

//        this.listaVentas = ventas;
//        return ventas;
    }
    public CarritoCompra getCarritoCompra() {
        return carritoCompra;
    }
    public void setCarritoCompra(CarritoCompra carritoCompra) {
        this.carritoCompra = carritoCompra;
    }

    public void addProducto(Producto producto) {
        GestionDbProducto.getInstance().crear(producto);
        listaProducto.add(producto);
    }
    public void addUsuario(Usuario usuario) {
        listaUsuario.add(usuario);
        GestionDbUsuario.getInstance().crear(usuario);
    }
    public Producto getProductoByCodigo(int codigo){
        Producto p1 = GestionDbProducto.getInstance().findByCodigo(codigo);
        return p1;
    }

    public float getTotalCarrito(){
        float suma = 0;
        if(this.carritoCompra != null){
            for(Producto producto: this.carritoCompra.getListaProducto()){
                suma += (producto.getPrecio()*producto.getCantidad());
            }
        }
        return suma;
    }

    public Usuario getUserByUsername(String username) {
        Usuario auxUsuario = GestionDbUsuario.getInstance().findUsuarioByUsername(username);
        return auxUsuario;
    }

    public Usuario userVerification(String username, String password){
        Usuario u1 = GestionDbUsuario.getInstance().findUsuarioByUsername(username);
        if (u1 == null || !(u1.getPassword().equals(password)))
            return null;

        return u1;
    }

    public void procesarCheckout(String nombreCliente, List<Producto> productList) throws SQLException, IOException, ClassNotFoundException {

        List<ProductoVenta> listaBD = new ArrayList<>();

        for(Producto prod : productList){
            ProductoVenta p1 = prod.getProductoVenta();
            if (!(listaBD.stream().anyMatch(tmp -> tmp.getCodigo() == p1.getCodigo()))){
                List<Producto> iguales = productList.stream().filter(tmp -> tmp.getCodigo() == (prod.getCodigo()))
                        .collect(Collectors.toList());
                p1.setCantidad(iguales.size());
                listaBD.add(p1);
            }
        }
        Ventas venta = new Ventas(nombreCliente, listaBD);
        if(venta != null){
            GestionDbVenta.getInstance().crear(venta);
            GestionCockroachDB.getInstance().crearVenta(venta);
            listaVentas.add(venta);
            carritoCompra.getListaProducto().clear();
        }else{
            System.out.println("Error en la transaccion.");
        }
    }

    public Producto actualizarProducto(Producto producto){
        Producto prodTmp = GestionDbProducto.getInstance().findByCodigo(producto.getCodigo());
        if(prodTmp != null){
            producto.setId(prodTmp.getId());
            prodTmp = producto;
            GestionDbProducto.getInstance().editar(prodTmp);
        }
        return producto;
    }

    public Ventas getVentaByCodigo(String codigo) throws SQLException, IOException, ClassNotFoundException {
//        Ventas v1 = GestionDbVenta.getInstance().buscarVentaByCodigo(codigo);
        Ventas v1 = GestionCockroachDB.getInstance().buscarVentaByCodigo(codigo);
        return v1;
    }

    public void addComentarioProd(Comentario comentario) {
        GestionDbProducto.getInstance().addComentario(comentario);
    }
//    public void removeComentarioProd(Comentario comentario) {
//        GestionDbProducto.getInstance().removeComentario(comentario);
//    }

    public Comentario getComentarioById(Long id){
        Comentario comentario = GestionDbProducto.getInstance().getComentarioByCodigo(id);
        return comentario;
    }

    public float calcTotalVenta(List<Ventas> listVentas) throws SQLException, IOException, ClassNotFoundException {
        float suma = 0;
        for (Ventas ventas: listVentas) {
            suma += ventas.getMonto();
        }
        return suma;
    }

    public int calcProdsVendidos(List<Ventas> listaVentas) throws SQLException, IOException, ClassNotFoundException {
        int suma = 0;
        for (Ventas ventas: listaVentas) {
            for(ProductoVenta prodVenta : ventas.getListaProductos()){
                suma += prodVenta.getCantidad();
            }
        }
        return suma;
    }

    public List<Producto> getProdsVendidos() throws SQLException, IOException, ClassNotFoundException {
        List<Producto> listProducto = new ArrayList<>();
        List<Producto> listProductoClean = new ArrayList<>();
//        ArrayList<ProductoVenta> listProductoVenta = new ArrayList<>();

        for(Ventas ventas : this.getListaVentas()){
            for (ProductoVenta prodVenta : ventas.getProductoVenta()){
                    listProducto.add(prodVenta.getAsProducto());
            }
        }

        Set<Integer> codigosUnicos = new HashSet<Integer>();
        for(Producto producto : listProducto){
            if(!codigosUnicos.contains(producto.getCodigo())){
                codigosUnicos.add(producto.getCodigo());
                listProductoClean.add(producto);
            }else{
                Producto prodTmp = listProductoClean.stream().filter(prod -> prod.getCodigo() == producto.getCodigo()).findFirst().get();
                prodTmp.setCantidad(prodTmp.getCantidad()+producto.getCantidad());
            }
        }

        return listProductoClean;
    }
}