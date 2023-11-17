package com.example.duan1.model;

public class admin {
    private String ma, taiKhoan, matKhau;

    public admin() {
    }

    public admin(String ma, String taiKhoan, String matKhau) {
        this.ma = ma;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
