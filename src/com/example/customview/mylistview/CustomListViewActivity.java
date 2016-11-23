package com.example.customview.mylistview;

import java.util.ArrayList;
import java.util.List;

import com.example.customview.mylistview.MyListView.OnDeleteListener;
import com.example.myandroiddemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CustomListViewActivity extends Activity{
	
	private MyListView myListView;
	private ArrayAdapter<String> adapter;
	private MyListViewAdapter myListViewAdapter;
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customlistview);
		setTitle("CustomListView");
		initArrayList();
		myListView = (MyListView) findViewById(R.id.customlistview);
		myListView.setOnDeleteListener(new OnDeleteListener() {
			@Override
			public void onDelete(int index) {
				list.remove(index);
				//立即改变adapter中的list适配数据
				//adapter.notifyDataSetChanged();
				myListViewAdapter.notifyDataSetChanged();
			}
		});
		//要实现带有删除按钮的功能 不能使用原生态的adapter，必须使用自定义的adapter，否则会出问题的，大概原因是因为ListView是自定义的导致的
	//	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		myListViewAdapter = new MyListViewAdapter(this, 0, list);
		myListView.setAdapter(myListViewAdapter);
	}
	private void initArrayList(){
		for(int i = 0;i<20;i++){
			list.add("Item  "+ i);
		}
		
	}
}
