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
				//arg0:�������listview   arg2:�����item��λ�á������������±���ȡ�arg3:�����view��id
				//arg1�ǵ�ǰitem��view��ͨ�������Ի�ø����еĸ��������
				TextView  tv =(TextView) arg1.findViewById(R.id.slidingmenu_listview_item_tv);
				if(tv.getText().toString().equals("Item"+arg2)){
					tv.setText("�ұ������");
				}else{
					tv.setText("Item"+arg2);
				}
				ImageView iv =(ImageView) arg1.findViewById(R.id.slidingmenu_listview_item_iv);
				//�������ͼƬ��bug�������������һ�µ�һ��Item����ʱ������ͼƬ������ȥ��ڶ���Item���ڶ���Item�Ͳ�����ͼƬ
				//��õİ취��ÿһ��Item������һ��flag������swtich case ����arg2���������ĸ�Item����Ϊarg2�϶��Ǵ�0��ʼ���ε�����
				
				//���о�����������flag,Ҳ����Item1��flag1��Item2��flag2��Item3��flag1��Item4��flag2.......
				if(imageFlag){
					//ImageView һ�ָ���ͼƬ�ķ�ʽ
					Drawable mdrawable = getResources().getDrawable(R.drawable.test1);
					iv.setImageDrawable(mdrawable);
					imageFlag= false;
				}else if(!imageFlag){
					//ImageView һ�ָ���ͼƬ�ķ�ʽ
					iv.setImageResource(R.drawable.test3);
					imageFlag=  true;
				}
				Toast.makeText(SildingMenuActivity.this, "Item arg2 ="+arg2+",arg3 ="+arg3, 0).show();
				mMenu.toggle("");
			}
		});
	}
		
	//��ť�ļ��������¼�
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
