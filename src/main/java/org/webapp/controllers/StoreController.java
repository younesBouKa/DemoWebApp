package org.webapp.controllers;

import org.tools.Log;
import org.web.annotations.others.Controller;
import org.web.annotations.others.Valid;
import org.web.annotations.params.global.Names;
import org.web.annotations.params.global.Param;
import org.web.annotations.params.global.ParamSrc;
import org.web.annotations.params.global.Source;
import org.web.annotations.params.types.QueryParam;
import org.web.annotations.scopes.RequestScope;
import org.web.annotations.scopes.SessionScope;
import org.webapp.dao.OrderDAO;
import org.webapp.others.NameQualifier;
import org.webapp.services.OrderService;
import org.webapp.services.ProductService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@SessionScope
@Singleton
@Controller(root = "/store")
public class StoreController{
    private static final Log logger = Log.getInstance(StoreController.class);
    @Inject
    @RequestScope
    private OrderService orderService;

    @Inject
    @NameQualifier(name="org.webapp.services.impls.ProductServiceImpl")
    @SessionScope
    private ProductService productService;

    // test mixing annotation (get the nearest one to parameter)
    // in this case @QueryParam
    @org.web.annotations.methods.Get(route = "/orders")
    //@Source(src = ParamSrc.BODY)
    //@Names(names = {"orderId"})
    public OrderDAO getOrder(
            //@Param(name = "id", type = ParamSrc.HEADER)
            @QueryParam(name = "id")
            @Valid String orderId,
            @QueryParam(name = "count") boolean page,
            HttpSession httpSession,
            HttpServletRequest request){
        logger.debug("HttpServletRequest: "+request.getPathInfo());
        logger.debug("HttpSession: "+httpSession.getId());
        logger.debug("params: "+orderId+" , "+page);
        return orderService.getOrder(orderId);
    }


    @org.web.annotations.methods.Route(route = "/orders", method = org.web.annotations.methods.HttpMethod.POST)
    @Source(src = ParamSrc.BODY)
    @Names(names={"userId","productId","quantity","unitePrice"})
    public OrderDAO saveOrder(String userId,
                              @Param(name = "product", type=ParamSrc.QUERY) String productId,
                              int quantity,
                              int unitePrice,
                              HttpServletRequest request,
                              HttpServletResponse response){
        logger.debug("saveOrder path: "+request.getRequestURI());
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setProductId(productId);
        orderDAO.setQuantity(quantity);
        orderDAO.setIdUser(userId);
        orderDAO.setUnitePrice(unitePrice);
        return orderService.saveOrder(orderDAO);
    }



    @org.web.annotations.methods.Put(route = "/orders")
    @Source(src = ParamSrc.BODY)
    @Names(names={"orderId","userId","productId","quantity","unitePrice"})
    public OrderDAO updateOrder(String orderId, String userId, String productId, int quantity, int unitePrice){
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.setId(orderId);
        orderDAO.setProductId(productId);
        orderDAO.setQuantity(quantity);
        orderDAO.setIdUser(userId);
        orderDAO.setUnitePrice(unitePrice);
        return orderService.saveOrder(orderDAO);
    }

    @org.web.annotations.methods.Delete(route = "/orders")
    public boolean deleteOrder(@QueryParam(name = "id") String orderId){
        return orderService.deleteOrder(orderId);
    }
}
