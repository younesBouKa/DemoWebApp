package org.webapp.services.impls;

import org.webapp.dao.ProductDAO;
import org.webapp.others.FlagQualifier;
import org.webapp.services.DBService;
import org.webapp.services.ProductService;
import org.webapp.services.UserService;
import org.injection.annotations.Component;
import org.injection.annotations.qualifiers.RegexQualifier;
import org.tools.Log;

import javax.inject.Inject;

@Component
public class ProductServiceImpl implements ProductService {
    private static final Log logger = Log.getInstance(ProductServiceImpl.class);

    private DBService dbService;

    @Inject
    public ProductServiceImpl(
            //@RegexQualifier(regex="(.*)DBServiceImpl")
            @FlagQualifier
            DBService dbService){
        this.dbService = dbService;
    }

    @Override
    public ProductDAO getProduct(String productId) {
        logger.debug("getting product: "+productId);
        return dbService.getProductById(productId);
    }

    @Override
    public ProductDAO saveProduct(ProductDAO productDAO) {
        return dbService.saveProduct(productDAO);
    }

    @Override
    public boolean deleteProduct(String productId) {
        return dbService.deleteProduct(productId);
    }

    //@Inject //TODO test loop detection
    public void injectUserService(UserService userService){

    }
}
