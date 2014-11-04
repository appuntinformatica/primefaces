class PrimefacesBootStrap {

    def init = { servletContext ->
        log.info "PrimefacesBootStrap start"
        grails.plugins.primefaces.PrimefacesBeans.init()
        
    }
    def destroy = {
    }
}
