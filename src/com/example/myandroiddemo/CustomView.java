package com.example.myandroiddemo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.example.myandroiddemo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CustomView extends View{

	private String  mText;
	private int mTextColor;
	private Paint mPaint;
	private float mTextSize;
	private Rect mRect;

	public CustomView(Context context) {
		this(context,null);
		System.out.println("CustomView1");
	}
	public CustomView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		System.out.println("CustomView2");
	}
	
	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		System.out.println("CustomView3");
		/*
		 * 获取自定义的样式属性,也可以添加get和set的方法来设置这些属性的值
		 * */
		TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
		mText = ta.getString(R.styleable.CustomView_customText);
		mTextColor = ta.getColor(R.styleable.CustomView_customTextColor, Color.RED);
		mTextSize = ta.getDimensionPixelSize(R.styleable.CustomView_customTextSize, (int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
		System.out.println("mTextSize="+mTextSize);
		ta.recycle();
		mPaint = new Paint();  
        mRect = new Rect();
		 this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mText = randomText();  
                requestLayout();
			}
		});
	}
	private String randomText()
	{
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() <= random.nextInt(10))
		{
			int randomInt = random.nextInt(10);
			set.add(randomInt);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i : set)
		{
			sb.append("" + i);
		}
	//	System.out.println(sb.toString());
		return sb.toString();
	}
	@Override
	protected void onDraw(Canvas canvas) {
//		System.out.println("ondraw");
		/*绘制文本的宽和高*/
		mPaint.setColor(Color.BLACK);
//		System.out.println("getMeasuredWidth()="+getMeasuredWidth());
//		System.out.println("getMeasuredHeight()"+getMeasuredHeight());
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		/*绘制文本内容*/
		mPaint.setColor(mTextColor);
//		System.out.println("getWidth()="+getWidth());
//		System.out.println("getHeight()="+getHeight());
		//mText.length()*mTextSize/ 2
		canvas.drawText(mText, getWidth() / 2 - mRect.width() / 2  , getHeight() / 2 + mRect.height() / 2,mPaint);
	}
	/*系统帮我们测量的高度和宽度都是MATCH_PARNET，当我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果，
	 * 当我们设置为WRAP_CONTENT,或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
	*所以，当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法”：
	*重写之前先了解MeasureSpec的specMode,一共三种类型：
	*EXACTLY：一般是设置了明确的值或者是MATCH_PARENT ,，精确的，表示父view为子view确定精确的尺寸
	*AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT,子view可以在指定的尺寸内尽量大。
	*UNSPECIFIED：表示子布局想要多大就多大，很少使用,未加规定的，表示没有给子view添加任何规定。*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		System.out.println("onMeasure");
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightAize = MeasureSpec.getSize(heightMeasureSpec);
		int newWidth;
		int newHeight;
		mPaint.setTextSize(mTextSize);  
	    mPaint.getTextBounds(mText, 0, mText.length(), mRect); 
		if(widthMode == MeasureSpec.EXACTLY){
			newWidth = widthSize;
		}else{
			  float textWidth = mRect.width(); //文本框的宽度
//	        System.out.println(getPaddingLeft()+"----"+textWidth+"---"+getPaddingRight());
	        newWidth = (int) (getPaddingLeft() + textWidth + getPaddingRight()+mTextSize);  
		}
		if(heightMode == MeasureSpec.EXACTLY){
			newHeight = widthSize;
		}else{
	        float textHeight = mRect.height();  
//	        System.out.println(getPaddingTop()+"----"+textHeight+"---"+getPaddingBottom());
	        newHeight = (int) (getPaddingTop() + textHeight + getPaddingBottom()+mTextSize);  
		}
		setMeasuredDimension(newWidth, newHeight);  
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		
		super.onLayout(changed, left, top, right, bottom);
	}
}
