package encapsulaciones;

import jakarta.persistence.*;
import servicios.GestionDbProducto;
import java.io.Serializable;

@Entity
public class ProductoVenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int codigo;
    private String nombre;
    private float precio;
    private int cantidad;

    public ProductoVenta(int codigo, String nombre, float precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public ProductoVenta() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void merge(Producto p1) {
        this.codigo = p1.getCodigo();
        this.nombre = p1.getNombre();
        this.precio = p1.getPrecio();
        this.cantidad = p1.getCantidad();
    }
    public Producto getAsProducto() {
        Producto p1 = GestionDbProducto.getInstance().findByCodigo(codigo);
        p1.setCantidad(cantidad);
        p1.setPrecio(precio);
        return p1;
    }
}
