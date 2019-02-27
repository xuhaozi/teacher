package com.example.admin.musicclassroom.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdaptehistoryList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteVideoListItem;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.entity.VideoVo;
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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class Fragment_Teaching_Video extends mFragment {
    //视频
    private View views;

    @ViewInject(R.id.right_video_list)
    private ListView right_video_list;


    @ViewInject(R.id.videoView)
    private VideoView videoView;

    private List<VideoVo> videoVoList;
    private ListViewAdapteVideoListItem listViewAdapteVideoListItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_teaching_video, null);
        x.view().inject(this, views);
        GetVideoList();
        return views;
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
        AjaxParams params = new AjaxParams();
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
                                Fragment videoFragment = new Fragment_MusicInfo_Item();
                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                transaction.add(R.id.video_fragment, videoFragment).commit();
                            }
                        });
                        Uri uri = Uri.parse(Variable.accessaddress_img+videoVoList.get(0).getVideo());
                        //设置视频控制器
                        videoView.setMediaController(new MediaController(getActivity()));
                        //播放完成回调
                        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
                        //设置视频路径
                        videoView.setVideoURI(uri);
                        //开始播放视频
                        videoView.start();

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

}
