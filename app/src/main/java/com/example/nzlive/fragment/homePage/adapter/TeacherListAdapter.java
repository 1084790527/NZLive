package com.example.nzlive.fragment.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherListBean;

import java.util.List;

public class TeacherListAdapter extends BaseAdapter {

    private List<TeacherListBean> mlist;
    private Context mContext;

    public TeacherListAdapter(List<TeacherListBean> mList, Context context){
        this.mlist=mList;
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return mlist==null ? 0:mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.teacher_list_view,null);

        TeacherListBean bean=mlist.get(position);

        ((TextView)convertView.findViewById(R.id.tv_userid)).setText(bean.getUserid());
        ((TextView)convertView.findViewById(R.id.tv_username)).setText(bean.getUsername());
        ((TextView)convertView.findViewById(R.id.tv_dormroom)).setText(bean.getDormroom());
        ((TextView)convertView.findViewById(R.id.tv_status)).setText(bean.getStatus());
        return convertView;
    }
}
