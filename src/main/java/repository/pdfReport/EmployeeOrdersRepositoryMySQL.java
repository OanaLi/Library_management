package repository.pdfReport;

import model.Book;
import model.builder.BookBuilder;
import view.model.UserReportDTO;
import view.model.builder.UserReportDTOBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Tables.BOOK;
import static database.Constants.Tables.ORDER;

public class EmployeeOrdersRepositoryMySQL implements EmployeeOrdersRepository {

    private final Connection connection;

    public EmployeeOrdersRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<UserReportDTO> findAll() {
        List<UserReportDTO> userReport = new ArrayList<>();

        String sql = "SELECT u.username, SUM(o.quantity) AS soldBooks, SUM(o.quantity * o.pricePerItem) AS totalSum " +
                " FROM `order` o " +
                " JOIN user u ON u.id = o.employeeSellerId " +
                " JOIN user_role ur ON u.id = ur.user_id " +
                " JOIN role r ON ur.role_id = r.id " +
                " WHERE r.role = 'employee' AND o.soldDate >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) " +
                " GROUP BY u.id";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                userReport.add(getUserReportDTOFromResultSet(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userReport;
    }


    private UserReportDTO getUserReportDTOFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserReportDTOBuilder()
                .setUsername(resultSet.getString("username"))
                .setSoldBooks(resultSet.getInt("soldBooks"))
                .setTotalSum(resultSet.getInt("totalSum"))
                .build();
    }
}
