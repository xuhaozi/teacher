package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.radiobroadcast.IListener;
import com.example.admin.musicclassroom.radiobroadcast.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteMusictheoryList extends BaseAdapter implements IListener {
    private Context context;
    private List<TheoryVo> menus;

    private GridViewAdapteMusictheoryListItem gridViewAdapteMusictheoryListItem;
    public GridViewAdapteMusictheoryList(Context context, List<TheoryVo> menus) {
        ListenerManager.getInstance().registerListtener(this);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_appreciatetype_item, null);
            vh.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            vh.lv_data = (ListView) convertView.findViewById(R.id.lv_data);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final TheoryVo item = getItem(position);
        vh.tv_type.setText(item.getTheoryChapter().toString().trim());
        gridViewAdapteMusictheoryListItem=new GridViewAdapteMusictheoryListItem(context,item.getTheoryVoList());
        vh.lv_data.setAdapter(gridViewAdapteMusictheoryListItem);
        vh.lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListenerManager.getInstance().sendBroadCast("1");
            }
        });
//        vh.tv_author.setText(item.getWordAuthorName()+" "+item.getAnAuthorName()+" ".toString().trim());


        return convertView;
    }

    @Override
    public void notifyAllActivity(String str) {

    }

    private class ViewHolder {
        private TextView tv_type;

        private ListView lv_data;
    }


}
