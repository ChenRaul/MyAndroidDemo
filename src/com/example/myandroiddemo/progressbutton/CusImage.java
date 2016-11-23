
package com.example.myandroiddemo.progressbutton;

/**
 * @author Vyshakh, Rahul
 *
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
oval :指定圆弧的外轮廓矩形区域。
startAngle: 圆弧起始角度，单位为度。
sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
paint: 绘制圆弧的画板属性，如颜色，是否填充等。
本例演示了drawArc的四种不同用法,
1. 填充圆弧但不含圆心：
mPaints[0] = new Paint();
mPaints[0].setAntiAlias(true);
mPaints[0].setStyle(Paint.Style.FILL);
mPaints[0].setColor(0x88FF0000);
mUseCenters[0] = false;
2. 填充圆弧带圆心（扇形）
mPaints[1] = new Paint(mPaints[0]);
mPaints[1].setColor(0x8800FF00);
mUseCenters[1] = true;
3. 只绘圆周，不含圆心
mPaints[2] = new Paint(mPaints[0]);
mPaints[2].setStyle(Paint.Style.STROKE);
mPaints[2].setStrokeWidth(4);
mPaints[2].setColor(0x880000FF);
mUseCenters[2] = false;
4. 只绘圆周，带圆心（扇形）
mPaints[3] = new Paint(mPaints[2]);
mPaints[3].setColor(0x88888888);
mUseCenters[3] = true;*/
public class CusImage extends View {

	private Paint myPaint;
	private Paint myFramePaint;
	public TextView value;
	private float startAngle ;
	public float temp;
	float sweepAngle;
	private int flag = 0;
	RectF rect;
	private MasterLayout m;
	int pix = 0;

	public CusImage(Context context, AttributeSet attrs, MasterLayout m) {
		super(context, attrs);
		this.m = m;
		init();
	}

	public CusImage(Context context, MasterLayout m) {
		super(context);
		this.m = m;
		init();
	}

	private void init() {

		myPaint = new Paint();
		DisplayMetrics metrics = getContext().getResources()
				.getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		float scarea = width * height;
		pix = (int) Math.sqrt(scarea * 0.0217);

		myPaint.setAntiAlias(true);
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setColor(Color.rgb(0, 161, 234));  //Edit this to change progress arc color.
		myPaint.setStrokeWidth(7);

		myFramePaint = new Paint();
		myFramePaint.setAntiAlias(true);
		myFramePaint.setColor(Color.TRANSPARENT);

		float startx = (float) (pix * 0.05);
		float endx = (float) (pix * 0.95);
		float starty = (float) (pix * 0.05);
		float endy = (float) (pix * 0.95);
		rect = new RectF(startx, starty, endx, endy);//RectF是画矩形的，
		//RectF(float left, float top, float right, float bottom)
	}

	public void setupprogress(int progress) {

		//Updating progress arc 
		
		sweepAngle = (float) (progress*3.6);
		System.out.println("setupprogress: sweepAngle ="+sweepAngle);
	}

	public void reset() {

		//Resetting progress arc
		
		sweepAngle = 0;
		startAngle = -90;
		flag = 1;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int desiredWidth = pix;
		int desiredHeight = pix;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		
		if (widthMode == MeasureSpec.EXACTLY) {
			
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			
			width = Math.min(desiredWidth, widthSize);
		} else {
			
			width = desiredWidth;
		}

		
		if (heightMode == MeasureSpec.EXACTLY) {
			
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			
			height = Math.min(desiredHeight, heightSize);
		} else {
			
			height = desiredHeight;
		}

		
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		/*public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
		oval :指定圆弧的外轮廓矩形区域。
		startAngle: 圆弧起始角度，单位为度。
		sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
		useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
		paint: 绘制圆弧的画板属性，如颜色，是否填充等。*/
		
		canvas.drawArc(rect, startAngle, sweepAngle, false, myPaint);
		startAngle = -90;
		System.out.println("flag ="+flag);
		if (sweepAngle < 360 && flag == 0) {
			System.out.println("startAngle = "+ startAngle+"sweepAngle="+sweepAngle);
			invalidate();

		} else if (flag == 1) {

			sweepAngle = 0;
			startAngle = -90;
			flag = 0;
			invalidate();
		} else {

			sweepAngle = 0;
			startAngle = -90;
			m.finalAnimation();

		}
	}
}
