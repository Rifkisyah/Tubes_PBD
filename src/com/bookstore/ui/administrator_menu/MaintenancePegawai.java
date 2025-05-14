/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.administrator_menu;

import com.bookstore.data.QuerySelector;
import com.bookstore.ui.SuperAdminDashboardFrame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
public class MaintenancePegawai extends javax.swing.JFrame {
    public DefaultTableModel model;
    QuerySelector querySelector;
    SuperAdminDashboardFrame superAdminDashboardFrame;
    private boolean filterComboBoxIntialize = false;
    private HashMap<String, String> roleNameToIdMap = new HashMap<>();
    /**
     * Creates new form MaintenancePegawai
     * @param superAdminDashboardFrame
     */
    public MaintenancePegawai(SuperAdminDashboardFrame superAdminDashboardFrame) {
        this.superAdminDashboardFrame = superAdminDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Maintenance Pegawai");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        MaintenancePegawai.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Aplikasi?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    MaintenancePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    MaintenancePegawai.this.setVisible(false);
                    superAdminDashboardFrame.setEnabled(true);
                    superAdminDashboardFrame.requestFocus();
                } else {
                    MaintenancePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
        dataRoleToComboBox(ComboBoxRolePegawai, "T_Role", "id_role", "nama_role");
        dataRoleToComboBox(ComboBoxFilterRole, "T_Role", "id_role", "nama_role");
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
    
    public void dataPegawaiToTabel(){
        this.querySelector = new QuerySelector();

        try{
            querySelector.selectPegawaiAndRole();
            
            model.setRowCount(0);
            
            while(querySelector.getResultSet().next()){
                Object[] fieldx = new Object[7];
                    fieldx[0] = querySelector.getResultSet().getString("Id_Pegawai");
                    fieldx[1] = querySelector.getResultSet().getString("Nama");
                    fieldx[2] = querySelector.getResultSet().getString("password");
                    fieldx[3] = querySelector.getResultSet().getString("Id_Role");
                    fieldx[4] = querySelector.getResultSet().getString("Nama_Role");
                    fieldx[5] = querySelector.getResultSet().getString("Tanggal_Buat_Akun");
                    fieldx[6] = querySelector.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
                    model.addRow(fieldx);
            }
        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            System.err.println("data ke tabel gagal");
        }
    }
    
    private void columnWrapping(){
        TableColumn column;
        employeeTable.setAutoResizeMode(employeeTable.AUTO_RESIZE_OFF);
        column = employeeTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = employeeTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = employeeTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = employeeTable.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = employeeTable.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = employeeTable.getColumnModel().getColumn(5);
        column.setPreferredWidth(200);
        column = employeeTable.getColumnModel().getColumn(6);
        column.setPreferredWidth(200);
    }
    
    public void setHeaderTable(){
        model = new DefaultTableModel();
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Password");
        model.addColumn("Role Id");
        model.addColumn("Nama Role");
        model.addColumn("Tanggal Buat Akun");
        model.addColumn("Tanggal Terakhir Masuk Akun");
        employeeTable.setModel(model);
        columnWrapping();
        dataPegawaiToTabel();
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
                
                roleNameToIdMap.put(name, id);

                // Hindari duplikat dengan index 0 (opsional)
                if (firstItem == null || !firstItem.toString().equals(name)) {
                    comboBox.addItem(name);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteData() {
        if (JOptionPane.showConfirmDialog(this, "Apakah Yakin Akan Dihapus?", "Informasi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String idPegawai = FieldInputIdPegawai.getText().trim();
            try {
                
                querySelector.deleteByKey("T_AkunPegawai", "Id_Pegawai", idPegawai);
                
                if (querySelector.getAffectedRows() > 0) {
                    FieldInputIdPegawai.setText("");
                    FieldInputNamaPegawai.setText("");
                    FieldInputPassword.setText("");
                    FieldInputConfirmPassword.setText("");
                    FieldInputIdPegawai.requestFocus();
                    
                    
                    dataPegawaiToTabel();
                    
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan / Gagal Dihapus", "Info", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Hapus Data Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pegawai Batal Dihapus");
            FieldInputIdPegawai.requestFocus();
        }
    }
    
    private void cekValidasiPassword(){
        String pass = FieldInputPassword.getText();
        String konfPass = FieldInputConfirmPassword.getText();
        if(pass.equals(konfPass)){
            ComboBoxRolePegawai.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "validasi password salah!", "Pesan", JOptionPane.ERROR_MESSAGE);
            FieldInputConfirmPassword.requestFocus();
        }
    }
    
    private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataPegawaiToTabel();
            } else {
                String roleName = (String) ComboBoxFilterRole.getSelectedItem();
                
                if(roleName.equals("-- Pilih Filter Role --")){
                    querySelector.selectLikeWithJoinAkunPegawai("id_pegawai", "nama", search_value);
                } else {
                    System.out.println("roleNameToIdMap = " + this.roleNameToIdMap);
                    String roleId = this.roleNameToIdMap.get(roleName);

                    if (roleId == null || roleId.trim().isEmpty()) {
                        // Gagal mapping role → fallback ke tanpa filter role
                        querySelector.selectLikeWithJoinAkunPegawai("id_pegawai", "nama", search_value);
                    } else {
                        querySelector.selectLikeWithJoinAndFilterAkunPegawai("id_pegawai", "nama", search_value, "id_role", roleId);
                    }
                }

                if (querySelector.getResultSet().isBeforeFirst()) {
                    while (querySelector.getResultSet().next()) {
                    Object[] fieldx = new Object[7];
                        fieldx[0] = querySelector.getResultSet().getString("Id_Pegawai");
                        fieldx[1] = querySelector.getResultSet().getString("Nama");
                        fieldx[2] = querySelector.getResultSet().getString("password");
                        fieldx[3] = querySelector.getResultSet().getString("Id_Role");
                        fieldx[4] = querySelector.getResultSet().getString("Nama_Role");
                        fieldx[5] = querySelector.getResultSet().getString("Tanggal_Buat_Akun");
                        fieldx[6] = querySelector.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
                        model.addRow(fieldx);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pegawai Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        FieldInputIdPegawai = new javax.swing.JTextField();
        FieldInputNamaPegawai = new javax.swing.JTextField();
        FieldInputPassword = new javax.swing.JTextField();
        FieldInputConfirmPassword = new javax.swing.JTextField();
        ComboBoxRolePegawai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        DeletePegawaiButton = new javax.swing.JButton();
        SavePegawaiButton = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        search_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        ComboBoxFilterRole = new javax.swing.JComboBox<>();
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

        FieldInputIdPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldInputIdPegawaiKeyPressed(evt);
            }
        });

        FieldInputPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputPasswordActionPerformed(evt);
            }
        });

        FieldInputConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputConfirmPasswordActionPerformed(evt);
            }
        });

        ComboBoxRolePegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Role Pegawai Belum Dipilih --" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Pegawai");

        DeletePegawaiButton.setBackground(new java.awt.Color(102, 0, 0));
        DeletePegawaiButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeletePegawaiButton.setForeground(new java.awt.Color(255, 255, 255));
        DeletePegawaiButton.setText("Hapus");
        DeletePegawaiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletePegawaiButtonActionPerformed(evt);
            }
        });

        SavePegawaiButton.setBackground(new java.awt.Color(0, 102, 0));
        SavePegawaiButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SavePegawaiButton.setForeground(new java.awt.Color(255, 255, 255));
        SavePegawaiButton.setText("Tambahkan");
        SavePegawaiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavePegawaiButtonActionPerformed(evt);
            }
        });

        btn_keluar.setBackground(new java.awt.Color(33, 52, 72));
        btn_keluar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_keluar.setForeground(new java.awt.Color(255, 255, 255));
        btn_keluar.setText("keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tambah Atau Update Pegawai");

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
                                .addComponent(btn_keluar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SavePegawaiButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeletePegawaiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FieldInputNamaPegawai, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(FieldInputIdPegawai)
                                    .addComponent(FieldInputPassword)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ComboBoxRolePegawai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(154, 154, 154))
                                    .addComponent(FieldInputConfirmPassword))))
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
                    .addComponent(FieldInputIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(FieldInputNamaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(FieldInputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(FieldInputConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBoxRolePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeletePegawaiButton)
                    .addComponent(SavePegawaiButton)
                    .addComponent(btn_keluar))
                .addGap(32, 32, 32))
        );

        jPanel2.setBackground(new java.awt.Color(84, 119, 146));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 52, 72), 3, true));

        search_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_fieldActionPerformed(evt);
            }
        });
        search_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_fieldKeyPressed(evt);
            }
        });

        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
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
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(employeeTable);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cari :");

        ComboBoxFilterRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Filter Role --" }));
        ComboBoxFilterRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFilterRoleActionPerformed(evt);
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
                        .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxFilterRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxFilterRole, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 17, Short.MAX_VALUE)))
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

    private void FieldInputConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputConfirmPasswordActionPerformed

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked
        FieldInputIdPegawai.setText(model.getValueAt(employeeTable.getSelectedRow(), 0) + "");
        FieldInputNamaPegawai.setText(model.getValueAt(employeeTable.getSelectedRow(), 1) + "");
        FieldInputPassword.setText(model.getValueAt(employeeTable.getSelectedRow(), 2) + "");
        FieldInputConfirmPassword.setText(model.getValueAt(employeeTable.getSelectedRow(), 2) + "");
        
        FieldInputIdPegawai.setEnabled(false);
        SavePegawaiButton.setText("Update");

        employeeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Ambil nilai dari kolom "Nama Negara"
                    String valueBox = employeeTable.getValueAt(selectedRow, 4).toString();

                    // Loop item di comboBox dan set selectedIndex
                    for (int i = 0; i < ComboBoxRolePegawai.getItemCount(); i++) {
                        if (valueBox.equals(ComboBoxRolePegawai.getItemAt(i))) {
                            ComboBoxRolePegawai.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }//GEN-LAST:event_employeeTableMouseClicked

    private void SavePegawaiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavePegawaiButtonActionPerformed
        String employeeId = FieldInputIdPegawai.getText();
        String employeeName = FieldInputNamaPegawai.getText();
        String pwd = FieldInputPassword.getText();
        String confPwd = FieldInputConfirmPassword.getText();
        String roleName = (String) ComboBoxRolePegawai.getSelectedItem();
        
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current_date = formatDate.format(date);
        
        String roleId = null;

        if(employeeId.isEmpty() || employeeName.isEmpty() || pwd.isEmpty() || roleName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Terdapat Data Yang Masih Kosong!", "error", JOptionPane.ERROR_MESSAGE);
            FieldInputIdPegawai.requestFocus();
            
        } else if(!confPwd.equals(pwd)){
            JOptionPane.showMessageDialog(this, "Konfirmasi Password Salah!", "error", JOptionPane.ERROR_MESSAGE);
            FieldInputIdPegawai.requestFocus();
            
        } else {
            try {
                querySelector.countDataByColumn("T_AkunPegawai", "Id_Pegawai", employeeId);

                if (querySelector.getAffectedRows() > 0) {
                    if(JOptionPane.showConfirmDialog(this, "Pegawai Sudah Ada! Apakah Kamu mau Uodate Data ini?", "info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        FieldInputIdPegawai.setEnabled(false);
                
                        if(roleName.equals("-- Role Pegawai Belum Dipilih --")){
                            JOptionPane.showMessageDialog(this, "Role Pegawai Belum Dipilih!", "info", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        } else {
                            roleId = this.roleNameToIdMap.get(roleName);
                        }
                        
                        if (roleId == null) {
                            JOptionPane.showMessageDialog(this, "Data Pegawai tidak ditemukan!", "Error", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        // Jika ID sudah ada, update
                        querySelector.updateThreeColumns(
                            "T_AkunPegawai",
                            "Nama", employeeName,
                            "password", pwd,
                            "id_role", roleId,
                            "Id_Pegawai", employeeId
                        );

                        if (querySelector.getAffectedRows() > 0) {
                                FieldInputIdPegawai.setText("");
                                FieldInputNamaPegawai.setText("");
                                FieldInputPassword.setText("");
                                FieldInputConfirmPassword.setText("");
                                ComboBoxRolePegawai.setSelectedIndex(0);

                                dataPegawaiToTabel();
                                FieldInputIdPegawai.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Pegawai Berhasil Diperbarui");
                            } else {
                                JOptionPane.showMessageDialog(null, "Pegawai Gagal Diperbarui");
                            }
                    }
                } else {
                    // Jika belum ada, insert
                    querySelector.setInsert5Columns(
                        "T_AkunPegawai",
                        "Id_Pegawai", "Nama", "password", "Id_Role", "Tanggal_Buat_Akun",
                        employeeId, employeeName, pwd, roleId, current_date
                    );
                    
                    dataPegawaiToTabel();

                    FieldInputIdPegawai.setText("");
                    FieldInputNamaPegawai.setText("");
                    FieldInputPassword.setText("");
                    FieldInputConfirmPassword.setText("");
                    FieldInputIdPegawai.requestFocus();
                    JOptionPane.showMessageDialog(this, "Data Pegawai Berhasil Disimpan", "info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(this, "Cek Role id Gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("selected role :" + roleId);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_SavePegawaiButtonActionPerformed

    private void DeletePegawaiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletePegawaiButtonActionPerformed
        if(FieldInputIdPegawai.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Id Tidak Boleh Kosong");
            FieldInputIdPegawai.requestFocus();
        } else {
            deleteData();
        }
    }//GEN-LAST:event_DeletePegawaiButtonActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar Dari Maintenance Pegawai?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            superAdminDashboardFrame.setEnabled(true);
            superAdminDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void FieldInputIdPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldInputIdPegawaiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            cekValidasiPassword();
        }
    }//GEN-LAST:event_FieldInputIdPegawaiKeyPressed

    private void FieldInputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputPasswordActionPerformed

    private void search_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_fieldActionPerformed
        String search_value = search_field.getText().trim();
        performSearch(search_value);
    }//GEN-LAST:event_search_fieldActionPerformed

    private void ComboBoxFilterRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFilterRoleActionPerformed
        if(this.filterComboBoxIntialize){
            String filterRoleName = (String) ComboBoxFilterRole.getSelectedItem();
            search_field.setText("");
        
            if (filterRoleName == null || filterRoleName.equals("-- Pilih Filter Role --")) {
                dataPegawaiToTabel();
                return;
            } else {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                try {
                    if (filterRoleName.trim().isEmpty()) {
                        performSearch(filterRoleName);
                    } else {
                        querySelector.selectPegawaiAndRoleByRoleFilter(filterRoleName);

                        if (querySelector.getResultSet().isBeforeFirst()) {
                            while (querySelector.getResultSet().next()) {
                                Object[] fieldx = new Object[7];
                                fieldx[0] = querySelector.getResultSet().getString("Id_Pegawai");
                                fieldx[1] = querySelector.getResultSet().getString("Nama");
                                fieldx[2] = querySelector.getResultSet().getString("password");
                                fieldx[3] = querySelector.getResultSet().getString("Id_Role");
                                fieldx[4] = querySelector.getResultSet().getString("Nama_Role");
                                fieldx[5] = querySelector.getResultSet().getString("Tanggal_Buat_Akun");
                                fieldx[6] = querySelector.getResultSet().getString("Tanggal_Terakhir_Masuk_Akun");
                                model.addRow(fieldx);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Pegawai Tidak Ditemukan!", "Gagal Mencari Rak", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_ComboBoxFilterRoleActionPerformed

    private void search_fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_fieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            String search_value = search_field.getText().trim();
            performSearch(search_value);
        }
    }//GEN-LAST:event_search_fieldKeyPressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFilterRole;
    private javax.swing.JComboBox<String> ComboBoxRolePegawai;
    private javax.swing.JButton DeletePegawaiButton;
    private javax.swing.JTextField FieldInputConfirmPassword;
    private javax.swing.JTextField FieldInputIdPegawai;
    private javax.swing.JTextField FieldInputNamaPegawai;
    private javax.swing.JTextField FieldInputPassword;
    private javax.swing.JButton SavePegawaiButton;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JTable employeeTable;
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
    private javax.swing.JTextField search_field;
    // End of variables declaration//GEN-END:variables
}
