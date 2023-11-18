package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.loaihang;

import java.util.ArrayList;
import java.util.List;

public class loaiHangDao {
    private SQLiteDatabase db;

    public loaiHangDao(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(loaihang obj) {
        ContentValues values = new ContentValues();
        values.put("TenLoai", obj.getTenLoaiH());
        return db.insert("LoaiHang", null, values);
    }

    public long update(loaihang obj) {
        ContentValues values = new ContentValues();
        values.put("TenHang", obj.getTenLoaiH());
        return db.update("LoaiHang", values, "MaLoai = ?", new String[]{String.valueOf(obj.getMaLoaiH())});
    }

    public long delete(String id) {
        return db.delete("LoaiHang", "MaLoai = ?", new String[]{String.valueOf(id)});
    }

    public List<loaihang> getAll() {
        String sql = "SELECT * FROM LoaiHang";
        return getData(sql);
    }

    public loaihang getID(String id) {
        String sql = "SELECT * FROM LoaiHang WHERE MaLoai=?";
        List<loaihang> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<loaihang> getData(String sql, String... selectionArgs) {
        List<loaihang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            loaihang obj = new loaihang();
            obj.setMaLoaiH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaLoai"))));
            obj.setTenLoaiH(cursor.getString(cursor.getColumnIndex("TenLoai")));
            list.add(obj);
        }
        return list;
    }

}

