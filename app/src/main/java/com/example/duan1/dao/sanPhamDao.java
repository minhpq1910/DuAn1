package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.sanpham;

import java.util.ArrayList;
import java.util.List;

public class sanPhamDao {
    private SQLiteDatabase db;

    public sanPhamDao(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(sanpham obj) {
        ContentValues values = new ContentValues();
        values.put("TenSP", obj.getTenSP());
        values.put("SL", obj.getSL());
        values.put("Gia", obj.getGia());
        values.put("MaLoai", obj.getMaLoaiH());
        values.put("Avt", obj.getUrlAvt());
        return db.insert("SanPham", null, values);
    }

    public long update(sanpham obj) {
        ContentValues values = new ContentValues();
        values.put("TenSP", obj.getTenSP());
        values.put("SL", obj.getSL());
        values.put("Gia", obj.getGia());
        values.put("MaLoai", obj.getMaLoaiH());
        values.put("Avt", obj.getUrlAvt());
        return db.update("SanPham", values, "MaSP = ?", new String[]{String.valueOf(obj.getMaSP())});
    }

    public long delete(String id) {
        return db.delete("SanPham", "MaSP = ?", new String[]{String.valueOf(id)});
    }

    public List<sanpham> getAll() {
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }

    public sanpham getID(String id) {
        String sql = "SELECT * FROM SanPham WHERE MaSP=?";
        List<sanpham> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<sanpham> getData(String sql, String... selectionArgs) {
        List<sanpham> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            sanpham obj = new sanpham();
            obj.setMaSP(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaSP"))));
            obj.setTenSP(cursor.getString(cursor.getColumnIndex("TenSP")));
            obj.setSL(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SL"))));
            obj.setGia(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Gia"))));
            obj.setMaLoaiH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaLoai"))));
            obj.setUrlAvt(cursor.getString(cursor.getColumnIndex("Avt")));
            list.add(obj);
        }
        return list;
    }


}

