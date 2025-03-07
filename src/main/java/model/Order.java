package model;

import java.time.LocalDate;

public class Order {
    private Long id;
    private String item;
    private int quantity;
    private int pricePerItem;
    private LocalDate soldDate;
    private Long employeeSellerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(int pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public LocalDate getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public Long getEmployeeSellerId() {
        return employeeSellerId;
    }

    public void setEmployeeSellerId(Long employeeSellerId) {
        this.employeeSellerId = employeeSellerId;
    }

    @Override
    public String toString() {
        return "Order [id: " + id + ", item: " + item + ", quantity: " + quantity + ", pricePerItem: " + pricePerItem + ", soldDate: " + soldDate + ", employeeSellerId: " + employeeSellerId + "]";
    }
}
