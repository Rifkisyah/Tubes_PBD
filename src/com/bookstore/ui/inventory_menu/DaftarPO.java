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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class DaftarPO extends javax.swing.JFrame {
    InventoryDashboardFrame inventoryDashboardFrame;
    private DefaultTableModel tableModel;
    QuerySelector querySelector;
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
        column = POTabel.getColumnModel().getColumn(9); // Jumlah PO
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(10); // Total Biaya
        column.setPreferredWidth(120);
    }
    
    private void getDataTable(){
        this.querySelector = new QuerySelector();

        try{
            querySelector.selectAllPurchaseOrder();            
            
            while(querySelector.getResultSet().next()){
                Object[] fieldx = new Object[12];
                fieldx[0] = querySelector.getResultSet().getString("nota_PO");
                fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                fieldx[2] = querySelector.getResultSet().getString("nama_pegawai");
                fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                fieldx[4] = querySelector.getResultSet().getString("Nama_Vendor");
                fieldx[5] = querySelector.getResultSet().getString("isbn");
                fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                fieldx[8] = querySelector.getResultSet().getString("estimasi_tanggal_datang");
                fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
                fieldx[11] = querySelector.getResultSet().getString("status_PO");

                this.tableModel.addRow(fieldx);
            }

        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    
    private void setHeaderTable(){
        this.tableModel = new DefaultTableModel();
        POTabel.setModel(tableModel);

        tableModel.addColumn("No. PO");
        tableModel.addColumn("ID Pegawai");
        tableModel.addColumn("Nama Pegawai");
        tableModel.addColumn("ID Vendor");
        tableModel.addColumn("Nama Vendor");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Judul Buku");
        tableModel.addColumn("Tanggal PO");
        tableModel.addColumn("Estimasi Tiba");
        tableModel.addColumn("Jumlah PO");
        tableModel.addColumn("Total Biaya");
        tableModel.addColumn("Status PO");

        columnSizing();
        getDataTable();
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        POTabel = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();

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
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No PO", "ID Pegawai", "Nama Pegawai", "ISBN", "Judul Buku", "ID Vendor", "Nama Vendor", "Tanggal PO", "Estimasi Tiba", "Jumlah PO", "Total Biaya", "Status PO"
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CloseButton)
                .addGap(131, 131, 131))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTable POTabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
