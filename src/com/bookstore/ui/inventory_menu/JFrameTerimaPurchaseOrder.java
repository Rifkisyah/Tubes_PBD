/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.inventory_menu;

import com.bookstore.model.DetailMasterBuku;
import com.bookstore.model.PurchaseOrder;
import com.bookstore.model.Rak;
import com.bookstore.model.Vendor;
import com.bookstore.ui.JFrameGudang;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rifki
 */
public class JFrameTerimaPurchaseOrder extends javax.swing.JFrame {
    JFrameGudang inventoryDashboardFrame;
    private DefaultTableModel model;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form ReceivedPurchaseOrder
     */
    public JFrameTerimaPurchaseOrder(JFrameGudang inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Buat Purchase Order");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(JFrameTerimaPurchaseOrder.this,
                        "Apakah Kamu Yakin Ingin Keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    JFrameTerimaPurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JFrameTerimaPurchaseOrder.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    JFrameTerimaPurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        mengaturHeaderTabel();
        dataKeCombobox(JComboBox_rakBuku);
        
        this.filterComboBoxIntialize = true;
        
        JTexfield_pencarianPO.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(JTexfield_pencarianPO.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(JTexfield_pencarianPO.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(JTexfield_pencarianPO.getText().trim());
            }
            
        });
    }

   private void mengaturUkuranKolom(){
        TableColumn column;
        JTable_po.setAutoResizeMode(JTable_po.AUTO_RESIZE_OFF);
        
        column = JTable_po.getColumnModel().getColumn(0); //ID stock
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(1); //isbn
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(2); // judul buku
        column.setPreferredWidth(200);
        column = JTable_po.getColumnModel().getColumn(3); // kode rak
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(4); // nama rak
        column.setPreferredWidth(200);
        column = JTable_po.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = JTable_po.getColumnModel().getColumn(7); // jumlah stock
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(8); // tanggal update stock
        column.setPreferredWidth(150);
        column = JTable_po.getColumnModel().getColumn(9); // harga satuan
        column.setPreferredWidth(100);
        column = JTable_po.getColumnModel().getColumn(10); // harga satuan
        column.setPreferredWidth(150);
        
    }
    
    private void dataKeTabel(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        
        try{
            purchaseOrder.getSemuaDataBerdasarkanStatus("Diproses", "Partial");
            
            model.setRowCount(0);
            
            while(purchaseOrder.getResultSet().next()){
                Object[] fieldx = new Object[11];
                fieldx[0] = purchaseOrder.getResultSet().getString("id_PO");
                fieldx[1] = purchaseOrder.getResultSet().getString("id_pegawai");
                fieldx[2] = purchaseOrder.getResultSet().getString("nama");
                fieldx[3] = purchaseOrder.getResultSet().getString("id_vendor");
                fieldx[4] = purchaseOrder.getResultSet().getString("nama_vendor");
                fieldx[5] = purchaseOrder.getResultSet().getString("isbn");
                fieldx[6] = purchaseOrder.getResultSet().getString("judul_buku");
                fieldx[7] = purchaseOrder.getResultSet().getString("tanggal_PO");
                fieldx[8] = purchaseOrder.getResultSet().getDate("estimasi_tanggal_datang");
                fieldx[9] = purchaseOrder.getResultSet().getInt("jumlah_PO");
                fieldx[10] = purchaseOrder.getResultSet().getDouble("total_biaya");
                this.model.addRow(fieldx);
            }
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void mengaturHeaderTabel(){
        this.model = new DefaultTableModel();
        JTable_po.setModel(model);
        
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
        
        mengaturUkuranKolom();
        dataKeTabel();
    }

   private void dataKeCombobox(JComboBox comboBox) {
       Rak rak = new Rak();
       
        try {
            rak.getSemuaData();
            // Simpan item index ke-0
            Object firstItem = null;
            if (comboBox.getItemCount() > 0) {
                firstItem = comboBox.getItemAt(0);
            }

            // Hapus semua item, lalu tambahkan kembali item index 0
            comboBox.removeAllItems();
            if (firstItem != null) {
                comboBox.addItem(firstItem.toString()); // tambah kembali item 0
            }

            // Tambahkan data dari database
            while (rak.getResultSet().next()) {
                String id = rak.getResultSet().getString("kode_rak");
                String name = rak.getResultSet().getString("nama_rak");
                
                vendorNameToIdMap.put(name, id);

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(name)) {
                    comboBox.addItem(name);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
    private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataKeTabel();
            } else {
                purchaseOrder.getPencarianDataTabelPo(search_value);

                if (purchaseOrder.getResultSet().isBeforeFirst()) {
                    while (purchaseOrder.getResultSet().next()) {
                        Object[] fieldx = new Object[11];
                        fieldx[0] = purchaseOrder.getResultSet().getString("id_PO");
                        fieldx[1] = purchaseOrder.getResultSet().getString("id_pegawai");
                        fieldx[2] = purchaseOrder.getResultSet().getString("nama");
                        fieldx[3] = purchaseOrder.getResultSet().getString("id_vendor");
                        fieldx[4] = purchaseOrder.getResultSet().getString("nama_vendor");
                        fieldx[5] = purchaseOrder.getResultSet().getString("isbn");
                        fieldx[6] = purchaseOrder.getResultSet().getString("judul_buku");
                        fieldx[7] = purchaseOrder.getResultSet().getString("tanggal_PO");
                        fieldx[8] = purchaseOrder.getResultSet().getDate("estimasi_tanggal_datang");
                        fieldx[9] = purchaseOrder.getResultSet().getInt("jumlah_PO");
                        fieldx[10] = purchaseOrder.getResultSet().getDouble("total_biaya");
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

    public String generateIdTerimaPO() {
        PurchaseOrder penerimaanPO = new PurchaseOrder(); // Ganti dengan class sesuai struktur kamu
        String prefix = "RPO-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        int counter = 1;
        String newId;

        while (true) {
            newId = prefix + String.format("%04d", counter);
            if (!penerimaanPO.cekIdPenerimaanPOSudahAda(newId)) {
                break;
            }
            counter++;
        }
        return newId;
    }

    
    public String generateIdInventory(){
        DetailMasterBuku detailMasterBuku = new DetailMasterBuku();
        String prefix = "INV-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        int counter = 1;
        String newId;

        while (true) {
            newId = prefix + String.format("%04d", counter);
            if (!detailMasterBuku.cekIdDetailMasterBukuSudahAda(newId)) {
                break;
            }
            counter++;
        }
        return newId;
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

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.updateJumlahDiterima(qtyReceived, poId);
        
        String receivedId = generateIdTerimaPO();
        purchaseOrder.menambahkanDataTerimaPO(receivedId, poId, isbn, receivedDate, qtyReceived, receivedPrice, note, status);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
    }

    private void clearInputFields() {
        JTextfield_idPo.setText("");
        JTextfield_jumlahDiterima.setText("");
        JTextarea_keteranganPenerimaan.setText("");
        JComboBox_rakBuku.setSelectedIndex(0);
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
        JTexfield_pencarianPO = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_po = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        JTextfield_jumlahDiterima = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        JButton_terimaPo = new javax.swing.JButton();
        JButton_keluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        JTextfield_idPo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTextarea_keteranganPenerimaan = new javax.swing.JTextArea();
        JButton_resetPo = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        JComboBox_rakBuku = new javax.swing.JComboBox<>();

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

        JTexfield_pencarianPO.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        JTexfield_pencarianPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTexfield_pencarianPOActionPerformed(evt);
            }
        });

        JTable_po.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        JTable_po.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JTable_po.setModel(new javax.swing.table.DefaultTableModel(
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
        JTable_po.setAlignmentY(1.0F);
        JTable_po.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JTable_po.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_poMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_po);

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
                        .addComponent(JTexfield_pencarianPO)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTexfield_pencarianPO, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(84, 119, 146));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Jumlah Diterima");

        JTextfield_jumlahDiterima.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JTextfield_jumlahDiterima.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JTextfield_jumlahDiterima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_jumlahDiterimaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Formulir Penerimaan Purchase Order");

        JButton_terimaPo.setBackground(new java.awt.Color(0, 153, 0));
        JButton_terimaPo.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_terimaPo.setForeground(new java.awt.Color(255, 255, 255));
        JButton_terimaPo.setText("Terima PO");
        JButton_terimaPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_terimaPoActionPerformed(evt);
            }
        });

        JButton_keluar.setBackground(new java.awt.Color(33, 52, 72));
        JButton_keluar.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_keluar.setForeground(new java.awt.Color(255, 255, 255));
        JButton_keluar.setText("Keluar");
        JButton_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_keluarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("ID PO");

        JTextfield_idPo.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Keterangan Penerimaan");

        JTextarea_keteranganPenerimaan.setColumns(20);
        JTextarea_keteranganPenerimaan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        JTextarea_keteranganPenerimaan.setRows(5);
        jScrollPane2.setViewportView(JTextarea_keteranganPenerimaan);

        JButton_resetPo.setBackground(new java.awt.Color(153, 0, 0));
        JButton_resetPo.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_resetPo.setForeground(new java.awt.Color(255, 255, 255));
        JButton_resetPo.setText("Reset");
        JButton_resetPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_resetPoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Disimpan Di Rak");

        JComboBox_rakBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Rak Buku --" }));

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
                        .addComponent(JButton_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(JButton_terimaPo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JButton_resetPo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                            .addComponent(JTextfield_jumlahDiterima)
                            .addComponent(JTextfield_idPo)
                            .addComponent(JComboBox_rakBuku, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextfield_idPo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_jumlahDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(JComboBox_rakBuku)
                        .addGap(3, 3, 3)))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_keluar)
                    .addComponent(JButton_terimaPo)
                    .addComponent(JButton_resetPo))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
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

    private void JTexfield_pencarianPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTexfield_pencarianPOActionPerformed
        String search_value = JTexfield_pencarianPO.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_JTexfield_pencarianPOActionPerformed

    private void JTable_poMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_poMouseClicked
        JTextfield_idPo.setText(model.getValueAt(JTable_po.getSelectedRow(), 0) + "");
        JTextfield_jumlahDiterima.setText(model.getValueAt(JTable_po.getSelectedRow(), 9) + "");
        JTextfield_idPo.setEnabled(false);
    }//GEN-LAST:event_JTable_poMouseClicked

    private void JButton_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_JButton_keluarActionPerformed

    private void JButton_terimaPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_terimaPoActionPerformed
        String poId = JTextfield_idPo.getText().trim();
        String qtyText = JTextfield_jumlahDiterima.getText().trim();
        String noteReceived = JTextarea_keteranganPenerimaan.getText().trim();
        String rackName = (String) JComboBox_rakBuku.getSelectedItem();
        
        PurchaseOrder purchaseOrder = new PurchaseOrder();

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
            purchaseOrder.getSemuaPoBerdasarkanIdPo(poId);

            if (!purchaseOrder.getResultSet().next()) {
                showError("PO tidak ditemukan!");
                return;
            }

            String isbn = purchaseOrder.getResultSet().getString("isbn");
            int totalPo = purchaseOrder.getResultSet().getInt("jumlah_po");
            int qtyAlreadyReceived = purchaseOrder.getResultSet().getInt("jumlah_diterima");
            BigDecimal totalPrice = purchaseOrder.getResultSet().getBigDecimal("total_biaya");
            LocalDate estimatedDate = LocalDate.now();
            String status = purchaseOrder.getResultSet().getString("status_po");
            String vendorId = purchaseOrder.getResultSet().getString("id_vendor");

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

            purchaseOrder.updateStatusDanJumlahDiterima(new String[] { "status_po", "jumlah_diterima" }, new Object[] { newStatus, totalDiterimaBaru }, poId);
            
            Rak rak = new Rak();
            rak.getKodeRak(rackName);

            String rackCode = null;

            ResultSet rackResultSet = rak.getResultSet();
            if (rackResultSet.next()) {
                rackCode = rackResultSet.getString("kode_rak");
            }

            if (rackCode == null) {
                JOptionPane.showMessageDialog(this, "Rak Belum Dipilih!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            DetailMasterBuku detailMasterBuku = new DetailMasterBuku();
            detailMasterBuku.getSemuaDataBerdasarkanIsbn(isbn, "toko");

            detailMasterBuku.getDataByIsbnAndVendorAndRak(isbn, vendorId, rackCode, "toko");

            if (detailMasterBuku.getResultSet().next()) {
                // ISBN, Vendor, Rak match ➤ Update stok
                int stokLama = detailMasterBuku.getResultSet().getInt("stock_buku");
                int stokBaru = stokLama + qtyReceived;

                detailMasterBuku.updateStokDanTanggal(
                    stokBaru,
                    isbn,
                    vendorId,
                    rackCode,
                    "toko"
                );


            } else {
                // Tidak ditemukan kombinasi lengkap ➤ Tambah entri baru
                String idDetailMasterbuku = generateIdInventory();
                int hargaSatuan = totalPrice.divide(BigDecimal.valueOf(totalPo)).intValue();

                detailMasterBuku.menambahkanBukuBaru(
                    idDetailMasterbuku,
                    isbn,
                    rackCode,
                    vendorId,
                    qtyReceived,
                    estimatedDate,
                    hargaSatuan,
                    "toko"
                );
            }

            handlePOReception(poId, isbn, estimatedDate, qtyReceived, totalPo, totalPrice, noteReceived);
            clearInputFields();

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            dataKeTabel();

            JOptionPane.showMessageDialog(this,
                totalDiterimaBaru == totalPo
                    ? "Semua Jumlah PO Berhasil diterima!"
                    : "Sebagian Jumlah PO Berhasil diterima! sebesar " + qtyReceived,
                "Info", JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            clearInputFields();
        }
    }//GEN-LAST:event_JButton_terimaPoActionPerformed
                                                 

    private void JTextfield_jumlahDiterimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_jumlahDiterimaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_jumlahDiterimaActionPerformed

    private void JButton_resetPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_resetPoActionPerformed
        JTextfield_idPo.setText("");
        JTextfield_jumlahDiterima.setText("");
        JTextarea_keteranganPenerimaan.setText("");
        
        JTextfield_idPo.setEnabled(true);
    }//GEN-LAST:event_JButton_resetPoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_keluar;
    private javax.swing.JButton JButton_resetPo;
    private javax.swing.JButton JButton_terimaPo;
    private javax.swing.JComboBox<String> JComboBox_rakBuku;
    private javax.swing.JTable JTable_po;
    private javax.swing.JTextField JTexfield_pencarianPO;
    private javax.swing.JTextArea JTextarea_keteranganPenerimaan;
    private javax.swing.JTextField JTextfield_idPo;
    private javax.swing.JTextField JTextfield_jumlahDiterima;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    // End of variables declaration//GEN-END:variables
}
