package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "ENTER_YOUR_DATABASE_URL";
    private static final String USER = "ENTER_YOUR_DATABASE_USERNAME";
    private static final String PASSWORD = "ENTER_YOUR_DATABASE_PASSWORD";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schema) {
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD);
            createTables();
        } catch (ClassNotFoundException e){ //exceptie specifica
            e.printStackTrace();
        } catch (SQLException e) { //exceptie generala
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException{
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author VARCHAR(500) NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " stock int NOT NULL," + //nou
                " price int NOT NULL," + //nou
                " PRIMARY KEY (id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);

        sql = "CREATE TABLE IF NOT EXISTS `order`(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " item VARCHAR(1000) NOT NULL," +
                " quantity INT NOT NULL," +
                " pricePerItem INT NOT NULL," +
                " soldDate datetime DEFAULT NULL," +
                " employeeSellerId bigint NOT NULL," +
                " PRIMARY KEY (id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException{
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection(){
        return connection;
    }
}
