package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.duan1.adapter.hoaDonAdapter;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.fragment.frg_doanhthu;
import com.example.duan1.fragment.frg_doimatkhau;
import com.example.duan1.fragment.frg_hoadon;
import com.example.duan1.fragment.frg_loaihang;
import com.example.duan1.fragment.frg_sanpham;
import com.example.duan1.model.nhanVien;
import com.google.android.material.navigation.NavigationView;

public class screen_nhanvien extends AppCompatActivity {
    Toolbar toolbar_nv;
    NavigationView navigationView_nv;
    DrawerLayout drawerLayout_nv;
    hoaDonAdapter adapter;
    nhanVienDao nvDao;
    Context context = this;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_nhanvien);
        toolbar_nv = findViewById(R.id.toolbar_nv);
        navigationView_nv = findViewById(R.id.navigationView_nv);
        drawerLayout_nv = findViewById(R.id.drawerLayout_nv);

        setSupportActionBar(toolbar_nv);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout_nv, toolbar_nv, R.string.open_nv, R.string.close_nv);
        drawerLayout_nv.addDrawerListener(toggle);
        toggle.syncState();
        NavigationUser();

        //lấy thông tin người đăng nhập
        String userName = getIntent().getStringExtra("NV");
        frg_doanhthu.setUserInfo(userName);


        navigationView_nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuQLHD_nv) {
                    frg_hoadon frgHd = new frg_hoadon();
                    relaceFrg(frgHd);
                    toolbar_nv.setTitle("Quản lý hoá đơn");
                } else if (item.getItemId() == R.id.menuQLLH_nv) {
                    frg_loaihang frgLH = new frg_loaihang();
                    relaceFrg(frgLH);
                    toolbar_nv.setTitle("Quản lý loại hàng");
                } else if (item.getItemId() == R.id.menuQLSP_nv) {
                    frg_sanpham frgSP = new frg_sanpham();
                    relaceFrg(frgSP);
                    toolbar_nv.setTitle("Quản lý sản phẩm");
                } else if (item.getItemId() == R.id.menuDMK_nv) {
                    frg_doimatkhau frgDMK = new frg_doimatkhau();
                    relaceFrg(frgDMK);
                    toolbar_nv.setTitle("Đổi mật khẩu");
                } else if (item.getItemId() == R.id.menuDX_nv) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chắn muốn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(screen_nhanvien.this, login.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.create().show();
                } else if (item.getItemId() == R.id.menuDT) {
                    frg_doanhthu frgDT = new frg_doanhthu();
                    relaceFrg(frgDT);
                    toolbar_nv.setTitle("Doanh thu");
                }

                drawerLayout_nv.closeDrawer(navigationView_nv);
                return true;
            }
        });
        // Mở quản lý hoá đơn lên trước
        frg_hoadon frgHd = new frg_hoadon();
        relaceFrg(frgHd);
        toolbar_nv.setTitle("Quản lý hoá đơn");
    }

    public void NavigationUser() {
        NavigationView nv = findViewById(R.id.navigationView_nv);
        View headerView = nv.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.NameUser);
        // lấy tên người dùng từ Intent
        Intent i = getIntent();
        String tennv = i.getStringExtra("NV");
        nvDao = new nhanVienDao(this);
        nhanVien nvien = nvDao.getID(tennv);
        String username = nvien.getHoTen();
        tvUser.setText("NV:  " + username);
    }

    public void relaceFrg(Fragment frg) {
        FragmentManager fg = getSupportFragmentManager();
        fg.beginTransaction().replace(R.id.frameLayout_nv, frg).commit();
    }
}