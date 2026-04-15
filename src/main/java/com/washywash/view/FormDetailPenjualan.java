package com.washywash.view;

import javax.swing.*;

import com.washywash.model.Barang;
import com.washywash.model.Penjualan;
import com.washywash.model.DetailPenjualan;
import com.washywash.service.DetailPenjualanService;

import com.washywash.service.BarangService;
import com.washywash.repository.impl.BarangRepositoryImpl;

import java.awt.*;

public class FormDetailPenjualan extends JDialog {
    private JTextField txtKodeBarang;
    private JTextField txtQty;
    private JTextField txtHarga;
    private JTextField txtSubtotal;

    private JButton btnSimpan;
    private JButton btnReset;

    private final String kodePenjualan;
    private final BarangService barangService;
    private final DetailPenjualanService detailService;
    private final Runnable onSuccessRefresh;

    public FormDetailPenjualan(String kodePenjualan, DetailPenjualanService detailService, Runnable onSuccessRefresh) {
        this.kodePenjualan = kodePenjualan;
        this.detailService = detailService;
        this.onSuccessRefresh = onSuccessRefresh;
        this.barangService = new BarangService(new BarangRepositoryImpl());

        initComponents();
    }

    private void initComponents() {
        setTitle("Form Detail Penjualan");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setModal(true);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kode Barang
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Kode Barang"), gbc);

        gbc.gridx = 1;
        txtKodeBarang = new JTextField();
        add(txtKodeBarang, gbc);

        // Qty
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Qty"), gbc);

        gbc.gridx = 1;
        txtQty = new JTextField();
        add(txtQty, gbc);

        // Harga
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Harga"), gbc);

        gbc.gridx = 1;
        txtHarga = new JTextField();
        txtHarga.setEditable(false);
        add(txtHarga, gbc);

        // Subtotal
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Subtotal"), gbc);

        gbc.gridx = 1;
        txtSubtotal = new JTextField();
        txtSubtotal.setEditable(false);
        add(txtSubtotal, gbc);

        JPanel panelButton = new JPanel(new FlowLayout());

        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");

        panelButton.add(btnSimpan);
        panelButton.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(panelButton, gbc);

        // EVENT 🔥
        txtKodeBarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                loadBarang();
            }
        });
        
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungSubtotal();
            }
        });

        btnSimpan.addActionListener(e -> simpanDetail());
        btnReset.addActionListener(e -> resetForm());
    }

    private void loadBarang() {
        try {
            String kodeBarang = txtKodeBarang.getText().trim();

            Barang barang = barangService.cariBarang(kodeBarang);

            if (barang == null) {
                throw new RuntimeException("Barang tidak ditemukan");
            }

            txtHarga.setText(String.valueOf(barang.getHarga()));

            hitungSubtotal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void hitungSubtotal() {
        try {
            double harga = Double.parseDouble(txtHarga.getText());
            int qty = Integer.parseInt(txtQty.getText());

            double subtotal = harga * qty;

            txtSubtotal.setText(String.valueOf(subtotal));

        } catch (Exception e) {
            txtSubtotal.setText("0");
        }
    }

    private void simpanDetail() {
        try {
            String kodeBarang = txtKodeBarang.getText().trim();
            int qty = Integer.parseInt(txtQty.getText().trim());

            Penjualan penjualan = new Penjualan();
            penjualan.setKodePenjualan(kodePenjualan);

            Barang barangDb = barangService.cariBarang(kodeBarang);

            if (barangDb == null) {
                throw new RuntimeException("Barang tidak ditemukan");
            }

            DetailPenjualan detail = new DetailPenjualan(
                    penjualan,
                    barangDb,
                    qty,
                    barangDb.getHarga()
            );

            detailService.tambahDetail(detail);

            JOptionPane.showMessageDialog(this, "Detail berhasil disimpan");

            if (onSuccessRefresh != null) {
                onSuccessRefresh.run();
            }

            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus angka!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void resetForm() {
        txtKodeBarang.setText("");
        txtQty.setText("");
        txtHarga.setText("");
        txtSubtotal.setText("");
    }
}