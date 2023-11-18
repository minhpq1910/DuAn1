package com.example.duan1.model;

public class loaihang {
    private int maLoaiH;
    private String tenLoaiH;

    public loaihang() {
    }

    public loaihang(int maLoaiH, String tenLoaiH) {
        this.maLoaiH = maLoaiH;
        this.tenLoaiH = tenLoaiH;
    }

    public int getMaLoaiH() {
        return maLoaiH;
    }

    public void setMaLoaiH(int maLoaiH) {
        this.maLoaiH = maLoaiH;
    }

    public String getTenLoaiH() {
        return tenLoaiH;
    }

    public void setTenLoaiH(String tenLoaiH) {
        this.tenLoaiH = tenLoaiH;
    }
}
