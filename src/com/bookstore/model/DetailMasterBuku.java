/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

import com.bookstore.data.MysqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author rifki
 */
public class DetailMasterBuku {
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;
    
    public void getDetailMasterbukuBerdasarkanTipe(String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "dm.id_detail_master_buku, " +
                "dm.isbn, " +
                "mb.judul_buku, " +
                "dm.kode_rak, " +
                "rk.nama_rak, " +
                "dm.id_vendor, " +
                "v.nama_vendor, " +
                "dm.stock_buku, " +
                "dm.tanggal_update_stock, " +
                "dm.harga_satuan, " +
                "dm.jenis_inventaris " +
                "FROM " +
                "T_Detailmasterbuku dm " +
                "LEFT " +
                "JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
                "JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
                "JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
                "WHERE dm.jenis_inventaris = ? " +
                "ORDER BY dm.id_detail_master_buku ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.pstmt.setString(1, type);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelMasterBuku(String nilaiCari, String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query =
                "SELECT " +
                "dm.id_detail_master_buku, dm.isbn, mb.judul_buku, " +
                "dm.kode_rak, rk.nama_rak, " +
                "dm.id_vendor, v.nama_vendor, " +
                "dm.stock_buku, dm.tanggal_update_stock, " +
                "dm.harga_satuan, dm.jenis_inventaris " +
                "FROM T_Detailmasterbuku dm " +
                "LEFT JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
                "LEFT JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
                "LEFT JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
                "WHERE (dm.isbn LIKE ? OR mb.judul_buku LIKE ?) " +
                "AND dm.jenis_inventaris = ? " +
                "ORDER BY dm.id_detail_master_buku ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, "%" + nilaiCari + "%");
            this.pstmt.setString(2, "%" + nilaiCari + "%");
            this.pstmt.setString(3, type);

            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelBerdasarkanFilterVendor(String nilaiCari, String idVendor, String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
            "SELECT " +
            "dm.id_detail_master_buku, dm.isbn, mb.judul_buku, " +
            "dm.kode_rak, rk.nama_rak, " +
            "dm.id_vendor, v.nama_vendor, " +
            "dm.stock_buku, dm.tanggal_update_stock, " +
            "dm.harga_satuan, dm.jenis_inventaris " +
            "FROM T_Detailmasterbuku dm " +
            "LEFT JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
            "LEFT JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
            "LEFT JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
            "WHERE (dm.isbn LIKE ? OR mb.judul_buku LIKE ?) AND dm.id_vendor = ? AND dm.jenis_inventaris = ?" +
            "ORDER BY dm.id_detail_master_buku ASC";

        this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
        this.pstmt.setString(1, "%" + nilaiCari + "%");
        this.pstmt.setString(2, "%" + nilaiCari + "%");
        this.pstmt.setString(3, idVendor);
        this.pstmt.setString(4, type);

        this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getFilterVendor(String namaVendor, String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "dm.id_detail_master_buku, " +
                "dm.isbn, " +
                "mb.judul_buku, " +
                "dm.kode_rak, " +
                "rk.nama_rak, " +
                "dm.id_vendor, " +
                "v.nama_vendor, " +
                "dm.stock_buku, " +
                "dm.tanggal_update_stock, " +
                "dm.harga_satuan, " +
                "dm.jenis_inventaris " +
                "FROM " +
                "T_Detailmasterbuku dm " +
                "LEFT " +
                "JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
                "JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
                "JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
                "WHERE dm.jenis_inventaris = ? AND v.nama_vendor = ? " +
                "ORDER BY dm.id_detail_master_buku ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.pstmt.setString(1, type);
            this.pstmt.setString(2, namaVendor);
            this.rsltst = this.pstmt.executeQuery();   
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getStokDanHargaSatuan(String isbn, String idVendor, String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT stock_buku, harga_satuan FROM t_detailmasterbuku WHERE isbn = ? AND id_vendor = ? AND jenis_inventaris = ? ORDER BY 1 ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, isbn);
            this.pstmt.setString(2, idVendor);
            this.pstmt.setString(3, type);
            this.rsltst = this.pstmt.executeQuery();
        } catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void updateStokDanTanggal(int newStock, String isbn, String idVendor, String type){
        this.mysqlConnection = new MysqlConnection();
        LocalDate tanggalUpdateStok = LocalDate.now();
        
        try {
            String update = "UPDATE t_detailmasterbuku SET stock_buku = ?, tanggal_update_stock = ? WHERE isbn = ? AND id_vendor = ? AND jenis_inventaris = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            this.pstmt.setInt(1, newStock);
            this.pstmt.setDate(2, java.sql.Date.valueOf(tanggalUpdateStok));
            this.pstmt.setString(3, isbn);
            this.pstmt.setString(4, idVendor);
            this.pstmt.setString(5, type);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void updateStokDanTanggal(int newStock, String isbn, String idVendor, String kodeRak, String jenisInventaris) {
        this.mysqlConnection = new MysqlConnection();
        LocalDate tanggalUpdateStok = LocalDate.now();

        try {
            String update = "UPDATE t_detailmasterbuku SET stock_buku = ?, tanggal_update_stock = ? " +
                            "WHERE isbn = ? AND id_vendor = ? AND kode_rak = ? AND jenis_inventaris = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            this.pstmt.setInt(1, newStock);
            this.pstmt.setDate(2, java.sql.Date.valueOf(tanggalUpdateStok));
            this.pstmt.setString(3, isbn);  
            this.pstmt.setString(4, idVendor);
            this.pstmt.setString(5, kodeRak);
            this.pstmt.setString(6, jenisInventaris);
            this.affectedRow = this.pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    
    
    public void getSemuaDataBerdasarkanIsbn(String isbn, String type){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT * FROM t_detailmasterbuku WHERE isbn = ? AND jenis_inventaris = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.pstmt.setString(1, isbn);
            this.pstmt.setString(2, type);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void menambahkanBukuBaru(String idDetailmasterbuku, String isbn, String kodeRak, String poId, int jumlah, LocalDate tglUpdate, int harga, String jenisInventaris) throws SQLException {
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String sql = "INSERT INTO t_detailmasterbuku (id_detail_master_buku, isbn, kode_rak, id_vendor, stock_buku, tanggal_update_stock, harga_satuan, jenis_inventaris) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(sql);
            this.pstmt.setString(1, idDetailmasterbuku);
            this.pstmt.setString(2, isbn);
            this.pstmt.setString(3, kodeRak);
            this.pstmt.setString(4, poId);
            this.pstmt.setInt(5, jumlah);
            this.pstmt.setDate(6, java.sql.Date.valueOf(tglUpdate));
            this.pstmt.setInt(7, harga);
            this.pstmt.setString(8, jenisInventaris);

            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public boolean cekIdDetailMasterBukuSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_detailmasterbuku WHERE id_detail_master_buku = ?";
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
    
    public void getDataByIsbnAndVendorAndRak(String isbn, String vendorId, String rackCode, String jenis) {
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT * FROM t_detailmasterbuku WHERE isbn = ? AND id_vendor = ? AND kode_rak = ? AND jenis_inventaris = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, isbn);
            this.pstmt.setString(2, vendorId);
            this.pstmt.setString(3, rackCode);
            this.pstmt.setString(4, jenis);
            this.rsltst = this.pstmt.executeQuery();
            
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
