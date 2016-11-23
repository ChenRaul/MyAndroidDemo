package com.example.twodgamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

/*看到369页*/
public class TwoDViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GameView gameView = new GameView(this);
		gameView.x = 100;
		gameView.y = 100;
		//使gameview充满屏幕，但是和下面那一句的效果没有啥子区别
	//	setContentView(gameView,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		setContentView(gameView);
		System.out.println(gameView.getWidth()+"---"+gameView.getHeight());
	}
}
