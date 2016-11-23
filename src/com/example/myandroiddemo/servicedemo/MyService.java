package com.example.myandroiddemo.servicedemo;

import com.example.myandroiddemo.locationdemo.MyBroadCastReceiver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/*Service�޷����ⲿ���з���Ķ�̬�����������ʺ�����̨����
 * ����������(�û�ͨ��Intent����Url��Service���Ƽ�ʹ��IntentService).*/
public class MyService extends Service{
	
	private MyBinder mBinder = new MyBinder();//ͨ��Binder������service��activity��ͨ��
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("MyService Create");
	}
	/*���ڷ���ֵ onStartCommand���������flags��������START_STICKY��ʾ����ͨ����ʽ����������ֹͣ��
	 * ������START_NOT_STICKY or START_REDELIVER_INTENT��ʾ���������������
	 * ��������ʱ�Ŵ�������״̬��*/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("MyService onStartCommand");
		System.out.println(flags+"---"+startId);
		return super.onStartCommand(intent, flags, startId);
		
	}
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("MyService onUnbind");
		return super.onUnbind(intent);
	}
	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("MyService onRebind");
		super.onRebind(intent);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("MyService Destroy");
		super.onDestroy();
	}	
	/**
     * ���񱻰�ʱ����
     * ����ֵ�����õ����ߺͷ���ͨ�ţ�����ServiceConnection��
     * public void onServiceConnected(ComponentName name, IBinder service)�����ڶ�������
     */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("MyService onBind");
		return mBinder;
	}
	public void setText(){
		/*д����ķ�������*/
		System.out.println("MyService �Ĺ������� setText��������");//һ��Ҫȷ����������֮��������ڵ��øú���
	}
	class MyBinder extends Binder{
		/*Binder ������ķ������Ա�����������Binder���ã�
		 * ����˵������дһ������ ���ص�ǰservice�Ķ���ʵ�����Ա������ʹ�ã�
		 * �磺public MyService getService() {
          *  		return MyService.this;
        *	}
		 * �����Ϳ����õ����ߵ��ò���service�������һЩ�����������磺
		 * public void setText(){
		*		д����ķ�������
		*	}
		 * 
		 * 
		 * */
		public void startDownload(){
			System.out.println("��ʼִ����������");
		}
		public MyService getMyService(){
			System.out.println("��ȡMyServiceʵ��");
			return MyService.this;
		}
	}

}
