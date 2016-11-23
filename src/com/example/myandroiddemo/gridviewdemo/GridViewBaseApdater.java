package com.example.myandroiddemo.gridviewdemo;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewBaseApdater extends BaseAdapter{

	private Context context;
	private int[] rsid;
	public GridViewBaseApdater(Context context,int[] rsid){
		this.context = context;
		this.rsid = rsid;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rsid.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageview;
		if(convertView == null){
			imageview = new ImageView(context);
			   // 设置View的height和width：这样保证无论image原来的尺寸，每个图像将重新适合这个指定的尺寸。
			imageview.setLayoutParams(new GridView.LayoutParams(100,100));
			imageview.setAdjustViewBounds(true);
            /* ImageView.ScaleType.CENTER 但不执行缩放比例
             * ImageView.ScaleType.CENTER_CROP 按比例统一缩放图片（保持图片的尺寸比例）便于图片的两维（宽度和高度）等于或大于相应的视图维度
             * ImageView.ScaleType.CENTER_INSIDE 按比例统一缩放图片（保持图片的尺寸比例）便于图片的两维（宽度和高度）等于或小于相应的视图维度 */
			imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//android:adjustViewBounds="true"
			
		}else{
			imageview = (ImageView)convertView;
		}
		imageview.setImageResource(rsid[position]);
        return imageview;
	}

}
