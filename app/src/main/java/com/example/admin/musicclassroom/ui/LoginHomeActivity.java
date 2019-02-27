package com.example.admin.musicclassroom.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.LoginInfoVo;
import com.example.admin.musicclassroom.mActivity;
import com.example.admin.musicclassroom.variable.Variable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_login)
public class LoginHomeActivity extends mActivity {



    @ViewInject(R.id.btn_login_onclik)
    private Button btn_LoginClick;

    @ViewInject(R.id.ed_accountnumber)
    private EditText et_userPhone;
    @ViewInject(R.id.ed_password)
    private EditText et_userPassword;


    private Dialog progressDialog;


    private String name;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        InitViews();
        restoreInfo();
    }




    private void InitViews() {
        progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("正在登录");

    }

    /**
     * 请求登陆接口
     */
    private void requestLogin(String username,String password) {
        FinalHttp finalHttp = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("name", username);
        params.put("pwd", password);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                    Variable.loginInfoVo = new Gson().fromJson(content, LoginInfoVo.class);
                    if (Variable.loginInfoVo!=null||Variable.loginInfoVo.getCode().equals("200")){
                        finish();
                        showMessage("登录成功");
                        Intent intent=new Intent(LoginHomeActivity.this,MainActivity.class);
                        LoginHomeActivity.this.startActivity(intent);
//                        iNewsGetHotSpotList();
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


    private void restoreInfo() {
        SharedPreferences sp = getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        et_userPhone.setText(sp.getString("musicname", ""));
    }
    private void meminfo(String usr, String pwd) {
        SharedPreferences.Editor editor = getSharedPreferences("MusicData", Context.MODE_PRIVATE).edit();
        editor.putString("musicname", usr);
        editor.putString("musicpwd", pwd);
        editor.commit();
    }


    /**
     * 登录
     *
     * @param v
     */
    @Event(value = R.id.btn_login_onclik, type = View.OnClickListener.class)
    private void btn_codeClick(View v) {
        name = et_userPhone.getText().toString().trim();
        password = et_userPassword.getText().toString().trim();
        meminfo(name, password);
        requestLogin(name, password);


    }


}

