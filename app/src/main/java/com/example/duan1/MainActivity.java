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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.duan1.fragment.frg_addNv;
import com.example.duan1.fragment.frg_changePass;
import com.example.duan1.fragment.frg_doanhthu;
import com.example.duan1.fragment.frg_hoadon;
import com.example.duan1.fragment.frg_loaihang;
import com.example.duan1.fragment.frg_sanpham;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Context context = this;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        NavigationUser();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuQLHD) {
                    frg_hoadon frgHd = new frg_hoadon();
                    relaceFrg(frgHd);
                    toolbar.setTitle("Quản lý hoá đơn");
                } else if (item.getItemId() == R.id.menuQLLH) {
                    frg_loaihang frgLH = new frg_loaihang();
                    relaceFrg(frgLH);
                    toolbar.setTitle("Quản lý loại hàng");
                } else if (item.getItemId() == R.id.menuQLSP) {
                    frg_sanpham frgSP = new frg_sanpham();
                    relaceFrg(frgSP);
                    toolbar.setTitle("Quản lý sản phẩm");
                } else if (item.getItemId() == R.id.menuQLNV) {
                    frg_nhanvien frgNV = new frg_nhanvien();
                    relaceFrg(frgNV);
                    toolbar.setTitle("Quản lý nhân viên");
                } else if (item.getItemId() == R.id.menuTNV) {
                    frg_addNv frgTNV = new frg_addNv();
                    relaceFrg(frgTNV);
                    toolbar.setTitle("Thêm nhân viên");
                } else if (item.getItemId() == R.id.menuDMK) {
                    frg_changePass frgCP = new frg_changePass();
                    relaceFrg(frgCP);
                    toolbar.setTitle("Đổi mật khẩu");
                } else if (item.getItemId() == R.id.menuDX) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chắn muốn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, login.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.create().show();
                } else if (item.getItemId() == R.id.menuDT) {
                    frg_doanhthu frgDT = new frg_doanhthu();
                    relaceFrg(frgDT);
                    toolbar.setTitle("Doanh thu");
                }



                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        // Mở quản lý phiếu mượn lên trước
        frg_hoadon frgHd = new frg_hoadon();
        relaceFrg(frgHd);
        toolbar.setTitle("Quản lý hoá đơn");
    }

    public void NavigationUser() {
        NavigationView nv = findViewById(R.id.navigationView);
        View headerView = nv.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.NameUser);
        // lấy tên người dùng từ Intent
        Intent i = getIntent();
        String tentv = i.getStringExtra("MaNV");
        tvUser.setText(tentv);
        // điều hướng
        if (tentv.equals("hihi")) {
            nv.getMenu().findItem(R.id.menuTNV).setVisible(true);
        }
    }

    public void relaceFrg(Fragment frg) {
        FragmentManager fg = getSupportFragmentManager();
        fg.beginTransaction().replace(R.id.frameLayout, frg).commit();
    }
}