/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uas_pemrograman_desktop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rifki
 */
public class DbConnection {
    private String url;
    private String username;
    private String password;

    public Connection getConnection(){
        Connection conn = null;
        try {
            this.url = "jdbc:mysql://localhost:3306/db5520123107";
            this.username = "root";
            this.password = "root";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return conn;
    }
}
