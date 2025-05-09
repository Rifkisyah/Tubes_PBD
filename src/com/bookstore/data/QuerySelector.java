/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.data;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author rifki
 */
public class QuerySelector { // ini adalah class yang menyimpan kesemua query
    private String queryCheck, queryInsert, queryUpdate, queryDelete; // atribut untuk menyimpan tiap jenis query
    private MysqlConnection mysqlConnection; // koneksi ke databse
    private PreparedStatement stmt; // prepared statement untuk mengeksekusi query
    private ResultSet rslt; // untuk menampung hasil eksekusi statement
    private int countData; // untuk menghitung berapa jumlah baris data
    
    // untuk mengambil semua data di tabel PO
    public void getAllDataPo() throws ClassNotFoundException, SQLException{ 
        this.mysqlConnection = new MysqlConnection();
        this.queryCheck = 
            "SELECT " +
            "po.nota_PO, " +
            "po.id_pegawai, " +
            "ap.nama AS nama_pegawai, " +
            "po.id_vendor, " +
            "v.Nama_Vendor, " +
            "po.isbn, " +
            "mb.judul_buku, " +
            "po.tanggal_PO, " +
            "po.estimasi_tanggal_datang, " +
            "po.jumlah_PO, " +
            "po.total_biaya, " +
            "po.status_PO " +
            "FROM T_PurchaseOrder po " +
            "JOIN T_AkunPegawai ap ON po.id_pegawai = ap.id_pegawai " +
            "JOIN T_MasterBuku mb ON po.isbn = mb.isbn " +
            "JOIN T_Vendor v ON po.id_vendor = v.id_vendor";
        
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        this.rslt = stmt.executeQuery();
    }
    
    // untuk mengambil semua data di tabel Rak
    public void getAllDataRack() throws ClassNotFoundException, SQLException{ 
        this. mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT *FROM T_Rak";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        this.rslt = stmt.executeQuery();
    }
    
    // untuk mengambil semua data di tabel detail master buku jenis toko
    public void getAllDataStock() throws ClassNotFoundException, SQLException{
        this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT *FROM T_DetailMasterbuku WHERE jenis_inventaris = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, "toko");
        this.rslt = stmt.executeQuery();
    }
    
    //untuk mengambil semua data di tabel detail master buku jenis vendor
    public void getAllDataVendorStock() throws ClassNotFoundException, SQLException{
       this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT *FROM T_DetailMasterbuku WHERE jenis_inventaris = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, "vendor");
        this.rslt = stmt.executeQuery();
    }
    
    // untuk menghitung data yang tersedia
    public void getCountRowData(String data_reference, String table, String column) throws SQLException, ClassNotFoundException{ 
        this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, data_reference);
        this.rslt = stmt.executeQuery();
        
        rslt.next();
        this.countData = rslt.getInt(1);
    }

    // untuk menghapus 1 buah baris data pada tabel
    public void deleteRowData(String data_reference, String table, String column) throws ClassNotFoundException, SQLException{
        this.mysqlConnection = new MysqlConnection();
        
        this.queryDelete = "DELETE FROM " + table + " WHERE " + column + " = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryDelete);
        
        stmt.setString(1, data_reference);
        this.countData = stmt.executeUpdate();
    }
    
    //untuk  melakukan update data pada tabel dengan 3 kolom
    public void updateRowDataWith3Columns(String data_reference, String data1, String data2, String table, String column_reference, String column2, String column3) throws SQLException, ClassNotFoundException{
        this.mysqlConnection = new MysqlConnection();
        
        this.queryInsert = "UPDATE " + table + " SET " + column2 + " = ?, " + column3 + " = ? WHERE " + column_reference + " = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryInsert);
        
        stmt.setString(1, data1);
        stmt.setString(2, data2);
        stmt.setString(3, data_reference);
        this.countData = stmt.executeUpdate();
    }
    
    // menambahkan data ke tabel dengan 3 kolom
    public void insertRowDataWith3Columns(String data1, String data2, String data3, String table, String column1, String column2, String column3) throws ClassNotFoundException, SQLException{
        this.mysqlConnection = new MysqlConnection();
        
        this.queryInsert = "INSERT INTO " + table + "(" + column1 + ", " + column2 + ", " + column3 + ") values(?, ?, ?)";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryInsert);
        stmt.setString(1, data1);
        stmt.setString(2, data2);
        stmt.setString(3, data3);
        this.countData = stmt.executeUpdate();
    }
    
    // Mengecek kecocokan id_pegawai dan password lama
    public ResultSet checkPassword(String idPegawai, String password) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        this.queryCheck = "SELECT * FROM T_AkunPegawai WHERE id_pegawai = ? AND password = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, idPegawai);
        stmt.setString(2, password);
        this.rslt = stmt.executeQuery();
        return rslt;
    }

    // Melakukan update password
    public int updatePassword(String idPegawai, String newPassword) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        this.queryUpdate = "UPDATE T_AkunPegawai SET password = ? WHERE id_pegawai = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryUpdate);
        stmt.setString(1, newPassword);
        stmt.setString(2, idPegawai);
        return stmt.executeUpdate();
    }

    // untuk search field
    public void searchBookDetail(String keyword, String jenisInventaris) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();

        this.queryCheck = "SELECT * FROM T_DetailMasterbuku WHERE (isbn LIKE ? OR judul_buku LIKE ?) AND jenis_inventaris = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");
        stmt.setString(3, jenisInventaris);

        this.rslt = stmt.executeQuery();
    }

    //untuk mendapatkan semua nama dari vendor
    public void getAllVendor() throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT * FROM T_Vendor";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        this.rslt = stmt.executeQuery();
    }

    // untuk menambahkan data ke PO
    public void insertPurchaseOrder(String notaPO, String idPegawai, String idVendor, String isbn, LocalDate tanggalPO, LocalDate estimasiDatang, int jumlahPO, BigDecimal totalBiaya, String statusPO)
        throws SQLException, ClassNotFoundException {

        this.mysqlConnection = new MysqlConnection();

        this.queryInsert = "INSERT INTO t_purchaseorder " +
                           "(nota_PO, id_pegawai, id_vendor, isbn, tanggal_po, estimasi_tanggal_datang, jumlah_po, total_biaya, status_po) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        this.stmt = mysqlConnection.getConnection().prepareStatement(queryInsert);
        stmt.setString(1, notaPO);
        stmt.setString(2, idPegawai);
        stmt.setString(3, idVendor);
        stmt.setString(4, isbn);
        stmt.setDate(5, java.sql.Date.valueOf(tanggalPO));
        stmt.setDate(6, java.sql.Date.valueOf(estimasiDatang));
        stmt.setInt(7, jumlahPO);
        stmt.setBigDecimal(8, totalBiaya);
        stmt.setString(9, statusPO);

        this.countData = stmt.executeUpdate(); // untuk menyimpan status eksekusi (jumlah baris terpengaruh)
    }

    // untuk mendapatkan id vebdor berdasarkan nama vendor
    public String getIdVendorByName(String vendorName) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT id_vendor FROM t_detailmasterbuku WHERE nama_vendor = ? LIMIT 1";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, vendorName);
        this.rslt = stmt.executeQuery();

        if (rslt.next()) {
            return rslt.getString("id_vendor");
        } else {
            return null;
        }
    }
    
    // untuk mendapatkan stock saat ini di gudang vendor
    public int getCurrentStock(String isbn, String idVendor) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        
        this.queryCheck = "SELECT stock_buku FROM t_detailmasterbuku WHERE isbn = ? AND id_vendor = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, isbn);
        stmt.setString(2, idVendor);
        this.rslt = stmt.executeQuery();

        if (rslt.next()) {
            return rslt.getInt("stock_buku");
        } else {
            return -1; // menandakan tidak ditemukan
        }
    }

    // untuk update stock milik vendor
    public void updateStock(String isbn, String idVendor, int newStock) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        this.queryUpdate = "UPDATE t_detailmasterbuku SET stock_buku = ?, tanggal_update_stock = NOW() WHERE isbn = ? AND id_vendor = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryUpdate);
        stmt.setInt(1, newStock);
        stmt.setString(2, isbn);
        stmt.setString(3, idVendor);
        this.countData = stmt.executeUpdate();
    }

    // untuk mendapatkan stock dan harga tiap produk PO
    public int getStockAndPrice(String isbn, String idVendor) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        this.queryCheck = "SELECT stock_buku, harga_satuan FROM t_detailmasterbuku WHERE isbn = ? AND id_vendor = ? AND jenis_inventaris = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, isbn);
        stmt.setString(2, idVendor);
        stmt.setString(3, "vendor");
        this.rslt = stmt.executeQuery();

        if (rslt.next()) {
            int stock = rslt.getInt("stock_buku");
            BigDecimal hargaSatuan = rslt.getBigDecimal("harga_satuan");
            return stock;
        }
        return -1; // menandakan buku tidak ditemukan
    }

    // untuk mendapatkan harga satuan dari buku
    public BigDecimal getHargaSatuan(String isbn, String idVendor) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();
        this.queryCheck = "SELECT harga_satuan FROM t_detailmasterbuku WHERE isbn = ? AND id_vendor = ? AND jenis_inventaris = ?";
        this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
        stmt.setString(1, isbn);
        stmt.setString(2, idVendor);
        stmt.setString(3, "vendor");
        this.rslt = stmt.executeQuery();

        if (rslt.next()) {
            return rslt.getBigDecimal("harga_satuan");
        } else {
            return BigDecimal.ZERO; // Mengembalikan harga 0 jika tidak ditemukan
        }
    }

    
    // untuk mengembalikan nilai hasil query
    public ResultSet getRslt() {
        return rslt;
    }

    // untuk mengembalikan jumlah baris data yang di hitung
    public int getCountData() {
        return countData;
    }
    
    
}
