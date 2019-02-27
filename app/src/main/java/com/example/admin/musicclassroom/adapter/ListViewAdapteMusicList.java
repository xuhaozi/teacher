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
import com.example.admin.musicclassroom.entity.MusicVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapteMusicList extends BaseAdapter {
    private Context context;
    private List<MusicVo> menus;

    public ListViewAdapteMusicList(Context context, List<MusicVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<MusicVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public MusicVo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_music_list_item, null);
            vh.iv_play = (ImageView) convertView.findViewById(R.id.iv_play);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.ll_play = (LinearLayout) convertView.findViewById(R.id.ll_play);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final MusicVo item = getItem(position);
        vh.tv_title.setText(item.getMusicName().toString().trim());

        //隔行变色
        if (position % 2 == 0){
            convertView.setBackgroundResource(R.color.transparent);
        }else{
            convertView.setBackgroundResource(R.color.Violet);
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_play;
        private LinearLayout ll_play;
        private TextView tv_title;
        private TextView tv_time;
    }


}
