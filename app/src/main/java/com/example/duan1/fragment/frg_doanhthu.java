package com.example.duan1.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.doanhThuAdapter;
import com.example.duan1.adapter.hoaDonAdapter;
import com.example.duan1.adapter.topAdapter;
import com.example.duan1.dao.hoaDonDao;
import com.example.duan1.model.top10;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class frg_doanhthu extends Fragment {
    Button btnDoanhThu, btnhomnay;
    EditText edTuNgay, edDenNgay;
    TextView tvDoanhThu;
    ListView lvTop;
    hoaDonDao dao;
    ArrayList<top10> list;
    doanhThuAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int mYear, mMonth, mDay;
    private static String currentUser;

    public frg_doanhthu() {
        // Required empty public constructor
    }

    public static void setUserInfo(String user) {
        currentUser = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_doanhthu, container, false);
        edTuNgay = v.findViewById(R.id.edTuNgay);
        edDenNgay = v.findViewById(R.id.edDenNgay);
        tvDoanhThu = v.findViewById(R.id.tvDoanhThu);
        btnDoanhThu = v.findViewById(R.id.btnDoanhThu);
        btnhomnay = v.findViewById(R.id.btnhomnay);
        lvTop = v.findViewById(R.id.lvTop);

        lvTop = v.findViewById(R.id.lvTop);
        list = new ArrayList<>();
        adapter = new doanhThuAdapter(getContext(), this, list);
        lvTop.setAdapter(adapter);


        hienToday();
        doanhThu();
        doanhThuToday();

        edTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        edDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        return v;
    }


    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edTuNgay.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edDenNgay.setText(sdf.format(c.getTime()));
        }
    };

    public void doanhThuToday() {
        SharedPreferences pref = getActivity().getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("nhanvien".equals(loaiTaiKhoan)) {
            btnhomnay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
                    int todayRevenue = hoaDonDao.getDoanhThuForUser(currentUser, sdf.format(new Date()), sdf.format(new Date()));
                    tvDoanhThu.setText(todayRevenue + " VND");
                    ProductsUser(currentUser, sdf.format(new Date()), sdf.format(new Date()));
                }
            });

        } else {
            btnhomnay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
                    int todayRevenue = hoaDonDao.getDoanhThu(sdf.format(new Date()), sdf.format(new Date()));
                    tvDoanhThu.setText(todayRevenue + " VND");
                    displayTopProducts(sdf.format(new Date()), sdf.format(new Date()));
                }
            });
        }
    }

    public void doanhThu() {
        SharedPreferences pref = getActivity().getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("nhanvien".equals(loaiTaiKhoan)) {
            btnDoanhThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tuNgay = edTuNgay.getText().toString();
                    String denNgay = edDenNgay.getText().toString();
                    hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
                    if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
                    } else {
                        int doanhThu = hoaDonDao.getDoanhThuForUser(currentUser, tuNgay, denNgay);
                        tvDoanhThu.setText(doanhThu + " VND");
                        ProductsUser(currentUser, tuNgay, denNgay);

                    }
                }
            });
        } else {
            btnDoanhThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tuNgay = edTuNgay.getText().toString();
                    String denNgay = edDenNgay.getText().toString();
                    hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
                    if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
                    } else {
                        int doanhThu = hoaDonDao.getDoanhThu(tuNgay, denNgay);
//                    Log.d("DEBUG", "onClick - Doanh Thu: " + doanhThu);
                        tvDoanhThu.setText(doanhThu + " VND");

                        displayTopProducts(tuNgay, denNgay);
                    }
                }
            });
        }
    }

    public void hienToday() {
        SharedPreferences pref = getActivity().getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("nhanvien".equals(loaiTaiKhoan)) {
            hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
            int todayRevenue = hoaDonDao.getDoanhThuForUser(currentUser, sdf.format(new Date()), sdf.format(new Date()));
            tvDoanhThu.setText(todayRevenue + " VND");
            ProductsUser(currentUser, sdf.format(new Date()), sdf.format(new Date()));
        } else {
            hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
            int todayRevenue = hoaDonDao.getDoanhThu(sdf.format(new Date()), sdf.format(new Date()));
            tvDoanhThu.setText(todayRevenue + " VND");
            displayTopProducts(sdf.format(new Date()), sdf.format(new Date()));

        }
    }

    private void displayTopProducts(String tuNgay, String denNgay) {
        // Lấy danh sách sản phẩm hàng đầu dựa trên khoảng thời gian
        hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
        ArrayList<top10> danhSachSanPhamHangDau = (ArrayList<top10>) hoaDonDao.getProducts(tuNgay, denNgay);

        // Cập nhật bộ chuyển đổi hoặc thành phần giao diện người dùng của bạn với danh sách mới
        adapter = new doanhThuAdapter(getContext(), this, danhSachSanPhamHangDau);
        lvTop.setAdapter(adapter);
    }

    private void ProductsUser(String user, String tuNgay, String denNgay) {
        hoaDonDao hoaDonDao = new hoaDonDao(getActivity());
        ArrayList<top10> danhSachSanPhamHangDau = (ArrayList<top10>) hoaDonDao.getProductsUser(user, tuNgay, denNgay);
        // Cập nhật bộ chuyển đổi hoặc thành phần giao diện người dùng của bạn với danh sách mới
        adapter = new doanhThuAdapter(getContext(), this, danhSachSanPhamHangDau);
        Log.d("DEBUG", "Size of danhSachSanPhamHangDau: " + danhSachSanPhamHangDau.size());
        lvTop.setAdapter(adapter);
    }
}