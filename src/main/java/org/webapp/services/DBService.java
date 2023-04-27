package org.webapp.services;

import org.webapp.dao.OrderDAO;
import org.webapp.dao.ProductDAO;
import org.webapp.dao.UserDAO;

public interface DBService {

    // user methods
    UserDAO getUserById(String userId);
    UserDAO saveUser(UserDAO userDAO);
    boolean deleteUser(String userId);

    // ProductDAO methods
    ProductDAO getProductById(String productId);
    ProductDAO saveProduct(ProductDAO productDAO);
    boolean deleteProduct(String productId);

    // Order methods
    OrderDAO getOrderById(String orderId);
    OrderDAO saveOrder(OrderDAO orderDAO);
    boolean deleteOrder(String orderId);
}
