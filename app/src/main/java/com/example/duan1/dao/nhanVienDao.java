package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.admin;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;
import java.util.List;

public class nhanVienDao {
    private SQLiteDatabase db;

    public nhanVienDao(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("MaNV", obj.getMaNV());
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        return db.insert("NhanVien", null, values);
    }

    public long update(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        return db.update("NhanVien", values, "MaNV = ?", new String[]{String.valueOf(obj.getMaNV())});
    }

//    public long updatePass(nhanVien obj) {
//        ContentValues values = new ContentValues();
//        values.put("HoTen", obj.getHoTen());
//        values.put("MatKhau", obj.getMatKhau());
//        return db.update("NhanVien", values, "MaNV = ?", new String[]{String.valueOf(obj.getMaNV())});
//    }

    public long delete(String id) {
        return db.delete("NhanVien", "MaNV = ?", new String[]{String.valueOf(id)});
    }

    public List<nhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    public nhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<nhanVien> list = getData(sql, id);
        return list.get(0);
    }

    // check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV =? AND MatKhau =?";
        List<nhanVien> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    @SuppressLint("Range")
    private List<nhanVien> getData(String sql, String... selectionArgs) {
        List<nhanVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            nhanVien obj = new nhanVien();
            obj.setMaNV(cursor.getString(cursor.getColumnIndex("MaNV")));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            obj.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
            list.add(obj);
        }
        return list;
    }
}