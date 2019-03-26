package com.example.admin.musicclassroom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusicAllList;
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusicianList;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.MusicianVo;
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

import java.util.ArrayList;
import java.util.List;


public class Fragment_Appreciate_Musician extends mFragment {
    //音乐家
    private View views;


    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;

    @ViewInject(R.id.tv_domestic)
    private TextView tv_domestic;
    @ViewInject(R.id.tv_abroad)
    private TextView tv_abroad;

    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    private List<MusicianVo> musicianVos;
    private List<MusicianVo> musicianVosInland;//囯内本地数据
    private List<MusicianVo> musicianVosForeign;//国外本地数据

    private GridViewAdapteMusicianList gridViewAdapteMusicianList;

    private TextView[] arr_text;// 图标的数组，用于文字高亮

    private int demoFlag;

    @Override
    protected int setContentView() {
        return R.layout.fragment_appreciate_musician;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        InitViews();
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }


    private void initDemo() {
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){//获取本地数据
            musicianVosInland=new ArrayList<>();
            musicianVosInland.add(new MusicianVo("马友友","file:///android_asset/马友友/马友友.jpg"));
            musicianVosForeign=new ArrayList<>();
            musicianVosForeign.add(new MusicianVo("贝多芬","file:///android_asset/贝多芬/贝多芬.jpg"));
            getDemoFlagData("国内");
        }else {//获取网络数据
            getMusician("国内");
        }
    }
    private void getDemoFlagData(final String str){
        if("国内".equals(str)){
            musicianVos=musicianVosInland;
        }else if("国外".equals(str)){
            musicianVos=musicianVosForeign;
        }
        gridViewAdapteMusicianList=new GridViewAdapteMusicianList(getActivity(),musicianVos,demoFlag);
        gv_datas.setAdapter(gridViewAdapteMusicianList);
        gv_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment videoFragment = new Fragment_Musician_details(musicianVos.get(i).getMusicianId(),str);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.video_fragment, videoFragment).commit();
            }
        });
    }
    //初始化fragment
    private void InitViews() {
        arr_text = new TextView[2];
        arr_text[0] = tv_domestic;
        arr_text[1] = tv_abroad;
        changeCurrBtn(0);
    }

    // 高亮按钮
    private void changeCurrBtn(int index) {
        for (int i = 0; i < arr_text.length; i++) {
            arr_text[i].setTextColor(getResources().getColor(R.color.home_Theborder_Unchecked));
        }
        // 哪个高亮
        arr_text[index].setTextColor(getResources().getColor(R.color.home_Theborder_Selection));
    }

    /**
     * 通过音乐家名称搜索音乐家
     */
    private void getMusician(final String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("country",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    musicianVos = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MusicianVo>>() {
                    }.getType());
                    if (musicianVos != null) {
                        gridViewAdapteMusicianList=new GridViewAdapteMusicianList(getActivity(),musicianVos);
                        gv_datas.setAdapter(gridViewAdapteMusicianList);
                        gv_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Fragment videoFragment = new Fragment_Musician_details(musicianVos.get(i).getMusicianId(),str);
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
        finalHttp.post(Variable.address_musician, params, callBack);
    }

    //国内器乐
    @Event(value = R.id.tv_domestic, type = View.OnClickListener.class)
    private void tv_domesticClick(View v) {
        changeCurrBtn(0);
        tv_Choice.setText("国内音乐家");
        if (demoFlag==1){
            getDemoFlagData("国内");
        }else {
            getMusician("国内");
        }
    }

    //国外器乐
    @Event(value = R.id.tv_abroad, type = View.OnClickListener.class)
    private void tv_abroadClick(View v) {
        changeCurrBtn(1);
        tv_Choice.setText("国外音乐家");
        if (demoFlag==1){
            getDemoFlagData("国外");
        }else {
            getMusician("国外");
        }

    }

}
