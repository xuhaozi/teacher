package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.fragment.Fragment_MusicInfo_Item;
import com.example.admin.musicclassroom.radiobroadcast.IListener;
import com.example.admin.musicclassroom.radiobroadcast.ListenerManager;
import com.example.admin.musicclassroom.variable.Variable;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteAppreciateTypeList extends BaseAdapter implements IListener {
    private Context context;
    private List<MusicStyleVo> menus;

    private GridViewAdapteAppreciateTypeListItem gridViewAdapteAppreciateTypeListItem;
    public GridViewAdapteAppreciateTypeList(Context context, List<MusicStyleVo> menus) {
        ListenerManager.getInstance().registerListtener(this);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_appreciatetype_item, null);
            vh.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            vh.lv_data = (ListView) convertView.findViewById(R.id.lv_data);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final MusicStyleVo item = getItem(position);
        vh.tv_type.setText(item.getStyleKind().toString().trim());
        gridViewAdapteAppreciateTypeListItem=new GridViewAdapteAppreciateTypeListItem(context,item.getMusicStyleVoList());
        vh.lv_data.setAdapter(gridViewAdapteAppreciateTypeListItem);
        vh.lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListenerManager.getInstance().sendBroadCast(String.valueOf(item.getMusicStyleVoList().get(i).getParentId()));
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
