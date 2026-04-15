package com.washywash.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Penjualan {
    private String kodePenjualan;
    private Pelanggan pelanggan;
    private Date tanggal;
    private double diskon;
    private double total;

    private List<DetailPenjualan> listDetail;

    public Penjualan() {
        this.listDetail = new ArrayList<>();
    }

    public Penjualan(String kodePenjualan, Pelanggan pelanggan, Date tanggal, double diskon) {
        this.kodePenjualan = kodePenjualan;
        this.pelanggan = pelanggan;
        this.tanggal = tanggal;
        this.diskon = diskon;
        this.listDetail = new ArrayList<>();
    }


    public String getKodePenjualan() {
        return kodePenjualan;
    }

    public void setKodePenjualan(String kodePenjualan) {
        this.kodePenjualan = kodePenjualan;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetailPenjualan> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<DetailPenjualan> listDetail) {
        this.listDetail = listDetail;
    }

    public void tambahDetail(DetailPenjualan detail) {
        this.listDetail.add(detail);
    }

    public double hitungTotal() {
        double total = 0;

        for (DetailPenjualan d : listDetail) {
            total += d.getSubtotal();
        }

        total = total - diskon;

        if (total < 0) {
            total = 0;
        }

        return total;
    }

    public void refreshTotal() {
        this.total = hitungTotal();
    }
}