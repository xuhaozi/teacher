package com.example.admin.musicclassroom.fragment;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
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
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.MusicianVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
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


public class Fragment_Musician_details extends mFragment {
    //音乐家详情
    private View views;

    private MusicianVo musicianVo;

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


    @ViewInject(R.id.lv_music_data)
    private ListView lv_music_data;


    private ListViewAdapteMusicList listViewAdapteMusicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_musician_details, null);
        x.view().inject(this, views);
        getmusicalInfo("1");
        return views;
    }

    String str = "吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。";

    private void InitView(final MusicianVo musicianVo) {
        tv_Choice.setText(musicianVo.getMusicianName());
        tv_name.setText(musicianVo.getMusicianName());
//        tv_body.setText(musicalVo.getMusicalIntroduce());
        tv_body.setText(str);
        tv_body.setMovementMethod(ScrollingMovementMethod.getInstance());
        Glide.with(getActivity())
                .load(Variable.accessaddress_img + musicianVo.getMusicianImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(iv_instrumentalmusic_img);
        listViewAdapteMusicList = new ListViewAdapteMusicList(getActivity(), musicianVo.getMusicVoList());
        lv_music_data.setAdapter(listViewAdapteMusicList);
        lv_music_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
                    mediaPlayer.setDataSource(Variable.accessaddress_img+musicianVo.getMusicVoList().get(i).getMusicMp3());
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
     * 获取音乐家详情
     */
    private void getmusicalInfo(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token", Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("musicianId", str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    musicianVo = new Gson().fromJson(obj.getString("data"), new TypeToken<MusicianVo>() {
                    }.getType());
                    if (musicianVo != null) {
                        InitView(musicianVo);
                    } else {
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
        finalHttp.post(Variable.address_musician_info, params, callBack);
    }


    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Appreciate_Instrumental();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }
}
