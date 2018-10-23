package com.example.nzlive.fragment.homePage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.example.nzlive.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExchangeRecyclerAdapter extends DelegateAdapter.Adapter<ExchangeRecyclerAdapter.MainViewHolder> {
    // 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法.

    private ArrayList<HashMap<String, Object>> listItem;
    // 用于存放数据列表

    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;

    private MyItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public ExchangeRecyclerAdapter(Context context, LayoutHelper layoutHelper, ArrayList<HashMap<String, Object>> listItem) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT), listItem);
    }

    public ExchangeRecyclerAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams, ArrayList<HashMap<String, Object>> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.listItem = listItem;
    }

    // 把ViewHolder绑定Item的布局
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.exchange_list_view, parent, false));
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        holder.image.setImageResource((Integer) listItem.get(position).get("ItemImage"));
        holder.Text.setText((String) listItem.get(position).get("ItemTitle"));
    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    // 设置Item的点击事件
    // 绑定MainActivity传进来的点击监听器
    public void setOnItemClickListener(MyItemClickListener listener) {
        myItemClickListener = listener;
    }


    //定义Viewholder
    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView Text;
        public ImageView image;

        public MainViewHolder(View root) {
            super(root);

            // 绑定视图
            image = (ImageView) root.findViewById(R.id.iv_item);
            Text = (TextView) root.findViewById(R.id.tv_item);


            root.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (myItemClickListener != null)
                                                myItemClickListener.onItemClick(v, getPosition());
                                        }
                                    }
                    //监听到点击就回调MainActivity的onItemClick函数
            );

        }

        public TextView getText() {
            return Text;
        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View v, int position);
    }
}
