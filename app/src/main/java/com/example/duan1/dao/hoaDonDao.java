package com.example.duan1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.database.Dbhelper;
import com.example.duan1.model.hoadon;
import com.example.duan1.model.sanpham;
import com.example.duan1.model.top10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        values.put("taiKhoan", obj.getMaNV());
        values.put("SL", obj.getSL());
        values.put("Gia", obj.getGia());
        values.put("TrangThai", obj.getTrangThai());
        values.put("Ngay", sdf.format(obj.getNgay()));
        return db.insert("HoaDon", null, values);
    }

    public long update(hoadon obj) {
        ContentValues values = new ContentValues();
        values.put("MaSP", obj.getMaSP());
        values.put("taiKhoan", obj.getMaNV());
        values.put("SL", obj.getSL());
        values.put("Gia", obj.getGia());
        values.put("TrangThai", obj.getTrangThai());
        values.put("Ngay", sdf.format(obj.getNgay()));
        return db.update("HoaDon", values, "MaHD = ?", new String[]{String.valueOf(obj.getMaHD())});
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
            obj.setMaNV(cursor.getString(cursor.getColumnIndex("taiKhoan")));
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

    //thống kê top 10
    public List<top10> getTop10BestSellingProducts() {
        String sql = "SELECT SanPham.TenSP, SUM(HoaDon.SL) AS TotalSold " +
                "FROM HoaDon " +
                "INNER JOIN SanPham ON HoaDon.MaSP = SanPham.MaSP " +
                "WHERE HoaDon.TrangThai = 1 " +
                "GROUP BY SanPham.TenSP " +
                "ORDER BY TotalSold DESC " +
                "LIMIT 10";

        return getTop10Data(sql);
    }

    @SuppressLint("Range")
    private List<top10> getTop10Data(String sql, String... selectionArgs) {
        List<top10> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            top10 obj = new top10();
            obj.setTenSP(cursor.getString(cursor.getColumnIndex("TenSP")));
            obj.setSL(cursor.getInt(cursor.getColumnIndex("TotalSold")));
            list.add(obj);
        }
        return list;
    }

    // thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(gia * SL) AS doanhThu FROM HoaDon WHERE TrangThai = 1 AND ngay BETWEEN ? AND ?";

        try (Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex("doanhThu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @SuppressLint("Range")
    public int getDoanhThuForUser(String user, String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(SanPham.Gia * HoaDon.SL) AS doanhThu FROM HoaDon INNER JOIN SanPham ON HoaDon.MaSP = SanPham.MaSP WHERE taiKhoan = ? AND TrangThai = 1 AND ngay BETWEEN ? AND ?";

        try (Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{user, tuNgay, denNgay})) {
            if (cursor.moveToFirst()) {
                int doanhThu = cursor.getInt(cursor.getColumnIndex("doanhThu"));
                return doanhThu;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Doanh Thu: 0");
        return 0;
    }
}

