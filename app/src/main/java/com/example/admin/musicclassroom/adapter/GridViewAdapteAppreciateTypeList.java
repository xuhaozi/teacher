package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<List<MusicStyleVo>> menus;
    private List<String> kindList;

    private GridViewAdapteAppreciateTypeListItem gridViewAdapteAppreciateTypeListItem;
    public GridViewAdapteAppreciateTypeList(Context context, List<List<MusicStyleVo>> menus,List<String> kindList) {
        ListenerManager.getInstance().registerListtener(this);
        this.context = context;
        if (menus == null) menus = new ArrayList<List<MusicStyleVo>>();
        this.menus = menus;
        this.kindList=kindList;
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public List<MusicStyleVo> getItem(int position) {
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
            vh.expandlist = (ExpandableListView) convertView.findViewById(R.id.expandlist);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final List<MusicStyleVo> item = getItem(position);
        vh.tv_type.setText(kindList.get(position));
        vh.expandlist.setGroupIndicator(null);
        vh.expandlist.setAdapter(new ExpandablelistViewAdapterType(item,context));
        vh.expandlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(item.get(i).getMusicStyleVoList()==null){
                    Toast.makeText(context,item.get(i).getStyleName()+item.get(i).getStyleId(),Toast.LENGTH_SHORT).show();
                    ListenerManager.getInstance().sendBroadCast(item.get(i).getStyleId()+"");
                    return true;
                }
                return false;
            }
        });
        vh.expandlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(context,item.get(i).getMusicStyleVoList().get(i1).getStyleName(),Toast.LENGTH_SHORT).show();
                ListenerManager.getInstance().sendBroadCast(item.get(i).getMusicStyleVoList().get(i1).getStyleName()+"");
                return true;
            }
        });
//        gridViewAdapteAppreciateTypeListItem=new GridViewAdapteAppreciateTypeListItem(context,item.getMusicStyleVoList());
//        vh.lv_data.setAdapter(gridViewAdapteAppreciateTypeListItem);
//        vh.lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ListenerManager.getInstance().sendBroadCast(String.valueOf(item.getMusicStyleVoList().get(i).getParentId()));
//            }
//        });
//        vh.tv_author.setText(item.getWordAuthorName()+" "+item.getAnAuthorName()+" ".toString().trim());


        return convertView;
    }

    @Override
    public void notifyAllActivity(String str) {

    }

    private class ViewHolder {
        private TextView tv_type;

        private ExpandableListView expandlist;
    }


}
