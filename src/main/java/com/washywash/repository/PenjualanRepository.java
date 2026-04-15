package com.washywash.repository;

import com.washywash.model.Penjualan;
import java.util.List;

public interface PenjualanRepository {
    void save(Penjualan penjualan);
    void update(Penjualan penjualan);
    void delete(String kodePenjualan);
    Penjualan findById(String kodePenjualan);
    List<Penjualan> findAll();
}
