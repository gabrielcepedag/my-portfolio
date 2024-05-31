package com.example.p7_functions.encapsulaciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Reserva {
    private String uuid;
    private String correo;
    private String nombre;
    private String matricula;
    private String horario;
    private String laboratorio;

    public Reserva(String uuid, String correo, String nombre, String matricula, String horario, String laboratorio) {
        this.correo = correo;
        this.nombre = nombre;
        this.matricula = matricula;
        this.horario = horario;
        this.laboratorio = laboratorio;
        this.uuid = uuid;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Reserva() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
