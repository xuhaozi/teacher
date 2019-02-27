package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteAppreciateTypeList;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.radiobroadcast.IListener;
import com.example.admin.musicclassroom.radiobroadcast.ListenerManager;
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


public class Fragment_Appreciate_Type extends mFragment implements IListener {
    //音乐类型
    private View views;

    private List<MusicStyleVo> musicStyleVos;

    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;

    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    @ViewInject(R.id.tv_domestic)
    private TextView tv_domestic;
    @ViewInject(R.id.tv_abroad)
    private TextView tv_abroad;

    private TextView[] arr_text;// 图标的数组，用于文字高亮


    private GridViewAdapteAppreciateTypeList gridViewAdapteAppreciateTypeList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_appreciate_type, null);
        x.view().inject(this, views);
        ListenerManager.getInstance().registerListtener(this);
        InitViews();
        getmusicall("国内");
        return views;
    }

    /**
     * 通过国家获取音乐风格
     */
    private void getmusicall(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("country",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    JSONObject obj2= (JSONObject) obj.get("data");
                    musicStyleVos = new Gson().fromJson(obj2.getString("style"), new TypeToken<List<MusicStyleVo>>() {
                    }.getType());
                    if (musicStyleVos != null) {
                        gridViewAdapteAppreciateTypeList=new GridViewAdapteAppreciateTypeList(getActivity(),musicStyleVos);
                        gv_datas.setAdapter(gridViewAdapteAppreciateTypeList);

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
        finalHttp.post(Variable.address_music_all, params, callBack);
    }



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

    //国内
    @Event(value = R.id.tv_domestic, type = View.OnClickListener.class)
    private void tv_domesticClick(View v) {
        changeCurrBtn(0);
        tv_Choice.setText("国内音乐类型");
        getmusicall("国内");
    }

    //国外
    @Event(value = R.id.tv_abroad, type = View.OnClickListener.class)
    private void tv_abroadClick(View v) {
        changeCurrBtn(1);
        tv_Choice.setText("国外音乐类型");
        getmusicall("国外");

    }


    @Override
    public void notifyAllActivity(String str) {
        if (!str.equals("123")){
            Fragment videoFragment = new Fragment_Type_details();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.video_fragment, videoFragment).commit();
        }
    }
}
