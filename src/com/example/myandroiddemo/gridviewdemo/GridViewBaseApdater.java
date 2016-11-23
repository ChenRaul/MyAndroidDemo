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
			   // ����View��height��width��������֤����imageԭ���ĳߴ磬ÿ��ͼ�������ʺ����ָ���ĳߴ硣
			imageview.setLayoutParams(new GridView.LayoutParams(100,100));
			imageview.setAdjustViewBounds(true);
            /* ImageView.ScaleType.CENTER ����ִ�����ű���
             * ImageView.ScaleType.CENTER_CROP ������ͳһ����ͼƬ������ͼƬ�ĳߴ����������ͼƬ����ά����Ⱥ͸߶ȣ����ڻ������Ӧ����ͼά��
             * ImageView.ScaleType.CENTER_INSIDE ������ͳһ����ͼƬ������ͼƬ�ĳߴ����������ͼƬ����ά����Ⱥ͸߶ȣ����ڻ�С����Ӧ����ͼά�� */
			imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//android:adjustViewBounds="true"
			
		}else{
			imageview = (ImageView)convertView;
		}
		imageview.setImageResource(rsid[position]);
        return imageview;
	}

}
