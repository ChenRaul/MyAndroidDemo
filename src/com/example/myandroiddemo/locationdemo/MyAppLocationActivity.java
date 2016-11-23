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
	private View view1,view2;//两个view，就相当于两个横线，详细看layout xml
	private Button button;
	private boolean isRunThread = true;
	MyHandler myHandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myapp_location);
		
		noticeTextView = (TextView) findViewById(R.id.textView2);
		//注册网络监听广播
		myBroadCastReceiver = new MyBroadCastReceiver();
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadCastReceiver, mFilter);
        
        myHandler= new MyHandler();
		//百度定位
		mLocationClient = ((com.example.myandroiddemo.locationdemo.LocationApplication)getApplication()).mLocationClient;
		//下面这两句是将位置信息显示在制定的textView上，由于mLocationResult在LocationApplication(它是继承了Application类)类中是public
		//所以可以在此处给它赋值，将此处的textView1给引用过去，当然也可以直接将LocationApplication类中的功能这些全部写在这里，
		//上面的也是一样，只不过上面引用的为：此处引用它处的对象，与下面的textView button 被它处引用是相反的
		//后面的button也是如此,这种方式相当于在两个不同文件的类中引用同一个组件view，这种方式可以借鉴！！！
		//关键应该是另一个要引用的类是继承了 Application
		//不管怎么引用，被其它文件的类中要引用的这些对象 ，在类中定义时必须是默认的或者public的类型，
		//就如同此处被引用的 mView1 mView2 mbutton minfo mLocationClient在类LocationApplication中都是public
	
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
		
		//android 自带的定位管理
		locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if (gpsEnabled && isInternetConnected()) {//如果GPS和网络都连接正常，则直接使用百度的高精度定位
	    	InitBaiDuLocation(LocationMode.Hight_Accuracy);
	    	mLocationClient.start();
	    	noticeTextView.setText("提示：GPS和网络正常，使用高精度定位！");
	    }else if(gpsEnabled && !isInternetConnected()){//如果GPS正常，网络连接不正常，则直接使用自带的GPS定位，当然也可以使用百度的GPS地位
//	    	InitBaiDuLocation(LocationMode.Device_Sensors);
//	    	mLocationClient.start();
	    	mLocationClient.stop();
	    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, listener);
	    	noticeTextView.setText("提示：GPS正常，网络无连接，使用GPS定位！");
	    }else if(!gpsEnabled && isInternetConnected()){//如果GPS不正常，网络连接正常，则直接使用百度的网络定位,即低功耗
	    	new AlertDialog.Builder(this).setMessage("打开设置以便打开GPS，获取更好的精度").setTitle("提示：")
			.setPositiveButton("确认", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					 startActivity(settingsIntent);
					 finish();
				}
			}).setNegativeButton("否,只使用网络定位", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					InitBaiDuLocation(LocationMode.Battery_Saving);
			    	mLocationClient.start();
			    	noticeTextView.setText("提示：GPS关闭，网络连接，使用网络定位！");
				}
			}).show();
	    }else{//都不正常
	    	LocationResult.setText("定位失败！！请打开设置界面，打开GPS或者连接网络!然后重启程序进行定位");
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
				
				noticeTextView.setText("提示：GPS已经关闭，网络断开，请打开GPS或者网络后，重新启动程序，然后进行定位！");
				noticeTextView.setTextColor(Color.RED);
				break;
			default:
				break;
			}
			
		}
	}
	private void InitBaiDuLocation(LocationMode locationMode){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(locationMode);//设置定位模式,有高精度、低功耗、仅依靠GPS可选
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，有三种：gcJ02 、bd0911、 bd09 坐标，	
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//设置是否返回  反地理编码，true为是
		mLocationClient.setLocOption(option);
	}
	private boolean isInternetConnected(){
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);   
	    if (cm == null) {   
	    	
	    } else {
	    	//如果仅仅是用来判断网络连接
	    	//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
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
	    		message.obj = "经度："+location.getLatitude()+"纬度："+location.getLongitude()+"海拔："+location.getAltitude();
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
