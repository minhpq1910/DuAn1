package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.loaiHangAdapter;
import com.example.duan1.dao.loaiHangDao;
import com.example.duan1.model.loaihang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class frg_loaihang extends Fragment {
    ListView lvLoaiH;
    ArrayList<loaihang> list;
    static loaiHangDao dao;
    loaiHangAdapter adapter;
    loaihang item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaLoaiHang, edTenLoaiHang;
    Button btnSave, btnCancel;

    public frg_loaihang() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_loaihang, container, false);
        lvLoaiH = v.findViewById(R.id.lvLoaiH);
        fab = v.findViewById(R.id.fab);
        dao = new loaiHangDao(getActivity());
        capNhatLv();
        checkAn();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        return v;
    }

    void capNhatLv() {
        list = (ArrayList<loaihang>) dao.getAll();
        adapter = new loaiHangAdapter(getActivity(), this, list);
        lvLoaiH.setAdapter(adapter);
    }

    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                capNhatLv();
                dialog.cancel();
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getContext(), "Không xóa", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loai_hang);
        edMaLoaiHang = dialog.findViewById(R.id.edMaLoaiHang);
        edTenLoaiHang = dialog.findViewById(R.id.edTenLoaiHang);
        btnCancel = dialog.findViewById(R.id.btnCancelLH);
        btnSave = dialog.findViewById(R.id.btnSaveLH);

        edMaLoaiHang.setEnabled(false);
        if (type != 0) {
            edMaLoaiHang.setText(String.valueOf(item.getMaLoaiH()));
            edTenLoaiHang.setText(item.getTenLoaiH());
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new loaihang();
                item.setTenLoaiH(edTenLoaiHang.getText().toString());
                if (validate() > 0) {
                    if (type == 0) {
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        item.setMaLoaiH(Integer.parseInt(edMaLoaiHang.getText().toString()));
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int validate() {
        int check = 1;
        if (edTenLoaiHang.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void checkAn() {
        SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("admin".equals(loaiTaiKhoan)) {
            // Hiển thị nút xoá khi là admin
            fab.setVisibility(View.VISIBLE);
            lvLoaiH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    item = list.get(position);
                    openDialog(getActivity(), 1);
                    return false;
                }
            });
        } else {
            // Ẩn nút xoá khi là nhân viên
            fab.setVisibility(View.GONE);
        }
    }
}