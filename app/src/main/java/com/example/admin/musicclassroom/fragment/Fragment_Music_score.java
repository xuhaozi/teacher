package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.mFragment;

import org.xutils.x;


public class Fragment_Music_score extends mFragment {
    //谱子
    private View views;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_music_score, null);
        x.view().inject(this, views);

        return views;
    }

}
