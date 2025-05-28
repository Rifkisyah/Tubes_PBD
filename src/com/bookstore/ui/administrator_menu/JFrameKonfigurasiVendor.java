/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.administrator_menu;

import com.bookstore.model.Vendor;
import com.bookstore.ui.JFrameAdmin;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class JFrameKonfigurasiVendor extends javax.swing.JFrame {
    JFrameAdmin superAdminDashboardFrame;
    DefaultTableModel model;
    /**
     * Creates new form ConfigVendor
     * @param superAdminDashboardFrame
     */
    public JFrameKonfigurasiVendor(JFrameAdmin superAdminDashboardFrame) {
        this.superAdminDashboardFrame = superAdminDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Maintenance Role Pegawai");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(JFrameKonfigurasiVendor.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Aplikasi?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    JFrameKonfigurasiVendor.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JFrameKonfigurasiVendor.this.setVisible(false);
                    superAdminDashboardFrame.setEnabled(true);
                    superAdminDashboardFrame.requestFocus();
                } else {
                    JFrameKonfigurasiVendor.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        mengaturHeaderTabel();
        
        JTextfield_searchVendor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchVendor.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchVendor.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchVendor.getText().trim());
            }
            
        });
    }
    
    public void dataKeTabel(){
        Vendor vendor = new Vendor();

        try{
            vendor.getSemuaDataVendor();

            model.setRowCount(0);
            
            while (vendor.getResultSet().next()) {    
            Object[] fieldx = new Object[4];
                fieldx[0] = vendor.getResultSet().getString("id_vendor");
                fieldx[1] = vendor.getResultSet().getString("Nama_vendor");
                fieldx[2] = vendor.getResultSet().getString("alamat_vendor");
                fieldx[3] = vendor.getResultSet().getString("kontak_vendor");
                model.addRow(fieldx);
            }
        } catch (SQLException  e){
            e.printStackTrace();
        }
    }
    
    private void mengaturukuranKolom(){
        TableColumn column;
        dataVendorTable.setAutoResizeMode(dataVendorTable.AUTO_RESIZE_OFF);
        column = dataVendorTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
        column = dataVendorTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(250);
        column = dataVendorTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(250);
        column = dataVendorTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(250);
    }

    
    private void mengaturHeaderTabel(){
        model = new DefaultTableModel();
        dataVendorTable.setModel(model);
        model.addColumn("Id Vendor");
        model.addColumn("Nama Vendor");
        model.addColumn("Alamat Vendor");
        model.addColumn("Kontak vendor");
        mengaturukuranKolom();
        dataKeTabel();
    }
    
       private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        Vendor vendor = new Vendor();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataKeTabel();
            } else {
                vendor.getPencarianDataTabel(search_value);

                if (vendor.getResultSet().isBeforeFirst()) {
                    while (vendor.getResultSet().next()) {
                    Object[] fieldx = new Object[4];
                        fieldx[0] = vendor.getResultSet().getString("id_vendor");
                        fieldx[1] = vendor.getResultSet().getString("nama_vendor");
                        fieldx[2] = vendor.getResultSet().getString("alamat_vendor");
                        fieldx[3] = vendor.getResultSet().getString("kontak_vendor");
                        model.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vendor Tidak Ditemukan!", "Gagal Mencari Vendor", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
       
    public String generateIdVendor(){
        Vendor vendor = new Vendor();
        String prefix = "V";
        int counter = 1;
        String newId;

        while (true) {
            newId = prefix + String.format("%03d", counter);
            if (!vendor.cekIdVendorSudahAda(newId)) {
                break;
            }
            counter++;
        }
        return newId;
    }
    
    private void resetDataInput(){
        JTextfield_idVendor.setText("");
        JTextfield_namaVendor.setText("");
        JTextfield_kontakVendor.setText("");
        JTextefield_alamatvendor.setText("");
        JTextfield_searchVendor.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataVendorTable = new javax.swing.JTable();
        JTextfield_searchVendor = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        JTextfield_idVendor = new javax.swing.JTextField();
        JButton_generateIdVendor = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        JTextfield_namaVendor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        JTextefield_alamatvendor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        JTextfield_kontakVendor = new javax.swing.JTextField();
        JButton_addNewVendor = new javax.swing.JButton();
        JButton_deleteVendor = new javax.swing.JButton();
        JButton_close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(148, 180, 193));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Vendor");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cari Vendor :");

        dataVendorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Vendor", "Nama Vendor", "Alamat Vendor", "Kontak Vendor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        dataVendorTable.setShowGrid(true);
        dataVendorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataVendorTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dataVendorTable);

        JTextfield_searchVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_searchVendorActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID Vendor");

        JTextfield_idVendor.setEditable(false);
        JTextfield_idVendor.setEnabled(false);
        JTextfield_idVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_idVendorActionPerformed(evt);
            }
        });

        JButton_generateIdVendor.setText("Generate ID");
        JButton_generateIdVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_generateIdVendorActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Vendor");

        JTextfield_namaVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_namaVendorActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Alamat Vendor");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Kontak Vendor");

        JTextfield_kontakVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_kontakVendorActionPerformed(evt);
            }
        });

        JButton_addNewVendor.setBackground(new java.awt.Color(0, 153, 0));
        JButton_addNewVendor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_addNewVendor.setForeground(new java.awt.Color(255, 255, 255));
        JButton_addNewVendor.setText("Tambahkan");
        JButton_addNewVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_addNewVendorActionPerformed(evt);
            }
        });

        JButton_deleteVendor.setBackground(new java.awt.Color(153, 0, 51));
        JButton_deleteVendor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_deleteVendor.setForeground(new java.awt.Color(255, 255, 255));
        JButton_deleteVendor.setText("Hapus");
        JButton_deleteVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_deleteVendorActionPerformed(evt);
            }
        });

        JButton_close.setBackground(new java.awt.Color(33, 52, 72));
        JButton_close.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_close.setForeground(new java.awt.Color(255, 255, 255));
        JButton_close.setText("Keluar");
        JButton_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(JButton_addNewVendor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JButton_deleteVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(JButton_close)
                                .addGap(28, 28, 28))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(JTextfield_idVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JButton_generateIdVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JTextfield_namaVendor)
                            .addComponent(JTextefield_alamatvendor)
                            .addComponent(JTextfield_kontakVendor))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_idVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JButton_generateIdVendor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_namaVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextefield_alamatvendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_kontakVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_deleteVendor)
                    .addComponent(JButton_addNewVendor)
                    .addComponent(JButton_close))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTextfield_searchVendor))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JTextfield_searchVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dataVendorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataVendorTableMouseClicked
        JTextfield_idVendor.setText(model.getValueAt(dataVendorTable.getSelectedRow(), 0) + "");
        JTextfield_namaVendor.setText(model.getValueAt(dataVendorTable.getSelectedRow(), 1) + "");
        JTextefield_alamatvendor.setText(model.getValueAt(dataVendorTable.getSelectedRow(), 2) + "");
        JTextfield_kontakVendor.setText(model.getValueAt(dataVendorTable.getSelectedRow(), 3) + "");
    }//GEN-LAST:event_dataVendorTableMouseClicked

    private void JButton_addNewVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_addNewVendorActionPerformed
        String vendorId = JTextfield_idVendor.getText().trim();
        String vendorName = JTextfield_namaVendor.getText().trim();
        String vendorContact = JTextfield_kontakVendor.getText().trim();
        String vendorAddress = JTextefield_alamatvendor.getText().trim();
        
        Vendor vendor = new Vendor();
        
        if (vendorId.equals("")) {
            JOptionPane.showMessageDialog(this, "id vendor masih kosong!");
            JTextfield_idVendor.requestFocus();
            return;
        }
        
        vendor.getJumlahDataBerdasarkanIdVendor(vendorId);
        if (vendor.getAffectedRow() > 0) {
            if(JOptionPane.showConfirmDialog(this, "Data Vendor Sudah Ada! Apakah Kamu mau Uodate Data ini?", "info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                
                vendor.updateVendor(vendorId, vendorName, vendorAddress, vendorContact);
                
                if (vendor.getAffectedRow() > 0) {
                    resetDataInput();
                    
                    dataKeTabel();
                    JOptionPane.showMessageDialog(this, "Data Vendor Berhasil Diperbarui");
                } else {
                    JOptionPane.showMessageDialog(this, "Data Vendor Gagal Diperbarui");
                }
            }
        } else {
            vendor.memasukanVendorBaru(vendorId, vendorName, vendorAddress, vendorContact);
            
            if (vendor.getAffectedRow() > 0) {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();
                resetDataInput();
                dataKeTabel();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
            }
        }
    }//GEN-LAST:event_JButton_addNewVendorActionPerformed

    private void JButton_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_closeActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            superAdminDashboardFrame.setEnabled(true);
            superAdminDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_JButton_closeActionPerformed

    private void JButton_deleteVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_deleteVendorActionPerformed
        int deleting = JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Menghapus Data Ini?", "Hapus Data Vendor", JOptionPane.YES_NO_OPTION);
        
        if(deleting == JOptionPane.YES_OPTION){
            String idVendor = JTextfield_idVendor.getText().trim();
            String namaVendor = JTextfield_namaVendor.getText().trim();
            String alamatVendor = JTextefield_alamatvendor.getText().trim();
            String kontakVendor = JTextfield_kontakVendor.getText().trim();

            Vendor vendor = new Vendor();
            
            if(idVendor.isEmpty()){
                JOptionPane.showMessageDialog(this, "ID Vendor Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
                JTextfield_idVendor.requestFocus();
            } else if(namaVendor.isEmpty()){
                JOptionPane.showMessageDialog(this, "Nama Vendor Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
                JTextfield_namaVendor.requestFocus();
            } else if(alamatVendor.isEmpty()){
                JOptionPane.showMessageDialog(this, "Alamat Vendor Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
                JTextefield_alamatvendor.requestFocus();
            } else if(kontakVendor.isEmpty()){
                JOptionPane.showMessageDialog(this, "Kontak Vendor Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
                JTextfield_kontakVendor.requestFocus();
            } else {
                vendor.getJumlahDataBerdasarkanIdVendor(idVendor);

                if(vendor.getAffectedRow() > 0){
                    vendor.deleteDataBerdasarkanIdVendor(idVendor);

                    if (vendor.getAffectedRow() > 0) {
                        model.getDataVector().removeAllElements();
                        model.fireTableDataChanged();
                        resetDataInput();
                        dataKeTabel();
                        JOptionPane.showMessageDialog(this, "Data Vendor Berhasil Dihapus", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Vendor Tidak Ditemukan!", "informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_JButton_deleteVendorActionPerformed

    private void JTextfield_searchVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_searchVendorActionPerformed
        String searchValue = JTextfield_searchVendor.getText().trim();
        performSearch(searchValue);
    }//GEN-LAST:event_JTextfield_searchVendorActionPerformed

    private void JTextfield_idVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_idVendorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_idVendorActionPerformed

    private void JTextfield_namaVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_namaVendorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_namaVendorActionPerformed

    private void JButton_generateIdVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_generateIdVendorActionPerformed
        JTextfield_idVendor.setText(generateIdVendor());
    }//GEN-LAST:event_JButton_generateIdVendorActionPerformed

    private void JTextfield_kontakVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_kontakVendorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_kontakVendorActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_addNewVendor;
    private javax.swing.JButton JButton_close;
    private javax.swing.JButton JButton_deleteVendor;
    private javax.swing.JButton JButton_generateIdVendor;
    private javax.swing.JTextField JTextefield_alamatvendor;
    private javax.swing.JTextField JTextfield_idVendor;
    private javax.swing.JTextField JTextfield_kontakVendor;
    private javax.swing.JTextField JTextfield_namaVendor;
    private javax.swing.JTextField JTextfield_searchVendor;
    private javax.swing.JTable dataVendorTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
