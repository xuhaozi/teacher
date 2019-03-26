package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.adapter.GridViewAdapteInstrumentalVIdeoList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.entity.MusicVo;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.MusicianVo;
import com.example.admin.musicclassroom.entity.VideoVo;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_Musician_details extends mFragment {
    //音乐家详情
    private View views;

    private MusicianVo musicianVo;

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


    @ViewInject(R.id.lv_music_data)
    private ListView lv_music_data;


    private ListViewAdapteMusicList listViewAdapteMusicList;
    private int demoFlag;
    private Long musicianId;
    private String strDemo;//本地数据标识
    public Fragment_Musician_details(Long musicianId, String str) {
        super();
        this.musicianId=musicianId;
        this.strDemo=str;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_musician_details;
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


    private void initDemo(){
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){
            if("国内".equals(strDemo)){
                List<MusicVo> musicVoList=new ArrayList<>();
                musicVoList.add(new MusicVo("《Rondino》","Rondino.mp3"));
                musicVoList.add(new MusicVo("《泰绮丝冥想曲》","泰绮丝冥想曲.mp3"));
                musicVoList.add(new MusicVo("《天鹅》","天鹅.mp3"));
                String str="马友友，1955年10月7日出生于法国巴黎，祖籍浙江鄞县（今宁波市鄞州区）。大提琴演奏者，毕业于哈佛大学、茱莉亚音乐学院。\n" +
                        "1959年，由父亲启蒙学习大提琴，并和家人迁居纽约。1962年，参加了为筹建华盛顿文化中心举行的巡回义演音乐会，美国总统肯尼迪夫妇出席晚会 。1971年，16岁的马友友在纽约卡内基音乐厅举行独奏音乐会。1976年，他从哈佛大学毕业，取得人类学学位。1985年，首次获得格莱美奖最佳乐器独奏奖（无交响乐团）。1991年，哈佛大学授予他荣誉博士学位。\n" +
                        "1998年，《马友友的巴赫灵感》问世 。1999年，与巴伦波因合作，和中东音乐家们组成的“中东青年管弦乐团”一起在德国威玛演出，同年获得顾尔德奖。\n" +
                        "2006年，时任联合国秘书长的安南任命马友友为联合国和平使者，同年获得唐大卫奖。2011年，美国总统奥巴马在白宫举行了授勋仪式，为马友友等颁发了代表美国平民最高荣誉的总统自由勋章。2017年2月13日，在美国洛杉矶第18次获得第59届格莱美奖“最佳世界音乐专辑”奖。";
                musicianVo=new MusicianVo("马友友",str,"file:///android_asset/马友友/马友友.jpg",musicVoList);
            }else if("国外".equals(strDemo)){
                List<MusicVo> musicVoList=new ArrayList<>();
                musicVoList.add(new MusicVo("《欢乐颂》","欢乐颂.mp3"));
                musicVoList.add(new MusicVo("《献给爱丽丝》","献给爱丽丝.mp3"));
                musicVoList.add(new MusicVo("《月光》","月光.mp3"));
                String str="   贝多芬（1770~1827）德国作曲家,伟大的音乐家之一。\n" +
                        "   出生于德国波恩的平民家庭，很早就显露了音乐才能，八岁开始登台演出。1792年到维也纳深造，艺术上进步飞快。贝多芬信仰共和，崇尚英雄，创作了有大量充满时代气息的优秀作品，如：交响曲《英雄》、《命运》；序曲《哀格蒙特》；钢琴奏鸣曲《悲怆》、《月光曲》、《暴风雨》、《热情》等等。一生坎坷，没有建立家庭。二十六岁时开始耳聋，晚年全聋，只能通过谈话册与人交谈。但孤寂的生活并没有使他沉默和隐退，在一切进步思想都遭禁止的封建复辟年代里，依然坚守“自由、平等”的政治信念，通过言论和作品，为共和理想奋臂呐喊，写下不朽名作《第九交响曲》。\n" +
                        "  \n" +
                        "  他的作品受十八世纪启蒙运动和德国狂飙突进运动的影响，个性鲜明，较前人有了很大的发展。在音乐表现上，他几乎涉及当时所有的音乐体裁；大大提高了钢琴的表现力，使之获得交响性的戏剧效果；又使交响曲成为直接反映社会变革的重要音乐形式。贝多芬集古典音乐的大成，同时开辟了浪漫时期音乐的道路，对世界音乐的发展有着举足轻重的作用，被尊称为“乐圣”。\n";
                musicianVo=new MusicianVo("贝多芬",str,"file:///android_asset/贝多芬/贝多芬.jpg",musicVoList);
            }
            InitView(musicianVo);
        }else {
            getmusicalInfo(musicianId+"");
        }
    }

    String str = "吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。";

    private void InitView(final MusicianVo musicianVo) {
        tv_Choice.setText(musicianVo.getMusicianName());
        tv_name.setText(musicianVo.getMusicianName());
//        tv_body.setText(musicalVo.getMusicalIntroduce());
        tv_body.setText(str);
        tv_body.setMovementMethod(ScrollingMovementMethod.getInstance());
        if(demoFlag==1){
            Glide.with(getActivity())
                    .load( musicianVo.getMusicianImage().toString())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(iv_instrumentalmusic_img);
        }else {
            Glide.with(getActivity())
                    .load(Variable.accessaddress_img + musicianVo.getMusicianImage().toString())
                    .placeholder(R.mipmap.icon_default_bg)
                    .crossFade()
                    .into(iv_instrumentalmusic_img);
        }

        listViewAdapteMusicList = new ListViewAdapteMusicList(getActivity(), musicianVo.getMusicVoList());
        lv_music_data.setAdapter(listViewAdapteMusicList);
        lv_music_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
                    if(demoFlag==1){
                        //播放 assets/a2.mp3 音乐文件
                        AssetFileDescriptor fd = getActivity().getAssets().openFd(musicianVo.getMusicVoList().get(i).getMusicMp3());
                        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                    }else {
                        mediaPlayer.setDataSource(Variable.accessaddress_img+musicianVo.getMusicVoList().get(i).getMusicMp3());
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
     * 获取音乐家详情
     */
    private void getmusicalInfo(String str) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token", Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("musicianId", str);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    musicianVo = new Gson().fromJson(obj.getString("data"), new TypeToken<MusicianVo>() {
                    }.getType());
                    if (musicianVo != null) {
                        InitView(musicianVo);
                    } else {
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
        finalHttp.post(Variable.address_musician_info, params, callBack);
    }


    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Appreciate_Musician();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }
}
