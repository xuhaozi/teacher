package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.entity.MusicianVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.view.VIdeoPlayPopupWindow;
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


public class Fragment_Musictheory_details extends mFragment {
    //乐理详情
    private View views;

    private TheoryVo theoryVo;

    @ViewInject(R.id.iv_musictheory_img)
    private ImageView iv_musictheory_img;
    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;
    @ViewInject(R.id.ll_return)
    private LinearLayout ll_return;

    @ViewInject(R.id.iv_video)
    private ImageView iv_video;


    private VIdeoPlayPopupWindow vIdeoPlayPopupWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_musictheory_details, null);
        x.view().inject(this, views);
        getmusicalInfo("2");
        return views;
    }

    private void InitView(TheoryVo theoryVo){
        tv_Choice.setText(theoryVo.getTheoryName());
        Glide.with(getActivity())
                .load(Variable.accessaddress_img + theoryVo.getTheoryImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(iv_musictheory_img);
    }

    /**
     * 获取乐理详情
     */
    private void getmusicalInfo(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("theoryId",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    theoryVo = new Gson().fromJson(obj.getString("data"), new TypeToken<TheoryVo>() {
                    }.getType());
                    if (theoryVo != null) {
                        InitView(theoryVo);
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
        finalHttp.post(Variable.address_theory_info, params, callBack);
    }

    //查看本課視頻
    @Event(value = R.id.iv_video, type = View.OnClickListener.class)
    private void iv_videoClick(View v) {
        vIdeoPlayPopupWindow = new VIdeoPlayPopupWindow(getActivity(),theoryVo);
        vIdeoPlayPopupWindow.showAtLocation(getView().findViewById(R.id.iv_video), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Musictheory_Item();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }
}
