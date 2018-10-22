package com.example.nzlive.fragment.homePage.exchange;

import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.example.nzlive.R;
import com.example.nzlive.fragment.homePage.adapter.ExchangeRecyclerAdapter;
import com.example.nzlive.util.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ExchangeActivity extends AppCompatActivity implements ExchangeRecyclerAdapter.MyItemClickListener, View.OnClickListener {

    private ExchangeRecyclerAdapter Adapter_linearLayout,Adapter_GridLayout,Adapter_FixLayout,Adapter_ScrollFixLayout
            ,Adapter_FloatLayout,Adapter_ColumnLayout,Adapter_SingleLayout,Adapter_onePlusNLayout,
            Adapter_StickyLayout,Adapter_StaggeredGridLayout;
    private ArrayList<HashMap<String, Object>> listItem;
    private RecyclerView rv_exchange;
    private SmartRefreshLayout srl_exchange;
    private FloatingActionButton fab_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exchange);

        init ();

        text();
    }

    private void text() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        // 创建VirtualLayoutManager对象
        // 同时内部会创建一个LayoutHelperFinder对象，用来后续的LayoutHelper查找

        //RecyclerView设置预加载Item的个数
        layoutManager.setInitialPrefetchItemCount(8);
        rv_exchange.setLayoutManager(layoutManager);
        // 将VirtualLayoutManager绑定到recyclerView

        /**
         * 步骤2：设置组件复用回收池
         * */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rv_exchange.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        /**
         * 步骤3:设置需要存放的数据
         * */
        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 20; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);
        }


        /**
         * 步骤4:根据数据列表,创建对应的LayoutHelper
         * */

        // 为了展示效果,此处将上面介绍的所有布局都显示出来

        /**
         设置线性布局
         */
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        // 创建对应的LayoutHelper对象

        // 公共属性
        linearLayoutHelper.setItemCount(4);// 设置布局里Item个数
        linearLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        linearLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        // linearLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        linearLayoutHelper.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比

        // linearLayoutHelper特有属性
        linearLayoutHelper.setDividerHeight(10);
        // 设置间隔高度
        // 设置的间隔会与RecyclerView的addItemDecoration（）添加的间隔叠加.

        linearLayoutHelper.setMarginBottom(100);
        // 设置布局底部与下个布局的间隔

        // 创建自定义的Adapter对象 & 绑定数据 & 绑定对应的LayoutHelper进行布局绘制
        Adapter_linearLayout  = new ExchangeRecyclerAdapter(this, linearLayoutHelper, listItem) {
            // 参数2:绑定绑定对应的LayoutHelper
            // 参数3:传入该布局需要显示的数据个数
            // 参数4:传入需要绑定的数据
            // 通过重写onBindViewHolder()设置更丰富的布局效果

            @Override
            public void onBindViewHolder(ExchangeRecyclerAdapter.MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 为了展示效果,将布局的第一个数据设置为linearLayout
                if (position == 0) {
                    holder.Text.setText("Linear");
                }

                //为了展示效果,将布局里不同位置的Item进行背景颜色设置
                if (position < 2) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };


        Adapter_linearLayout.setOnItemClickListener(this);
        // 设置每个Item的点击事件

        GridLayoutHelper gridLayoutHelper=new GridLayoutHelper(2);
        gridLayoutHelper.setItemCount(20);
        gridLayoutHelper.setPadding(20, 20, 20, 20);
        gridLayoutHelper.setMargin(20, 20, 20, 20);
        gridLayoutHelper.setAspectRatio(2);
        gridLayoutHelper.setMarginBottom(100);
        Adapter_GridLayout =new ExchangeRecyclerAdapter(this,gridLayoutHelper,listItem){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

//                if (position == 0) {
//                    holder.Text.setText("Linear");
//                }
//
//                //为了展示效果,将布局里不同位置的Item进行背景颜色设置
//                if (position < 2) {
//                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
//                } else if (position % 2 == 0) {
//                    holder.itemView.setBackgroundColor(0xaa22ff22);
//                } else {
//                    holder.itemView.setBackgroundColor(0xccff22ff);
//                }
                holder.itemView.setBackgroundColor(0xFFFFFFFF);
            }
        };

        Adapter_GridLayout.setOnItemClickListener(this);



        /**
         *步骤5:将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
         **/

        // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        // 2. 将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
//        adapters.add(Adapter_linearLayout) ;
//        adapters.add(Adapter_StickyLayout) ;
//        adapters.add(Adapter_ScrollFixLayout) ;
        adapters.add(Adapter_GridLayout) ;
//        adapters.add(Adapter_FixLayout) ;
//        adapters.add(Adapter_FloatLayout) ;
//        adapters.add(Adapter_ColumnLayout) ;
//        adapters.add(Adapter_SingleLayout) ;
//        adapters.add(Adapter_onePlusNLayout) ;
//        adapters.add(Adapter_StaggeredGridLayout) ;

        // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);

        // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters);

        // 5. 将delegateAdapter绑定到recyclerView
        rv_exchange.setAdapter(delegateAdapter);

        /**
         *步骤6:Item之间的间隔
         **/

        rv_exchange.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });

    }

    private void init() {
        fab_test=findViewById(R.id.fab_test);
        fab_test.setOnClickListener(this);
        rv_exchange=findViewById(R.id.rv_exchange);
        srl_exchange=findViewById(R.id.srl_exchange);
        srl_exchange.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //上拉加载
                LogUtil.Logd(getApplicationContext(),"llllll");
                int x=listItem.size();
                for (int i = x; i < x+20; i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemTitle", "第" + i + "行");
                    map.put("ItemImage", R.mipmap.ic_launcher);
                    listItem.add(map);
                }
                srl_exchange.finishLoadmore();
                Adapter_GridLayout.notifyDataSetChanged();

            }
        });
        srl_exchange.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //下拉刷新
                LogUtil.Logd(getApplicationContext(),"rrrrrrrr");
                srl_exchange.finishRefresh();
            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {
        LogUtil.Logd(getApplicationContext(), ""+listItem.get(position).get("ItemTitle"));
//        Toast.makeText(this, (String) listItem.get(position).get("ItemTitle"), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_test:
//                LogUtil.Logd(getApplicationContext(),"dj" );
                startActivity(new Intent(getApplicationContext(),releaseActivity.class));
                break;
        }
    }
}
