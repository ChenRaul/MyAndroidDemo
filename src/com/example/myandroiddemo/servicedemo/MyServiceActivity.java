package com.example.myandroiddemo.servicedemo;

import com.example.myandroiddemo.R;
import com.example.myandroiddemo.servicedemo.MyService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyServiceActivity extends Activity implements OnClickListener{

	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private Button bt5;
	private MyBinder myBinder;
	private MyService myService;
	private ServiceConnection con = new ServiceConnection() {
		/**
         * 服务所在进程被kill或是crash时系统调用，而不是unbindService时调用
         */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			System.out.println("Service onServiceDisconnected");
		}
		 /**
         * 服务连接时调用，若已经连接不进行调用
         * 
         * 调用bindService(myServiceIntent, con, Context.BIND_AUTO_CREATE)
         *绑定服务，绑定成功后返回true,这时会执行onServiceConnected函数
         */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			System.out.println("Service onServiceConnected");
			myBinder = (MyBinder) service; //获取binder后，就可以通过此binder来操作service里面的属性、方法等
			myService =myBinder.getMyService();//获取MyService实例对象，由于bindSevice调用成功后，才会调用该方法，所以这个实例是肯定存在的
			myService.setText();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myservice);
		
		bt1 = (Button) findViewById(R.id.bt1);
		bt2 = (Button) findViewById(R.id.bt2);
		bt3 = (Button) findViewById(R.id.bt3);
		bt4 = (Button) findViewById(R.id.bt4);
		bt5 = (Button) findViewById(R.id.bt5);
		bt1.setText("开启服务");
		bt2.setText("关闭服务");
		bt3.setText("Bind服务");
		bt4.setText("unBind服务");
		bt5.setText("开启IntentService");
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt1:
			Intent startIntent = new Intent(this, MyService.class);  
	        startService(startIntent); 
			break;
		case R.id.bt2:
			Intent stopIntent = new Intent(this, MyService.class);  
	        stopService(stopIntent);
			break;
		case R.id.bt3:
			Intent bindIntent = new Intent(this, MyService.class);  
			/*第三个参数是一个标志位，这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service，
			这会使得MyService中的onCreate()方法得到执行(前提是没有执行startService方法)，但onStartCommand()方法不会执行*/
			bindService(bindIntent, con, BIND_AUTO_CREATE);
			/*与startService启动的Service不同，若服务通过bindService启动并且没有通过startService启动，
			 * 则在连接断开时服务就会自动解绑(onUnbind)并终止(onDestroy)，而在调用者(Activity)退出后会自动断开
			 * 连接，所以这时服务会自己解绑并终止。若存在某个组件绑定了该服务，则调用该服务的stopService不会停止服务。*/
			//startService(bindIntent);
			break;
		case R.id.bt4:
			
			if (myService != null) {
				myService.setText();
			    unbindService(con);
			    myService = null;
			}
			break;
		case R.id.bt5:
		 Intent myIntentServiceIntent = new Intent(this, MyIntentService.class);
		 startService(myIntentServiceIntent);
		 break;
		default:
			break;
		}
	}
	
}
