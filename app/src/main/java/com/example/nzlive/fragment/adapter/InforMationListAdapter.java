package com.example.nzlive.fragment.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.InforMationListBean;

import java.util.List;

public class InforMationListAdapter extends BaseAdapter {

    private Context context;
    private List<InforMationListBean> list;

    public InforMationListAdapter(Context context,List<InforMationListBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.infor_mation_list_view,null);
        ((ImageView)convertView.findViewById(R.id.iv_item)).setImageResource(list.get(position).getLogo());
        ((TextView)convertView.findViewById(R.id.tv_title)).setText(list.get(position).getTitle()+"");
        ((TextView)convertView.findViewById(R.id.tv_text)).setText(list.get(position).getText()+"");
        return convertView;
    }
}
