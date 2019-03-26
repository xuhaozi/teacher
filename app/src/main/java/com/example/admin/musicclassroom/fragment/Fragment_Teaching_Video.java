package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.Utils.RawDataSourceProvider;
import com.example.admin.musicclassroom.adapter.GridViewAdaptehistoryList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteVideoListItem;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.widget.MessageEvent;
import com.example.admin.musicclassroom.widget.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressLint("ValidFragment")
public class Fragment_Teaching_Video extends mFragment {
    //视频
    private View views;

    @ViewInject(R.id.right_video_list)
    private ListView right_video_list;


    @ViewInject(R.id.videoView)
    private VideoView videoView ;

    private List<VideoVo> videoVoList;
    private ListViewAdapteVideoListItem listViewAdapteVideoListItem;

    private int demoFlag;
    private String path;
    private Long courseId;
    private int position;//本地数据所用下标
    private MediaController mediaController;
    public Fragment_Teaching_Video(Long courseId, int position) {
        super();
        this.courseId=courseId;
        this.position=position;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_teaching_video;
    }

    @Override
    protected void init() {
        views=rootView;
        EventBus.getDefault().register(this);
        x.view().inject(this, views);
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }


    private void initDemo() {
        //获取演示标识
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        Log.i("demoFlag",demoFlag+"");
        if(demoFlag==1){
            videoVoList=new ArrayList<>();
            if(position==0){
                videoVoList.clear();
                videoVoList.add(new VideoVo("小雨沙沙沙","/test/小雨沙沙沙/小雨沙沙沙.mp4"));
            }else if (position==1){
                videoVoList.clear();
                videoVoList.add(new VideoVo("布谷","/test/布谷/布谷.mp4"));
            }else if(position==2){
                videoVoList.clear();
                videoVoList.add(new VideoVo("祝你圣诞快乐1","/test/祝你圣诞快乐/祝你圣诞快乐1.mp4"));
                videoVoList.add(new VideoVo("祝你圣诞快乐2","/test/祝你圣诞快乐/祝你圣诞快乐2.mp4"));
            }



            playVideo();
        }else {
            GetVideoList();
        }
    }

    public static class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }

    /**
     * 获取视频列表
     */
    private void GetVideoList() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        final AjaxParams params = new AjaxParams();
        params.put("courseId","2");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    videoVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<VideoVo>>() {
                    }.getType());
                    if (videoVoList != null) {
                        listViewAdapteVideoListItem=new ListViewAdapteVideoListItem(getActivity(),videoVoList);
                        right_video_list.setAdapter(listViewAdapteVideoListItem);
                        right_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //上个安卓写的，感觉不对暂时注释掉
//                                Fragment videoFragment = new Fragment_MusicInfo_Item();
//                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                                transaction.add(R.id.video_fragment, videoFragment).commit();

                                Uri uri = Uri.parse(Variable.accessaddress_img+videoVoList.get(i).getVideo());
                                //设置视频路径
                                videoView.setVideoURI(uri);
                                //开始播放视频
                                if (videoView.isPlaying()){
                                    videoView.stopPlayback();
                                    videoView.start();
                                }else {
                                    videoView.start();
                                }
                            }
                        });
                        Uri uri = Uri.parse(Variable.accessaddress_img+videoVoList.get(0).getVideo());
                        //设置视频控制器
                        mediaController=new MediaController(getActivity());
                        videoView.setMediaController(mediaController);
                        //播放完成回调
                        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
                        //设置视频路径
                        videoView.setVideoURI(uri);
                        //开始播放视频
//                        videoView.start();
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
        finalHttp.post(Variable.address_video, params, callBack);
    }
    //播放本地视频
    public void playVideo(){
        listViewAdapteVideoListItem=new ListViewAdapteVideoListItem(getActivity(),videoVoList);
        right_video_list.setAdapter(listViewAdapteVideoListItem);
        right_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //上个安卓写的，感觉不对暂时注释掉
//                                Fragment videoFragment = new Fragment_MusicInfo_Item();
//                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                                transaction.add(R.id.video_fragment, videoFragment).commit();

                String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+videoVoList.get(i).getVideo() ;
                //设置视频路径
                videoView.setVideoPath(videoUrl1);
                //开始播放视频
                if (videoView.isPlaying()){
                    videoView.stopPlayback();
                    videoView.start();
                }else {
                    videoView.start();
                }
            }
        });
        String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+videoVoList.get(0).getVideo() ;
        mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        videoView.setVideoPath(videoUrl1);
        //开始播放视频
        videoView.start();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(videoView!=null&&videoView.isPlaying()){
            videoView.pause();
            mediaController.hide();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
