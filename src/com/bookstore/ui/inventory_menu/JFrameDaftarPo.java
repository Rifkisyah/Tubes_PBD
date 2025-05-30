/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.inventory_menu;

import com.bookstore.model.PurchaseOrder;
import com.bookstore.ui.JFrameGudang;
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
public class JFrameDaftarPo extends javax.swing.JFrame {
    JFrameGudang inventoryDashboardFrame;
    private DefaultTableModel model;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form NewJFrame
     */
    public JFrameDaftarPo(JFrameGudang inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Daftar Purchase Order");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(JFrameDaftarPo.this,
                        "Apakah Kamu Yakin Ingin Keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    JFrameDaftarPo.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JFrameDaftarPo.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    JFrameDaftarPo.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
        
        JTexfield_search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(JTexfield_search.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(JTexfield_search.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(JTexfield_search.getText().trim());
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
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        try{
            purchaseOrder.getSemuaPo();
            
            model.setRowCount(0);
            
            while(purchaseOrder.getResultSet().next()){
                Object[] fieldx = new Object[13];
                fieldx[0] = purchaseOrder.getResultSet().getString("id_PO");
                fieldx[1] = purchaseOrder.getResultSet().getString("id_pegawai");
                fieldx[2] = purchaseOrder.getResultSet().getString("nama");
                fieldx[3] = purchaseOrder.getResultSet().getString("id_vendor");
                fieldx[4] = purchaseOrder.getResultSet().getString("nama_vendor");
                fieldx[5] = purchaseOrder.getResultSet().getString("isbn");
                fieldx[6] = purchaseOrder.getResultSet().getString("judul_buku");
                fieldx[7] = purchaseOrder.getResultSet().getString("tanggal_PO");
                fieldx[8] = purchaseOrder.getResultSet().getString("estimasi_tanggal_datang");
                fieldx[9] = purchaseOrder.getResultSet().getInt("jumlah_PO");
                fieldx[10] = purchaseOrder.getResultSet().getInt("jumlah_diterima");
                fieldx[11] = purchaseOrder.getResultSet().getDouble("total_biaya");
                fieldx[12] = purchaseOrder.getResultSet().getString("status_PO");

                this.model.addRow(fieldx);
            }

        } catch (SQLException ex){
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
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataToTable();
            } else {
                purchaseOrder.getPencarianDataTabelPo(search_value);

                if (purchaseOrder.getResultSet().isBeforeFirst()) {
                    while (purchaseOrder.getResultSet().next()) {
                        Object[] fieldx = new Object[13];
                            fieldx[0] = purchaseOrder.getResultSet().getString("id_PO");
                            fieldx[1] = purchaseOrder.getResultSet().getString("id_pegawai");
                            fieldx[2] = purchaseOrder.getResultSet().getString("nama");
                            fieldx[3] = purchaseOrder.getResultSet().getString("id_vendor");
                            fieldx[4] = purchaseOrder.getResultSet().getString("nama_vendor");
                            fieldx[5] = purchaseOrder.getResultSet().getString("isbn");
                            fieldx[6] = purchaseOrder.getResultSet().getString("judul_buku");
                            fieldx[7] = purchaseOrder.getResultSet().getString("tanggal_PO");
                            fieldx[8] = purchaseOrder.getResultSet().getString("estimasi_tanggal_datang");
                            fieldx[9] = purchaseOrder.getResultSet().getInt("jumlah_PO");
                            fieldx[10] = purchaseOrder.getResultSet().getInt("jumlah_diterima");
                            fieldx[11] = purchaseOrder.getResultSet().getDouble("total_biaya");
                            fieldx[12] = purchaseOrder.getResultSet().getString("status_PO");
                        this.model.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Po Tidak Ditemukan!", "informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
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
        JTexfield_search = new javax.swing.JTextField();

        search_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(84, 119, 146));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 370));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Daftar Purchase Order");

        POTabel.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
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

        JTexfield_search.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        JTexfield_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTexfield_searchActionPerformed(evt);
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
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(JTexfield_search)))
                                .addGap(17, 17, 17))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTexfield_search, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1234, Short.MAX_VALUE)
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
        // nothing
    }//GEN-LAST:event_search_fieldActionPerformed

    private void JTexfield_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTexfield_searchActionPerformed
        String search_value = JTexfield_search.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_JTexfield_searchActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTextField JTexfield_search;
    private javax.swing.JTable POTabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField search_field;
    // End of variables declaration//GEN-END:variables
}
