package grails.plugins.primefaces;

import grails.util.Holders;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class WebApplicationUtils {

    public static <T> T getBean(String beanName) {
        return (T) Holders.getApplicationContext().getBean(beanName);
    }

    public static HttpSession getSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (HttpSession) facesContext.getExternalContext().getSession(true);
    }
    
    public static HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }
    
}