package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.LoginComponentFactory;
import mapper.BookMapper;
import model.Order;
import model.User;
import model.builder.OrderBuilder;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;

public class BookController {

    private final BookView bookView;
    private final BookService bookService;
    private final OrderService orderService;
    private final User appUser;

    public BookController(User appUser, BookView bookView, BookService bookService, OrderService orderService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;
        this.appUser = appUser;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
        this.bookView.addBackButtonListener(new BackButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();


            if(title.isEmpty() || author.isEmpty() || stock.isEmpty() || price.isEmpty()){
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author, Title, Price or Quantity fields", "Can not have an empty field.");
            } else{
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(Integer.parseInt(stock)).setPrice(Integer.parseInt(price)).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if(savedBook){
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else{
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding the book to the database. Please try again!");

                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if(deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else{
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "There was a problem with the database.");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String quantity = bookView.getQuantity();
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (bookDTO == null) {
                bookView.addDisplayAlertMessage("Sell Error", "Problem at selecting Book", "You must select a book before pressing the sell button.");

            } else if (quantity.isEmpty()) {
                bookView.addDisplayAlertMessage("Sell Error", "Problem at Quantity", "Can not have an empty quantity field.");

            } else {
                int pricePerItem = bookDTO.getPrice();
                int stock = bookDTO.getStock();
                String item = bookDTO.getTitle() + ", " + bookDTO.getAuthor();

                if(Integer.parseInt(quantity) > stock) {
                    bookView.addDisplayAlertMessage("Sell Error", "Problem at Quantity", "Quantity can't be bigger than Stock.");

                } else {
                    Order order = new OrderBuilder().setItem(item).setSoldDate(LocalDate.now()).setPricePerItem(pricePerItem).setQuantity(Integer.parseInt(quantity)).setEmployeeSellerId(appUser.getId()).build();
                    boolean soldOrder = orderService.sell(order);
                    bookDTO.setStock(stock - Integer.parseInt(quantity));
                    boolean updatedBookStock = bookService.updateStock(BookMapper.convertBookDTOToBook(bookDTO));

                    if (soldOrder && updatedBookStock) {
                        bookView.addDisplayAlertMessage("Sold Successful", "Book Sold", "Book was successfully sold.");
                        bookView.removeBookFromObservableList(bookDTO);
                        bookView.addBookToObservableList(bookDTO);
                    } else {
                        bookView.addDisplayAlertMessage("Sold Error", "Problem at selling Book", "There was a problem at selling the book. Please try again!");

                    }
                }
            }
        }
    }

    private class BackButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            bookView.getPrimaryStage().setScene( LoginComponentFactory.getInstance(null, null).getLoginView().getScene());
            //mai trebuie un clear data pentru scena
            //scena ar putea fi trimisa ca parametru in BookController ca backScene?
            bookView.getPrimaryStage().show();
        }
    }
}
