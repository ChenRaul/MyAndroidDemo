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
				//�����ı�adapter�е�list��������
				//adapter.notifyDataSetChanged();
				myListViewAdapter.notifyDataSetChanged();
			}
		});
		//Ҫʵ�ִ���ɾ����ť�Ĺ��� ����ʹ��ԭ��̬��adapter������ʹ���Զ����adapter������������ģ����ԭ������ΪListView���Զ���ĵ��µ�
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
