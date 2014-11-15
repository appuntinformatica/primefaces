import grails.util.GrailsNameUtils

// includeTargets << grailsScript("_GrailsBootstrap")
// includeTargets << grailsScript("_GrailsPackage")

includeTargets << new File(primefacesPluginDir, 'scripts/_PFCommon.groovy')

packageName = ''
domainClassName = ''
propertyName = ''


target(pfGenerateAll: "Generates a CRUD interface (service + beans + primefaces-view) for a domain class") {
    depends(checkVersion, configureProxy, packageApp, classpath)


    if (!configure()) {
        return 1
    }
}

USAGE = """
    pf-generate-all [NAME]

where
    NAME       = The name of the domain class. If not provided, this
                 command will ask you for the name.
"""

private boolean configure() {
    def args = argsMap.params
    if (1 != args.size()) {
        errorMessage USAGE
        return false
    }
    
    def fullDomainClassName = args[0]
    
    //fullDomainClassName = fullDomainClassName.indexOf('.') > 0 ? fullDomainClassName : GrailsNameUtils.getClassNameRepresentation(fullDomainClassName)
    
    /*
    def domainClass = grailsApp.getDomainClass(fullDomainClassName)
    if (!domainClass) {
        errorMessage "No domain class found for name ${fullDomainClassName}. Please try again and enter a valid domain class name"
        return false
    }
    */
    

    (packageName, domainClassName) = splitClassName(fullDomainClassName)
    propertyName = GrailsNameUtils.getPropertyName(domainClassName)
    
    printMessage """
            packageName = ${packageName}
            domainClassName = ${domainClassName}
            propertyName = ${propertyName}
    """
    
    templateAttributes = [packageName: packageName,
                            domainClassName: domainClassName,
                            propertyName: propertyName]
                        
    generateServices()
    generateBeans()
    
    true
}
private void generateServices() {
    String dir = packageToDir(packageName)
    generateFile "$templateDir/Service.groovy.template", "$appDir/services/${dir}${domainClassName}Service.groovy"
}

private void generateBeans() {
    String dir = packageToDir(packageName)
    generateFile "$templateDir/ManagedBean.groovy.template", "src/groovy/${dir}/beans/${domainClassName}ManagedBean.groovy"
    generateFile "$templateDir/LazyDataModel.groovy.template", "src/groovy/${dir}/beans/${domainClassName}LazyDataModel.groovy"
}

setDefaultTarget(pfGenerateAll)