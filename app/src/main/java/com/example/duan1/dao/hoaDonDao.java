package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.hoadon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class hoaDonDao {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public hoaDonDao(Context context) {
        this.context = context;
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(hoadon obj) {
        ContentValues values = new ContentValues();
        values.put("MaSP", obj.getMaSP());
        values.put("MaNV", obj.getMaNV());
        values.put("SL", obj.getSL());
        values.put("Gia", obj.getGia());
        values.put("TrangThai", obj.getTrangThai());
        values.put("Ngay", sdf.format(obj.getNgay()));
        return db.insert("HoaDon", null, values);
    }

    public long delete(String id) {
        return db.delete("HoaDon", "MaHD = ?", new String[]{String.valueOf(id)});
    }

    public List<hoadon> getAll() {
        String sql = "SELECT * FROM HoaDon ORDER BY MaHD DESC";
        return getData(sql);
    }

    public hoadon getID(String id) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD=?";
        List<hoadon> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<hoadon> getData(String sql, String... selectionArgs) {
        List<hoadon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            hoadon obj = new hoadon();
            obj.setMaHD(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaHD"))));
            obj.setMaSP(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaSP"))));
            obj.setMaNV(cursor.getString(cursor.getColumnIndex("MaNV")));
            obj.setSL(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SL"))));
            obj.setGia(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Gia"))));
            obj.setTrangThai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("TrangThai"))));
            try {
                obj.setNgay(sdf.parse(cursor.getString(cursor.getColumnIndex("Ngay"))));
//                Log.d("jjfjn", "getData: "+sdf.parse(cursor.getString(cursor.getColumnIndex("ngay"))));
            } catch (ParseException e) {
                e.printStackTrace();
//                Log.i("akhjj","123");
            }
            list.add(obj);
        }
        return list;
    }
//    // thống kê top 10
//    @SuppressLint("Range")
//    public List<Top> getTop() {
//        String sqlTop = "SELECT maSach,count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
//        List<Top> list = new ArrayList<Top>();
//        SachDAO sachDAO = new SachDAO(context);
//        Cursor cursor = db.rawQuery(sqlTop, null);
//        while (cursor.moveToNext()) {
//            Top top = new Top();
//            @SuppressLint("Range") Sach sach = sachDAO.getID(cursor.getString(cursor.getColumnIndex("maSach")));
//            top.setTenSach(sach.getTenSach());
//            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
//            list.add(top);
//
//        }
//        return list;
//    }

    // thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(gia) as doanhThu FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));

            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}

