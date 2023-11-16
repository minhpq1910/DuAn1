package com.example.duan1.model;

public class nhanVien {
    private String MaNV,MatKhau,HoTen;

    public nhanVien() {
    }

    public nhanVien(String maNV, String matKhau, String hoTen) {
        MaNV = maNV;
        MatKhau = matKhau;
        HoTen = hoTen;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
}
