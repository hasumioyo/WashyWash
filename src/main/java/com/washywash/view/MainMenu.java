package com.washywash.view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContent;

    public MainMenu() {
        setTitle("WashyWash Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelSidebar = new JPanel();
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setPreferredSize(new Dimension(200, 0));
        panelSidebar.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(32, 178, 170)), // garis kanan
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        );
        panelSidebar.setBackground(new Color(32, 178, 170));

        JButton btnPelanggan = new JButton("Pelanggan");
        JButton btnBarang = new JButton("Barang");
        JButton btnPenjualan = new JButton("Penjualan");

        btnPelanggan.setBackground(new Color(255, 255, 255));
        btnPelanggan.setForeground(new Color(32, 178, 170)); 

        btnBarang.setBackground(new Color(255, 255, 255));
        btnBarang.setForeground(new Color(32, 178, 170));

        btnPenjualan.setBackground(new Color(255, 255, 255));
        btnPenjualan.setForeground(new Color(32, 178, 170));

        btnPelanggan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnBarang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnPenjualan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        panelSidebar.add(btnPelanggan);
        panelSidebar.add(Box.createVerticalStrut(10));
        panelSidebar.add(btnBarang);
        panelSidebar.add(Box.createVerticalStrut(10));
        panelSidebar.add(btnPenjualan);

        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);

        panelContent.add(new FormPelanggan(), "pelanggan");
        panelContent.add(new FormBarang(), "barang");
        panelContent.add(new FormPenjualan(), "penjualan");

        btnPelanggan.addActionListener(e -> {
            cardLayout.show(panelContent, "pelanggan");
        });

        btnBarang.addActionListener(e -> {
            cardLayout.show(panelContent, "barang");
        });

        btnPenjualan.addActionListener(e -> {
            cardLayout.show(panelContent, "penjualan");
        });

        setLayout(new BorderLayout());
        add(panelSidebar, BorderLayout.WEST);
        add(panelContent, BorderLayout.CENTER);
    }
}