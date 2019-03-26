package com.example.admin.musicclassroom.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.mFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class Fragment_Music_score extends mFragment {
    //谱子畫板
    private View views;
    @ViewInject(R.id.iv_image)
    private ImageView iv_image;

    @ViewInject(R.id.iv_white)
    private ImageView iv_white;
    @ViewInject(R.id.iv_yellow)
    private ImageView iv_yellow;
    @ViewInject(R.id.iv_red)
    private ImageView iv_red;
    @ViewInject(R.id.iv_blue_tow)
    private ImageView iv_blue_tow;
    @ViewInject(R.id.iv_blue)
    private ImageView iv_blue;

    @ViewInject(R.id.iv_clear)
    private ImageView iv_clear;

    @ViewInject(R.id.iv_left)
    private ImageView iv_left;
    @ViewInject(R.id.iv_right)
    private ImageView iv_right;


    private Bitmap copyBitmap;
    private Paint paint;
    private Canvas canvas;
    private float startX;
    private float startY;


    @Override
    protected int setContentView() {
        return R.layout.fragment_music_score;
    }

    @Override
    protected void init() {
        views=rootView;
        x.view().inject(this, views);
        initview();
    }

    @Override
    protected void lazyLoad() {

    }


    public void initview(){
        //使用Bitmap工厂把图片加载进来
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_music_score);
        //创建一个空的图片，宽度和高度 还有信息跟原图片一样
        copyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //创建画笔
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        //创建一个画布
        canvas = new Canvas(copyBitmap);
        //开始画画
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        iv_image.setImageBitmap(copyBitmap);
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取动作的事件
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        //按下事件
                        startX = event.getX();
                        startY = event.getY();
                        Log.e("按下", startX + "," + startY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //滑动事件
                        float x = event.getX();
                        float y = event.getY();
                        //在画布上画直线，不能画点，滑动事件获得的坐标不是连续的
                        canvas.drawLine(startX, startY, x, y, paint);
                        //更新图片
                        iv_image.setImageBitmap(copyBitmap);
                        startX = x;
                        startY = y;
                        Log.e("滑动", x + "," + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起事件
                        float upX = event.getX();
                        float upY = event.getY();
                        Log.e("抬起", upX + "," + upY);
                        break;
                }
                //必须设置为true，否则只执行按下事件
                return true;
            }
        });

    }



    //画笔白色
    @Event(value = R.id.iv_white, type = View.OnClickListener.class)
    private void paintColor_white_Click(View v) {
        paint.setColor(Color.WHITE);
    }
    //画笔黄色
    @Event(value = R.id.iv_yellow, type = View.OnClickListener.class)
    private void paintColor_yellow_Click(View v) {
        paint.setColor(Color.YELLOW);
    }
    //画笔红色
    @Event(value = R.id.iv_red, type = View.OnClickListener.class)
    private void paintColor_red_Click(View v) {
        paint.setColor(Color.RED);
    }
    //画笔蓝色
    @Event(value = R.id.iv_blue_tow, type = View.OnClickListener.class)
    private void paintColor_bluetow_Click(View v) {
        paint.setColor(Color.BLUE);
    }
    //画笔深蓝色
    @Event(value = R.id.iv_blue, type = View.OnClickListener.class)
    private void paintColor_blue_Click(View v) {
        paint.setColor(Color.BLUE);
    }
    //橡皮擦
    @Event(value = R.id.iv_clear, type = View.OnClickListener.class)
    private void iv_clearClick(View v) {
        initview();
    }

    //后退
    @Event(value = R.id.iv_left, type = View.OnClickListener.class)
    private void iv_leftClick(View v) {

        showMessage("上一步");
    }
    //前进
    @Event(value = R.id.iv_right, type = View.OnClickListener.class)
    private void iv_rightClick(View v) {
        showMessage("下一步");
    }

}
