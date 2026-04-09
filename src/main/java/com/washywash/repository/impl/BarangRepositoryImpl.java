package com.washywash.repository.impl;

import com.washywash.config.DatabaseConnection;
import com.washywash.model.Barang;
import com.washywash.repository.BarangRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangRepositoryImpl implements BarangRepository{
    @Override
    public void save(Barang barang) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, jenis_barang, satuan, harga, stok) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getKodeBarang());
            ps.setString(2, barang.getNamaBarang());
            ps.setString(3, barang.getJenisBarang());
            ps.setString(4, barang.getSatuan());
            ps.setDouble(5, barang.getHarga());
            ps.setInt(6, barang.getStok());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menyimpan barang: " + e.getMessage());
        }
    }

    @Override
    public void update(Barang barang) {
        String sql = "UPDATE barang SET nama_barang=?, jenis_barang=?, satuan=?, harga=?, stok=? WHERE kode_barang=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setString(2, barang.getJenisBarang());
            ps.setString(3, barang.getSatuan());
            ps.setDouble(4, barang.getHarga());
            ps.setInt(5, barang.getStok());
            ps.setString(6, barang.getKodeBarang());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengupdate barang: " + e.getMessage());
        }
    }

    @Override
    public void delete(String kodeBarang) {
        String sql = "DELETE FROM barang WHERE kode_barang=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeBarang);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus barang: " +
            e.getMessage());
        }
    }

    @Override
    public Barang findById(String kodeBarang) {
        String sql = "SELECT * FROM barang WHERE kode_barang=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeBarang);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            return new Barang(
            rs.getString("kode_barang"),
            rs.getString("nama_barang"),
            rs.getString("jenis_barang"),
            rs.getString("satuan"),
            rs.getDouble("harga"),
            rs.getInt("stok")
            );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari barang: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Barang> findAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang ORDER BY kode_barang ASC";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
            Barang barang = new Barang(
            rs.getString("kode_barang"),
            rs.getString("nama_barang"),
            rs.getString("jenis_barang"),
            rs.getString("satuan"),
            rs.getDouble("harga"),
            rs.getInt("stok")
            );
            list.add(barang);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data barang: " + e.getMessage());
        }
        return list;
    }
}
