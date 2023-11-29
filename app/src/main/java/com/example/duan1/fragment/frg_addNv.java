package com.example.duan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.model.nhanVien;

import java.util.ArrayList;

public class frg_addNv extends Fragment {
    ArrayList<nhanVien> list;
    static nhanVienDao dao;
    nhanVien item;
    EditText edNV, edTenNV, edPass, edRePass, edUrlNV;
    Button btnSave, btnCancel;

    public frg_addNv() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_nhanvien, container, false);
        dao = new nhanVienDao(getActivity());

        edNV = view.findViewById(R.id.edNV);
        edTenNV = view.findViewById(R.id.edTenNV);
        edPass = view.findViewById(R.id.edPass);
        edRePass = view.findViewById(R.id.edRePass);
        edUrlNV = view.findViewById(R.id.edUrlNV);
        btnCancel = view.findViewById(R.id.btnCancelNV);
        btnSave = view.findViewById(R.id.btnSaveNV);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUrlNV.setText("");
                edNV.setText("");
                edTenNV.setText("");
                edPass.setText("");
                edRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new nhanVien();
                item.setTaiKhoan(edNV.getText().toString());
                item.setHoTen(edTenNV.getText().toString());
                item.setMatKhau(edPass.getText().toString());
                item.setAvt(edUrlNV.getText().toString());
                if (validate() == 1) {
                    if (dao.insert(item) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edUrlNV.setText("");
                        edNV.setText("");
                        edTenNV.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate() {
        int check = 1;
        if (edNV.getText().length() == 0 || edTenNV.getText().length() == 0 || edPass.getText().length() == 0 || edRePass.getText().length() == 0 || edUrlNV.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edPass.getText().toString();
            String repass = edRePass.getText().toString();
            if (!pass.equals(repass)) {
                Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}