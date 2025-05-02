/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.toko_buku.data;

import java.sql.*;

/**
 *
 * @author rifki
 */
public class DbConnection {
    String url = "jdbc:mysql://localhost:3306/db_tokobuku";
    String username = "root";
    String password = "root";
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("koneksi berhasil!");
        return DriverManager.getConnection(url, username, password);
    }
}
