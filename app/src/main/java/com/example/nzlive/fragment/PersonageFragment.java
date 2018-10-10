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

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button btn_logout;

    public PersonageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_personage, container, false);

        init();

        return view;
    }

    private void init() {
        btn_logout=view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(this);
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
