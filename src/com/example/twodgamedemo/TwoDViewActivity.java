package com.example.twodgamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

/*����369ҳ*/
public class TwoDViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GameView gameView = new GameView(this);
		gameView.x = 100;
		gameView.y = 100;
		//ʹgameview������Ļ�����Ǻ�������һ���Ч��û��ɶ������
	//	setContentView(gameView,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		setContentView(gameView);
		System.out.println(gameView.getWidth()+"---"+gameView.getHeight());
	}
}
