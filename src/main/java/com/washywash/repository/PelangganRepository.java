package com.washywash.repository;

import com.washywash.model.Pelanggan;
import java.util.List;

public interface PelangganRepository {
    void save(Pelanggan pelanggan);
    void update(Pelanggan pelanggan);
    void delete(String kodePelanggan);
    Pelanggan findById(String kodePelanggan);
    List<Pelanggan> findAll();
}
