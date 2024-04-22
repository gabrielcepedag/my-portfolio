
package soap.UrlWebServices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para url complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="url">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accesos" type="{http://soap/}acceso" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cantAccesos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="qr" type="{http://soap/}codigoQR" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://soap/}usuario" minOccurs="0"/>
 *         &lt;element name="vistaPrevia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "url", propOrder = {
    "accesos",
    "cantAccesos",
    "qr",
    "usuario",
    "vistaPrevia"
})
public class Url {

    @XmlElement(nillable = true)
    protected List<Acceso> accesos;
    protected int cantAccesos;
    protected CodigoQR qr;
    protected Usuario usuario;
    protected String vistaPrevia;

    /**
     * Gets the value of the accesos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accesos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccesos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Acceso }
     * 
     * 
     */
    public List<Acceso> getAccesos() {
        if (accesos == null) {
            accesos = new ArrayList<Acceso>();
        }
        return this.accesos;
    }

    /**
     * Obtiene el valor de la propiedad cantAccesos.
     * 
     */
    public int getCantAccesos() {
        return cantAccesos;
    }

    /**
     * Define el valor de la propiedad cantAccesos.
     * 
     */
    public void setCantAccesos(int value) {
        this.cantAccesos = value;
    }

    /**
     * Obtiene el valor de la propiedad qr.
     * 
     * @return
     *     possible object is
     *     {@link CodigoQR }
     *     
     */
    public CodigoQR getQr() {
        return qr;
    }

    /**
     * Define el valor de la propiedad qr.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigoQR }
     *     
     */
    public void setQr(CodigoQR value) {
        this.qr = value;
    }

    /**
     * Obtiene el valor de la propiedad usuario.
     * 
     * @return
     *     possible object is
     *     {@link Usuario }
     *     
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define el valor de la propiedad usuario.
     * 
     * @param value
     *     allowed object is
     *     {@link Usuario }
     *     
     */
    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

    /**
     * Obtiene el valor de la propiedad vistaPrevia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVistaPrevia() {
        return vistaPrevia;
    }

    /**
     * Define el valor de la propiedad vistaPrevia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVistaPrevia(String value) {
        this.vistaPrevia = value;
    }

}
