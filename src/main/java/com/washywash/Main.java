package com.washywash;

import com.washywash.view.MainMenu;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });   
    }
}