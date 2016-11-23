package com.example.myandroiddemo;

import java.util.Arrays;
import java.util.LinkedList;

import com.example.cuetomview.CustomViewActivity;
import com.example.customview.mylistview.CustomListViewActivity;
import com.example.myandroiddemo.animationdaldialogeffects.AnimationEffectsDialogActivity;
import com.example.myandroiddemo.frame_animation.MyFrameAnimationDemo;
import com.example.myandroiddemo.gifplay.AndroidGifDemoActivity;
import com.example.myandroiddemo.gridviewdemo.MyGridViewDemoActivity;
import com.example.myandroiddemo.locationdemo.MyAppLocationActivity;
import com.example.myandroiddemo.notification.MyNotificationDemoActivity;
import com.example.myandroiddemo.progressbutton.ProgressButtonDemoActivity;
import com.example.myandroiddemo.servicedemo.MyAidlServiceClientActivity;
import com.example.myandroiddemo.servicedemo.MyServiceActivity;
import com.example.myandroiddemo.touch.TouchActivity;
import com.example.myandroiddemo.tween_animation.MyTweenAnimationDemo;
import com.example.slidingmenu.SildingMenuActivity;
import com.example.twodgamedemo.TwoDSurfaceViewAcitivity;
import com.example.twodgamedemo.TwoDViewActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/*前面四个功能完成时间： 2015.7.7*/
/*android:gravity　属性是对该view中内容的限定．比如一个button 上面的text. 
 * 你可以设置该text 相对于view的靠左，靠右等位置;
 * android:layout_gravity是用来设置该view相对与父view 的位置:
 * 比如一个button 在linearlayout里，你想把该button放在linearlayout里靠左、靠右等位置就可以通过该属性设置*/
public class MainActivity extends Activity {
	private String[] listStrings = {"APP Location定位","可以进行改动的progressButton","各种通知Demo(Notification)",
			"Gif 播放","各种动画效果的dialog","同时显示两个surfaceview的一种demo，类似于视屏通话时的两个窗口",
			"利用webservice.webxml.com.cn网站查询电话归属地","通过OntouchListener监听手势用Matrix实现图片的操作",
			"Service Demo","AIDL Service Demo","FrameAnimation Demo","TweenAnimation Demo",
			"GridView Demo","继承View 画一个触摸让实心圆以一定的速度移动","继承SurfaceView 画一个触摸让实心圆以一定的速度移动",
			"自定义组件ListView,可以通过手势来删除选中的一行","继承HorizontalScrollView的侧滑菜单",
			"各种自定义View","moreDemo"};
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinkedList<String> mLinkedList = new LinkedList<String>();
		mLinkedList.addAll(Arrays.asList(listStrings));
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mLinkedList);
		listView = new ListView(this);
		listView.setAdapter(arrayAdapter);
		setContentView(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position == 0){
					startActivity(MyAppLocationActivity.class);
				}else if(position ==1){
					startActivity(ProgressButtonDemoActivity.class);
				}else if(position ==2){
					startActivity(MyNotificationDemoActivity.class);
				}else if(position == 3){
					startActivity(AndroidGifDemoActivity.class);
					
				}else if(position ==4){
					startActivity(AnimationEffectsDialogActivity.class);
				}
				else if(position ==5){
					startActivity(TwoSurfaceViewActivity.class);
					
				}else if(position ==6){
					startActivity(MobileNumberBelongActivity.class);
				}else if(position == 7){
					startActivity(TouchActivity.class);
				}else if(position == 8){
					startActivity(MyServiceActivity.class);
				}else if(position ==9){
					startActivity(MyAidlServiceClientActivity.class);
				}
				else if(position ==10){
					startActivity(MyFrameAnimationDemo.class);
					
				}else if(position == 11){
					startActivity(MyTweenAnimationDemo.class);
					
				}else if(position == 12){
					startActivity(MyGridViewDemoActivity.class);
				}else if(position == 13){
					startActivity(TwoDViewActivity.class);
				}
				else if(position == 14){
					startActivity(TwoDSurfaceViewAcitivity.class);
				}else if(position == 15){
					startActivity(CustomListViewActivity.class);
				}
				else if(position == 16){
					startActivity(SildingMenuActivity.class);
				}
				else if(position == 17){
					startActivity(CustomViewActivity.class);
				}else if(position == 18){
					Toast.makeText(MainActivity.this, "更多demo后续更新！！", 3).show();
				}
			}
		});
	}
	private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
	 @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuInflater inflater = getMenuInflater();  
        //加载我们的菜单文件  
        inflater.inflate(R.menu.main, menu);  
        return true;  
    } 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings){
			//当我们点击 Settings 菜单的时候就会跳转到我们的  首选项视图，
            Intent intent = new Intent().setClass(this, SettingActivity.class);  
            //因为我们要接收上一个Activity 就是我们的首选项视图 返回的数据，所以这里用 startActivityForResult()方法启动我们的首选项视图  
            //参数一：我们要跳转到哪里  
            //参数二：回传码  
            this.startActivityForResult(intent, 0); 
		}
		return true;
	}
}
