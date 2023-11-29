package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
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
        values.put("taiKhoan", obj.getTaiKhoan());
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        values.put("Avt", obj.getAvt());
        return db.insert("NhanVien", null, values);
    }

    public long update(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());
        values.put("Avt", obj.getAvt());
        return db.update("NhanVien", values, "taiKhoan = ?", new String[]{String.valueOf(obj.getTaiKhoan())});
    }

    public long updatePass(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("HoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());

        return db.update("NhanVien", values, "taiKhoan = ?", new String[]{String.valueOf(obj.getTaiKhoan())});
    }

    public long delete(String id) {
        return db.delete("NhanVien", "taiKhoan = ?", new String[]{String.valueOf(id)});
    }

    public List<nhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    public nhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE taiKhoan=?";
        List<nhanVien> list = getData(sql, id);
        return list.get(0);
    }

    // check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM NhanVien WHERE taiKhoan =? AND MatKhau =?";
        List<nhanVien> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
    //quên mật khẩu
    public String forgot(String taiKhoan) {
        String sql = "SELECT MatKhau FROM NhanVien WHERE taiKhoan=?";
        Cursor cursor = db.rawQuery(sql, new String[]{taiKhoan});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("MatKhau");
            if (columnIndex != -1) {
                String matKhau = cursor.getString(columnIndex);
                cursor.close();
                return matKhau;
            } else {
                cursor.close();
                return null; // Trả về null nếu "MatKhau" không phải là một cột hợp lệ
            }
        }

        return null; // Trả về null nếu không tìm thấy tài khoản phù hợp hoặc cursor không có dữ liệu
    }

    @SuppressLint("Range")
    private List<nhanVien> getData(String sql, String... selectionArgs) {
        List<nhanVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            nhanVien obj = new nhanVien();
            obj.setTaiKhoan(cursor.getString(cursor.getColumnIndex("taiKhoan")));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            obj.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
            obj.setAvt(cursor.getString(cursor.getColumnIndex("Avt")));
            list.add(obj);
        }
        return list;
    }
}