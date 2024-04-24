package com.example.massycake.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long empleadoId;
    @Column(name="cedula", unique = true)
    @NotBlank(message = "Cedula es requerido")
    private String cedula;
    @Column(name="nombre")
    @NotBlank(message = "Nombre es requerido")
    private String nombre;
    @Column(name="apellido")
    @NotBlank(message = "Apellido es requerido")
    private String apellido;
    @Column(name="usuario", unique = true)
    @NotBlank(message = "Usuario es requerido")
    private String usuario;

    //    TODO: MIN LENGTH 3 VALIDATION. ONLY FOR DATABASE. PASSWORD EDIT FORM IS BLANK
    @Column(name="password")
//    @NotBlank(message = "Password es requerido")
    private String password;
    @Column(name="telefono")
    private String telefono;
    @Column(name="direccion")
    private String direccion;
    @Column(columnDefinition = "boolean default false")
    private boolean admin;
    @Column(columnDefinition = "boolean default false")
    private boolean eliminado;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    private List<Factura> facturas;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
    @Column(nullable = false)
    private String rol;

    public Empleado() {
    }

    public Empleado(String cedula, String nombre, String apellido, String usuario, String password, String telefono, String direccion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "empleadoId=" + empleadoId +
                ", cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", admin=" + admin + '\'' +
                ", eliminado=" + eliminado + '\'' +
                ", rol=" + rol + '\'' +
                '}';
    }
}