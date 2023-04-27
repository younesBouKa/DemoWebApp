package org.webapp.services.impls;

import org.injection.annotations.Component;
import org.web.annotations.scopes.RequestScope;
import org.web.annotations.scopes.SessionScope;
import org.webapp.dao.UserDAO;
import org.webapp.others.NameQualifier;
import org.webapp.services.DBService;
import org.webapp.services.OrderService;
import org.webapp.services.ProductService;
import org.webapp.services.UserService;
import org.injection.annotations.qualifiers.RegexQualifier;
import org.injection.annotations.qualifiers.markers.ElseFirstFound;
import org.injection.annotations.qualifiers.markers.EvalWithAND;
import org.injection.annotations.qualifiers.markers.FirstFound;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;


@Component
public class UserServiceImpl implements UserService {

    private DBService dbService;
    private ProductService productService;
    @Inject
    private OrderService orderService;

    @Inject
    public UserServiceImpl(
            @RequestScope
            @RegexQualifier(regex = ".*(_2)")
            @NameQualifier(name = "toto")
            @EvalWithAND
            //@EvalWithOR
            @ElseFirstFound
            //@Named(value = "org.webapp.services.impls.DBServiceImpl_2")
            DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public UserDAO login(String userId) {
        return dbService.getUserById(userId);
    }

    @Override
    public UserDAO register(UserDAO userDAO) {
        return dbService.saveUser(userDAO);
    }

    @Override
    public boolean deleteUser(String userId) {
        return dbService.deleteUser(userId);
    }

    @Override
    public UserDAO updateUserInfo(UserDAO userDAO) {
        return dbService.saveUser(userDAO);
    }

    @Override
    public String getMessage() {
        return "["+ new Date() +"]: "+this.toString();
    }

    @Inject
    public void injectProductService(
            @SessionScope
            @FirstFound ProductService productService){
        this.productService = productService;
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "dbService=" + dbService +
                ", productService=" + productService +
                ", orderService=" + orderService +
                '}';
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
