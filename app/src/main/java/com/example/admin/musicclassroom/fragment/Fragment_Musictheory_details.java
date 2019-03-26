package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.MusicianVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.view.VIdeoPlayPopupWindow;
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

import static android.content.Context.AUDIO_SERVICE;

@SuppressLint("ValidFragment")
public class Fragment_Musictheory_details extends mFragment {
    //乐理详情
    private View views;

    private TheoryVo theoryVo;

    @ViewInject(R.id.iv_musictheory_img)
    private ImageView iv_musictheory_img;
    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;
    @ViewInject(R.id.ll_return)
    private LinearLayout ll_return;

    @ViewInject(R.id.iv_video)
    private ImageView iv_video;

    private String theoryId;
    private VIdeoPlayPopupWindow vIdeoPlayPopupWindow;
    private VideoView videoView;
    private SeekBar seekbarVideo;//视频进度条
    private ImageView ivVideoPause;//视频开始按钮
    private TextView tv_video_current_time;//视频当前时长
    private TextView tv_video_duration;//视频总时长
    private SeekBar seekbar_voice;//音量进度条

    private AudioManager am ;//音量管理器
    private int currentVoice ;//当前音量
    private int maxVoice;//总音量
    private Boolean isMute=false;//音量标识

    private int demoFlag;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //1.得到当前的视频播放进程
                    int currentPosition = videoView.getCurrentPosition();//0

                    //2.SeekBar.setProgress(当前进度);
                    seekbarVideo.setProgress(currentPosition);

//                    3.更新文本播放进度
                    tv_video_current_time.setText(stringForTime(currentPosition));

                    //4.每0.5秒更新一次
                    handler.removeMessages(1);
                    handler.sendEmptyMessageDelayed(1, 500);
                    break;
            }
        }
    };
    public Fragment_Musictheory_details(String str) {
        super();
        this.theoryId=str;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_musictheory_details;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        initDemo();
    }

    private void initDemo() {
        //获取演示标识
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        Log.i("demoFlag",demoFlag+"");
        if(demoFlag==1){

        }else {
            getmusicalInfo(theoryId);
        }
    }

    @Override
    protected void lazyLoad() {

    }

    private void InitView(TheoryVo theoryVo){
        tv_Choice.setText(theoryVo.getTheoryName());
        Glide.with(getActivity())
                .load(Variable.accessaddress_img + theoryVo.getTheoryImage().toString())
                .placeholder(R.mipmap.icon_default_bg)
                .crossFade()
                .into(iv_musictheory_img);
    }

    /**
     * 获取乐理详情
     */
    private void getmusicalInfo(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("theoryId",str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    theoryVo = new Gson().fromJson(obj.getString("data"), new TypeToken<TheoryVo>() {
                    }.getType());
                    if (theoryVo != null) {
                        InitView(theoryVo);
                    }else {
                        showMessage("数据加载失败");
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
        finalHttp.post(Variable.address_theory_info, params, callBack);
    }

    //查看本課視頻
    @Event(value = R.id.iv_video, type = View.OnClickListener.class)
    private void iv_videoClick(View v) {
//        vIdeoPlayPopupWindow = new VIdeoPlayPopupWindow(getActivity(),theoryVo.getVideoVoList().get(0).getVideoName(),theoryVo.getVideoVoList().get(0).getVideo());
//        vIdeoPlayPopupWindow.showAtLocation(getView().findViewById(R.id.iv_video), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        setDialog(theoryVo.getVideoVoList().get(0).getVideoName(),theoryVo.getVideoVoList().get(0).getVideo());
    }

    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Musictheory_Item();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }
    /**
     * 设置视频Dialog
     */
    private void setDialog(String videoName,String videoUri) {
        final Dialog mDialog=new Dialog(getActivity());
        mDialog.setContentView(R.layout.layout_popupwindow_video);
        //点击外部不关闭
        mDialog.setCancelable(false);
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setDimAmount(0);
        //设置dialog弹窗宽高
        WindowManager.LayoutParams params= window.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        //dialog宽高适应子布局xml
//                params.height= LinearLayout.LayoutParams.WRAP_CONTENT;
//                params.width= LinearLayout.LayoutParams.MATCH_PARENT;
        params.height=845;
        params.width=1284;
        window.setAttributes(params);
        //设置标题
        TextView tv_title=mDialog.findViewById(R.id.tv_title);
        tv_title.setText(videoName);
        //关闭按钮
        ImageView iv_close=mDialog.findViewById(R.id.tv_close);
        //全屏
        ImageView iv_fullscreen=mDialog.findViewById(R.id.iv_fullscreen);
        iv_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"暂无此功能",Toast.LENGTH_SHORT).show();
            }
        });
        tv_video_current_time=mDialog.findViewById(R.id.tv_video_current_time);
        tv_video_duration=mDialog.findViewById(R.id.tv_video_duration);
        seekbar_voice=mDialog.findViewById(R.id.seekbar_voice);
        //为音量SeekBar设置监听
        seekbar_voice.setOnSeekBarChangeListener(new VoiceOnSeekBarChangeListener());
        getVoice();

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"close",Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });

        seekbarVideo=(SeekBar) mDialog.findViewById(R.id.seekbar_video);
        videoView=(VideoView)mDialog.findViewById(R.id.videoView);
        ivVideoPause=mDialog.findViewById(R.id.iv_video_pause);

        ivVideoPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    ivVideoPause.setImageResource(R.mipmap.videw_btn_pause);
                    handler.sendEmptyMessage(1);
                }else {
                    videoView.start();
                    ivVideoPause.setImageResource(R.mipmap.videw_btn_start);
                    handler.sendEmptyMessage(1);
                }
            }
        });

        //设置视频准备好了的监听
        videoView.setOnPreparedListener(new MyPreparedListener());
        //设置视频播放出错的监听
        videoView.setOnErrorListener(new MyErrorListener());
        //设置视频播放完成的监听
        videoView.setOnCompletionListener(new MyCompletionListener());

        if(demoFlag==1){
//            String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+musicalVo.getVideoVoList().get(i).getVideo();
            //设置视频路径
//            videoView.setVideoPath(videoUrl1);
        }else {
            Uri uri = Uri.parse(Variable.accessaddress_img+videoUri);
            videoView.setVideoURI(uri);
        }
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setPadding(0,0,0,100);
        //设置视频控制器
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        //播放完成回调
        videoView.setOnCompletionListener( new Fragment_Teaching_Video.MyPlayerOnCompletionListener());
        //设置视频路径
//        videoView.start();
        ivVideoPause.setImageResource(R.mipmap.videw_btn_pause);

        seekbarVideo.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());
    }
    private String stringForTime(int duration){
        int totalSeconds = duration/1000;
        int seconds = totalSeconds % 60;
        String secondsStr=seconds+"";
        if(seconds<=9){
            secondsStr="0"+seconds;
        }
        int minutes = (totalSeconds/60)%60;
        String minutesStr=minutes+"";
        if(minutes<=9){
            minutesStr="0"+minutes;
        }
        int hours = totalSeconds/3600;
        String hoursStr=hours+"";
        if(hours<=9){
            hoursStr="0"+hours;
        }
        if(hours>0){
            return hoursStr+":"+minutesStr+":"+secondsStr;
        } else {
            return minutesStr+":"+secondsStr;
        }
    }
    class VoiceOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        //该参数同上述SeekBar
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                if (progress > 0) {
                    isMute = false;
                } else {
                    isMute = true;
                }
                updataVoice(progress, isMute);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    /**
     * 设置音量的大小
     * @param progress
     */
    private void updataVoice(int progress, boolean isMute) {
        if (isMute) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            seekbar_voice.setProgress(0);
        } else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            seekbar_voice.setProgress(progress);
            currentVoice = progress;
        }
    }

    /**
     * 设置音量
     */
    private void getVoice(){
        //得到音量
        am = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        currentVoice = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVoice= am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //最大音量和SeekBar关联
        seekbar_voice.setMax(maxVoice);
        //设置当前进度-当前音量
        seekbar_voice.setProgress(currentVoice);
    }
    private class MyErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            return false;
        }
    }

    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

        }
    }

    private class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (b) {
                videoView.seekTo(i);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            ivVideoPause.setImageResource(R.mipmap.videw_btn_pause);
        }
    }
    private class MyPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            //视频准备好了，进行播放
            mediaPlayer.start();

            //视频的总时长，并关联SeekBar的总长度
            int duration = videoView.getDuration();

            //设置SeekBar进度的总长
            seekbarVideo.setMax(duration);

            //设置视频的总时间
            tv_video_duration.setText(stringForTime(duration));

            //发消息
            handler.sendEmptyMessage(1);
        }
    }
}
