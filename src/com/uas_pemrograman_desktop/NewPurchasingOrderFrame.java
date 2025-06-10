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
public class NewPurchasingOrderFrame extends javax.swing.JFrame {
    private  DefaultTableModel dtm;
    private int instock;
    private MenuUtamaFrame muf;
    /**
     * Creates new form NewPurchasingOrderFrame
     */
    public NewPurchasingOrderFrame(MenuUtamaFrame muf) {
        this.muf = muf;
        
        initComponents();
        
        setHeaderTable();
        setComboBox();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(NewPurchasingOrderFrame.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Pembuatan PO?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    NewPurchasingOrderFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    NewPurchasingOrderFrame.this.setVisible(false);
                    muf.setVisible(true);
                    muf.requestFocus();
                } else {
                    NewPurchasingOrderFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    
    private void setComboBox(){
        try{
            DbConnection dbc = new DbConnection();
            
            String queryCheckNoPo = "SELECT * FROM tpurchasingorder WHERE flag = 0";
            PreparedStatement pstmt = dbc.getConnection().prepareStatement(queryCheckNoPo);
            ResultSet rslt = pstmt.executeQuery();
            
            Object firstItem = null;
            if(jComboBoxNoPo.getItemCount() > 0){
                firstItem = jComboBoxNoPo.getItemAt(0);
            }
            
            jComboBoxNoPo.removeAllItems();
            if(firstItem != null){
                jComboBoxNoPo.addItem(firstItem.toString());
            }
            
            while(rslt.next()){
                String noPo = rslt.getString("nopo");
                
                if(firstItem == null || !firstItem.toString().equals(noPo)){
                    jComboBoxNoPo.addItem(noPo);
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void clearInput(){
        jTextFieldNoPo.setText("");
        jTextFieldNamaProduk.setText("");
        jTextFieldAlamatSupplier.setText("");
        jTextFieldCostPrice.setText("");
        jTextFieldIdProduct.setText("");
        jTextFieldHargaProduk.setText("");
        jTextFieldDiskon.setText("");
        jTextFieldQty.setText("");
        jTextFieldNamaSupplier.setText("");
        jTextFieldAlamatSupplier.setText("");
        jTextFieldPICSupplier.setText("");
        
        jComboBoxNoPo.setSelectedIndex(0);
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
    
    public void setInstockProduk(String instock){
        this.instock = Integer.parseInt(instock);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
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
        jButtonAddNoPo = new javax.swing.JButton();
        jTextFieldIdProduct = new javax.swing.JTextField();
        jTextFieldNamaProduk = new javax.swing.JTextField();
        jTextFieldHargaProduk = new javax.swing.JTextField();
        jTextFieldDiskon = new javax.swing.JTextField();
        jTextFieldCostPrice = new javax.swing.JTextField();
        jTextFieldQty = new javax.swing.JTextField();
        jButtonEditQty = new javax.swing.JButton();
        jButtonSearchProduct = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxNoPo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonSimpan = new javax.swing.JButton();
        jButtonHapus = new javax.swing.JButton();
        jButtonKeluar = new javax.swing.JButton();
        jButtonResetInput = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldAlamatSupplier = new javax.swing.JTextField();
        jButtonSearchSupplier = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldKodeSupplier = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNamaSupplier = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldPICSupplier = new javax.swing.JTextField();

        jMenu1.setText("jMenu1");

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

        jButtonAddNoPo.setText("Tambah No Po");
        jButtonAddNoPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddNoPoActionPerformed(evt);
            }
        });

        jTextFieldIdProduct.setEditable(false);
        jTextFieldIdProduct.setEnabled(false);

        jTextFieldNamaProduk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextFieldDiskon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButtonEditQty.setText("Edit");
        jButtonEditQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditQtyActionPerformed(evt);
            }
        });

        jButtonSearchProduct.setText("Cari Produk");
        jButtonSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchProductActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("%");

        jComboBoxNoPo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih No Po" }));
        jComboBoxNoPo.setEnabled(false);
        jComboBoxNoPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNoPoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
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
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addGap(31, 31, 31)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldDiskon, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCostPrice))
                                .addGap(9, 9, 9)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldHargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEditQty)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonAddNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddNoPo))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearchProduct))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(jTextFieldNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldHargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jTextFieldDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldCostPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEditQty)
                    .addComponent(jComboBoxNoPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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
        jLabel1.setText("Buat Purchasing Order");

        jButtonSimpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonSimpan.setText("Simpan");
        jButtonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimpanActionPerformed(evt);
            }
        });

        jButtonHapus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonHapus.setText("Hapus");
        jButtonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHapusActionPerformed(evt);
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

        jButtonSearchSupplier.setText("Cari Supplier");
        jButtonSearchSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchSupplierActionPerformed(evt);
            }
        });

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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldKodeSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSearchSupplier))
                    .addComponent(jTextFieldAlamatSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldNamaSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPICSupplier, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSearchSupplier)
                    .addComponent(jTextFieldKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonKeluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jButtonSimpan)
                    .addComponent(jButtonHapus)
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

    private void jButtonAddNoPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddNoPoActionPerformed
        try{
            DbConnection dbc = new DbConnection();
            
            jTextFieldNoPo.setText("");
            
            if(jTextFieldNoPo.getText().trim().isEmpty()){
                String queryCount = "SELECT COUNT(*) FROM tpurchasingorder WHERE nopo = ?";
                PreparedStatement pstmt = dbc.getConnection().prepareStatement(queryCount);

                String prefix = "POX";
                int counter = 1;
                String newId;

                while(true){
                    newId = prefix + String.format("%03d", counter);
                    pstmt.setString(1, newId);
                    ResultSet rslt = pstmt.executeQuery();

                    boolean exists = false;
                    if(rslt.next()){
                        exists = rslt.getInt(1) > 0;
                    }

                    if(exists == false){
                        break;
                    }
                    counter++;
                }
                jTextFieldNoPo.setText(newId);   
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonAddNoPoActionPerformed

    private void jButtonResetInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetInputActionPerformed
        clearInput();
        jTextFieldNamaProduk.setEnabled(true);
        jTextFieldHargaProduk.setEnabled(true);
        jTextFieldDiskon.setEnabled(true);
        jTextFieldCostPrice.setEnabled(true);
        jTextFieldNamaSupplier.setEnabled(true);
        jTextFieldAlamatSupplier.setEnabled(true);
        jTextFieldPICSupplier.setEnabled(true);

        jButtonEditQty.setEnabled(true);
        jComboBoxNoPo.setEnabled(false);
    }//GEN-LAST:event_jButtonResetInputActionPerformed

    private void jButtonSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchProductActionPerformed
        ProductSearchFrame psf = new ProductSearchFrame(this);
        psf.setVisible(true);
        psf.requestFocus();
    }//GEN-LAST:event_jButtonSearchProductActionPerformed

    private void jButtonKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKeluarActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar Dari Pembuatan PO?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            this.muf.setVisible(true);
            this.muf.requestFocus();
        }
    }//GEN-LAST:event_jButtonKeluarActionPerformed

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        try{
            DbConnection dbc = new DbConnection();
            
            String noPo = jTextFieldNoPo.getText().trim();
            String idProduk = jTextFieldIdProduct.getText().trim();
            String namaProduk = jTextFieldNamaProduk.getText().trim();
            String hargaText = jTextFieldHargaProduk.getText().trim();
            String diskonText = jTextFieldDiskon.getText().trim();
            String costPriceText = jTextFieldCostPrice.getText().trim();
            String qtyText = jTextFieldQty.getText().trim();
            
            String kodeSupplier = jTextFieldKodeSupplier.getText().trim();
            String namaSupplier = jTextFieldNamaSupplier.getText().trim();
            String alamatSupplier = jTextFieldAlamatSupplier.getText().trim();
            String PICSupplier = jTextFieldPICSupplier.getText().trim();
            
            if(noPo.equals("")){
                JOptionPane.showMessageDialog(this, "Field Nomor PO Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldNoPo.requestFocus();
            } else if(idProduk.equals("")){
                JOptionPane.showMessageDialog(this, "Field ID Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldIdProduct.requestFocus();
            } else if(namaProduk.equals("")){
                JOptionPane.showMessageDialog(this, "Field Nama Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldNamaProduk.requestFocus();
            } else if(hargaText.equals("")){
                JOptionPane.showMessageDialog(this, "Field Harga Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldHargaProduk.requestFocus();
            } else if(diskonText.equals("")){
                JOptionPane.showMessageDialog(this, "Field Diskon Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldDiskon.requestFocus();
            } else if(costPriceText.equals("")){
                JOptionPane.showMessageDialog(this, "Field Cost Price Produk Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldCostPrice.requestFocus();
            } else if(qtyText.equals("")){
                JOptionPane.showMessageDialog(this, "Field Quantity Pesanan Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldQty.requestFocus();
            } else if(kodeSupplier.equals("")){
                JOptionPane.showMessageDialog(this, "Field Kode Supplier Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldKodeSupplier.requestFocus();
            } else if(namaSupplier.equals("")){
                JOptionPane.showMessageDialog(this, "Field Nama Supplier Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldNamaSupplier.requestFocus();
            } else if(alamatSupplier.equals("")){
                JOptionPane.showMessageDialog(this, "Field Alamat Supplier Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldAlamatSupplier.requestFocus();
            } else if(PICSupplier.equals("")){
                JOptionPane.showMessageDialog(this, "Field PIC Supplier Masih Kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldPICSupplier.requestFocus();
            } else {
                
                int hargaProduk = Integer.parseInt(hargaText);
                int diskonProduk = Integer.parseInt(diskonText);
                int costPriceProduk = Integer.parseInt(costPriceText);
                int qtyPesanan = Integer.parseInt(qtyText);
                
                String checkExistsProductOnSupplier = "SELECT * FROM TDetailProduk WHERE ItemID = ? AND KodeSupplier = ?";
                PreparedStatement pstmt = dbc.getConnection().prepareStatement(checkExistsProductOnSupplier);
                pstmt.setString(1, idProduk);
                pstmt.setString(2, kodeSupplier);
                ResultSet rslt = pstmt.executeQuery();
                
                if(rslt.next()){
                    
                    int dbPrice = rslt.getInt("Price");
                    int dbDisc = rslt.getInt("Disc");
                    int dbCostPrice = rslt.getInt("CostPrice");
                    
                    if(dbPrice != hargaProduk || dbDisc != diskonProduk || dbCostPrice != costPriceProduk){
                        JOptionPane.showMessageDialog(this, "Produk " + namaProduk + "\nDengan Harga Rp." + hargaProduk + ", Diskon Rp." + diskonProduk + ", costprice Rp." + costPriceProduk + "\nTidak Tersedia Di Supplier " + namaSupplier + ". Silakan cek kembali.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    String queryCheckAvaiblePo = "SELECT * FROM tpurchasingorder WHERE nopo = ?";
                    pstmt = dbc.getConnection().prepareStatement(queryCheckAvaiblePo);
                    pstmt.setString(1, noPo);
                    rslt = pstmt.executeQuery();
                    
                    if(!rslt.next()){
                        
                        if(qtyPesanan > this.instock){
                            jTextFieldQty.setText("");
                            JOptionPane.showMessageDialog(this, "Jumlah Pesanan Melebihi Jumlah Instok!", "Warning", JOptionPane.WARNING_MESSAGE);
                            jTextFieldQty.requestFocus();
                        } else {

                            String queryInsert = "INSERT INTO tpurchasingorder(NoPo, ItemID, kodesupplier, Price, Disc, CostPrice, QtyPo, QtyTrm, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            pstmt = dbc.getConnection().prepareStatement(queryInsert);
                            pstmt.setString(1, noPo);
                            pstmt.setString(2, idProduk);
                            pstmt.setString(3, kodeSupplier);
                            pstmt.setInt(4, hargaProduk);
                            pstmt.setInt(5, diskonProduk);
                            pstmt.setInt(6, costPriceProduk);
                            pstmt.setInt(7, qtyPesanan);
                            pstmt.setInt(8, 0);
                            pstmt.setInt(9, 0);

                            int affected = pstmt.executeUpdate();

                            if(affected > 0){
                                
                                int newInstok = this.instock - qtyPesanan;
                                if(newInstok < 0){
                                    newInstok = 0;
                                }

                                String queryUpdateInstock = "UPDATE tdetailproduk SET instock = ? WHERE itemid = ? AND kodesupplier = ?";
                                pstmt = dbc.getConnection().prepareStatement(queryUpdateInstock);
                                pstmt.setInt(1, newInstok);
                                pstmt.setString(2, idProduk);
                                pstmt.setString(3, kodeSupplier);
                                pstmt.executeUpdate();
                                
                                setDataTable();
                                clearInput();
                                JOptionPane.showMessageDialog(this, "Po Produk dengan \nnama : " + namaProduk + " \nPada supplier : " + namaSupplier + " \nBerhasil Dibuat!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                        
                    } else {
                        
                        if(JOptionPane.showConfirmDialog(this, "Apakah Yakin Ingin Update Data Qty?", "Konfirmasi Update", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            
                            String queryGetOldQty = "SELECT QtyPo FROM tpurchasingorder WHERE NoPo = ?";
                            PreparedStatement pstmtGetOld = dbc.getConnection().prepareStatement(queryGetOldQty);
                            pstmtGetOld.setString(1, noPo);
                            ResultSet rsOld = pstmtGetOld.executeQuery();

                            int qtyLama = 0;
                            if(rsOld.next()){
                                qtyLama = rsOld.getInt("QtyPo");
                            }
                            
                            int selisihQty = qtyPesanan - qtyLama;
                            
                            String queryUpdate = "UPDATE tpurchasingorder SET qtypo = ? WHERE nopo = ?";
                            PreparedStatement pstmtUpdate = dbc.getConnection().prepareStatement(queryUpdate);
                            pstmtUpdate.setInt(1, qtyPesanan);
                            pstmtUpdate.setString(2, noPo);

                            int affected = pstmtUpdate.executeUpdate();
                            
                            if(affected > 0){
                                
                                int newInstok = this.instock - selisihQty;
                                if(newInstok < 0){
                                    newInstok = 0;
                                }

                                String queryUpdateInstock = "UPDATE tdetailproduk SET instock = ? WHERE itemid = ? AND kodesupplier = ?";
                                pstmt = dbc.getConnection().prepareStatement(queryUpdateInstock);
                                pstmt.setInt(1, newInstok);
                                pstmt.setString(2, idProduk);
                                pstmt.setString(3, kodeSupplier);
                                pstmt.executeUpdate();
                                
                                jButtonSimpan.setText("Simpan");

                                jTextFieldNamaProduk.setEnabled(true);
                                jTextFieldHargaProduk.setEnabled(true);
                                jTextFieldDiskon.setEnabled(true);
                                jTextFieldCostPrice.setEnabled(true);
                                jTextFieldNamaSupplier.setEnabled(true);
                                jTextFieldAlamatSupplier.setEnabled(true);
                                jTextFieldPICSupplier.setEnabled(true);

                                jButtonEditQty.setEnabled(true);
                                jComboBoxNoPo.setEnabled(false);
                                
                                clearInput();
                                setDataTable();
                                JOptionPane.showMessageDialog(this, "Qty Po berhasil di update!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                            } 
                        } else {
                            jButtonSimpan.setText("Simpan");

                            jTextFieldNamaProduk.setEnabled(true);
                            jTextFieldHargaProduk.setEnabled(true);
                            jTextFieldDiskon.setEnabled(true);
                            jTextFieldCostPrice.setEnabled(true);
                            jTextFieldNamaSupplier.setEnabled(true);
                            jTextFieldAlamatSupplier.setEnabled(true);
                            jTextFieldPICSupplier.setEnabled(true);

                            jButtonEditQty.setEnabled(true);
                            jComboBoxNoPo.setEnabled(false);

                            clearInput();
                            JOptionPane.showMessageDialog(this, "update Dibatalkan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    
                } else {
                    clearInput();
                    JOptionPane.showMessageDialog(this, "Produk " + namaProduk + " \nTidak Ditemukan Pada Supplier " + namaSupplier, "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSimpanActionPerformed

    private void jButtonSearchSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchSupplierActionPerformed

        SupplierSearchFrame ssf = new SupplierSearchFrame(this);

        ssf.setKodeSupplier(jTextFieldAlamatSupplier.getText().trim());

        ssf.setVisible(true);
    }//GEN-LAST:event_jButtonSearchSupplierActionPerformed

    private void jButtonEditQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditQtyActionPerformed
        jTextFieldNamaProduk.setEnabled(false);
        jTextFieldHargaProduk.setEnabled(false);
        jTextFieldDiskon.setEnabled(false);
        jTextFieldCostPrice.setEnabled(false);
        jTextFieldNamaSupplier.setEnabled(false);
        jTextFieldAlamatSupplier.setEnabled(false);
        jTextFieldPICSupplier.setEnabled(false);
        
        jButtonEditQty.setEnabled(false);
        jComboBoxNoPo.setEnabled(true);
    }//GEN-LAST:event_jButtonEditQtyActionPerformed

    private void jComboBoxNoPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNoPoActionPerformed
        try {
            DbConnection dbc = new DbConnection();
            int selectedIndex = jComboBoxNoPo.getSelectedIndex();

            if(selectedIndex != 0){
                String noPo = (String) jComboBoxNoPo.getSelectedItem();

                String queryCheckPo = "SELECT a.*, b.*, c.namaproduk " +
                                      "FROM tpurchasingorder a " +
                                      "JOIN tsupplier b ON a.kodesupplier = b.kodesupplier " +
                                      "JOIN tmasterid c ON a.ItemID = c.ItemID " +
                                      "WHERE a.nopo = ?";
                PreparedStatement pstmt = dbc.getConnection().prepareStatement(queryCheckPo);
                pstmt.setString(1, noPo);

                ResultSet rslt = pstmt.executeQuery();

                if(rslt.next()){
                    jTextFieldNoPo.setText(rslt.getString("a.nopo"));
                    jTextFieldIdProduct.setText(rslt.getString("a.itemid"));
                    jTextFieldNamaProduk.setText(rslt.getString("c.namaproduk"));
                    jTextFieldHargaProduk.setText(rslt.getString("a.price"));
                    jTextFieldDiskon.setText(rslt.getString("a.disc"));
                    jTextFieldCostPrice.setText(rslt.getString("a.costprice"));

                    jTextFieldKodeSupplier.setText(rslt.getString("a.kodesupplier"));
                    jTextFieldNamaSupplier.setText(rslt.getString("b.namasupplier"));
                    jTextFieldAlamatSupplier.setText(rslt.getString("alamat"));
                    jTextFieldPICSupplier.setText(rslt.getString("pic"));

                    jButtonSimpan.setText("update");
                }
            } else {
                jTextFieldNoPo.setText("");
                jTextFieldIdProduct.setText("");
                jTextFieldNamaProduk.setText("");
                jTextFieldHargaProduk.setText("");
                jTextFieldDiskon.setText("");
                jTextFieldCostPrice.setText("");
                jTextFieldKodeSupplier.setText("");
                jTextFieldNamaSupplier.setText("");
                jTextFieldAlamatSupplier.setText("");
                jTextFieldPICSupplier.setText("");

                jButtonSimpan.setText("Simpan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jComboBoxNoPoActionPerformed

    private void jButtonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusActionPerformed
        try {
            DbConnection dbc = new DbConnection();

            String noPo = jTextFieldNoPo.getText().trim();
            String idProduk = jTextFieldIdProduct.getText().trim();
            String kodeSupplier = jTextFieldKodeSupplier.getText().trim();

            if(noPo.equals("")){
                JOptionPane.showMessageDialog(this, "Nomor PO masih kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextFieldNoPo.requestFocus();
                return;
            }

            // Konfirmasi hapus
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus PO ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){

                // Ambil qtyPo sebelum hapus untuk update stok
                String queryGetQty = "SELECT QtyPo FROM tpurchasingorder WHERE NoPo = ?";
                PreparedStatement pstmtGetQty = dbc.getConnection().prepareStatement(queryGetQty);
                pstmtGetQty.setString(1, noPo);
                ResultSet rsQty = pstmtGetQty.executeQuery();

                int qtyPo = 0;
                if(rsQty.next()){
                    qtyPo = rsQty.getInt("QtyPo");
                } else {
                    JOptionPane.showMessageDialog(this, "Nomor PO tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Hapus data PO
                String queryDelete = "DELETE FROM tpurchasingorder WHERE NoPo = ?";
                PreparedStatement pstmtDelete = dbc.getConnection().prepareStatement(queryDelete);
                pstmtDelete.setString(1, noPo);
                int affected = pstmtDelete.executeUpdate();

                if(affected > 0){
                    // Update instock produk (tambah qtyPo kembali)
                    String queryUpdateInstock = "UPDATE tdetailproduk SET instock = instock + ? WHERE ItemID = ? AND KodeSupplier = ?";
                    PreparedStatement pstmtUpdateInstock = dbc.getConnection().prepareStatement(queryUpdateInstock);
                    pstmtUpdateInstock.setInt(1, qtyPo);
                    pstmtUpdateInstock.setString(2, idProduk);
                    pstmtUpdateInstock.setString(3, kodeSupplier);
                    pstmtUpdateInstock.executeUpdate();

                    setDataTable();
                    clearInput();
                    JOptionPane.showMessageDialog(this, "PO berhasil dihapus dan stok diperbarui!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus PO!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonHapusActionPerformed

    private void jTablePoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePoMouseClicked
        jTextFieldNoPo.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 0) + "");
        jTextFieldIdProduct.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 1) + "");
        jTextFieldNamaProduk.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 2) + "");
        jTextFieldHargaProduk.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 3) + "");
        jTextFieldDiskon.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 4) + "");
        jTextFieldCostPrice.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 5) + "");
        jTextFieldQty.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 6) + "");
        jTextFieldKodeSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 9) + "");
        jTextFieldNamaSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 10) + "");
        jTextFieldAlamatSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 11) + "");
        jTextFieldPICSupplier.setText(dtm.getValueAt(jTablePo.getSelectedRow(), 12) + "");
        
        
    }//GEN-LAST:event_jTablePoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddNoPo;
    private javax.swing.JButton jButtonEditQty;
    private javax.swing.JButton jButtonHapus;
    private javax.swing.JButton jButtonKeluar;
    private javax.swing.JButton jButtonResetInput;
    private javax.swing.JButton jButtonSearchProduct;
    private javax.swing.JButton jButtonSearchSupplier;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JComboBox<String> jComboBoxNoPo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
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
    private javax.swing.JTextField jTextFieldQty;
    // End of variables declaration//GEN-END:variables
}
