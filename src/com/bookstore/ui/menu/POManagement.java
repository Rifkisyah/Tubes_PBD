/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.menu;

import com.bookstore.data.EmployeeAccount;
import com.bookstore.data.MysqlConnection;
import com.bookstore.data.QuerySelector;
import com.bookstore.data.SessionAccount;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class POManagement extends javax.swing.JFrame {
    private DefaultTableModel tableModel;
    MysqlConnection mysqlConnection;
    PreparedStatement stmt;
    ResultSet rslt;
    private int countData;
    private String queryCheck, queryInsert, queryUpdate, queryDelete;
    /**
     * Creates new form POManagement
     */
    public POManagement() {
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        setHeaderTable();
        dataVendorComboBox();
        
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
        VendorStockBookTable.setAutoResizeMode(VendorStockBookTable.AUTO_RESIZE_OFF);
        
        column = VendorStockBookTable.getColumnModel().getColumn(0); //ID stock
        column.setPreferredWidth(120);
        column = VendorStockBookTable.getColumnModel().getColumn(1); //isbn
        column.setPreferredWidth(120);
        column = VendorStockBookTable.getColumnModel().getColumn(2); // judul buku
        column.setPreferredWidth(200);
        column = VendorStockBookTable.getColumnModel().getColumn(3); // kode rak
        column.setPreferredWidth(120);
        column = VendorStockBookTable.getColumnModel().getColumn(4); // nama rak
        column.setPreferredWidth(200);
        column = VendorStockBookTable.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(120);
        column = VendorStockBookTable.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = VendorStockBookTable.getColumnModel().getColumn(7); // jumlah stock
        column.setPreferredWidth(100);
        column = VendorStockBookTable.getColumnModel().getColumn(8); // tanggal update stock
        column.setPreferredWidth(150);
        column = VendorStockBookTable.getColumnModel().getColumn(9); // harga satuan
        column.setPreferredWidth(200);
    }
    
    private void getDataTable(){
        QuerySelector querySelector = new QuerySelector();
        
        try{
            querySelector.getAllDataVendorStock();
            
            while(querySelector.getRslt().next()){
                Object[] fieldx = new Object[10];
                    fieldx[0] = querySelector.getRslt().getString("id_detail_master_buku");
                    fieldx[1] = querySelector.getRslt().getString("isbn");
                    fieldx[2] = querySelector.getRslt().getString("judul_buku");
                    fieldx[3] = querySelector.getRslt().getString("kode_rak");
                    fieldx[4] = querySelector.getRslt().getString("nama_rak");
                    fieldx[5] = querySelector.getRslt().getString("id_vendor");
                    fieldx[6] = querySelector.getRslt().getString("nama_vendor");
                    fieldx[7] = querySelector.getRslt().getString("stock_buku");
                    fieldx[8] = querySelector.getRslt().getString("tanggal_update_stock");
                    fieldx[9] = querySelector.getRslt().getString("harga_satuan");
                    this.tableModel.addRow(fieldx);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    private void setHeaderTable(){
        this.tableModel = new DefaultTableModel();
        VendorStockBookTable.setModel(tableModel);
        
        tableModel.addColumn("ID Stock");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Judul Buku");
        tableModel.addColumn("ID Rak");
        tableModel.addColumn("Nama Rak");
        tableModel.addColumn("ID Vendor");
        tableModel.addColumn("Nama Vendor");
        tableModel.addColumn("Jumlah Stock");
        tableModel.addColumn("Tanggal Update Stock");
        tableModel.addColumn("Harga Satuan");
        
        columnSizing();
        getDataTable();
    }
    
    private void performSearch(String search_value) {
        QuerySelector querySelector = new QuerySelector();
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                getDataTable();
            } else {
                querySelector.searchBookDetail(search_value, "vendor");

                if (querySelector.getRslt().isBeforeFirst()) {
                    while (querySelector.getRslt().next()) {
                        Object[] fieldx = new Object[10];
                        fieldx[0] = querySelector.getRslt().getString("id_detail_master_buku");
                        fieldx[1] = querySelector.getRslt().getString("isbn");
                        fieldx[2] = querySelector.getRslt().getString("judul_buku");
                        fieldx[3] = querySelector.getRslt().getString("kode_rak");
                        fieldx[4] = querySelector.getRslt().getString("nama_rak");
                        fieldx[5] = querySelector.getRslt().getString("id_vendor");
                        fieldx[6] = querySelector.getRslt().getString("nama_vendor");
                        fieldx[7] = querySelector.getRslt().getString("stock_buku");
                        fieldx[8] = querySelector.getRslt().getString("tanggal_update_stock");
                        fieldx[9] = querySelector.getRslt().getString("harga_satuan");
                        tableModel.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void dataVendorComboBox() {
        QuerySelector querySelector = new QuerySelector();

        try {
            querySelector.getAllVendor();
            ResultSet rslt = querySelector.getRslt();

            vendor_combobox.removeAllItems();
            while (rslt.next()) {
                vendor_combobox.addItem(rslt.getString("nama_vendor"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private String generateNotaPO() {
        return "PO" + System.currentTimeMillis(); 
    }
    
    private void insertPurchaseOrder(String isbn, String idVendor, int jumlahPO, BigDecimal hargaSatuan) {
        try {
            EmployeeAccount employeeAccount = SessionAccount.getSessionAccount();
            String idPegawai = employeeAccount.getId();

            String notaPO = generateNotaPO();
            LocalDate tanggalPO = LocalDate.now();
            LocalDate estimasiDatang = tanggalPO.plusDays(7);
            BigDecimal totalBiaya = hargaSatuan.multiply(BigDecimal.valueOf(jumlahPO));
            String statusPO = "Diproses";

            QuerySelector querySelector = new QuerySelector();
            querySelector.insertPurchaseOrder(notaPO, idPegawai, idVendor, isbn, tanggalPO, estimasiDatang, jumlahPO, totalBiaya, statusPO);

            JOptionPane.showMessageDialog(this, "PO berhasil dibuat dengan Nota: " + notaPO, "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membuat PO!", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VendorStockBookTable = new javax.swing.JTable();
        search_field = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        isbn_field = new javax.swing.JTextField();
        book_title_field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        total_book_field = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        vendor_combobox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        price_field = new javax.swing.JTextField();
        CreateNewPOButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        TotalPriceLabel = new javax.swing.JLabel();
        CheckAvailableField = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 800));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Purchase Order");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Cari Produk Disini");

        VendorStockBookTable.setBackground(new java.awt.Color(153, 153, 255));
        VendorStockBookTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        VendorStockBookTable.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        VendorStockBookTable.setForeground(new java.awt.Color(255, 255, 255));
        VendorStockBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Stock", "ISBN", "Judul Buku", "Kode Rak", "Nama Rak", "ID Vendor", "Nama Vendor", "Jumlah Stock", "Tanggal Update Stock", "Harga Satuan"
            }
        ));
        VendorStockBookTable.setAlignmentY(1.0F);
        VendorStockBookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VendorStockBookTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(VendorStockBookTable);

        search_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Buat PO");

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Judul Buku");

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("ISBN");

        isbn_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        book_title_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Jumlah Buku");

        total_book_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Vendor");

        vendor_combobox.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Harga Satuan");

        price_field.setEditable(false);
        price_field.setBackground(new java.awt.Color(204, 204, 204));
        price_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        price_field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        CreateNewPOButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CreateNewPOButton.setText("Buat PO");
        CreateNewPOButton.setEnabled(false);
        CreateNewPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateNewPOButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Harga");

        TotalPriceLabel.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        TotalPriceLabel.setForeground(new java.awt.Color(255, 255, 255));
        TotalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TotalPriceLabel.setText("Rp. 0");

        CheckAvailableField.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CheckAvailableField.setText("Cek Ketersediaan Produk");
        CheckAvailableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckAvailableFieldActionPerformed(evt);
            }
        });

        CloseButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
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
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CreateNewPOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TotalPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(total_book_field, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CheckAvailableField, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(search_field))
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(32, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(isbn_field, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(book_title_field, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(price_field, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(vendor_combobox, javax.swing.GroupLayout.Alignment.LEADING, 0, 252, Short.MAX_VALUE)))))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isbn_field))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(book_title_field)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(vendor_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(price_field))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckAvailableField)
                    .addComponent(total_book_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TotalPriceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateNewPOButton)
                    .addComponent(CloseButton))
                .addGap(125, 125, 125))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            this.setFocusable(false);
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void CreateNewPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateNewPOButtonActionPerformed
        String isbn = isbn_field.getText().trim();
        String vendorName = (String) vendor_combobox.getSelectedItem();
        int jumlahPO;
        BigDecimal hargaSatuan;

        try {
            jumlahPO = Integer.parseInt(total_book_field.getText().trim());
            hargaSatuan = new BigDecimal(price_field.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga atau jumlah PO tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            QuerySelector querySelector = new QuerySelector();
            String idVendor = querySelector.getIdVendorByName(vendorName);

            if (idVendor == null) {
                JOptionPane.showMessageDialog(this, "Vendor tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int currentStock = querySelector.getCurrentStock(isbn, idVendor);
            if (currentStock == -1) {
                JOptionPane.showMessageDialog(this, "Data stok tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newStock = currentStock - jumlahPO;
            if (newStock < 0) {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi saat proses update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            querySelector.updateStock(isbn, idVendor, newStock);
            insertPurchaseOrder(isbn, idVendor, jumlahPO, hargaSatuan); // masih panggil method refactored sebelumnya

            CreateNewPOButton.setEnabled(false);
            CloseButton.setEnabled(true);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat proses PO!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CreateNewPOButtonActionPerformed

    private void search_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_fieldActionPerformed
        String search_value = search_field.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_search_fieldActionPerformed

    private void VendorStockBookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VendorStockBookTableMouseClicked
        isbn_field.setText(tableModel.getValueAt(VendorStockBookTable.getSelectedRow(), 1) + "");
        book_title_field.setText(tableModel.getValueAt(VendorStockBookTable.getSelectedRow(), 2) + "");
//        vendor_combobox.setText(tableModel.getValueAt(VendorStockBookTable.getSelectedRow(), 1) + "");
        price_field.setText(tableModel.getValueAt(VendorStockBookTable.getSelectedRow(), 9) + "");
        total_book_field.setText(tableModel.getValueAt(VendorStockBookTable.getSelectedRow(), 7) + "");
    }//GEN-LAST:event_VendorStockBookTableMouseClicked

    private void CheckAvailableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckAvailableFieldActionPerformed
        String isbn = isbn_field.getText().trim();
        String title = book_title_field.getText().trim();
        String vendorName = (String) vendor_combobox.getSelectedItem();

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN Masih Kosong!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul Buku Masih Kosong!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (vendorName == null || vendorName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vendor Masih Kosong!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalPo;
        try {
            totalPo = Integer.parseInt(total_book_field.getText().trim());
            if (totalPo <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah PO harus lebih dari 0!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah PO tidak valid!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            QuerySelector querySelector = new QuerySelector();

            // Dapatkan id_vendor berdasarkan nama vendor
            String idVendor = querySelector.getIdVendorByName(vendorName);
            if (idVendor == null) {
                JOptionPane.showMessageDialog(this, "Vendor tidak ditemukan di database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Dapatkan stok dan harga satuan berdasarkan ISBN dan idVendor
            int currentStock = querySelector.getStockAndPrice(isbn, idVendor);
            if (currentStock == -1) {
                JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (currentStock < totalPo) {
                JOptionPane.showMessageDialog(this, "Stok Buku Tidak Cukup!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal hargaSatuan = querySelector.getHargaSatuan(isbn, idVendor); // Mendapatkan harga satuan
            BigDecimal grandTotalPO = hargaSatuan.multiply(BigDecimal.valueOf(totalPo));

            // Update UI
            price_field.setText(hargaSatuan.toString());
            TotalPriceLabel.setText(grandTotalPO.toString());
            CreateNewPOButton.setEnabled(true);
            CloseButton.setEnabled(false);

            JOptionPane.showMessageDialog(this, "Buku Tersedia! Silakan lanjutkan ke PO.", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat akses database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CheckAvailableFieldActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckAvailableField;
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton CreateNewPOButton;
    private javax.swing.JLabel TotalPriceLabel;
    private javax.swing.JTable VendorStockBookTable;
    private javax.swing.JTextField book_title_field;
    private javax.swing.JTextField isbn_field;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField price_field;
    private javax.swing.JTextField search_field;
    private javax.swing.JTextField total_book_field;
    private javax.swing.JComboBox<String> vendor_combobox;
    // End of variables declaration//GEN-END:variables
}
