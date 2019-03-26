package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.TheoryVo;

import java.util.List;


public class ExpandablelistViewAdapter extends BaseExpandableListAdapter {
    private List<TheoryVo> theoryVoList;
    private Context context;

    public ExpandablelistViewAdapter(List<TheoryVo> theoryVoList, Context context) {
        this.theoryVoList = theoryVoList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        Log.i("getGroupCount",theoryVoList.size()+"");
        return theoryVoList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        Log.i("getChildrenCount",theoryVoList.get(i).getTheoryVoList().size()+"");
        return theoryVoList.get(i).getTheoryVoList().size();
    }

    @Override
    public Object getGroup(int i) {
        return theoryVoList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return theoryVoList.get(i).getTheoryVoList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder vh = null;
        if (view == null) {
            vh = new GroupViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.activity_appreciatetype_item_item, null);
            vh.tv_type_item = (TextView) view.findViewById(R.id.tv_type_item);
            vh.ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            vh.iv_spread=view.findViewById(R.id.iv_spread);
            view.setTag(vh);
        } else {
            vh = (GroupViewHolder) view.getTag();
        }
        vh.tv_type_item.setText(theoryVoList.get(i).getTheoryName());
        if(theoryVoList.get(i).getTheoryVoList()!=null){
            vh.iv_spread.setVisibility(View.VISIBLE);
        }
        Log.i("data",theoryVoList.get(i).getTheoryName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder vh = null;
        if (view == null) {
            vh = new ChildViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.expand_item, null);
            vh.tv_expand_item = (TextView) view.findViewById(R.id.tv_expand_item);
            view.setTag(vh);
        } else {
            vh = (ChildViewHolder) view.getTag();
        }
        vh.tv_expand_item.setText(theoryVoList.get(i).getTheoryVoList().get(i1).getTheoryName());
        Log.i("data1",theoryVoList.get(i).getTheoryVoList().get(i1).getTheoryName());
        return view;
    }
    private class GroupViewHolder {
        private TextView tv_type_item;
        private LinearLayout ll_item;
        private ImageView iv_spread;
    }
    private class ChildViewHolder {
        private TextView tv_expand_item;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
