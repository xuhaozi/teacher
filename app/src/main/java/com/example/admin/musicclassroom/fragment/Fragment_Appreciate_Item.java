package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.mFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class Fragment_Appreciate_Item extends mFragment {
    //鉴赏
    private View views;

    @ViewInject(R.id.btn_music)
    private Button btn_music;

    @ViewInject(R.id.btn_video)
    private Button btn_video;

    @ViewInject(R.id.btn_musictheory)
    private Button btn_musictheory;

    @ViewInject(R.id.fl_body_teaching)
    private FrameLayout fl_body_teaching;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private Fragment mContent, fragment_Appreciate_Instrumental, fragment_Appreciate_Type, fragment_Appreciate_Musician;

    private Button[] arr_img;// 图标的数组，用于文字高亮
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_appreciate_item, null);
        x.view().inject(this, views);
        InitViews();
        return views;
    }

    //初始化fragment
    private void InitViews() {
        arr_img = new Button[3];
        arr_img[0] = btn_music;
        arr_img[1] = btn_video;
        arr_img[2] = btn_musictheory;

        fragment_Appreciate_Instrumental = new Fragment_Appreciate_Instrumental();
        fragment_Appreciate_Type = new Fragment_Appreciate_Type();
        fragment_Appreciate_Musician = new Fragment_Appreciate_Musician();
        setDefaultFragment(fragment_Appreciate_Instrumental);
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
        // 3个文字都变色
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

            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
        } else if (index == 1) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[2].setBackgroundResource(R.color.background);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[2].setTextColor(getResources().getColor(R.color.teaching_item_title));
        } else if (index == 2) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);

            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
        }

    }


    //乐谱
    @Event(value = R.id.btn_music, type = View.OnClickListener.class)
    private void ll_teaching_gerenClick(View v) {
        changeCurrBtn(0);
        switchContent(fragment_Appreciate_Instrumental);
    }

    //视频
    @Event(value = R.id.btn_video, type = View.OnClickListener.class)
    private void ll_piano_gerenClick(View v) {
        changeCurrBtn(1);
        switchContent(fragment_Appreciate_Type);
    }
    //乐理
    @Event(value = R.id.btn_musictheory, type = View.OnClickListener.class)
    private void ll_musictheory_gerenClick(View v) {
        changeCurrBtn(2);
        switchContent(fragment_Appreciate_Musician);
    }


}
