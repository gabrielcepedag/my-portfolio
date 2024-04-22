package encapsulaciones;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private long id;
    @Column(unique = true)
    private int codigo;
    private String nombre;
    private float precio;
    private int cantidad;
    @JsonIgnore
    private String descripcion;
    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comentario> comentarios;
    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Foto> fotos;

    public Producto(int codigo, String nombre, float precio, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = 0;
        this.comentarios = new ArrayList<>();
        this.fotos = new ArrayList<>();
    }
    public Producto() {}

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
    public List<Comentario> getComentarios() {
        return comentarios;
    }
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    public List<Foto> getFotos() {
        return fotos;
    }
    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void addFoto(Foto foto) {
        fotos.add(foto);
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonIgnore
    public ProductoVenta getProductoVenta() {
        ProductoVenta p1 = new ProductoVenta();
        p1.setCodigo(this.codigo);
        p1.setNombre(this.nombre);
        p1.setPrecio(this.precio);

        return p1;
    }
    public void actualizar(Producto prod) {
        codigo = prod.getCodigo();
        nombre = prod.getNombre();
        precio = prod.getPrecio();
        descripcion = prod.getDescripcion();
        comentarios = prod.getComentarios();
        cantidad = prod.getCantidad();
        fotos = prod.getFotos();
    }
    public void addCommentario(Comentario comentario){
        comentarios.add(comentario);
    }
}
