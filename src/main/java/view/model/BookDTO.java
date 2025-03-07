package view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookDTO {

    //author
    private StringProperty author;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if(author == null) {
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }


    //title
    private StringProperty title;

    public void setTitle(String title){
        titleProperty().set(title);
    }

    public String getTitle(){
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if(title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }


    //quantity
    private IntegerProperty stock;

    public void setStock(int stock) {
        stockProperty().set(stock);
    }

    public int getStock() {
        return stockProperty().get();
    }

    public IntegerProperty stockProperty() {
        if(stock == null) {
            stock = new SimpleIntegerProperty(this, "stock");
        }
        return stock;
    }


    //price
    private IntegerProperty price;

    public void setPrice(int price) {
        priceProperty().set(price);
    }

    public int getPrice() {
        return priceProperty().get();
    }

    public IntegerProperty priceProperty() {
        if(price == null) {
            price = new SimpleIntegerProperty(this, "price");
        }
        return price;
    }


}
