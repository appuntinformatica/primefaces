grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.27'
		
		
		
		/*
____________________________________________
grails  springWebFlowVersion  springVersion
--------------------------------------------
        2.3.0.RELEASE         3.0.5.RELEASE
        2.3.1.RELEASE         3.1.1.RELEASE
        2.3.2.RELEASE         3.2.1.RELEASE
        2.3.3.RELEASE         4.0.0.RELEASE
        2.3.4.RELEASE         4.0.0.RELEASE
2.4.2   2.4.0.RELEASE         4.0.2.RELEASE
2.4.3   2.4.1.RELEASE	      4.0.6.RELEASE


_______________________________
Grails          springVersion
-------------------------------
2.1.0           3.1.0.RELEASE
2.2.4           3.1.4.RELEASE
2.3.7           3.2.8.RELEASE
2.4.2           4.0.5.RELEASE
2.4.3           4.0.6.RELEASE
2.4.4           4.0.7.RELEASE
_______________________________

*/

        compile 'org.springframework.webflow:spring-webflow:2.4.1.RELEASE'
        compile 'org.springframework.webflow:spring-faces:2.4.1.RELEASE'

        compile 'org.apache.myfaces.core:myfaces-api:2.2.5'
        compile 'org.apache.myfaces.core:myfaces-impl:2.2.5'
        compile 'javax.servlet.jsp.jstl:jstl-api:1.2'

        compile 'org.primefaces:primefaces:5.1'
    }

    plugins {
        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
