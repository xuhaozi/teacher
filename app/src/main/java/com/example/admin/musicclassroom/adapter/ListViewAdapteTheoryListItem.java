package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapteTheoryListItem extends BaseAdapter {
    private Context context;
    private List<TheoryVo> menus;
    private int demoFlag;
    public ListViewAdapteTheoryListItem(Context context, List<TheoryVo> menus,int demoFlag) {
        this.context = context;
        if (menus == null) menus = new ArrayList<TheoryVo>();
        this.menus = menus;
        this.demoFlag=demoFlag;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_video_item, null);
            vh.iv_cover = (ImageView) convertView.findViewById(R.id.iv_video_img);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_video_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final TheoryVo item = getItem(position);
        if(demoFlag==1){
            Glide.with(context)
                    .load(item.getTheoryImage())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(vh.iv_cover);
        }else {
            Glide.with(context)
                    .load(Variable.accessaddress_img+item.getTheoryImage())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(vh.iv_cover);
        }

        vh.tv_title.setText(item.getTheoryChapter().toString()+"-"+item.getTheoryName().toString());


        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
    }


}
