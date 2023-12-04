package com.example.duan1.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.R;
import com.example.duan1.dao.loaiHangDao;
import com.example.duan1.dao.sanPhamDao;
import com.example.duan1.fragment.frg_sanpham;
import com.example.duan1.login;
import com.example.duan1.model.loaihang;
import com.example.duan1.model.sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class sanPhamAdapter extends ArrayAdapter<sanpham> {
    private Context context;
    frg_sanpham fragment;
    List<sanpham> list;
    TextView tvMaSP, tvTenSP, tvGia, tvLoaiSP;
    ImageView imgDel, AvtSP;

    public sanPhamAdapter(@NonNull Context context, frg_sanpham fragment, List<sanpham> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham, null);
        }
        final sanpham item = list.get(position);
        if (item != null) {
            loaiHangDao loaiHDao = new loaiHangDao(context);
            loaihang loaihang = loaiHDao.getID(String.valueOf(item.getMaLoaiH()));
            tvMaSP = v.findViewById(R.id.tvMaSP);
            tvMaSP.setText("Mã sản phẩm: " + item.getMaSP());

            tvTenSP = v.findViewById(R.id.tvTenSP);
            tvTenSP.setText("Tên sản phẩm: " + item.getTenSP());
            tvGia = v.findViewById(R.id.tvGia);
            tvGia.setText("Giá: " + item.getGia());
            tvLoaiSP = v.findViewById(R.id.tvLoaiSP);
            tvLoaiSP.setText("Loại hàng: " + loaihang.getTenLoaiH());
            AvtSP = v.findViewById(R.id.AvtSP);
            Picasso.get().load(item.getUrlAvt()).into(AvtSP);

            imgDel = v.findViewById(R.id.imgDeleteSP);
            checkAn();
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pt xóa
                fragment.xoa(String.valueOf(item.getMaSP()));
            }
        });
        return v;
    }

    public void checkAn() {
        // Lấy loại tài khoản từ SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("admin".equals(loaiTaiKhoan)) {
            // Hiển thị nút xoá khi là admin
            imgDel.setVisibility(View.VISIBLE);
        } else {
            // Ẩn nút xoá khi là nhân viên
            imgDel.setVisibility(View.GONE);
        }
    }
}
