package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;
import java.util.List;

public class nhanVienDAO {
    private SQLiteDatabase db;

    public nhanVienDAO(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("SDT", obj.getSDT());
        values.put("Luong", obj.getLuong());
        return db.insert("NhanVien", null, values);
    }

    public long update(nhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("SDT", obj.getSDT());
        values.put("Luong", obj.getLuong());
        return db.update("NhanVien", values, "maNV = ?", new String[]{String.valueOf(obj.getMaNV())});
    }

    public long delete(String id) {
        return db.delete("NhanVien", "maNV = ?", new String[]{String.valueOf(id)});
    }

    public List<nhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    public nhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<nhanVien> list = getData(sql, id);
        return list.get(0);
    }
    // đăng nhập
    public boolean checkLogin(String MaNV,String MatKhau){
        SQLiteDatabase db = Dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where MaNV = ? and MatKhau = ?",new String[]{MaNV, MatKhau});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    @SuppressLint("Range")
    private List<nhanVien> getData(String sql, String... selectionArgs) {
        List<nhanVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            nhanVien obj = new nhanVien();
            obj.setMaNV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            obj.setSDT(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SDT"))));
            obj.setLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Luong"))));

            Log.i("//==", obj.toString());
            list.add(obj);
        }
        return list;
    }


}
