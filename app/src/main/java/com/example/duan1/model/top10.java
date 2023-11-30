package com.example.duan1.model;

public class top10 {
    private String TenSP;
    private int SL;
    private float Gia;

    public top10() {
    }

    public top10(String tenSP, int SL, float gia) {
        TenSP = tenSP;
        this.SL = SL;
        Gia = gia;
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

    public float getGia() {
        return Gia;
    }

    public void setGia(float gia) {
        Gia = gia;
    }
}
