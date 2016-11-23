package com.example.myandroiddemo.locationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {

	private ConnectivityManager mConnectivityManager; 
	private NetworkInfo netInfo; 
	private TextView noticeTextView;
	@Override 
	 public void onReceive(Context context, Intent intent) { 
			String action = intent.getAction(); 
	        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) { 
	            mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
	            netInfo = mConnectivityManager.getActiveNetworkInfo();   
	            if(netInfo != null && netInfo.isAvailable()){ 
	                 //�������� 
	            	
	                String name = netInfo.getTypeName(); 
	                if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){ 
	                	//WiFi���� 
	                	Toast.makeText(context, "��������,��ǰ���ӵ���WIFI�������磡", 0).show();
	                }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){ 
	                	//�������� 
	                	Toast.makeText(context, "��������,��ǰ���ӵ����������磡", 0).show();
	                }else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){ 
	                	//3g���� 
	                	Toast.makeText(context, "��������,��ǰ���ӵ����ֻ����磡", 0).show();	
	                } 
	             }else { 
	            	  //����Ͽ� 
	            	 Toast.makeText(context, "����Ͽ����������������磡", 0).show();
	            } 
	        } 
	   
	   }  

}
