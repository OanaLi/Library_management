import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import model.Book;
import database.DatabaseConnectionFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {
    private static BookRepository bookRepository;
    private static Book testBook, testBook2;

    @BeforeAll
    public static void setup(){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

        makeTestBooks();
    }

    private static void makeTestBooks() {
        testBook=new BookBuilder().setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 20) )
                .setPrice(15)
                .setStock(10)
                .build();

        testBook2=new BookBuilder().setTitle("Iona")
                .setAuthor("Liviu Ciurea")
                .setPublishedDate(LocalDate.of(1990, 11, 30) )
                .setPrice(30)
                .setStock(7)
                .build();
    }

    @BeforeEach
    public void clearDatabase() {
        bookRepository.removeAll();
    }

    @Test
    public void testFindAll(){
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void testFindById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void testSave(){
        assertTrue(bookRepository.save(testBook));
    }

    @Test
    public void testDelete(){
        bookRepository.save(testBook);
        assertTrue(bookRepository.delete(testBook));
    }

    @Test
    public void testRemoveAll(){
        bookRepository.save(testBook);
        bookRepository.save(testBook2);
        bookRepository.removeAll();
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }


}
