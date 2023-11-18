package com.example.duan1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context) {
        super(context, "shop", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //bảng Admin
        String tb_Admin = "create table admin(" +
                "ma text primary key," +
                "taiKhoan text not null," +
                "MatKhau text not null)";
        db.execSQL(tb_Admin);

        // Tạo bảng nhân viên
        String tb_NhanVien = "create table NhanVien(" +
                "MaNV text primary key," +
                "HoTen text not null," +
                "MatKhau text not null)";
        db.execSQL(tb_NhanVien);

        // Tạo bảng loại hàng
        String tb_LoaiHang = "create table LoaiHang(" +
                "MaLoai integer primary key autoincrement," +
                "TenLoai text not null)";
        db.execSQL(tb_LoaiHang);

        // Tạo bảng sản phẩm
        String tb_SanPham = "create table SanPham(" +
                "MaSP integer primary key autoincrement," +
                "TenSP text not null," +
                "SL integer not null," +
                "Gia integer not null," +
                "MaLoai integer references LoaiHang(MaLoai))";
        db.execSQL(tb_SanPham);

        // Tạo bảng Hoá đơn
        String tb_HoaDon = "create table HoaDon(" +
                "MaHD integer primary key autoincrement," +
                "MaSP integer references SanPham(MaSP)," +
                "MaNV integer references NhanVien(MaNV)," +
                "SL integer not null," +
                "Gia integer not null," +
                "Ngay text not null)";
        db.execSQL(tb_HoaDon);

        //data mẫu
        db.execSQL("INSERT INTO admin VALUES ('admin','Minh','1111')");
        db.execSQL("INSERT INTO LoaiHang VALUES (1, 'Đồ ăn'),(2,'Nước uống'),(3, 'Đồ phụ gia')");
        db.execSQL("INSERT INTO SanPham VALUES (1, 'BimBim', 25, 5000, 1), (2, 'Coca-cola', 100, 16000, 2), (3, 'Mì chính', 50, 50000, 3)");
        db.execSQL("INSERT INTO NhanVien VALUES ('PQMinh','Phạm Quang Minh','1234'),('nv01','Trần Văn A','123abc')");
        db.execSQL("INSERT INTO HoaDon VALUES (1,2,'PQMinh', 2, 32000, '16/11/2023')," +
                "(2,1,'PQMinh', 3, 15000, '16/11/2023')," +
                "(3,2,'PQMinh', 1, 16000, '16/11/2023')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("drop table if exists NhanVien");
            sqLiteDatabase.execSQL("drop table if exists admin");
            sqLiteDatabase.execSQL("drop table if exists LoaiHang");
            sqLiteDatabase.execSQL("drop table if exists SanPham");
            sqLiteDatabase.execSQL("drop table if exists HoaDon");
            onCreate(sqLiteDatabase);
        }
    }
}

