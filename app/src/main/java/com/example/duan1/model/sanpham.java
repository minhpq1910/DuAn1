package com.example.duan1.model;

public class sanpham {
    private int MaSP;
    private String TenSP;
    private int SL, Gia, MaLoaiH;
    private String urlAvt;
    public sanpham() {
    }

    public sanpham(int maSP, String tenSP, int SL, int gia, int maLoaiH, String urlAvt) {
        MaSP = maSP;
        TenSP = tenSP;
        this.SL = SL;
        Gia = gia;
        MaLoaiH = maLoaiH;
        this.urlAvt = urlAvt;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public int getMaLoaiH() {
        return MaLoaiH;
    }

    public void setMaLoaiH(int maLoaiH) {
        MaLoaiH = maLoaiH;
    }

    public String getUrlAvt() {
        return urlAvt;
    }

    public void setUrlAvt(String urlAvt) {
        this.urlAvt = urlAvt;
    }
}
