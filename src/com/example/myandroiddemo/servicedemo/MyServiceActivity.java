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
         * �������ڽ��̱�kill����crashʱϵͳ���ã�������unbindServiceʱ����
         */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			System.out.println("Service onServiceDisconnected");
		}
		 /**
         * ��������ʱ���ã����Ѿ����Ӳ����е���
         * 
         * ����bindService(myServiceIntent, con, Context.BIND_AUTO_CREATE)
         *�󶨷��񣬰󶨳ɹ��󷵻�true,��ʱ��ִ��onServiceConnected����
         */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			System.out.println("Service onServiceConnected");
			myBinder = (MyBinder) service; //��ȡbinder�󣬾Ϳ���ͨ����binder������service��������ԡ�������
			myService =myBinder.getMyService();//��ȡMyServiceʵ����������bindSevice���óɹ��󣬲Ż���ø÷������������ʵ���ǿ϶����ڵ�
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
		bt1.setText("��������");
		bt2.setText("�رշ���");
		bt3.setText("Bind����");
		bt4.setText("unBind����");
		bt5.setText("����IntentService");
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
			/*������������һ����־λ�����ﴫ��BIND_AUTO_CREATE��ʾ��Activity��Service�����������Զ�����Service��
			���ʹ��MyService�е�onCreate()�����õ�ִ��(ǰ����û��ִ��startService����)����onStartCommand()��������ִ��*/
			bindService(bindIntent, con, BIND_AUTO_CREATE);
			/*��startService������Service��ͬ��������ͨ��bindService��������û��ͨ��startService������
			 * �������ӶϿ�ʱ����ͻ��Զ����(onUnbind)����ֹ(onDestroy)�����ڵ�����(Activity)�˳�����Զ��Ͽ�
			 * ���ӣ�������ʱ������Լ������ֹ��������ĳ��������˸÷�������ø÷����stopService����ֹͣ����*/
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
