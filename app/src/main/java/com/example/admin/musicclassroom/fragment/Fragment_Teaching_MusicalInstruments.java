package com.example.admin.musicclassroom.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteInstrumentalVIdeoList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteTheoryListItem;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.widget.HvListView;
import com.example.admin.musicclassroom.widget.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;


public class Fragment_Teaching_MusicalInstruments extends mFragment {
    //器乐
    private View views;

    private List<MusicalVo> musicalVo;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_body)
    private TextView tv_body;
    @ViewInject(R.id.iv_instrumentalmusic_img)
    private ImageView iv_instrumentalmusic_img;


    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;
    @ViewInject(R.id.ll_return)
    private LinearLayout ll_return;


    @ViewInject(R.id.gv_video_list)
    private MyGridView gv_video_list;


    @ViewInject(R.id.lv_music_data)
    private ListView lv_music_data;



    private ListViewAdapteMusicList listViewAdapteMusicList;
    private GridViewAdapteInstrumentalVIdeoList gridViewAdapteInstrumentalVIdeoList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_teaching_musicallinstruments, null);
        x.view().inject(this, views);
        GetMusictheoryList();
        return views;
    }

    String str="吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。";
    private void InitView(final MusicalVo musicalVo){
        tv_Choice.setText(musicalVo.getMusicalName());
        tv_name.setText(musicalVo.getMusicalName());
//        tv_body.setText(musicalVo.getMusicalIntroduce());
        tv_body.setText(str);
        tv_body.setMovementMethod(ScrollingMovementMethod.getInstance());
        Glide.with(getActivity())
                .load(Variable.accessaddress_img + musicalVo.getMusicalImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(iv_instrumentalmusic_img);
        gridViewAdapteInstrumentalVIdeoList=new GridViewAdapteInstrumentalVIdeoList(getActivity(),musicalVo.getVideoVoList());
        gv_video_list.setAdapter(gridViewAdapteInstrumentalVIdeoList);
        gv_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(Variable.accessaddress_img+musicalVo.getVideoVoList().get(i).getVideo());
            }
        });
        listViewAdapteMusicList=new ListViewAdapteMusicList(getActivity(),musicalVo.getMusicVoList());
        lv_music_data.setAdapter(listViewAdapteMusicList);
        lv_music_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
                    mediaPlayer.setDataSource(Variable.accessaddress_img+musicalVo.getMusicVoList().get(i).getMusicMp3());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                            return false;
                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // todo
                        }
                    });

                    mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                            // i 0~100
                            Log.d("Progress:", "缓存进度" + i + "%");
                        }
                    });
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            // todo
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取乐器列表
     */
    private void GetMusictheoryList() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("courseId","2");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    musicalVo = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MusicalVo>>() {
                    }.getType());
                    if (musicalVo != null) {
                        InitView(musicalVo.get(0));
                    }else {
                        showMessage("数据加载失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        };
        finalHttp.post(Variable.address_musical, params, callBack);
    }

    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Teaching_Item();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }

}
