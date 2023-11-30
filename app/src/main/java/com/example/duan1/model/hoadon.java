package com.example.duan1.model;

import java.util.Date;

public class hoadon {
    private int MaHD, MaSP;
    private String MaNV;
    private int SL, Gia, TrangThai;
    private Date ngay;
    private String HoTenNV;
    public hoadon() {
    }

    public hoadon(int maHD, int maSP, String maNV, int SL, int gia, int trangThai, Date ngay, String hoTenNV) {
        MaHD = maHD;
        MaSP = maSP;
        MaNV = maNV;
        this.SL = SL;
        Gia = gia;
        TrangThai = trangThai;
        this.ngay = ngay;
        HoTenNV = hoTenNV;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
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

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getHoTenNV() {
        return HoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        HoTenNV = hoTenNV;
    }
}
