package com.bookstore.ui.administrator_menu;

import com.bookstore.data.QuerySelector;
import com.bookstore.ui.SuperAdminDashboardFrame;
import com.bookstore.ui.inventory_menu.DaftarPO;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;
/**
 *
 * @author rifki
 */
public class MaintenanceRolePegawai extends javax.swing.JFrame {
    public DefaultTableModel model;
    private String idrole;
    QuerySelector querySelector;
    SuperAdminDashboardFrame superAdminDashboardFrame;
    /**
     * Creates new form MaintenanceRolePegawai
     * @param superAdminDashboardFrame
     */
    public MaintenanceRolePegawai(SuperAdminDashboardFrame superAdminDashboardFrame) {
        this.superAdminDashboardFrame = superAdminDashboardFrame;
        
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Maintenance Role Pegawai");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        MaintenanceRolePegawai.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Aplikasi?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    MaintenanceRolePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    MaintenanceRolePegawai.this.setVisible(false);
                    superAdminDashboardFrame.setEnabled(true);
                    superAdminDashboardFrame.requestFocus();
                } else {
                    MaintenanceRolePegawai.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });
        
        setHeaderTable();
    }
    
    public void dataRoleToTabel(){
        this.querySelector = new QuerySelector();

        try{
            querySelector.selectAllFromTable("T_Role");
            
            model.setRowCount(0);

            while (querySelector.getResultSet().next()) {    
            Object[] fieldx = new Object[3];
                fieldx[0] = querySelector.getResultSet().getString("Id_Role");
                fieldx[1] = querySelector.getResultSet().getString("Nama_Role");
                fieldx[2] = querySelector.getResultSet().getString("Tanggal_Buat");
                model.addRow(fieldx);
            }
        } catch (SQLException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }
    
    private void columnWrapping(){
        TableColumn column;
        roleTable.setAutoResizeMode(roleTable.AUTO_RESIZE_OFF);
        column = roleTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
        column = roleTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(250);
        column = roleTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
    }

    
    public void setHeaderTable(){
        model = new DefaultTableModel();
        model.addColumn("Id Role");
        model.addColumn("Nama Role");
        model.addColumn("Tanggal Buat Akun");
        roleTable.setModel(model);
        columnWrapping();
        dataRoleToTabel();
    }
    
        private void performSearch(String search_value) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            if (search_value == null || search_value.trim().isEmpty()) {
                dataRoleToTabel();
            } else {
                querySelector.selectLikeWithJoinAkunPegawai("id_pegawai", "nama", search_value);

                if (querySelector.getResultSet().isBeforeFirst()) {
                    while (querySelector.getResultSet().next()) {
                        Object[] fieldx = new Object[3];
                        fieldx[0] = querySelector.getResultSet().getString("Id_Role");
                        fieldx[1] = querySelector.getResultSet().getString("Nama_Role");
                        fieldx[2] = querySelector.getResultSet().getString("Tanggal_Buat");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        InputIdRole = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        roleTable = new javax.swing.JTable();
        SaveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        deleteRoleButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        InputNamaRole = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(84, 119, 146));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Role Pegawai");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Role");

        roleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Role", "Nama Role", "Tanggal Buat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        roleTable.setShowGrid(true);
        roleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roleTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(roleTable);

        SaveButton.setBackground(new java.awt.Color(0, 153, 0));
        SaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SaveButton.setForeground(new java.awt.Color(255, 255, 255));
        SaveButton.setText("Tambahkan");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        exitButton.setBackground(new java.awt.Color(33, 52, 72));
        exitButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setText("Keluar");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        deleteRoleButton.setBackground(new java.awt.Color(204, 0, 51));
        deleteRoleButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteRoleButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteRoleButton.setText("Hapus");
        deleteRoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRoleButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Role");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(exitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SaveButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteRoleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputIdRole))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputNamaRole)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(InputIdRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InputNamaRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveButton)
                    .addComponent(deleteRoleButton)
                    .addComponent(exitButton))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roleTableMouseClicked
        InputIdRole.setText(model.getValueAt(roleTable.getSelectedRow(), 0) + "");
        InputNamaRole.setText(model.getValueAt(roleTable.getSelectedRow(), 1) + "");
        SaveButton.setText("Update");
        InputIdRole.setEnabled(false);
    }//GEN-LAST:event_roleTableMouseClicked

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        try {
            String rolename = InputNamaRole.getText().trim();
            String idRole = InputIdRole.getText().trim();
            Date tanggal = new Date();
            SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tanggaldibuat = formatTanggal.format(tanggal);

            if (rolename.isEmpty() || idRole.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input masih kosong!");
                InputIdRole.requestFocus();
                return;
            }
            querySelector.countDataByColumn("T_Role", "id_role", idRole);

                if (querySelector.getAffectedRows() > 0) {
                    if(JOptionPane.showConfirmDialog(this, "Role Sudah Ada! Apakah Kamu mau Update Data ini?", "info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        
                        querySelector.updateOneColumn("T_Role", "Nama_Role", rolename, "Id_Role", idRole);
                        
                        if (querySelector.getAffectedRows() > 0) {
                            InputNamaRole.setText("");
                            InputIdRole.setText("");
                            
                            dataRoleToTabel();
                            InputIdRole.setEnabled(true);
                            SaveButton.setText("Tambahkan");
                            JOptionPane.showMessageDialog(null, "Role Berhasil Diperbarui");
                        } else {
                            JOptionPane.showMessageDialog(null, "Role Gagal Diperbarui");
                        }
                    }
                } else {
                    querySelector.insertThreeColumns("T_Role", "Id_Role", "Nama_Role", "Tanggal_Buat", idRole, rolename, tanggaldibuat);

                    if (querySelector.getAffectedRows() > 0) {
                        model.getDataVector().removeAllElements();
                        model.fireTableDataChanged();
                        InputIdRole.setText("");
                        InputNamaRole.setText("");
                        dataRoleToTabel();
                        JOptionPane.showMessageDialog(null, "Role Berhasil Disimpan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Role Gagal Disimpan");
                    }
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MaintenanceRolePegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar dari Role Pegawai?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            superAdminDashboardFrame.setEnabled(true);
            superAdminDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void deleteRoleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRoleButtonActionPerformed
        int deleting = JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Menghapus Data Ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if(deleting == JOptionPane.YES_OPTION){
            String idRole = InputIdRole.getText().trim();
            if(idRole.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nama Role Masih Kosong!");
                InputIdRole.requestFocus();
            } else {
                try {
                    querySelector.deleteByKey("T_Role", "id_role", idRole);

                    if (querySelector.getAffectedRows() > 0) {
                        model.getDataVector().removeAllElements();
                        model.fireTableDataChanged();

                        InputIdRole.setText("");
                        InputNamaRole.setText("");
                        dataRoleToTabel();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "ID Tidak Ditemukan atau Tidak Dihapus");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(MaintenanceRolePegawai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_deleteRoleButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InputIdRole;
    private javax.swing.JTextField InputNamaRole;
    private javax.swing.JButton SaveButton;
    private javax.swing.JButton deleteRoleButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable roleTable;
    // End of variables declaration//GEN-END:variables
}
