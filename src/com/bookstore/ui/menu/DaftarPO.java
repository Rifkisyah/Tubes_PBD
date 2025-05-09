/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.bookstore.ui.menu;

import com.bookstore.data.MysqlConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class DaftarPO extends javax.swing.JFrame {
    private DefaultTableModel tableModel;
    MysqlConnection mysqlConnection;
    PreparedStatement stmt;
    ResultSet rslt;
    private int countData;
    private String queryCheck, queryInsert, queryUpdate, queryDelete;
    /**
     * Creates new form NewJFrame
     */
    public DaftarPO() {
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        setHeaderTable();
    }
    
        private void columnSizing(){
        TableColumn column;
        POTabel.setAutoResizeMode(POTabel.AUTO_RESIZE_OFF);
        
        column = POTabel.getColumnModel().getColumn(0); //nota
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(1); //id pegawai
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(2); // nama pegawai
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(3); // isbn
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(4); // judul buku
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(120);
        column = POTabel.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(7); // tanggal PO
        column.setPreferredWidth(100);
        column = POTabel.getColumnModel().getColumn(7); // Estimasi Tiba
        column.setPreferredWidth(100);
        column = POTabel.getColumnModel().getColumn(8); // Jumlah PO
        column.setPreferredWidth(100);
        column = POTabel.getColumnModel().getColumn(9); // Total Biaya
        column.setPreferredWidth(200);
        column = POTabel.getColumnModel().getColumn(10); // Status PO
        column.setPreferredWidth(100);
    }
    
    private void getDataTable(){
        mysqlConnection = new MysqlConnection();

        try{
            this.queryCheck = 
                "SELECT " +
                "po.nota_PO, " +
                "po.id_pegawai, " +
                "ap.nama AS nama_pegawai, " +
                "po.id_vendor, " +
                "v.Nama_Vendor, " +
                "po.isbn, " +
                "mb.judul_buku, " +
                "po.tanggal_PO, " +
                "po.estimasi_tanggal_datang, " +
                "po.jumlah_PO, " +
                "po.total_biaya, " +
                "po.status_PO " +
                "FROM T_PurchaseOrder po " +
                "JOIN T_AkunPegawai ap ON po.id_pegawai = ap.id_pegawai " +
                "JOIN T_MasterBuku mb ON po.isbn = mb.isbn " +
                "JOIN T_Vendor v ON po.id_vendor = v.id_vendor";

            this.stmt = mysqlConnection.getConnection().prepareStatement(queryCheck);
            this.rslt = stmt.executeQuery();

            while(rslt.next()){
                Object[] fieldx = new Object[12];
                fieldx[0] = rslt.getString("nota_PO");
                fieldx[1] = rslt.getString("id_pegawai");
                fieldx[2] = rslt.getString("nama_pegawai");
                fieldx[3] = rslt.getString("id_vendor");
                fieldx[4] = rslt.getString("Nama_Vendor");
                fieldx[5] = rslt.getString("isbn");
                fieldx[6] = rslt.getString("judul_buku");
                fieldx[7] = rslt.getString("tanggal_PO");
                fieldx[8] = rslt.getString("estimasi_tanggal_datang");
                fieldx[9] = rslt.getInt("jumlah_PO");
                fieldx[10] = rslt.getDouble("total_biaya");
                fieldx[11] = rslt.getString("status_PO");

                this.tableModel.addRow(fieldx);
            }

        } catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    
    private void setHeaderTable(){
        this.tableModel = new DefaultTableModel();
        POTabel.setModel(tableModel);

        tableModel.addColumn("No. PO");
        tableModel.addColumn("ID Pegawai");
        tableModel.addColumn("Nama Pegawai");
        tableModel.addColumn("ID Vendor");
        tableModel.addColumn("Nama Vendor");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Judul Buku");
        tableModel.addColumn("Tanggal PO");
        tableModel.addColumn("Estimasi Tiba");
        tableModel.addColumn("Jumlah PO");
        tableModel.addColumn("Total Biaya");
        tableModel.addColumn("Status PO");

        columnSizing();
        getDataTable();
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        POTabel = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(720, 370));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 3, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 370));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Daftar Purchase Order");

        POTabel.setBackground(new java.awt.Color(153, 153, 255));
        POTabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        POTabel.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        POTabel.setForeground(new java.awt.Color(255, 255, 255));
        POTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No PO", "ID Pegawai", "Nama Pegawai", "ISBN", "Judul Buku", "ID Vendor", "Nama Vendor", "Tanggal PO", "Estimasi Tiba", "Jumlah PO", "Total Biaya", "Status PO"
            }
        ));
        POTabel.setAlignmentY(1.0F);
        jScrollPane1.setViewportView(POTabel);

        CloseButton.setBackground(new java.awt.Color(204, 0, 51));
        CloseButton.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        CloseButton.setForeground(new java.awt.Color(255, 255, 255));
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CloseButton)
                .addGap(131, 131, 131))
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

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            this.setFocusable(false);
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaftarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DaftarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DaftarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DaftarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DaftarPO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTable POTabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
