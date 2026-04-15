package com.washywash.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.washywash.model.Pelanggan;
import com.washywash.model.Penjualan;
import com.washywash.model.DetailPenjualan;

import com.washywash.repository.PenjualanRepository;
import com.washywash.repository.DetailPenjualanRepository;
import com.washywash.repository.impl.PenjualanRepositoryImpl;
import com.washywash.repository.impl.DetailPenjualanRepositoryImpl;

import com.washywash.service.PenjualanService;
import com.washywash.service.DetailPenjualanService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;

public class FormPenjualan extends JPanel {
    private JTextField txtKodePenjualan;
    private JTextField txtTanggal;
    private JTextField txtKodePelanggan;
    private JTextField txtDiskon;
    private JLabel lblTotal;

    private JButton btnSimpan;
    private JButton btnTambahItem;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnCari;
    private JButton btnReset;

    private JTable tablePenjualan;
    private DefaultTableModel tableModel;

    private final PenjualanService penjualanService;
    private final DetailPenjualanService detailService;

    public FormPenjualan() {
        PenjualanRepository penRepo = new PenjualanRepositoryImpl();
        DetailPenjualanRepository detRepo = new DetailPenjualanRepositoryImpl();

        this.detailService = new DetailPenjualanService(detRepo);
        this.penjualanService = new PenjualanService(penRepo, detailService);

        initComponents();
        setDefaultTanggal();
        loadTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kode
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(new JLabel("Kode"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtKodePenjualan = new JTextField();
        panelForm.add(txtKodePenjualan, gbc);

        // Tanggal
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Tanggal"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTanggal = new JTextField();
        panelForm.add(txtTanggal, gbc);

        // Pelanggan
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Pelanggan"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtKodePelanggan = new JTextField();
        panelForm.add(txtKodePelanggan, gbc);

        // Diskon
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panelForm.add(new JLabel("Diskon"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDiskon = new JTextField("0");
        panelForm.add(txtDiskon, gbc);

        // Total
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panelForm.add(new JLabel("Total"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        lblTotal = new JLabel("0");
        panelForm.add(lblTotal, gbc);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnTambahItem = new JButton("Tambah Item");
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnCari = new JButton("Cari");
        btnReset = new JButton("Reset");

        panelButton.add(btnTambahItem);
        panelButton.add(btnSimpan);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);
        panelButton.add(btnCari);
        panelButton.add(btnReset);

        // Aksi
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Aksi"), gbc);

        gbc.gridx = 1;
        panelForm.add(panelButton, gbc);

        String[] columns = {
            "Kode Penjualan",
            "Kode Pelanggan",
            "Tanggal",
            "Diskon",
            "Total"
        };

        tableModel = new DefaultTableModel(columns, 0);
        tablePenjualan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablePenjualan);


        panelUtama.add(panelForm, BorderLayout.NORTH);
        panelUtama.add(scrollPane, BorderLayout.CENTER);

        add(panelUtama);

        btnTambahItem.addActionListener(e -> openDetail());
        btnSimpan.addActionListener(e -> simpanPenjualan());
        btnUpdate.addActionListener(e -> updatePenjualan());
        btnHapus.addActionListener(e -> hapusPenjualan());
        btnCari.addActionListener(e -> cariPenjualan());
        btnReset.addActionListener(e -> resetForm());

        tablePenjualan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePenjualan.getSelectedRow() != -1) {
                isiFormDariTable();
            }
        });
    }

    private void setDefaultTanggal() {
        txtTanggal.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    private void openDetail() {
        if (txtKodePenjualan.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Simpan header dulu!");
            return;
        }

        new FormDetailPenjualan(
            txtKodePenjualan.getText(),
            detailService,
            this::loadTable
        ).setVisible(true);
    }

    private void simpanPenjualan() {
        try {
            Pelanggan p = new Pelanggan();
            p.setKodePelanggan(txtKodePelanggan.getText());

            Penjualan penjualan = new Penjualan(
                txtKodePenjualan.getText(),
                p,
                new java.text.SimpleDateFormat("yyyy-MM-dd").parse(txtTanggal.getText()),
                parseDouble(txtDiskon.getText())
            );

            penjualanService.tambahPenjualan(penjualan);

            loadTable();
            resetForm();

            JOptionPane.showMessageDialog(this, "Berhasil simpan");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void updatePenjualan() {
        try {
            Penjualan penjualan = getPenjualanFromForm();
            penjualanService.updatePenjualan(penjualan);
            JOptionPane.showMessageDialog(this, "Data penjualan berhasil diupdate.");
            loadTable();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusPenjualan() {
        try {
            String kodePenjualan = txtKodePenjualan.getText();
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                penjualanService.hapusPenjualan(kodePenjualan);
                JOptionPane.showMessageDialog(this, "Data penjualan berhasil dihapus.");
                loadTable();
                resetForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cariPenjualan() {
        try {
            Penjualan p = penjualanService.cariPenjualan(txtKodePenjualan.getText());

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
                return;
            }

            txtKodePelanggan.setText(p.getPelanggan().getKodePelanggan());
            txtTanggal.setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format(p.getTanggal()));
            txtDiskon.setText(String.valueOf(p.getDiskon()));
            lblTotal.setText(String.valueOf(p.getTotal()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private Penjualan getPenjualanFromForm() {
        try {
            String kode = txtKodePenjualan.getText().trim();

            Pelanggan p = new Pelanggan();
            p.setKodePelanggan(txtKodePelanggan.getText().trim());

            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(txtTanggal.getText());

            double diskon = parseDouble(txtDiskon.getText());

            Penjualan penjualan = new Penjualan(
                kode,
                p,
                tanggal,
                diskon
            );

            penjualan.setTotal(parseDouble(lblTotal.getText()));

            return penjualan;

        } catch (Exception e) {
            throw new RuntimeException("Format data salah: " + e.getMessage());
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);

        List<Penjualan> list = penjualanService.getAllPenjualan();

        for (Penjualan p : list) {
            tableModel.addRow(new Object[]{
                p.getKodePenjualan(),
                p.getPelanggan().getKodePelanggan(),
                new java.text.SimpleDateFormat("yyyy-MM-dd").format(p.getTanggal()),
                p.getDiskon(),
                p.getTotal()
            });
        }
    }

    private double parseDouble(String v) {
        try {
            return Double.parseDouble(v);
        } catch (Exception e) {
            return 0;
        }
    }

    private void resetForm() {
        txtKodePenjualan.setText("");
        txtKodePelanggan.setText("");
        txtDiskon.setText("0");
        lblTotal.setText("0");
        setDefaultTanggal();
    }

    private void isiFormDariTable() {
    int row = tablePenjualan.getSelectedRow();

        txtKodePenjualan.setText(tableModel.getValueAt(row, 0).toString());
        txtKodePelanggan.setText(tableModel.getValueAt(row, 1).toString());
        txtTanggal.setText(tableModel.getValueAt(row, 2).toString());
        txtDiskon.setText(tableModel.getValueAt(row, 3).toString());
        lblTotal.setText(tableModel.getValueAt(row, 4).toString());
    }
}