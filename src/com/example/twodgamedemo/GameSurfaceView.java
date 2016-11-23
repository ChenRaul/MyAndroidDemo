package com.example.twodgamedemo;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/*继承SurfaceView，基于x，y坐标为中心画一个实心圆，并能通过触摸来让实心圆以一定的速度移动*/
public class GameSurfaceView extends SurfaceView implements Callback, OnTouchListener{

	public float x,y;
	private SurfaceHolder surfaceHolder;
	Paint paint;
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameSurfaceView(Context context) {
		super(context);
		setOnTouchListener(this);
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		 paint = new Paint();
		paint.setColor(Color.RED);
		this.setFocusable(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//绘制初始实圆心
		System.out.println(this.getHeight()+"-----------"+this.getWidth());
		drawCirle();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		System.out.println(height+"-----"+width);
		System.out.println(this.getHeight()+"-----------"+this.getWidth());
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		DrawThread drawThread = new DrawThread(event.getX(), event.getY());
		drawThread.start();
		return false;
	}
	public void drawCirle(){
		//获取canvas对象
		Canvas canvas = surfaceHolder.lockCanvas();
		//清空屏幕
		canvas.drawColor(Color.WHITE);
		canvas.drawCircle(x, y, 20, paint);
		
		//释放canvas对象
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	class DrawThread extends Thread{
		private float newX,newY;//新的x，y的坐标值
		public DrawThread(float newX,float newY){
			this.newX = newX;
			this.newY = newY;
		}
		@Override
		public void run() {
			/*由于触摸点到上一次实心圆的x坐标之间与y坐标之间的差值距离并不一定相等（newX与x的差值并不一定与newY与y的差值相等），
			 * 因此需要计算出横纵坐标移动的比例。*/
			float scale =Math.abs(newY-y)/ Math.abs(newX-x);
			//如果说scale = Math.abs(newY-y)/Math.abs(newX-x),那么下面的x就不应该加或者减scale，而是1
			//y则应该是减或者加scale。
			/*为什么不是x,y都是加减1呢?这是因为当newX与x的差值大于newY与y的差值时,会发现当他们都是加减1时,由于x,y
			 * 是同时开始变化,导致y都已经和newY相等了,而x还与newX不相等,这就会让实心圆到达了newY的坐标却没有到达newX的坐标
			 * ,同理当newX与x的差值小于newY与y的差值时,会达到相反的效果
			 * 
			 * 可以自己试验一下,可以在发送msg后Thread.sleep一点时间来看效果*/
			System.out.println("scale = "+scale);
			while(newX != x && newY != y){
				//向右移动
				if(newX > x){
					x+=1;
				}else if(newX < x){//向左移动
					x-=1;
				}
				
				if(newY >y){//向下移动
					y+=scale;
				}
				else if(newY <y){//向上移动
					y-=scale;
				}
			
					drawCirle();
				
			}
		}
	}
}
