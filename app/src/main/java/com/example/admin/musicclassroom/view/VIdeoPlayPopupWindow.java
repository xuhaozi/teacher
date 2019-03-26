package com.example.admin.musicclassroom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.entity.TheoryVo;
import com.example.admin.musicclassroom.fragment.Fragment_Teaching_Video;
import com.example.admin.musicclassroom.variable.Variable;


/**
 * 視頻播放彈出層
 */
public class VIdeoPlayPopupWindow extends PopupWindow {

    private View mMenuView;
    private VideoView videoView;


    private TextView tv_title;
    private TextView tv_close;

    private Context context;


    @SuppressLint("InflateParams")
//    public VIdeoPlayPopupWindow(final Context context,TheoryVo theoryVo) {
    public VIdeoPlayPopupWindow(final Context context,String title,String uriStr) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_popupwindow_video, null);
        tv_title = (TextView) mMenuView.findViewById(R.id.tv_title);
        tv_close = (TextView) mMenuView.findViewById(R.id.tv_close);
        videoView = (VideoView) mMenuView.findViewById(R.id.videoView);

//        tv_title.setText(theoryVo.getVideoVoList().get(0).getVideoName());
//        Uri uri = Uri.parse(Variable.accessaddress_img+theoryVo.getVideoVoList().get(0).getVideo());
        tv_title.setText(title);
//        Uri uri = Uri.parse(Variable.accessaddress_img+uriStr);
//        //设置视频控制器
//        videoView.setMediaController(new MediaController(context));
//        //播放完成回调
//        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
//        //设置视频路径
//        videoView.setVideoURI(uri);
//        //开始播放视频
//        videoView.start();
        String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+uriStr ;
        videoView.setMediaController(new MediaController(context));
        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        videoView.setVideoPath(videoUrl1);
        //开始播放视频
        videoView.start();
        tv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int white = mMenuView.findViewById(R.id.pop_layout).getWidth();
                int y = (int) event.getY();
                int X = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || X < white || y < white || X < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public static class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }
}
