/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

import com.bookstore.data.MysqlConnection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author rifki
 */
public class PurchaseOrder {
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;
    
    public void getSemuaPoBerdasarkanIdPo(String idPo){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "SELECT * FROM t_purchaseorder WHERE id_po = ? ORDER BY 1 ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, idPo);
            this.rsltst = this.pstmt.executeQuery();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void menambahkanPO(String idPo, String idPegawai, String idVendor, String isbn, int jumlahPo, int jumlahDiterima, BigDecimal totalBiaya, String statusPo){
        this.mysqlConnection = new MysqlConnection();
        int estimasiHari = 7;
        LocalDate tanggalPO = LocalDate.now();
        LocalDate estimasiDatang = tanggalPO.plusDays(estimasiHari);
        
        try {
            String insert = "INSERT INTO t_purchaseorder " +
                           "(id_PO, id_pegawai, id_vendor, isbn, tanggal_po, estimasi_tanggal_datang, jumlah_po, jumlah_diterima, total_biaya, status_po) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            this.pstmt = mysqlConnection.getConnection().prepareStatement(insert);
            this.pstmt.setString(1, idPo);
            this.pstmt.setString(2, idPegawai);
            this.pstmt.setString(3, idVendor);
            this.pstmt.setString(4, isbn);
            this.pstmt.setDate(5, java.sql.Date.valueOf(tanggalPO));
            this.pstmt.setDate(6, java.sql.Date.valueOf(estimasiDatang));
            this.pstmt.setInt(7, jumlahPo);
            this.pstmt.setInt(8, jumlahDiterima);
            this.pstmt.setBigDecimal(9, totalBiaya);
            this.pstmt.setString(10, statusPo);

            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void menambahkanDataTerimaPO(String idPenerimaan, String idPo, String isbn, LocalDate tanggalTerima, int jumlahDiterima, BigDecimal totalHarga, String keterangan, String status){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "INSERT INTO t_penerimaanpurchaseorder " +
               "(Id_penerimaan_PO, id_po, isbn, tanggal_terima, jumlah_datang, total_harga, keterangan_penerimaan, status_penerimaan) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, idPenerimaan);
            this.pstmt.setString(2, idPo);
            this.pstmt.setString(3, isbn);
            this.pstmt.setDate(4, java.sql.Date.valueOf(tanggalTerima)); // Jika ingin pakai java.sql.Date, gunakan setDate()
            this.pstmt.setInt(5, jumlahDiterima);
            this.pstmt.setBigDecimal(6, totalHarga);
            this.pstmt.setString(7, keterangan);
            this.pstmt.setString(8, status);

            this.affectedRow = this.pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getSemuaPo(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query =
                "SELECT " +
                "po.Id_PO, " +
                "po.id_pegawai, " +
                "p.nama, " +
                "po.id_vendor, " +
                "v.nama_vendor, " +
                "po.isbn, " +
                "mb.judul_buku, " +
                "po.tanggal_po, " +
                "po.estimasi_tanggal_datang, " +
                "po.jumlah_po, " +
                "po.jumlah_diterima, " +
                "po.total_biaya, " +
                "po.status_po " +
                "FROM t_purchaseorder po " +
                "LEFT JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
                "JOIN t_akunpegawai p ON po.id_pegawai = p.id_pegawai " +
                "JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
                "ORDER BY po.Id_PO ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            
            this.rsltst = this.pstmt.executeQuery();
        
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getStatusPo() {
        this.mysqlConnection = new MysqlConnection();
        try {
            String query = "SELECT DISTINCT po.status_po FROM t_purchaseorder po ORDER BY po.status_po ASC";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.rsltst = this.pstmt.executeQuery();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    
    public void getSemuaDataBerdasarkanStatus(String status1, String status2){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query =
                "SELECT " +
                "po.Id_PO, " +
                "po.id_pegawai, " +
                "p.nama, " +
                "po.id_vendor, " +
                "v.nama_vendor, " +
                "po.isbn, " +
                "mb.judul_buku, " +
                "po.tanggal_po, " +
                "po.estimasi_tanggal_datang, " +
                "po.jumlah_po, " +
                "po.jumlah_diterima, " +
                "po.total_biaya, " +
                "po.status_po " +
                "FROM t_purchaseorder po " +
                "LEFT JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
                "JOIN t_akunpegawai p ON po.id_pegawai = p.id_pegawai " +
                "JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
                "WHERE po.status_po IN (?, ?) " +
                "ORDER BY po.Id_PO ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, status1);
            this.pstmt.setString(2, status2);
            
            this.rsltst = this.pstmt.executeQuery();
        
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void updateJumlahDiterima(int jumlahDiterima, String idPo){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query = "UPDATE T_PurchaseOrder SET jumlah_diterima = ? WHERE id_po = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setInt(1, jumlahDiterima);
            this.pstmt.setString(2, idPo);
            this.affectedRow = this.pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelPo(String nilaiCari){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String query =
                "SELECT " +
                "po.id_po, po.id_pegawai, ap.nama, po.id_vendor, vd.nama_vendor, mb.judul_buku, " +
                "po.isbn, po.tanggal_po, po.estimasi_tanggal_datang, " +
                "po.jumlah_po, po.jumlah_diterima, po.total_biaya, po.status_po " +
                "FROM t_purchaseorder po " +
                "JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
                "JOIN t_akunpegawai ap ON po.id_pegawai = ap.id_pegawai " +
                "JOIN t_vendor vd ON po.id_vendor = vd.id_vendor " +
                "WHERE po.isbn LIKE ? OR po.id_po LIKE ? " +
                "ORDER BY po.id_po ASC";


            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setString(1, "%" + nilaiCari + "%");
            this.pstmt.setString(2, "%" + nilaiCari + "%");

            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelPoBerdasarkanFilterVendor(String nilaiCari, String idVendor){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "po.id_po, po.id_pegawai, po.id_vendor, mb.judul_buku, " +
                "po.isbn, po.tanggal_po, po.estimasi_tanggal_datang, " +
                "po.jumlah_po, po.jumlah_diterima, po.total_biaya, po.status_po " +
                "FROM t_purchaseorder po " +
                "LEFT JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
                "WHERE po.isbn LIKE ? OR mb.judul_buku LIKE ? AND po.id_vendor = ? " +
                "ORDER BY po.id_po ASC";

        this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
        this.pstmt.setString(1, "%" + nilaiCari + "%");
        this.pstmt.setString(2, "%" + nilaiCari + "%");
        this.pstmt.setString(3, idVendor);

        this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelPoBerdasarkanFilterStatus(String nilaiCari, String status){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "po.id_po, po.id_pegawai, ap.nama, po.id_vendor, vd.nama_vendor, mb.judul_buku, " +
                "po.isbn, po.tanggal_po, po.estimasi_tanggal_datang, " +
                "po.jumlah_po, po.jumlah_diterima, po.total_biaya, po.status_po " +
                "FROM t_purchaseorder po " +
                "LEFT JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
                "JOIN t_akunpegawai ap ON po.id_pegawai = ap.id_pegawai " +
                "JOIN t_vendor vd ON po.id_vendor = vd.id_vendor " +
                "WHERE (po.isbn LIKE ? OR mb.judul_buku LIKE ?) AND po.status_po = ? " +
                "ORDER BY po.id_po ASC";

        this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
        this.pstmt.setString(1, "%" + nilaiCari + "%");
        this.pstmt.setString(2, "%" + nilaiCari + "%");
        this.pstmt.setString(3, status);

        this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void updateStatusDanJumlahDiterima(String[] kolom, Object[] nilai, String idPo){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            if (kolom.length != 2 || kolom.length != 2) {
                throw new IllegalArgumentException("Harus tepat 2 kolom dan 2 nilai.");
            }

            String query = "UPDATE t_purchaseorder SET " + kolom[0] + " = ?, " + kolom[1] + " = ? WHERE id_po = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(query);
            this.pstmt.setObject(1, nilai[0]);
            this.pstmt.setObject(2, nilai[1]);
            this.pstmt.setString(3, idPo);
            this.pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean cekIdPOSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_purchaseorder WHERE id_po = ?";
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

    
    public boolean cekIdPenerimaanPOSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_penerimaanpurchaseorder WHERE id_penerimaan_po = ?";
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

    
    public ResultSet getResultSet() {
        return rsltst;
    }

    public int getAffectedRow() {
        return affectedRow;
    }
    
    
}
