package com.example.myandroiddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.slidingmenu.ScreenUtils;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView
{
	/**
	 * 屏幕宽度 
	 */
	private int mScreenWidth;
	/**
	 * dp,菜单距离右边框的距离
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;
	private ViewGroup menu;
	private ViewGroup content;
	private boolean isOpen;

	private boolean once;

	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				//默认50 
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));//  默认为10DP
				System.out.println("mMenuRightPadding = "+mMenuRightPadding);
				System.out.println("mMenuRightPadding1= "+a.getDimensionPixelSize(attr, 0));
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//l 参数是 Current horizontal scroll origin：当前水平滑动的起始点
		super.onScrollChanged(l, t, oldl, oldt);
		 float scale = l * 1.0f / mMenuWidth;  
	        float leftScale = 1 - 0.3f * scale;  
	        float rightScale = 0.8f + scale * 0.2f;  
	        ViewHelper.setScaleX(menu, leftScale); //设置X缩放 
	        ViewHelper.setScaleY(menu, leftScale);  //设置Y缩放 
	        ViewHelper.setAlpha(menu, 0.001f + 0.999f * (1 - scale)); 
	        //设置背景图片的透明图
	        this.getBackground().setAlpha((int) ((0.001f + 0.999f * (1 - scale))*250));
	        ViewHelper.setTranslationX(menu, mMenuWidth * scale * 0.6f); //设置X方向的移动距离 （mMenuWidth * scale * 0.6f），动态变化
	        
	        
	        ViewHelper.setPivotX(content, 0);  //content 界面缩放的x中轴点
	        ViewHelper.setPivotY(content, content.getHeight() / 2);  //content 界面缩放的y中轴点 
	        ViewHelper.setScaleX(content, rightScale);  
	        ViewHelper.setScaleY(content, rightScale);  
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * 显示的设置一个宽度
		 */
		if (!once)
		{
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			 menu = (ViewGroup) wrapper.getChildAt(0);
			 content = (ViewGroup) wrapper.getChildAt(1);
			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			System.out.println("mHalfMenuWidth="+mHalfMenuWidth);
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			System.out.println("onLayout:mMenuWidth="+mMenuWidth);
			//当scrollTo()的传入参数为负的时候，view就向坐标轴正方向滚动；当为正的时候，view就向坐标轴负方向滚动
			this.scrollTo(mMenuWidth, 0);//将View滑动到x为mMenuWidth，y为0的位置，向左为正，向右为负，向上为正，向下为负
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单 
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * 切换菜单状态 
	 */
	public void toggle(String string)
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			if(string.equals("menu")){
				openMenu();
			}
				
		}
	}

}
