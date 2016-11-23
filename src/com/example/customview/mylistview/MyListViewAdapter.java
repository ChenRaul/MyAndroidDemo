package com.example.customview.mylistview;


import java.util.List;

import com.example.myandroiddemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListViewAdapter extends ArrayAdapter<String> {  
  
	private List<String> list;
    public MyListViewAdapter(Context context, int textViewResourceId, List<String> objects) {  
        super(context, textViewResourceId, objects);  
        this.list = objects; 
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        View view;  
        if (convertView == null) {  
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_item, null);  
        } else {  
            view = convertView;  
        }  
        TextView textView = (TextView) view.findViewById(R.id.custom_text_view);  
        textView.setText(list.get(position));  
        return view;  
    }  
  
} 