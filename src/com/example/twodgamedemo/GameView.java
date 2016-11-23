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

/*�̳�View������x��y����Ϊ���Ļ�һ��ʵ��Բ������ͨ����������ʵ��Բ��һ�����ٶ��ƶ�*/
public class GameView extends View implements OnTouchListener{
	
	public float x;//Բ�ĵĵ�ǰ x����
	public float y;//Բ�ĵĵ�ǰy����
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
		/*�ڴ˴���û���canvas*/
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		//����ʵ��Բ
		canvas.drawCircle(x, y, 10, paint);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//����������ʱ��ʱ���Ϳ���һ���߳��ڲ�ͬ��xy���������ʵ��Բ
		System.out.println(event.getX()+"-----"+event.getY());
		DrawThread drawThread = new DrawThread(v, event.getX(), event.getY());
		drawThread.start();
		return false;
	}
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			((View) msg.obj).invalidate();//ˢ��canvas�������ػ�ʵ��Բ
			super.handleMessage(msg);
			
		};
	};
	class DrawThread extends Thread{
		private float newX,newY;//�µ�x��y������ֵ
		private View view;
		public DrawThread(View view,float newX,float newY){
			this.view = view;
			this.newX = newX;
			this.newY = newY;
		}
		@Override
		public void run() {
			/*���ڴ����㵽��һ��ʵ��Բ��x����֮����y����֮��Ĳ�ֵ���벢��һ����ȣ�newX��x�Ĳ�ֵ����һ����newY��y�Ĳ�ֵ��ȣ���
			 * �����Ҫ��������������ƶ��ı�����*/
			float scale = Math.abs(newX-x)/Math.abs(newY-y);
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
					x+=scale;
				}else if(newX < x){//�����ƶ�
					x-=scale;
				}
				
				if(newY >y){//�����ƶ�
					y+=1;
				}
				else if(newY <y){//�����ƶ�
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
