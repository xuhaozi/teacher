package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
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
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GridViewAdapteInstrumentalVIdeoList extends BaseAdapter {
    private Context context;
    private List<VideoVo> menus;

    public GridViewAdapteInstrumentalVIdeoList(Context context, List<VideoVo> menus) {
        this.context = context;
        if (menus == null) menus = new ArrayList<VideoVo>();
        this.menus = menus;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public VideoVo getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_instrumental_video_item, null);
            vh.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.ll_bg = (LinearLayout) convertView.findViewById(R.id.ll_bg);
            vh.ll_bg.getBackground().setAlpha(155);//0~255透明度值
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final VideoVo item = getItem(position);


//        MediaMetadataRetriever media = new MediaMetadataRetriever();
//        media.setDataSource(Variable.accessaddress_img + item.getVideo().toString(), new HashMap<String, String>());// videoPath 本地视频的路径
//        Bitmap bitmap  = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC );
//        vh.iv_cover.setImageBitmap(bitmap);
        Glide.with(context)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548232754209&di=e0fe417e44a15c2a8f880a09f146b84c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019c48568b6a9f6ac7251bb655a888.jpg%401280w_1l_2o_100sh.png")
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(vh.iv_cover);
        vh.tv_title.setText(item.getVideoName().toString().trim());


        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
        private LinearLayout ll_bg;
    }
}
