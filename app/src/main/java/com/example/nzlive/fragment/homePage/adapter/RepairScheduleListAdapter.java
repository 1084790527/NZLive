package com.example.nzlive.fragment.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.RepairScheduleListBean;

import java.util.List;

public class RepairScheduleListAdapter extends BaseAdapter {

    private List<RepairScheduleListBean> list;
    private Context context;
    public RepairScheduleListAdapter(Context context, List<RepairScheduleListBean> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.repair_schedule_list_view,null);
        ((TextView)convertView.findViewById(R.id.tv_id)).setText(position == 0 ? "序号" : position+"");
        ((TextView)convertView.findViewById(R.id.tv_date)).setText(list.get(position).getDate()+"");
        ((TextView)convertView.findViewById(R.id.tv_schedule)).setText(position == 0 ? "状态" : judgeSchedule(list.get(position).getSchedule()+""));
        return convertView;
    }

    private String judgeSchedule(String schedule){
        switch (schedule){
            case "0":
                return "等待通过审核";
            case "1":
                return "辅导员审批通过";
            case "2":
                return "教务处审批通过";
            case "3":
                return "已报修";
            default:
                return "状态错误";
        }
    }
}
