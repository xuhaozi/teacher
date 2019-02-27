package com.example.admin.musicclassroom.Utils;

/**
 * 音乐播放帮助类
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


import com.example.admin.musicclassroom.R;

import java.util.HashMap;

public class PanioMusic {
    // 资源文件
    int Music[] = {R.raw.white1, R.raw.white2, R.raw.white3, R.raw.white4, R.raw.white5,
            R.raw.white6, R.raw.white7,
            R.raw.white8, R.raw.white9, R.raw.white10, R.raw.white11, R.raw.white12,
            R.raw.white13, R.raw.white14,
            R.raw.white15, R.raw.white16, R.raw.white17, R.raw.white18, R.raw.white19,
            R.raw.white20, R.raw.white21,
            R.raw.white22, R.raw.white23, R.raw.white24, R.raw.white25, R.raw.white26,
            R.raw.white27, R.raw.white28,
            R.raw.white29, R.raw.white30, R.raw.white31, R.raw.white32, R.raw.white33,
            R.raw.white34, R.raw.white35,
            R.raw.white36, R.raw.white37, R.raw.white38, R.raw.white39, R.raw.white40,
            R.raw.white41, R.raw.white42,
            R.raw.white43, R.raw.white44, R.raw.white45, R.raw.white46, R.raw.white47,
            R.raw.white48, R.raw.white49,
            R.raw.white50, R.raw.white51, R.raw.white52
    };
    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;

    public PanioMusic(Context context) {
        soundPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < Music.length; i++) {
            soundPoolMap.put(i, soundPool.load(context, Music[i], 1));
        }
    }

    public int soundPlay(int no) {
        return soundPool.play(soundPoolMap.get(no), 100, 100, 0, 0, 1.0f);
    }

    public int soundOver() {
        return soundPool.play(soundPoolMap.get(1), 100, 100, 1, 0, 1.0f);
    }

    @Override
    protected void finalize() throws Throwable {
        soundPool.release();
        super.finalize();
    }
}

