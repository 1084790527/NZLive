package com.example.nzlive.fragment.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.KnowingRecordingListBean;
import com.example.nzlive.util.LogUtil;

import java.util.List;

public class KnowingRecordingListAdapter extends BaseAdapter{

    private List<KnowingRecordingListBean> mList;
    private Context mContext;

    public KnowingRecordingListAdapter(List<KnowingRecordingListBean> mList, Context mContext){
        this.mContext=mContext;
        this.mList=mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.knowing_recording_list_view,null);
        KnowingRecordingListBean bean=mList.get(position);
//        LogUtil.Logd(mContext,bean.toString());
        ((TextView)convertView.findViewById(R.id.tv_num)).setText(position == 0 ? "序号" : position+"");
        ((TextView)convertView.findViewById(R.id.tv_date)).setText(bean.getDate()+"");
        ((TextView)convertView.findViewById(R.id.tv_time)).setText(bean.getTime()+"");
        return convertView;
    }
}
