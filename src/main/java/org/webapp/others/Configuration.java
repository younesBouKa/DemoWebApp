package org.webapp.others;

import org.injection.annotations.Alternative;
import org.injection.annotations.AlternativeConfig;
import org.injection.annotations.BeanScanPackages;
import org.injection.annotations.Component;
import org.injection.core.data.AlternativeInstance;
import org.injection.enums.BeanSourceType;
import org.web.annotations.scopes.RequestScope;
import org.web.annotations.scopes.SessionScope;
import org.webapp.services.DBService;
import org.webapp.services.OrderService;
import org.webapp.services.ProductService;
import org.webapp.services.UserService;
import org.webapp.services.impls.UserServiceImpl;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@BeanScanPackages(packages = {"org.webapp"})
public class Configuration {

    @Singleton
    @Alternative
    public UserService getUserServiceBean(DBService dbService,
                                          @SessionScope ProductService productService,
                                          @RequestScope OrderService orderService){
        UserServiceImpl userService =  new UserServiceImpl(dbService);
        userService.injectProductService(productService);
        userService.setOrderService(orderService);
        return userService;
    }

    @AlternativeConfig
    public Set<AlternativeInstance> getAlternatives(){
        Set<AlternativeInstance> alternatives = new HashSet<>();
        alternatives.add(new AlternativeInstance(UserService.class, "org.webapp.others.Configuration.getUserServiceBean", BeanSourceType.METHOD));
        //alternatives.add(new AlternativeInstance(UserService.class, UserServiceImpl.class, BeanSourceType.CLASS));
        return alternatives;
    }
}
