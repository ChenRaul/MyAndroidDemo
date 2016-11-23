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
		 * ��ȡ�Զ������ʽ����,Ҳ�������get��set�ķ�����������Щ���Ե�ֵ
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
		/*�����ı��Ŀ�͸�*/
		mPaint.setColor(Color.BLACK);
//		System.out.println("getMeasuredWidth()="+getMeasuredWidth());
//		System.out.println("getMeasuredHeight()"+getMeasuredHeight());
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		/*�����ı�����*/
		mPaint.setColor(mTextColor);
//		System.out.println("getWidth()="+getWidth());
//		System.out.println("getHeight()="+getHeight());
		//mText.length()*mTextSize/ 2
		canvas.drawText(mText, getWidth() / 2 - mRect.width() / 2  , getHeight() / 2 + mRect.height() / 2,mPaint);
	}
	/*ϵͳ�����ǲ����ĸ߶ȺͿ�ȶ���MATCH_PARNET��������������ȷ�Ŀ�Ⱥ͸߶�ʱ��ϵͳ�����ǲ����Ľ�������������õĽ����
	 * ����������ΪWRAP_CONTENT,����MATCH_PARENTϵͳ�����ǲ����Ľ������MATCH_PARENT�ĳ��ȡ�
	*���ԣ���������WRAP_CONTENTʱ��������Ҫ�Լ����в���������дonMesure��������
	*��д֮ǰ���˽�MeasureSpec��specMode,һ���������ͣ�
	*EXACTLY��һ������������ȷ��ֵ������MATCH_PARENT ,����ȷ�ģ���ʾ��viewΪ��viewȷ����ȷ�ĳߴ�
	*AT_MOST����ʾ�Ӳ���������һ�����ֵ�ڣ�һ��ΪWARP_CONTENT,��view������ָ���ĳߴ��ھ�����
	*UNSPECIFIED����ʾ�Ӳ�����Ҫ���Ͷ�󣬺���ʹ��,δ�ӹ涨�ģ���ʾû�и���view����κι涨��*/
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
			  float textWidth = mRect.width(); //�ı���Ŀ��
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
