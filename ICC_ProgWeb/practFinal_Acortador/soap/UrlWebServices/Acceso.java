
package soap.UrlWebServices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para acceso complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="acceso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ipCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="navegador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="plataforma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://soap/}url" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "acceso", propOrder = {
    "ipCliente",
    "navegador",
    "plataforma",
    "url"
})
public class Acceso {

    protected String ipCliente;
    protected String navegador;
    protected String plataforma;
    protected Url url;

    /**
     * Obtiene el valor de la propiedad ipCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpCliente() {
        return ipCliente;
    }

    /**
     * Define el valor de la propiedad ipCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpCliente(String value) {
        this.ipCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad navegador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNavegador() {
        return navegador;
    }

    /**
     * Define el valor de la propiedad navegador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNavegador(String value) {
        this.navegador = value;
    }

    /**
     * Obtiene el valor de la propiedad plataforma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Define el valor de la propiedad plataforma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlataforma(String value) {
        this.plataforma = value;
    }

    /**
     * Obtiene el valor de la propiedad url.
     * 
     * @return
     *     possible object is
     *     {@link Url }
     *     
     */
    public Url getUrl() {
        return url;
    }

    /**
     * Define el valor de la propiedad url.
     * 
     * @param value
     *     allowed object is
     *     {@link Url }
     *     
     */
    public void setUrl(Url value) {
        this.url = value;
    }

}
