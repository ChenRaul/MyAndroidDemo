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

/*ǰ���ĸ��������ʱ�䣺 2015.7.7*/
/*android:gravity�������ǶԸ�view�����ݵ��޶�������һ��button �����text. 
 * ��������ø�text �����view�Ŀ��󣬿��ҵ�λ��;
 * android:layout_gravity���������ø�view����븸view ��λ��:
 * ����һ��button ��linearlayout�����Ѹ�button����linearlayout�￿�󡢿��ҵ�λ�þͿ���ͨ������������*/
public class MainActivity extends Activity {
	private String[] listStrings = {"APP Location��λ","���Խ��иĶ���progressButton","����֪ͨDemo(Notification)",
			"Gif ����","���ֶ���Ч����dialog","ͬʱ��ʾ����surfaceview��һ��demo������������ͨ��ʱ����������",
			"����webservice.webxml.com.cn��վ��ѯ�绰������","ͨ��OntouchListener����������Matrixʵ��ͼƬ�Ĳ���",
			"Service Demo","AIDL Service Demo","FrameAnimation Demo","TweenAnimation Demo",
			"GridView Demo","�̳�View ��һ��������ʵ��Բ��һ�����ٶ��ƶ�","�̳�SurfaceView ��һ��������ʵ��Բ��һ�����ٶ��ƶ�",
			"�Զ������ListView,����ͨ��������ɾ��ѡ�е�һ��","�̳�HorizontalScrollView�Ĳ໬�˵�",
			"�����Զ���View","moreDemo"};
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
					Toast.makeText(MainActivity.this, "����demo�������£���", 3).show();
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
        //�������ǵĲ˵��ļ�  
        inflater.inflate(R.menu.main, menu);  
        return true;  
    } 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings){
			//�����ǵ�� Settings �˵���ʱ��ͻ���ת�����ǵ�  ��ѡ����ͼ��
            Intent intent = new Intent().setClass(this, SettingActivity.class);  
            //��Ϊ����Ҫ������һ��Activity �������ǵ���ѡ����ͼ ���ص����ݣ����������� startActivityForResult()�����������ǵ���ѡ����ͼ  
            //����һ������Ҫ��ת������  
            //���������ش���  
            this.startActivityForResult(intent, 0); 
		}
		return true;
	}
}
