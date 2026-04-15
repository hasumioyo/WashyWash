package com.washywash.repository;

import com.washywash.model.DetailPenjualan;
import java.util.List;

public interface DetailPenjualanRepository {
    void save(DetailPenjualan detail);
    void deleteByPenjualan(String kodePenjualan);
    List<DetailPenjualan> findByPenjualan(String kodePenjualan);

    double sumByPenjualan(String kodePenjualan);
}
