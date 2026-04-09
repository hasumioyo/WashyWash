package com.washywash.service;

import com.washywash.model.Barang;
import com.washywash.repository.BarangRepository;

import java.util.List;

public class BarangService {
    private final BarangRepository barangRepository;

    public BarangService(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    public void tambahBarang(Barang barang) {
        validateBarang(barang);

        if (barangRepository.findById(barang.getKodeBarang()) != null) {
            throw new IllegalArgumentException("Kode barang sudah ada.");
        }

        barangRepository.save(barang);
    }

    public void updateBarang(Barang barang) {
        validateBarang(barang);

        if (barangRepository.findById(barang.getKodeBarang()) == null) {
            throw new IllegalArgumentException("Barang tidak ditemukan.");
        }

        barangRepository.update(barang);
    }

    public void hapusBarang(String kodeBarang) {
        if (kodeBarang == null || kodeBarang.isBlank()) {
            throw new IllegalArgumentException("Kode barang tidak boleh kosong.");
        }

        if (barangRepository.findById(kodeBarang) == null) {
            throw new IllegalArgumentException("Barang tidak ditemukan.");
        }

        barangRepository.delete(kodeBarang);
    }

    public Barang cariBarang(String kodeBarang) {
        if (kodeBarang == null || kodeBarang.isBlank()) {
            throw new IllegalArgumentException("Kode barang tidak boleh kosong.");
        }

        return barangRepository.findById(kodeBarang);
    }

    public List<Barang> getSemuaBarang() {
        return barangRepository.findAll();
    }

    private void validateBarang(Barang barang) {
        if (barang.getKodeBarang() == null || barang.getKodeBarang().isBlank()) {
            throw new IllegalArgumentException("Kode barang wajib diisi.");
        }

        if (barang.getNamaBarang() == null || barang.getNamaBarang().isBlank()) {
            throw new IllegalArgumentException("Nama barang wajib diisi.");
        }

        if (barang.getJenisBarang() == null || barang.getJenisBarang().isBlank()) {
            throw new IllegalArgumentException("Jenis barang wajib diisi.");
        }

        if (barang.getSatuan() == null || barang.getSatuan().isBlank()) {
            throw new IllegalArgumentException("Satuan wajib diisi.");
        }

        if (barang.getHarga() < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        }

        if (barang.getStok() < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
    }
}
