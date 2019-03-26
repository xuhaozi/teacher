package com.example.admin.musicclassroom.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteAppreciateTypeList;
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusictheoryList;
import com.example.admin.musicclassroom.adapter.ViewPagerAdapter;
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

import java.util.ArrayList;
import java.util.List;


public class Fragment_Musictheory_Item extends mFragment implements IListener {
    //乐理
    private View views;
    private List<List<TheoryVo>> theoryVos;
    private List<String> chapterList;

    @ViewInject(R.id.vp_musictheory)
    private ViewPager vp_musictheory;
    @ViewInject(R.id.ll_musictheory)
    private LinearLayout ll_musictheory;
    private List<View> viewPagerList;//存放viewpager数据

    private int demoFlag;
    private int mNum=0;//记录上次小圆点下标

    @Override
    protected int setContentView() {
        return R.layout.fragment_musictheory_item;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        ListenerManager.getInstance().registerListtener(this);
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }

    private void initDemo() {
        //获取演示标识
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){//获取本地数据
            getDemo();
        }else {//获取网络数据
            getmusicall();
        }
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
                    theoryVos = new Gson().fromJson(obj2.getString("theory"), new TypeToken<List<List<TheoryVo>>>() {
                    }.getType());
                    chapterList=new Gson().fromJson(obj2.getString("chapter"),new TypeToken<List<String>>(){}.getType());
                    if (theoryVos != null) {
                        setViewPager();
//                        gridViewAdapteMusictheoryList=new GridViewAdapteMusictheoryList(getActivity(),theoryVos);
//                        gv_datas.setAdapter(gridViewAdapteMusictheoryList);

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

    private void setViewPager() {
        int num=0;//viewpager页数
        View view;
        if(chapterList.size()%4!=0){
            num=chapterList.size()/4+1;
        }else {
            num=chapterList.size()/4;
        }
        viewPagerList=new ArrayList<>();
        for(int i=0;i<num;i++){
            //截取每页数据
            List<List<TheoryVo>> theoryVoListTemp=new ArrayList<>();
            List<String> chapterListTemp=new ArrayList<>();
            if(i==num-1){
                for(int y=i*4;y<chapterList.size();y++){
                    theoryVoListTemp.add(theoryVos.get(y));
                    chapterListTemp.add(chapterList.get(y));
                }
            }else {
                for(int y=i*4;y<(i+1)*4;y++){
                    theoryVoListTemp.add(theoryVos.get(y));
                    chapterListTemp.add(chapterList.get(y));
                }
            }
            //设置GridView
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_musictheory_item_mygrid, null);
            MyGridView myGridView = inflate.findViewById(R.id.gv_datas);
            GridViewAdapteMusictheoryList gridViewAdapteMusictheoryList = new GridViewAdapteMusictheoryList(getActivity(), theoryVoListTemp, chapterListTemp);
            myGridView.setAdapter(gridViewAdapteMusictheoryList);
            viewPagerList.add(inflate);

            //创建底部指示器(小圆点)
            view = new View(getActivity());
            view.setBackgroundResource(R.drawable.viewpager_background);
            view.setEnabled(false);
            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(14, 14);
            //设置间隔
            if (i!=0) {
                layoutParams.leftMargin = 30;
            }
            //添加到LinearLayout
            ll_musictheory.addView(view, layoutParams);
        }
        vp_musictheory.setAdapter(new ViewPagerAdapter(viewPagerList,getActivity()));
        //第一次显示小黑点
        ll_musictheory.getChildAt(0).setEnabled(true);
        //注册
        vp_musictheory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ll_musictheory.getChildAt(mNum).setEnabled(false);
                ll_musictheory.getChildAt(i).setEnabled(true);
                mNum = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void notifyAllActivity(String str) {
        Fragment videoFragment = new Fragment_Musictheory_details(str);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }

    public void getDemo() {

    }
}
