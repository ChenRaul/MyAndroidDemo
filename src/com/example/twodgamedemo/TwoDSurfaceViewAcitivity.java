package com.example.twodgamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class TwoDSurfaceViewAcitivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GameSurfaceView gameSurfaceView = new GameSurfaceView(this);
		gameSurfaceView.x = 100;
		gameSurfaceView.y = 100;
		//ʹgameview������Ļ�����Ǻ�������һ���Ч��û��ɶ������
		setContentView(gameSurfaceView,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		System.out.println(gameSurfaceView.getWidth()+"---"+gameSurfaceView.getHeight());
		//setContentView(gameSurfaceView);
	}
}
