package com.example.duan1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context) {
        super(context, "shop", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //bảng Admin
        String tb_Admin = "create table admin(" +
                "taiKhoan text primary key," +
                "hoTen text not null," +
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
                "MaLoai integer references LoaiHang(MaLoai)," +
                "Avt text not null)";
        db.execSQL(tb_SanPham);

        // Tạo bảng Hoá đơn
        String tb_HoaDon = "create table HoaDon(" +
                "MaHD integer primary key autoincrement," +
                "MaSP integer references SanPham(MaSP)," +
                "MaNV text references NhanVien(MaNV)," +
                "SL integer not null," +
                "Gia integer not null," +
                "TrangThai integer not null," +
                "Ngay text not null)";
        db.execSQL(tb_HoaDon);

        //data mẫu
        db.execSQL("INSERT INTO admin VALUES ('admin','Minh','1111')");
        db.execSQL("INSERT INTO LoaiHang VALUES (1, 'Đồ ăn'),(2,'Nước uống'),(3, 'Đồ phụ gia')");
        db.execSQL("INSERT INTO SanPham VALUES (1, 'BimBim', 25, 5000, 1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZCw7s_SSZDwXbvJm2ztlAmv01xd3ovChqAw&usqp=CAU'), (2, 'Coca-cola', 100, 16000, 2, 'https://tienthanhltd.com.vn/assets/shops/2018_09/cocacola15l.jpg'), (3, 'Mì chính', 50, 50000, 3, 'https://pvmarthanoi.com.vn/wp-content/uploads/2023/02/bot-ngot-ajinomoto-goi-1kg-201912111050340356-500x600.jpg')");
        db.execSQL("INSERT INTO NhanVien VALUES ('PQMinh','Phạm Quang Minh','1234'),('nv01','Trần Văn A','123abc')");
        db.execSQL("INSERT INTO HoaDon VALUES (1,2,'PQMinh', 2, 32000, 1, '16/11/2023')," +
                "(2,1,'PQMinh', 3, 15000, 1, '17/11/2023')," +
                "(3,2,'PQMinh', 1, 16000, 1, '16/11/2023')");

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

