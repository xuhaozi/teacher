package com.example.admin.musicclassroom.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.fragment.Fragment_Appreciate_Item;
import com.example.admin.musicclassroom.fragment.Fragment_Drawingboard_Item;
import com.example.admin.musicclassroom.fragment.Fragment_Game_Item;
import com.example.admin.musicclassroom.fragment.Fragment_Musictheory_Item;
import com.example.admin.musicclassroom.fragment.Fragment_My_Item;
import com.example.admin.musicclassroom.fragment.Fragment_Piano_Item;
import com.example.admin.musicclassroom.fragment.Fragment_Teaching_Item;
import com.example.admin.musicclassroom.musicentity.beans.ScorePartWise;
import com.example.admin.musicclassroom.widget.MusicView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @ViewInject(R.id.ll_teaching)
    private LinearLayout ll_teaching;
    @ViewInject(R.id.ll_piano)
    private LinearLayout ll_piano;
    @ViewInject(R.id.ll_musictheory)
    private LinearLayout ll_musictheory;
    @ViewInject(R.id.ll_appreciate)
    private LinearLayout ll_appreciate;
    @ViewInject(R.id.ll_drawingboard)
    private LinearLayout ll_drawingboard;
    @ViewInject(R.id.ll_game)
    private LinearLayout ll_game;

    @ViewInject(R.id.ll_touxiang)
    private LinearLayout ll_touxiang;




    @ViewInject(R.id.iv_teaching)
    private ImageView iv_teaching;
    @ViewInject(R.id.iv_piano)
    private ImageView iv_piano;
    @ViewInject(R.id.iv_musictheory)
    private ImageView iv_musictheory;
    @ViewInject(R.id.iv_appreciate)
    private ImageView iv_appreciate;
    @ViewInject(R.id.iv_drawingboard)
    private ImageView iv_drawingboard;
    @ViewInject(R.id.iv_game)
    private ImageView iv_game;


    @ViewInject(R.id.iv_touxiang)
    private ImageView iv_touxiang;

    @ViewInject(R.id.fl_body)
    private FrameLayout fl_body;

    @ViewInject(R.id.music_view)
    private MusicView musicView;

    private ScorePartWise scorePartWise;

    private Fragment mContent, fragment_Teaching_Item, fragment_Piano_Item, fragment_Musictheory_Item, fragment_Appreciate_Item, fragment_Drawingboard_Item,fragment_Game_Item,fragment_My_Item;//首页模块中的碎片
    private FragmentManager fm;
    private FragmentTransaction ft;


    private ImageView[] arr_img;// 图标的数组，用于文字高亮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "checkPermission: 已经授权！");
        }
        InitViews();
        changeCurrBtn(0);

    }


    //初始化fragment
    private void InitViews() {
        arr_img = new ImageView[7];
        arr_img[0] = iv_teaching;
        arr_img[1] = iv_piano;
        arr_img[2] = iv_musictheory;
        arr_img[3] = iv_appreciate;
        arr_img[4] = iv_drawingboard;
        arr_img[5] = iv_game;
        arr_img[6] = iv_touxiang;

        fragment_Teaching_Item = new Fragment_Teaching_Item();
        fragment_Piano_Item = new Fragment_Piano_Item();
        fragment_Musictheory_Item = new Fragment_Musictheory_Item();
        fragment_Appreciate_Item = new Fragment_Appreciate_Item();
        fragment_Drawingboard_Item = new Fragment_Drawingboard_Item();
        fragment_Game_Item = new Fragment_Game_Item();
        fragment_My_Item=new Fragment_My_Item();
        setDefaultFragment(fragment_Teaching_Item);
    }


    //切换fragment的显示隐藏
    public void switchContent(Fragment to) {
        if (mContent != to) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(mContent).add(R.id.fl_body, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }


    public void setDefaultFragment(Fragment fragment) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fl_body, fragment).commit();
        mContent = fragment;
    }

    // 高亮按钮
    private void changeCurrBtn(int index) {
        // 5个文字都变色
        for (int i = 0; i < arr_img.length; i++) {
            UpdataImgState(index);
        }
        // 哪个高亮
        UpdataImgState(index);
    }


    public void UpdataImgState(int index) {
        if (index == 0) {
            arr_img[index].setImageResource(R.mipmap.icon_teaching_blue);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        } else if (index == 1) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[index].setImageResource(R.mipmap.icon_piano_blue);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        } else if (index == 2) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[index].setImageResource(R.mipmap.icon_musictheory_blue);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        } else if (index == 3) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[index].setImageResource(R.mipmap.icon_appreciate_blue);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        } else if (index == 4) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[index].setImageResource(R.mipmap.icon_drawingboard_blue);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        }
        else if (index == 5) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[index].setImageResource(R.mipmap.icon_game_blue);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang);
        }
        else if (index == 6) {
            arr_img[0].setImageResource(R.mipmap.icon_teaching);
            arr_img[1].setImageResource(R.mipmap.icon_piano);
            arr_img[2].setImageResource(R.mipmap.icon_musictheory);
            arr_img[3].setImageResource(R.mipmap.icon_appreciate);
            arr_img[4].setImageResource(R.mipmap.icon_drawingboard);
            arr_img[5].setImageResource(R.mipmap.icon_game);
            arr_img[6].setImageResource(R.mipmap.icon_touxiang_blue);
        }
    }



    //教学
    @Event(value = R.id.ll_teaching, type = View.OnClickListener.class)
    private void ll_teaching_gerenClick(View v) {

        changeCurrBtn(0);
        switchContent(fragment_Teaching_Item);
    }

    //钢琴
    @Event(value = R.id.ll_piano, type = View.OnClickListener.class)
    private void ll_piano_gerenClick(View v) {
        changeCurrBtn(1);
        switchContent(fragment_Piano_Item);

    }
    //乐理
    @Event(value = R.id.ll_musictheory, type = View.OnClickListener.class)
    private void ll_musictheory_gerenClick(View v) {
        changeCurrBtn(2);
        switchContent(fragment_Musictheory_Item);
    }
    //鉴赏
    @Event(value = R.id.ll_appreciate, type = View.OnClickListener.class)
    private void ll_appreciate_gerenClick(View v) {
        changeCurrBtn(3);
        switchContent(fragment_Appreciate_Item);
    }
    //画板
    @Event(value = R.id.ll_drawingboard, type = View.OnClickListener.class)
    private void ll_drawingboard_gerenClick(View v) {
        changeCurrBtn(4);
        switchContent(fragment_Drawingboard_Item);
    }
    //游戏
    @Event(value = R.id.ll_game, type = View.OnClickListener.class)
    private void ll_game_gerenClick(View v) {
        changeCurrBtn(5);
        switchContent(fragment_Game_Item);
    }


    //个人中心
    @Event(value = R.id.ll_touxiang, type = View.OnClickListener.class)
    private void ll_touxiang_gerenClick(View v) {
        changeCurrBtn(6);
        switchContent(fragment_My_Item);
    }



    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
    };
    boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
