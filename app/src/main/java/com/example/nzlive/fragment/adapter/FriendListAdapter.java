package com.example.nzlive.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.FriendChildrenListBean;
import com.example.nzlive.bean.FriendGroupListBean;

import java.util.List;

public class FriendListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FriendGroupListBean> groupList;
    private List<FriendChildrenListBean> childrenList;
    public FriendListAdapter(Context context, List<FriendGroupListBean> groupList, List<FriendChildrenListBean> childrenList){
        this.context=context;
        this.groupList=groupList;
        this.childrenList=childrenList;
    }
    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrenList == null ? 0 : childrenList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.friend_group_list_view,null);
        ((TextView)convertView.findViewById(R.id.tv_title)).setText(groupList.get(groupPosition).getTitle()+"");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.friend_children_list_view,null);
        ((TextView)convertView.findViewById(R.id.tv_text)).setText(childrenList.get(childPosition).getText()+"");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
