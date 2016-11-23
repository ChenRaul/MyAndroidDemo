package com.example.myandroiddemo.gifplay;


import com.example.myandroiddemo.GifMovieView;
import com.example.myandroiddemo.GifView;
import com.example.myandroiddemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*注释的都是引用GifView的播放代码， 也就是说它还是有问题，所以用第二种 GifMovieView来播放gif动画*/
public class AndroidGifDemoActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gifplay);
		
		final GifMovieView gif1 = (GifMovieView) findViewById(R.id.gif1);
		gif1.setMovieResource(R.drawable.co);
		System.out.println("gif1.setMovieResource 执行完毕");
//
//		Button bt1 = (Button) findViewById(R.id.button1);
//		Button bt2 = (Button) findViewById(R.id.button2);
//		Button bt3 = (Button) findViewById(R.id.button3);
//		bt2.setOnClickListener(this);
//		bt1.setOnClickListener(this);
//		bt3.setOnClickListener(this);
//		bt1.setVisibility(View.GONE);
//		bt2.setVisibility(View.GONE);
//		bt3.setVisibility(View.GONE);
//		mGifView = (GifView) findViewById(R.id.gifView1);
		// mGifView.play(R.drawable.test);
	}
	public void onGifClick(View v){
		GifMovieView gif = (GifMovieView) v;
		gif.setPaused(!gif.isPaused());//点击暂停或者播放
	}
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.button1:
//			if (mGifView.isPause()) {
//				System.out.println("23123weqwe");
//				mGifView.play();
//			} else {
//				System.out.println("1111111111");
//				mGifView.play(R.drawable.co);
//			}
//			break;
//		case R.id.button2:
//			mGifView.pause();
//			break;
//		case R.id.button3:
//			mGifView.stop();
//			break;
//		}
//	}

}