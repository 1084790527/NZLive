package com.example.nzlive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.FriendChildrenListBean;
import com.example.nzlive.bean.FriendGroupListBean;
import com.example.nzlive.fragment.adapter.FriendListAdapter;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.websocket.SocketConnet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.tavendo.autobahn.WebSocketException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    private View view;
    private ExpandableListView lv_friend;
    private FriendListAdapter adapter;

    private static final String TAG = "AAA";
//    private SocketConnet connet;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_friend, container, false);

        init();

        return view;
    }

    private void init() {
        lv_friend=view.findViewById(R.id.lv_friend);
        List<FriendChildrenListBean> childrenListBeans=new ArrayList<>();
        for (int i=0;i<10;i++){
            FriendChildrenListBean childrenListBean=new FriendChildrenListBean();
            childrenListBean.setText("123123");
            childrenListBeans.add(childrenListBean);
        }
        List<FriendGroupListBean> groupListBeans=new ArrayList<>();
        for (int i=0;i<10;i++){
            FriendGroupListBean groupListBean=new FriendGroupListBean();
            groupListBean.setTitle("456789");
            groupListBeans.add(groupListBean);
        }

        adapter=new FriendListAdapter(getActivity(),groupListBeans,childrenListBeans);

        lv_friend.setAdapter(adapter);
    }

}
