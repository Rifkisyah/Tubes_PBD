package com.bookstore.model;

import com.bookstore.data.MysqlConnection;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;


/**
 *
 * @author rifki
 */
public class AkunPegawai {
    private String idPegawai, idRole, nama, password;
    private LocalDate tanggalBuatAkun, tanggalTerakhirMasuk;
    
    private MysqlConnection mysqlConnection;
    private PreparedStatement pstmt;
    private ResultSet rsltst;
    private int affectedRow;

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getTanggalBuatAkun() {
        return tanggalBuatAkun;
    }

    public void setTanggalBuatAkun(LocalDate tanggalBuatAkun) {
        this.tanggalBuatAkun = tanggalBuatAkun;
    }

    public LocalDate getTanggalTerakhirMasuk() {
        return tanggalTerakhirMasuk;
    }

    public void setTanggalTerakhirMasuk(LocalDate tanggalTerakhirMasuk) {
        this.tanggalTerakhirMasuk = tanggalTerakhirMasuk;
    }
    
    public boolean cekIdPegawaiSudahAda(String id){
        String sql = "SELECT COUNT(*) FROM t_akunpegawai WHERE id_pegawai = ?";
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


    public void masukAkun(String idPegawai, String password){
        this.idPegawai = idPegawai;
        this.password = password;
        this.tanggalTerakhirMasuk = LocalDate.now();
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT * FROM t_akunpegawai WHERE id_pegawai = ? AND password = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            
            this.pstmt.setString(1, this.idPegawai);
            this.pstmt.setString(2, password);
            
            this.rsltst = pstmt.executeQuery();            
            if(this.rsltst.next()){
                setIdRole(this.rsltst.getString("id_role"));
                setNama(this.rsltst.getString("nama"));
                setPassword(this.rsltst.getString("password"));
                
                String update = "UPDATE T_AkunPegawai SET tanggal_terakhir_masuk_akun = ? WHERE id_pegawai = ? AND password = ?";
                this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
                
                this.pstmt.setDate(1, Date.valueOf(tanggalTerakhirMasuk));
                this.pstmt.setString(2, this.idPegawai);
                this.pstmt.setString(3, this.password);
                this.pstmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void gantiPassword(String passwordLama, String passwordBaru, String konfirmasiPassword){
        this.mysqlConnection = new MysqlConnection();
        
        if(!passwordLama.equals(this.password)){
            JOptionPane.showMessageDialog(null, "Password lama Salah!", "error", JOptionPane.ERROR_MESSAGE);
        } else if (!passwordBaru.equals(konfirmasiPassword)){
            JOptionPane.showMessageDialog(null, "Konfirmasi password Salah!", "error", JOptionPane.ERROR_MESSAGE);
        } else if (passwordLama.equals(passwordBaru)){
            JOptionPane.showMessageDialog(null, "password tidak berubah! password lama dan baru sama.", "informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                String update = "UPDATE t_akunpegawai SET password = ? WHERE id_pegawai = ?";
                this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
                
                this.pstmt.setString(1, passwordBaru);
                this.pstmt.setString(2, this.idPegawai);
                
                this.affectedRow = this.pstmt.executeUpdate();
                if(this.affectedRow > 0){
                    setPassword(rsltst.getString("password"));                
                }
                
                JOptionPane.showMessageDialog(null, "Password Berhasil Dirubah!", "informasi", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (SQLException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void getSemuaDataPegawaiDanRole(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "pg.id_pegawai, " +
                "pg.nama, " +
                "pg.password, " +
                "pg.id_role, " +
                "r.nama_role, " +
                "pg.tanggal_buat_akun, " +
                "pg.tanggal_terakhir_masuk_akun " +
                "FROM t_akunpegawai pg " +
                "LEFT JOIN t_role r ON pg.id_role = r.id_role " +
                "ORDER BY pg.id_pegawai ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.rsltst = this.pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getSemuaDataPegawai(){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select = "SELECT * FROM t_akunpegawai";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.rsltst = pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void deleteDataBerdasarkanIdPegawai(String idPegawai){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String delete = "DELETE FROM t_akunpegawai WHERE id_pegawai = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(delete);
            
            this.pstmt.setString(1, idPegawai);
            this.affectedRow = pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPegawaiBerdasarkanNamaRole(String namaRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String select =
                "SELECT " +
                "pg.id_pegawai, " +
                "pg.nama, " +
                "pg.password, " +
                "pg.id_role, " +
                "r.nama_role, " +
                "pg.tanggal_buat_akun, " +
                "pg.tanggal_terakhir_masuk_akun " +
                "FROM T_AkunPegawai pg " +
                "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
                "WHERE r.nama_role = ? " +
                "ORDER BY pg.id_pegawai ASC";

            this.pstmt = mysqlConnection.getConnection().prepareStatement(select);
            this.pstmt.setString(1, namaRole);
            this.rsltst = this.pstmt.executeQuery(); 
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabel(String nilaiCari){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String like = "SELECT " +
               "pg.id_pegawai, pg.nama, pg.password, pg.id_role, r.nama_role AS Nama_Role, " +
               "pg.tanggal_buat_akun, pg.tanggal_terakhir_masuk_akun " +
               "FROM T_AkunPegawai pg " +
               "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
               "WHERE pg.id_pegawai LIKE ? OR pg.nama LIKE ? " +
               "ORDER BY pg.id_pegawai ASC";

        this.pstmt = mysqlConnection.getConnection().prepareStatement(like);
        this.pstmt.setString(1, "%" + nilaiCari + "%");
        this.pstmt.setString(2, "%" + nilaiCari + "%");

        this.rsltst = pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getPencarianDataTabelFilterRole(String nilaiCari, String idRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String likeWithFilter = "SELECT " +
                "pg.id_pegawai, pg.nama, pg.password, pg.id_role, r.nama_role, " +
                "pg.tanggal_buat_akun, pg.tanggal_terakhir_masuk_akun " +
                "FROM T_AkunPegawai pg " +
                "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
                "WHERE (pg.id_pegawai LIKE ? OR pg.nama LIKE ?) AND pg.id_role = ? " +
                "ORDER BY pg.id_pegawai ASC";
            
            this.pstmt = mysqlConnection.getConnection().prepareStatement(likeWithFilter);
            this.pstmt.setString(1, "%" + nilaiCari + "%");
            this.pstmt.setString(2, "%" + nilaiCari + "%");
            this.pstmt.setString(3, idRole);

            this.rsltst = pstmt.executeQuery();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void getJumlahDataBerdasarkanId(String idPegawai){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String count = "SELECT COUNT(*) FROM t_akunpegawai WHERE id_pegawai = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(count);
            this.pstmt.setString(1, idPegawai);
            this.rsltst = this.pstmt.executeQuery();

            if (this.rsltst.next()) {
                this.affectedRow = this.rsltst.getInt(1);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public void updatePegawai(String idPegawai, String nama, String password, String idRole){
        this.mysqlConnection = new MysqlConnection();
        
        try {
            String update = "UPDATE t_akunpegawai SET nama = ?, password = ?, id_role = ? WHERE id_pegawai = ?";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(update);
            
            this.pstmt.setString(1, nama);
            this.pstmt.setString(2, password);
            this.pstmt.setString(3, idRole);
            this.pstmt.setString(4, idPegawai);
            this.affectedRow = this.pstmt.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    public void memasukanPegawaibaru(String idPegawai, String namaPegawai, String password, String idRole){
        this.mysqlConnection = new MysqlConnection();
        this.tanggalBuatAkun = LocalDate.now();
        
        try {
            String insert = "INSERT INTO t_akunpegawai(id_pegawai, nama, password, id_role, tanggal_buat_akun) VALUES(?, ?, ?, ?, ?)";
            this.pstmt = mysqlConnection.getConnection().prepareStatement(insert);
            
            this.pstmt.setString(1, idPegawai);
            this.pstmt.setString(2, namaPegawai);
            this.pstmt.setString(3, password);
            this.pstmt.setString(4, idRole);
            this.pstmt.setDate(5, Date.valueOf(tanggalBuatAkun));
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
