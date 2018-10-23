package com.example.nzlive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.InforMationListBean;
import com.example.nzlive.fragment.adapter.InforMationListAdapter;
import com.example.nzlive.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InforMationFragment extends Fragment implements AdapterView.OnItemClickListener {


    private View view;
    private ListView lv_information;
    private InforMationListAdapter adapter;
    private List<InforMationListBean> list;
    public InforMationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_infor_mation, container, false);

        init();

        setData();

        return view;
    }

    private void setData() {
        for (int i=0;i<30;i++){
            InforMationListBean bean=new InforMationListBean();
            bean.setLogo(R.drawable.logo);
            bean.setTitle(i+"测试");
            bean.setText(i+"测试测试");
            list.add(bean);
            adapter.notifyDataSetChanged();
        }
    }

    private void init() {
        lv_information=view.findViewById(R.id.lv_information);
        lv_information.setOnItemClickListener(this);
        list=new ArrayList<>();
        adapter=new InforMationListAdapter(getActivity(),list);
        lv_information.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.Logd(getActivity(),"点第"+position+"个");
    }
}
