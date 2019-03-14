package com.example.admin.musicclassroom.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.Utils.XMLParser;
import com.example.admin.musicclassroom.entity.LoginInfoVo;
import com.example.admin.musicclassroom.mActivity;
import com.example.admin.musicclassroom.musicentity.beans.ScorePartWise;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.widget.MusicView;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.test)
public class testActivity extends mActivity {
//五线谱xml渲染解析
    private MusicView music_view;
    private ScorePartWise scorePartWise;
    private TextView tv_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        InitViews();
    }
    private void InitViews() {
        music_view=findViewById(R.id.music_view);
            tv_play=findViewById(R.id.tv_play);
//读取本地根目录
            String filepath = "";
            filepath = getString();
            File file = new File(filepath + "/布谷.xml");
            File midifile = new File(filepath + "/布谷.mid");
//      解析xml读取本地file
            XMLParser xmlparser = new XMLParser(file, getApplicationContext());
            scorePartWise = xmlparser.readFromXml();
            music_view.setScorePartWise(scorePartWise, this, midifile);
             tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                播放mid跟弹的类
                String filepath = "";
                filepath = getString();
                File  mp3file = new File(filepath + "/布谷.mp3");
                music_view.playMusic(false, mp3file);
            }
        });
    }
    @NonNull
    private String getString() {
        String filepath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            filepath = getFilesDir().getAbsolutePath();
        }
        return filepath;
    }

}

