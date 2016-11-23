package com.example.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myandroiddemo.R;
import com.example.myandroiddemo.SlidingMenu;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SildingMenuActivity extends Activity
{
	private SlidingMenu mMenu;
	private ListView listView;
	private List<Map<String, Object>> list;
	private boolean imageFlag = false;
	private int tempFlag=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slidingmenu);
		 
		list = new ArrayList<Map<String,Object>>();
		initList();
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		listView = (ListView) findViewById(R.id.sliding_menu_listview);
		listView.setAdapter(new SimpleAdapter(this, list, R.layout.slidingmenu_listview_item,
				new String[]{"icon","name"}, new int[]{R.id.slidingmenu_listview_item_iv,R.id.slidingmenu_listview_item_tv}));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				//arg0:就是你的listview   arg2:点击的item的位置。和你的数组的下标相等。arg3:被电击view的id
				//arg1是当前item的view，通过它可以获得该项中的各个组件。
				TextView  tv =(TextView) arg1.findViewById(R.id.slidingmenu_listview_item_tv);
				if(tv.getText().toString().equals("Item"+arg2)){
					tv.setText("我被点击了");
				}else{
					tv.setText("Item"+arg2);
				}
				ImageView iv =(ImageView) arg1.findViewById(R.id.slidingmenu_listview_item_iv);
				//下面更换图片有bug，就是如果点了一下第一个Item，此时更换了图片，但再去点第二个Item，第二个Item就不换换图片
				//最好的办法是每一个Item都设置一个flag，利用swtich case 根据arg2来区别是哪个Item，因为arg2肯定是从0开始依次递增的
				
				//还有就是设置两个flag,也即是Item1用flag1，Item2用flag2，Item3用flag1，Item4用flag2.......
				if(imageFlag){
					//ImageView 一种更换图片的方式
					Drawable mdrawable = getResources().getDrawable(R.drawable.test1);
					iv.setImageDrawable(mdrawable);
					imageFlag= false;
				}else if(!imageFlag){
					//ImageView 一种更换图片的方式
					iv.setImageResource(R.drawable.test3);
					imageFlag=  true;
				}
				Toast.makeText(SildingMenuActivity.this, "Item arg2 ="+arg2+",arg3 ="+arg3, 0).show();
				mMenu.toggle("");
			}
		});
	}
		
	//按钮的监听函数事件
	public void toggleMenu(View view)
	{
		mMenu.toggle("menu");
	}
	private void initList(){
		for(int i =0;i<15;i++){
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("icon", R.drawable.test1);
            hashMap.put("name", "Item"+i);
            list.add(hashMap);
		}
	}
	
}
