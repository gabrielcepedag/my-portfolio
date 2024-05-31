package com.example.massycake.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;

@Entity
public class DetallePedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detalle_pedidoId;
    @Column
    private String masa;
    @Column
    private String forma;
    @Column
    private String relleno;
    @Column
    private Float peso;
    @Column
    private String unidad;
    @Column
    private String nota;
    @Column
    private Float costo;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "detallePedido_ingrediente")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<PedidoIngrediente> ingredientes;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "detalle_pedido_receta",
            joinColumns = @JoinColumn(name = "detalle_pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "receta_id")
    )
    private List<Receta> recetas;

    public DetallePedido(String masa, String forma, String relleno, float peso, String nota, Pedido pedido, String unidad) {
        this.masa = masa;
        this.forma = forma;
        this.relleno = relleno;
        this.peso = peso;
        this.unidad = unidad;
        this.nota = nota;
        this.pedido = pedido;
        //TODO: CALCULAR EL COSTO DE TODO ESTO. Sumar ese costo al costo total del pedido recibido en el constructor.
    }
    public DetallePedido() {}

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
    public void addReceta(Receta receta) {
        recetas.add(receta);
    }
    public void removeReceta(Receta receta) {
        recetas.remove(receta);
    }

    public long getDetalle_pedidoId() {
        return detalle_pedidoId;
    }

    public void setDetalle_pedidoId(long detalle_pedidoId) {
        this.detalle_pedidoId = detalle_pedidoId;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getRelleno() {
        return relleno;
    }

    public void setRelleno(String relleno) {
        this.relleno = relleno;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "detalle_pedidoId=" + detalle_pedidoId +
                ", masa='" + masa + '\'' +
                ", forma='" + forma + '\'' +
                ", relleno='" + relleno + '\'' +
                ", peso=" + peso +
                ", nota='" + nota + '\'' +
                '}';
    }
    public void addIngrediente(PedidoIngrediente newIngrediente) {
        this.ingredientes.add(newIngrediente);
    }
    public List<PedidoIngrediente> getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(List<PedidoIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
