package com.washywash.service;

import com.washywash.model.DetailPenjualan;
import com.washywash.repository.DetailPenjualanRepository;

import java.util.List;

public class DetailPenjualanService {
    private final DetailPenjualanRepository detailRepository;

    public DetailPenjualanService(DetailPenjualanRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public void tambahDetail(DetailPenjualan detail) {
        validateDetail(detail);
        detailRepository.save(detail);
    }

    public void hapusByPenjualan(String kodePenjualan) {
        if (kodePenjualan == null || kodePenjualan.isBlank()) {
            throw new IllegalArgumentException("Kode penjualan wajib diisi.");
        }

        detailRepository.deleteByPenjualan(kodePenjualan);
    }

    public List<DetailPenjualan> getByPenjualan(String kodePenjualan) {
        if (kodePenjualan == null || kodePenjualan.isBlank()) {
            throw new IllegalArgumentException("Kode penjualan wajib diisi.");
        }

        return detailRepository.findByPenjualan(kodePenjualan);
    }

    public double hitungTotal(String kodePenjualan) {
        List<DetailPenjualan> list = detailRepository.findByPenjualan(kodePenjualan);

        double total = 0;
        for (DetailPenjualan d : list) {
            total += d.getSubtotal();
        }

        return total;
    }

    private void validateDetail(DetailPenjualan detail) {
        if (detail.getPenjualan() == null) {
            throw new IllegalArgumentException("Penjualan wajib diisi.");
        }

        if (detail.getBarang() == null) {
            throw new IllegalArgumentException("Barang wajib diisi.");
        }

        if (detail.getQty() <= 0) {
            throw new IllegalArgumentException("Qty harus lebih dari 0.");
        }

        if (detail.getHarga() < 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0.");
        }

        if (detail.getPenjualan().getKodePenjualan() == null ||
            detail.getPenjualan().getKodePenjualan().isBlank()) {
            throw new IllegalArgumentException("Kode penjualan wajib diisi.");
        }

        if (detail.getBarang().getKodeBarang() == null ||
            detail.getBarang().getKodeBarang().isBlank()) {
            throw new IllegalArgumentException("Kode barang wajib diisi.");
        }
    }
}