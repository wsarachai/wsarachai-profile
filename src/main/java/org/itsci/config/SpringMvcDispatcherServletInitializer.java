package org.itsci.config;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Configures the Spring MVC DispatcherServlet and its mapping.
 * Initializes the servlet context programmatically rather than using web.xml.
 */
public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Return any root context configuration classes (often for service/repository
        // layers)
        return null; // Use null instead of empty array when there are no classes
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Return servlet context configuration classes (for web/controller layer)
        return new Class[] { WebConfig.class };
    }

    @Override
    @NonNull
    protected String[] getServletMappings() {
        // Map DispatcherServlet to "/" (all requests go through the dispatcher)
        return new String[] { "/" };
    }
}