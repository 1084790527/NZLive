package com.example.nzlive.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.Splash;
import com.example.nzlive.login.login;
import com.example.nzlive.util.ConstantValue;
import com.example.nzlive.util.SharePreUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button btn_logout;
    private TextView tv_userid,tv_system,tv_class,tv_username,tv_dormroom,tv_counselorid;


    public PersonageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_personage, container, false);

        init();

        setTV();

        return view;
    }

    private void setTV() {
        String s=SharePreUtil.getData(getContext(),"user","data","");
        JSONObject user=null;
        try {
            user=new JSONObject(s);
            tv_userid.setText(user.getString("userid")+"");
            tv_system.setText("计算机系");
            tv_class.setText("16应用");
            tv_username.setText(user.getString("username")+"");
            tv_dormroom.setText(user.getString("dormroom")+"");
            tv_counselorid.setText("辅导员");
        } catch (JSONException e) {
            e.printStackTrace();
            tv_userid.setText("服务器数据错误！");
            return;
        }
        if (user==null){
            tv_userid.setText("服务器数据错误！");
            return;
        }
    }

    private void init() {
        btn_logout=view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        tv_userid=view.findViewById(R.id.tv_userid);
        tv_system=view.findViewById(R.id.tv_system);
        tv_class=view.findViewById(R.id.tv_class);
        tv_username=view.findViewById(R.id.tv_username);
        tv_dormroom=view.findViewById(R.id.tv_dormroom);
        tv_counselorid=view.findViewById(R.id.tv_counselorid);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                SharePreUtil.saveBoolean(getActivity(), ConstantValue.ISFIRST, true);
                startActivity(new Intent(getActivity(), login.class));
                getActivity().finish();
                break;
        }
    }
}
