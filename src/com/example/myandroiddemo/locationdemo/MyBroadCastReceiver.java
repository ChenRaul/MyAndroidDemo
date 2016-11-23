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
	                 //网络连接 
	            	
	                String name = netInfo.getTypeName(); 
	                if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){ 
	                	//WiFi网络 
	                	Toast.makeText(context, "网络连接,当前连接的是WIFI无线网络！", 0).show();
	                }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){ 
	                	//有线网络 
	                	Toast.makeText(context, "网络连接,当前连接的是有线网络！", 0).show();
	                }else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){ 
	                	//3g网络 
	                	Toast.makeText(context, "网络连接,当前连接的是手机网络！", 0).show();	
	                } 
	             }else { 
	            	  //网络断开 
	            	 Toast.makeText(context, "网络断开，请重新设置网络！", 0).show();
	            } 
	        } 
	   
	   }  

}
