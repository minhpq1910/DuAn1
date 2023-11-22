package com.example.duan1.model;

public class nhanVien {
    private String MaNV, HoTen, MatKhau;

    public nhanVien() {
    }

    public nhanVien(String maNV, String hoTen, String matKhau) {
        MaNV = maNV;
        HoTen = hoTen;
        MatKhau = matKhau;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
