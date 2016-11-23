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

/*�̳�SurfaceView������x��y����Ϊ���Ļ�һ��ʵ��Բ������ͨ����������ʵ��Բ��һ�����ٶ��ƶ�*/
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
		//���Ƴ�ʼʵԲ��
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
		//��ȡcanvas����
		Canvas canvas = surfaceHolder.lockCanvas();
		//�����Ļ
		canvas.drawColor(Color.WHITE);
		canvas.drawCircle(x, y, 20, paint);
		
		//�ͷ�canvas����
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	class DrawThread extends Thread{
		private float newX,newY;//�µ�x��y������ֵ
		public DrawThread(float newX,float newY){
			this.newX = newX;
			this.newY = newY;
		}
		@Override
		public void run() {
			/*���ڴ����㵽��һ��ʵ��Բ��x����֮����y����֮��Ĳ�ֵ���벢��һ����ȣ�newX��x�Ĳ�ֵ����һ����newY��y�Ĳ�ֵ��ȣ���
			 * �����Ҫ��������������ƶ��ı�����*/
			float scale =Math.abs(newY-y)/ Math.abs(newX-x);
			//���˵scale = Math.abs(newY-y)/Math.abs(newX-x),��ô�����x�Ͳ�Ӧ�üӻ��߼�scale������1
			//y��Ӧ���Ǽ����߼�scale��
			/*Ϊʲô����x,y���ǼӼ�1��?������Ϊ��newX��x�Ĳ�ֵ����newY��y�Ĳ�ֵʱ,�ᷢ�ֵ����Ƕ��ǼӼ�1ʱ,����x,y
			 * ��ͬʱ��ʼ�仯,����y���Ѿ���newY�����,��x����newX�����,��ͻ���ʵ��Բ������newY������ȴû�е���newX������
			 * ,ͬ��newX��x�Ĳ�ֵС��newY��y�Ĳ�ֵʱ,��ﵽ�෴��Ч��
			 * 
			 * �����Լ�����һ��,�����ڷ���msg��Thread.sleepһ��ʱ������Ч��*/
			System.out.println("scale = "+scale);
			while(newX != x && newY != y){
				//�����ƶ�
				if(newX > x){
					x+=1;
				}else if(newX < x){//�����ƶ�
					x-=1;
				}
				
				if(newY >y){//�����ƶ�
					y+=scale;
				}
				else if(newY <y){//�����ƶ�
					y-=scale;
				}
			
					drawCirle();
				
			}
		}
	}
}
