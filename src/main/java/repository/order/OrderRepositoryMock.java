package repository.order;

import model.Book;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryMock implements OrderRepository {

    public final List<Order> orders;

    public OrderRepositoryMock() {
        orders = new ArrayList<Order>();
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orders.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean sell(Order order) {
        return orders.add(order);
    }

    @Override
    public boolean delete(Order order) {
        return orders.remove(order);
    }

    @Override
    public void removeAll() {
        orders.clear();
    }
}
