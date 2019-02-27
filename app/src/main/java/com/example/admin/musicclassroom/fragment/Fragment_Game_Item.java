package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.mFragment;

import org.xutils.x;


public class Fragment_Game_Item extends mFragment {
    //游戏
    private View views;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_game_item, null);
        x.view().inject(this, views);

        return views;
    }

}
