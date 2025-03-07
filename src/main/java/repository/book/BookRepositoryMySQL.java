package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.BOOK;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM " + BOOK;

        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    //aici nu trebuia cu prepared statement?
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM " + BOOK + " WHERE id=?";

        Optional<Book> book = Optional.empty();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO " + BOOK + " VALUES(null, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4, book.getStock());
            preparedStatement.setInt(5, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM " + BOOK + " WHERE author =\'" + book.getAuthor() + "\' AND title =\'" +book.getTitle()+"\';";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStock(Book book) {
        String newSql = "UPDATE " + BOOK + " SET stock = + ? WHERE author = ? AND title = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setInt(1, book.getStock());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getTitle());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    @Override
    public void removeAll() {
        String newSql = "TRUNCATE TABLE " + BOOK + ";" ;

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getInt("price"))
                .setStock(resultSet.getInt("stock"))
                .build();
    }
}
