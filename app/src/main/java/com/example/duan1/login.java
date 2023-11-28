package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.duan1.dao.adminDao;
import com.example.duan1.dao.nhanVienDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    TextInputEditText ed_txtuser, ed_txtpass;
    TextInputLayout in_user, in_pass;
    Button btnLogin, btnCancel;
    CheckBox chkSave;
    nhanVienDao nvDao;
    adminDao aDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_txtuser = findViewById(R.id.ed_txtUser);
        ed_txtpass = findViewById(R.id.ed_txtPass);
        in_user = findViewById(R.id.in_User);
        in_pass = findViewById(R.id.in_Pass);
        btnLogin = findViewById(R.id.btn_LogIn);
        btnCancel = findViewById(R.id.btn_Cancel);
        chkSave = findViewById(R.id.chkSave);
        nvDao = new nhanVienDao(this);
        aDao = new adminDao(this);

        SharedPreferences pref = getSharedPreferences("User_File", MODE_PRIVATE);
        ed_txtuser.setText(pref.getString("Username", ""));
        ed_txtpass.setText(pref.getString("Password", ""));
        chkSave.setChecked(pref.getBoolean("Remember", false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        String user = ed_txtuser.getText().toString();
        String pass = ed_txtpass.getText().toString();
        if (user.isEmpty() || pass.isEmpty()) {
            if (user.equals("")) {
                in_user.setError("Không được để trống tên đăng nhập");
            } else {
                in_user.setError(null);
            }

            if (pass.equals("")) {
                in_pass.setError("Không được để trống mật khẩu");
            } else {
                in_pass.setError(null);
            }
        } else {
            int nvLoginResult = nvDao.checkLogin(user, pass);
            int adminLoginResult = aDao.checkLogin(user, pass);
            if (nvLoginResult == 1) {
                Toast.makeText(this, "Login thành công vào tài khoản nhân viên", Toast.LENGTH_SHORT).show();
                rememberUser(user, pass, chkSave.isChecked(), "nhanvien");
                Intent i = new Intent(login.this, screen_nhanvien.class);
                i.putExtra("MaNV", user);
                i.putExtra("LoaiTK", "nhanvien"); // Truyền loại tài khoản
                startActivity(i);
                finish();
            } else if (adminLoginResult == 1) {
                Toast.makeText(this, "Login thành công vào tài khoản admin", Toast.LENGTH_SHORT).show();
                rememberUser(user, pass, chkSave.isChecked(), "admin");
                Intent i = new Intent(login.this, MainActivity.class);
                i.putExtra("MaNV", user);
                i.putExtra("LoaiTK", "admin"); // Truyền loại tài khoản
                startActivity(i);
                finish();
            } else {
                in_user.setError("Tên đăng nhập hoặc mật khẩu không đúng");
                in_pass.setError("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        }
    }

    private void rememberUser(String u, String p, boolean status, String loaiTaiKhoan) {
        SharedPreferences pref = getSharedPreferences("User_File", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            //lưu dữ liệu
            edit.putString("Username", u);
            edit.putString("Password", p);
            edit.putBoolean("Remember", status);
            edit.putString("LoaiTaiKhoan", loaiTaiKhoan); // Lưu loại tài khoản
        }
        //lưu lại toàn bộ
        edit.commit();
    }
}