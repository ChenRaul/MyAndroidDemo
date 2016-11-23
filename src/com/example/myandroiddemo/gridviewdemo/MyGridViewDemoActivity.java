package com.example.myandroiddemo.gridviewdemo;


import com.example.myandroiddemo.R;
import com.example.myandroiddemo.animationdaldialogeffects.MyListViewBaseAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MyGridViewDemoActivity extends Activity{
	
	private GridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridviewdemo);
		setTitle("GridView Demo");
		gridView = (GridView) findViewById(R.id.gridview);
		int[] rsid = {R.drawable.test1,R.drawable.test2,R.drawable.test3,R.drawable.test4,R.drawable.test1,R.drawable.test2,R.drawable.test3,R.drawable.test4,
				R.drawable.test1,R.drawable.test2,R.drawable.test3,R.drawable.test4,R.drawable.test1,R.drawable.test2,R.drawable.test3,R.drawable.test4};
		
	   // gridView.setNumColumns(4);
	   
	    gridView.setAdapter(new GridViewBaseApdater(this,rsid));
	   
		gridView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					 Toast.makeText(MyGridViewDemoActivity.this, ""+position, Toast.LENGTH_SHORT).show();
					
				}    
	        });
	}
}
