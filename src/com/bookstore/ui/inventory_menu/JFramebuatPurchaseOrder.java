package com.bookstore.ui.inventory_menu;

import com.bookstore.data.SesiAkunPegawai;
import com.bookstore.model.AkunPegawai;
import com.bookstore.model.DetailMasterBuku;
import com.bookstore.model.PurchaseOrder;
import com.bookstore.model.Vendor;
import com.bookstore.ui.JFrameGudang;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rifki
 */
public class JFrameBuatPurchaseOrder extends javax.swing.JFrame {
    JFrameGudang inventoryDashboardFrame;
    private DefaultTableModel model;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> vendorNameToIdMap = new HashMap<>();
    /**
     * Creates new form POManagement
     */
    
    private final WindowListener defaultWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showConfirmDialog(JFrameBuatPurchaseOrder.this,
                    "Apakah Kamu Yakin Ingin Keluar?",
                    "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Aksi keluar jika user setuju
                JFrameBuatPurchaseOrder.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrameBuatPurchaseOrder.this.dispose();
                inventoryDashboardFrame.setEnabled(true);
                inventoryDashboardFrame.requestFocus();
            } else {
                // Jangan lakukan apapun (biarkan frame tetap terbuka)
                JFrameBuatPurchaseOrder.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }
    };


    private final WindowListener poCheckedWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            JOptionPane.showMessageDialog(JFrameBuatPurchaseOrder.this,
                    "Tidak Bisa Keluar, Sedang Proses PO",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            // Tidak melakukan apapun supaya jendela tidak tertutup
        }
    };

    
    public JFrameBuatPurchaseOrder(JFrameGudang inventoryDashboardFrame) {
        this.inventoryDashboardFrame = inventoryDashboardFrame;
        
        this.addWindowListener(defaultWindowListener);
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Buat Purchase Order");
        
        mengaturHeaderTabel();
        dataKeComboBox(JComboBox_vendor);
        dataKeComboBox(JComboBox_filterVendor);
        this.filterComboBoxIntialize = true;
        
        JTextfield_pencarian.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(JTextfield_pencarian.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(JTextfield_pencarian.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(JTextfield_pencarian.getText().trim());
            }
            
        });
    }
    
    private void mengaturUkuranKolom(){
        TableColumn column;
        JTable_bukuVendor.setAutoResizeMode(JTable_bukuVendor.AUTO_RESIZE_OFF);
        
        column = JTable_bukuVendor.getColumnModel().getColumn(0); //ID stock
        column.setPreferredWidth(120);
        column = JTable_bukuVendor.getColumnModel().getColumn(1); //isbn
        column.setPreferredWidth(120);
        column = JTable_bukuVendor.getColumnModel().getColumn(2); // judul buku
        column.setPreferredWidth(200);
        column = JTable_bukuVendor.getColumnModel().getColumn(3); // kode rak
        column.setPreferredWidth(120);
        column = JTable_bukuVendor.getColumnModel().getColumn(4); // nama rak
        column.setPreferredWidth(200);
        column = JTable_bukuVendor.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(120);
        column = JTable_bukuVendor.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = JTable_bukuVendor.getColumnModel().getColumn(7); // jumlah stock
        column.setPreferredWidth(100);
        column = JTable_bukuVendor.getColumnModel().getColumn(8); // tanggal update stock
        column.setPreferredWidth(150);
        column = JTable_bukuVendor.getColumnModel().getColumn(9); // harga satuan
        column.setPreferredWidth(200);
    }
    
    private void dataKeTabel(){
        DetailMasterBuku detailMasterBuku = new DetailMasterBuku();
        
        try{
            detailMasterBuku.getDetailMasterbukuBerdasarkanTipe("vendor");
            model.setRowCount(0);
            
            while(detailMasterBuku.getResultSet().next()){
                Object[] fieldx = new Object[10];
                fieldx[0] = detailMasterBuku.getResultSet().getString("id_detail_master_buku");
                fieldx[1] = detailMasterBuku.getResultSet().getString("isbn");
                fieldx[2] = detailMasterBuku.getResultSet().getString("judul_buku");
                fieldx[3] = detailMasterBuku.getResultSet().getString("kode_rak");
                fieldx[4] = detailMasterBuku.getResultSet().getString("nama_rak");
                fieldx[5] = detailMasterBuku.getResultSet().getString("id_vendor");
                fieldx[6] = detailMasterBuku.getResultSet().getString("nama_vendor");
                fieldx[7] = detailMasterBuku.getResultSet().getString("stock_buku");
                fieldx[8] = detailMasterBuku.getResultSet().getString("tanggal_update_stock");
                fieldx[9] = detailMasterBuku.getResultSet().getString("harga_satuan");
                this.model.addRow(fieldx);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void mengaturHeaderTabel(){
        this.model = new DefaultTableModel();
        JTable_bukuVendor.setModel(model);
        
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
        
        mengaturUkuranKolom();
        dataKeTabel();
    }

   private void dataKeComboBox(JComboBox comboBox) {
       Vendor vendor = new Vendor();
       
       try {
           vendor.getSemuaDataVendor();

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
            while (vendor.getResultSet().next()) {
                String id = vendor.getResultSet().getString("id_vendor");
                String name = vendor.getResultSet().getString("nama_vendor");
                
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
        
        DetailMasterBuku detailMasterBuku = new DetailMasterBuku();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataKeTabel();
            } else {
                String vendorName = (String) JComboBox_filterVendor.getSelectedItem();
                
                if(vendorName.equals("-- Pilih Filter Role --")){
                    detailMasterBuku.getPencarianDataTabelMasterBuku(search_value, "vendor");
                    
                } else {
                    String vendorId = this.vendorNameToIdMap.get(vendorName);

                    if (vendorId == null || vendorId.trim().isEmpty()) {
                        detailMasterBuku.getPencarianDataTabelMasterBuku(search_value, "vendor");
                    } else {
                        detailMasterBuku.getPencarianDataTabelBerdasarkanFilterVendor(search_value, vendorId, "vendor");
                    }
                }

                if (detailMasterBuku.getResultSet().isBeforeFirst()) {
                    while (detailMasterBuku.getResultSet().next()) {
                    Object[] fieldx = new Object[10];
                        fieldx[0] = detailMasterBuku.getResultSet().getString("id_detail_master_buku");
                        fieldx[1] = detailMasterBuku.getResultSet().getString("isbn");
                        fieldx[2] = detailMasterBuku.getResultSet().getString("judul_buku");
                        fieldx[3] = detailMasterBuku.getResultSet().getString("kode_rak");
                        fieldx[4] = detailMasterBuku.getResultSet().getString("nama_rak");
                        fieldx[5] = detailMasterBuku.getResultSet().getString("id_vendor");
                        fieldx[6] = detailMasterBuku.getResultSet().getString("nama_vendor");
                        fieldx[7] = detailMasterBuku.getResultSet().getString("stock_buku");
                        fieldx[8] = detailMasterBuku.getResultSet().getString("tanggal_update_stock");
                        fieldx[9] = detailMasterBuku.getResultSet().getString("harga_satuan");
                        model.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pegawai Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String generateIdPO() {
        PurchaseOrder purchaseOrder = new PurchaseOrder(); // Ganti sesuai class PO kamu
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMddHHmm"); // contoh: 2505281435 (10 digit)
        String timestamp = LocalDateTime.now().format(dateFormatter); 
        String prefix = "PO" + timestamp; // "PO2505281435" = 12 karakter

        int counter = 1;
        String newId;

        while (true) {
            String counterStr = String.format("%03d", counter); // 3 digit (001, 002, ...)
            newId = prefix + counterStr; // total: 12 + 3 = 15 karakter
            if (!purchaseOrder.cekIdPOSudahAda(newId)) {
                break;
            }
            counter++;
        }

        return newId; // contoh hasil: PO2505281435001
    }

    
    private void insertPurchaseOrder(String isbn, String idVendor, int jumlahPO, BigDecimal hargaSatuan) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        
        AkunPegawai akunPegawai = SesiAkunPegawai.getSesiAkunPegawai();
        String idPegawai = akunPegawai.getIdPegawai();
        
        String idPO = generateIdPO();
        BigDecimal totalBiaya = hargaSatuan.multiply(BigDecimal.valueOf(jumlahPO));
        String statusPO = "Diproses";
        int jumlah_diterima = 0;
        
        purchaseOrder.menambahkanPO(idPO, idPegawai, idVendor, isbn, jumlahPO, jumlah_diterima, totalBiaya, statusPO);
        JOptionPane.showMessageDialog(this, "PO berhasil dibuat dengan Nomor PO : " + idPO, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void poCondition(String key){
        // Bersihkan listener lama
        for (WindowListener wl : this.getWindowListeners()) {
            this.removeWindowListener(wl);
        }

        switch (key) {
            case "PoChecked" -> {
                this.addWindowListener(poCheckedWindowListener);

                JButton_cekKetersediaan.setEnabled(false);
                JButton_buatPO.setEnabled(true);
                JButton_batalBuatPO.setEnabled(true);
                JButton_keluar.setEnabled(false);
                JTable_bukuVendor.setEnabled(false);
                JTextfield_pencarian.setEditable(false);
                JButton_resetInput.setEnabled(false);
            }

            case "PoCreated", "PoCanceled" -> {
                this.addWindowListener(defaultWindowListener);

                JButton_cekKetersediaan.setEnabled(true);
                JButton_buatPO.setEnabled(false);
                JButton_batalBuatPO.setEnabled(false);
                JButton_keluar.setEnabled(true);
                JTable_bukuVendor.setEnabled(true);
                JTextfield_pencarian.setEditable(true);
                JTextfield_isbn.setText("");
                JTextfield_judulBuku.setText("");
                JComboBox_vendor.setSelectedIndex(0);
                JTexfield_hargaSatuanBuku.setText("");
                JTextfield_jumlahBuku.setText("");
                TotalPriceLabel.setText("Rp. 0");
                JButton_resetInput.setEnabled(true);
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
        JTextfield_pencarian = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_bukuVendor = new javax.swing.JTable();
        JComboBox_filterVendor = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        JComboBox_vendor = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        JTexfield_hargaSatuanBuku = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        JButton_buatPO = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TotalPriceLabel = new javax.swing.JLabel();
        JTextfield_isbn = new javax.swing.JTextField();
        JButton_cekKetersediaan = new javax.swing.JButton();
        JTextfield_judulBuku = new javax.swing.JTextField();
        JButton_keluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        JButton_batalBuatPO = new javax.swing.JButton();
        JTextfield_jumlahBuku = new javax.swing.JTextField();
        JButton_resetInput = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(148, 180, 193));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 800));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 52, 72));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Buat Purchase Order");

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Cari Buku Dari Vendor :");

        JTextfield_pencarian.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        JTextfield_pencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_pencarianActionPerformed(evt);
            }
        });

        JTable_bukuVendor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        JTable_bukuVendor.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JTable_bukuVendor.setModel(new javax.swing.table.DefaultTableModel(
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
        JTable_bukuVendor.setAlignmentY(1.0F);
        JTable_bukuVendor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JTable_bukuVendor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_bukuVendorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_bukuVendor);

        JComboBox_filterVendor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Filter Vendor --" }));
        JComboBox_filterVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox_filterVendorActionPerformed(evt);
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
                        .addComponent(JTextfield_pencarian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JComboBox_filterVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_pencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JComboBox_filterVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        JComboBox_vendor.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        JComboBox_vendor.setMaximumRowCount(100);
        JComboBox_vendor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Vendor Belum Dipilih --" }));

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Harga Satuan");

        JTexfield_hargaSatuanBuku.setEditable(false);
        JTexfield_hargaSatuanBuku.setBackground(new java.awt.Color(204, 204, 204));
        JTexfield_hargaSatuanBuku.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        JTexfield_hargaSatuanBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Formulir Pembuatan Purchase Order");

        JButton_buatPO.setBackground(new java.awt.Color(0, 153, 0));
        JButton_buatPO.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_buatPO.setForeground(new java.awt.Color(255, 255, 255));
        JButton_buatPO.setText("Buat PO");
        JButton_buatPO.setEnabled(false);
        JButton_buatPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_buatPOActionPerformed(evt);
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

        JTextfield_isbn.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        JButton_cekKetersediaan.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_cekKetersediaan.setText("Cek Ketersediaan Buku");
        JButton_cekKetersediaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_cekKetersediaanActionPerformed(evt);
            }
        });

        JTextfield_judulBuku.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

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
        jLabel7.setText("Jumlah Buku");

        JButton_batalBuatPO.setBackground(new java.awt.Color(204, 0, 51));
        JButton_batalBuatPO.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_batalBuatPO.setForeground(new java.awt.Color(255, 255, 255));
        JButton_batalBuatPO.setText("Batal PO");
        JButton_batalBuatPO.setEnabled(false);
        JButton_batalBuatPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_batalBuatPOActionPerformed(evt);
            }
        });

        JTextfield_jumlahBuku.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        JButton_resetInput.setBackground(new java.awt.Color(153, 0, 0));
        JButton_resetInput.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        JButton_resetInput.setForeground(new java.awt.Color(255, 255, 255));
        JButton_resetInput.setText("Reset");
        JButton_resetInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_resetInputActionPerformed(evt);
            }
        });

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
                                        .addComponent(JTexfield_hargaSatuanBuku, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(JComboBox_vendor, 0, 249, Short.MAX_VALUE))
                                    .addComponent(JTextfield_jumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(JButton_cekKetersediaan)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TotalPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(JButton_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JButton_resetInput, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JButton_buatPO, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(JButton_batalBuatPO, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTextfield_judulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTextfield_isbn, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(JTextfield_isbn)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextfield_judulBuku)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JComboBox_vendor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JTexfield_hargaSatuanBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(TotalPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTextfield_jumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JButton_cekKetersediaan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_buatPO)
                    .addComponent(JButton_keluar)
                    .addComponent(JButton_batalBuatPO)
                    .addComponent(JButton_resetInput))
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

    private void JTextfield_pencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_pencarianActionPerformed
        String search_value = JTextfield_pencarian.getText().trim();
        performSearch(search_value); 
    }//GEN-LAST:event_JTextfield_pencarianActionPerformed

    private void JTable_bukuVendorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_bukuVendorMouseClicked
        int selectedRow = JTable_bukuVendor.getSelectedRow();
        
        if(selectedRow >= 0){
            JTextfield_isbn.setText(model.getValueAt(JTable_bukuVendor.getSelectedRow(), 1) + "");
            JTextfield_judulBuku.setText(model.getValueAt(JTable_bukuVendor.getSelectedRow(), 2) + "");
            JTexfield_hargaSatuanBuku.setText(model.getValueAt(JTable_bukuVendor.getSelectedRow(), 9) + "");
            JTextfield_jumlahBuku.setText(model.getValueAt(JTable_bukuVendor.getSelectedRow(), 7) + "");
            
            String valueBox = JTable_bukuVendor.getValueAt(selectedRow, 6).toString();

            for (int i = 0; i < JComboBox_vendor.getItemCount(); i++) {
                if (valueBox.equals(JComboBox_vendor.getItemAt(i))) {
                    JComboBox_vendor.setSelectedIndex(i);
                    break;
                }
            }
        }
    }//GEN-LAST:event_JTable_bukuVendorMouseClicked

    private void JComboBox_filterVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboBox_filterVendorActionPerformed
        if(this.filterComboBoxIntialize){
            String filterVendorName = (String) JComboBox_filterVendor.getSelectedItem();
            JTextfield_pencarian.setText("");
            DetailMasterBuku detailMasterBuku = new DetailMasterBuku();
        
            if (filterVendorName == null || filterVendorName.equals("-- Pilih Filter Vendor --")) {
                dataKeTabel();
                return;
            } else {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                try {
                    if (filterVendorName.trim().isEmpty()) {
                        performSearch(filterVendorName);
                    } else {
                        detailMasterBuku.getFilterVendor(filterVendorName, "vendor");

                        if (detailMasterBuku.getResultSet().isBeforeFirst()) {
                            while (detailMasterBuku.getResultSet().next()) {
                                Object[] fieldx = new Object[10];
                                fieldx[0] = detailMasterBuku.getResultSet().getString("id_detail_master_buku");
                                fieldx[1] = detailMasterBuku.getResultSet().getString("isbn");
                                fieldx[2] = detailMasterBuku.getResultSet().getString("judul_buku");
                                fieldx[3] = detailMasterBuku.getResultSet().getString("kode_rak");
                                fieldx[4] = detailMasterBuku.getResultSet().getString("nama_rak");
                                fieldx[5] = detailMasterBuku.getResultSet().getString("id_vendor");
                                fieldx[6] = detailMasterBuku.getResultSet().getString("nama_vendor");
                                fieldx[7] = detailMasterBuku.getResultSet().getString("stock_buku");
                                fieldx[8] = detailMasterBuku.getResultSet().getString("tanggal_update_stock");
                                fieldx[9] = detailMasterBuku.getResultSet().getString("harga_satuan");
                                this.model.addRow(fieldx);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Gagal Mencari Buku", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_JComboBox_filterVendorActionPerformed

    private void JButton_batalBuatPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_batalBuatPOActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Membatalkan Proses Purchase Order?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

            poCondition("PoCanceled");

            JOptionPane.showMessageDialog(this, "PO Berhasil Dibatalkan", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_JButton_batalBuatPOActionPerformed

    private void JButton_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_JButton_keluarActionPerformed

    private void JButton_cekKetersediaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_cekKetersediaanActionPerformed
        String isbn = JTextfield_isbn.getText().trim();
        String title = JTextfield_judulBuku.getText().trim();
        String vendorName = (String) JComboBox_vendor.getSelectedItem();
        
        String vendorId = null;
        DetailMasterBuku detailMasterBuku = new DetailMasterBuku();

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
        
        try {
            int totalPo = Integer.parseInt(JTextfield_jumlahBuku.getText().trim());
            if (totalPo <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah PO harus lebih dari 0!", "Terjadi Masalah", JOptionPane.ERROR_MESSAGE);
                return;
            }

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

            detailMasterBuku.getStokDanHargaSatuan(isbn, vendorId, "vendor");

            if (detailMasterBuku.getResultSet().next()) {
                int currentStock = detailMasterBuku.getResultSet().getInt("stock_buku");
                BigDecimal hargaSatuan = detailMasterBuku.getResultSet().getBigDecimal("harga_satuan");

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
                JTexfield_hargaSatuanBuku.setText(hargaSatuan.toString());
                TotalPriceLabel.setText(grandTotalPO.toString());

                poCondition("PoChecked");

                JOptionPane.showMessageDialog(this, "Buku Tersedia! Silakan lanjutkan ke PO.", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } else {
                // Tidak ada hasil dari query buku
                JOptionPane.showMessageDialog(this, "Buku Tidak Ditemukan!", "Terjadi Masalah Ketika PO", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat akses database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_JButton_cekKetersediaanActionPerformed

    // fixme
    private void JButton_buatPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_buatPOActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Membuat Purchase Order?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            String isbn = JTextfield_isbn.getText().trim();
            String vendorName = (String) JComboBox_vendor.getSelectedItem();
            int jumlahPO;
            BigDecimal hargaSatuan;
                
            DetailMasterBuku detailMasterBuku = new DetailMasterBuku();
            Vendor vendor = new Vendor();

            // Validasi input jumlah PO dan harga satuan
            try {
                jumlahPO = Integer.parseInt(JTextfield_jumlahBuku.getText().trim());
                hargaSatuan = new BigDecimal(JTexfield_hargaSatuanBuku.getText().trim());

                if (jumlahPO <= 0 || hargaSatuan.compareTo(BigDecimal.ZERO) <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah PO dan harga harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Harga atau jumlah PO tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                vendor.getIdVendor(vendorName);

                String idVendor = null;

                if (vendor.getResultSet().next()) {
                    idVendor = vendor.getResultSet().getString("id_vendor");
                }

                if (idVendor == null) {
                    JOptionPane.showMessageDialog(this, "Vendor tidak ditemukan!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                detailMasterBuku.getStokDanHargaSatuan(isbn, idVendor, "vendor");

                if (!detailMasterBuku.getResultSet().next()) {
                    JOptionPane.showMessageDialog(this, "Data stok tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int currentStock = detailMasterBuku.getResultSet().getInt("stock_buku");

                int newStock = currentStock - jumlahPO;
                if (newStock < 0) {
                    JOptionPane.showMessageDialog(this, "Stok tidak mencukupi saat proses update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                detailMasterBuku.updateStokDanTanggal(newStock, isbn, idVendor, "vendor");

                // Masukkan data PO baru
                insertPurchaseOrder(isbn, idVendor, jumlahPO, hargaSatuan);

                // Update UI
                poCondition("PoCreated");

//                JOptionPane.showMessageDialog(this, "Purchase Order berhasil dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat proses PO!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_JButton_buatPOActionPerformed

    private void JButton_resetInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_resetInputActionPerformed
        JTextfield_isbn.setText("");
        JTextfield_judulBuku.setText("");
        JComboBox_vendor.setSelectedIndex(0);
        JTexfield_hargaSatuanBuku.setText("");
        JTextfield_jumlahBuku.setText("");

        JButton_buatPO.setEnabled(false);
        JButton_batalBuatPO.setEnabled(false);
        JButton_cekKetersediaan.setEnabled(true);
    }//GEN-LAST:event_JButton_resetInputActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_batalBuatPO;
    private javax.swing.JButton JButton_buatPO;
    private javax.swing.JButton JButton_cekKetersediaan;
    private javax.swing.JButton JButton_keluar;
    private javax.swing.JButton JButton_resetInput;
    private javax.swing.JComboBox<String> JComboBox_filterVendor;
    private javax.swing.JComboBox<String> JComboBox_vendor;
    private javax.swing.JTable JTable_bukuVendor;
    private javax.swing.JTextField JTexfield_hargaSatuanBuku;
    private javax.swing.JTextField JTextfield_isbn;
    private javax.swing.JTextField JTextfield_judulBuku;
    private javax.swing.JTextField JTextfield_jumlahBuku;
    private javax.swing.JTextField JTextfield_pencarian;
    private javax.swing.JLabel TotalPriceLabel;
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
    // End of variables declaration//GEN-END:variables
}
