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
/*圆环交替 等待效果*/
public class CustomCircleView extends View {

	private int firstColor;//第一圈的颜色，默认是红色
	private int secondColor;//第二圈的颜色，默认是黄色
	private int speed;//画圆圈的速度
	private int cirlceWidth;//圆圈的宽度
	private Paint mPaint;
	protected int mProgress;
	protected boolean isNext =false;//是否应该开始换一个颜色画圆圈了

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
		//绘图线程，由于需要自动进行画圆圈，且还要在画满一周后变颜色，所以需线程死循环控制条件来执行绘图
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
						//通过线程来控制DrawArc画圆弧的速度，speed越大速度就越慢
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
//			System.out.println("CustomCirView设置的宽度值太小了");
			return;
		}
		/*getWidth()是本组件的宽，所以圆圈的半径可以通过xml的layout_width来设置*/
		int centre = getWidth()/2;//获取圆心的x坐标
		int radius = centre-cirlceWidth/2;//半径
		mPaint.setStrokeWidth(cirlceWidth);//设置画笔圆周线的宽度
		mPaint.setAntiAlias(true);//消除锯齿
		mPaint.setStyle(Paint.Style.STROKE);//设置空心
		//定义圆弧的形状和大小的界限，此处是正圆，所以是正方形的RectF，界限就是其圆弧的半径
		//public RectF (float left, float top, float right, float bottom) ,left:矩形的左边的X坐标
		RectF oval = new RectF(centre-radius, centre-radius, centre+radius, centre+radius);
		if(!isNext){
			//第一中颜色的圈是完整的，用drawCircle直接画完；
			//第二种颜色才开始使用drawArc根据圆弧转过的角度来画（转过角度的速度由Thread.sleep来控制，意思就是让它睡一会又才继续画圆弧，不然圆弧一瞬间就画完了）
			mPaint.setColor(firstColor);
			canvas.drawCircle(centre, centre, radius, mPaint);//画出圆环,所以X,y坐标一样
			mPaint.setColor(secondColor);
			canvas.drawArc(oval, -90, mProgress, false, mPaint);// 根据进度画圆弧
		}else{
			//第一中颜色的圈是完整的，用drawCircle直接画完；第二种颜色才开始使用drawArc根据圆弧转过的角度来画
			mPaint.setColor(secondColor);
			canvas.drawCircle(centre, centre, radius, mPaint);//画出圆环,所以X,y坐标一样
			mPaint.setColor(firstColor);
			canvas.drawArc(oval, -90, mProgress, false, mPaint);// 根据进度画圆弧
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
}
