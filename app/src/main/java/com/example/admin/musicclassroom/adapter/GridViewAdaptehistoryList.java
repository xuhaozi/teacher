package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdaptehistoryList extends BaseAdapter {
    private Context context;
    private List<CourseVo> menus;

    public GridViewAdaptehistoryList(Context context, List<CourseVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<CourseVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public CourseVo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_history_item, null);
            vh.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            vh.ll_play = (LinearLayout) convertView.findViewById(R.id.ll_play);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final CourseVo item = getItem(position);
        Glide.with(context)
                .load(Variable.accessaddress_img + item.getCourseImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(vh.iv_cover);
        vh.tv_title.setText(item.getCourseName().toString().trim());
        vh.tv_author.setText(item.getWordAuthorName()+" "+item.getAnAuthorName()+" ".toString().trim());


        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
        private TextView tv_author;
        private LinearLayout ll_play;
    }


}
