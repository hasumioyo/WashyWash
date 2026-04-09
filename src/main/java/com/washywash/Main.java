package com.washywash;

import com.washywash.view.FormBarang;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FormBarang().setVisible(true);
        });   
    }
}