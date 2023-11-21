package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.R;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;

public class nhanVienSpinnerAdapter extends ArrayAdapter<nhanVien> {
    private Context context;
    private ArrayList<nhanVien> list;

    TextView tvMaTV, tvTenTV;

    public nhanVienSpinnerAdapter(@NonNull Context context, ArrayList<nhanVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_nhanvien_spinner, null);
        }
        final nhanVien item = list.get(position);
        if (item != null) {
            tvMaTV = v.findViewById(R.id.tvMaTVsp);
            tvMaTV.setText(item.getMaNV() + ". ");
            tvTenTV = v.findViewById(R.id.tvTenTVSp);
            tvTenTV.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_nhanvien_spinner, null);
        }
        final nhanVien item = list.get(position);
        if (item != null) {
            tvMaTV = v.findViewById(R.id.tvMaTVsp);
            tvMaTV.setText(item.getMaNV() + ". ");
            tvTenTV = v.findViewById(R.id.tvTenTVSp);
            tvTenTV.setText(item.getHoTen());
        }
        return v;
    }
}
