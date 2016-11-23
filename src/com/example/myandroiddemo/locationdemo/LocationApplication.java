package com.example.myandroiddemo.locationdemo;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * ��Application
 */
public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	
	public TextView mLocationResult,logMsg;
	public TextView trigger,exit,minfo;
	public View mView1;
	public View mView2;
	public Vibrator mVibrator;
	public Button mbutton;
	private String latitude;
	private String longitude;
	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		
		
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
	}

	
	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			latitude = String.valueOf(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			longitude = String.valueOf(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			System.out.println(location.getLocType()+BDLocation.TypeGpsLocation+BDLocation.TypeNetWorkLocation);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}


	}
	
	
	/**
	 * ��ʾ�����ַ���
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null){
				mLocationResult.setText(str);
			}
			if(mView1 != null){
				mView1.setVisibility(View.VISIBLE);
			}
			if(mView2 != null){
				mView2.setVisibility(View.VISIBLE);
			}
			if(mbutton != null){
				mbutton.setVisibility(View.VISIBLE);
				mbutton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Uri uri =Uri.parse("geo:"+longitude+","+latitude);
						Intent it = new Intent(Intent.ACTION_VIEW,uri);
						//Context����һ��startActivity������Activity�̳���Context��
//						������startActivity���������ʹ�� Activity��startActivity������
//						�������κ����ƣ������ʹ��Context��startActivity�����Ļ�������Ҫ����һ���µ�task��
//						��������Ϊʹ����Context��startActivity������
//						����취�ǣ���һ��flag��intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
						//��������������ͻᱨ�쳣�����ִ���
						it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(it);
					}
				});
			}
			if(minfo != null){
				minfo.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �߾��ȵ���Χ���ص�
	 * @author jpren
	 *
	 */
	
}
