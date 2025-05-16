/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.inventory_menu;

import com.bookstore.data.QuerySelector;
import com.bookstore.ui.InventoryDashboardFrame;
import com.bookstore.ui.inventory_menu.CreatePurchaseOrder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rifki
 */
public class ReceivedPurchaseOrder extends javax.swing.JFrame {
    InventoryDashboardFrame inventoryDashboardFrame;
    private DefaultTableModel model;
    QuerySelector querySelector;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form ReceivedPurchaseOrder
     */
    public ReceivedPurchaseOrder(InventoryDashboardFrame inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Buat Purchase Order");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ReceivedPurchaseOrder.this,
                        "Apakah Kamu Yakin Ingin Keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    ReceivedPurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ReceivedPurchaseOrder.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    ReceivedPurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
        dataRoleToComboBox();
        this.filterComboBoxIntialize = true;
        
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
        purchaseOrderTable.setAutoResizeMode(purchaseOrderTable.AUTO_RESIZE_OFF);
        
        column = purchaseOrderTable.getColumnModel().getColumn(0); //ID stock
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(1); //isbn
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(2); // judul buku
        column.setPreferredWidth(200);
        column = purchaseOrderTable.getColumnModel().getColumn(3); // kode rak
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(4); // nama rak
        column.setPreferredWidth(200);
        column = purchaseOrderTable.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = purchaseOrderTable.getColumnModel().getColumn(7); // jumlah stock
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(8); // tanggal update stock
        column.setPreferredWidth(150);
        column = purchaseOrderTable.getColumnModel().getColumn(9); // harga satuan
        column.setPreferredWidth(100);
        column = purchaseOrderTable.getColumnModel().getColumn(10); // harga satuan
        column.setPreferredWidth(150);
        
    }
    
    private void dataToTable(){
        this.querySelector = new QuerySelector();
        
        try{
            querySelector.selectPurchaseOrderByTwoStatus("Diproses", "Partial");
            
            model.setRowCount(0);
            
            while(querySelector.getResultSet().next()){
                Object[] fieldx = new Object[11];
                fieldx[0] = querySelector.getResultSet().getString("id_PO");
                fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                fieldx[2] = querySelector.getResultSet().getString("nama");
                fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                fieldx[5] = querySelector.getResultSet().getString("isbn");
                fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                fieldx[8] = querySelector.getResultSet().getDate("estimasi_tanggal_datang");
                fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
                this.model.addRow(fieldx);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    private void setHeaderTable(){
        this.model = new DefaultTableModel();
        purchaseOrderTable.setModel(model);
        
        model.addColumn("ID PO");
        model.addColumn("ID Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("ID Vendor");
        model.addColumn("Nama Vendor");
        model.addColumn("ISBN");
        model.addColumn("Judul Buku");
        model.addColumn("Tanggal PO");
        model.addColumn("Estimasi Tanggal Kedatangan");
        model.addColumn("Jumlah");
        model.addColumn("Total Biaya");
        
        columnSizing();
        dataToTable();
    }

   private void dataRoleToComboBox() {
        try {
            querySelector.selectPurchaseOrderByTwoStatus("Diproses", "Partial");

            // Simpan item index ke-0
            Object firstItem = null;
            if (ComboBoxFilterVendor.getItemCount() > 0) {
                firstItem = ComboBoxFilterVendor.getItemAt(0);
            }

            // Hapus semua item, lalu tambahkan kembali item index 0
            ComboBoxFilterVendor.removeAllItems();
            if (firstItem != null) {
                ComboBoxFilterVendor.addItem(firstItem.toString()); // tambah kembali item 0
            }

            // Tambahkan data dari database
            while (querySelector.getResultSet().next()) {
                String id = querySelector.getResultSet().getString("id_po");
                String name = querySelector.getResultSet().getString("nama_vendor");
                
                vendorNameToIdMap.put(name, id);

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(name)) {
                    ComboBoxFilterVendor.addItem(name);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
   
    private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataToTable();
            } else {
                String roleName = (String) ComboBoxFilterVendor.getSelectedItem();
                
                if(roleName.equals("-- Pilih Filter Vendor --")){
                    querySelector.selectLikeWithJoinDetailMasterBuku("isbn", "judul_buku", search_value, "vendor");
                } else {
                    System.out.println("roleNameToIdMap = " + this.vendorNameToIdMap);
                    String vendorId = this.vendorNameToIdMap.get(roleName);

                    if (vendorId == null || vendorId.trim().isEmpty()) {
                        // Gagal mapping role → fallback ke tanpa filter role
                        querySelector.selectLikeWithJoinDetailMasterBuku("isbn", "judul_buku", search_value, "vendor");
                    } else {
                        querySelector.selectLikeWithJoinAndFilterDetailMasterBuku("isbn", "judul_buku", search_value, "id_vendor", vendorId, "vendor");
                    }
                }

                if (querySelector.getResultSet().isBeforeFirst()) {
                    while (querySelector.getResultSet().next()) {
                        Object[] fieldx = new Object[11];
                        fieldx[0] = querySelector.getResultSet().getString("id_PO");
                        fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                        fieldx[2] = querySelector.getResultSet().getString("nama");
                        fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                        fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                        fieldx[5] = querySelector.getResultSet().getString("isbn");
                        fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                        fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                        fieldx[8] = querySelector.getResultSet().getDate("estimasi_tanggal_datang");
                        fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                        fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
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

    private String generateIdReceviedPO() {
        return "RPO" + System.currentTimeMillis(); 
    }

    private void handlePOReception(String poId, String isbn, LocalDate estimatedDate, int qtyReceived, int totalPo, BigDecimal totalPrice, String note) throws SQLException, ClassNotFoundException {
        BigDecimal unitPrice = totalPrice.divide(BigDecimal.valueOf(totalPo), 2, RoundingMode.HALF_UP);
        BigDecimal receivedPrice = unitPrice.multiply(BigDecimal.valueOf(qtyReceived));

        LocalDate receivedDate = LocalDate.now();
        int dayDiff = (int) ChronoUnit.DAYS.between(estimatedDate, receivedDate);

        String status;
        if (dayDiff < 0) status = "Diterima Lebih Awal";
        else if (dayDiff == 0) status = "Diterima Tepat Waktu";
        else status = "Terlambat";

        querySelector.updateReceviedQty(qtyReceived, poId);
        String receivedId = generateIdReceviedPO();
        querySelector.insertReceivedPO(receivedId, poId, isbn, receivedDate, qtyReceived, receivedPrice, note, status);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
    }

    private void clearInputFields() {
        input_field_id_po.setText("");
        input_field_qty_recevied.setText("");
        input_field_note.setText("");
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        search_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseOrderTable = new javax.swing.JTable();
        ComboBoxFilterVendor = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        input_field_qty_recevied = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        confirmPOButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        input_field_id_po = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        input_field_note = new javax.swing.JTextArea();
        resetInputButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(148, 180, 193));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 800));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 52, 72));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Terima Purchase Order");

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Cari PO :");

        search_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });

        purchaseOrderTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        purchaseOrderTable.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        purchaseOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID PO", "ID Pegawai", "Nama Pegawai", "ID Vendor", "Nama Vendor", "ISBN", "Judul Buku", "Tanggal PO", "Estimasi Tanggal Kedatangan", "Jumlah", "Total Biaya", "Status"
            }
        ));
        purchaseOrderTable.setAlignmentY(1.0F);
        purchaseOrderTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchaseOrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseOrderTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(purchaseOrderTable);

        ComboBoxFilterVendor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Filter Vendor --" }));
        ComboBoxFilterVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFilterVendorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_field)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxFilterVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxFilterVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(84, 119, 146));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Jumlah Diterima");

        input_field_qty_recevied.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        input_field_qty_recevied.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        input_field_qty_recevied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_field_qty_receviedActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Formulir Penerimaan Purchase Order");

        confirmPOButton.setBackground(new java.awt.Color(0, 153, 0));
        confirmPOButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        confirmPOButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmPOButton.setText("Terima PO");
        confirmPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPOButtonActionPerformed(evt);
            }
        });

        CloseButton.setBackground(new java.awt.Color(33, 52, 72));
        CloseButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CloseButton.setForeground(new java.awt.Color(255, 255, 255));
        CloseButton.setText("Keluar");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("ID PO");

        input_field_id_po.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Keterangan Penerimaan");

        input_field_note.setColumns(20);
        input_field_note.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        input_field_note.setRows(5);
        jScrollPane2.setViewportView(input_field_note);

        resetInputButton.setBackground(new java.awt.Color(153, 0, 0));
        resetInputButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        resetInputButton.setForeground(new java.awt.Color(255, 255, 255));
        resetInputButton.setText("Reset");
        resetInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmPOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resetInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                            .addComponent(input_field_qty_recevied)
                            .addComponent(input_field_id_po))))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input_field_id_po, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_field_qty_recevied, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(confirmPOButton)
                    .addComponent(resetInputButton))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1352, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_fieldActionPerformed
        String search_value = search_field.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_search_fieldActionPerformed

    private void purchaseOrderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseOrderTableMouseClicked
        input_field_id_po.setText(model.getValueAt(purchaseOrderTable.getSelectedRow(), 0) + "");
        input_field_qty_recevied.setText(model.getValueAt(purchaseOrderTable.getSelectedRow(), 9) + "");
        input_field_id_po.setEnabled(false);
    }//GEN-LAST:event_purchaseOrderTableMouseClicked

    private void ComboBoxFilterVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFilterVendorActionPerformed
        if(this.filterComboBoxIntialize){
            String filterVendorName = (String) ComboBoxFilterVendor.getSelectedItem();
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
                                Object[] fieldx = new Object[11];
                                fieldx[0] = querySelector.getResultSet().getString("id_PO");
                                fieldx[1] = querySelector.getResultSet().getString("id_pegawai");
                                fieldx[2] = querySelector.getResultSet().getString("nama");
                                fieldx[3] = querySelector.getResultSet().getString("id_vendor");
                                fieldx[4] = querySelector.getResultSet().getString("nama_vendor");
                                fieldx[5] = querySelector.getResultSet().getString("isbn");
                                fieldx[6] = querySelector.getResultSet().getString("judul_buku");
                                fieldx[7] = querySelector.getResultSet().getString("tanggal_PO");
                                fieldx[8] = querySelector.getResultSet().getDate("estimasi_tanggal_datang");
                                fieldx[9] = querySelector.getResultSet().getInt("jumlah_PO");
                                fieldx[10] = querySelector.getResultSet().getDouble("total_biaya");
                                this.model.addRow(fieldx);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, " Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_ComboBoxFilterVendorActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void confirmPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPOButtonActionPerformed
        String poId = input_field_id_po.getText().trim();
        String qtyText = input_field_qty_recevied.getText().trim();
        String noteReceived = input_field_note.getText().trim();

        if (poId.isEmpty()) {
            showError("ID PO masih kosong!");
            return;
        }
        if (qtyText.isEmpty()) {
            showError("Jumlah Diterima masih kosong!");
            return;
        }
        if (noteReceived.isEmpty()) {
            showError("Keterangan masih kosong!");
            return;
        }

        int qtyReceived;
        try {
            qtyReceived = Integer.parseInt(qtyText);
            if (qtyReceived <= 0) {
                showError("Jumlah yang diterima harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Jumlah yang diterima tidak valid!");
            return;
        }

        try {
            querySelector.selectAllByColumn("T_PurchaseOrder", "id_po", poId);
            ResultSet rs = querySelector.getResultSet();
            if (!rs.next()) {
                showError("PO tidak ditemukan!");
                return;
            }

            String isbn = rs.getString("isbn");
            int totalPo = rs.getInt("jumlah_po");
            int qtyAlreadyReceived = rs.getInt("jumlah_diterima");
            BigDecimal totalPrice = rs.getBigDecimal("total_biaya");
            LocalDate estimatedDate = rs.getDate("estimasi_tanggal_datang").toLocalDate();
            String status = rs.getString("status_po");

            int sisa = totalPo - qtyAlreadyReceived;

            if (qtyReceived > sisa) {
                showError("Jumlah yang diterima melebihi sisa dari PO! (Sisa: " + sisa + ")");
                return;
            }

            switch (status) {
                case "Diterima":
                    showError("Semua barang untuk PO ini sudah diterima. Tidak bisa input ulang.");
                    return;
                case "Batal":
                    showError("PO ini sudah dibatalkan. Tidak bisa diproses.");
                    return;
                case "Partial":
                case "Diproses":
                    break;
                default:
                    showError("Status PO tidak dikenali: " + status);
                    return;
            }

            int totalDiterimaBaru = qtyAlreadyReceived + qtyReceived;
            String newStatus = totalDiterimaBaru == totalPo ? "Diterima" : "Partial";

            // Update status dan jumlah_diterima di tabel PO
            querySelector.updateTwoColumns(
                "T_PurchaseOrder",
                new String[] { "status_po", "jumlah_diterima" },
                new Object[] { newStatus, totalDiterimaBaru },
                "id_po",
                poId
            );

            handlePOReception(poId, isbn, estimatedDate, qtyReceived, totalPo, totalPrice, noteReceived);
            clearInputFields();

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            dataToTable();

            JOptionPane.showMessageDialog(this,
                totalDiterimaBaru == totalPo
                    ? "Semua Jumlah PO Berhasil diterima!"
                    : "Sebagian Jumlah PO Berhasil diterima! sebesar " + qtyReceived,
                "Info", JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException | ClassNotFoundException e) {
            showError("Terjadi kesalahan saat mengakses database!");
            clearInputFields();
        }
    }//GEN-LAST:event_confirmPOButtonActionPerformed
                                                 

    private void input_field_qty_receviedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_field_qty_receviedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_field_qty_receviedActionPerformed

    private void resetInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetInputButtonActionPerformed
        input_field_id_po.setText("");
        input_field_qty_recevied.setText("");
        input_field_note.setText("");
        
        input_field_id_po.setEnabled(true);
    }//GEN-LAST:event_resetInputButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JComboBox<String> ComboBoxFilterVendor;
    private javax.swing.JButton confirmPOButton;
    private javax.swing.JTextField input_field_id_po;
    private javax.swing.JTextArea input_field_note;
    private javax.swing.JTextField input_field_qty_recevied;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable purchaseOrderTable;
    private javax.swing.JButton resetInputButton;
    private javax.swing.JTextField search_field;
    // End of variables declaration//GEN-END:variables
}
