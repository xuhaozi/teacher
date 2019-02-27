package com.example.admin.musicclassroom.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.ListViewAdapteVideoListItem;
import com.example.admin.musicclassroom.entity.ExerciseVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.widget.CustomRoundAngleImageView;
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


public class Fragment_Teaching_Exercises extends mFragment {
    //习题
    private View views;
    @ViewInject(R.id.iv_img)
    private CustomRoundAngleImageView iv_img;

    @ViewInject(R.id.btn_title)
    private Button btn_title;

    private List<ExerciseVo> exerciseVoList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_teaching_exercises, null);
        x.view().inject(this, views);
        GetExercisesList();
        return views;
    }


    /**
     * 获取习题列表
     */
    private void GetExercisesList() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("courseId","2");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    exerciseVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ExerciseVo>>() {
                    }.getType());
                    if (exerciseVoList != null) {
                        btn_title.getBackground().setAlpha(155);//0~255透明度值
                        btn_title.setText(exerciseVoList.get(0).getExercise());
                        Glide.with(getActivity())
                                .load(Variable.accessaddress_img + exerciseVoList.get(0).getExerciseImage().toString())
                                .placeholder(R.mipmap.icon_default_bg)
                                .crossFade()
                                .into(iv_img);
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
        finalHttp.post(Variable.address_exercise, params, callBack);
    }

}
