package com.example.massycake.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ClienteRequest {
    @NotBlank(message = "Debe indicar la cedula del cliente")
    private String cedula;
    @NotBlank(message = "Debe indicar el nombre del cliente")
    private String nombre;
    private String direccion;
    @NotBlank(message = "Debe indicar el telefono del cliente")
    private String telefono;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
