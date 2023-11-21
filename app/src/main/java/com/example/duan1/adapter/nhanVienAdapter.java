package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.R;
import com.example.duan1.fragment.frg_nhanvien;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;

public class nhanVienAdapter extends ArrayAdapter<nhanVien> {
    private Context context;
    frg_nhanvien fragment;
    private ArrayList<nhanVien> list;
    TextView tvMaNV, tvTenNV, tvMK;
    ImageView imgDel;

    public nhanVienAdapter(@NonNull Context context, frg_nhanvien fragment, ArrayList<nhanVien> list) {
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
            v = inflater.inflate(R.layout.item_nhanvien, null);
        }
        final nhanVien item = list.get(position);
        if (item != null) {
            tvMaNV = v.findViewById(R.id.tvMaNV);
            tvMaNV.setText("Mã nhân viên: " + item.getMaNV());

            tvTenNV = v.findViewById(R.id.tvTenNV);
            tvTenNV.setText("Tên nhân viên: " + item.getHoTen());
            tvMK = v.findViewById(R.id.tvMKNV);
            tvMK.setText("Mật khẩu: " + item.getMatKhau());

            imgDel = v.findViewById(R.id.imgDeleteLS);
        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức xóa
                fragment.xoa(String.valueOf(item.getMaNV()));

            }
        });
        return v;
    }
}
