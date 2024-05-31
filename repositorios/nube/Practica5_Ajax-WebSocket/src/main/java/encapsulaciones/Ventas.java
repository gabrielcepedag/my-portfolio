package encapsulaciones;

import jakarta.persistence.*;
import servicios.GestionCockroachDB;
import servicios.MainServices;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Ventas implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private int id;
    @Transient
    private static final long serialVersionUID = 1L;
    @Transient
//    public static int idVenta = 1;
    private String codigo;
    private String fechaCompra;
    private String nombreCliente;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<ProductoVenta> productoVenta;
    private float monto;

    public Ventas(String nombreCliente, List<ProductoVenta> listaProductos) throws SQLException, IOException, ClassNotFoundException {
        this.id = MainServices.lastIdVentas + 1;
        this.codigo = "V-"+this.id;
//        Ventas.idVenta += 1;
        this.nombreCliente = nombreCliente;
        this.productoVenta = listaProductos;
        this.monto = calculaMonto();
        this.fechaCompra = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        MainServices.lastIdVentas++;
    }
    public Ventas() {}

    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }
    public String getCodigo() {
        return codigo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getFechaCompra() {
        return fechaCompra;
    }
    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ProductoVenta> getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(List<ProductoVenta> productoVenta) {
        this.productoVenta = productoVenta;
    }

    public List<ProductoVenta> getListaProductosFromDB() throws SQLException, IOException, ClassNotFoundException {
        List<ProductoVenta> lista = GestionCockroachDB.getInstance().findProductoVentaById(id);
        this.productoVenta = lista;
        return productoVenta;
    }
    public List<ProductoVenta> getListaProductos() throws SQLException, IOException, ClassNotFoundException {
        return productoVenta;
    }

    private float calculaMonto() {
        float suma = 0;
        for (ProductoVenta p1: productoVenta) {
            suma += (p1.getPrecio() * p1.getCantidad());
        }
        return suma;
    }
}
