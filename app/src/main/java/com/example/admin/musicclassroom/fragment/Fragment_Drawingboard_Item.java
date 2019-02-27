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


public class Fragment_Drawingboard_Item extends mFragment {
    //画板
    private View views;

    @ViewInject(R.id.btn_Drawingboard)
    private Button btn_Drawingboard;
    @ViewInject(R.id.btn_Musicscore)
    private Button btn_Musicscore;


    @ViewInject(R.id.fl_body_teaching)
    private FrameLayout fl_body_teaching;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment mContent, fragment_Drawing_board, fragment_Music_score;
    private Button[] arr_img;// 图标的数组，用于文字高亮
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_drawingboard_item, null);
        x.view().inject(this, views);
        InitViews();
        return views;
    }

    //初始化fragment
    private void InitViews() {
        arr_img = new Button[2];
        arr_img[0] = btn_Drawingboard;
        arr_img[1] = btn_Musicscore;

        fragment_Drawing_board = new Fragment_Drawing_board();
        fragment_Music_score = new Fragment_Music_score();
        setDefaultFragment(fragment_Drawing_board);
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

    public void UpdataImgState(int index) {
        if (index == 0) {
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[1].setBackgroundResource(R.color.background);
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
            arr_img[1].setTextColor(getResources().getColor(R.color.teaching_item_title));

        } else if (index == 1) {
            arr_img[0].setBackgroundResource(R.color.background);
            arr_img[index].setBackgroundResource(R.mipmap.icon_violet);
            arr_img[0].setTextColor(getResources().getColor(R.color.teaching_item_title));
            arr_img[index].setTextColor(getResources().getColor(R.color.white));
        }

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


    //乐谱
    @Event(value = R.id.btn_Drawingboard, type = View.OnClickListener.class)
    private void ll_teaching_gerenClick(View v) {
        changeCurrBtn(0);
        switchContent(fragment_Drawing_board);
    }

    //视频
    @Event(value = R.id.btn_Musicscore, type = View.OnClickListener.class)
    private void ll_piano_gerenClick(View v) {
        changeCurrBtn(1);
        switchContent(fragment_Music_score);
    }

}
