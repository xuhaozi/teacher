package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
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
import android.widget.AdapterView;
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
import com.example.admin.musicclassroom.adapter.GridViewAdapteInstrumentalVIdeoList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteTheoryListItem;
import com.example.admin.musicclassroom.entity.MusicVo;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
import com.example.admin.musicclassroom.view.CustomMediaPlayerAssertFolder;
import com.example.admin.musicclassroom.view.VIdeoPlayPopupWindow;
import com.example.admin.musicclassroom.widget.HvListView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.content.Context.AUDIO_SERVICE;

@SuppressLint("ValidFragment")
public class Fragment_Teaching_MusicalInstruments extends mFragment {
    //器乐
    private View views;

    private List<MusicalVo> musicalVo;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_body)
    private TextView tv_body;
    @ViewInject(R.id.iv_instrumentalmusic_img)
    private ImageView iv_instrumentalmusic_img;


    @ViewInject(R.id.tv_Choice)
    private TextView tv_Choice;
    @ViewInject(R.id.ll_return)
    private LinearLayout ll_return;


    @ViewInject(R.id.gv_video_list)
    private MyGridView gv_video_list;


    @ViewInject(R.id.lv_music_data)
    private ListView lv_music_data;


    private ListViewAdapteMusicList listViewAdapteMusicList;
    private GridViewAdapteInstrumentalVIdeoList gridViewAdapteInstrumentalVIdeoList;
    private VIdeoPlayPopupWindow vIdeoPlayPopupWindow=null;
    private int demoFlag;
    private Long courseId;
    private int position;
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


    public Fragment_Teaching_MusicalInstruments(Long courseId, int position) {
        super();
        this.courseId=courseId;
        this.position=position;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_teaching_musicallinstruments;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        initDemo();
    }

    @Override
    protected void lazyLoad() {

    }

    private void initDemo() {
        //获取演示标识
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        Log.i("demoFlag",demoFlag+"");
        if(demoFlag==1){
            musicalVo=new ArrayList<>();
            List<VideoVo> videoVoList=new ArrayList<>();
            List<MusicVo> musicVoList=new ArrayList<>();
            if(position==0){
                musicalVo.clear();
                videoVoList.clear();
                musicVoList.clear();
                videoVoList.add(new VideoVo("沙槌","/test/小雨沙沙沙/沙槌/沙槌视频/沙锤.mp4"));
                musicVoList.add(new MusicVo("沙槌","file:///android_asset/小雨沙沙沙/沙槌/沙槌乐曲/沙槌.mp3"));
                String str="沙锤是摇奏体鸣乐器，亦称沙球。起源于南美印第安人的节奏性打击乐器。传统沙槌用一个球形干葫芦，内装一些干硬的种子粒或碎石子，以葫芦原有细长颈部为柄，摇动时硬粒撞击葫芦壁发声。也有木制、陶制、藤编和塑料制等形状类似的沙槌，内装珠子、铅丸等物。通常双手各持一只摇。多用密封的椰子壳制成，内装沙粒，两个一组。沙槌有木把，演奏时左右手各握一把，双手交替上下晃动，奏出各种节奏音型。沙槌发音清脆而略带沙沙声，多用于演奏有特殊风格的舞曲。 沙槌为非固定音高乐器，用一线记谱。沙槌在拉丁美洲各国有多种形制，巴西有用马口铁制作的，以两个截头圆锥体的大口互对而成的沙槌；还有哑铃状的双头沙槌和十字形多头沙槌等。\n";
                musicalVo.add(new MusicalVo("沙槌","file:///android_asset/小雨沙沙沙/沙槌/沙槌.jpg","沙槌.mp3",str,musicVoList,videoVoList));
            }else if(position==1){
                musicalVo.clear();
                videoVoList.clear();
                musicVoList.clear();
                videoVoList.add(new VideoVo("《Despacito》","/test/布谷/钢琴/钢琴视频/Despacito.mp4"));
                videoVoList.add(new VideoVo("《风之谷》","/test/布谷/钢琴/钢琴视频/风之谷.mp4"));
                videoVoList.add(new VideoVo("《加勒比海盗》","/test/布谷/钢琴/钢琴视频/加勒比海盗.mp4"));
                videoVoList.add(new VideoVo("《舒伯特_即兴曲》","/test/布谷/钢琴/钢琴视频/舒伯特_即兴曲.mp4"));
                musicVoList.add(new MusicVo("《克罗地亚狂想曲》","克罗地亚狂想曲.mp3"));
                musicVoList.add(new MusicVo("《鸟之诗》","鸟之诗.mp3"));
                musicVoList.add(new MusicVo("《千与千寻》","千与千寻.mp3"));
                String str="钢琴（pianoforte）是西洋古典音乐中的一种键盘乐器，由88个琴键（52个白键，36个黑键）和金属弦音板组成。意大利人克利斯托弗利（BartolomeoCristofori，1655-1731）在1709年发明了钢琴。钢琴第一次用于独奏乐器是在1768年J.C.在英国的一次演出。现代钢琴因形状和体积的不同，主要分为立式钢琴和三角钢琴。音乐会所用的大三角钢琴是乐器中的庞然大物，有9英尺长，最重的可达79吨。迄今为止最昂贵的钢琴是一架1888年生产的斯坦威（steinway）牌三角钢琴，1980年在纽约以18万英镑的高价被拍卖。\n" +
                        "钢琴因其独特的音响，88个琴键的全音域，历来受到作曲家的钟爱。在流行、摇滚、爵士以及古典等几乎所有的音乐形式中都扮演了重要角色。\n" +
                        "钢琴的起源，最早可追溯到古埃及与古西腊的弦什(一弦琴)。将弦什的琴弦不断增加，逐渐形成了多弦乐器。进而多弦乐器又演变成两种演奏形式的乐器。一是以手指拨动琴弦发音的多弦乐器。后与键盘结合成为拨弦古钢琴。另一种是以手指拨动琴键，装置于键尾的小槌击弦发音的古钢琴。这两种乐器都是现代钢琴的鼻祖，故统称之为古钢琴。 \n";
                musicalVo.add(new MusicalVo("钢琴","file:///android_asset/布谷/钢琴/钢琴.jpg","钢琴.mp3",str,musicVoList,videoVoList));
            }else if(position==2){
                musicalVo.clear();
                videoVoList.clear();
                musicVoList.clear();
                videoVoList.add(new VideoVo("《带你去旅行》","/test/祝你圣诞快乐/尤克里里/尤克里里视频/带你去旅行.mp4"));
                videoVoList.add(new VideoVo("《天空之城》","/test/祝你圣诞快乐/尤克里里/尤克里里视频/天空之城.mp4"));
                videoVoList.add(new VideoVo("《小情歌》","/test/祝你圣诞快乐/尤克里里/尤克里里视频/小情歌.mp4"));
                videoVoList.add(new VideoVo("《西游记》","/test/祝你圣诞快乐/尤克里里/尤克里里视频/西游记.mp4"));
                musicVoList.add(new MusicVo("《Summer》","Summer.mp3"));
                musicVoList.add(new MusicVo("《卡农》","卡农.mp3"));
                musicVoList.add(new MusicVo("《同桌的你》","同桌的你.mp3"));
                String str="Ukulele即夏威夷小吉他，在港台等地一般译作乌克丽丽，在大陆一般习惯称为尤克里里，是一种四弦夏威夷的拨弦乐器，发明于葡萄牙盛行于夏威夷，归属在吉他乐器一族。在1915年首届巴拿马太平洋万国博览会期间，夏威夷馆便是以吉他和尤克里里合奏为特色，让乔治E· K·Awai的皇家夏威夷Quartette，与尤克里里制作者以及球员约拿书Kumalae一起表演。合奏让尤克里里琴进入美国。 尤克里里为了像爵士乐一样年龄的音乐产物。因为它的高度便携式和相对地低廉价格，在整个20年代，引发了一股风潮。在尤克里里的制造商之中，马丁增加了一种豪华和谐的尤克里里， banjolele和 tiple 利用之为需求的生产。民间有一说法，这是一个适合大人及儿童，并且好听可爱，又能激发节奏潜能的乐器。只要它在手中，没有你不会弹的歌。\n";
                musicalVo.add(new MusicalVo("尤克里里","file:///android_asset/祝你圣诞快乐/尤克里里/尤克里里.jpg","尤克里里.mp3",str,musicVoList,videoVoList));
            }
            getDemoList();
        }else {
            GetMusictheoryList();
        }
    }

    String str="吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。";
    private void InitView(final MusicalVo musicalVo){
        tv_Choice.setText(musicalVo.getMusicalName());
        tv_name.setText(musicalVo.getMusicalName());
//        tv_body.setText(musicalVo.getMusicalIntroduce());
        tv_body.setText(str);
        tv_body.setMovementMethod(ScrollingMovementMethod.getInstance());
        if(demoFlag==1){
            Glide.with(getActivity())
                    .load( musicalVo.getMusicalImage().toString())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(iv_instrumentalmusic_img);
        }else {
            Glide.with(getActivity())
                    .load(Variable.accessaddress_img + musicalVo.getMusicalImage().toString())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(iv_instrumentalmusic_img);
        }

        gridViewAdapteInstrumentalVIdeoList=new GridViewAdapteInstrumentalVIdeoList(getActivity(),musicalVo.getVideoVoList());
        gv_video_list.setAdapter(gridViewAdapteInstrumentalVIdeoList);
        gv_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                vIdeoPlayPopupWindow = new VIdeoPlayPopupWindow(getActivity(),musicalVo.getVideoVoList().get(i).getVideoName(),musicalVo.getVideoVoList().get(i).getVideo());
//                vIdeoPlayPopupWindow.showAtLocation(views, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                setDialog(musicalVo,i);

            }
        });
        listViewAdapteMusicList=new ListViewAdapteMusicList(getActivity(),musicalVo.getMusicVoList());
        lv_music_data.setAdapter(listViewAdapteMusicList);
        lv_music_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
                    if(demoFlag==1){
                        //播放 assets/a2.mp3 音乐文件
                        AssetFileDescriptor fd = getActivity().getAssets().openFd(musicalVo.getMusicVoList().get(i).getMusicMp3());
                        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                    }else {
                        mediaPlayer.setDataSource(Variable.accessaddress_img+musicalVo.getMusicVoList().get(i).getMusicMp3());
                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();
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
            }
        });
    }
    /**
     * 设置视频Dialog
     */
    private void setDialog(MusicalVo musicalVo, int i) {
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
        tv_title.setText(musicalVo.getVideoVoList().get(i).getVideoName());
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
            String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+musicalVo.getVideoVoList().get(i).getVideo();
            //设置视频路径
            videoView.setVideoPath(videoUrl1);
        }else {
            Uri uri = Uri.parse(Variable.accessaddress_img+musicalVo.getVideoVoList().get(i).getVideo());
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
        ivVideoPause.setImageResource(R.mipmap.videw_btn_start);

        seekbarVideo.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());
    }

    /**
     * 获取乐器列表
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
                    musicalVo = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MusicalVo>>() {
                    }.getType());
                    if (musicalVo != null) {
                        InitView(musicalVo.get(0));
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
        finalHttp.post(Variable.address_musical, params, callBack);
    }

    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Teaching_Item();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }

    public void getDemoList() {
        InitView(musicalVo.get(0));
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
}
