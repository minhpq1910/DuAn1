package com.example.duan1.model;

public class admin {
    private String taiKhoan, hoTen, matKhau;

    public admin() {
    }

    public admin(String taiKhoan, String hoTen, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
