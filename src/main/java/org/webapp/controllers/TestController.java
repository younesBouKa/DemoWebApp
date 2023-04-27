package org.webapp.controllers;

import org.injection.annotations.qualifiers.markers.FirstFound;
import org.tools.Log;
import org.web.Constants;
import org.web.WebContext;
import org.web.WebProvider;
import org.web.annotations.methods.Get;
import org.web.annotations.others.Controller;
import org.web.annotations.others.Valid;
import org.web.annotations.params.types.QueryParam;
import org.web.annotations.scopes.RequestScope;
import org.web.annotations.scopes.SessionScope;
import org.webapp.others.NameQualifier;
import org.webapp.services.OrderService;
import org.webapp.services.ProductService;
import org.webapp.services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@SessionScope
@Singleton
@Controller(root = "/test")
public class TestController {
    private static final Log logger = Log.getInstance(TestController.class);
    @Inject
    @RequestScope
    private OrderService orderService;

    @Inject
    @NameQualifier(name="org.webapp.services.impls.ProductServiceImpl")
    @SessionScope
    private ProductService productService;

    // test mixing annotation (get the nearest one to parameter)
    // in this case @QueryParam
    @Get(route = "/orders")
    //@Source(src = ParamSrc.BODY)
    //@Names(names = {"orderId"})
    public String test(
            //@Param(name = "id", type = ParamSrc.HEADER)
            @QueryParam(name = "id")
            @Valid String orderId,
            @QueryParam(name = "count") boolean page,
            HttpSession httpSession,
            HttpServletRequest request){
        logger.debug("HttpServletRequest: "+request.getPathInfo());
        logger.debug("HttpSession: "+httpSession.getId());
        logger.debug("params: "+orderId+" , "+page);
        StringBuilder stringBuffer = new StringBuilder();
        WebContext webContext = (WebContext) request.getAttribute(Constants.WEB_CONTEXT);
        if(webContext!=null){
            webContext.getInjectionConfig()
                    .getBeanQualifierManager()
                    .setDefaultQualifier(()->FirstFound.class);
            stringBuffer.append(webContext.getInjectionConfig()
                    .getBeanContainer()
                    .getBeansWithFilter((beanInstance)->true)
                    .size());
        }
        WebProvider webProvider = (WebProvider) request.getAttribute(Constants.REQUEST_WEB_PROVIDER);
        if(webProvider!=null){
            UserService userService = webProvider.getBeanInstance(UserService.class);
            logger.debug(userService.login(orderId));
        }
        return stringBuffer.toString();
    }
}
