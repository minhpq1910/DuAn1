package com.example.duan1.model;

public class nhanVien {
    private String TaiKhoan, HoTen, MatKhau, Avt;

    public nhanVien() {
    }

    public nhanVien(String taiKhoan, String hoTen, String matKhau, String avt) {
        TaiKhoan = taiKhoan;
        HoTen = hoTen;
        MatKhau = matKhau;
        Avt = avt;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
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

    public String getAvt() {
        return Avt;
    }

    public void setAvt(String avt) {
        Avt = avt;
    }
}
