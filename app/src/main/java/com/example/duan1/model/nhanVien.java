package com.example.duan1.model;

public class nhanVien {
    private int maNV;
    private String hoTen;
    private int SDT;

    private int Luong;

    public nhanVien() {
    }

    public nhanVien(int maNV, String hoTen, int SDT, int luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.SDT = SDT;
        Luong = luong;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public int getLuong() {
        return Luong;
    }

    public void setLuong(int luong) {
        Luong = luong;
    }
}
