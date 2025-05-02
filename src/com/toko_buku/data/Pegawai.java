/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.toko_buku.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author rifki
 */
public class Pegawai {
    private String idPegawai, namaPegawai, passwordPegawai, idRole;

    public Pegawai(String idPegawai, String namaPegawai, String passwordPegawai, String idRole) {
        this.idPegawai = idPegawai;
        this.idRole = idRole;
        this.namaPegawai = namaPegawai;
        this.passwordPegawai = passwordPegawai;
    }
    
    public static Pegawai PegawaiMasuk(String idPegawai, String PasswordPegawai, Connection conn){
        try {
            // Query to check if the employee exists
            String query = "SELECT * FROM T_AkunPegawai WHERE Id_Pegawai = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, idPegawai);
            stmt.setString(2, PasswordPegawai);

            ResultSet rslt = stmt.executeQuery();

            if(rslt.next()){
                // Query to update the last login date
                String updateQuery = "UPDATE T_AkunPegawai SET Tanggal_Terakhir_Masuk = ? WHERE Id_Pegawai = ? AND Password = ?";
                PreparedStatement stmt2 = conn.prepareStatement(updateQuery);

                stmt2.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                stmt2.setString(2, idPegawai);
                stmt2.setString(3, PasswordPegawai);
                stmt2.executeUpdate();

                // Return the employee object
                return new Pegawai(
                        rslt.getString("Id_Pegawai"), 
                        rslt.getString("Nama_Pegawai"), 
                        rslt.getString("Password"), 
                        rslt.getString("Id_Role")
                );
            } else {
                return null;  // Return null if no matching employee is found
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;  // Handle exceptions and return null if an error occurs
        }
    }

    
    // getter & setter

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

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getPasswordPegawai() {
        return passwordPegawai;
    }

    public void setPasswordPegawai(String passwordPegawai) {
        this.passwordPegawai = passwordPegawai;
    }
}
