package service.order;

import model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    boolean sell(Order order);
    boolean delete(Order order);
}
