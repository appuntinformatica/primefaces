package grails.plugins.primefaces;

import grails.util.Holders;
import groovy.util.ConfigObject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class PrimefacesBeans {
    private static Logger log = Logger.getLogger(PrimefacesBeans.class);
        
    public static void init(String... packagesForScanning) {
        try {
            registryPrimefacesBeans("grails.plugins.primefaces");
            if (packagesForScanning.length > 0) {
                for (String packageName : packagesForScanning) {
                    registryPrimefacesBeans(packageName);
                }
            } else {
                log.warn("No package fonund for 'grails.plugins.primefaces.beans.packages'");
            }
        } catch (Exception ex) {
            log.warn("No config found for 'grails.plugins.primefaces.beans.packages'");
            log.error(ex.getMessage(), ex);
        }
    }

    public static void init() {
        log.info("Start");
        try {
            registryPrimefacesBeans("grails.plugins.primefaces");
            ConfigObject config = Holders.getConfig();
            config = (ConfigObject) config.getProperty("grails");
            config = (ConfigObject) config.getProperty("plugins");
            config = (ConfigObject) config.getProperty("primefaces");
            config = (ConfigObject) config.getProperty("beans");
            List<String> packagesForScanning = (List<String>) config.getProperty("packages");
            
            if (packagesForScanning.size() > 0) {
                for (String packageName : packagesForScanning) {
                    registryPrimefacesBeans(packageName);
                }
            } else {
                log.warn("No package fonund for 'grails.plugins.primefaces.beans.packages'");
            }
        } catch (ClassCastException ex) {
            log.warn("No config found for 'grails.plugins.primefaces.beans.packages'");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    private static void registryPrimefacesBeans(String packageName) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ManagedBean.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
            log.info("beanClassName = " + bd.getBeanClassName());
            try {
                Class clazz = Class.forName(bd.getBeanClassName());
                ManagedBean mb = (ManagedBean) clazz.getAnnotation(ManagedBean.class);

                ApplicationContext appContext = Holders.getApplicationContext();
                BeanFactory factory = appContext.getAutowireCapableBeanFactory();
                
                BeanDefinitionRegistry registry = ((BeanDefinitionRegistry ) factory);
   
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();  
                beanDefinition.setBeanClass(clazz);  
                beanDefinition.setLazyInit(false);  
                beanDefinition.setAbstract(false);  
                beanDefinition.setAutowireCandidate(true);
                beanDefinition.setScope(SCOPE_SINGLETON);
                
                String beanName = mb.name();
                if (beanName.equals("")) {
                    beanName = clazz.getSimpleName();
                    beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                }
    
                MutablePropertyValues propertyValues = new MutablePropertyValues();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType() == GrailsService.class) {
                            try {
                                GrailsService gs = (GrailsService) annotation;
                                propertyValues.addPropertyValue(gs.name(), appContext.getBean(gs.name()));
                                log.info("\t GrailsService annotation named '" + gs.name() + "' [" + field.getType() + "] is created");
                            } catch (NoSuchBeanDefinitionException ex) {
                                log.warn(ex.getMessage());
                            }           
                        } else if (annotation.annotationType() == ManagedProperty.class) {
                            try {
                                ManagedProperty mp = (ManagedProperty) annotation;
                                String propName = mp.value().replace("#{", "").replace("}", "");
                                propertyValues.addPropertyValue(propName, appContext.getBean(propName));
                            log.info("\t ManagedProperty annotation named '" + propName + "' [" + field.getType() + "] is created");
                            } catch (NoSuchBeanDefinitionException ex) {
                                log.warn(ex.getMessage());
                            }                       
                        }
                    }
                }
                log.info("beanName '" + beanName + "' is created");
                registry.registerBeanDefinition(beanName, beanDefinition);  
                if (!propertyValues.isEmpty())
                    beanDefinition.setPropertyValues(propertyValues);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }   
}