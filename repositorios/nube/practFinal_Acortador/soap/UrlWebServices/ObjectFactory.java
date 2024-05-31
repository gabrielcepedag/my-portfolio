
package soap.UrlWebServices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the soap.UrlWebServices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetListaUrlByUsuarioResponse_QNAME = new QName("http://soap/", "getListaUrlByUsuarioResponse");
    private final static QName _GetListaUrlByUsuario_QNAME = new QName("http://soap/", "getListaUrlByUsuario");
    private final static QName _GetListaUrlResponse_QNAME = new QName("http://soap/", "getListaUrlResponse");
    private final static QName _GetListaUrl_QNAME = new QName("http://soap/", "getListaUrl");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: soap.UrlWebServices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetListaUrlByUsuario }
     * 
     */
    public GetListaUrlByUsuario createGetListaUrlByUsuario() {
        return new GetListaUrlByUsuario();
    }

    /**
     * Create an instance of {@link GetListaUrlByUsuarioResponse }
     * 
     */
    public GetListaUrlByUsuarioResponse createGetListaUrlByUsuarioResponse() {
        return new GetListaUrlByUsuarioResponse();
    }

    /**
     * Create an instance of {@link GetListaUrl }
     * 
     */
    public GetListaUrl createGetListaUrl() {
        return new GetListaUrl();
    }

    /**
     * Create an instance of {@link GetListaUrlResponse }
     * 
     */
    public GetListaUrlResponse createGetListaUrlResponse() {
        return new GetListaUrlResponse();
    }

    /**
     * Create an instance of {@link Acceso }
     * 
     */
    public Acceso createAcceso() {
        return new Acceso();
    }

    /**
     * Create an instance of {@link Usuario }
     * 
     */
    public Usuario createUsuario() {
        return new Usuario();
    }

    /**
     * Create an instance of {@link Url }
     * 
     */
    public Url createUrl() {
        return new Url();
    }

    /**
     * Create an instance of {@link CodigoQR }
     * 
     */
    public CodigoQR createCodigoQR() {
        return new CodigoQR();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaUrlByUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getListaUrlByUsuarioResponse")
    public JAXBElement<GetListaUrlByUsuarioResponse> createGetListaUrlByUsuarioResponse(GetListaUrlByUsuarioResponse value) {
        return new JAXBElement<GetListaUrlByUsuarioResponse>(_GetListaUrlByUsuarioResponse_QNAME, GetListaUrlByUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaUrlByUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getListaUrlByUsuario")
    public JAXBElement<GetListaUrlByUsuario> createGetListaUrlByUsuario(GetListaUrlByUsuario value) {
        return new JAXBElement<GetListaUrlByUsuario>(_GetListaUrlByUsuario_QNAME, GetListaUrlByUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaUrlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getListaUrlResponse")
    public JAXBElement<GetListaUrlResponse> createGetListaUrlResponse(GetListaUrlResponse value) {
        return new JAXBElement<GetListaUrlResponse>(_GetListaUrlResponse_QNAME, GetListaUrlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListaUrl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "getListaUrl")
    public JAXBElement<GetListaUrl> createGetListaUrl(GetListaUrl value) {
        return new JAXBElement<GetListaUrl>(_GetListaUrl_QNAME, GetListaUrl.class, null, value);
    }

}
