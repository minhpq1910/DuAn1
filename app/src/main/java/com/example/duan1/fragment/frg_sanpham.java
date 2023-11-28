package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.loaiHangSpinnerAdapter;
import com.example.duan1.adapter.sanPhamAdapter;
import com.example.duan1.dao.loaiHangDao;
import com.example.duan1.dao.sanPhamDao;
import com.example.duan1.model.loaihang;
import com.example.duan1.model.sanpham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class frg_sanpham extends Fragment {
    ListView lvSP;
    sanPhamDao dao;
    sanPhamAdapter adapter;
    sanpham item;
    List<sanpham> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaSP, edTenSP, edSL, edGia, edAvt;
    Spinner spinner;
    Button btnSave, btnCancel;

    loaiHangSpinnerAdapter spinnerAdapter;
    ArrayList<loaihang> listLoaiH;
    loaiHangDao loaihangDAO;
    loaihang loaiHang;
    int maLoaiH, position;
    private SearchView searchView;
    private String loaiTaiKhoan;

    public frg_sanpham() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_sanpham, container, false);
        lvSP = v.findViewById(R.id.lvSanP);
        dao = new sanPhamDao(getActivity());
        capNhatLv();
        fab = v.findViewById(R.id.fab);
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
        list = (ArrayList<sanpham>) dao.getAll();
        adapter = new sanPhamAdapter(getActivity(), this, list);
        lvSP.setAdapter(adapter);
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
        dialog.setContentView(R.layout.dialog_sanpham);
        edMaSP = dialog.findViewById(R.id.edMaSP);
        edTenSP = dialog.findViewById(R.id.edTenSP);
        edSL = dialog.findViewById(R.id.edSL);
        spinner = dialog.findViewById(R.id.spLoaiH);
        edGia = dialog.findViewById(R.id.edGia);
        edAvt = dialog.findViewById(R.id.edAvt);
        btnCancel = dialog.findViewById(R.id.btnCancelSP);
        btnSave = dialog.findViewById(R.id.btnSaveSP);

        listLoaiH = new ArrayList<loaihang>();
        loaihangDAO = new loaiHangDao(context);
        listLoaiH = (ArrayList<loaihang>) loaihangDAO.getAll();

        spinnerAdapter = new loaiHangSpinnerAdapter(context, listLoaiH);
        spinner.setAdapter(spinnerAdapter);
        // lay maLoaiSach
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiH = listLoaiH.get(position).getMaLoaiH();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // kiem tra tupe insert hay update
        edMaSP.setEnabled(false);
        if (type != 0) {
            edMaSP.setText(String.valueOf(item.getMaSP()));
            edTenSP.setText(item.getTenSP());
            edSL.setText(String.valueOf(item.getSL()));
            edGia.setText(String.valueOf(item.getGia()));
            edAvt.setText(item.getUrlAvt());
            for (int i = 0; i < listLoaiH.size(); i++)
                if (item.getMaLoaiH() == (listLoaiH.get(i).getMaLoaiH())) {
                    position = i;
                }
            Log.i("demo", "posSach " + position);
            spinner.setSelection(position);
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
                item = new sanpham();
                item.setTenSP(edTenSP.getText().toString());
                item.setSL(parseInt(edSL.getText().toString(), 0));
                item.setMaLoaiH(maLoaiH);
                item.setGia(parseInt(edGia.getText().toString(), 0));
                item.setUrlAvt(edAvt.getText().toString());

                if (validate() > 0) {
                    if (type == 0) {
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        item.setMaSP(Integer.parseInt(edMaSP.getText().toString()));
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sứa thất bại", Toast.LENGTH_SHORT).show();
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
        if (edTenSP.getText().length() == 0 || edSL.getText().length() == 0 || edGia.getText().length() == 0 || edAvt.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public static int parseInt(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void checkAn() {
        SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("admin".equals(loaiTaiKhoan)) {
            // Hiển thị nút xoá khi là admin
            fab.setVisibility(View.VISIBLE);
            lvSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
