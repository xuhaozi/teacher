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
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteAppreciateTypeList;
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusictheoryList;
import com.example.admin.musicclassroom.adapter.ViewPagerAdapter;
import com.example.admin.musicclassroom.entity.MusicStyleVo;
import com.example.admin.musicclassroom.entity.MusicianVo;
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
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Appreciate_Type extends mFragment implements IListener {
    //音乐类型
    private View views;

    private List<List<MusicStyleVo>> musicStyleVos;
    private List<String> kindList;

    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;

    @ViewInject(R.id.gv_datas)
    private MyGridView gv_datas;

    @ViewInject(R.id.tv_domestic)
    private TextView tv_domestic;
    @ViewInject(R.id.tv_abroad)
    private TextView tv_abroad;
    @ViewInject(R.id.vp_musictheory)
    private ViewPager vp_musictheory;
    private TextView[] arr_text;// 图标的数组，用于文字高亮

    private int demoFlag;
    private GridViewAdapteAppreciateTypeList gridViewAdapteAppreciateTypeList;
    @ViewInject(R.id.ll_musictheory)
    private LinearLayout ll_musictheory;
    private List<View> viewPagerList;//存放viewpager数据

    private int mNum=0;//记录上次小圆点下标

    @Override
    protected int setContentView() {
        return R.layout.fragment_appreciate_type;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        ListenerManager.getInstance().registerListtener(this);
        InitViews();
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }



    private void initDemo(){
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){//获取本地数据
            musicStyleVos=new ArrayList<>();

        }else {//获取网络数据
            getmusicall("国内");
        }
    }
    /**
     * 通过国家获取音乐风格
     */
    private void getmusicall(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        Log.i("token",Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("country",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    JSONObject obj2= (JSONObject) obj.get("data");
                    musicStyleVos = new Gson().fromJson(obj2.getString("style"), new TypeToken<List<List<MusicStyleVo>>>() {
                    }.getType());
                    kindList=new Gson().fromJson(obj2.getString("kind"),new TypeToken<List<String>>(){}.getType());
                    if (musicStyleVos != null) {
//                        gridViewAdapteAppreciateTypeList=new GridViewAdapteAppreciateTypeList(getActivity(),musicStyleVos);
//                        gv_datas.setAdapter(gridViewAdapteAppreciateTypeList);
                        setViewPager();
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
        if (demoFlag==1){
        }else {
            getmusicall("国内");
        }
    }

    //国外
    @Event(value = R.id.tv_abroad, type = View.OnClickListener.class)
    private void tv_abroadClick(View v) {
        changeCurrBtn(1);
        tv_Choice.setText("国外音乐类型");
        getmusicall("国外");
        if (demoFlag==1){
        }else {
            getmusicall("国内");
        }
    }


    @Override
    public void notifyAllActivity(String str) {
        if (!str.equals("123")){
            Fragment videoFragment = new Fragment_Type_details();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.video_fragment, videoFragment).commit();
        }
    }

    private void setViewPager() {
        int num=0;//viewpager页数
        View view;
        if(kindList.size()%4!=0){
            num=kindList.size()/4+1;
        }else {
            num=kindList.size()/4;
        }
        viewPagerList=new ArrayList<>();
        for(int i=0;i<num;i++){
            //截取每页数据
            List<List<MusicStyleVo>> musicStyleVoListTemp=new ArrayList<>();
            List<String> kindListTemp=new ArrayList<>();
            if(i==num-1){
                for(int y=i*4;y<kindList.size();y++){
                    musicStyleVoListTemp.add(musicStyleVos.get(y));
                    kindListTemp.add(kindList.get(y));
                }
            }else {
                for(int y=i*4;y<(i+1)*4;y++){
                    musicStyleVoListTemp.add(musicStyleVos.get(y));
                    kindListTemp.add(kindList.get(y));
                }
            }
            //设置GridView
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_musictheory_item_mygrid, null);
            MyGridView myGridView = inflate.findViewById(R.id.gv_datas);
            GridViewAdapteAppreciateTypeList gridViewAdapteAppreciateTypeList = new GridViewAdapteAppreciateTypeList(getActivity(), musicStyleVoListTemp, kindListTemp);
            myGridView.setAdapter(gridViewAdapteAppreciateTypeList);
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
}
