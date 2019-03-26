package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.TheoryVo;

import java.util.List;


public class ExpandablelistViewAdapterType extends BaseExpandableListAdapter {
    private List<MusicStyleVo> musicStyleVos;
    private Context context;

    public ExpandablelistViewAdapterType(List<MusicStyleVo> musicStyleVos, Context context) {
        this.musicStyleVos = musicStyleVos;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        Log.i("getGroupCount",musicStyleVos.size()+"");
        return musicStyleVos.size();
    }

    @Override
    public int getChildrenCount(int i) {
        Log.i("getChildrenCount",musicStyleVos.get(i).getMusicStyleVoList().size()+"");
        return musicStyleVos.get(i).getMusicStyleVoList().size();
    }

    @Override
    public Object getGroup(int i) {
        return musicStyleVos.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return musicStyleVos.get(i).getMusicStyleVoList().get(i1);
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
        vh.tv_type_item.setText(musicStyleVos.get(i).getStyleName());
        if(musicStyleVos.get(i).getMusicStyleVoList()!=null){
            vh.iv_spread.setVisibility(View.VISIBLE);
        }
        Log.i("data",musicStyleVos.get(i).getStyleName());
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
        vh.tv_expand_item.setText(musicStyleVos.get(i).getMusicStyleVoList().get(i1).getStyleName());
        Log.i("data1",musicStyleVos.get(i).getMusicStyleVoList().get(i1).getStyleName());
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
