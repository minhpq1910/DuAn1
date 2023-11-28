package com.example.duan1.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.R;
import com.example.duan1.fragment.frg_loaihang;
import com.example.duan1.model.loaihang;

import java.util.ArrayList;

public class loaiHangAdapter extends ArrayAdapter<loaihang> {
    private Context context;
    frg_loaihang fragment;
    private ArrayList<loaihang> list;
    TextView tvMaLoai, tvTenLoai;
    ImageView imgDel;

    public loaiHangAdapter(@NonNull Context context, frg_loaihang fragment, ArrayList<loaihang> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loai_hang, null);
        }
        final loaihang item = list.get(position);
        if (item != null) {
            tvMaLoai = v.findViewById(R.id.tvMaLoaiHang);
            tvMaLoai.setText("Mã Loại: " + item.getMaLoaiH());
            tvTenLoai = v.findViewById(R.id.tvTenLoaiHang);
            tvTenLoai.setText("Tên Loại: " + item.getTenLoaiH());

            imgDel = v.findViewById(R.id.imgDeleteLH);
            checkAn();
        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức xóa
                fragment.xoa(String.valueOf(item.getMaLoaiH()));
            }
        });
        return v;
    }
    public void checkAn() {
        // Lấy loại tài khoản từ SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("User_File", Context.MODE_PRIVATE);
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
