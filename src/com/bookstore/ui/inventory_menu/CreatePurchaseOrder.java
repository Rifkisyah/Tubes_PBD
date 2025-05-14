package com.bookstore.ui.inventory_menu;

import com.bookstore.data.EmployeeAccount;
import com.bookstore.data.QuerySelector;
import com.bookstore.data.SessionAccount;
import com.bookstore.ui.InventoryDashboardFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rifki
 */
public class CreatePurchaseOrder extends javax.swing.JFrame {
    InventoryDashboardFrame inventoryDashboardFrame;
    private DefaultTableModel model;
    QuerySelector querySelector;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form POManagement
     */
    public CreatePurchaseOrder(InventoryDashboardFrame inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Buat Purchase Order");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(CreatePurchaseOrder.this,
                        "Apakah Kamu Yakin Ingin Keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    CreatePurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    CreatePurchaseOrder.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    CreatePurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
        dataRoleToComboBox(vendor_combobox, "T_Vendor", "id_vendor", "nama_vendor");
        dataRoleToComboBox(ComboBoxFilterVendor, "T_Vendor", "id_vendor", "nama_vendor");
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
    
    private void dataDetailBookToTable(){
        this.querySelector = new QuerySelector();
        
        try{
            querySelector.selectDetailMasterBuku("vendor");
            
            model.setRowCount(0);
            
            while(querySelector.getResultSet().next()){
                Object[] fieldx = new Object[10];
                fieldx[0] = querySelector.getResultSet().getString("id_detail_master_buku");
                fieldx[1] = querySelector.getResultSet().getString("isbn");
                fieldx[2] = querySelector.getResultSet().getString("judul_buku");
                fieldx[3] = querySelector.getResultSet().getString("kode_rak");
                fieldx[4] = querySelector.getResultSet().getString("nama_rak");
                fieldx[5] = querySelector.getResultSet().getString("id_vendor");
                fieldx[6] = querySelector.getResultSet().getString("nama_vendor");
                fieldx[7] = querySelector.getResultSet().getString("stock_buku");
                fieldx[8] = querySelector.getResultSet().getString("tanggal_update_stock");
                fieldx[9] = querySelector.getResultSet().getString("harga_satuan");
                this.model.addRow(fieldx);
            }
            
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    private void setHeaderTable(){
        this.model = new DefaultTableModel();
        VendorStockBookTable.setModel(model);
        
        model.addColumn("ID Stock");
        model.addColumn("ISBN");
        model.addColumn("Judul Buku");
        model.addColumn("ID Rak");
        model.addColumn("Nama Rak");
        model.addColumn("ID Vendor");
        model.addColumn("Nama Vendor");
        model.addColumn("Jumlah Stock");
        model.addColumn("Tanggal Update Stock");
        model.addColumn("Harga Satuan");
        
        columnSizing();
        dataDetailBookToTable();
    }

   private void dataRoleToComboBox(JComboBox comboBox, String table, String targetIdColumn, String targetColumn) {
        try {
            querySelector.selectAllFromTable(table);

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
            while (querySelector.getResultSet().next()) {
                String id = querySelector.getResultSet().getString(targetIdColumn);
                String name = querySelector.getResultSet().getString(targetColumn);
                
                vendorNameToIdMap.put(name, id);

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(name)) {
                    comboBox.addItem(name);
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
                dataDetailBookToTable();
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
                        Object[] fieldx = new Object[10];
                        fieldx[0] = querySelector.getResultSet().getString("id_detail_master_buku");
                        fieldx[1] = querySelector.getResultSet().getString("isbn");
                        fieldx[2] = querySelector.getResultSet().getString("judul_buku");
                        fieldx[3] = querySelector.getResultSet().getString("kode_rak");
                        fieldx[4] = querySelector.getResultSet().getString("nama_rak");
                        fieldx[5] = querySelector.getResultSet().getString("id_vendor");
                        fieldx[6] = querySelector.getResultSet().getString("nama_vendor");
                        fieldx[7] = querySelector.getResultSet().getString("stock_buku");
                        fieldx[8] = querySelector.getResultSet().getString("tanggal_update_stock");
                        fieldx[9] = querySelector.getResultSet().getString("harga_satuan");
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

    
    private String generateNotaPO() {
        return "PO" + System.currentTimeMillis(); 
    }
    
    private void insertPurchaseOrder(String isbn, String idVendor, int jumlahPO, BigDecimal hargaSatuan) {
        try {
            EmployeeAccount employeeAccount = SessionAccount.getSessionAccount();
            String idPegawai = employeeAccount.getId();

            String notaPO = generateNotaPO();
            LocalDate tanggalPO = LocalDate.now();
            LocalDate estimasiDatang = tanggalPO.plusDays(7); // edit
            BigDecimal totalBiaya = hargaSatuan.multiply(BigDecimal.valueOf(jumlahPO));
            String statusPO = "Diproses";

            querySelector.insertPurchaseOrder(notaPO, idPegawai, idVendor, isbn, tanggalPO, estimasiDatang, jumlahPO, totalBiaya, statusPO);

            JOptionPane.showMessageDialog(this, "PO berhasil dibuat dengan Nota: " + notaPO, "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membuat PO!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void poCondition(String key){
        switch (key) {
            case "PoChecked" -> {
                // button
                CheckAvailableButton.setEnabled(false);
                CreateNewPOButton.setEnabled(true);
                CancelPOButton.setEnabled(true);
                CloseButton.setEnabled(false);
                // jtable
                VendorStockBookTable.setEnabled(false);
                // jtextfield
                search_field.setEditable(false);
            }
                
            case "PoCreated" -> {
                // button
                CheckAvailableButton.setEnabled(true);
                CreateNewPOButton.setEnabled(false);
                CancelPOButton.setEnabled(false);
                CloseButton.setEnabled(true);
                // jtable
                VendorStockBookTable.setEnabled(true);
                // jtextfield
                search_field.setEditable(true);
                isbn_field.setText("");
                book_title_field.setText("");
                vendor_combobox.setSelectedIndex(0);
                price_field.setText("");
                total_book_field.setText("");
            }
                
            case "PoCanceled" -> {
                // button
                CheckAvailableButton.setEnabled(true);
                CreateNewPOButton.setEnabled(false);
                CancelPOButton.setEnabled(false);
                CloseButton.setEnabled(true);
                // jtable
                VendorStockBookTable.setEnabled(true);
                // jtextfield
                search_field.setEditable(true);
                isbn_field.setText("");
                book_title_field.setText("");
                vendor_combobox.setSelectedIndex(0);
                price_field.setText("");
                total_book_field.setText("");
            }
                
            default -> {
            }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        search_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        VendorStockBookTable = new javax.swing.JTable();
        ComboBoxFilterVendor = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        vendor_combobox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        price_field = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CreateNewPOButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TotalPriceLabel = new javax.swing.JLabel();
        isbn_field = new javax.swing.JTextField();
        CheckAvailableButton = new javax.swing.JButton();
        book_title_field = new javax.swing.JTextField();
        CloseButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        CancelPOButton = new javax.swing.JButton();
        total_book_field = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(148, 180, 193));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 800));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 52, 72));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Purchase Order");

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Cari Buku :");

        search_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });

        VendorStockBookTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        VendorStockBookTable.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
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
        VendorStockBookTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VendorStockBookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VendorStockBookTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(VendorStockBookTable);

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
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(84, 119, 146));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Vendor");

        vendor_combobox.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        vendor_combobox.setMaximumRowCount(100);
        vendor_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Vendor Belum Dipilih --" }));

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Harga Satuan");

        price_field.setEditable(false);
        price_field.setBackground(new java.awt.Color(204, 204, 204));
        price_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        price_field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Buat Purchase Order");

        CreateNewPOButton.setBackground(new java.awt.Color(0, 153, 0));
        CreateNewPOButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CreateNewPOButton.setForeground(new java.awt.Color(255, 255, 255));
        CreateNewPOButton.setText("Buat PO");
        CreateNewPOButton.setEnabled(false);
        CreateNewPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateNewPOButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Judul Buku");

        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Harga");

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("ISBN");

        TotalPriceLabel.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        TotalPriceLabel.setForeground(new java.awt.Color(255, 255, 255));
        TotalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TotalPriceLabel.setText("Rp. 0");

        isbn_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        CheckAvailableButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CheckAvailableButton.setText("Cek Ketersediaan Buku");
        CheckAvailableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckAvailableButtonActionPerformed(evt);
            }
        });

        book_title_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

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
        jLabel7.setText("Jumlah Buku");

        CancelPOButton.setBackground(new java.awt.Color(204, 0, 51));
        CancelPOButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CancelPOButton.setForeground(new java.awt.Color(255, 255, 255));
        CancelPOButton.setText("Batal PO");
        CancelPOButton.setEnabled(false);
        CancelPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelPOButtonActionPerformed(evt);
            }
        });

        total_book_field.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(price_field, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(vendor_combobox, 0, 249, Short.MAX_VALUE))
                                    .addComponent(total_book_field, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(CheckAvailableButton)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TotalPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CreateNewPOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CancelPOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(book_title_field, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(isbn_field, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(204, 204, 204)))))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isbn_field)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(book_title_field)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vendor_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(price_field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(TotalPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_book_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckAvailableButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateNewPOButton)
                    .addComponent(CloseButton)
                    .addComponent(CancelPOButton))
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
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1329, Short.MAX_VALUE)
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

    private void VendorStockBookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VendorStockBookTableMouseClicked
        isbn_field.setText(model.getValueAt(VendorStockBookTable.getSelectedRow(), 1) + "");
        book_title_field.setText(model.getValueAt(VendorStockBookTable.getSelectedRow(), 2) + "");
        price_field.setText(model.getValueAt(VendorStockBookTable.getSelectedRow(), 9) + "");
        total_book_field.setText(model.getValueAt(VendorStockBookTable.getSelectedRow(), 7) + "");
        
        VendorStockBookTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = VendorStockBookTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Ambil nilai dari kolom "Nama Negara"
                    String valueBox = VendorStockBookTable.getValueAt(selectedRow, 6).toString();

                    // Loop item di comboBox dan set selectedIndex
                    for (int i = 0; i < vendor_combobox.getItemCount(); i++) {
                        if (valueBox.equals(vendor_combobox.getItemAt(i))) {
                            vendor_combobox.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }//GEN-LAST:event_VendorStockBookTableMouseClicked

    private void ComboBoxFilterVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFilterVendorActionPerformed
        if(this.filterComboBoxIntialize){
            String filterVendorName = (String) ComboBoxFilterVendor.getSelectedItem();
            search_field.setText("");
        
            if (filterVendorName == null || filterVendorName.equals("-- Pilih Filter Vendor --")) {
                dataDetailBookToTable();
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
                                Object[] fieldx = new Object[10];
                                fieldx[0] = querySelector.getResultSet().getString("id_detail_master_buku");
                                fieldx[1] = querySelector.getResultSet().getString("isbn");
                                fieldx[2] = querySelector.getResultSet().getString("judul_buku");
                                fieldx[3] = querySelector.getResultSet().getString("kode_rak");
                                fieldx[4] = querySelector.getResultSet().getString("nama_rak");
                                fieldx[5] = querySelector.getResultSet().getString("id_vendor");
                                fieldx[6] = querySelector.getResultSet().getString("nama_vendor");
                                fieldx[7] = querySelector.getResultSet().getString("stock_buku");
                                fieldx[8] = querySelector.getResultSet().getString("tanggal_update_stock");
                                fieldx[9] = querySelector.getResultSet().getString("harga_satuan");
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
    }//GEN-LAST:event_ComboBoxFilterVendorActionPerformed

    private void CancelPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelPOButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Membatalkan Proses Purchase Order?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

            poCondition("PoCanceled");

            JOptionPane.showMessageDialog(this, "PO Berhasil Dibatalkan", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_CancelPOButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void CheckAvailableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckAvailableButtonActionPerformed
        String isbn = isbn_field.getText().trim();
        String title = book_title_field.getText().trim();
        String vendorName = (String) vendor_combobox.getSelectedItem();
        
        String vendorId = null;

        // Validasi input
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
            if(vendorName.equals("-- Vendor Belum Dipilih --")){
                JOptionPane.showMessageDialog(this, "Vendor Belum Dipilih!", "info", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                vendorId = this.vendorNameToIdMap.get(vendorName);
            }

            if (vendorId == null) {
                JOptionPane.showMessageDialog(this, "Vendor tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ambil stok dan harga buku berdasarkan isbn, id_vendor, dan jenis_inventaris
            querySelector.selectTwoColumnsByThreeKeys(
                "stock_buku", "harga_satuan",
                "T_DetailMasterbuku",
                "isbn", isbn,
                "id_vendor", vendorId,
                "jenis_inventaris", "vendor"
            );

            ResultSet bookResult = querySelector.getResultSet();
            if (bookResult.next()) {
                int currentStock = bookResult.getInt("stock_buku");
                BigDecimal hargaSatuan = bookResult.getBigDecimal("harga_satuan");

                if (currentStock <= 0) {
                    JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (currentStock < totalPo) {
                    JOptionPane.showMessageDialog(this, "Stok Buku Tidak Cukup!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BigDecimal grandTotalPO;
                grandTotalPO = hargaSatuan.multiply(BigDecimal.valueOf(totalPo));

                // Update UI
                price_field.setText(hargaSatuan.toString());
                TotalPriceLabel.setText(grandTotalPO.toString());

                poCondition("PoChecked");

                JOptionPane.showMessageDialog(this, "Buku Tersedia! Silakan lanjutkan ke PO.", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } else {
                // Tidak ada hasil dari query buku
                JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat akses database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CheckAvailableButtonActionPerformed

    // fixme
    private void CreateNewPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateNewPOButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Membuat Purchase Order?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            String isbn = isbn_field.getText().trim();
            String vendorName = (String) vendor_combobox.getSelectedItem();
            int jumlahPO;
            BigDecimal hargaSatuan;

            // Validasi input jumlah PO dan harga satuan
            try {
                jumlahPO = Integer.parseInt(total_book_field.getText().trim());
                hargaSatuan = new BigDecimal(price_field.getText().trim());

                if (jumlahPO <= 0 || hargaSatuan.compareTo(BigDecimal.ZERO) <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah PO dan harga harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Harga atau jumlah PO tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Ambil id_vendor dari nama vendor
                querySelector.selectOneColumnByOneKey("id_vendor", "T_Vendor", "nama_vendor", vendorName);
                String idVendor = null;

                ResultSet vendorResult = querySelector.getResultSet();
                if (vendorResult.next()) {
                    idVendor = vendorResult.getString("id_vendor");
                }

                if (idVendor == null) {
                    JOptionPane.showMessageDialog(this, "Vendor tidak ditemukan!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Ambil stok buku berdasarkan ISBN dan ID Vendor
                querySelector.selectOneColumnByTwoKeys("stock_buku", "T_DetailMasterbuku", "isbn", isbn, "id_vendor", idVendor);

                ResultSet stockResult = querySelector.getResultSet();
                if (!stockResult.next()) {
                    JOptionPane.showMessageDialog(this, "Data stok tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int currentStock = stockResult.getInt("stock_buku");

                int newStock = currentStock - jumlahPO;
                if (newStock < 0) {
                    JOptionPane.showMessageDialog(this, "Stok tidak mencukupi saat proses update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update stok dan tanggal
                querySelector.updateStockAndDate(
                    "T_DetailMasterbuku",
                    "stock_buku", newStock,
                    "tanggal_update_stock", java.sql.Date.valueOf(java.time.LocalDate.now()),
                    "isbn", isbn,
                    "id_vendor", idVendor
                );

                // Masukkan data PO baru
                insertPurchaseOrder(isbn, idVendor, jumlahPO, hargaSatuan);

                // Update UI
                poCondition("PoCreated");

                JOptionPane.showMessageDialog(this, "Purchase Order berhasil dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat proses PO!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_CreateNewPOButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelPOButton;
    private javax.swing.JButton CheckAvailableButton;
    private javax.swing.JButton CloseButton;
    private javax.swing.JComboBox<String> ComboBoxFilterVendor;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField price_field;
    private javax.swing.JTextField search_field;
    private javax.swing.JTextField total_book_field;
    private javax.swing.JComboBox<String> vendor_combobox;
    // End of variables declaration//GEN-END:variables
}
