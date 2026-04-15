package com.washywash.service;

import com.washywash.model.Pelanggan;
import com.washywash.repository.PelangganRepository;

import java.util.List;

public class PelangganService {
    private final PelangganRepository pelangganRepository;

    public PelangganService(PelangganRepository pelangganRepository) {
        this.pelangganRepository = pelangganRepository;
    }

    public void tambahPelanggan(Pelanggan pelanggan) {
        validatePelanggan(pelanggan);

        if (pelangganRepository.findById(pelanggan.getKodePelanggan()) != null) {
            throw new IllegalArgumentException("Kode pelanggan sudah ada.");
        }

        pelangganRepository.save(pelanggan);
    }

    public void updatePelanggan(Pelanggan pelanggan) {
        validatePelanggan(pelanggan);

        if (pelangganRepository.findById(pelanggan.getKodePelanggan()) == null) {
            throw new IllegalArgumentException("Pelanggan tidak ditemukan.");
        }

        pelangganRepository.update(pelanggan);
    }

    public void hapusPelanggan(String kodePelanggan) {
        if (kodePelanggan == null || kodePelanggan.isBlank()) {
            throw new IllegalArgumentException("Kode pelanggan tidak boleh kosong.");
        }

        if (pelangganRepository.findById(kodePelanggan) == null) {
            throw new IllegalArgumentException("Pelanggan tidak ditemukan.");
        }

        pelangganRepository.delete(kodePelanggan);
    }

    public Pelanggan cariPelanggan(String kodePelanggan) {
        if (kodePelanggan == null || kodePelanggan.isBlank()) {
            throw new IllegalArgumentException("Kode pelanggan tidak boleh kosong.");
        }

        return pelangganRepository.findById(kodePelanggan);
    }

    public List<Pelanggan> getSemuaPelanggan() {
        return pelangganRepository.findAll();
    }

    private void validatePelanggan(Pelanggan pelanggan) {
        if (pelanggan.getKodePelanggan() == null || pelanggan.getKodePelanggan().isBlank()) {
            throw new IllegalArgumentException("Kode pelanggan wajib diisi.");
        }

        if (pelanggan.getNamaPelanggan() == null || pelanggan.getNamaPelanggan().isBlank()) {
            throw new IllegalArgumentException("Nama pelanggan wajib diisi.");
        }

        if (pelanggan.getNoHp() == null || pelanggan.getNoHp().isBlank()) {
            throw new IllegalArgumentException("No HP wajib diisi.");
        }

        if (pelanggan.getEmail() == null || pelanggan.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email wajib diisi.");
        }
    }
}
