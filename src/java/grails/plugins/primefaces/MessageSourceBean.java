package grails.plugins.primefaces;

import static grails.plugins.primefaces.WebApplicationUtils.getSession;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@ManagedBean(name = "message")
public class MessageSourceBean {
    private Logger log = Logger.getLogger(MessageSourceBean.class);

    private MessageSource messageSource;
    public MessageSourceBean() {
        messageSource = WebApplicationUtils.getBean("messageSource");
    }

    public String i18n(String code) {
        return i18n(code, null);
    }
    
    public String i18n(String code, String[] args) {
        try {
            return messageSource.getMessage(code, args, getLocale());
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            return code;
        }
    }
    
    public String i18n(String code, String[] args, Locale locale) {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            return code;
        }
    }
    
    public Locale getLocale() {
        Locale locale;
        HttpSession session = getSession();
        if (session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, "en");
        }
        locale = new Locale(session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString());
        return locale;
    }
    
    public void setLocale(String lang) {
        HttpSession session = getSession();
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, lang);
    }
    
    public String getErrorMessage(FieldError fieldError) {
        String code = String.format("%s.%s.%s", fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        return messageSource.getMessage(code, new Object[] { fieldError.getRejectedValue() }, fieldError.getDefaultMessage(), getLocale());
    }

}
