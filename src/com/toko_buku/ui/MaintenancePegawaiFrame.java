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
import javax.swing.JTable;

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
        TabelPegawai.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        column = TabelPegawai.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = TabelPegawai.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = TabelPegawai.getColumnModel().getColumn(2);
        column.setPreferredWidth(300);
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
        tb.addColumn("Nama Pegawair");
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
            
            JComboRole.removeAllItems();
            
            while(this.res.next()){
                JComboRole.addItem(this.res.getString("Nama_Role"));
            }
            
//            this.res.last();
//            int jumlahData = this.res.getRow();
//            this.res.first();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("data ke kombo box gagal");
        }
    }
    
    private void membersihkanTextField(){
        txt_iduser.setText("");
        txt_name.setText("");
        txt_pass.setText("");
        txt_confPass.setText("");
        txt_iduser.requestFocus();
    }
    
    private void cekDataUser(){
        try {
            if(txt_iduser.getText().length() != 8){
                JOptionPane.showMessageDialog(null, "Panjang Karakter tidak boleh lebih dari 3 digit");
                txt_iduser.requestFocus();
            } else {
                String query = "SELECT *FROM T_AkunPegawai WHERE Id_Pegawai=" + txt_iduser.getText() + "";
                ResultSet result = this.stat.executeQuery(query);
                if(result.next()){
                    txt_name.setText(result.getString("Nama_Pegawai"));
                    txt_pass.setText(result.getString("password"));
                    txt_confPass.setText(result.getString("password"));
                    txt_name.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Pegawai Tidak Ditemukan");
                    txt_name.setText("");
                    txt_pass.setText("");
                    txt_confPass.setText("");
                    txt_name.requestFocus();
                }
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Pegawai Gagal Untuk Ditemukan!");
        }
    }
    
    private void cekValidasiPassword(){
        String pass = txt_pass.getText();
        String konfPass = txt_confPass.getText();
        if(pass.equals(konfPass)){
            JComboRole.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "validasi password salah!", "Pesan", JOptionPane.ERROR_MESSAGE);
            txt_confPass.requestFocus();
        }
    }
    
    private void cekRoleId(){
        String roleid = JComboRole.getSelectedItem().toString();
        
        try {
            String query = "SELECT * FROM T_Role WHERE Nama_Role='" + roleid +"'";
            ResultSet result = this.stat.executeQuery(query);
            if(result.next()){
                this.role_id = result.getString("Id_Role");
            } else {
                this.role_id = "0";
            }
            insertPegawai();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Cek Role id Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("selected role :" + roleid);
            e.printStackTrace();
        }
    }
    
    private void insertPegawai(){
        try {
            Date tanggal = new Date();
            SimpleDateFormat setTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tglSekarang =  setTanggal.format(tanggal);
            
            String queryInsert = "INSERT INTO T_AkunPegawai (Id_Pegawai, Nama_Pegawai, password, Id_Role, Tanggal_Buat_Akun) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(queryInsert);
            ps.setString(1, txt_iduser.getText());
            ps.setString(2, txt_name.getText());
            ps.setString(3, txt_pass.getText());
            ps.setString(4, this.role_id);
            ps.setString(5, tglSekarang);
            ps.executeUpdate();
            dataToTable();
            membersihkanTextField();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "insert", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(MaintenancePegawaiFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void hapusData(){
        if(JOptionPane.showConfirmDialog(null, "Apakah Yakin Akan Dihapus?", "informasi", JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION){
            try {
                Statement statDel = this.conn.createStatement();
                statDel.executeUpdate("DELETE FROM T_AkunPegawai WHERE Id_Pegawai=" + txt_iduser.getText());
                
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                membersihkanTextField();
                dataToTable();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(this, "Hapus Data Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pegawai Batal Dihapus");
            txt_iduser.requestFocus();
        }
    }
    
//    private boolean isRoleExists(String roleId) throws SQLException {
//        String query = "SELECT 1 FROM T_Role WHERE Id_Role = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, roleId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return rs.next();
//            }
//        }
//    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_confPass = new javax.swing.JTextField();
        JComboRole = new javax.swing.JComboBox<>();
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
        txt_iduser = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_pass = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_confPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_confPassActionPerformed(evt);
            }
        });

        JComboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        JComboRole.setSelectedItem(JComboRole);

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
        TabelPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabelPegawaiKeyPressed(evt);
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

        txt_iduser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_iduserActionPerformed(evt);
            }
        });
        txt_iduser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_iduserKeyPressed(evt);
            }
        });

        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        txt_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passActionPerformed(evt);
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
                            .addComponent(JComboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txt_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_confPass, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_name)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txt_iduser, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txt_iduser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txt_confPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(JComboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txt_confPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_confPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_confPassActionPerformed

    private void txt_iduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_iduserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_iduserActionPerformed

    private void txt_iduserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_iduserKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
//            txt_name.requestFocus();
            cekValidasiPassword();
        }
    }//GEN-LAST:event_txt_iduserKeyPressed

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void txt_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Quit?") == JOptionPane.YES_OPTION){
            this.setVisible(false);
            adminFrame.setEnabled(true);
            adminFrame.requestFocus();
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String kodeUser = txt_iduser.getText();
        String namaUser = txt_name.getText();
        String pass = txt_pass.getText();
        String konfPass = txt_confPass.getText();
        String role_id = JComboRole.getSelectedItem().toString();
        String roleid;
        
        if(namaUser.isEmpty() && kodeUser.isEmpty() && pass.isEmpty() && konfPass.isEmpty()){
            JOptionPane.showMessageDialog(null, "Terdapat Data Yangg Masih Kosong!");
            txt_iduser.requestFocus();
        } else {
            if(kodeUser.length() >= 6){
                if(pass.equals(konfPass)){
                    cekRoleId();
                } else {
                    JOptionPane.showMessageDialog(null, "Password Tidak Sama!", "Pesan", JOptionPane.ERROR_MESSAGE);
                    txt_confPass.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Panjang Karakter ID Harus 8 Digit");
                txt_iduser.requestFocus();
            }

        } 
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if(txt_iduser.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Id Tidak Boleh Kosong");
            txt_iduser.requestFocus();
        } else {
            hapusData();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void TabelPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelPegawaiKeyPressed

    }//GEN-LAST:event_TabelPegawaiKeyPressed

    private void TabelPegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPegawaiMouseClicked
//        txt_iduser.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 0) + "");
//        txt_name.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 1) + "");
//        txt_pass.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 2) + "");
//        txt_confPass.setText(tb.getValueAt(TabelPegawai.getSelectedRow(), 2) + "");
    }//GEN-LAST:event_TabelPegawaiMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JComboRole;
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
    private javax.swing.JTextField txt_confPass;
    private javax.swing.JTextField txt_iduser;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_pass;
    // End of variables declaration//GEN-END:variables
}
