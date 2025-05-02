/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.toko_buku.ui;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
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

/**
 *
 * @author rifki
 */
public class MaintenanceInventoryBukuFrame extends javax.swing.JFrame {
    Connection conn = null;
    Statement stat;
    ResultSet res;
    DefaultTableModel tb;
    
    InventoryFrame inventoryFrame;
    /**
     * Creates new form JFrameMaintenanceUser
     * @param adminFrame
     */
    public MaintenanceInventoryBukuFrame(InventoryFrame inventoryFrame) {
        this.inventoryFrame = inventoryFrame;
        initComponents();
        koneksiKeMySQL();
        dataToTable();
        setDefaultCloseOperation(0);
        setTitle("Toko Buku - Maintenance Inventory Buku");
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
        TabelInventory.setAutoResizeMode(TabelInventory.AUTO_RESIZE_OFF);
        column = TabelInventory.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = TabelInventory.getColumnModel().getColumn(1);
        column.setPreferredWidth(100);
        column = TabelInventory.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = TabelInventory.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = TabelInventory.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = TabelInventory.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
    }
    
    private void dataToTable() {
        tb = new DefaultTableModel();
        tb.addColumn("Id Inventory");
        tb.addColumn("ISBN");
        tb.addColumn("Kode Rak");
        tb.addColumn("Stock");
        tb.addColumn("Tanggal Update Stock");
        tb.addColumn("Harga Satuan");
        TabelInventory.setModel(tb);

        try {
            this.res = this.stat.executeQuery("SELECT Id_Inventory, ISBN, Kode_Rak, Stock, Tanggal_Update_Stock, Harga_Satuan FROM T_Inventory");
            while (res.next()) {
                tb.addRow(new Object[]{
                    res.getString("Id_Inventory"),
                    res.getString("ISBN"),
                    res.getString("Kode_Rak"),
                    res.getInt("Stock"),
                    res.getTimestamp("Tanggal_Update_Stock"),
                    res.getBigDecimal("Harga_Satuan")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("data ke tabel gagal");
        }
        aturKolom();
    }

    
    private void membersihkanTextField(){
        FieldInputIdInventory.setText("");
        FieldInputISBN.setText("");
        FieldInputJudulBuku.setText("");
        FieldInputHargaSatuan.setText("");
        FieldInputStock.setText("");
        FieldInputkodeRak.setText("");
        FieldInputIdInventory.requestFocus();
    }
    
    private void insertUpdate() {
    try {
        Statement StrCheck = this.conn.createStatement();

        String IdInventory = FieldInputIdInventory.getText().trim();
        String ISBN = FieldInputISBN.getText().trim();
        String KodeRak = FieldInputkodeRak.getText().trim();
        int Stock = Integer.parseInt(FieldInputStock.getText().trim());
        String HargaSatuan = FieldInputHargaSatuan.getText().trim();

        Date Tanggal = new Date();
        SimpleDateFormat FormatTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TanggalSekarang = FormatTanggal.format(Tanggal);

        // Cek apakah data dengan Id_Inventory sudah ada
        String QueryCheck = "SELECT * FROM T_Inventory WHERE Id_Inventory = ?";
        PreparedStatement PsCheck = conn.prepareStatement(QueryCheck);
        PsCheck.setString(1, IdInventory);
        ResultSet RsCheck = PsCheck.executeQuery();

        if (RsCheck.next()) {
            // UPDATE
            String QueryUpdate = "UPDATE T_Inventory SET ISBN = ?, Kode_Rak = ?, Stock = ?, Tanggal_Update_Stock = ?, Harga_Satuan = ? WHERE Id_Inventory = ?";
            PreparedStatement PsUpdate = conn.prepareStatement(QueryUpdate);
            PsUpdate.setString(1, ISBN);
            PsUpdate.setString(2, KodeRak);
            PsUpdate.setInt(3, Stock);
            PsUpdate.setString(4, TanggalSekarang);
            PsUpdate.setBigDecimal(5, new BigDecimal(HargaSatuan));
            PsUpdate.setString(6, IdInventory);
            PsUpdate.executeUpdate();

            dataToTable();
            membersihkanTextField();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate", "Update", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // INSERT
            String QueryInsert = "INSERT INTO T_Inventory (Id_Inventory, ISBN, Kode_Rak, Stock, Tanggal_Update_Stock, Harga_Satuan) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement PsInsert = conn.prepareStatement(QueryInsert);
            PsInsert.setString(1, IdInventory);
            PsInsert.setString(2, ISBN);
            PsInsert.setString(3, KodeRak);
            PsInsert.setInt(4, Stock);
            PsInsert.setString(5, TanggalSekarang);
            PsInsert.setBigDecimal(6, new BigDecimal(HargaSatuan));
            PsInsert.executeUpdate();

            dataToTable();
            membersihkanTextField();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Insert", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException | NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Penambahan atau update gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    private void hapusData() {
        if (JOptionPane.showConfirmDialog(null, "Apakah yakin akan dihapus?", "Informasi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String IdInventory = FieldInputIdInventory.getText().trim();
                String QueryDelete = "DELETE FROM T_Inventory WHERE Id_Inventory = ?";
                PreparedStatement PsDelete = this.conn.prepareStatement(QueryDelete);
                PsDelete.setString(1, IdInventory);
                PsDelete.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                membersihkanTextField();
                dataToTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Hapus data gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data inventory batal dihapus");
            FieldInputIdInventory.requestFocus();
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

        FieldInputHargaSatuan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelInventory = new javax.swing.JTable();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        FieldInputIdInventory = new javax.swing.JTextField();
        FieldInputISBN = new javax.swing.JTextField();
        FieldInputJudulBuku = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        FieldInputStock = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        FieldInputkodeRak = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabelInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Inventory", "ISBN", "Judul Buku", "Kode Rak", "Stock Buku", "Harga Satuan"
            }
        ));
        TabelInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelInventoryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelInventory);

        btn_simpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_simpan.setText("Tambahkan Buku");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_hapus.setText("Hapus Buku");
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
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Maintenance Stock Buku");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("ID Inventory");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ISBN");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Judul Buku");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Harga Satuan");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Stock Buku");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Kode Rak");

        FieldInputkodeRak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldInputkodeRakKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(FieldInputJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(FieldInputIdInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(FieldInputISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(FieldInputHargaSatuan, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(FieldInputkodeRak, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(FieldInputStock, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(1, 1, 1)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldInputIdInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(FieldInputISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FieldInputJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldInputkodeRak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldInputStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FieldInputHargaSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_hapus)
                    .addComponent(btn_keluar))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar?") == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryFrame.setEnabled(true);
            inventoryFrame.requestFocus();
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String IdInventory = FieldInputIdInventory.getText().trim();
        String ISBN = FieldInputISBN.getText().trim();
        String KodeRak = FieldInputkodeRak.getText().trim();
        String HargaSatuan = FieldInputHargaSatuan.getText().trim();
        String StockInputStr = FieldInputStock.getText().trim();

        if (IdInventory.isEmpty() || ISBN.isEmpty() || KodeRak.isEmpty() || StockInputStr.isEmpty() || HargaSatuan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong!");
            FieldInputIdInventory.requestFocus();
            return;
        }

        if (IdInventory.length() != 8) {
            JOptionPane.showMessageDialog(null, "Panjang karakter ID Inventory harus 8 digit");
            FieldInputIdInventory.requestFocus();
            return;
        }
        
        insertUpdate();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if(FieldInputIdInventory.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Id Tidak Boleh Kosong");
            FieldInputIdInventory.requestFocus();
        } else {
            hapusData();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void TabelInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelInventoryMouseClicked
        FieldInputIdInventory.setText(tb.getValueAt(TabelInventory.getSelectedRow(), 0) + "");
        FieldInputISBN.setText(tb.getValueAt(TabelInventory.getSelectedRow(), 1) + "");
        FieldInputJudulBuku.setText(tb.getValueAt(TabelInventory.getSelectedRow(), 2) + "");
        FieldInputHargaSatuan.setText(tb.getValueAt(TabelInventory.getSelectedRow(), 2) + "");
    }//GEN-LAST:event_TabelInventoryMouseClicked

    private void FieldInputkodeRakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldInputkodeRakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputkodeRakKeyPressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FieldInputHargaSatuan;
    private javax.swing.JTextField FieldInputISBN;
    private javax.swing.JTextField FieldInputIdInventory;
    private javax.swing.JTextField FieldInputJudulBuku;
    private javax.swing.JTextField FieldInputStock;
    private javax.swing.JTextField FieldInputkodeRak;
    private javax.swing.JTable TabelInventory;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
