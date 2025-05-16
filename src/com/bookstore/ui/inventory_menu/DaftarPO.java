/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.inventory_menu;

import com.bookstore.data.QuerySelector;
import com.bookstore.ui.InventoryDashboardFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;
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
public class DaftarPO extends javax.swing.JFrame {
    InventoryDashboardFrame inventoryDashboardFrame;
    private DefaultTableModel model;
    QuerySelector querySelector;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form NewJFrame
     */
    public DaftarPO(InventoryDashboardFrame inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Daftar Purchase Order");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        DaftarPO.this,
                        "Apakah Kamu Yakin Ingin Keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    DaftarPO.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    DaftarPO.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
        dataToComboBox();
        
        search_field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(search_field.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(search_field.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(search_field.getText().trim());
            }
            
        });
    }
    
        private void columnSizing(){
        TableColumn column;
        POTabel.setAutoResizeMode(POTabel.AUTO_RESIZE_OFF);
        
        column = POTabel.getColumnModel().getColumn(0); //nota
        column.setPreferredWidth(150);
        column = POTabel.getColumnModel().getColumn(1); //id pegawai
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(2); // nama pegawai
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(3); // isbn
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(4); // judul buku
        column.setPreferredWidth(250);
        column = POTabel.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(150);
        column = POTabel.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(250);
        column = POTabel.getColumnModel().getColumn(7); // tanggal PO
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(8); // Estimasi Tiba
        column.setPreferredWidth(100);
        column = POTabel.getColumnModel().getColumn(9); // Estimasi Tiba
        column.setPreferredWidth(100);
        column = POTabel.getColumnModel().getColumn(10); // Jumlah PO
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(11); // Total Biaya
        column.setPreferredWidth(120);
    }
    
    private void dataToTable(){
        this.querySelector = new QuerySelector();

        try{
            querySelector.selectAllPurchaseOrder();            
            
            while(querySelector.getResultSet().next()){
                Object[] fieldx = new Object[13];
                fieldx[0] = querySelector.getResultSet().getString("id_PO");
                fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                fieldx[2] = querySelector.getResultSet().getString("nama_pegawai");
                fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                fieldx[5] = querySelector.getResultSet().getString("isbn");
                fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                fieldx[8] = querySelector.getResultSet().getString("estimasi_tanggal_datang");
                fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                fieldx[10] = querySelector.getResultSet().getInt("jumlah_diterima");
                fieldx[11] = querySelector.getResultSet().getDouble("total_biaya");
                fieldx[12] = querySelector.getResultSet().getString("status_PO");

                this.model.addRow(fieldx);
            }

        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private void dataToComboBox() {
        try {
            querySelector.selectAllFromTable("t_purchaseorder");

            // Simpan item index ke-0
            Object firstItem = null;
            if (filterSearch.getItemCount() > 0) {
                firstItem = filterSearch.getItemAt(0);
            }

            // Hapus semua item, lalu tambahkan kembali item index 0
            filterSearch.removeAllItems();
            if (firstItem != null) {
                filterSearch.addItem(firstItem.toString()); // tambah kembali item 0
            }

            // Tambahkan data dari database
            while (querySelector.getResultSet().next()) {
                String status = querySelector.getResultSet().getString("status_po");

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(status)) {
                    filterSearch.addItem(status);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setHeaderTable(){
        this.model = new DefaultTableModel();
        POTabel.setModel(model);

        model.addColumn("No. PO");
        model.addColumn("ID Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("ID Vendor");
        model.addColumn("Nama Vendor");
        model.addColumn("ISBN");
        model.addColumn("Judul Buku");
        model.addColumn("Tanggal PO");
        model.addColumn("Estimasi Tiba");
        model.addColumn("Jumlah PO");
        model.addColumn("Jumlah Diterima");
        model.addColumn("Total Biaya");
        model.addColumn("Status PO");

        columnSizing();
        dataToTable();
    }

    private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataToTable();
            } else {
                String statusName = (String) filterSearch.getSelectedItem();
                
                if(statusName.equals("-- Pilih Filter Status PO --")){
                    querySelector.selectLikeWithJoinPurchaseOrder("id_po", "judul_buku", search_value);
                } else {
                    querySelector.selectAllByColumnsLike("T_Purchaseorder", "status_po", statusName);
                }

                if (querySelector.getResultSet().isBeforeFirst()) {
                    while (querySelector.getResultSet().next()) {
                        Object[] fieldx = new Object[12];
                        fieldx[0] = querySelector.getResultSet().getString("id_PO");
                        fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                        fieldx[2] = querySelector.getResultSet().getString("nama_pegawai");
                        fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                        fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                        fieldx[5] = querySelector.getResultSet().getString("isbn");
                        fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                        fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                        fieldx[8] = querySelector.getResultSet().getString("estimasi_tanggal_datang");
                        fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                        fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
                        fieldx[11] = querySelector.getResultSet().getString("status_PO");
                        this.model.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pegawai Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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

        search_field = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        POTabel = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search_field1 = new javax.swing.JTextField();
        filterSearch = new javax.swing.JComboBox<>();

        search_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(84, 119, 146));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 3, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 370));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Daftar Purchase Order");

        POTabel.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        POTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID PO", "ID Pegawai", "Nama Pegawai", "ISBN", "Judul Buku", "ID Vendor", "Nama Vendor", "Tanggal PO", "Estimasi Tiba", "Jumlah PO", "Jumlah Diterima", "Total Biaya", "Status PO"
            }
        ));
        POTabel.setAlignmentY(1.0F);
        jScrollPane1.setViewportView(POTabel);

        CloseButton.setBackground(new java.awt.Color(33, 52, 72));
        CloseButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CloseButton.setForeground(new java.awt.Color(255, 255, 255));
        CloseButton.setText("Keluar");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Cari PO :");

        search_field1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_field1ActionPerformed(evt);
            }
        });

        filterSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Filter Status PO --" }));
        filterSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(search_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(filterSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(17, 17, 17)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filterSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CloseButton)
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void search_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_fieldActionPerformed
        String search_value = search_field.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_search_fieldActionPerformed

    private void search_field1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_field1ActionPerformed
        String search_value = search_field.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_search_field1ActionPerformed

    private void filterSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterSearchActionPerformed
        if(this.filterComboBoxIntialize){
            String filterVendorName = (String) filterSearch.getSelectedItem();
            search_field.setText("");

            if (filterVendorName == null || filterVendorName.equals("-- Pilih Filter Vendor --")) {
                dataToTable();
                return;
            } else {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                try {
                    if (filterVendorName.trim().isEmpty()) {
                        performSearch(filterVendorName);
                    } else {
                        querySelector.selectDetailMasterBukuByVendorFilter(filterVendorName, "vendor");

                        if (querySelector.getResultSet().isBeforeFirst()) {
                            while (querySelector.getResultSet().next()) {
                                Object[] fieldx = new Object[12];
                                fieldx[0] = querySelector.getResultSet().getString("id_PO");
                                fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                                fieldx[2] = querySelector.getResultSet().getString("nama_pegawai");
                                fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                                fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                                fieldx[5] = querySelector.getResultSet().getString("isbn");
                                fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                                fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                                fieldx[8] = querySelector.getResultSet().getString("estimasi_tanggal_datang");
                                fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                                fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
                                fieldx[11] = querySelector.getResultSet().getString("status_PO");
                                this.model.addRow(fieldx);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_filterSearchActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTable POTabel;
    private javax.swing.JComboBox<String> filterSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField search_field;
    private javax.swing.JTextField search_field1;
    // End of variables declaration//GEN-END:variables
}
