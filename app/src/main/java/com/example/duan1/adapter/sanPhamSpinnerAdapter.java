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
import com.example.duan1.model.sanpham;

import java.util.ArrayList;

public class sanPhamSpinnerAdapter extends ArrayAdapter<sanpham> {
    private Context context;
    private ArrayList<sanpham> list;

    TextView tvMaSP, tvTenSP;

    public sanPhamSpinnerAdapter(@NonNull Context context, ArrayList<sanpham> list) {
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
            v = inflater.inflate(R.layout.item_sanpham_spinner, null);
        }
        final sanpham item = list.get(position);
        if (item != null) {
            tvMaSP = v.findViewById(R.id.tvMaSPSpin);
            tvMaSP.setText(item.getMaSP() + ". ");
            tvTenSP = v.findViewById(R.id.tvTenSPSpin);
            tvTenSP.setText(item.getTenSP());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham_spinner, null);
        }
        final sanpham item = list.get(position);
        if (item != null) {
            tvMaSP = v.findViewById(R.id.tvMaSPSpin);
            tvMaSP.setText(item.getMaSP() + ". ");
            tvTenSP = v.findViewById(R.id.tvTenSPSpin);
            tvTenSP.setText(item.getTenSP());
        }
        return v;
    }
}
