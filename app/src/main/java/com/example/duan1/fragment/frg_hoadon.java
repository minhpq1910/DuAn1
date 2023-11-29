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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.hoaDonAdapter;
import com.example.duan1.adapter.nhanVienSpinnerAdapter;
import com.example.duan1.adapter.sanPhamSpinnerAdapter;
import com.example.duan1.dao.hoaDonDao;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.dao.sanPhamDao;
import com.example.duan1.model.hoadon;
import com.example.duan1.model.nhanVien;
import com.example.duan1.model.sanpham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class frg_hoadon extends Fragment {
    ListView lvHD;
    ArrayList<hoadon> list;
    static hoaDonDao dao;
    hoaDonAdapter adapter;
    hoadon item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaHD;
    Spinner spNV, spSP;
    EditText tvSL;
    TextView tvNgay, tvGia;
    CheckBox chkTrangThai;
    Button btnSave, btnCancel;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    nhanVienSpinnerAdapter nvSpinAdapter;
    ArrayList<nhanVien> listNVien;
    nhanVienDao nvDAO;
    String maNVien;
    sanPhamSpinnerAdapter spSpinAdapter;
    ArrayList<sanpham> listSP;
    sanPhamDao spDAO;
    sanpham sp;
    int maSP, Gia;
    int positionNV, positionSP;

    public frg_hoadon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_hoadon, container, false);
        lvHD = v.findViewById(R.id.lvHoaDon);
        fab = v.findViewById(R.id.fab);
        dao = new hoaDonDao(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lvHD.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);// update
                return false;
            }
        });
        capNhatlv();
        return v;
    }

    void capNhatlv() {
        list = (ArrayList<hoadon>) dao.getAll();
        adapter = new hoaDonAdapter(getActivity(), this, list);
        lvHD.setAdapter(adapter);
    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_hoadon);
        edMaHD = dialog.findViewById(R.id.edMaHD);
        spNV = dialog.findViewById(R.id.spMaNV);
        spSP = dialog.findViewById(R.id.spMaSP);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvSL = dialog.findViewById(R.id.edSL);
        tvGia = dialog.findViewById(R.id.tvGiaSP);
        chkTrangThai = dialog.findViewById(R.id.chkTrangThai);
        btnCancel = dialog.findViewById(R.id.btnCancelHD);
        btnSave = dialog.findViewById(R.id.btnSaveHD);
        // set ngày
        tvNgay.setText("Ngày: " + sdf.format(new Date()));
        edMaHD.setEnabled(false);

        nvDAO = new nhanVienDao(context);
        listNVien = new ArrayList<nhanVien>();
        listNVien = (ArrayList<nhanVien>) nvDAO.getAll();
        nvSpinAdapter = new nhanVienSpinnerAdapter(context, listNVien);
        spNV.setAdapter(nvSpinAdapter);
        spNV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maNVien = listNVien.get(position).getTaiKhoan();
//                Toast.makeText(context, "Chọn: "+listThanhVien.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDAO = new sanPhamDao(context);
        listSP = new ArrayList<sanpham>();
        listSP = (ArrayList<sanpham>) spDAO.getAll();
        spSpinAdapter = new sanPhamSpinnerAdapter(context, listSP);
        spSP.setAdapter(spSpinAdapter);
        // lay masp
        spSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSP = listSP.get(position).getMaSP();
                Gia = listSP.get(position).getGia();
                tvGia.setText("Giá: " + Gia);
//                Toast.makeText(context, "Chọn: "+listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // edit
        if (type != 0) {
            edMaHD.setText(String.valueOf(item.getMaHD()));
            for (int i = 0; i < listNVien.size(); i++)
                if (item.getMaNV() == (listNVien.get(i).getTaiKhoan())) {
                    positionNV = i;
                }
            spNV.setSelection(positionNV);

            for (int i = 0; i < listSP.size(); i++)
                if (item.getMaSP() == (listSP.get(i).getMaSP())) {
                    positionSP = i;
                }
            spSP.setSelection(positionSP);

            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));
            tvSL.setText("" + item.getSL());
            tvGia.setText("Giá: " + item.getGia());
            if (item.getTrangThai() == 1) {
                chkTrangThai.setChecked(true);
            } else {
                chkTrangThai.setChecked(false);
            }

            if (item.getTrangThai() == 0) {
                edMaHD.setEnabled(true);
                spNV.setEnabled(true);
                spSP.setEnabled(true);
                tvSL.setEnabled(true);
                chkTrangThai.setEnabled(true);
                btnSave.setEnabled(true);
            } else {
                // Vô hiệu hóa nếu trạng thái không phải là 0
                edMaHD.setEnabled(false);
                spNV.setEnabled(false);
                spSP.setEnabled(false);
                tvSL.setEnabled(false);
                chkTrangThai.setEnabled(false);
                btnSave.setEnabled(false);
            }
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
                item = new hoadon();
                item.setMaSP(maSP);
                item.setMaNV(String.valueOf(maNVien));
                item.setNgay(new Date());
                item.setGia(Gia);
                item.setSL(Integer.parseInt(tvSL.getText().toString()));
                if (chkTrangThai.isChecked()) {
                    item.setTrangThai(1);
                } else {
                    item.setTrangThai(0);
                }
                if (type == 0) {
                    // insert
                    long insert = dao.insert(item);
                    if (insert > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatlv();
                    dialog.dismiss();
                } else {
                    // update
                    item.setMaHD(Integer.parseInt(edMaHD.getText().toString()));
                    if (dao.update(item) > 0) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                    capNhatlv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
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
                capNhatlv();
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
}