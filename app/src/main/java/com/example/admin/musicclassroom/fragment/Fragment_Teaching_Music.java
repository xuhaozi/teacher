package com.example.admin.musicclassroom.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.Utils.FileUtils;
import com.example.admin.musicclassroom.Utils.PanioMusic;
import com.example.admin.musicclassroom.Utils.XMLParser;
import com.example.admin.musicclassroom.entity.CourseVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.musicentity.beans.ScorePartWise;
import com.example.admin.musicclassroom.ui.MainActivity;
import com.example.admin.musicclassroom.ui.testActivity;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.widget.MusicView;
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

import java.io.File;
import java.io.IOException;


public class Fragment_Teaching_Music extends mFragment {
    //曲谱
    private View views;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.AllKeys)
    private LinearLayout AllKeys;
    @ViewInject(R.id.btn_key)
    private Button btn_key;
    @ViewInject(R.id.btn_staff)
    private Button btn_staff;
    @ViewInject(R.id.btn_spectrum)
    private Button btn_spectrum;
    @ViewInject(R.id.btn_sendout)
    private Button btn_sendout;
    @ViewInject(R.id.btn_fullscreen)
    private Button btn_fullscreen;


    private CourseVo courseVo;
    @ViewInject(R.id.iv_music_play)
    private ImageView iv_music_play;
    @ViewInject(R.id.ll_music_play)
    private LinearLayout ll_music_play;

    @ViewInject(R.id.music_view)
    private MusicView musicView;
    private ScorePartWise scorePartWise;

    @ViewInject(R.id.pb_lod)
    private ProgressBar pb_lod;

    private LinearLayout llKeys;

    private SoundPool soundPool;
    private ImageButton button[];// 按钮数组
    private View parent;// 父视图
    private int buttonId[];// 按钮id
    private boolean havePlayed[];// 是否已经播放了声音，当手指在同一个按钮内滑动，且已经发声，就为true
    private View keys;// 按钮们所在的视图
    private int pressedkey[];
    private Button[] arr_btn;// 数组，用于高亮

    private PanioMusic utils;// 工具类
    private boolean isShowKeys = false;
    private Boolean ismusic=false;

    private int demoFlag;

    @Override
    protected int setContentView() {
        return R.layout.fragment_teaching_music;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        InitKey(views);
        InitViews();
        initDemo();

    }

    private void initDemo() {
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){

        }else {
            getdetail("1");

        }
    }

    @Override
    protected void lazyLoad() {

    }


    private void InitKey(View view){
        soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        // 新建工具类
        utils = new PanioMusic(getActivity());
        // 按钮资源Id
        buttonId = new int[52];
        buttonId[0] = R.id.btPanioOne;
        buttonId[1] = R.id.btPanioTwo;
        buttonId[2] = R.id.btPanioThree;
        buttonId[3] = R.id.btPanioFour;
        buttonId[4] = R.id.btPanioFive;
        buttonId[5] = R.id.btPanioSix;
        buttonId[6] = R.id.btPanioSeven;
        buttonId[7] = R.id.btwhtie8;
        buttonId[8] = R.id.btwhtie9;
        buttonId[9] = R.id.btwhtie10;
        buttonId[10] = R.id.btwhtie11;
        buttonId[11] = R.id.btwhtie12;
        buttonId[12] = R.id.btwhtie13;
        buttonId[13] = R.id.btwhtie14;
        buttonId[14] = R.id.btwhtie15;
        buttonId[15] = R.id.btwhtie16;
        buttonId[16] = R.id.btwhtie17;
        buttonId[17] = R.id.btwhtie18;
        buttonId[18] = R.id.btwhtie19;
        buttonId[19] = R.id.btwhtie20;
        buttonId[20] = R.id.btwhtie21;
        buttonId[21] = R.id.btwhtie22;
        buttonId[22] = R.id.btwhtie23;
        buttonId[23] = R.id.btwhtie24;
        buttonId[24] = R.id.btwhtie25;
        buttonId[25] = R.id.btwhtie26;
        buttonId[26] = R.id.btwhtie27;
        buttonId[27] = R.id.btwhtie28;
        buttonId[28] = R.id.btwhtie29;
        buttonId[29] = R.id.btwhtie30;
        buttonId[30] = R.id.btwhtie31;
        buttonId[31] = R.id.btwhtie32;
        buttonId[32] = R.id.btwhtie33;
        buttonId[33] = R.id.btwhtie34;
        buttonId[34] = R.id.btwhtie35;
        buttonId[35] = R.id.btwhtie36;
        buttonId[36] = R.id.btwhtie37;
        buttonId[37] = R.id.btwhtie38;
        buttonId[38] = R.id.btwhtie39;
        buttonId[39] = R.id.btwhtie40;
        buttonId[40] = R.id.btwhtie41;
        buttonId[41] = R.id.btwhtie42;
        buttonId[42] = R.id.btwhtie43;
        buttonId[43] = R.id.btwhtie44;
        buttonId[44] = R.id.btwhtie45;
        buttonId[45] = R.id.btwhtie46;
        buttonId[46] = R.id.btwhtie47;
        buttonId[47] = R.id.btwhtie48;
        buttonId[48] = R.id.btwhtie49;
        buttonId[49] = R.id.btwhtie50;
        buttonId[50] = R.id.btwhtie51;
        buttonId[51] = R.id.btwhtie52;



        button = new ImageButton[52];
        havePlayed = new boolean[52];

        // 获取按钮对象
        for (int i = 0; i < button.length; i++) {
            button[i] = (ImageButton)view.findViewById(buttonId[i]);
            button[i].setClickable(false);
            havePlayed[i] = false;
        }
        pressedkey = new int[5];
        for (int j = 0; j < pressedkey.length; j++) {
            pressedkey[j] = -1;
        }
        parent=view.findViewById(R.id.llparent);
        parent.setClickable(true);
        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int temp;
                int tempIndex;
                int pointercount;
                pointercount = event.getPointerCount();
                for (int count = 0; count < pointercount; count++) {
                    boolean moveflag = false;// 标记是否是在按键上移动
                    temp = isInAnyScale(event.getX(count), event.getY(count),
                            button);
                    if (temp != -1) {// 事件对应的是当前点
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                // // 单独一根手指或最先按下的那个
                                // pressedkey = temp;
                            case MotionEvent.ACTION_POINTER_DOWN:
                                Log.i("--", "count" + count);
                                pressedkey[count] = temp;
                                if (!havePlayed[temp]) {// 在某个按键范围内
//                                    button[temp]
//                                            .setBackgroundResource(R.drawable.white4test);
                                    // 播放音阶
                                    utils.soundPlay(temp);
                                    Log.i("--", "sound" + temp);
                                    havePlayed[temp] = true;
                                }
                                break;
                            case MotionEvent.ACTION_MOVE:
                                temp = pressedkey[count];
                                for (int i = temp + 1; i >= temp - 1; i--) {
                                    // 当在两端的按钮时，会有一边越界
                                    if (i < 0 || i >= button.length) {
                                        continue;
                                    }
                                    if (isInScale(event.getX(count),
                                            event.getY(count), button[i])) {// 在某个按键内
                                        moveflag = true;
                                        if (i != temp) {// 在相邻按键内
                                            boolean laststill = false;
                                            boolean nextstill = false;
                                            // 假设手指已经从上一个位置抬起，但是没有真的抬起，所以不移位
                                            pressedkey[count] = -1;
                                            for (int j = 0; j < pointercount; j++) {
                                                if (pressedkey[j] == temp) {
                                                    laststill = true;
                                                }
                                                if (pressedkey[j] == i) {
                                                    nextstill = true;
                                                }
                                            }
                                            if (!nextstill) {// 移入的按键没有按下
                                                // 设置当前按键
//                                                button[i]
//                                                        .setBackgroundResource(R.drawable.button_pressed);
                                                // 发音
                                                utils.soundPlay(i);
                                                havePlayed[i] = true;
                                            }

                                            pressedkey[count] = i;

                                            if (!laststill) {// 没有手指按在上面
                                                // 设置上一个按键
//                                                button[temp]
//                                                        .setBackgroundResource(R.drawable.button);
                                                havePlayed[temp] = false;
                                            }
                                            break;
                                        }
                                    }
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_POINTER_UP:
                                // 事件与点对应
                                tempIndex = event.getActionIndex();
                                if (tempIndex == count) {
                                    Log.i("--", "index" + tempIndex);
                                    boolean still = false;
                                    // 当前点已抬起
                                    for (int t = count; t < 5; t++) {
                                        if (t != 4) {
                                            if (pressedkey[t + 1] >= 0) {
                                                pressedkey[t] = pressedkey[t + 1];
                                            } else {
                                                pressedkey[t] = -1;
                                            }
                                        } else {
                                            pressedkey[t] = -1;
                                        }

                                    }
                                    for (int i = 0; i < pressedkey.length; i++) {// 是否还有其他点
                                        if (pressedkey[i] == temp) {
                                            still = true;
                                            break;
                                        }
                                    }
                                    if (!still) {// 已经没有手指按在该键上
//                                        button[temp]
//                                                .setBackgroundResource(R.drawable.button);
                                        havePlayed[temp] = false;
                                        Log.i("--", "button" + temp + "up");
                                    }
                                    break;
                                }
                        }
                    }
                    //
                    if (event.getActionMasked() == MotionEvent.ACTION_MOVE
                            && !moveflag) {
                        if (pressedkey[count] != -1) {
//                            button[pressedkey[count]]
//                                    .setBackgroundResource(R.drawable.button);
                            havePlayed[pressedkey[count]] = false;
                        }
                    }
                }
                return false;
            }
        });
        keys =view.findViewById(R.id.llKeys) ;
        Intent intent=new Intent(getActivity(),testActivity.class);
        this.startActivity(intent);

    }
    //初始化fragment
    private void InitViews() {
        arr_btn = new Button[5];
        arr_btn[0] = btn_key;
        arr_btn[1] = btn_staff;
        arr_btn[2] = btn_spectrum;
        arr_btn[3] = btn_sendout;
        arr_btn[4] = btn_fullscreen;
        isShowKeys = true;
        changeCurrBtn(0);
    }

    @NonNull
    private String getString() {
        String filepath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            filepath = getActivity().getFilesDir().getAbsolutePath();
        }
        return filepath;
    }

//    数据填充
    private void InitData(final CourseVo courseVo){
//        文件路径
         String filepath = "";
         filepath = getString();
        File file = new File(filepath + "/你的名字叫什么.xml");
        File filemp3 = new File(filepath + "/你的名字叫什么.mp3");
        File midifile = new File(filepath + "/你的名字叫什么.mid");
         XMLParser xmlparser = new XMLParser(file, getActivity());
         scorePartWise = xmlparser.readFromXml();
         musicView.setScorePartWise(scorePartWise, getActivity(), midifile);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setDataSource(filemp3.getPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // todo
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    // i 0~100
                    Log.d("Progress:", "缓存进度" + i + "%");
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // todo
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv_title.setText(courseVo.getCourseName().toString().trim());
        ll_music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ismusic==false){
                    iv_music_play.setImageResource(R.mipmap.icon_stop_music);
                    ismusic=true;
                    mediaPlayer.start();
                }else {
                    iv_music_play.setImageResource(R.mipmap.icon_play_music);
                    ismusic=false;
                    mediaPlayer.pause();
                }
            }
        });
    }


    /**
     * 获取详细数据
     */
    private void getdetail(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("courseId",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    courseVo = new Gson().fromJson(obj.getString("data"), new TypeToken<CourseVo>() {
                    }.getType());
                    if (courseVo != null) {
                        InitData(courseVo);
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
        finalHttp.post(Variable.address_detail, params, callBack);
    }

    // 高亮按钮
    private void changeCurrBtn(int index) {
        // 5个文字都变色
        for (int i = 0; i < arr_btn.length; i++) {
            UpdatabtnState(index);
        }
        // 哪个高亮
        UpdatabtnState(index);
    }


    public void UpdatabtnState(int index) {
        if (index == 0) {
            if (isShowKeys){
                arr_btn[index].setTextColor(getResources().getColor(R.color.btn_blue));
            }else {
                arr_btn[0].setTextColor(getResources().getColor(R.color.white));
            }
            arr_btn[1].setTextColor(getResources().getColor(R.color.white));
            arr_btn[2].setTextColor(getResources().getColor(R.color.white));
            arr_btn[3].setTextColor(getResources().getColor(R.color.white));
            arr_btn[4].setTextColor(getResources().getColor(R.color.white));
        } else if (index == 1) {
            arr_btn[index].setTextColor(getResources().getColor(R.color.white));
            arr_btn[2].setTextColor(getResources().getColor(R.color.white));
            arr_btn[3].setTextColor(getResources().getColor(R.color.white));
            arr_btn[4].setTextColor(getResources().getColor(R.color.white));
        } else if (index == 2) {
            arr_btn[1].setTextColor(getResources().getColor(R.color.white));
            arr_btn[index].setTextColor(getResources().getColor(R.color.btn_blue));
            arr_btn[3].setTextColor(getResources().getColor(R.color.white));
            arr_btn[4].setTextColor(getResources().getColor(R.color.white));
        } else if (index == 3) {
            arr_btn[1].setTextColor(getResources().getColor(R.color.white));
            arr_btn[2].setTextColor(getResources().getColor(R.color.white));
            arr_btn[index].setTextColor(getResources().getColor(R.color.btn_blue));
            arr_btn[4].setTextColor(getResources().getColor(R.color.white));
        } else if (index == 4) {
            arr_btn[1].setTextColor(getResources().getColor(R.color.white));
            arr_btn[2].setTextColor(getResources().getColor(R.color.white));
            arr_btn[3].setTextColor(getResources().getColor(R.color.white));
            arr_btn[index].setTextColor(getResources().getColor(R.color.btn_blue));
        }
    }


    /**
     * 判断某个点是否在某个按钮的范围内
     *
     * @param x      横坐标
     * @param y      纵坐标
     * @param button 按钮对象
     * @return 在：true；不在：false
     */
    private boolean isInScale(float x, float y, ImageButton button) {
        // keys.getTop()是获取按钮所在父视图相对其父视图的右上角纵坐标

        if (x > button.getLeft() && x < button.getRight()
                && y > button.getTop() + keys.getTop()
                && y < button.getBottom() + keys.getTop()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断某个点是否在一个按钮集合中的某个按钮内
     *
     * @param x      横坐标
     * @param y      纵坐标
     * @param button 按钮数组
     * @return
     */
    private int isInAnyScale(float x, float y, ImageButton[] button) {
        // keys.getTop()是获取按钮所在父视图相对其父视图的右上角纵坐标

        for (int i = 0; i < button.length; i++) {
            if (x > button[i].getLeft() && x < button[i].getRight()
                    && y > button[i].getTop() + keys.getTop()
                    && y < button[i].getBottom() + keys.getTop()) {
                return i;
            }
        }
        return -1;
    }



    //琴键
    @Event(value = R.id.btn_key, type = View.OnClickListener.class)
    private void ll_teaching_gerenClick(View v) {
        changeCurrBtn(0);
        if (isShowKeys){
            AllKeys.setVisibility(View.VISIBLE);
            isShowKeys = false;
        }else {
            isShowKeys = true;
            AllKeys.setVisibility(View.GONE);
        }
    }

    //五线谱
    @Event(value = R.id.btn_staff, type = View.OnClickListener.class)
    private void ll_piano_gerenClick(View v) {
        changeCurrBtn(1);
    }
    //简谱
    @Event(value = R.id.btn_spectrum, type = View.OnClickListener.class)
    private void ll_musictheory_gerenClick(View v) {
        changeCurrBtn(2);
    }
    //发送MID
    @Event(value = R.id.btn_sendout, type = View.OnClickListener.class)
    private void ll_appreciate_gerenClick(View v) {
        changeCurrBtn(3);
    }
    //全屏
    @Event(value = R.id.btn_fullscreen, type = View.OnClickListener.class)
    private void ll_drawingboard_gerenClick(View v) {
        changeCurrBtn(4);
    }
}
