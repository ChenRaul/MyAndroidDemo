package com.example.myandroiddemo.servicedemo;

import com.example.myandroiddemo.locationdemo.MyBroadCastReceiver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/*Service无法与外部进行方便的动态交互，所以适合做后台服务，
 * 如网络下载(用户通过Intent传入Url到Service，推荐使用IntentService).*/
public class MyService extends Service{
	
	private MyBinder mBinder = new MyBinder();//通过Binder来建立service与activity的通信
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("MyService Create");
	}
	/*对于返回值 onStartCommand方法里面的flags，若返回START_STICKY表示服务通过显式调用启动或停止，
	 * 若返回START_NOT_STICKY or START_REDELIVER_INTENT表示服务仅在有请求发送
	 * 过来处理时才处于运行状态。*/
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
     * 服务被绑定时调用
     * 返回值用于让调用者和服务通信，传入ServiceConnection的
     * public void onServiceConnected(ComponentName name, IBinder service)函数第二个参数
     */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("MyService onBind");
		return mBinder;
	}
	public void setText(){
		/*写具体的方法内容*/
		System.out.println("MyService 的公共方法 setText（）调用");//一定要确保服务建立了之后调用者在调用该函数
	}
	class MyBinder extends Binder{
		/*Binder 类里面的方法可以被调用者利用Binder调用，
		 * 比如说还可以写一个方法 返回当前service的对象实例，以便调用者使用，
		 * 如：public MyService getService() {
          *  		return MyService.this;
        *	}
		 * 这样就可以让调用者调用操作service类里面的一些公共方法。如：
		 * public void setText(){
		*		写具体的方法内容
		*	}
		 * 
		 * 
		 * */
		public void startDownload(){
			System.out.println("开始执行下载任务！");
		}
		public MyService getMyService(){
			System.out.println("获取MyService实例");
			return MyService.this;
		}
	}

}
