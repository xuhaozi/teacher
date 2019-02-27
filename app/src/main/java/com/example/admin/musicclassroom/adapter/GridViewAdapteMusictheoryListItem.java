package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.TheoryVo;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteMusictheoryListItem extends BaseAdapter {
    private Context context;
    private List<TheoryVo> menus;

    public GridViewAdapteMusictheoryListItem(Context context, List<TheoryVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<TheoryVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public TheoryVo getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_appreciatetype_item_item, null);
            vh.tv_type_item = (TextView) convertView.findViewById(R.id.tv_type_item);
            vh.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final TheoryVo item = getItem(position);
        vh.tv_type_item.setText(item.getTheoryName().toString().trim());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type_item;
        private LinearLayout ll_item;
    }


}
