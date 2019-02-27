package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.MusicStyleVo;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteAppreciateTypeListItem extends BaseAdapter {
    private Context context;
    private List<MusicStyleVo> menus;

    public GridViewAdapteAppreciateTypeListItem(Context context, List<MusicStyleVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<MusicStyleVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public MusicStyleVo getItem(int position) {
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
        final MusicStyleVo item = getItem(position);
        vh.tv_type_item.setText(item.getStyleKind().toString().trim());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type_item;
        private LinearLayout ll_item;
    }


}
