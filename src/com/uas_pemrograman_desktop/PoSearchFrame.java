/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.uas_pemrograman_desktop;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class PoSearchFrame extends javax.swing.JFrame {
    private DefaultTableModel dtm;
    private ReceivedPurchaseOrderFrame rpo;
    /**
     * Creates new form PoSearchFrame
     * @param rpo
     */
    public PoSearchFrame(ReceivedPurchaseOrderFrame rpo) {
        this.rpo = rpo;
        
        initComponents();
        
        setHeaderTable();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(PoSearchFrame.this,
                        "Apakah Yakin Ingin Keluar Dari Menu Pencarian?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    PoSearchFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    PoSearchFrame.this.setVisible(false);
                    rpo.setEnabled(true);
                    rpo.requestFocus();
                } else {
                    PoSearchFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
    
    private void setColSize(){
        TableColumn column;
        jTablePo.setAutoResizeMode(jTablePo.AUTO_RESIZE_OFF);
        column = jTablePo.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(1);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(2);
        column.setPreferredWidth(250);
        column = jTablePo.getColumnModel().getColumn(3);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(4);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(5);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(6);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(7);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(8);
        column.setPreferredWidth(100);
        column = jTablePo.getColumnModel().getColumn(9);
        column.setPreferredWidth(150);
        column = jTablePo.getColumnModel().getColumn(10);
        column.setPreferredWidth(250);
        column = jTablePo.getColumnModel().getColumn(11);
        column.setPreferredWidth(250);
        column = jTablePo.getColumnModel().getColumn(12);
        column.setPreferredWidth(200);
    }
    
    private void setDataTable(){
        try{
            DbConnection dbc = new DbConnection();
            
            String querySelect = "SELECT a.*, b.*, c.namaproduk FROM tpurchasingorder a JOIN tsupplier b ON a.kodesupplier = b.kodesupplier JOIN tmasterid c ON a.ItemID = c.ItemID WHERE a.flag = 0"; 
            PreparedStatement pstmt = dbc.getConnection().prepareStatement(querySelect);
            ResultSet rslt = pstmt.executeQuery();
                    
            dtm.setRowCount(0);
            
            while (rslt.next()) {    
                Object[] fieldx = new Object[13];
                fieldx[0] = rslt.getString("a.nopo");
                fieldx[1] = rslt.getString("a.itemid");
                fieldx[2] = rslt.getString("c.namaproduk");
                fieldx[3] = rslt.getString("a.price");
                fieldx[4] = rslt.getString("a.disc");
                fieldx[5] = rslt.getString("a.costprice");
                fieldx[6] = rslt.getString("a.qtypo");
                fieldx[7] = rslt.getString("a.qtytrm");
                fieldx[8] = rslt.getString("a.flag");
                fieldx[9] = rslt.getString("b.kodesupplier");
                fieldx[10] = rslt.getString("b.namasupplier");
                fieldx[11] = rslt.getString("b.Alamat");
                fieldx[12] = rslt.getString("b.pic");
                dtm.addRow(fieldx);
            }
        } catch (SQLException  e){
            e.printStackTrace();
        }
    }
    
    private void setHeaderTable(){
        this.dtm = new DefaultTableModel();
        jTablePo.setModel(dtm);
        dtm.addColumn("No Po");
        dtm.addColumn("ID Item");
        dtm.addColumn("Nama Produk");
        dtm.addColumn("Price");
        dtm.addColumn("Diskon");
        dtm.addColumn("Cost Price");
        dtm.addColumn("Qty Po");
        dtm.addColumn("Qty Terima");
        dtm.addColumn("Flag");
        dtm.addColumn("Kode Supplier");
        dtm.addColumn("Nama Supplier");
        dtm.addColumn("Alamat Supplier");
        dtm.addColumn("PIC Supplier");
        
        setColSize();
        setDataTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldPencarianNamaProduk = new javax.swing.JTextField();
        jTextFieldNoPo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(57, 62, 70));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Pencarian Purchasing Order");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cari Nama Produk  :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("No Po");

        jTextFieldPencarianNamaProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPencarianNamaProdukActionPerformed(evt);
            }
        });

        jTextFieldNoPo.setEditable(false);
        jTextFieldNoPo.setEnabled(false);

        jTablePo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Po", "Item ID", "Nama Produk", "Price", "Diskon", "CostPrice", "QtyPo", "QtyTrm", "Flag", "Kode Supplier", "Nama Supplier", "Alamat Supplier", "PIC"
            }
        ));
        jTablePo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldPencarianNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldNoPo)))
                        .addGap(15, 15, 15))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldPencarianNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPencarianNamaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPencarianNamaProdukActionPerformed
        try {
            String namaProduk = jTextFieldPencarianNamaProduk.getText().trim();  // Input dari user
            DbConnection dbc = new DbConnection();

            dtm.setRowCount(0); // Bersihkan isi tabel

            if (namaProduk.isEmpty()) {
                setDataTable();  // Tampilkan semua jika kosong
            } else {
                String query = "SELECT a.nopo, a.itemid, a.price, a.disc, a.costprice, a.qtypo, a.qtytrm, a.flag, " +
                               "b.kodesupplier, b.namasupplier, b.alamat, b.pic, c.namaproduk " +
                               "FROM tpurchasingorder a " +
                               "JOIN tsupplier b ON a.kodesupplier = b.kodesupplier " +
                               "JOIN tmasterid c ON a.itemid = c.itemid " +
                               "WHERE a.itemid LIKE ? OR c.namaproduk LIKE ? " +
                               "ORDER BY b.kodesupplier ASC";

                PreparedStatement pstmt = dbc.getConnection().prepareStatement(query);
                pstmt.setString(1, "%" + namaProduk + "%"); // Bisa cocokkan ItemID
                pstmt.setString(2, "%" + namaProduk + "%"); // Atau NamaProduk

                ResultSet rslt = pstmt.executeQuery();

                if (rslt.isBeforeFirst()) {
                    while (rslt.next()) {    
                        Object[] fieldx = new Object[13];
                        fieldx[0] = rslt.getString("nopo");
                        fieldx[1] = rslt.getString("itemid");
                        fieldx[2] = rslt.getString("namaproduk");
                        fieldx[3] = rslt.getString("price");
                        fieldx[4] = rslt.getString("disc");
                        fieldx[5] = rslt.getString("costprice");
                        fieldx[6] = rslt.getString("qtypo");
                        fieldx[7] = rslt.getString("qtytrm");
                        fieldx[8] = rslt.getString("flag");
                        fieldx[9] = rslt.getString("kodesupplier");
                        fieldx[10] = rslt.getString("namasupplier");
                        fieldx[11] = rslt.getString("alamat");
                        fieldx[12] = rslt.getString("pic");
                        dtm.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Produk tidak ditemukan!", "Pencarian", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jTextFieldPencarianNamaProdukActionPerformed

    private void jTablePoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePoMouseClicked
        jTextFieldNoPo.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 0) + "");
        
        this.rpo.setNoPo(dtm.getValueAt(jTablePo.getSelectedRow(), 0) + "");
        this.rpo.setIdProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 1) + "");
        this.rpo.setNamaProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 2) + "");
        this.rpo.setHargaProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 3) + "");
        this.rpo.setDiskonProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 4) + "");
        this.rpo.setCostPriceProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 5) + "");
        this.rpo.setQtyPoProduk(dtm.getValueAt(jTablePo.getSelectedRow(), 6) + "");
        this.rpo.setKodeSupplier(dtm.getValueAt(jTablePo.getSelectedRow(), 9) + "");
        this.rpo.setNamaSupplier(dtm.getValueAt(jTablePo.getSelectedRow(), 10) + "");
        this.rpo.setAlamatSupplier(dtm.getValueAt(jTablePo.getSelectedRow(), 11) + "");
        this.rpo.setPicSupplier(dtm.getValueAt(jTablePo.getSelectedRow(), 12) + "");
    }//GEN-LAST:event_jTablePoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePo;
    private javax.swing.JTextField jTextFieldNoPo;
    private javax.swing.JTextField jTextFieldPencarianNamaProduk;
    // End of variables declaration//GEN-END:variables
}
