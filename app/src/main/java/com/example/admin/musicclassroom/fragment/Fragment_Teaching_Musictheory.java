package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.ListViewAdapteTheoryListItem;
import com.example.admin.musicclassroom.adapter.ListViewAdapteVideoListItem;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_Teaching_Musictheory extends mFragment {
    //乐理
    private View views;

    private List<TheoryVo> theoryVoList;
    @ViewInject(R.id.right_theory_list)
    private ListView right_theory_list;

    @ViewInject(R.id.iv_imgitem)
    private ImageView iv_imgitem;
    private ListViewAdapteTheoryListItem listViewAdapteTheoryListItem;
    private int demoFlag;
    private Long courseId;
    private int position;
    public Fragment_Teaching_Musictheory(Long courseId, int position) {
        super();
        this.courseId=courseId;
        this.position=position;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_teaching_musictheory;
    }

    @Override
    protected void init() {
        views=rootView;
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
            //本地数据
            theoryVoList=new ArrayList<>();
            List<VideoVo> videoVoList=new ArrayList<>();
            if(position==0){
                theoryVoList.clear();
                videoVoList.clear();
                videoVoList.add(new VideoVo("四二拍","/test/小雨沙沙沙/42拍.mp4"));
                theoryVoList.add(new TheoryVo("第一章 音的高低","四二拍","file:///android_asset/小雨沙沙沙/四二拍.png",videoVoList));
            }else if(position==1){
                theoryVoList.clear();
                videoVoList.clear();
                videoVoList.add(new VideoVo("四三拍","/test/布谷/43拍.mp4"));
                theoryVoList.add(new TheoryVo("第一章 音的高低","四三拍","file:///android_asset/布谷/四三拍.png",videoVoList));
            }else if(position==2){
                theoryVoList.clear();
                videoVoList.clear();
                videoVoList.add(new VideoVo("四三拍","/test/祝你圣诞快乐/43拍.mp4"));
                theoryVoList.add(new TheoryVo("第一章 音的高低","四三拍","file:///android_asset/祝你圣诞快乐/四三拍.png",videoVoList));
            }
            getDemoList();
        }else {
            GetMusictheoryList();
        }
    }

    /**
     * 获取乐理列表
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
                    theoryVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<TheoryVo>>() {
                    }.getType());
                    if (theoryVoList != null) {
                        listViewAdapteTheoryListItem=new ListViewAdapteTheoryListItem(getActivity(),theoryVoList,demoFlag);
                        right_theory_list.setAdapter(listViewAdapteTheoryListItem);
                        Glide.with(getActivity())
                                .load(Variable.accessaddress_img+theoryVoList.get(0).getTheoryImage())
                                .placeholder(R.mipmap.icon_default_bg)
                                .crossFade()
                                .into(iv_imgitem);

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
        finalHttp.post(Variable.address_theory, params, callBack);
    }

    /**
     * 获取本地乐理列表
     */
    public void getDemoList() {
        listViewAdapteTheoryListItem=new ListViewAdapteTheoryListItem(getActivity(),theoryVoList,demoFlag);
        right_theory_list.setAdapter(listViewAdapteTheoryListItem);
        right_theory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(getActivity())
                        .load(theoryVoList.get(position).getTheoryImage())
                        .placeholder(R.mipmap.icon_default_bg)
                        .crossFade()
                        .into(iv_imgitem);
            }
        });
        Glide.with(getActivity())
                .load(theoryVoList.get(0).getTheoryImage())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(iv_imgitem);
    }
}
