package model.builder;

import model.Order;

import java.time.LocalDate;

public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder setId(Long id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder setItem(String item) {
        order.setItem(item);
        return this;
    }

    public OrderBuilder setQuantity(int quantity) {
        order.setQuantity(quantity);
        return this;
    }

    public OrderBuilder setPricePerItem(int pricePerItem) {
        order.setPricePerItem(pricePerItem);
        return this;
    }

    public OrderBuilder setSoldDate(LocalDate soldDate) {
        order.setSoldDate(soldDate);
        return this;
    }

    public OrderBuilder setEmployeeSellerId(Long employeeSellerId) {
        order.setEmployeeSellerId(employeeSellerId);
        return this;
    }

    public Order build() {
        return order;
    }
}
