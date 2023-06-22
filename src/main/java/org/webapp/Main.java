package org.webapp;

import org.web.server.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        EmbeddedServerFactory embeddedServerFactory = new TomcatEmbeddedServerFactory();
        // create server config instance
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(8090);
        serverConfig.setContextPath("/webapp");
        serverConfig.setDocBase(new File("src/main/webapp/").getAbsolutePath());
        serverConfig.setListenerClasses(TomcatEmbeddedServerFactory.getListenersClasses());
        // add filter for static resources
       /* FilterConfig defaultFilterConfig = new FilterConfig();
        defaultFilterConfig.setUrlPattern("/*");
        defaultFilterConfig.setFilterName("DefaultFilter");
        defaultFilterConfig.setFilterClass("org.webapp.DefaultFilter");
        defaultFilterConfig.getParameters().put("staticResourceFolder", "/WEB-INF/static");
        serverConfig.addFilterConfig(defaultFilterConfig);*/
        // add static resources servlet
        ServletConfig staticServletConfig = new ServletConfig();
        staticServletConfig.setServletName("StaticServlet");
        staticServletConfig.setServletClass(StaticServlet.class.getCanonicalName());
        staticServletConfig.setUrlPattern("/static/*");
        staticServletConfig.addInitParam(StaticServlet.PATH_PREFIX_PARAM_NAME, "/static");
        staticServletConfig.addInitParam(StaticServlet.STATIC_RESOURCE_FOLDER_PARAM_NAME, "/WEB-INF/static");
        serverConfig.addServletConfig(staticServletConfig);
        // add web dispatcher servlet
        ServletConfig dispatcherServletConfig = TomcatEmbeddedServerFactory.getWebDispatcherServletConfig();
        dispatcherServletConfig.setUrlPattern("/api/*");
        serverConfig.addServletConfig(dispatcherServletConfig);
        // add demo servlet
        ServletConfig demoServletConfig = TomcatEmbeddedServerFactory.getDemoServletConfig();
        demoServletConfig.setUrlPattern("/demo/*");
        serverConfig.addServletConfig(demoServletConfig);

        // start server
        embeddedServerFactory.init(serverConfig);
        embeddedServerFactory.start();
    }
}
