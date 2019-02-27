package com.example.admin.musicclassroom.fragment;

import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;


public class Fragment_Teaching_Item extends mFragment {
    //教学
    private View views;
    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    private List<CourseVo> courseVoList;
    private GridViewAdaptehistoryList gridViewAdaptehistoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_teaching_item, null);
        x.view().inject(this, views);
        gethistory();
        return views;
    }



    String json="{\n" +
            "\t\"code\": 200,\n" +
            "\t\"msg\": \"success\",\n" +
            "\t\"data\": [{\n" +
            "\t\t\"courseId\": 1,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"故乡的小路\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171019040710.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/故乡的小路.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}, {\n" +
            "\t\t\"courseId\": 3,\n" +
            "\t\t\"grade\": \"一年级\",\n" +
            "\t\t\"term\": \"上学期\",\n" +
            "\t\t\"unit\": \"第一单元\",\n" +
            "\t\t\"courseName\": \"春晓\",\n" +
            "\t\t\"courseImage\": \"uploadimage/suoluetu/20171011044123.jpg\",\n" +
            "\t\t\"wordAuthorName\": \"冼星海\",\n" +
            "\t\t\"anAuthorName\": \"贝多芬\",\n" +
            "\t\t\"mp3\": \"uploadimage/yuepuyin/春晓.mp3\"\n" +
            "\t}],\n" +
            "\t\"timestamp\": 1547014232\n" +
            "}";
    /**
     * 历史数据
     */
    private void gethistory() {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(json);
                    courseVoList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CourseVo>>() {
                    }.getType());
                    if (courseVoList != null) {
                        gridViewAdaptehistoryList=new GridViewAdaptehistoryList(getActivity(),courseVoList);
                        gv_datas.setAdapter(gridViewAdaptehistoryList);
                        gv_datas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Fragment videoFragment = new Fragment_MusicInfo_Item();
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
        finalHttp.post(Variable.address_history, params, callBack);
    }

}
