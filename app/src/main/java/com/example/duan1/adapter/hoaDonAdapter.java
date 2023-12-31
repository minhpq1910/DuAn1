package com.example.duan1.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.R;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.dao.sanPhamDao;
import com.example.duan1.fragment.frg_hoadon;
import com.example.duan1.model.hoadon;
import com.example.duan1.model.nhanVien;
import com.example.duan1.model.sanpham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class hoaDonAdapter extends ArrayAdapter<hoadon> {
    private Context context;
    frg_hoadon fragment;
    private ArrayList<hoadon> list;
    TextView tv_MaHD, tv_nhanV, tv_SP, tv_SL, tv_Gia, tv_Ngay, tv_trangthai;
    ImageView imgDel;
    sanPhamDao spDao;
    nhanVienDao nvDao;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public hoaDonAdapter(@NonNull Context context, frg_hoadon fragment, ArrayList<hoadon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoadon, null);
        }
        final hoadon item = list.get(position);
        if (item != null) {
            tv_MaHD = v.findViewById(R.id.tv_MaHD);
            tv_MaHD.setText("Mã Hoá Đơn: " + item.getMaHD());
            spDao = new sanPhamDao(context);
            sanpham sp = spDao.getID(String.valueOf(item.getMaSP()));
            tv_SP = v.findViewById(R.id.tv_SP);
            tv_SP.setText("Tên sản phẩm: " + sp.getTenSP());


            tv_nhanV = v.findViewById(R.id.tv_nhanV);
            tv_nhanV.setText(item.getHoTenNV());

            tv_SL = v.findViewById(R.id.tv_SL);
            tv_SL.setText("Số lượng: " + item.getSL());

            tv_Gia = v.findViewById(R.id.tv_Gia);

            int gia = item.getGia();
            gia = sp.getGia() * item.getSL();
            tv_Gia.setText("Giá: " + gia);


            tv_trangthai = v.findViewById(R.id.tv_trangthai);
            if (item.getTrangThai() == 1) {
                tv_trangthai.setTextColor(Color.GREEN);
                tv_trangthai.setText("Đã thanh toán");
            } else {
                tv_trangthai.setTextColor(Color.RED);
                tv_trangthai.setText("Chưa thanh toán");
            }
            tv_Ngay = v.findViewById(R.id.tv_Ngay);
            tv_Ngay.setText("Ngày : " + sdf.format(item.getNgay()));
            imgDel = v.findViewById(R.id.imgdelete_HD);

            anXoa();
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goi phuong thuc xoa
                fragment.xoa(String.valueOf(item.getMaHD()));
            }
        });
        return v;

    }
    public void anXoa() {
        // Lấy loại tài khoản từ SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("LoaiTK_File", Context.MODE_PRIVATE);
        String loaiTaiKhoan = pref.getString("LoaiTaiKhoan", "");
        Log.d("LoaiTaiKhoan", loaiTaiKhoan);
        if ("admin".equals(loaiTaiKhoan)) {
            // Hiển thị nút xoá khi là admin
            imgDel.setVisibility(View.VISIBLE);
        } else {
            // Ẩn nút xoá khi là nhân viên
            imgDel.setVisibility(View.GONE);
        }
    }
}
