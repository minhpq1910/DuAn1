package com.example.duan1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;
import com.example.duan1.dao.adminDao;
import com.example.duan1.dao.nhanVienDao;
import com.example.duan1.model.admin;
import com.example.duan1.model.nhanVien;
import com.google.android.material.textfield.TextInputEditText;

public class frg_doimatkhau extends Fragment {
    TextInputEditText edPassOld, edPassChange, edRePassChange;
    Button btnSaveUserChange, btnCancleUserChange;
    adminDao addao;
    nhanVienDao nvDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doimatkhau, container, false);
        addao = new adminDao(getActivity());
        edPassOld = v.findViewById(R.id.edPassOld);
        edPassChange = v.findViewById(R.id.edPassChange);
        edRePassChange = v.findViewById(R.id.edRePassChange);
        btnSaveUserChange = v.findViewById(R.id.btnSaveUserChange);
        btnCancleUserChange  = v.findViewById(R.id.btnCancelUserChange);
        btnCancleUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPassChange.setText("");
                edRePassChange.setText("");
            }
        });

        btnSaveUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
                String user = pref.getString("Username","");
                if (validate() > 0) {

//                    nhanVien nv = nvDao.getID(user);
//                    nv.setMatKhau(edPassChange.getText().toString());
//
//                    if (nvDao.updatePass(nv) > 0) {
//                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                        edPassOld.setText("");
//                        edPassChange.setText("");
//                        edRePassChange.setText("");
//                    } else {
//                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
//                    }

                    admin admin = addao.getID(user);
                    admin.setMatKhau(edPassChange.getText().toString());

                    if (addao.updatePass(admin) > 0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassChange.setText("");
                        edRePassChange.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    public int validate(){
        int check =1;
        if (edPassOld.getText().length() == 0 || edPassChange.getText().length() == 0 || edRePassChange.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
            String passOld = pref.getString("Password","");
            String pass = edPassChange.getText().toString();
            String rePass = edRePassChange.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }
}
