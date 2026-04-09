package com.washywash.repository;

import com.washywash.model.Barang;
import java.util.List;

public interface BarangRepository {
    void save(Barang barang);
    void update(Barang barang);
    void delete(String kodeBarang);
    Barang findById(String kodeBarang);
    List<Barang> findAll();
}
