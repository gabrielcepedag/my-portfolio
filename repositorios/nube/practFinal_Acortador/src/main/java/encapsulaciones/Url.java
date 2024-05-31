package encapsulaciones;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import servicios.GestionDbUsuario;
import servicios.MainServices;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Entity
public class Url implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    final String urlLarga;
    final String urlAcortada;
    private int cantAccesos;
    private String fechaCreacion;
    @Lob
    private String vistaPrevia;
    @OneToOne(mappedBy = "url", fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private CodigoQR qr;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "url", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Acceso> accesos = new ArrayList<>();

    public Url(String urlLarga) throws SQLException, IOException, ClassNotFoundException {
        this.urlLarga = urlLarga;
        this.urlAcortada = MainServices.getInstance().acortarUrl(urlLarga);
        this.fechaCreacion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.vistaPrevia = MainServices.getInstance().vistaPrevia(this);
        byte[] qrBytes = QRCode.from(urlAcortada).to(ImageType.PNG).stream().toByteArray();
        String encodedString = Base64.getEncoder().encodeToString(qrBytes);
        CodigoQR qr = new CodigoQR();
        qr.setNombre(urlLarga);
        qr.setFotoBase64(encodedString);
        qr.setMimeType("image/png");
        qr.setUrl(this);
        this.qr = qr;
        cantAccesos = 0;
    }

    public Url() {
        urlLarga = null;
        urlAcortada = null;
    }

    public String getUrlLarga() {
        return urlLarga;
    }

    public String getUrlAcortada() {
        return urlAcortada;
    }

    public int getCantAccesos() {
        return cantAccesos;
    }

    public void setCantAccesos(int cantAccesos) {
        this.cantAccesos = cantAccesos;
    }

    public String getVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(String vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }

    public CodigoQR getQr() {
        return qr;
    }

    public void setQr(CodigoQR qr) {
        this.qr = qr;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public List<Acceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(List<Acceso> accesos) {
        this.accesos = accesos;
    }

    public Long getId() {
        return id;
    }
    public void addAcceso(Acceso acceso) {
        GestionDbUsuario.getInstance().addAcceso(acceso);
        this.accesos.add(acceso);
    }

    public void removeAcceso(Acceso acceso) {
        GestionDbUsuario.getInstance().removeAcceso(acceso);
        this.accesos.remove(acceso);
    }
}
