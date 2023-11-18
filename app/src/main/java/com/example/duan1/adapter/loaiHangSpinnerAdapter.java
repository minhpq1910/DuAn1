package com.example.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.model.loaihang;
import com.example.duan1.R;

import java.util.ArrayList;

public class loaiHangSpinnerAdapter extends ArrayAdapter<loaihang> {
    private Context context;
    ArrayList<loaihang> list;
    TextView tvMaLoaiH, tvTenLoaiH;

    public loaiHangSpinnerAdapter(@NonNull Context context, ArrayList<loaihang> list) {
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
            v = inflater.inflate(R.layout.item_loaihang_spinner, null);

        }
        final loaihang item = list.get(position);
        if (item != null) {
            tvMaLoaiH = v.findViewById(R.id.tvMaLoaiHSpin);
            tvMaLoaiH.setText(item.getMaLoaiH() + ". ");

            tvTenLoaiH = v.findViewById(R.id.tvTenLoaiHSpin);
            tvTenLoaiH.setText(item.getTenLoaiH());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaihang_spinner, null);

        }
        final loaihang item = list.get(position);
        if (item != null) {
            tvMaLoaiH = v.findViewById(R.id.tvMaLoaiHSpin);
            tvMaLoaiH.setText(item.getMaLoaiH() + ". ");

            tvTenLoaiH = v.findViewById(R.id.tvTenLoaiHSpin);
            tvTenLoaiH.setText(item.getTenLoaiH());
        }
        return v;
    }
}
