package com.example.duan1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.dao.adminDao;
import com.example.duan1.model.admin;
import com.google.android.material.textfield.TextInputEditText;

public class frg_doimatkhauad extends Fragment {

    TextInputEditText edPassOldad, edPassChangead, edRePassChangead;
    Button btnSaveUserChangead, btnCancleUserChangead;
    adminDao addao;


    public frg_doimatkhauad() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frg_doimatkhauad, container, false);
        addao = new adminDao(getActivity());
        edPassOldad = v.findViewById(R.id.edPassOldad);
        edPassChangead = v.findViewById(R.id.edPassChangead);
        edRePassChangead = v.findViewById(R.id.edRePassChangead);
        btnSaveUserChangead = v.findViewById(R.id.btnSaveUserChangead);
        btnCancleUserChangead  = v.findViewById(R.id.btnCancelUserChangead);

        btnCancleUserChangead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOldad.setText("");
                edPassChangead.setText("");
                edRePassChangead.setText("");
            }
        });

        btnSaveUserChangead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
                String user = pref.getString("Username","");
                if (validate() > 0) {

                    admin admin = addao.getID(user);
                    admin.setMatKhau(edPassChangead.getText().toString());

                    if (addao.updatePass(admin) > 0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOldad.setText("");
                        edPassChangead.setText("");
                        edRePassChangead.setText("");
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
        if (edPassOldad.getText().length() == 0 || edPassChangead.getText().length() == 0 || edRePassChangead.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            SharedPreferences pref = getActivity().getSharedPreferences("User_File", Context.MODE_PRIVATE);
            String passOld = pref.getString("Password","");
            String pass = edPassChangead.getText().toString();
            String rePass = edRePassChangead.getText().toString();
            if (!passOld.equals(edPassOldad.getText().toString())){
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