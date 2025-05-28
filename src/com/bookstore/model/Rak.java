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
public class Rak {
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;
    
    public void getSemuaData(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT * FROM t_rak ORDER BY 1 ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.rsltst = this.pstmt.executeQuery();
        
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getSemuaDataDariKodeRak(String kodeRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT * FROM t_rak WHERE kode_rak = ? ORDER BY 1 ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, kodeRak);
            this.rsltst = this.pstmt.executeQuery();
        
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getKodeRak(String namaRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT kode_rak FROM t_rak WHERE nama_rak = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.pstmt.setString(1, namaRak);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void menambahDataRak(String kodeRak, String namaRak, String lokasiRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "INSERT INTO t_rak(kode_rak, nama_rak, lokasi_rak) VALUES(?, ?, ?)";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, kodeRak);
            this.pstmt.setString(2, namaRak);
            this.pstmt.setString(3, lokasiRak);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void updateNullKetikaRakDihapus(String kodeRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String update = "UPDATE t_detailmasterbuku SET kode_rak = NULL WHERE kode_rak = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            this.pstmt.setString(1, kodeRak);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void memperbaruiDataRak(String namaRak, String lokasiRak, String kodeRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "UPDATE t_rak SET nama_rak = ?, lokasi_rak = ? WHERE kode_rak = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, namaRak);
            this.pstmt.setString(2, lokasiRak);
            this.pstmt.setString(3, kodeRak);
            this.affectedRow = this.pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void menghapusRak(String kodeRak){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "DELETE FROM t_rak  WHERE kode_rak = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, kodeRak);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean cekIdRakSudahAda(String id) {
    String sql = "SELECT COUNT(*) FROM t_rak WHERE kode_rak = ?";
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
