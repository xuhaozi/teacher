package com.example.admin.musicclassroom.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.ListViewAdapteTheoryListItem;
import com.example.admin.musicclassroom.adapter.ListViewAdapteVideoListItem;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
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


public class Fragment_Teaching_Musictheory extends mFragment {
    //乐理
    private View views;

private List<TheoryVo> theoryVoList;
    @ViewInject(R.id.right_theory_list)
    private ListView right_theory_list;

    @ViewInject(R.id.iv_imgitem)
    private ImageView iv_imgitem;
private ListViewAdapteTheoryListItem listViewAdapteTheoryListItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_teaching_musictheory, null);
        x.view().inject(this, views);
        GetMusictheoryList();
        return views;
    }


    /**
     * 获取乐理列表
     */
    private void GetMusictheoryList() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("courseId","2");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    theoryVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<TheoryVo>>() {
                    }.getType());
                    if (theoryVoList != null) {
                        listViewAdapteTheoryListItem=new ListViewAdapteTheoryListItem(getActivity(),theoryVoList);
                        right_theory_list.setAdapter(listViewAdapteTheoryListItem);
                        Glide.with(getActivity())
                                .load(Variable.accessaddress_img+theoryVoList.get(0).getTheoryImage())
                                .placeholder(R.mipmap.icon_default_bg)
                                .crossFade()
                                .into(iv_imgitem);

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
        finalHttp.post(Variable.address_theory, params, callBack);
    }

}
