package com.example.admin.musicclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.radiobroadcast.IListener;
import com.example.admin.musicclassroom.radiobroadcast.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapteMusictheoryList extends BaseAdapter implements IListener {
    private Context context;
    private List<List<TheoryVo>> theoryVoListTemp;
    private List<String> chapterListTemp;

    private GridViewAdapteMusictheoryListItem gridViewAdapteMusictheoryListItem;
    public GridViewAdapteMusictheoryList(Context context, List<List<TheoryVo>> theoryVoListTemp,List<String> chapterListTemp) {
        ListenerManager.getInstance().registerListtener(this);
        this.context = context;
        this.theoryVoListTemp=theoryVoListTemp;
        this.chapterListTemp=chapterListTemp;
    }
    @Override
    public int getCount() {
        return theoryVoListTemp.size();
    }

    @Override
    public List<TheoryVo> getItem(int position) {
        return theoryVoListTemp.get(position);
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
        final List<TheoryVo> item = getItem(position);
        vh.tv_type.setText(chapterListTemp.get(position));
        vh.expandlist.setGroupIndicator(null);
        vh.expandlist.setAdapter(new ExpandablelistViewAdapter(item,context));
        vh.expandlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(item.get(i).getTheoryVoList()==null){
                    Toast.makeText(context,item.get(i).getTheoryName()+item.get(i).getTheoryId(),Toast.LENGTH_SHORT).show();
                    ListenerManager.getInstance().sendBroadCast(item.get(i).getTheoryId()+"");
                    return true;
                }
                return false;
            }
        });
        vh.expandlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(context,item.get(i).getTheoryVoList().get(i1).getTheoryName(),Toast.LENGTH_SHORT).show();
                ListenerManager.getInstance().sendBroadCast(item.get(i).getTheoryVoList().get(i1).getTheoryId()+"");
                return true;
            }
        });
//        gridViewAdapteMusictheoryListItem=new GridViewAdapteMusictheoryListItem(context,item);
//        vh.expandlist.setAdapter(gridViewAdapteMusictheoryListItem);
//        vh.expandlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ListenerManager.getInstance().sendBroadCast(item.get(i).getTheoryId()+"");
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
