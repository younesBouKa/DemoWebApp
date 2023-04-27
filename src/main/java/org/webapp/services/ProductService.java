package org.webapp.services;

import org.webapp.dao.ProductDAO;

public interface ProductService {

    ProductDAO getProduct(String productId);
    ProductDAO saveProduct(ProductDAO productDAO);
    boolean deleteProduct(String productId);
}
