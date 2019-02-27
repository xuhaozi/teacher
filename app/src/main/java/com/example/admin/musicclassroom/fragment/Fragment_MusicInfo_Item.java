package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdaptehistoryList;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_MusicInfo_Item extends mFragment {
    //乐谱核心界面
    private View views;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.btn_music)
    private Button btn_music;
    @ViewInject(R.id.btn_video)
    private Button btn_video;
    @ViewInject(R.id.btn_musictheory)
    private Button btn_musictheory;
    @ViewInject(R.id.btn_instrumentalmusic)
    private Button btn_instrumentalmusic;
    @ViewInject(R.id.btn_exercises)
    private Button btn_exercises;

    @ViewInject(R.id.fl_body_teaching)
    private FrameLayout fl_body_teaching;

    private Button[] arr_img;// 图标的数组，用于文字高亮
    private Fragment mContent, fragment_Teaching_Music, fragment_Teaching_Video, fragment_Teaching_Musictheory, fragment_Teaching_MusicalInstruments, fragment_Teaching_Exercises;//首页模块中的碎片
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_musicinfo_item, null);
        x.view().inject(this, views);
        GetdetailsInfo();
        InitViews();
        changeCurrBtn(0);
        return views;
    }

    /**
     * 获取详情数据
     */
    private void GetdetailsInfo() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("courseId","1");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    Variable.course = new Gson().fromJson(obj.getString("data"), new TypeToken<CourseVo>() {
                    }.getType());
                    if (Variable.course != null) {
                        tv_title.setText(Variable.course.getCourseName());
                    }else {
                        showMessage("数据获取异常");
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
        finalHttp.post(Variable.address_detail, params, callBack);
    }


    //初始化fragment
    private void InitViews() {
        arr_img = new Button[5];
        arr_img[0] = btn_music;
        arr_img[1] = btn_video;
        arr_img[2] = btn_musictheory;
        arr_img[3] = btn_instrumentalmusic;
        arr_img[4] = btn_exercises;

        fragment_Teaching_Music = new Fragment_Teaching_Music();
        fragment_Teaching_Video = new Fragment_Teaching_Video();
        fragment_Teaching_Musictheory = new Fragment_Teaching_Musictheory();
        fragment_Teaching_MusicalInstruments = new Fragment_Teaching_MusicalInstruments();
        fragment_Teaching_Exercises = new Fragment_Teaching_Exercises();
        setDefaultFragment(fragment_Teaching_Music);
    }

    //切换fragment的显示隐藏
    public void switchContent(Fragment to) {
        if (mContent != to) {
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(mContent).add(R.id.fl_body_teaching, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }
    public void setDefaultFragment(Fragment fragment) {
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fl_body_teaching, fragment).commit();
        mContent = fragment;
    }

    // 高亮按钮
    private void changeCurrBtn(int index) {
        // 5个文字都变色
        for (int i = 0; i < arr_img.length; i++) {
            UpdataImgState(index);
        }
        // 哪个高亮
        UpdataImgState(index);
    }


    public void UpdataImgState(int index) {
        if (index == 0) {
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[2].setBackgroundResource(R.color.background);
            arr_img[3].setBackgroundResource(R.color.background);
            arr_img[4].setBackgroundResource(R.color.background);

            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[3].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[4].setTextColor(getResources().getColor(R.color.teaching_item_title));


        } else if (index == 1) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[2].setBackgroundResource(R.color.background);
            arr_img[3].setBackgroundResource(R.color.background);
            arr_img[4].setBackgroundResource(R.color.background);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[3].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[4].setTextColor(getResources().getColor(R.color.teaching_item_title));
        } else if (index == 2) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[3].setBackgroundResource(R.color.background);
            arr_img[4].setBackgroundResource(R.color.background);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[3].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[4].setTextColor(getResources().getColor(R.color.teaching_item_title));

        } else if (index == 3) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[2].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[4].setBackgroundResource(R.color.background);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[4].setTextColor(getResources().getColor(R.color.teaching_item_title));
        } else if (index == 4) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[2].setBackgroundResource(R.color.background);
            arr_img[3].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[3].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
        }

    }


    //乐谱
    @Event(value = R.id.btn_music, type = View.OnClickListener.class)
    private void ll_teaching_gerenClick(View v) {
        changeCurrBtn(0);
        switchContent(fragment_Teaching_Music);
    }

    //视频
    @Event(value = R.id.btn_video, type = View.OnClickListener.class)
    private void ll_piano_gerenClick(View v) {
        changeCurrBtn(1);
        switchContent(fragment_Teaching_Video);
    }
    //乐理
    @Event(value = R.id.btn_musictheory, type = View.OnClickListener.class)
    private void ll_musictheory_gerenClick(View v) {
        changeCurrBtn(2);
        switchContent(fragment_Teaching_Musictheory);
    }
    //器乐
    @Event(value = R.id.btn_instrumentalmusic, type = View.OnClickListener.class)
    private void ll_appreciate_gerenClick(View v) {
        changeCurrBtn(3);
        switchContent(fragment_Teaching_MusicalInstruments);
    }
    //习题
    @Event(value = R.id.btn_exercises, type = View.OnClickListener.class)
    private void ll_drawingboard_gerenClick(View v) {
        changeCurrBtn(4);
        switchContent(fragment_Teaching_Exercises);
    }


}
