package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.example.duan1.dao.adminDao;
import com.example.duan1.dao.hoaDonDao;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.dao.sanPhamDao;
import com.example.duan1.model.admin;
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
    Spinner spSP;
    EditText edSL;
    TextView tvNgay, tvGia, tvUser, tvTao;
    CheckBox chkTrangThai;
    Button btnSave, btnCancel;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    nhanVienDao nvDAO;
    adminDao aDao;
    String maNVien;
    sanPhamSpinnerAdapter spSpinAdapter;
    ArrayList<sanpham> listSP;
    sanPhamDao spDAO;
    sanpham sp;
    int maSP, Gia;
    int positionSP;
    private static String tennv, tenad;

    public frg_hoadon() {
        // Required empty public constructor
    }

    public static void setAd(String user) {
        tenad = user;
    }

    public static void setUser(String user) {
        tennv = user;
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
        tvUser = dialog.findViewById(R.id.tvUser);
        spSP = dialog.findViewById(R.id.spMaSP);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvTao = dialog.findViewById(R.id.tvTao);
        edSL = dialog.findViewById(R.id.edSL);
        tvGia = dialog.findViewById(R.id.tvGiaSP);
        chkTrangThai = dialog.findViewById(R.id.chkTrangThai);
        btnCancel = dialog.findViewById(R.id.btnCancelHD);
        btnSave = dialog.findViewById(R.id.btnSaveHD);


        SharedPreferences pref = getActivity().getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("hoadon", loaiTaiKhoan);


        String username;
        if ("nhanvien".equals(loaiTaiKhoan)) {
            nvDAO = new nhanVienDao(getContext());
            nhanVien nvien = nvDAO.getID(tennv);
            username = nvien.getHoTen();

        } else {
            aDao = new adminDao(getContext());
            admin ad = aDao.getID(tenad);
            username = ad.getHoTen();
        }


        tvTao.setVisibility(View.GONE);
        tvUser.setVisibility(View.GONE);
        edMaHD.setEnabled(false);
        // set ngày
        tvNgay.setText("Ngày: " + sdf.format(new Date()));
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // edit
        if (type != 0) {
            edMaHD.setText(String.valueOf(item.getMaHD()));
            for (int i = 0; i < listSP.size(); i++)
                if (item.getMaSP() == (listSP.get(i).getMaSP())) {
                    positionSP = i;
                }
            spSP.setSelection(positionSP);

            tvUser.setText(username);

            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));
            edSL.setText("" + item.getSL());
            tvGia.setText("Giá: " + item.getGia());
            if (item.getTrangThai() == 1) {
                chkTrangThai.setChecked(true);
            } else {
                chkTrangThai.setChecked(false);
            }

            if (item.getTrangThai() == 0) {
                tvUser.setEnabled(true);
                spSP.setEnabled(true);
                edSL.setEnabled(true);
                chkTrangThai.setEnabled(true);
                btnSave.setEnabled(true);
            } else {
                // Vô hiệu hóa nếu trạng thái không phải là 0
                tvUser.setEnabled(false);
                spSP.setEnabled(false);
                edSL.setEnabled(false);
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

                item.setHoTenNV(username);

                item.setNgay(new Date());
                item.setGia(Gia);
                item.setSL(parseInt(edSL.getText().toString(), 0));
                if (chkTrangThai.isChecked()) {
                    item.setTrangThai(1);
                } else {
                    item.setTrangThai(0);
                }
                if (validate() == 1) {
                    if (type == 0) {
                        // insert
                        if (dao.insert(item) > 0) {
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
            }
        });
        dialog.show();
    }

    public int validate() {
        int check = -1;
        String sl = edSL.getText().toString().trim();

        if (TextUtils.isEmpty(sl)) {
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.isDigitsOnly(sl)) {
            Toast.makeText(getContext(), "Số lượng phải là một số", Toast.LENGTH_SHORT).show();
        } else {
            // Nếu đầu vào là số, chuyển đổi sang kiểu số nguyên
            int quantity = Integer.parseInt(sl);
            if (quantity <= 0) {
                // Nếu số lượng không lớn hơn 0, hiển thị một thông báo
                Toast.makeText(getContext(), "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            } else {
                check = 1;
            }
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

    public void checkAn() {


    }
}