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
public class ReceivedPurchaseOrderFrame extends javax.swing.JFrame {
    private DefaultTableModel dtm;
    private MenuUtamaFrame muf;
    /**
     * Creates new form ReceivedPurchaseOrderFrame
     */
    public ReceivedPurchaseOrderFrame(MenuUtamaFrame muf) {
        this.muf = muf;
        
        initComponents();
        
        setHeaderTable();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ReceivedPurchaseOrderFrame.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Penerimaan PO?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    ReceivedPurchaseOrderFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ReceivedPurchaseOrderFrame.this.setVisible(false);
                    muf.setVisible(true);
                    muf.requestFocus();
                } else {
                    ReceivedPurchaseOrderFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
            
            String querySelect = "SELECT a.*, b.*, c.namaproduk FROM tpurchasingorder a JOIN tsupplier b ON a.kodesupplier = b.kodesupplier JOIN tmasterid c ON a.ItemID = c.ItemID WHERE a.flag = 1"; 
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
    
    private void clearInput(){
        jTextFieldNoPo.setText("");
        jTextFieldNamaProduk.setText("");
        jTextFieldAlamatSupplier.setText("");
        jTextFieldCostPrice.setText("");
        jTextFieldIdProduct.setText("");
        jTextFieldHargaProduk.setText("");
        jTextFieldDiskon.setText("");
        jTextFieldQtyPo.setText("");
        jTextFieldNamaSupplier.setText("");
        jTextFieldAlamatSupplier.setText("");
        jTextFieldPICSupplier.setText("");
        jTextFieldQtyTrm.setText("");
    }
    
    
    public void setNoPo(String noPo){
        jTextFieldNoPo.setText(noPo);
    }
    // supplier
    public void setKodeSupplier(String kodeSupplier) {
        jTextFieldKodeSupplier.setText(kodeSupplier);
    }

    public void setNamaSupplier(String namaSupplier) {
        jTextFieldNamaSupplier.setText(namaSupplier);
    }

    public void setAlamatSupplier(String alamatSupplier) {
        jTextFieldAlamatSupplier.setText(alamatSupplier);
    }

    public void setPicSupplier(String picSupplier) {
        jTextFieldPICSupplier.setText(picSupplier);
    }
    
    // produk
    public void setIdProduk(String idProduk){
        jTextFieldIdProduct.setText(idProduk);
    }
    
    public void setNamaProduk(String namaProduk){
        jTextFieldNamaProduk.setText(namaProduk);
    }
    
    public void setHargaProduk(String hargaProduk){
        jTextFieldHargaProduk.setText(hargaProduk);
    }
    
    public void setDiskonProduk(String diskonProduk){
        jTextFieldDiskon.setText(diskonProduk);
    }
    
    public void setCostPriceProduk(String costPrice){
        jTextFieldCostPrice.setText(costPrice);
    }
    
    public void setQtyPoProduk(String qtypo){
        jTextFieldQtyPo.setText(qtypo);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldNoPo = new javax.swing.JTextField();
        jTextFieldIdProduct = new javax.swing.JTextField();
        jTextFieldNamaProduk = new javax.swing.JTextField();
        jTextFieldHargaProduk = new javax.swing.JTextField();
        jTextFieldDiskon = new javax.swing.JTextField();
        jTextFieldCostPrice = new javax.swing.JTextField();
        jTextFieldQtyPo = new javax.swing.JTextField();
        jButtonSearchProduct = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldQtyTrm = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonTerima = new javax.swing.JButton();
        jButtonKeluar = new javax.swing.JButton();
        jButtonResetInput = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldAlamatSupplier = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldKodeSupplier = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNamaSupplier = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldPICSupplier = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(34, 40, 49));

        jPanel2.setBackground(new java.awt.Color(57, 62, 70));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No PO");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Produk");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Produk");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Harga");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Diskon");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Qty Pesanan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("CostPrice");

        jTextFieldNoPo.setEditable(false);
        jTextFieldNoPo.setEnabled(false);

        jTextFieldIdProduct.setEditable(false);
        jTextFieldIdProduct.setEnabled(false);

        jTextFieldNamaProduk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextFieldDiskon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButtonSearchProduct.setText("Cari Po");
        jButtonSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchProductActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("%");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Qty Terima");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addGap(31, 31, 31)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldHargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldQtyPo, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDiskon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCostPrice, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldQtyTrm, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldNamaProduk, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldNoPo, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                                    .addComponent(jTextFieldIdProduct))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearchProduct))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(jTextFieldNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldHargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldCostPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldQtyPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldQtyTrm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

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

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Terima Purchasing Order");

        jButtonTerima.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonTerima.setText("Terima");
        jButtonTerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTerimaActionPerformed(evt);
            }
        });

        jButtonKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonKeluar.setText("Keluar");
        jButtonKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKeluarActionPerformed(evt);
            }
        });

        jButtonResetInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonResetInput.setText("Reset");
        jButtonResetInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetInputActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(57, 62, 70));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kode Supplier");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama Supplier");

        jTextFieldKodeSupplier.setEditable(false);
        jTextFieldKodeSupplier.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Alamat Supplier");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("PIC Supplier");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAlamatSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(jTextFieldNamaSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPICSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldKodeSupplier, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAlamatSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPICSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonKeluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonTerima, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTerima)
                    .addComponent(jButtonKeluar)
                    .addComponent(jButtonResetInput))
                .addContainerGap(32, Short.MAX_VALUE))
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

    private void jButtonSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchProductActionPerformed
        PoSearchFrame psf = new PoSearchFrame(this);
        psf.setVisible(true);
        psf.requestFocus();
    }//GEN-LAST:event_jButtonSearchProductActionPerformed

    private void jTablePoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePoMouseClicked
        jTextFieldNoPo.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 0) + "");
        jTextFieldIdProduct.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 1) + "");
        jTextFieldNamaProduk.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 2) + "");
        jTextFieldHargaProduk.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 3) + "");
        jTextFieldDiskon.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 4) + "");
        jTextFieldCostPrice.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 5) + "");
        jTextFieldQtyPo.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 6) + "");
        jTextFieldKodeSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 9) + "");
        jTextFieldNamaSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 10) + "");
        jTextFieldAlamatSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 11) + "");
        jTextFieldPICSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 12) + "");

    }//GEN-LAST:event_jTablePoMouseClicked

    private void jButtonTerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTerimaActionPerformed
                                         
    try {
        DbConnection dbc = new DbConnection();

        String noPo = jTextFieldNoPo.getText().trim();
        String idProduk = jTextFieldIdProduct.getText().trim();
        String qtyTrmText = jTextFieldQtyTrm.getText().trim();

        if (noPo.equals("")) {
            JOptionPane.showMessageDialog(this, "Field Nomor PO Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextFieldNoPo.requestFocus();
            return;
        }
        if (idProduk.equals("")) {
            JOptionPane.showMessageDialog(this, "Field ID Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextFieldIdProduct.requestFocus();
            return;
        }
        if (qtyTrmText.equals("")) {
            JOptionPane.showMessageDialog(this, "Field Quantity Terima Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextFieldQtyTrm.requestFocus();
            return;
        }

        int qtyDiterimaInput;
        try {
            qtyDiterimaInput = Integer.parseInt(qtyTrmText);
            if (qtyDiterimaInput <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah yang diterima harus lebih dari 0!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldQtyTrm.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format jumlah terima tidak valid!", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextFieldQtyTrm.requestFocus();
            return;
        }

        // Ambil data PO (QtyPo, QtyTrm, flag) berdasarkan noPo dan idProduk
        String queryCheckPo = "SELECT QtyPo, QtyTrm, flag FROM tpurchasingorder WHERE NoPo = ? AND ItemID = ?";
        PreparedStatement pstmt = dbc.getConnection().prepareStatement(queryCheckPo);
        pstmt.setString(1, noPo);
        pstmt.setString(2, idProduk);
        ResultSet rslt = pstmt.executeQuery();

        if (rslt.next()) {
            int qtyPoDb = rslt.getInt("QtyPo");
            int qtyTrmDb = rslt.getInt("QtyTrm");
            int flagDb = rslt.getInt("flag");

            int newQtyTrm = qtyTrmDb + qtyDiterimaInput;

            if (newQtyTrm > qtyPoDb) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah terima melebihi jumlah PO (" + qtyPoDb + ").\n" + 
                    "Saat ini sudah diterima sebagian sebanyak " + qtyTrmDb + ".\n" + 
                    "Mohon masukkan jumlah terima yang sesuai.", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldQtyTrm.requestFocus();
                return;
            }

            int newFlag = (newQtyTrm >= qtyPoDb) ? 1 : 0;

            String queryUpdate = "UPDATE tpurchasingorder SET QtyTrm = ?, flag = ? WHERE NoPo = ? AND ItemID = ?";
            pstmt = dbc.getConnection().prepareStatement(queryUpdate);
            pstmt.setInt(1, newQtyTrm);
            pstmt.setInt(2, newFlag);
            pstmt.setString(3, noPo);
            pstmt.setString(4, idProduk);

            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                if (newFlag == 1) {
                    // Tambahkan pesan qty terima sebelumnya (sebelum update)
                    JOptionPane.showMessageDialog(this, 
                        "PO sudah diterima penuh.\n" +
                        "Jumlah yang sudah diterima sebelumnya: " + qtyTrmDb + "\n" +
                        "Jumlah yang diterima saat ini: " + qtyDiterimaInput + "\n" +
                        "Total diterima: " + newQtyTrm,
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "PO sudah diterima sebagian, masih kurang " + (qtyPoDb - newQtyTrm) + " item.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
                setDataTable();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui data PO.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "PO dengan nomor " + noPo + " dan produk " + idProduk + " tidak ditemukan.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat akses database.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_jButtonTerimaActionPerformed

    private void jButtonKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKeluarActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar Dari Penerimaan PO?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            this.muf.setVisible(true);
            this.muf.requestFocus();
        }
    }//GEN-LAST:event_jButtonKeluarActionPerformed

    private void jButtonResetInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetInputActionPerformed
        clearInput();
        jTextFieldNamaProduk.setEnabled(true);
        jTextFieldHargaProduk.setEnabled(true);
        jTextFieldDiskon.setEnabled(true);
        jTextFieldCostPrice.setEnabled(true);
        jTextFieldNamaSupplier.setEnabled(true);
        jTextFieldAlamatSupplier.setEnabled(true);
        jTextFieldPICSupplier.setEnabled(true);
    }//GEN-LAST:event_jButtonResetInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonKeluar;
    private javax.swing.JButton jButtonResetInput;
    private javax.swing.JButton jButtonSearchProduct;
    private javax.swing.JButton jButtonTerima;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePo;
    private javax.swing.JTextField jTextFieldAlamatSupplier;
    private javax.swing.JTextField jTextFieldCostPrice;
    private javax.swing.JTextField jTextFieldDiskon;
    private javax.swing.JTextField jTextFieldHargaProduk;
    private javax.swing.JTextField jTextFieldIdProduct;
    private javax.swing.JTextField jTextFieldKodeSupplier;
    private javax.swing.JTextField jTextFieldNamaProduk;
    private javax.swing.JTextField jTextFieldNamaSupplier;
    private javax.swing.JTextField jTextFieldNoPo;
    private javax.swing.JTextField jTextFieldPICSupplier;
    private javax.swing.JTextField jTextFieldQtyPo;
    private javax.swing.JTextField jTextFieldQtyTrm;
    // End of variables declaration//GEN-END:variables
}
