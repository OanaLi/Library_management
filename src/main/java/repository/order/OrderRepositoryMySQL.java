package repository.order;

import model.Book;
import model.Order;
import model.builder.OrderBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.ORDER;

public class OrderRepositoryMySQL implements OrderRepository {

    private final Connection connection;

    public OrderRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM `" + ORDER + "`;";

        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM `" + ORDER + "` WHERE id = ?";

        Optional<Order> order = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                order=Optional.of(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean sell(Order order) {
        String newSql = "INSERT INTO `" + ORDER + "` VALUES(null, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, order.getItem());
            preparedStatement.setInt(2, order.getQuantity());
            preparedStatement.setInt(3, order.getPricePerItem());
            preparedStatement.setDate(4, java.sql.Date.valueOf(order.getSoldDate()));
            preparedStatement.setLong(5, order.getEmployeeSellerId());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean delete(Order order) {

        String newSql = "DELETE FROM `" + ORDER + "` orders WHERE id = ?;";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String newSql = "TRUNCATE TABLE `" + ORDER + "` ;" ;

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException{
        Order order = new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setQuantity(resultSet.getInt("quantity"))
                .setPricePerItem(resultSet.getInt("pricePerItem"))
                .setSoldDate(new java.sql.Date(resultSet.getDate("soldDate").getTime()).toLocalDate())
                .setEmployeeSellerId(resultSet.getLong("employeeSellerId"))
                .build();

        return order;
    }
}
