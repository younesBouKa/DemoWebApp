package org.webapp;

import org.tools.Log;
import org.tools.exceptions.FrameworkException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DefaultFilter implements javax.servlet.Filter {
    private static final Log logger = Log.getInstance(DefaultFilter.class);
    private String staticResourceFolder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Filter config: "+filterConfig.toString());
        this.staticResourceFolder = filterConfig.getInitParameter("staticResourceFolder");
        if(staticResourceFolder==null || staticResourceFolder.trim().isEmpty())
            staticResourceFolder = "/WEB-INF/static";
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.startsWith("/static"))
            path = path.replace("/static",staticResourceFolder);
        RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(path);
        if(requestDispatcher==null)
            throw new FrameworkException("Can't find request dispatcher for path : "+path);
        requestDispatcher.forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("Destroy filter");
    }
}
