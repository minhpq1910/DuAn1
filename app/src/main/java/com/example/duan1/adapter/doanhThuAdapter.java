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
import com.example.duan1.fragment.frg_doanhthu;

import com.example.duan1.model.top10;

import java.util.ArrayList;

public class doanhThuAdapter extends ArrayAdapter<top10> {
    private Context context;
    frg_doanhthu fragment;
    ArrayList<top10> list;
    TextView tvtopSP, tvtopSL;
    ImageView imgDel;

    public doanhThuAdapter(@NonNull Context context, frg_doanhthu fragment, ArrayList<top10> list) {
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
            v = inflater.inflate(R.layout.item_top, null);
        }
        final top10 item = list.get(position);
        if (item != null) {

            tvtopSP = v.findViewById(R.id.tvtopSP);
            tvtopSP.setText("" + item.getTenSP());
            tvtopSL = v.findViewById(R.id.tvtopSL);
            tvtopSL.setText("x" + item.getSL());
        }
        return v;
    }

}
