package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private static volatile EmployeeComponentFactory instance;


    //SINGLETON
    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, User appUser){
        if (instance == null){
            synchronized (EmployeeComponentFactory.class){
                if(instance == null){
                    instance = new EmployeeComponentFactory(componentsForTest, primaryStage, appUser);
                }
            }
        }
        return instance;
    }


    private EmployeeComponentFactory(Boolean componentsForTest, Stage primaryStage, User appUser){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        this.orderRepository = new OrderRepositoryMySQL(connection);
        this.orderService=new OrderServiceImpl(orderRepository);

        List<BookDTO> bookDTOs = BookMapper.convertBookDTOToListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, bookDTOs);
        this.bookController = new BookController(appUser, bookView, bookService, orderService); //OBS IN CONTROLER INTERACTIONAM DOAR CU SERVICE, NU CU REPOSITORY!

    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

}
