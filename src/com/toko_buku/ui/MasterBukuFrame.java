/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.toko_buku.ui;

import com.toko_buku.data.DbConnection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;

/**
 *
 * @author rifki
 */
public class MasterBukuFrame extends javax.swing.JFrame {
    public DefaultTableModel model;
    SuperAdminFrame adminFrame;
    /**
     * Creates new form JFrameRole
     */
    public MasterBukuFrame(SuperAdminFrame adminFrame) throws ClassNotFoundException {
        this.adminFrame = adminFrame;
        initComponents();
        HeaderTable();
        setDefaultCloseOperation(0);
        setTitle("Toko Buku - Master Buku");
    }
    
    public void HeaderTable() throws ClassNotFoundException{
        model = new DefaultTableModel();
        TabelMasterBuku.setModel(model);
        model.addColumn("ISBN");
        model.addColumn("Judul Buku");
        model.addColumn("Nama Penulis");
        model.addColumn("Nama Penerbit");
        model.addColumn("Nama Kategori");
        model.addColumn("Genre Buku");
        model.addColumn("Jumlah Halaman");
        columnWrapping();
        viewRole();
    }
    
    private void columnWrapping(){
        TableColumn column;
        TabelMasterBuku.setAutoResizeMode(TabelMasterBuku.AUTO_RESIZE_OFF);
        column = TabelMasterBuku.getColumnModel().getColumn(0);
        column.setPreferredWidth(100);
        column = TabelMasterBuku.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = TabelMasterBuku.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = TabelMasterBuku.getColumnModel().getColumn(3);
        column.setPreferredWidth(200);
        column = TabelMasterBuku.getColumnModel().getColumn(4);
        column.setPreferredWidth(200);
        column = TabelMasterBuku.getColumnModel().getColumn(5);
        column.setPreferredWidth(200);
        column = TabelMasterBuku.getColumnModel().getColumn(6);
        column.setPreferredWidth(100);
    }
    
    public void viewRole() throws ClassNotFoundException{
        DbConnection ctd = new DbConnection();
        try{
            Connection conn = ctd.getConnection();
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT *FROM T_MasterBuku";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                
                Object [] fieldx = new Object[7];
                fieldx[0] = rs.getString("ISBN");
                fieldx[1] = rs.getString("Judul_Buku");
                fieldx[2] = rs.getString("Nama_Penulis");
                fieldx[3] = rs.getString("Nama_Penerbit");
                fieldx[4] = rs.getString("Nama_Kategori");
                fieldx[5] = rs.getString("Genre_Buku");
                fieldx[6] = rs.getString("Jumlah_Halaman");
                model.addRow(fieldx);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private void deleteData(){
            String isbn = FieldInputISBN.getText().trim();
            String judulBuku = FieldInputJudul.getText().trim();
            String namaPenulis = FieldInputPenulis.getText().trim();
            String namaPenerbit = FieldInputPenerbit.getText().trim();
            String namaKategori = FieldInputKategori.getText().trim();
            String genreBuku = FieldInputGenre.getText().trim();
            int jumlahHalaman = Integer.parseInt(FieldInputHalaman.getText().trim());
        
        if (judulBuku.isEmpty() || isbn.isEmpty() || namaPenulis.isEmpty() || namaPenerbit.isEmpty() || namaKategori.isEmpty() || genreBuku.isEmpty() || jumlahHalaman == 0) {
                JOptionPane.showMessageDialog(null, "Terdapat Data Yang Masih Kosong!");
                FieldInputISBN.requestFocus();
        } else {
            try {
                DbConnection ctd = new DbConnection();
                Connection conn = ctd.getConnection();
                String query = "DELETE FROM T_MasterBuku WHERE ISBN=?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, isbn);
                preparedStatement.executeUpdate();
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();
                clearinput();
                viewRole();
                JOptionPane.showMessageDialog(null, "Buku Berhasil Dihapus");
                
            } catch (ClassNotFoundException e){
                Logger.getLogger(MasterBukuFrame.class.getName()).log(Level.SEVERE, null, e);
            } catch (SQLException ex) {
                Logger.getLogger(MasterBukuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void clearinput(){
        FieldInputISBN.setText("");
        FieldInputJudul.setText("");
        FieldInputPenulis.setText("");
        FieldInputPenerbit.setText("");
        FieldInputKategori.setText("");
        FieldInputGenre.setText("");
        FieldInputHalaman.setText("");
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
        jLabel2 = new javax.swing.JLabel();
        FieldInputJudul = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelMasterBuku = new javax.swing.JTable();
        TombolSimpan = new javax.swing.JButton();
        TombolKeluar = new javax.swing.JButton();
        TombolDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        FieldInputISBN = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        FieldInputPenulis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        FieldInputPenerbit = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        FieldInputKategori = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        FieldInputGenre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        FieldInputHalaman = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Master Buku");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Judul Buku :");

        FieldInputJudul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputJudulActionPerformed(evt);
            }
        });

        TabelMasterBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ISBN", "Judul Buku", "Nama Penulis", "Nama Penerbit", "Nama Kategori", "Genre Buku", "Jumlah Halaman"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TabelMasterBuku.setShowGrid(true);
        TabelMasterBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMasterBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelMasterBuku);

        TombolSimpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TombolSimpan.setText("Tambahkan");
        TombolSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TombolSimpanActionPerformed(evt);
            }
        });

        TombolKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TombolKeluar.setText("Keluar");
        TombolKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TombolKeluarActionPerformed(evt);
            }
        });

        TombolDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TombolDelete.setText("Hapus");
        TombolDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TombolDeleteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ISBN :");

        FieldInputISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputISBNActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nama Penulis :");

        FieldInputPenulis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputPenulisActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nama Penerbit :");

        FieldInputPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputPenerbitActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Nama Kategori :");

        FieldInputKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputKategoriActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Genre Buku :");

        FieldInputGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputGenreActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Jumlah Halaman :");

        FieldInputHalaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldInputHalamanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TombolKeluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TombolSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TombolDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(FieldInputISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(FieldInputJudul)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(161, 161, 161)
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(FieldInputKategori, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                                            .addComponent(FieldInputPenerbit, javax.swing.GroupLayout.Alignment.TRAILING)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(FieldInputGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FieldInputHalaman))
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FieldInputPenulis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(23, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(FieldInputISBN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FieldInputJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(FieldInputPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(FieldInputPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(FieldInputKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FieldInputHalaman, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldInputGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TombolDelete)
                    .addComponent(TombolSimpan)
                    .addComponent(TombolKeluar))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TombolDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TombolDeleteActionPerformed
        int deleting = JOptionPane.showConfirmDialog(this, "Apakah Kamu Yakin Ingin Menghapus Buku Ini?");
        if(deleting == JOptionPane.YES_OPTION){
            deleteData();
        } else {
            JOptionPane.showMessageDialog(null, "Buku Berhasil Dihapus");
        }
              
    }//GEN-LAST:event_TombolDeleteActionPerformed

    private void TombolKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TombolKeluarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Apakah Kamu Yakin Ingin Keluar dari Master Buku?") == JOptionPane.YES_OPTION){
            this.setVisible(false);
            adminFrame.setEnabled(true);
            adminFrame.requestFocus();
        }
    }//GEN-LAST:event_TombolKeluarActionPerformed

    private void TombolSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TombolSimpanActionPerformed
        try {
            DbConnection ctd = new DbConnection();

            String isbn = FieldInputISBN.getText().trim();
            String judulBuku = FieldInputJudul.getText().trim();
            String namaPenulis = FieldInputPenulis.getText().trim();
            String namaPenerbit = FieldInputPenerbit.getText().trim();
            String namaKategori = FieldInputKategori.getText().trim();
            String genreBuku = FieldInputGenre.getText().trim();
            int jumlahHalaman = Integer.parseInt(FieldInputHalaman.getText().trim());

            if (judulBuku.isEmpty() || isbn.isEmpty() || namaPenulis.isEmpty() || namaPenerbit.isEmpty() || namaKategori.isEmpty() || genreBuku.isEmpty() || jumlahHalaman == 0) {
                JOptionPane.showMessageDialog(null, "Terdapat Data Yang Masih Kosong!");
                FieldInputISBN.requestFocus();
            } else {
                Connection conn = ctd.getConnection();

                // Cek apakah data dengan ISBN sudah ada
                String checkQuery = "SELECT COUNT(*) FROM T_MasterBuku WHERE ISBN = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setString(1, isbn);
                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    if (count > 0) {
                        // UPDATE jika data sudah ada
                        String updateQuery = "UPDATE T_MasterBuku SET Judul_Buku = ?, Nama_Penulis = ?, Nama_Penerbit = ?, Nama_Kategori = ?, Genre_Buku = ?, Jumlah_Halaman = ? WHERE ISBN = ?";
                        try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                            preparedStatement.setString(1, judulBuku);
                            preparedStatement.setString(2, namaPenulis);
                            preparedStatement.setString(3, namaPenerbit);
                            preparedStatement.setString(4, namaKategori);
                            preparedStatement.setString(5, genreBuku);
                            preparedStatement.setInt(6, jumlahHalaman);
                            preparedStatement.setString(7, isbn);

                            int dataUpdated = preparedStatement.executeUpdate();

                            if (dataUpdated > 0) {
                                model.getDataVector().removeAllElements();
                                model.fireTableDataChanged();
                                clearinput();
                                viewRole();
                                JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
                            } else {
                                JOptionPane.showMessageDialog(null, "Data Gagal Diperbarui");
                            }
                        }
                    } else {
                        // INSERT jika data belum ada
                        String insertQuery = "INSERT INTO T_MasterBuku (ISBN, Judul_Buku, Nama_Penulis, Nama_Penerbit, Nama_Kategori, Genre_Buku, Jumlah_Halaman) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                            preparedStatement.setString(1, isbn);
                            preparedStatement.setString(2, judulBuku);
                            preparedStatement.setString(3, namaPenulis);
                            preparedStatement.setString(4, namaPenerbit);
                            preparedStatement.setString(5, namaKategori);
                            preparedStatement.setString(6, genreBuku);
                            preparedStatement.setInt(7, jumlahHalaman);

                            int dataInserted = preparedStatement.executeUpdate();

                            if (dataInserted > 0) {
                                model.getDataVector().removeAllElements();
                                model.fireTableDataChanged();
                                clearinput();
                                viewRole();
                                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                            } else {
                                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MasterBukuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }                           
    }//GEN-LAST:event_TombolSimpanActionPerformed

    private void TabelMasterBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMasterBukuMouseClicked
        FieldInputISBN.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 0) + "");
        FieldInputJudul.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 1) + "");
        FieldInputPenulis.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 2) + "");
        FieldInputPenerbit.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 3) + "");
        FieldInputKategori.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 4) + "");
        FieldInputGenre.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 5) + "");
        FieldInputHalaman.setText(model.getValueAt(TabelMasterBuku.getSelectedRow(), 6) + "");
    }//GEN-LAST:event_TabelMasterBukuMouseClicked

    private void FieldInputJudulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputJudulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputJudulActionPerformed

    private void FieldInputISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputISBNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputISBNActionPerformed

    private void FieldInputPenulisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputPenulisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputPenulisActionPerformed

    private void FieldInputPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputPenerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputPenerbitActionPerformed

    private void FieldInputKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputKategoriActionPerformed

    private void FieldInputGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputGenreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputGenreActionPerformed

    private void FieldInputHalamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldInputHalamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldInputHalamanActionPerformed


    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FieldInputGenre;
    private javax.swing.JTextField FieldInputHalaman;
    private javax.swing.JTextField FieldInputISBN;
    private javax.swing.JTextField FieldInputJudul;
    private javax.swing.JTextField FieldInputKategori;
    private javax.swing.JTextField FieldInputPenerbit;
    private javax.swing.JTextField FieldInputPenulis;
    private javax.swing.JTable TabelMasterBuku;
    private javax.swing.JButton TombolDelete;
    private javax.swing.JButton TombolKeluar;
    private javax.swing.JButton TombolSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
