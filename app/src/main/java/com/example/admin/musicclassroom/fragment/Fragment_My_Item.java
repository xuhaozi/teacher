package com.example.admin.musicclassroom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.admin.musicclassroom.R;
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


public class Fragment_My_Item extends mFragment {
    //个人中心
    private View views;
    @ViewInject(R.id.ll_exit)
    private LinearLayout ll_exit;

    private List<CourseVo> courseVoList;
    private GridViewAdaptehistoryList gridViewAdaptehistoryList;

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_item;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
    }

    @Override
    protected void lazyLoad() {

    }
    @Event(value = R.id.ll_exit, type = View.OnClickListener.class)
    private void ll_exit_Click(View v) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MusicData", Context.MODE_PRIVATE).edit();
        editor.putString("token","");
        editor.putString("musicname", "");
        editor.putString("musicpwd", "");
        editor.putInt("demo",0);//修改演示标识
        editor.commit();
        getActivity().finish();
    }
}
