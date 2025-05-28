/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.administrator_menu;

import com.bookstore.model.AkunPegawai;
import com.bookstore.model.Role;
import com.bookstore.ui.JFrameAdmin;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JComboBox;
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
public class JFrameMaintenancePegawai extends javax.swing.JFrame {
    public DefaultTableModel model;
    JFrameAdmin superAdminDashboardFrame;
    private boolean filterComboBoxIntialize = false;
    private final HashMap<String, String> roleNameToIdMap = new HashMap<>();
    /**
     * Creates new form MaintenancePegawai
     * @param superAdminDashboardFrame
     */
    public JFrameMaintenancePegawai(JFrameAdmin superAdminDashboardFrame) {
        this.superAdminDashboardFrame = superAdminDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Maintenance Pegawai");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(JFrameMaintenancePegawai.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Aplikasi?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    JFrameMaintenancePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JFrameMaintenancePegawai.this.setVisible(false);
                    superAdminDashboardFrame.setEnabled(true);
                    superAdminDashboardFrame.requestFocus();
                } else {
                    JFrameMaintenancePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        aturHeaderTabel();
        dataKeCombobox(JComboBox_rolePegawai);
        dataKeCombobox(JComboBox_filterRole);
        this.filterComboBoxIntialize = true;
        
        JTextfield_searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchField.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchField.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(JTextfield_searchField.getText().trim());
            }
            
        });
    }
    
    public void dataKeTabel(){
        AkunPegawai akunPegawai = new AkunPegawai();

        try{
            akunPegawai.getSemuaDataPegawaiDanRole();
            
            model.setRowCount(0);
            
            while(akunPegawai.getResultSet().next()){
                Object[] fieldx = new Object[7];
                    fieldx[0] = akunPegawai.getResultSet().getString("Id_Pegawai");
                    fieldx[1] = akunPegawai.getResultSet().getString("Nama");
                    fieldx[2] = akunPegawai.getResultSet().getString("password");
                    fieldx[3] = akunPegawai.getResultSet().getString("Id_Role");
                    fieldx[4] = akunPegawai.getResultSet().getString("Nama_Role");
                    fieldx[5] = akunPegawai.getResultSet().getString("Tanggal_Buat_Akun");
                    fieldx[6] = akunPegawai.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
                    model.addRow(fieldx);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("data ke tabel gagal");
        }
    }
    
    private void aturUkuranKolomTabel(){
        TableColumn column;
        JTable_Pegawai.setAutoResizeMode(JTable_Pegawai.AUTO_RESIZE_OFF);
        column = JTable_Pegawai.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = JTable_Pegawai.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = JTable_Pegawai.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = JTable_Pegawai.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = JTable_Pegawai.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = JTable_Pegawai.getColumnModel().getColumn(5);
        column.setPreferredWidth(200);
        column = JTable_Pegawai.getColumnModel().getColumn(6);
        column.setPreferredWidth(200);
    }
    
    public void aturHeaderTabel(){
        model = new DefaultTableModel();
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Password");
        model.addColumn("Role Id");
        model.addColumn("Nama Role");
        model.addColumn("Tanggal Buat Akun");
        model.addColumn("Tanggal Terakhir Masuk Akun");
        JTable_Pegawai.setModel(model);
        aturUkuranKolomTabel();
        dataKeTabel();
    }
    
    private void dataKeCombobox(JComboBox comboBox) {
        Role role = new Role();
        
        try {
            role.getSemuaDataRole();

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
            while (role.getResultSet().next()) {
                String id = role.getResultSet().getString("id_role");
                String name = role.getResultSet().getString("nama_role");
                
                roleNameToIdMap.put(name, id);

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(name)) {
                    comboBox.addItem(name);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void meresetInput(){
        JTextfield_idPegawai.setText("");
        JTextfield_namaPegawai.setText("");
        JTextfield_passwordPegawai.setText("");
        JTextfield_konfirmasiPassword.setText("");
        
        JComboBox_filterRole.setSelectedIndex(0);
        JComboBox_rolePegawai.setSelectedIndex(0);
    }

    private void deleteData() {
        if (JOptionPane.showConfirmDialog(this, "Apakah Yakin Akan Dihapus?", "Informasi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String idPegawai = JTextfield_idPegawai.getText().trim();
            AkunPegawai akunPegawai = new AkunPegawai();
            
            akunPegawai.deleteDataBerdasarkanIdPegawai(idPegawai);
            if (akunPegawai.getAffectedRow() > 0) {
                meresetInput();
                JTextfield_idPegawai.requestFocus();
                dataKeTabel();
                
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan / Gagal Dihapus", "Info", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pegawai Batal Dihapus");
            JTextfield_idPegawai.requestFocus();
        }
    }
    
    private void cekValidasiPassword(){
        String pass = JTextfield_passwordPegawai.getText();
        String konfPass = JTextfield_konfirmasiPassword.getText();
        if(pass.equals(konfPass)){
            JComboBox_rolePegawai.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "validasi password salah!", "Pesan", JOptionPane.ERROR_MESSAGE);
            JTextfield_konfirmasiPassword.requestFocus();
        }
    }
    
    private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        AkunPegawai akunPegawai = new AkunPegawai();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataKeTabel();
            } else {
                String roleName = (String) JComboBox_filterRole.getSelectedItem();
                
                if(roleName.equals("-- Pilih Filter Role --")){
                    akunPegawai.getPencarianDataTabel(search_value);
                    
                } else {
                    String roleId = this.roleNameToIdMap.get(roleName);

                    if (roleId == null || roleId.trim().isEmpty()) {
                        // Gagal mapping role → fallback ke tanpa filter role
                        akunPegawai.getPencarianDataTabel(search_value);
                    } else {
                        akunPegawai.getPencarianDataTabelFilterRole(search_value, roleId);
                    }
                }

                if (akunPegawai.getResultSet().isBeforeFirst()) {
                    while (akunPegawai.getResultSet().next()) {
                    Object[] fieldx = new Object[7];
                        fieldx[0] = akunPegawai.getResultSet().getString("Id_Pegawai");
                        fieldx[1] = akunPegawai.getResultSet().getString("Nama");
                        fieldx[2] = akunPegawai.getResultSet().getString("password");
                        fieldx[3] = akunPegawai.getResultSet().getString("Id_Role");
                        fieldx[4] = akunPegawai.getResultSet().getString("Nama_Role");
                        fieldx[5] = akunPegawai.getResultSet().getString("Tanggal_Buat_Akun");
                        fieldx[6] = akunPegawai.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
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
    
    public String generateIdPegawai(){
        AkunPegawai pegawai = new AkunPegawai();
        String prefix = "PGW";
        int counter = 1;
        String newId;

        while (true) {
            newId = prefix + String.format("%03d", counter);
            if (!pegawai.cekIdPegawaiSudahAda(newId)) {
                break;
            }
            counter++;
        }
        return newId;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        JTextfield_idPegawai = new javax.swing.JTextField();
        JTextfield_namaPegawai = new javax.swing.JTextField();
        JTextfield_passwordPegawai = new javax.swing.JTextField();
        JTextfield_konfirmasiPassword = new javax.swing.JTextField();
        JComboBox_rolePegawai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        JButton_hapusPegawai = new javax.swing.JButton();
        JButton_tambahPegawai = new javax.swing.JButton();
        JButton_keluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        JButton_generateIdPegawai = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        JTextfield_searchField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_Pegawai = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        JComboBox_filterRole = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));

        jPanel3.setBackground(new java.awt.Color(148, 180, 193));

        jPanel1.setBackground(new java.awt.Color(84, 119, 146));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Pegawai");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Role Pegawai");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Confirm Password");

        JTextfield_idPegawai.setEditable(false);
        JTextfield_idPegawai.setCaretColor(new java.awt.Color(51, 51, 51));
        JTextfield_idPegawai.setEnabled(false);
        JTextfield_idPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_idPegawaiActionPerformed(evt);
            }
        });
        JTextfield_idPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTextfield_idPegawaiKeyPressed(evt);
            }
        });

        JTextfield_passwordPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_passwordPegawaiActionPerformed(evt);
            }
        });

        JTextfield_konfirmasiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_konfirmasiPasswordActionPerformed(evt);
            }
        });

        JComboBox_rolePegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Role Pegawai Belum Dipilih --" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Pegawai");

        JButton_hapusPegawai.setBackground(new java.awt.Color(102, 0, 0));
        JButton_hapusPegawai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_hapusPegawai.setForeground(new java.awt.Color(255, 255, 255));
        JButton_hapusPegawai.setText("Hapus");
        JButton_hapusPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_hapusPegawaiActionPerformed(evt);
            }
        });

        JButton_tambahPegawai.setBackground(new java.awt.Color(0, 102, 0));
        JButton_tambahPegawai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_tambahPegawai.setForeground(new java.awt.Color(255, 255, 255));
        JButton_tambahPegawai.setText("Tambahkan");
        JButton_tambahPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_tambahPegawaiActionPerformed(evt);
            }
        });

        JButton_keluar.setBackground(new java.awt.Color(33, 52, 72));
        JButton_keluar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButton_keluar.setForeground(new java.awt.Color(255, 255, 255));
        JButton_keluar.setText("keluar");
        JButton_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_keluarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tambah Atau Update Pegawai");

        JButton_generateIdPegawai.setText("Generate ID");
        JButton_generateIdPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_generateIdPegawaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(JButton_keluar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JButton_tambahPegawai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JButton_hapusPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(JTextfield_idPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JButton_generateIdPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(JTextfield_namaPegawai, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(JTextfield_passwordPegawai)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(JComboBox_rolePegawai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(154, 154, 154))
                                    .addComponent(JTextfield_konfirmasiPassword))))
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(JTextfield_idPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JButton_generateIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(JTextfield_namaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(JTextfield_passwordPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(JTextfield_konfirmasiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JComboBox_rolePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JButton_hapusPegawai)
                    .addComponent(JButton_tambahPegawai)
                    .addComponent(JButton_keluar))
                .addGap(32, 32, 32))
        );

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        JTextfield_searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextfield_searchFieldActionPerformed(evt);
            }
        });
        JTextfield_searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTextfield_searchFieldKeyPressed(evt);
            }
        });

        JTable_Pegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Pegawai", "Nama Pegawai", "Password", "Role Id", "Nama Role", "Tanggal Buat Akun", "Tanggal Terakhir Masuk Akun"
            }
        ));
        JTable_Pegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_PegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_Pegawai);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cari :");

        JComboBox_filterRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Filter Role --" }));
        JComboBox_filterRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox_filterRoleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(JTextfield_searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JComboBox_filterRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextfield_searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JComboBox_filterRole, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 52, 72));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Maintenance Pegawai");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTextfield_konfirmasiPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_konfirmasiPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_konfirmasiPasswordActionPerformed

    private void JTable_PegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_PegawaiMouseClicked
        int selectedRow = JTable_Pegawai.getSelectedRow();
        
        if(selectedRow >= 0){
            JTextfield_idPegawai.setText(model.getValueAt(JTable_Pegawai.getSelectedRow(), 0) + "");
            JTextfield_namaPegawai.setText(model.getValueAt(JTable_Pegawai.getSelectedRow(), 1) + "");
            JTextfield_passwordPegawai.setText(model.getValueAt(JTable_Pegawai.getSelectedRow(), 2) + "");
            JTextfield_konfirmasiPassword.setText(model.getValueAt(JTable_Pegawai.getSelectedRow(), 2) + "");

            JButton_tambahPegawai.setText("Update");

            // Ambil nama role dari tabel
            String valueBox = model.getValueAt(selectedRow, 4).toString();

            // Set ComboBox agar sesuai dengan nama role
            for (int i = 0; i < JComboBox_rolePegawai.getItemCount(); i++) {
                if (valueBox.equals(JComboBox_rolePegawai.getItemAt(i))) {
                    JComboBox_rolePegawai.setSelectedIndex(i);
                    break;
                }
            }   
        }
    }//GEN-LAST:event_JTable_PegawaiMouseClicked

    private void JButton_tambahPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_tambahPegawaiActionPerformed
        String idPegawai = JTextfield_idPegawai.getText();
        String namaPegawai = JTextfield_namaPegawai.getText();
        String password = JTextfield_passwordPegawai.getText();
        String konfirmasiPassword = JTextfield_konfirmasiPassword.getText();
        String namaRole = (String) JComboBox_rolePegawai.getSelectedItem();
        String roleId = null;
        AkunPegawai akunPegawai = new AkunPegawai();

        if(idPegawai.isEmpty() || namaPegawai.isEmpty() || password.isEmpty() || namaRole.isEmpty()){
            JOptionPane.showMessageDialog(this, "Terdapat Data Yang Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
            JTextfield_idPegawai.requestFocus();
            
        } else if(!konfirmasiPassword.equals(password)){
            JOptionPane.showMessageDialog(this, "Konfirmasi Password Salah!", "error", JOptionPane.ERROR_MESSAGE);
            JTextfield_idPegawai.requestFocus();
            
        } else {
            
            if(namaRole.equals("-- Role Pegawai Belum Dipilih --")){
                JOptionPane.showMessageDialog(this, "Role Pegawai Belum Dipilih!", "info", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                roleId = this.roleNameToIdMap.get(namaRole);
            }

            if (roleId == null) {
                JOptionPane.showMessageDialog(this, "Data Pegawai tidak ditemukan!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            akunPegawai.getJumlahDataBerdasarkanId(idPegawai);
            if (akunPegawai.getAffectedRow() > 0) {
                if(JOptionPane.showConfirmDialog(this, "Pegawai Sudah Ada! Apakah Kamu mau Uodate Data ini?", "info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    
                    akunPegawai.updatePegawai(idPegawai, namaPegawai, password, roleId);
                    
                    if (akunPegawai.getAffectedRow() > 0) {
                        meresetInput();
                        dataKeTabel();
                        
                        JOptionPane.showMessageDialog(null, "Pegawai Berhasil Diperbarui");
                    } else {
                        JOptionPane.showMessageDialog(null, "Pegawai Gagal Diperbarui");
                    }
                }
            } else {
                akunPegawai.memasukanPegawaibaru(idPegawai, namaPegawai, password, roleId);
                
                if(akunPegawai.getAffectedRow() > 0){
                    dataKeTabel();
                    meresetInput();
                    JTextfield_idPegawai.requestFocus();
                    JOptionPane.showMessageDialog(this, "Data Pegawai Berhasil Disimpan", "info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_JButton_tambahPegawaiActionPerformed

    private void JButton_hapusPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_hapusPegawaiActionPerformed
        if(JTextfield_idPegawai.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Id Tidak Boleh Kosong");
            JTextfield_idPegawai.requestFocus();
        } else {
            deleteData();
        }
    }//GEN-LAST:event_JButton_hapusPegawaiActionPerformed

    private void JButton_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar Dari Maintenance Pegawai?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            superAdminDashboardFrame.setEnabled(true);
            superAdminDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_JButton_keluarActionPerformed

    private void JTextfield_idPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextfield_idPegawaiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            cekValidasiPassword();
        }
    }//GEN-LAST:event_JTextfield_idPegawaiKeyPressed

    private void JTextfield_passwordPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_passwordPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_passwordPegawaiActionPerformed

    private void JTextfield_searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_searchFieldActionPerformed
        String search_value = JTextfield_searchField.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_JTextfield_searchFieldActionPerformed

    private void JComboBox_filterRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboBox_filterRoleActionPerformed
        if(this.filterComboBoxIntialize){
            String filterRoleName = (String) JComboBox_filterRole.getSelectedItem();
            AkunPegawai akunPegawai = new AkunPegawai();
            JTextfield_searchField.setText("");
        
            if (filterRoleName == null || filterRoleName.equals("-- Pilih Filter Role --")) {
                dataKeTabel();
                return;
            } else {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                try {
                    if (filterRoleName.trim().isEmpty()) {
                        performSearch(filterRoleName);
                    } else {
                        akunPegawai.getPegawaiBerdasarkanNamaRole(filterRoleName);

                        if (akunPegawai.getResultSet().isBeforeFirst()) {
                            while (akunPegawai.getResultSet().next()) {
                                Object[] fieldx = new Object[7];
                                fieldx[0] = akunPegawai.getResultSet().getString("Id_Pegawai");
                                fieldx[1] = akunPegawai.getResultSet().getString("Nama");
                                fieldx[2] = akunPegawai.getResultSet().getString("password");
                                fieldx[3] = akunPegawai.getResultSet().getString("Id_Role");
                                fieldx[4] = akunPegawai.getResultSet().getString("Nama_Role");
                                fieldx[5] = akunPegawai.getResultSet().getString("Tanggal_Buat_Akun");
                                fieldx[6] = akunPegawai.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
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
        }
    }//GEN-LAST:event_JComboBox_filterRoleActionPerformed

    private void JTextfield_searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTextfield_searchFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            String search_value = JTextfield_searchField.getText().trim();
            performSearch(search_value);
        }
    }//GEN-LAST:event_JTextfield_searchFieldKeyPressed

    private void JTextfield_idPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextfield_idPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTextfield_idPegawaiActionPerformed

    private void JButton_generateIdPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_generateIdPegawaiActionPerformed
        JTextfield_idPegawai.setText(generateIdPegawai());
    }//GEN-LAST:event_JButton_generateIdPegawaiActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_generateIdPegawai;
    private javax.swing.JButton JButton_hapusPegawai;
    private javax.swing.JButton JButton_keluar;
    private javax.swing.JButton JButton_tambahPegawai;
    private javax.swing.JComboBox<String> JComboBox_filterRole;
    private javax.swing.JComboBox<String> JComboBox_rolePegawai;
    private javax.swing.JTable JTable_Pegawai;
    private javax.swing.JTextField JTextfield_idPegawai;
    private javax.swing.JTextField JTextfield_konfirmasiPassword;
    private javax.swing.JTextField JTextfield_namaPegawai;
    private javax.swing.JTextField JTextfield_passwordPegawai;
    private javax.swing.JTextField JTextfield_searchField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
