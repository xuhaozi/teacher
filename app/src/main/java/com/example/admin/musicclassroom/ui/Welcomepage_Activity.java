package com.example.admin.musicclassroom.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.LoginInfoVo;
import com.example.admin.musicclassroom.mActivity;
import com.example.admin.musicclassroom.variable.Variable;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_welcomepage)
public class Welcomepage_Activity extends mActivity {
    /*
        *欢迎页
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        InitViews();
    }


    private void restoreInfo() {
        SharedPreferences sp = getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        if (sp != null && sp.getString("musicname", "") != null && sp.getString("musicpwd", "") != null) {
            if (!sp.getString("musicname","").equals("")&&!sp.getString("musicpwd","").equals("")){
                requestLogin(sp.getString("musicname", ""), sp.getString("musicpwd", ""));
            }else {
                Intent intent=new Intent(Welcomepage_Activity.this,LoginHomeActivity.class);
                Welcomepage_Activity.this.startActivity(intent);
            }
        }
    }

    /**
     * 请求登陆接口
     *
     * @param name
     * @param password
     */
    private void requestLogin(final String name, final String password) {
        FinalHttp finalHttp = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("name", name);
        params.put("pwd", password);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                Variable.loginInfoVo = new Gson().fromJson(content, LoginInfoVo.class);
                if (Variable.loginInfoVo.getCode().equals("200")){
                    finish();
                    showMessage("登录成功");
                    Intent intent=new Intent(Welcomepage_Activity.this,MainActivity.class);
                    Welcomepage_Activity.this.startActivity(intent);
                }else{
                    showMessage("登录失败");
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        };
        finalHttp.post(Variable.address_Login, params, callBack);
    }

    private void InitViews() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        }

        //后台处理耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    //耗时任务，比如加载网络数据
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            restoreInfo();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
