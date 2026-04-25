package com.washywash.repository.impl;

import com.washywash.config.DatabaseConnection;
import com.washywash.model.Barang;
import com.washywash.model.DetailPenjualan;
import com.washywash.model.Penjualan;
import com.washywash.repository.DetailPenjualanRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailPenjualanRepositoryImpl implements DetailPenjualanRepository {
    @Override
    public void save(DetailPenjualan detail) {
        String sql = "INSERT INTO detail_penjualan (kode_penjualan, kode_barang, qty, harga, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, detail.getPenjualan().getKodePenjualan());
            ps.setString(2, detail.getBarang().getKodeBarang());
            ps.setInt(3, detail.getQty());
            ps.setDouble(4, detail.getHarga());
            ps.setDouble(5, detail.getSubtotal());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal simpan detail penjualan: " + e.getMessage());
        }
    }

    @Override
    public void hapusDariPenjualanDanBarang(String kodePenjualan, String kodeBarang) {
        String sql = "DELETE FROM detail_penjualan WHERE kode_penjualan=? AND kode_barang=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePenjualan);
            ps.setString(2, kodeBarang);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal hapus detail penjualan: " + e.getMessage());
        }
    }

    @Override
    public List<DetailPenjualan> findByPenjualan(String kodePenjualan) {
        List<DetailPenjualan> list = new ArrayList<>();

        String sql = "SELECT * FROM detail_penjualan WHERE kode_penjualan=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePenjualan);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setKodePenjualan(rs.getString("kode_penjualan"));

                Barang barang = new Barang();
                barang.setKodeBarang(rs.getString("kode_barang"));

                DetailPenjualan detail = new DetailPenjualan(
                    penjualan,
                    barang,
                    rs.getInt("qty"),
                    rs.getDouble("harga")
                );

                list.add(detail);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal ambil detail penjualan: " + e.getMessage());
        }
        return list;
    }

    @Override
    public double sumByPenjualan(String kodePenjualan) {
        String sql = "SELECT SUM(subtotal) AS total FROM detail_penjualan WHERE kode_penjualan=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePenjualan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Gagal hitung total detail: " + e.getMessage());
        }

        return 0;
    }
}