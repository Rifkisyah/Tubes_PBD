package com.bookstore.data;

/**
 *
 * @author rifki
 */
import java.sql.*;

public class MysqlConnection {
    private String url;
    private String username;
    private String password;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        this.url = "jdbc:mysql://localhost:3306/db_tokobuku";
        this.username = "root";
        this.password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
