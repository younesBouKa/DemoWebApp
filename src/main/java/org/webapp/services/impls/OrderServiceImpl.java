package org.webapp.services.impls;

import org.webapp.dao.OrderDAO;
import org.webapp.others.NameQualifier;
import org.webapp.services.DBService;
import org.webapp.services.OrderService;
import org.webapp.services.ProductService;
import org.injection.annotations.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrderServiceImpl implements OrderService {

    @Inject
    @NameQualifier(name = "org.webapp.services.impls.DBServiceImpl")
    private DBService dbService;
    @Inject
    private ProductService productService;

    @Override
    public OrderDAO getOrder(String orderId) {
        return dbService.getOrderById(orderId);
    }

    @Override
    public List<OrderDAO> getOrdersByUser(String userId) {
        return Stream.of(dbService.getOrderById(userId)).collect(Collectors.toList()); // TODO to modify later
    }

    @Override
    public OrderDAO saveOrder(OrderDAO orderDAO) {
        return dbService.saveOrder(orderDAO);
    }

    @Override
    public boolean deleteOrder(String orderId) {
        return dbService.deleteOrder(orderId);
    }
}
