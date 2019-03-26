package com.example.admin.musicclassroom.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.admin.musicclassroom.R;


/**
 * 演示示范
 */
public class MaskPopupWindow extends PopupWindow {
    private View mMenuView;
    private ImageView iv_mask;

    int[] markImg = new int[]{R.drawable.mask_01, R.drawable.mask_02, R.drawable.mask_03, R.drawable.mask_04, R.drawable.mask_05, R.drawable.mask_06, R.drawable.mask_07, R.drawable.mask_08, R.drawable.mask_09, R.drawable.mask_10, R.drawable.mask_11};
    int Oncount = 1;

    private ImageView iv_left,iv_right,clear;
    @SuppressLint("InflateParams")
    public MaskPopupWindow(final Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_popupwindow_mask, null);
        iv_left = (ImageView) mMenuView.findViewById(R.id.iv_left);
        iv_right = (ImageView) mMenuView.findViewById(R.id.iv_right);
        clear = (ImageView) mMenuView.findViewById(R.id.clear);
        iv_mask = (ImageView) mMenuView.findViewById(R.id.iv_mask);
        iv_mask.setImageResource(markImg[0]);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Oncount != 0) {
                    Oncount--;
                    iv_mask.setImageResource(markImg[Oncount]);
                }
            }
        });

        iv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Oncount != 10) {
                    Oncount++;
                    iv_mask.setImageResource(markImg[Oncount]);
                } else {
                    dismiss();
                    Toast.makeText(context, "到底了", Toast.LENGTH_LONG).show();
                }
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
        ColorDrawable dw = new ColorDrawable(0x50000000);
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


}
