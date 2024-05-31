package com.example.massycake.payload.request;

import com.example.massycake.entities.DetallePedido;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class DetallePedidoRequest {
    @NotBlank(message = "Debe indicar la masa")
    private String tipoMasa;
    @NotBlank(message = "Debe indicar la forma")
    private String forma;
    @NotBlank(message = "Debe indicar el relleno")
    private String relleno;
    @NotBlank(message = "Debe indicar el peso")
    private float peso;
    private String nota;
    @NotBlank(message = "Debe indicar la unidad")
    private String unidad;

    public String getTipoMasa() {
        return tipoMasa;
    }

    public void setTipoMasa(String tipoMasa) {
        this.tipoMasa = tipoMasa;
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

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    //    public DetallePedido toDetallePedido(){
//        return new DetallePedido(masa,forma,relleno,peso,nota)
//    }
}
