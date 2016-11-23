package com.example.twodgamedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/*继承View，基于x，y坐标为中心画一个实心圆，并能通过触摸来让实心圆以一定的速度移动*/
public class GameView extends View implements OnTouchListener{
	
	public float x;//圆心的当前 x坐标
	public float y;//圆心的当前y坐标
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context) {
		super(context);
		setOnTouchListener(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*在此处获得画布canvas*/
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		//绘制实心圆
		canvas.drawCircle(x, y, 10, paint);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//当发生触摸时间时，就开启一次线程在不同的xy坐标出画是实心圆
		System.out.println(event.getX()+"-----"+event.getY());
		DrawThread drawThread = new DrawThread(v, event.getX(), event.getY());
		drawThread.start();
		return false;
	}
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			((View) msg.obj).invalidate();//刷新canvas画布，重绘实心圆
			super.handleMessage(msg);
			
		};
	};
	class DrawThread extends Thread{
		private float newX,newY;//新的x，y的坐标值
		private View view;
		public DrawThread(View view,float newX,float newY){
			this.view = view;
			this.newX = newX;
			this.newY = newY;
		}
		@Override
		public void run() {
			/*由于触摸点到上一次实心圆的x坐标之间与y坐标之间的差值距离并不一定相等（newX与x的差值并不一定与newY与y的差值相等），
			 * 因此需要计算出横纵坐标移动的比例。*/
			float scale = Math.abs(newX-x)/Math.abs(newY-y);
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
					x+=scale;
				}else if(newX < x){//向左移动
					x-=scale;
				}
				
				if(newY >y){//向下移动
					y+=1;
				}
				else if(newY <y){//向上移动
					y-=1;
				}
				
				try {
					Message msg =new Message();
					msg.obj = view;
					mHandler.sendMessage(msg);
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}
}
