/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

import com.bookstore.data.MysqlConnection;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author rifki
 */
public class Role {
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;
    
    public boolean cekIdRoleSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_role WHERE id_role = ?";
        this.mysqlConnection = new MysqlConnection();

        try (PreparedStatement stmt = mysqlConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public void getSemuaDataRole(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT * FROM t_role";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public void getJumlahDataBerdasarkanIdRole(String idRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String count = "SELECT COUNT(*) FROM t_role WHERE id_role = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(count);
            this.pstmt.setString(1, idRole);
            this.rsltst = this.pstmt.executeQuery();

            if (this.rsltst.next()) {
                this.affectedRow = this.rsltst.getInt(1);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void memasukanRoleBaru(String idRole, String namaRole){
        this.mysqlConnection = new MysqlConnection();
        LocalDate tanggalBuatRole = LocalDate.now();

        try {
            String insert = "INSERT INTO t_role(id_role, nama_role, tanggal_buat) VALUES(?, ?, ?)";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(insert);

            this.pstmt.setString(1, idRole);
            this.pstmt.setString(2, namaRole);
            this.pstmt.setDate(3, Date.valueOf(tanggalBuatRole));
            this.affectedRow = this.pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void updateRole(String namaRole, String idRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String update = "UPDATE t_role SET nama_role = ? WHERE id_role = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            this.pstmt.setString(1, namaRole);
            this.pstmt.setString(2, idRole);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void hapusRole(String idRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String delete = "DELETE FROM t_role WHERE id_role = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(delete);
            this.pstmt.setString(1, idRole);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public ResultSet getResultSet(){
        return this.rsltst;
    }
    
    public int getAffectedRow(){
        return this.affectedRow;
    }
}
