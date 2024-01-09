package encapsulaciones;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import servicios.GestionDbAcceso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Acceso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String navegador;
    private String ipCliente;
    private String plataforma;
    private String fecha;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Url url;
    public Acceso(String navegador, String ipCliente, String plataforma) {
        this.navegador = navegador;
        this.ipCliente = ipCliente;
        this.plataforma = plataforma;
        fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }
    public Acceso() {}

    public long getId() {
        return id;
    }
    public String getNavegador() {
        return navegador;
    }
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    public String getIpCliente() {
        return ipCliente;
    }
    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }
    public String getPlataforma() {
        return plataforma;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public Url getUrl() {
        return url;
    }
    public void setUrl(Url url) {
        this.url = url;
    }
    public String getFecha() {
        return fecha;
    }
}
