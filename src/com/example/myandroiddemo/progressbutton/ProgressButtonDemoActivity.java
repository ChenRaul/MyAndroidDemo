package com.example.myandroiddemo.progressbutton;

import com.example.myandroiddemo.R;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * @see 此项目主要就是进度条按钮，一点击按钮，进度条的值就会开始增加，由于同时动画也需要花费时间，这就导致动画放完了
 * 进度条的效果才会开始看见，而此时进度条的值其实在显示动画的这段时间已经增加了，所以看见的进度条的效果并不是从0开始的,
 * 这个我们可以改变Thread.Sleep（）函数的时间来看效果。
 * 改进方向：如果不想要动画，可以去掉动画
 * 2.可以将 进度条的值保存在那里（将进度条值赋给一个全局变量，或者将下载进度或者播放音乐的进度啊赋值保存好） 代下次可以继续从此处进行（比如说手机qq音乐的播放按钮）；
 * 3.这个可以做类似qq音乐那样的播放键，同时显示音乐的播放进度
 * 
 * CusImage 类是继承View画进度条，通过RectF画一个正方形，再用drawArc方法画一个圆周 
 * 
 * MasterLayout则是动画（进度条开始、停止、结束），同时将CusImage 等画的view添加到视图中
 * */
public class ProgressButtonDemoActivity extends Activity {


	static MasterLayout masterLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbutton);


		masterLayout = (MasterLayout) findViewById(R.id.MasterLayout01);

		//Onclick listener of the progress button
		masterLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				masterLayout.animation(); //Need to call this method for animation and progression
				if (masterLayout.flg_frmwrk_mode == 1) { 
					//Start state. Call any method that you want to execute
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(ProgressButtonDemoActivity.this,
									"Starting download", Toast.LENGTH_SHORT)
									.show();
						}
					});
					new DownLoadSigTask().execute();
				}
				if (masterLayout.flg_frmwrk_mode == 2) {
					//Running state. Call any method that you want to execute
					new DownLoadSigTask().cancel(true);
					masterLayout.reset();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(ProgressButtonDemoActivity.this,
									"Download stopped", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
				if (masterLayout.flg_frmwrk_mode == 3) {
					
					//End state. Call any method that you want to execute.
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(ProgressButtonDemoActivity.this,
									"Download complete", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
			}
		});

	}

	static class DownLoadSigTask extends AsyncTask<String, Integer, String> {

		
		@Override
		protected void onPreExecute() {

		}

		
		@Override
		protected String doInBackground(final String... args) {
			
			//Creating dummy task and updating progress
			
			for (int i = 0; i <= 100; i++) {
				try {
					Thread.sleep(50);//
					
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
				publishProgress(i);

			}

		
			return null;
		}

	
		@Override
		protected void onProgressUpdate(Integer... progress) {
			
			//publishing progress to progress arc
			
			masterLayout.cusview.setupprogress(progress[0]);
		}

	

	}

}
