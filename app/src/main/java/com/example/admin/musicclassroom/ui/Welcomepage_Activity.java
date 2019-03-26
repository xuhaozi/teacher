package com.example.admin.musicclassroom.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.AutoVo;
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
    private String token;
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
        token=sp.getString("token","");
        if (sp != null && token != null ) {
            if (!"".equals(token)){
                requestLogin(token);
            }else {
                Intent intent=new Intent(Welcomepage_Activity.this,LoginHomeActivity.class);
                Welcomepage_Activity.this.startActivity(intent);
            }
        }else {
            Intent intent=new Intent(Welcomepage_Activity.this,LoginHomeActivity.class);
            Welcomepage_Activity.this.startActivity(intent);
        }
    }

    /**
     * 请求登陆接口
     *
     * @param token
     */
    private void requestLogin(final String token) {
        Log.i("restore",token);
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token", token);
        AjaxParams params = new AjaxParams();
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                Log.i("restore",content);
                AutoVo autoVo = new Gson().fromJson(content, AutoVo.class);
                Variable.loginInfoVo=new LoginInfoVo(token);
                if (autoVo.getCode()==200){
                    finish();
                    showMessage("登录成功");
                    Log.i("restore",token);
                    Intent intent=new Intent(Welcomepage_Activity.this,MainActivity.class);
                    Welcomepage_Activity.this.startActivity(intent);
                }else{
                    showMessage("登录失败");
                    Log.i("restore","登录失败");
                    Intent intent=new Intent(Welcomepage_Activity.this,LoginHomeActivity.class);
                    Welcomepage_Activity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        };
        finalHttp.post(Variable.address_Automatic_logon, params, callBack);
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
