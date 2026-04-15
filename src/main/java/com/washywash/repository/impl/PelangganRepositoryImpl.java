package com.washywash.repository.impl;

import com.washywash.config.DatabaseConnection;
import com.washywash.model.Pelanggan;
import com.washywash.repository.PelangganRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganRepositoryImpl implements PelangganRepository{
    @Override
    public void save(Pelanggan pelanggan) {
        String sql = "INSERT INTO pelanggan (kode_pelanggan, nama_pelanggan, no_hp, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelanggan.getKodePelanggan());
            ps.setString(2, pelanggan.getNamaPelanggan());
            ps.setString(3, pelanggan.getNoHp());
            ps.setString(4, pelanggan.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menyimpan pelanggan: " + e.getMessage());
        }
    }

    @Override
    public void update(Pelanggan pelanggan) {
        String sql = "UPDATE pelanggan SET nama_pelanggan=?, no_hp=?, email=? WHERE kode_pelanggan=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelanggan.getNamaPelanggan());
            ps.setString(2, pelanggan.getNoHp());
            ps.setString(3, pelanggan.getEmail());
            ps.setString(4, pelanggan.getKodePelanggan());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengupdate pelanggan: " + e.getMessage());
        }
    }

    @Override
    public void delete(String kodePelanggan) {
        String sql = "DELETE FROM pelanggan WHERE kode_pelanggan=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePelanggan);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus pelanggan: " +
            e.getMessage());
        }
    }

    @Override
    public Pelanggan findById(String kodePelanggan) {
        String sql = "SELECT * FROM pelanggan WHERE kode_pelanggan=?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePelanggan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            return new Pelanggan(
            rs.getString("kode_pelanggan"),
            rs.getString("nama_pelanggan"),
            rs.getString("no_hp"),
            rs.getString("email")
            );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari pelanggan: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pelanggan> findAll() {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY kode_pelanggan ASC";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
            Pelanggan pelanggan = new Pelanggan(
            rs.getString("kode_pelanggan"),
            rs.getString("nama_pelanggan"),
            rs.getString("no_hp"),
            rs.getString("email")
            );
            list.add(pelanggan);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data pelanggan: " + e.getMessage());
        }
        return list;
    }
}
