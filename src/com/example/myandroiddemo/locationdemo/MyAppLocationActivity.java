package com.example.myandroiddemo.locationdemo;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.myandroiddemo.R;
import com.example.myandroiddemo.locationdemo.MyBroadCastReceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyAppLocationActivity extends Activity{

	private LocationManager locationManager;
	private LocationClient mLocationClient;
	private TextView LocationResult;
	private TextView noticeTextView,info;
	private MyBroadCastReceiver myBroadCastReceiver;
	private View view1,view2;//����view�����൱���������ߣ���ϸ��layout xml
	private Button button;
	private boolean isRunThread = true;
	MyHandler myHandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myapp_location);
		
		noticeTextView = (TextView) findViewById(R.id.textView2);
		//ע����������㲥
		myBroadCastReceiver = new MyBroadCastReceiver();
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadCastReceiver, mFilter);
        
        myHandler= new MyHandler();
		//�ٶȶ�λ
		mLocationClient = ((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mLocationClient;
		//�����������ǽ�λ����Ϣ��ʾ���ƶ���textView�ϣ�����mLocationResult��LocationApplication(���Ǽ̳���Application��)������public
		//���Կ����ڴ˴�������ֵ�����˴���textView1�����ù�ȥ����ȻҲ����ֱ�ӽ�LocationApplication���еĹ�����Щȫ��д�����
		//�����Ҳ��һ����ֻ�����������õ�Ϊ���˴����������Ķ����������textView button �������������෴��
		//�����buttonҲ�����,���ַ�ʽ�൱����������ͬ�ļ�����������ͬһ�����view�����ַ�ʽ���Խ��������
		//�ؼ�Ӧ������һ��Ҫ���õ����Ǽ̳��� Application
		//������ô���ã��������ļ�������Ҫ���õ���Щ���� �������ж���ʱ������Ĭ�ϵĻ���public�����ͣ�
		//����ͬ�˴������õ� mView1 mView2 mbutton minfo mLocationClient����LocationApplication�ж���public
	
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view1.setVisibility(View.GONE);
		view2.setVisibility(View.GONE);
		((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mView1 =view1;
		((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mView2 =view2;
		
		LocationResult = (TextView)findViewById(R.id.textView1);
		((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mLocationResult = LocationResult;
		
		button =(Button) findViewById(R.id.button);
		((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mbutton = button;
		button.setVisibility(View.GONE);
		
		info = (TextView) findViewById(R.id.info);
		info.setVisibility(View.GONE);
		((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).minfo = info;
		
		//android �Դ��Ķ�λ����
		locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if (gpsEnabled && isInternetConnected()) {//���GPS�����綼������������ֱ��ʹ�ðٶȵĸ߾��ȶ�λ
	    	InitBaiDuLocation(LocationMode.Hight_Accuracy);
	    	mLocationClient.start();
	    	noticeTextView.setText("��ʾ��GPS������������ʹ�ø߾��ȶ�λ��");
	    }else if(gpsEnabled && !isInternetConnected()){//���GPS�������������Ӳ���������ֱ��ʹ���Դ���GPS��λ����ȻҲ����ʹ�ðٶȵ�GPS��λ
//	    	InitBaiDuLocation(LocationMode.Device_Sensors);
//	    	mLocationClient.start();
	    	mLocationClient.stop();
	    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, listener);
	    	noticeTextView.setText("��ʾ��GPS���������������ӣ�ʹ��GPS��λ��");
	    }else if(!gpsEnabled && isInternetConnected()){//���GPS������������������������ֱ��ʹ�ðٶȵ����綨λ,���͹���
	    	new AlertDialog.Builder(this).setMessage("�������Ա��GPS����ȡ���õľ���").setTitle("��ʾ��")
			.setPositiveButton("ȷ��", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					 startActivity(settingsIntent);
					 finish();
				}
			}).setNegativeButton("��,ֻʹ�����綨λ", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					InitBaiDuLocation(LocationMode.Battery_Saving);
			    	mLocationClient.start();
			    	noticeTextView.setText("��ʾ��GPS�رգ��������ӣ�ʹ�����綨λ��");
				}
			}).show();
	    }else{//��������
	    	LocationResult.setText("��λʧ�ܣ���������ý��棬��GPS������������!Ȼ������������ж�λ");
	    }    	
		new Thread(){ 	
			@Override
			public void run() {
				while(isRunThread){
					if(!isInternetConnected() && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
						Message message = new Message();
			    		message.what =2;
			    		myHandler.sendMessage(message);
					}
				}
			}
		}.start();
	}
	@Override
	protected void onStart() {
	    super.onStart();
	  
	}
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Toast.makeText(MyAppLocationActivity.this, (CharSequence) msg.obj, 1).show();
				LocationResult.setText((CharSequence) msg.obj);
				break;
			case 2:
				
				noticeTextView.setText("��ʾ��GPS�Ѿ��رգ�����Ͽ������GPS���������������������Ȼ����ж�λ��");
				noticeTextView.setTextColor(Color.RED);
				break;
			default:
				break;
			}
			
		}
	}
	private void InitBaiDuLocation(LocationMode locationMode){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(locationMode);//���ö�λģʽ,�и߾��ȡ��͹��ġ�������GPS��ѡ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ�ȣ������֣�gcJ02 ��bd0911�� bd09 ���꣬	
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);//�����Ƿ񷵻�  ��������룬trueΪ��
		mLocationClient.setLocOption(option);
	}
	private boolean isInternetConnected(){
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
	    if (cm == null) {   
	    	
	    } else {
	    	//��������������ж���������
	    	//�����ʹ�� cm.getActiveNetworkInfo().isAvailable();  
	        NetworkInfo[] info = cm.getAllNetworkInfo();   
	        if (info != null) {   
	            for (int i = 0; i < info.length; i++) {   
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
	                    return true;   
	                }   
	            }   
	        }   
	   	}   
	   	return false;   
	} 
	private  LocationListener listener = new LocationListener() {

	    @Override
	    public void onLocationChanged(Location location) {
	        // A new location update is received.  Do something useful with it.  In this case,
	        // we're sending the update to a handler which then updates the UI with the new
	        // location.
	    	System.out.println("onLocationChanged");
	    		
	    		Message message = new Message();
	    		message.what =1;
	    		message.obj = "���ȣ�"+location.getLatitude()+"γ�ȣ�"+location.getLongitude()+"���Σ�"+location.getAltitude();
	    		myHandler.sendMessage(message);
	    }

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderEnabled");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderDisabled");
			
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunThread =false;
		mLocationClient.stop();
		unregisterReceiver(myBroadCastReceiver);
	}
}
