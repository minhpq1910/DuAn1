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

public class adminDao {
    private SQLiteDatabase db;

    public adminDao(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(admin obj) {
        ContentValues values = new ContentValues();
        values.put("taiKhoan", obj.getTaiKhoan());
        values.put("hoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        return db.insert("admin", null, values);
    }

    public long update(admin obj) {
        ContentValues values = new ContentValues();
        values.put("taiKhoan", obj.getTaiKhoan());
        values.put("hoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        return db.update("admin", values, "taiKhoan = ?", new String[]{String.valueOf(obj.getTaiKhoan())});
    }

    public long updatePass(admin obj) {
        ContentValues values = new ContentValues();
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        return db.update("admin", values, "taiKhoan = ?", new String[]{String.valueOf(obj.getTaiKhoan())});
    }

    public long delete(String id) {
        return db.delete("admin", "taiKhoan = ?", new String[]{String.valueOf(id)});
    }

    public List<admin> getAll() {
        String sql = "SELECT * FROM admin";
        return getData(sql);
    }

    public admin getID(String id) {
        String sql = "SELECT * FROM admin WHERE taiKhoan=?";
        List<admin> list = getData(sql, id);
        return list.get(0);
    }

    // check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM admin WHERE taiKhoan =? AND MatKhau=?";
        List<admin> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    @SuppressLint("Range")
    private List<admin> getData(String sql, String... selectionArgs) {
        List<admin> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            admin obj = new admin();
            obj.setTaiKhoan(cursor.getString(cursor.getColumnIndex("taiKhoan")));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            obj.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
            list.add(obj);
        }
        return list;
    }
}

