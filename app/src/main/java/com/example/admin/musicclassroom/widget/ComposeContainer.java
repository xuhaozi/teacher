package com.example.admin.musicclassroom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ComposeContainer extends android.support.v7.widget.AppCompatImageView{

	private Paint paint;
	private int width;

	public ComposeContainer(Context context) {
		super(context);
		init();
	}

	public ComposeContainer(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ComposeContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init(){
		paint=new Paint();
		paint.setColor(Color.RED );
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeWidth(5);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSpecMode= MeasureSpec.getMode(widthMeasureSpec);
		int heightSpecMode= MeasureSpec.getMode(heightMeasureSpec);
		int widthSpecSize= MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecSize= MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(measureView(widthSpecMode, widthSpecSize), measureView(heightSpecMode, heightSpecSize));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width=w;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 对当前控件的高和宽进行测量
	 *
	 * @param mode          测量模式
	 * @param widthOrHeight 测量宽或者高属性
	 * @return 返回测量结果
	 */
	private int measureView(int mode, int widthOrHeight) {
		int result;
		if (mode == MeasureSpec.EXACTLY) {
			result = widthOrHeight;
		} else {
			result = 400;
			if (mode == MeasureSpec.AT_MOST) {
				result = Math.min(result, widthOrHeight);
			}
		}
		return result;
	}
}
