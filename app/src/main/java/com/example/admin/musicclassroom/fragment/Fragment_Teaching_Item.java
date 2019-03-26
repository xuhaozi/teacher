package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.Utils.MaskPopupWindow;
import com.example.admin.musicclassroom.adapter.GridViewAdaptehistoryList;
import com.example.admin.musicclassroom.entity.CourseVo;
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

import java.util.List;


public class Fragment_Teaching_Item extends mFragment {
    //教学
    private View views;
    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    @ViewInject(R.id.iv_guide)
    private ImageView iv_guide;
    private List<CourseVo> courseVoList;
    private GridViewAdaptehistoryList gridViewAdaptehistoryList;
    private int demoFlag;

    @Override
    protected int setContentView() {
        return R.layout.fragment_teaching_item;

    }

    @Override
    protected void init() {
        x.view().inject(this, rootView);
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }


    private void initDemo() {
        //获取演示标识
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){//获取本地数据
            getDemo();
        }else {//获取网络数据
            gethistory();
        }
    }



    String json="{\n" +
            "\t\"code\": 200,\n" +
            "\t\"msg\": \"success\",\n" +
            "\t\"data\": [{\n" +
            "\t\t\"courseId\": 1,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"小雨沙沙沙\",\n" +
            "\t\t\"courseImage\": \"file:///android_asset/小雨沙沙沙/小雨缩略图.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"金月苓\",\n" +
            "\t\t\"anAuthorName\": \"\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/故乡的小路.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"布谷\",\n" +
            "\t\t\"courseImage\": \"file:///android_asset/布谷/布谷缩略图.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"德国儿童歌曲\",\n" +
            "\t\t\"anAuthorName\": \"\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"祝你圣诞快乐\",\n" +
            "\t\t\"courseImage\": \"file:///android_asset/祝你圣诞快乐/祝你圣诞快乐缩略图.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"薛范\",\n" +
            "\t\t\"anAuthorName\": \"\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}],\n" +
            "\t\"timestamp\": 1547014232\n" +
            "}";

    /**
     * 本地数据
     */
    private void getDemo() {
        try {
            JSONObject obj = new JSONObject(json);
            courseVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CourseVo>>() {
            }.getType());
            if (courseVoList != null) {
                gridViewAdaptehistoryList=new GridViewAdaptehistoryList(getActivity(),courseVoList,demoFlag);
                gv_datas.setAdapter(gridViewAdaptehistoryList);
                gv_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Fragment videoFragment = new Fragment_MusicInfo_Item(courseVoList.get(i).getCourseId(),i);
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        transaction.add(R.id.video_fragment, videoFragment).commit();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 历史数据
     */
    private void gethistory() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(json);
                    courseVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CourseVo>>() {
                    }.getType());
                    if (courseVoList != null) {
                        gridViewAdaptehistoryList=new GridViewAdaptehistoryList(getActivity(),courseVoList,demoFlag);
                        gv_datas.setAdapter(gridViewAdaptehistoryList);
                        gv_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Fragment videoFragment = new Fragment_MusicInfo_Item(courseVoList.get(i).getCourseId());
                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                transaction.add(R.id.video_fragment, videoFragment).commit();
                            }
                        });
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
        finalHttp.post(Variable.address_history, params, callBack);
    }

    private MaskPopupWindow maskPopupWindow;
    private Handler popupHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    maskPopupWindow=new MaskPopupWindow(getActivity());
                    maskPopupWindow.showAtLocation(getActivity().findViewById(R.id.iv_guide), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
            }
        }

    };

    //课程演示
    @Event(value = R.id.iv_guide, type = View.OnClickListener.class)
    private void iv_guideClick(View v) {
        popupHandler.sendEmptyMessageDelayed(0, 1000);
    }




}
