package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.nhanVienAdapter;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.model.nhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class frg_nhanvien extends Fragment {
    ListView lvNV;
    ArrayList<nhanVien> list;
    static nhanVienDao dao;
    nhanVienAdapter adapter;
    nhanVien item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaNV, edTenNV, edMK_NV, edtNhapLaiMKMK_NV, edUrlNV;
    Button btnSave, btnCancel;


    public frg_nhanvien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_nhanvien, container, false);
        lvNV = v.findViewById(R.id.lvNV);
        fab = v.findViewById(R.id.fab);
        dao = new nhanVienDao(getActivity());
        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);

            }
        });
        lvNV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return v;
    }

    void capNhatLv() {
        list = (ArrayList<nhanVien>) dao.getAll();
        adapter = new nhanVienAdapter(getActivity(), this, list);
        lvNV.setAdapter(adapter);
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
        dialog.setContentView(R.layout.dialog_nhanvien);
        edMaNV = dialog.findViewById(R.id.edMaNV);
        edTenNV = dialog.findViewById(R.id.edTenNV);
        edMK_NV = dialog.findViewById(R.id.edMK_NV);
        edtNhapLaiMKMK_NV = dialog.findViewById(R.id.edtNhapLaiMKMK_NV);
        edUrlNV = dialog.findViewById(R.id.edUrlNV);
        btnCancel = dialog.findViewById(R.id.btnCancelTV);
        btnSave = dialog.findViewById(R.id.btnSaveTV);


        if (type != 0) {
            edMaNV.setText(String.valueOf(item.getTaiKhoan()));
            edTenNV.setText(item.getHoTen());
            edMK_NV.setText(item.getMatKhau());
            edtNhapLaiMKMK_NV.setText(item.getMatKhau());
            edUrlNV.setText(item.getAvt());
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
                item = new nhanVien();
                item.setHoTen(edTenNV.getText().toString());
                item.setMatKhau(edMK_NV.getText().toString());
                item.setAvt(edUrlNV.getText().toString());
                if (validate() > 0) {
                    if (type == 0) {
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                            edMaNV.setText("");
//                            edTenNV.setText("");
//                            edMK_NV.setText("");
//                            edtNhapLaiMKMK_NV.setText("");
//                            edUrlNV.setText("");
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        item.setTaiKhoan(edMaNV.getText().toString());
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
        if (edMaNV.getText().length() == 0 || edTenNV.getText().length() == 0 || edMK_NV.getText().length() == 0 || edtNhapLaiMKMK_NV.getText().length() == 0 || edUrlNV.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edMK_NV.getText().toString();
            String repass = edtNhapLaiMKMK_NV.getText().toString();
            if (!pass.equals(repass)) {
                Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}
