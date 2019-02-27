package com.example.admin.musicclassroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.mFragment;

import org.xutils.x;


public class Fragment_Drawing_board extends mFragment {
    //画板
    private View views;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_drawing_board, null);
        x.view().inject(this, views);

        return views;
    }

}
