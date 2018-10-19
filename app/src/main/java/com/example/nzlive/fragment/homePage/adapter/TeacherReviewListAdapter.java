package com.example.nzlive.fragment.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherReviewListBean;

import java.util.List;

public class TeacherReviewListAdapter extends BaseAdapter {

    private Context context;
    private List<TeacherReviewListBean> list;
    private onItemDeleteListener mOnItemDeleteListener;
    public TeacherReviewListAdapter(Context context, List<TeacherReviewListBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.teacher_review_list_view,null);
        ((TextView)convertView.findViewById(R.id.tv_id)).setText(position+1+"");
        ((TextView)convertView.findViewById(R.id.tv_userid)).setText(list.get(position).getUserid());
        ((TextView)convertView.findViewById(R.id.tv_username)).setText(list.get(position).getUsername());
        ((TextView)convertView.findViewById(R.id.tv_dormroom)).setText(list.get(position).getDormroom());
        ((TextView)convertView.findViewById(R.id.tv_num)).setText(list.get(position).getNum());
        ((TextView)convertView.findViewById(R.id.tv_date)).setText(list.get(position).getDate());
        ((TextView)convertView.findViewById(R.id.tv_data)).setText(list.get(position).getData());
        boolean is=list.get(position).isIs();
        Button btn_agree=convertView.findViewById(R.id.btn_agree);
        btn_agree.setEnabled(is);
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position,"agree");
            }
        });
        Button btn_withdraw=convertView.findViewById(R.id.btn_withdraw);
        btn_withdraw.setEnabled(is);
        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position,"withdraw");
            }
        });
        return convertView;
    }

    public interface onItemDeleteListener{
        void onDeleteClick(int i,String btn);
    }

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener){
        this.mOnItemDeleteListener=mOnItemDeleteListener;
    }
}
