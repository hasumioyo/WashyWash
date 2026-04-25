package com.washywash.view;

import javax.swing.*;

import com.washywash.model.Barang;
import com.washywash.model.Penjualan;
import com.washywash.model.DetailPenjualan;
import com.washywash.service.DetailPenjualanService;
import com.washywash.service.BarangService;
import com.washywash.repository.impl.BarangRepositoryImpl;

import java.awt.*;
import java.util.List;

public class FormDetailPenjualan extends JDialog {

    private JComboBox<Barang> cmbBarang;
    private JTextField txtQty;
    private JTextField txtHarga;
    private JTextField txtSubtotal;

    private JButton btnSimpan;
    private JButton btnReset;

    private final String kodePenjualan;
    private final BarangService barangService;
    private final DetailPenjualanService detailService;
    private final Runnable onSuccessRefresh;

    public FormDetailPenjualan(String kodePenjualan,
                               DetailPenjualanService detailService,
                               Runnable onSuccessRefresh) {

        this.kodePenjualan = kodePenjualan;
        this.detailService = detailService;
        this.onSuccessRefresh = onSuccessRefresh;
        this.barangService = new BarangService(new BarangRepositoryImpl());

        initComponents();
        loadBarangCombo();
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

        // Barang
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Barang"), gbc);

        gbc.gridx = 1;
        cmbBarang = new JComboBox<>();
        add(cmbBarang, gbc);

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

        cmbBarang.addActionListener(e -> loadBarang());

        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungSubtotal();
            }
        });

        btnSimpan.addActionListener(e -> simpanDetail());
        btnReset.addActionListener(e -> resetForm());
    }

    private void loadBarangCombo() {
        List<Barang> list = barangService.getSemuaBarang();

        cmbBarang.removeAllItems();

        if (list.isEmpty()) {
            cmbBarang.addItem(null);
            cmbBarang.setRenderer((list1, value, index, isSelected, cellHasFocus) ->
                new JLabel("Belum ada data barang")
            );
        } else {
            for (Barang b : list) {
                cmbBarang.addItem(b);
            }

            cmbBarang.setRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus){

                    JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                    if(value instanceof Barang b){
                        lbl.setText(b.getKodeBarang()+" - "+b.getNamaBarang());
                    }

                    return lbl;
                }
            });
        }
    }

    private void loadBarang() {
        Barang barang = (Barang) cmbBarang.getSelectedItem();

        if (barang == null) return;

        txtHarga.setText(String.valueOf(barang.getHarga()));

        if (txtQty.getText().isBlank()) {
            txtQty.setText("1");
        }

        hitungSubtotal();
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
            Barang barang = (Barang) cmbBarang.getSelectedItem();

            if (barang == null) {
                JOptionPane.showMessageDialog(this, "Pilih barang dulu!");
                return;
            }

            int qty = Integer.parseInt(txtQty.getText().trim());

            Penjualan penjualan = new Penjualan();
            penjualan.setKodePenjualan(kodePenjualan);

            DetailPenjualan detail = new DetailPenjualan(
                    penjualan,
                    barang,
                    qty,
                    barang.getHarga()
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
        cmbBarang.setSelectedIndex(-1);
        txtQty.setText("");
        txtHarga.setText("");
        txtSubtotal.setText("");
    }
}