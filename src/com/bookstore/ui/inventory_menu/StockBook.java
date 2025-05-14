package com.bookstore.ui.inventory_menu;

import com.bookstore.data.QuerySelector;
import com.bookstore.ui.InventoryDashboardFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JFrame;

/**
 *
 * @author rifki
 */
public class StockBook extends javax.swing.JFrame {
    InventoryDashboardFrame inventoryDashboardFrame;
    private DefaultTableModel model;
    QuerySelector querySelector;
    /**
     * Creates new form StockBook
     */
    public StockBook(InventoryDashboardFrame inventoryDashboardFrame) {
        
        this.inventoryDashboardFrame = inventoryDashboardFrame;

        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Toko Buku - Daftar Stock Buku");
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        StockBook.this,
                        "Apakah Kamu Yakin Ingin Keluar Dari Aplikasi?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    StockBook.this.setVisible(false);
                    inventoryDashboardFrame.setEnabled(true);
                    inventoryDashboardFrame.requestFocus();
                } else {
                    StockBook.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });

        setHeaderTable();

    }
    
    private void columnSizing(){
        TableColumn column;
        StockBookTable.setAutoResizeMode(StockBookTable.AUTO_RESIZE_OFF);
        
        column = StockBookTable.getColumnModel().getColumn(0); //ID stock
        column.setPreferredWidth(120);
        column = StockBookTable.getColumnModel().getColumn(1); //isbn
        column.setPreferredWidth(120);
        column = StockBookTable.getColumnModel().getColumn(2); // judul buku
        column.setPreferredWidth(200);
        column = StockBookTable.getColumnModel().getColumn(3); // kode rak
        column.setPreferredWidth(120);
        column = StockBookTable.getColumnModel().getColumn(4); // nama rak
        column.setPreferredWidth(200);
        column = StockBookTable.getColumnModel().getColumn(5); // id vendor
        column.setPreferredWidth(120);
        column = StockBookTable.getColumnModel().getColumn(6); // nama vendor
        column.setPreferredWidth(200);
        column = StockBookTable.getColumnModel().getColumn(7); // jumlah stock
        column.setPreferredWidth(100);
        column = StockBookTable.getColumnModel().getColumn(8); // tanggal update stock
        column.setPreferredWidth(150);
        column = StockBookTable.getColumnModel().getColumn(9); // harga satuan
        column.setPreferredWidth(200);
    }
    
    private void getDataTable(){
        this.querySelector = new QuerySelector();
        
        try{
            querySelector.selectDetailMasterBuku("toko");
            
            model.setRowCount(0);
            
            while(this.querySelector.getResultSet().next()){
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
        
        StockBookTable.setModel(model);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        StockBookTable = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Konfigurasi Rak");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(84, 119, 146));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 3, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(520, 370));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Daftar Stock Buku");

        StockBookTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true));
        StockBookTable.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        StockBookTable.setModel(new javax.swing.table.DefaultTableModel(
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
        StockBookTable.setAlignmentY(1.0F);
        jScrollPane1.setViewportView(StockBookTable);

        CloseButton.setBackground(new java.awt.Color(33, 52, 72));
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.setVisible(false);
            inventoryDashboardFrame.setEnabled(true);
            inventoryDashboardFrame.requestFocus();
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTable StockBookTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
