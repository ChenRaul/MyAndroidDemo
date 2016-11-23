package com.example.myandroiddemo;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @see 查询电话的归属地
 * */
public class MobileNumberBelongActivity extends Activity implements OnClickListener{
    
	Button submitBtn ;
	EditText numberEt;
	TextView resultTv;
	String result = null;
	Handler myHandler;
	LinearLayout ll;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number);
        
        findViews();
        
        myHandler =new MyHandler();
    }

	private void findViews() {
		numberEt = (EditText) this.findViewById(R.id.mobile);
		submitBtn = (Button) this.findViewById(R.id.button);
		resultTv = (TextView) this.findViewById(R.id.result);
		ll = (LinearLayout) findViewById(R.id.number_ll);
		submitBtn.setOnClickListener(this);
	}

	//监听，判断并得到数据
	public void onClick(View v) {
		final String PhoneNumber = numberEt.getText().toString().trim();
		//ScreentShotUtil.getInstance().takeScreenshot(this, null);截屏需要root权限
		System.out.println(PhoneNumber);
		if(!"".equals(PhoneNumber)){
			new MyThread(PhoneNumber, this).start();
		}
	}
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				resultTv.setText(Html.fromHtml((String) msg.obj));
				break;
			default:
				break;
			}
		}
	}
	class MyThread extends Thread{
		private String string;
		private Context context;
		public MyThread(String PhoneNumber,Context context){
			this.string = PhoneNumber;
			this.context=context;
		}
		@Override
		public void run() {
			try {
				result = MobileService.getLocation(string,context);
				if(result != null){
					Message message = new Message();
					message.what = 1;
					message.obj = result;
					myHandler.sendMessage(message);
				}else{
					Message message = new Message();
					message.what = 1;
					message.obj = "没有找到此号码";
					myHandler.sendMessage(message);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
				System.out.println(e.toString());
			}
		}
	}
}