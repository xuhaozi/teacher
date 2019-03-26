package com.example.admin.musicclassroom.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
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
import com.example.admin.musicclassroom.adapter.GridViewAdapteMusicAllList;
import com.example.admin.musicclassroom.adapter.ListViewAdapteMusicList;
import com.example.admin.musicclassroom.entity.MusicVo;
import com.example.admin.musicclassroom.entity.MusicalVo;
import com.example.admin.musicclassroom.entity.VideoVo;
import com.example.admin.musicclassroom.mFragment;
import com.example.admin.musicclassroom.variable.Variable;
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

@SuppressLint("ValidFragment")
public class Fragment_Instrumental_details extends mFragment {
    //器乐详情
    private View views;

    private MusicalVo musicalVo;

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

    private Long musicalId;
    private String strDemo;//本地数据标识
    private int demoFlag;
    public Fragment_Instrumental_details(Long musicalId, String str) {
        super();
        this.musicalId=musicalId;
        this.strDemo=str;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_instrumental_details;
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
        SharedPreferences musicData = getContext().getSharedPreferences("MusicData", Context.MODE_PRIVATE);
        demoFlag= musicData.getInt("demo", 0);
        if(demoFlag==1){//获取本地数据
            if("国内".equals(strDemo)){
                List<VideoVo> videoVoList=new ArrayList<>();
                List<MusicVo> musicVoList=new ArrayList<>();
                videoVoList.add(new VideoVo("《铃鼓》","/test/祝你圣诞快乐/铃鼓/铃鼓视频/铃鼓演奏.mp4"));
                musicVoList.add(new MusicVo("《Songbird》","Songbird.mp3"));
                musicVoList.add(new MusicVo("《幸福的铃鼓》","幸福的铃鼓.mp3"));
                String str="铃鼓是维吾尔、朝鲜、乌孜别克、塔吉克等族的打击乐器，流行于新疆维吾尔自治区及吉林延边等地，鼓框木制，单面蒙皮，有大、中、小三种。用于歌舞或器乐合奏。 铃鼓又称“手鼓”，无论在民间舞蹈或乐队伴奏中，铃鼓都是一种色彩性很强的节奏打击乐器，可用作伴奏、伴舞和伴歌，节奏自由，任凭演奏者即兴发挥。\n" +
                        "铃鼓有大、中、小三种规格，鼓框高4―4.7厘米，鼓框厚1.2―1.3厘米，鼓面直径20―25厘米。塔吉克族\n" +
                        "的铃鼓，形制独特，多用旧筛子边框制作，还蒙以狼皮或牛皮，框内装3对小铁钹，发音低沉浑厚。\n" +
                        "演奏时，多用左手持鼓，以右手手指或手掌击奏。摇动鼓身，可使小钹同时作响。多用于歌唱或舞蹈伴奏，也可用于器乐合奏。";
                musicalVo=new MusicalVo("铃鼓","file:///android_asset/祝你圣诞快乐/铃鼓/铃鼓.png","铃鼓.mp3",str,musicVoList,videoVoList);
            }else if("国外".equals(strDemo)){
                List<VideoVo> videoVoList=new ArrayList<>();
                List<MusicVo> musicVoList=new ArrayList<>();
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
                musicalVo=new MusicalVo("钢琴","file:///android_asset/布谷/钢琴/钢琴.jpg","钢琴.mp3",str,musicVoList,videoVoList);
            }
            InitView(musicalVo);
        }else {//获取网络数据
            getmusicalInfo(musicalId);
        }
    }
    String str="吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。吉他在流行音乐、摇滚音乐、蓝调、民歌、佛朗明哥中，常被视为主要乐器。而在古典音乐的领域里，吉他常以独奏或二重奏的型式演出；当然，在室内乐和管弦乐中，吉他亦扮演着相当程度的陪衬角色。 古典吉他与小提琴、钢琴并列为世界著名三大乐器。";
    private void InitView(final MusicalVo musicalVo){
        tv_Choice.setText(musicalVo.getMusicalName());
        tv_name.setText(musicalVo.getMusicalName());
//        tv_body.setText(musicalVo.getMusicalIntroduce());
        tv_body.setText(musicalVo.getMusicalIntroduce());
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
                showMessage(Variable.accessaddress_img+musicalVo.getVideoVoList().get(i).getVideo());
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
     * 通过国家获取音乐风格
     */
    private void getmusicalInfo(Long musicalId) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.addHeader("token",  Variable.loginInfoVo.getData());
        AjaxParams params = new AjaxParams();
        params.put("musicalId",musicalId+"");
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String content) {
                try {
                    JSONObject obj = new JSONObject(content);
                    musicalVo = new Gson().fromJson(obj.getString("data"), new TypeToken<MusicalVo>() {
                    }.getType());
                    if (musicalVo != null) {
                        InitView(musicalVo);
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
        finalHttp.post(Variable.address_musical_info, params, callBack);
    }




    //返回上一级列表
    @Event(value = R.id.ll_return, type = View.OnClickListener.class)
    private void ll_returnClick(View v) {
        Fragment videoFragment = new Fragment_Appreciate_Instrumental();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.video_fragment, videoFragment).commit();
    }
}
