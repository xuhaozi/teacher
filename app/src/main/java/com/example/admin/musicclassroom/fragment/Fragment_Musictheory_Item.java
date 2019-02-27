package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteAppreciateTypeList;
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusictheoryList;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class Fragment_Musictheory_Item extends mFragment implements IListener {
    //乐理
    private View views;
    private List<TheoryVo> theoryVos;

    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    private GridViewAdapteMusictheoryList gridViewAdapteMusictheoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_musictheory_item, null);
        x.view().inject(this, views);
        ListenerManager.getInstance().registerListtener(this);
        getmusicall();
        return views;
    }
    /**
     * 通过国家获取音乐风格
     */
    private void getmusicall() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    JSONObject obj2= (JSONObject) obj.get("data");
                    theoryVos = new Gson().fromJson(obj2.getString("theory"), new TypeToken<List<TheoryVo>>() {
                    }.getType());
                    if (theoryVos != null) {
                        gridViewAdapteMusictheoryList=new GridViewAdapteMusictheoryList(getActivity(),theoryVos);
                        gv_datas.setAdapter(gridViewAdapteMusictheoryList);

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
        finalHttp.post(Variable.address_theory_all, params, callBack);
    }

    @Override
    public void notifyAllActivity(String str) {
        if (str.equals("1")){
            Fragment videoFragment = new Fragment_Musictheory_details();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.video_fragment, videoFragment).commit();
        }

    }
}
