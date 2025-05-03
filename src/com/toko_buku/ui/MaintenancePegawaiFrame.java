/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.toko_buku.ui;

import java.awt.event.KeyEvent;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rifki
 */
public class MaintenancePegawaiFrame extends javax.swing.JFrame {
    Connection conn = null;
    Statement stat;
    ResultSet res;
    PreparedStatement pst = null;
    String role_id="";
    DefaultTableModel tb;
    
    SuperAdminFrame adminFrame;
    /**
     * Creates new form JFrameMaintenanceUser
     */
    public MaintenancePegawaiFrame(SuperAdminFrame adminFrame) {
        this.adminFrame = adminFrame;
        initComponents();
        koneksiKeMySQL();
        dataToTable();
        dataKeKomboBox();
        setDefaultCloseOperation(0);
        setTitle("Toko Buku - Maintenance Pegawai");
    }
    
    private void koneksiKeMySQL(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_tokobuku", "root", "root");
            this.stat = this.conn.createStatement();
            System.out.println("koneksi berhasil");
        } catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
            System.err.println("koneksi gagal");
        }
    }
    
    private void aturKolom(){
        TableColumn column;
        TabelPegawai.setAutoResizeMode(TabelPegawai.AUTO_RESIZE_OFF);
        column = TabelPegawai.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = TabelPegawai.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = TabelPegawai.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = TabelPegawai.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = TabelPegawai.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = TabelPegawai.getColumnModel().getColumn(5);
        column.setPreferredWidth(200);
        column = TabelPegawai.getColumnModel().getColumn(6);
        column.setPreferredWidth(200);
    }
    
    private void dataToTable(){
        tb = new DefaultTableModel();
        tb.addColumn("Id Pegawai");
        tb.addColumn("Nama Pegawai");
        tb.addColumn("Password");
        tb.addColumn("Role Id");
        tb.addColumn("Nama Role");
        tb.addColumn("Tanggal Buat Akun");
        tb.addColumn("Tanggal Terakhir Masuk");
        TabelPegawai.setModel(tb);
        
        try{
            this.res = this.stat.executeQuery("select a.Id_Pegawai,a.Nama_Pegawai,a.password,a.Id_Role,b.Nama_Role,a.Tanggal_Buat_Akun,a.Tanggal_Terakhir_Masuk from T_AkunPegawai a,T_Role b where a.Id_Role=b.Id_Role");
            while(res.next()){
                tb.addRow(new Object[]{
                    res.getString("Id_Pegawai"),
                    res.getString("Nama_Pegawai"),
                    res.getString("password"),
                    res.getString("Id_Role"),
                    res.getString("Nama_Role"),
                    res.getString("Tanggal_Buat_Akun"),
                    res.getString("Tanggal_Terakhir_Masuk")
                });
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("data ke tabel gagal");
        }
        aturKolom();
    }
    
    private void dataKeKomboBox(){
        try {
            String query = "SELECT *FROM T_Role";
            this.pst = this.conn.prepareStatement(query);
            this.res = this.pst.executeQuery();
            
            ComboBoxRolePegawai.removeAllItems();
            
            while(this.res.next()){
                ComboBoxRolePegawai.addItem(this.res.getString("Nama_Role"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("data ke kombo box gagal");
        }
    }
    
    private void membersihkanTextField(){
        FieldInputIdPegawai.setText("");
        FieldInputNamaPegawai.setText("");
        FieldInputPassword.setText("");
        FieldInputConfirmPassword.setText("");
        FieldInputIdPegawai.requestFocus();
    }
    
    private void cekDataUser(){
        try {
            if(FieldInputIdPegawai.getText().length() != 8){
                JOptionPane.showMessageDialog(null, "Panjang Karakter tidak boleh lebih dari 3 digit");
                FieldInputIdPegawai.requestFocus();
            } else {
                String query = "SELECT *FROM T_AkunPegawai WHERE Id_Pegawai=" + FieldInputIdPegawai.getText() + "";
                ResultSet result = this.stat.executeQuery(query);
                if(result.next()){
                    FieldInputNamaPegawai.setText(result.getString("Nama_Pegawai"));
                    FieldInputPassword.setText(result.getString("password"));
                    FieldInputConfirmPassword.setText(result.getString("password"));
                    FieldInputNamaPegawai.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Pegawai Tidak Ditemukan");
                    FieldInputNamaPegawai.setText("");
                    FieldInputPassword.setText("");
                    FieldInputConfirmPassword.setText("");
                    FieldInputNamaPegawai.requestFocus();
                }
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Pegawai Gagal Untuk Ditemukan!");
        }
    }
    
    private void cekValidasiPassword(){
        String pass = FieldInputPassword.getText();
        String konfPass = FieldInputConfirmPassword.getText();
        if(pass.equals(konfPass)){
            ComboBoxRolePegawai.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "validasi password salah!", "Pesan", JOptionPane.ERROR_MESSAGE);
            FieldInputConfirmPassword.requestFocus();
        }
    }
    
    private void cekRoleId(){
        String roleid = ComboBoxRolePegawai.getSelectedItem().toString();
        
        try {
            String query = "SELECT * FROM T_Role WHERE Nama_Role='" + roleid +"'";
            ResultSet result = this.stat.executeQuery(query);
            if(result.next()){
                this.role_id = result.getString("Id_Role");
            } else {
                this.role_id = "0";
            }
            insertUpdate();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Cek Role id Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("selected role :" + roleid);
            e.printStackTrace();
        }
    }
    
    private void insertUpdate(){
        try {
            Date tanggal = new Date();
            SimpleDateFormat setTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tglSekarang =  setTanggal.format(tanggal);

            String idPegawai = FieldInputIdPegawai.getText().trim();
            String namaPegawai = FieldInputNamaPegawai.getText().trim();
            String password = FieldInputPassword.getText().trim();

            // Cek apakah ID Pegawai sudah ada
            String cekQuery = "SELECT COUNT(*) FROM T_AkunPegawai WHERE Id_Pegawai = ?";
            PreparedStatement cekStmt = conn.prepareStatement(cekQuery);
            cekStmt.setString(1, idPegawai);
            ResultSet rs = cekStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // Jika sudah ada, lakukan update
                String updateQuery = "UPDATE T_AkunPegawai SET Nama_Pegawai = ?, password = ?, Id_Role = ?, Tanggal_Buat_Akun = ? WHERE Id_Pegawai = ?";
                PreparedStatement ps = conn.prepareStatement(updateQuery);
                ps.setString(1, namaPegawai);
                ps.setString(2, password);
                ps.setString(3, this.role_id);
                ps.setString(4, tglSekarang);
                ps.setString(5, idPegawai);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui", "update", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Jika belum ada, lakukan insert
                String queryInsert = "INSERT INTO T_AkunPegawai (Id_Pegawai, Nama_Pegawai, password, Id_Role, Tanggal_Buat_Akun) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(queryInsert);
                ps.setString(1, idPegawai);
                ps.setString(2, namaPegawai);
                ps.setString(3, password);
                ps.setString(4, this.role_id);
                ps.setString(5, tglSekarang);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "insert", JOptionPane.INFORMATION_MESSAGE);
            }

            dataToTable();
            membersihkanTextField();
        } catch (SQLException ex) {
            Logger.getLogger(MaintenancePegawaiFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void hapusData() {
        if (JOptionPane.showConfirmDialog(null, "Apakah Yakin Akan Dihapus?", "Informasi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String query = "DELETE FROM T_AkunPegawai WHERE Id_Pegawai = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, FieldInputIdPegawai.getText().trim());

                int result = stmt.executeUpdate();
                if (result > 0) {
                    membersihkanTextField();
                    dataToTable();
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan / Gagal Dihapus", "Info", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Hapus Data Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pegawai Batal Dihapus");
            FieldInputIdPegawai.requestFocus();
        }
    }

    
    private boolean isRoleExists(String roleId) throws SQLException {
        String query = "SELECT 1 FROM T_Role WHERE Id_Role = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FieldInputConfirmPassword = new javax.swing.JTextField();
        ComboBoxRolePegawai = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelPegawai = new javax.swing.JTable();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        FieldInputIdPegawai = new javax.swing.JTextField();
        FieldInputNamaPegawai = new javax.swing.JTextField();
        FieldInputPassword = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        FieldInputConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputConfirmPasswordActionPerformed(evt);
            }
        });

        ComboBoxRolePegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBoxRolePegawai.setSelectedItem(ComboBoxRolePegawai);

        TabelPegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Pegawai", "Nama Pegawai", "Password", "Role Id", "Nama Role", "Tanggal Buat Akun", "Tanggal Terakhir Masuk"
            }
        ));
        TabelPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelPegawai);

        btn_simpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_simpan.setText("simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_hapus.setText("hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_keluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_keluar.setText("keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Maintenance Pegawai");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("ID Pegawai");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nama Pegawai");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Role Pegawai");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Confirm Password");

        FieldInputIdPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputIdPegawaiActionPerformed(evt);
            }
        });
        FieldInputIdPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldInputIdPegawaiKeyPressed(evt);
            }
        });

        FieldInputNamaPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputNamaPegawaiActionPerformed(evt);
            }
        });

        FieldInputPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(190, 190, 190))
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_keluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_hapus))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBoxRolePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(FieldInputPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(FieldInputConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(FieldInputNamaPegawai)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(FieldInputIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(FieldInputIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(FieldInputNamaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(FieldInputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(FieldInputConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(ComboBoxRolePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_hapus)
                    .addComponent(btn_keluar))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FieldInputConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputConfirmPasswordActionPerformed

    private void FieldInputIdPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputIdPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputIdPegawaiActionPerformed

    private void FieldInputIdPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldInputIdPegawaiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
//            txt_name.requestFocus();
            cekValidasiPassword();
        }
    }//GEN-LAST:event_FieldInputIdPegawaiKeyPressed

    private void FieldInputNamaPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputNamaPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputNamaPegawaiActionPerformed

    private void FieldInputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputPasswordActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar Dari Maintenance Pegawai?") == JOptionPane.YES_OPTION){
            this.setVisible(false);
            adminFrame.setEnabled(true);
            adminFrame.requestFocus();
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String kodeUser = FieldInputIdPegawai.getText();
        String namaUser = FieldInputNamaPegawai.getText();
        String pass = FieldInputPassword.getText();
        String konfPass = FieldInputConfirmPassword.getText();
        
        if(namaUser.isEmpty() && kodeUser.isEmpty() && pass.isEmpty() && konfPass.isEmpty()){
            JOptionPane.showMessageDialog(null, "Terdapat Data Yangg Masih Kosong!");
            FieldInputIdPegawai.requestFocus();
        } else {
            if(kodeUser.length() >= 6){
                if(pass.equals(konfPass)){
                    cekRoleId();
                } else {
                    JOptionPane.showMessageDialog(null, "Password Tidak Sama!", "Pesan", JOptionPane.ERROR_MESSAGE);
                    FieldInputConfirmPassword.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Panjang Karakter ID Harus 8 Digit");
                FieldInputIdPegawai.requestFocus();
            }

        } 
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if(FieldInputIdPegawai.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Id Tidak Boleh Kosong");
            FieldInputIdPegawai.requestFocus();
        } else {
            hapusData();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void TabelPegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPegawaiMouseClicked
        FieldInputIdPegawai.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 0) + "");
        FieldInputNamaPegawai.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 1) + "");
        FieldInputPassword.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 2) + "");
        FieldInputConfirmPassword.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 2) + "");
        
        String selectedRole = tb.getValueAt(TabelPegawai.getSelectedRow(), 3).toString().trim();

        // Loop ke item JComboBox untuk mencari dan set sesuai value
        for (int i = 0; i < ComboBoxRolePegawai.getItemCount(); i++) {
            String item = tb.getValueAt(TabelPegawai.getSelectedRow(), 3).toString().trim();
            if (item.equalsIgnoreCase(selectedRole)) {
                ComboBoxRolePegawai.setSelectedIndex(i);
                break;
            }
        }
    }//GEN-LAST:event_TabelPegawaiMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxRolePegawai;
    private javax.swing.JTextField FieldInputConfirmPassword;
    private javax.swing.JTextField FieldInputIdPegawai;
    private javax.swing.JTextField FieldInputNamaPegawai;
    private javax.swing.JTextField FieldInputPassword;
    private javax.swing.JTable TabelPegawai;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
