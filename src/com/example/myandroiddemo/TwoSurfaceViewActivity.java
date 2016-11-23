package com.example.myandroiddemo;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class TwoSurfaceViewActivity extends Activity{

	private SurfaceView sv1;
	private SurfaceView sv2;
	private SurfaceHolder sh1;
	private SurfaceHolder sh2;
	private Camera camera;
	private boolean isFocus = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twosurfaceview);
		sv1 = (SurfaceView) findViewById(R.id.sv1);
		sv2 = (SurfaceView) findViewById(R.id.sv2);
		
		sh1 = sv1.getHolder();
		sh2 = sv2.getHolder();
		sh1.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sh2.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		//sv1.setZOrderOnTop(false);        
		sv2.setZOrderOnTop(true);//只要这一句就可以了，上一句和下一句可以不要，这样就可以让两个surfaceview同时显示
		//sv2.setZOrderMediaOverlay(true);
		sh2.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				//sh2.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
				final MediaPlayer mediaPlayer = new MediaPlayer();
				new Thread(){
					@Override
					public void run() {
						try {//todo 
							mediaPlayer.reset();  
				        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
				            mediaPlayer.setDataSource("/mnt/sdcard/mv.mp4");
						    mediaPlayer.setDisplay(sh2);
							mediaPlayer.prepare();	
							//准备播放
							mediaPlayer.start(); 
						} catch (Exception e) {
							e.printStackTrace();
						}
						super.run();
					}
				}.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
		sh1.addCallback(new Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				try {
					initCamera();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void initCamera() throws IOException {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.lock();
			camera.release();
			camera = null;
		}
		try {
			camera  = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setCameraParams();
		camera.setDisplayOrientation(90);// 保证视屏或者图片跟手机拍摄方向一致
		
		camera.setPreviewDisplay(sh1);
		camera.startPreview();// 调用Camera.starPreview()方法开始显示实时的Camera图像。
		if (isFocus) {
			camera.autoFocus(null);
		}
	}
	public void setCameraParams() {
		if (camera != null) {
			Parameters params = camera.getParameters();
			List<String> list = params.getSupportedFocusModes();
			if (list.contains(Parameters.FOCUS_MODE_AUTO)) {// 自动聚焦
				isFocus = true;
				params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			params.set("orientation", "portrait");
			camera.setParameters(params);
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		//StandOutWindow.closeAll(this, SimpleWindow.class);
		if (camera != null) {
			camera.stopPreview();// 停止预览
			camera.release();
			camera = null;
		}
	}
}
