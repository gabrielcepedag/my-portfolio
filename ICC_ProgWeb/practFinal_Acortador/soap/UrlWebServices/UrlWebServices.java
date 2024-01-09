
package soap.UrlWebServices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UrlWebServices", targetNamespace = "http://soap/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UrlWebServices {


    /**
     * 
     * @return
     *     returns java.util.List<soap.UrlWebServices.Url>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getListaUrl", targetNamespace = "http://soap/", className = "soap.UrlWebServices.GetListaUrl")
    @ResponseWrapper(localName = "getListaUrlResponse", targetNamespace = "http://soap/", className = "soap.UrlWebServices.GetListaUrlResponse")
    @Action(input = "http://soap/UrlWebServices/getListaUrlRequest", output = "http://soap/UrlWebServices/getListaUrlResponse")
    public List<Url> getListaUrl();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<soap.UrlWebServices.Url>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getListaUrlByUsuario", targetNamespace = "http://soap/", className = "soap.UrlWebServices.GetListaUrlByUsuario")
    @ResponseWrapper(localName = "getListaUrlByUsuarioResponse", targetNamespace = "http://soap/", className = "soap.UrlWebServices.GetListaUrlByUsuarioResponse")
    @Action(input = "http://soap/UrlWebServices/getListaUrlByUsuarioRequest", output = "http://soap/UrlWebServices/getListaUrlByUsuarioResponse")
    public List<Url> getListaUrlByUsuario(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0);

}
