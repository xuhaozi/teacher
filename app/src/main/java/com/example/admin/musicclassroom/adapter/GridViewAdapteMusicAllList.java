package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteMusicAllList extends BaseAdapter {
    private Context context;
    private List<MusicalVo> menus;

    public GridViewAdapteMusicAllList(Context context, List<MusicalVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<MusicalVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public MusicalVo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_music_item, null);
            vh.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.ll_play = (LinearLayout) convertView.findViewById(R.id.ll_play);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final MusicalVo item = getItem(position);
        Glide.with(context)
                .load(Variable.accessaddress_img + item.getMusicalImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(vh.iv_cover);
        vh.tv_title.setText(item.getMusicalName().toString().trim());


        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
        private LinearLayout ll_play;
    }


}
