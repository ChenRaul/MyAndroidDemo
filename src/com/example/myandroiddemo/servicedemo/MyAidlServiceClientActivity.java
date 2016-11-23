package com.example.myandroiddemo.servicedemo;

import com.example.myandroiddemo.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyAidlServiceClientActivity extends Activity implements OnClickListener {

	private IMyService iMyService = null;
	private Button btn_bind;
	private Button btn_unbind;
	private TextView tv_show;
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//获得AIDL服务对象
			iMyService = IMyService.Stub.asInterface(service);
			btn_unbind.setEnabled(true);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_aidl_service);
		btn_bind = (Button) findViewById(R.id.bt_AIDL_bind);
		btn_unbind = (Button) findViewById(R.id.bt_AIDL_unBind);
		tv_show = (TextView) findViewById(R.id.tv_AIDL_show);
		btn_unbind.setEnabled(false);
		btn_bind.setOnClickListener(this);
		btn_unbind.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_bind){
			bindService(new Intent("com.example.myandroiddemo.servicedemo.IMyService"), serviceConnection, Context.BIND_AUTO_CREATE);
		}else{
			try {
				tv_show.setText(iMyService.getValue());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//stopService(new Intent(this, MyAidlService.class));
		}
	}
}
