package com.example.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;

public class adminDao {
    Dbhelper dbhelper;
    public adminDao(Context context){
        dbhelper = new Dbhelper(context);
    }

    // đăng nhập
    public boolean checkLogin(String ma,String MatKhau){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from admin where ma = ? and MatKhau = ?",new String[]{ma, MatKhau});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }
}
