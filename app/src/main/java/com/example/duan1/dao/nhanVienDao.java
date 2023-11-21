package com.example.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;

public class nhanVienDao {
    Dbhelper dbhelper;
    public nhanVienDao(Context context){
    dbhelper = new Dbhelper(context);
    }
    public ArrayList<nhanVien> getDSThuThu(){
        ArrayList<nhanVien> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select * from NhanVien",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (cursor.isAfterLast()){
                    nhanVien nv = new nhanVien();
                    nv.setMaNV(cursor.getString(0));
                    nv.setHoTen(cursor.getString(1));
                    nv.setMatKhau(cursor.getString(2));
                    list.add(nv);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkUser(String username){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where MaNV = ?",new String[]{username});
        int row = cursor.getCount();
        return (row > 0);
    }

    public boolean insert(nhanVien nv){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaNV",nv.getMaNV());
        values.put("HoTen",nv.getHoTen());
        values.put("MatKhau",nv.getMatKhau());
        long data = db.insert("NhanVien",null,values);
        return (data > 0);
    }

    // đăng nhập
    public boolean checkLogin(String MaNV,String MatKhau){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where MaNV = ? and MatKhau = ?",new String[]{MaNV, MatKhau});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateMK(String username, String oldPass, String newPass){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NhanVien where MaNV = ? and MatKhau = ?", new String[]{username,oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("MatKhau", newPass);
            long check = db.update("NhanVien",values,"MaNV = ?",new String[]{username});
            if(check == -1){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }
}
