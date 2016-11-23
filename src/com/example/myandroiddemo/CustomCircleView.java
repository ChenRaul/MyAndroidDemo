package com.example.myandroiddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
/*Բ������ �ȴ�Ч��*/
public class CustomCircleView extends View {

	private int firstColor;//��һȦ����ɫ��Ĭ���Ǻ�ɫ
	private int secondColor;//�ڶ�Ȧ����ɫ��Ĭ���ǻ�ɫ
	private int speed;//��ԲȦ���ٶ�
	private int cirlceWidth;//ԲȦ�Ŀ��
	private Paint mPaint;
	protected int mProgress;
	protected boolean isNext =false;//�Ƿ�Ӧ�ÿ�ʼ��һ����ɫ��ԲȦ��

	public CustomCircleView(Context context) {
		this(context,null);
	}

	public CustomCircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomCircleView(Context context, AttributeSet attrs,int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView, defStyleAttr, 0);
		firstColor = ta.getColor(R.styleable.CustomCircleView_firstColor, Color.RED);
		secondColor = ta.getColor(R.styleable.CustomCircleView_secondColor, Color.YELLOW);
		speed = ta.getInt(R.styleable.CustomCircleView_speed, 20);
		cirlceWidth = ta.getDimensionPixelSize(R.styleable.CustomCircleView_circleWidth, 
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
		ta.recycle();
		System.out.println("cirlceWidth="+cirlceWidth);
		System.out.println("spee=d="+speed);
		mPaint = new Paint();
		//��ͼ�̣߳�������Ҫ�Զ����л�ԲȦ���һ�Ҫ�ڻ���һ�ܺ����ɫ���������߳���ѭ������������ִ�л�ͼ
		new Thread(){
			public void run() {
				while(true){
					mProgress++;
					if(mProgress == 360){
						mProgress = 0;
						if (!isNext)  
                            isNext = true;  
                        else  
                            isNext = false; 
					}
					postInvalidate();
					try {
						//ͨ���߳�������DrawArc��Բ�����ٶȣ�speedԽ���ٶȾ�Խ��
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	@Override
	protected void onDraw(Canvas canvas) {
//		System.out.println(getWidth());
		if(getWidth() <= 2*(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics())){
//			System.out.println("CustomCirView���õĿ��ֵ̫С��");
			return;
		}
		/*getWidth()�Ǳ�����Ŀ�����ԲȦ�İ뾶����ͨ��xml��layout_width������*/
		int centre = getWidth()/2;//��ȡԲ�ĵ�x����
		int radius = centre-cirlceWidth/2;//�뾶
		mPaint.setStrokeWidth(cirlceWidth);//���û���Բ���ߵĿ��
		mPaint.setAntiAlias(true);//�������
		mPaint.setStyle(Paint.Style.STROKE);//���ÿ���
		//����Բ������״�ʹ�С�Ľ��ޣ��˴�����Բ�������������ε�RectF�����޾�����Բ���İ뾶
		//public RectF (float left, float top, float right, float bottom) ,left:���ε���ߵ�X����
		RectF oval = new RectF(centre-radius, centre-radius, centre+radius, centre+radius);
		if(!isNext){
			//��һ����ɫ��Ȧ�������ģ���drawCircleֱ�ӻ��ꣻ
			//�ڶ�����ɫ�ſ�ʼʹ��drawArc����Բ��ת���ĽǶ�������ת���Ƕȵ��ٶ���Thread.sleep�����ƣ���˼��������˯һ���ֲż�����Բ������ȻԲ��һ˲��ͻ����ˣ�
			mPaint.setColor(firstColor);
			canvas.drawCircle(centre, centre, radius, mPaint);//����Բ��,����X,y����һ��
			mPaint.setColor(secondColor);
			canvas.drawArc(oval, -90, mProgress, false, mPaint);// ���ݽ��Ȼ�Բ��
		}else{
			//��һ����ɫ��Ȧ�������ģ���drawCircleֱ�ӻ��ꣻ�ڶ�����ɫ�ſ�ʼʹ��drawArc����Բ��ת���ĽǶ�����
			mPaint.setColor(secondColor);
			canvas.drawCircle(centre, centre, radius, mPaint);//����Բ��,����X,y����һ��
			mPaint.setColor(firstColor);
			canvas.drawArc(oval, -90, mProgress, false, mPaint);// ���ݽ��Ȼ�Բ��
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
}
