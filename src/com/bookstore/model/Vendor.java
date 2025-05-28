/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

import com.bookstore.data.MysqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rifki
 */
public class Vendor {
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;
    
    public void getSemuaDataVendor(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT * FROM t_vendor";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabel(String nilaiCari){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String like = "SELECT * " +
               "FROM t_vendor " +
               "WHERE id_vendor LIKE ? OR nama_vendor LIKE ? OR alamat_vendor LIKE ? OR kontak_vendor LIKE ? " +
               "ORDER BY id_vendor ASC";

        this.pstmt = mysqlConnection.getConnection().prepareStatement(like);
        this.pstmt.setString(1, "%" + nilaiCari + "%");
        this.pstmt.setString(2, "%" + nilaiCari + "%");
        this.pstmt.setString(3, "%" + nilaiCari + "%");
        this.pstmt.setString(4, "%" + nilaiCari + "%");

        this.rsltst = pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getJumlahDataBerdasarkanIdVendor(String idVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String count = "SELECT COUNT(*) FROM t_vendor WHERE id_vendor = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(count);
            this.pstmt.setString(1, idVendor);
            this.rsltst = this.pstmt.executeQuery();

            if (this.rsltst.next()) {
                this.affectedRow = this.rsltst.getInt(1);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
       public void memasukanVendorBaru(String idVendor, String namaVendor, String alamatVendor, String kontakVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String insert = "INSERT INTO t_vendor(id_vendor, nama_vendor, alamat_vendor, kontak_vendor) VALUES(?, ?, ?, ?)";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(insert);
            
            this.pstmt.setString(1, idVendor);
            this.pstmt.setString(2, namaVendor);
            this.pstmt.setString(3, alamatVendor);
            this.pstmt.setString(4, kontakVendor);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
       
    public void updateVendor(String idVendor, String namaVendor, String alamatVendor, String kontakVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String update = "UPDATE t_vendor SET nama_vendor = ?, alamat_vendor = ?, kontak_vendor = ? WHERE id_vendor = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            
            this.pstmt.setString(1, idVendor);
            this.pstmt.setString(2, namaVendor);
            this.pstmt.setString(3, alamatVendor);
            this.pstmt.setString(4, kontakVendor);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void deleteDataBerdasarkanIdVendor(String idVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String delete = "DELETE FROM t_vendor WHERE id_vendor = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(delete);
            
            this.pstmt.setString(1, idVendor);
            this.affectedRow = pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getIdVendor(String namaVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT id_vendor FROM t_vendor WHERE nama_vendor = ? ORDER BY 1 ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, namaVendor);

            this.rsltst = this.pstmt.executeQuery();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean cekIdVendorSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_vendor WHERE id_vendor = ?";
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

    
    public ResultSet getResultSet(){
        return this.rsltst;
    }
    
    public int getAffectedRow(){
        return this.affectedRow;
    }
}
