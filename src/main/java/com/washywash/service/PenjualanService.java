package com.washywash.service;

import com.washywash.model.Penjualan;
import com.washywash.repository.PenjualanRepository;

import java.util.List;

public class PenjualanService {
    private final PenjualanRepository penjualanRepository;
    private final DetailPenjualanService detailService;

    public PenjualanService(PenjualanRepository penjualanRepository, DetailPenjualanService detailService) {
        this.penjualanRepository = penjualanRepository;
        this.detailService = detailService;
    }

    public void tambahPenjualan(Penjualan penjualan) {
        validatePenjualan(penjualan);

        if (penjualanRepository.findById(penjualan.getKodePenjualan()) != null) {
            throw new IllegalArgumentException("Kode penjualan sudah ada.");
        }

        double total = detailService.hitungTotal(penjualan.getKodePenjualan());
        penjualan.setTotal(total - penjualan.getDiskon());

        penjualanRepository.save(penjualan);
    }

    public void updatePenjualan(Penjualan penjualan) {
        validatePenjualan(penjualan);

        if (penjualanRepository.findById(penjualan.getKodePenjualan()) == null) {
            throw new IllegalArgumentException("Penjualan tidak ditemukan.");
        }

        penjualan.setTotal(penjualan.hitungTotal());

        penjualanRepository.update(penjualan);
    }

    public void hapusPenjualan(String kodePenjualan) {
        if (kodePenjualan == null || kodePenjualan.isBlank()) {
            throw new IllegalArgumentException("Kode penjualan tidak boleh kosong.");
        }

        if (penjualanRepository.findById(kodePenjualan) == null) {
            throw new IllegalArgumentException("Penjualan tidak ditemukan.");
        }

        penjualanRepository.delete(kodePenjualan);
    }

    public Penjualan cariPenjualan(String kodePenjualan) {
        if (kodePenjualan == null || kodePenjualan.isBlank()) {
            throw new IllegalArgumentException("Kode penjualan tidak boleh kosong.");
        }

        return penjualanRepository.findById(kodePenjualan);
    }

    public List<Penjualan> getAllPenjualan() {
        return penjualanRepository.findAll();
    }

    private void validatePenjualan(Penjualan p) {

        if (p.getKodePenjualan() == null || p.getKodePenjualan().isBlank()) {
            throw new IllegalArgumentException("Kode penjualan wajib diisi.");
        }

        if (p.getTanggal() == null) {
            throw new IllegalArgumentException("Tanggal wajib diisi.");
        }

        if (p.getPelanggan() == null ||
            p.getPelanggan().getKodePelanggan() == null ||
            p.getPelanggan().getKodePelanggan().isBlank()) {
            throw new IllegalArgumentException("Pelanggan wajib diisi.");
        }
    }
}