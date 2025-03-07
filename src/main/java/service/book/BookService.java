package service.book;

import model.Book;

import java.util.*;

public interface BookService {
    //definim metodele pe care le poate folosi Presentation

    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    boolean updateStock(Book book);
    int getAgeOfBook(Long id);

}
