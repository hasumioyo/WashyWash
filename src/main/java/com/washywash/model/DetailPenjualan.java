package com.washywash.model;

public class DetailPenjualan {
    private int idDetail;
    private Penjualan penjualan;
    private Barang barang;
    private int qty;
    private double harga;

    public DetailPenjualan(Penjualan penjualan, Barang barang, int qty, double harga) {
        this.penjualan = penjualan;
        this.barang = barang;
        this.qty = qty;
        this.harga = harga;
    }

    public DetailPenjualan(int idDetail, Penjualan penjualan, Barang barang, int qty, double harga) {
        this.idDetail = idDetail;
        this.penjualan = penjualan;
        this.barang = barang;
        this.qty = qty;
        this.harga = harga;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getSubtotal() {
        return qty * harga;
    }
}