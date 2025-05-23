package com.bookstore.data;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

/**
 * Kelas ini digunakan untuk membuat dan menjalankan query database.
 */
public class QuerySelector {
    private MysqlConnection mysqlConnection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int affectedRows;

    public QuerySelector() {
        mysqlConnection = new MysqlConnection();
    }

    // ==================== SELECT ====================

    /**
     * Ambil semua data purchase order beserta nama pegawai, nama vendor, dan judul buku.
     * Query ini menggunakan JOIN ke beberapa tabel untuk menampilkan data yang lengkap.
     */
    public void selectAllPurchaseOrder() throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "po.id_PO, " +
            "po.id_pegawai, " +
            "ap.nama AS nama_pegawai, " +
            "po.id_vendor, " +
            "v.nama_vendor, " +
            "po.isbn, " +
            "mb.judul_buku, " +
            "po.tanggal_PO, " +
            "po.estimasi_tanggal_datang, " +
            "po.jumlah_PO, " +
            "po.jumlah_diterima, " +
            "po.total_biaya, " +
            "po.status_PO " +
            "FROM T_PurchaseOrder po " +
            "JOIN T_AkunPegawai ap ON po.id_pegawai = ap.id_pegawai " +
            "JOIN T_MasterBuku mb ON po.isbn = mb.isbn " +
            "JOIN T_Vendor v ON po.id_vendor = v.id_vendor " +
            "ORDER BY po.id_PO ASC";
        
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectPegawaiAndRole() throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "pg.id_pegawai, " +
            "pg.nama, " +
            "pg.password, " +
            "pg.id_role, " +
            "r.nama_role, " +
            "pg.tanggal_buat_akun, " +
            "pg.tanggal_terakhir_masuk_akun " +
            "FROM T_AkunPegawai pg " +
            "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
            "ORDER BY pg.id_pegawai ASC";
        
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
    }
    
    // mengambil data pada tabel pegawai dan role
    public void selectPegawaiAndRoleByRoleFilter(String roleName) throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "pg.id_pegawai, " +
            "pg.nama, " +
            "pg.password, " +
            "pg.id_role, " +
            "r.nama_role, " +
            "pg.tanggal_buat_akun, " +
            "pg.tanggal_terakhir_masuk_akun " +
            "FROM T_AkunPegawai pg " +
            "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
            "WHERE r.nama_role = ? " +
            "ORDER BY pg.id_pegawai ASC";
        
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, roleName);
        resultSet = preparedStatement.executeQuery();
    }

    public void selectDetailMasterBuku(String invType) throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "dm.id_detail_master_buku, " +
            "dm.isbn, " +
            "mb.judul_buku, " +
            "dm.kode_rak, " +
            "rk.nama_rak, " +
            "dm.id_vendor, " +
            "v.nama_vendor, " +
            "dm.stock_buku, " +
            "dm.tanggal_update_stock, " +
            "dm.harga_satuan, " +
            "dm.jenis_inventaris " +
            "FROM " +
            "T_Detailmasterbuku dm " +
            "LEFT " +
            "JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
            "JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
            "JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
            "WHERE dm.jenis_inventaris = ? " +
            "ORDER BY dm.id_detail_master_buku ASC";
        
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, invType);
        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectDetailMasterBukuByVendorFilter(String vendorName, String invType) throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "dm.id_detail_master_buku, " +
            "dm.isbn, " +
            "mb.judul_buku, " +
            "dm.kode_rak, " +
            "rk.nama_rak, " +
            "dm.id_vendor, " +
            "v.nama_vendor, " +
            "dm.stock_buku, " +
            "dm.tanggal_update_stock, " +
            "dm.harga_satuan, " +
            "dm.jenis_inventaris " +
            "FROM " +
            "T_Detailmasterbuku dm " +
            "LEFT " +
            "JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
            "JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
            "JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
            "WHERE dm.jenis_inventaris = ? AND v.nama_vendor = ? " +
            "ORDER BY dm.id_detail_master_buku ASC";
        
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, invType);
        preparedStatement.setString(2, vendorName);
        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectPurchaseOrderByStatus(String status) throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "po.Id_PO, " +
            "po.id_pegawai, " +
            "p.nama, " +
            "po.id_vendor, " +
            "v.nama_vendor, " +
            "po.isbn, " +
            "mb.judul_buku, " +
            "po.tanggal_po, " +
            "po.estimasi_tanggal_datang, " +
            "po.jumlah_po, " +
            "po.jumlah_diterima, " +
            "po.total_biaya, " +
            "po.status_po " +
            "FROM t_purchaseorder po " +
            "LEFT JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
            "JOIN t_akunpegawai p ON po.id_pegawai = p.id_pegawai " +
            "JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
            "WHERE po.status_po = ? " +
            "ORDER BY po.Id_PO ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, status);
        resultSet = preparedStatement.executeQuery();
    }

    public void selectPurchaseOrderByTwoStatus(String status1, String status2) throws ClassNotFoundException, SQLException {
        String query =
            "SELECT " +
            "po.Id_PO, " +
            "po.id_pegawai, " +
            "p.nama, " +
            "po.id_vendor, " +
            "v.nama_vendor, " +
            "po.isbn, " +
            "mb.judul_buku, " +
            "po.tanggal_po, " +
            "po.estimasi_tanggal_datang, " +
            "po.jumlah_po, " +
            "po.jumlah_diterima, " +
            "po.total_biaya, " +
            "po.status_po " +
            "FROM t_purchaseorder po " +
            "LEFT JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
            "JOIN t_akunpegawai p ON po.id_pegawai = p.id_pegawai " +
            "JOIN t_masterbuku mb ON po.isbn = mb.isbn " +
            "WHERE po.status_po IN (?, ?) " +
            "ORDER BY po.Id_PO ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, status1);
        preparedStatement.setString(2, status2);
        resultSet = preparedStatement.executeQuery();
    }

    
    /**
     * Ambil semua data dari sebuah tabel.
     */
    public void selectAllFromTable(String tableName) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName + " ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Ambil semua data dari tabel berdasarkan satu nilai kolom.
     */
    public void selectAllByColumn(String tableName, String columnName, String columnValue) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ? ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, columnValue);
        resultSet = preparedStatement.executeQuery();
    }
    
    // untuk menampilkan semua data dari tabel berdasarkan 2 nilai kolom
    public void selectAllByTwoColumns(String tableName, String column1, String value1, String column2, String value2) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ? ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        resultSet = preparedStatement.executeQuery();
    }
    
    // untuk menampilkan 1 kolom berdasarkan kolom tertentu di tabel
    public void selectOneColumnByOneKey(String selectedColumn, String tableName, String columnName, String columnValue) throws SQLException, ClassNotFoundException {
        String query = "SELECT " + selectedColumn + " FROM " + tableName + " WHERE " + columnName + " = ? ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, columnValue);
        resultSet = preparedStatement.executeQuery();
    }
    
    // untuk menampilkan 1 kolom berdasarkan kolom tertentu di tabel
    public void selectOneColumnByTwoKeys(String selectedColumn, String tableName, String column1, String value1, String column2, String value2) throws SQLException, ClassNotFoundException {
        String query = "SELECT " + selectedColumn + " FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ? ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectOneColumnByThreeKeys(String selectedColumn, String tableName, String column1, String value1, String column2, String value2, String column3, String value3) throws ClassNotFoundException, SQLException{
        String query = "SELECT " + selectedColumn + " FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ? AND " + column3 + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2); 
        preparedStatement.setString(3, value3);
        resultSet = preparedStatement.executeQuery();
    }
    
    // untuk menampilkan 2 kolom berdasarkan 3 nilai kolom tertentu di tabel
    public void selectTwoColumnsByThreeKeys(String selectedColumn1, String selectedColumn2, String tableName, String column1, String value1, String column2, String value2, String column3, String value3) throws SQLException, ClassNotFoundException {
        String query = "SELECT " + selectedColumn1 + ", " + selectedColumn2 + " FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ? AND " + column3 + " = ? ORDER BY 1 ASC";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        preparedStatement.setString(3, value3);
        resultSet = preparedStatement.executeQuery();
    }
    

    // =========================== COUNT ===============================
    
    /**
     * Hitung jumlah data berdasarkan satu kolom dan nilainya.
     */
    public void countDataByColumn(String tableName, String columnName, String columnValue) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, columnValue);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            affectedRows = resultSet.getInt(1);
        }
    }
    
    // ========================== LIKE ===============================
    
        // untuk melakukan pencarian 1 nilai secara dinamis perhuruf
    public void selectAllByColumnsLike(String tableName, String column1, String value1) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName + " WHERE " + column1 + " LIKE ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + value1 + "%");

        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectLikeNoJoin(String tableName, String col1, String col2, String val) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName +
                       " WHERE " + col1 + " LIKE ? OR " + col2 + " LIKE ?";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");

        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectLikeAndFilterNoJoin(String tableName, String col1, String col2, String val, String col3, String val3) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + tableName +
                       " WHERE (" + col1 + " LIKE ? OR " + col2 + " LIKE ?) AND " + col3 + " = ?";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");
        preparedStatement.setString(3, val3);

        resultSet = preparedStatement.executeQuery();
    }
    
    // =================== JOIN =======================
    
    // akun pegawai
    public void selectLikeWithJoinAkunPegawai(String col1, String col2, String val) throws SQLException, ClassNotFoundException {
        String query = "SELECT " +
                       "pg.id_pegawai, pg.nama, pg.password, pg.id_role, r.nama_role AS Nama_Role, " +
                       "pg.tanggal_buat_akun, pg.tanggal_terakhir_masuk_akun " +
                       "FROM T_AkunPegawai pg " +
                       "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
                       "WHERE pg." + col1 + " LIKE ? OR pg." + col2 + " LIKE ? " +
                       "ORDER BY pg.id_pegawai ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");

        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectLikeWithJoinAndFilterAkunPegawai(String col1, String col2, String val, String col3, String val3) throws SQLException, ClassNotFoundException {
        String query = "SELECT " +
                       "pg.id_pegawai, pg.nama, pg.password, pg.id_role, r.nama_role, " +
                       "pg.tanggal_buat_akun, pg.tanggal_terakhir_masuk_akun " +
                       "FROM T_AkunPegawai pg " +
                       "LEFT JOIN T_Role r ON pg.id_role = r.id_role " +
                       "WHERE (pg." + col1 + " LIKE ? OR pg." + col2 + " LIKE ?) AND pg." + col3 + " = ? " +
                       "ORDER BY pg.id_pegawai ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");
        preparedStatement.setString(3, val3);

        resultSet = preparedStatement.executeQuery();
    }
    
    // detailmasterbuku
    public void selectLikeWithJoinDetailMasterBuku(String col1, String col2, String val, String invType) throws SQLException, ClassNotFoundException {
        String query =
            "SELECT " +
            "dm.id_detail_master_buku, dm.isbn, mb.judul_buku, " +
            "dm.kode_rak, rk.nama_rak, " +
            "dm.id_vendor, v.nama_vendor, " +
            "dm.stock_buku, dm.tanggal_update_stock, " +
            "dm.harga_satuan, dm.jenis_inventaris " +
            "FROM T_Detailmasterbuku dm " +
            "LEFT JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
            "LEFT JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
            "LEFT JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
            "WHERE (dm." + col1 + " LIKE ? OR mb." + col2 + " LIKE ?) " +
            "AND dm.jenis_inventaris = ? " +
            "ORDER BY dm.id_detail_master_buku ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");
        preparedStatement.setString(3, invType);

        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectLikeWithJoinAndFilterDetailMasterBuku(String col1, String col2, String val, String col3, String val3, String invType) throws SQLException, ClassNotFoundException {
        String query =
            "SELECT " +
            "dm.id_detail_master_buku, dm.isbn, mb.judul_buku, " +
            "dm.kode_rak, rk.nama_rak, " +
            "dm.id_vendor, v.nama_vendor, " +
            "dm.stock_buku, dm.tanggal_update_stock, " +
            "dm.harga_satuan, dm.jenis_inventaris " +
            "FROM T_Detailmasterbuku dm " +
            "LEFT JOIN T_Masterbuku mb ON dm.isbn = mb.isbn " +
            "LEFT JOIN T_Rak rk ON dm.kode_rak = rk.kode_rak " +
            "LEFT JOIN T_Vendor v ON dm.id_vendor = v.id_vendor " +
            "WHERE (dm." + col1 + " LIKE ? OR mb." + col2 + " LIKE ?) AND dm." + col3 + " = ? " +
            "AND dm.jenis_inventaris = ? " +
            "ORDER BY dm.id_detail_master_buku ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");
        preparedStatement.setString(3, val3);
        preparedStatement.setString(4, invType);

        resultSet = preparedStatement.executeQuery();
    }
    
    public void selectLikeWithJoinPurchaseOrder(String col1, String col2, String val) throws SQLException, ClassNotFoundException {
        String query =
            "SELECT " +
            "po.Id_PO, po.id_pegawai, po.id_vendor, v.nama_vendor, po.isbn, " +
            "po.tanggal_po, po.estimasi_tanggal_datang, po.jumlah_po, po.total_biaya, po.status_po " +
            "FROM t_purchaseorder po " +
            "JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
            "WHERE po." + col1 + " LIKE ? OR po." + col2 + " LIKE ? " +
            "ORDER BY po.Id_PO ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");

        resultSet = preparedStatement.executeQuery();
    }

    public void selectLikeWithJoinAndFilterPurchaseOrder(String col1, String col2, String val, String col3, String val3) throws SQLException, ClassNotFoundException {
        String query =
            "SELECT " +
            "po.Id_PO, po.id_pegawai, po.id_vendor, v.nama_vendor, po.isbn, " +
            "po.tanggal_po, po.estimasi_tanggal_datang, po.jumlah_po, po.total_biaya, po.status_po " +
            "FROM t_purchaseorder po " +
            "JOIN t_vendor v ON po.id_vendor = v.id_vendor " +
            "WHERE (po." + col1 + " LIKE ? OR po." + col2 + " LIKE ?) " +
            "AND po." + col3 + " = ? " +
            "ORDER BY po.Id_PO ASC";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, "%" + val + "%");
        preparedStatement.setString(2, "%" + val + "%");
        preparedStatement.setString(3, val3);

        resultSet = preparedStatement.executeQuery();
    }


    // ==================== INSERT ====================

    // menambahkan PO setelah check ketersediaan PO
    public void insertPurchaseOrder(String purchaseOrderNumber, String employeeId, String vendorId, String isbn, LocalDate purchaseDate, LocalDate estimatedArrivalDate, int orderQuantity, int recievedQuantity, BigDecimal totalCost, String orderStatus) throws SQLException, ClassNotFoundException {
        this.mysqlConnection = new MysqlConnection();

        String query  = "INSERT INTO t_purchaseorder " +
                           "(id_PO, id_pegawai, id_vendor, isbn, tanggal_po, estimasi_tanggal_datang, jumlah_po, jumlah_diterima, total_biaya, status_po) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        this.preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, purchaseOrderNumber);
        preparedStatement.setString(2, employeeId);
        preparedStatement.setString(3, vendorId);
        preparedStatement.setString(4, isbn);
        preparedStatement.setDate(5, java.sql.Date.valueOf(purchaseDate));
        preparedStatement.setDate(6, java.sql.Date.valueOf(estimatedArrivalDate));
        preparedStatement.setInt(7, orderQuantity);
        preparedStatement.setInt(8, recievedQuantity);
        preparedStatement.setBigDecimal(9, totalCost);
        preparedStatement.setString(10, orderStatus);

        this.affectedRows = preparedStatement.executeUpdate(); // untuk menyimpan status eksekusi (jumlah baris terpengaruh)
    }
    
    /**
     * Tambah data dengan 2 kolom.
     */
    public void insertTwoColumns(String tableName, String column1, String column2, String value1, String value2) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + tableName + " (" + column1 + ", " + column2 + ") VALUES (?, ?)";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        affectedRows = preparedStatement.executeUpdate();
    }

    /**
     * Tambah data dengan 3 kolom.
     */
    public void insertThreeColumns(String tableName, String column1, String column2, String column3, String value1, String value2, String value3) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + tableName + " (" + column1 + ", " + column2 + ", " + column3 + ") VALUES (?, ?, ?)";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        preparedStatement.setString(3, value3);
        affectedRows = preparedStatement.executeUpdate();
    }

    /**
     * Tambah data dengan 4 kolom.
     */
    public void insertFourColumns(String tableName, String column1, String column2, String column3, String column4, String value1, String value2, String value3, String value4) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + tableName + " (" + column1 + ", " + column2 + ", " + column3 + ", " + column4 + ") VALUES (?, ?, ?, ?)";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        preparedStatement.setString(3, value3);
        preparedStatement.setString(4, value4);
        affectedRows = preparedStatement.executeUpdate();
    }
    
    public void setInsert5Columns(String table, String col1, String col2, String col3, String col4, String col5, String val1, String val2, String val3, String val4, String val5) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + table + " (" + col1 + ", " + col2 + ", " + col3 + ", " + col4 + ", " + col5 + ") VALUES (?, ?, ?, ?, ?)";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, val1);
        preparedStatement.setString(2, val2);
        preparedStatement.setString(3, val3);
        preparedStatement.setString(4, val4);
        preparedStatement.setString(5, val5);
        affectedRows = preparedStatement.executeUpdate();
    }
    
    public void insertReceivedPO(String idPenerimaanPO, String idPO, String isbn, LocalDate tanggalTerima, int jumlahDatang, BigDecimal totalHarga, String keteranganPenerimaan, String status) throws SQLException, ClassNotFoundException {

        String query = "INSERT INTO t_penerimaanpurchaseorder " +
                       "(Id_penerimaan_PO, id_po, isbn, tanggal_terima, jumlah_datang, total_harga, keterangan_penerimaan, status_penerimaan) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, idPenerimaanPO);
        preparedStatement.setString(2, idPO);
        preparedStatement.setString(3, isbn);
        preparedStatement.setDate(4, java.sql.Date.valueOf(tanggalTerima)); // Jika ingin pakai java.sql.Date, gunakan setDate()
        preparedStatement.setInt(5, jumlahDatang);
        preparedStatement.setBigDecimal(6, totalHarga);
        preparedStatement.setString(7, keteranganPenerimaan);
        preparedStatement.setString(8, status);

        affectedRows = preparedStatement.executeUpdate();
    }


    // ==================== UPDATE ====================

    /**
     * Ubah data pada 1 kolom berdasarkan kondisi satu kolom.
     */
    public void updateOneColumn(String tableName, String column1, String value1, String column2, String value2) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + tableName + " SET " + column1 + " = ? WHERE " + column2 + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        affectedRows = preparedStatement.executeUpdate();
    }
    
    public void updateReceviedQty(int qty, String idPo) throws SQLException, ClassNotFoundException {
        String query = "UPDATE T_PurchaseOrder SET jumlah_diterima = ? WHERE id_po = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, qty);
        preparedStatement.setString(2, idPo);
        affectedRows = preparedStatement.executeUpdate();
    }

    /**
     * Ubah data pada 2 kolom berdasarkan kondisi satu kolom.
     */
    public void updateTwoColumns(String tableName, String column1, String value1, String column2, String value2, String conditionColumn, String conditionValue) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + tableName + " SET " + column1 + " = ?, " + column2 + " = ? WHERE " + conditionColumn + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        preparedStatement.setString(3, conditionValue);
        affectedRows = preparedStatement.executeUpdate();
    }

    /**
     * Ubah data pada 3 kolom berdasarkan kondisi satu kolom.
     */
    public void updateThreeColumns(String tableName, String column1, String value1, String column2, String value2, String column3, String value3, String conditionColumn, String conditionValue) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + tableName + " SET " + column1 + " = ?, " + column2 + " = ?, " + column3 + " = ? WHERE " + conditionColumn + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, value1);
        preparedStatement.setString(2, value2);
        preparedStatement.setString(3, value3);
        preparedStatement.setString(4, conditionValue);
        affectedRows = preparedStatement.executeUpdate();
    }
    
    // untuk update data stock dan tanggal update stock
    public void updateStockAndDate(String tableName, String column1, int value1, String column2, Date value2, String conditionColumn1, String conditionValue1, String conditionColumn2, String conditionValue2) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + tableName + " SET " + column1 + " = ?, " + column2 + " = ? WHERE " + conditionColumn1 + " = ? AND " + conditionColumn2 + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, value1);
        preparedStatement.setDate(2, value2);
        preparedStatement.setString(3, conditionValue1);
        preparedStatement.setString(4, conditionValue2);
        affectedRows = preparedStatement.executeUpdate();
    }
    
    // untuk mengubah data yang berelasi menjadi null
    public void setColumnToNullByCondition(String tableName, String targetColumn, String conditionColumn, String conditionValue) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + tableName + " SET " + targetColumn + " = NULL WHERE " + conditionColumn + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, conditionValue);
        affectedRows = preparedStatement.executeUpdate();
    }

    public void updateTwoColumns(String table, String[] columns, Object[] values, String keyColumn, String keyValue) throws SQLException, ClassNotFoundException {
        if (columns.length != 2 || values.length != 2) {
            throw new IllegalArgumentException("Harus tepat 2 kolom dan 2 nilai.");
        }

        String query = "UPDATE " + table + " SET " + columns[0] + " = ?, " + columns[1] + " = ? WHERE " + keyColumn + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setObject(1, values[0]);
        preparedStatement.setObject(2, values[1]);
        preparedStatement.setString(3, keyValue);
        preparedStatement.executeUpdate();
    }

    
    // ==================== DELETE ====================

    /**
     * Hapus data dari tabel berdasarkan satu kolom.
     */
    public void deleteByKey(String tableName, String columnName, String columnValue) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        preparedStatement = mysqlConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, columnValue);
        affectedRows = preparedStatement.executeUpdate();
    }

    // ==================== GETTER ====================

    /**
     * Ambil hasil SELECT.
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Ambil hasil COUNT, INSERT, UPDATE, DELETE.
     */
    public int getAffectedRows() {
        return affectedRows;
    }
}
