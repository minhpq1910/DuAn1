package com.example.duan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;
import com.example.duan1.adapter.topAdapter;
import com.example.duan1.dao.hoaDonDao;
import com.example.duan1.model.top10;

import java.util.ArrayList;


public class frg_top extends Fragment {
    ListView lvTop;
    ArrayList<top10> list;
    topAdapter adapter;
    hoaDonDao dao;

    public frg_top() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_top, container, false);
        lvTop = v.findViewById(R.id.lvTop);

        dao = new hoaDonDao(getActivity());
        list = (ArrayList<top10>) dao.getTop10BestSellingProducts();
        adapter = new topAdapter(getActivity(), this, list);
        lvTop.setAdapter(adapter);
        return v;
    }
}