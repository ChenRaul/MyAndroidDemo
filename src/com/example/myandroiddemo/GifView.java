package com.example.myandroiddemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * 此项目动画gif播放在手机上有问题，但是在模拟器上没有问题，而且把它单独弄成一个工程在任何测试设备上没有问题，没有弄清楚原因
 * android gif display control view. lightweight!very very easy to use! you can
 * use it for free!
 * gif动画播放  可以自己设置路径 或者指定图片
 * 调用接口 play(int rsid) rsid是播放资源的id 或者play(String path) path 是资源播放的路径
 * @author droidwolf email:droidwolf2010@gmail.com
 * 
 */
public class GifView extends View {
	private Movie mMovie;
	private long mStartMills = 0, mPauseMills = 0;
	private int mLastRelativeMills = 0;
	private int DEFAULT_WIDHT = 50, DEFAULT_HEIGHT = 50;

	public GifView(Context context) {
		super(context);
		System.out.println("eeeeeeeeeeeeeeeeeee");
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("qqqqqqqqqqqqq");
		loadGif(context, attrs, 0);
	}

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("wwwwwwwwwwwwwww");
		loadGif(context, attrs, defStyle);
	}

	private void loadGif(Context context, AttributeSet attrs, int defStyle) {
		/*
		 * Android自定义属性时TypedArray的使用方法:
		 * http://blog.csdn.net/eyu8874521/article/details/8552534
		 * 
		 * 更具体的可以看：
		 * Android 中自定义控件和属性(attr.xml,declare-styleable,TypedArray)的方法和使用:
		 * http://blog.csdn.net/jincf2011/article/details/6344678
		 * */
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.GifView, defStyle, 0);
		/*如果layout xml文件中配置了gifResource属性，则rsid的值跟R文件中gifResource属性的值一样，
		 * 如在此处，它的属性值是：lf:gifResource="@drawable/test"，则rsid此时就是R.drawable.test*/
		int rsid = ta.getResourceId(R.styleable.GifView_gifResource, -1);
		System.out.println("rsid = "+ rsid);
		if (rsid != -1) {//表示drawable文件中读取到了文件
			setGifResource(rsid);
		} else {//如果没有读取到，就从设置的默认路径中去读取gif图片，或者自己设置手动路径,比如说调用play(int rsid)或者
			//play(String path)两个方法就可以了，这两个方法都是提供给调用者播放gif的接口
			//由于layout xml中并没有设置gifPath这个属性，所以当gifResource没有设置时，path也是空
			String path = ta.getString(R.styleable.GifView_gifPath);
			System.out.println("path ="+ path);
			if (path != null)
				setGifPath(path);
		}
		ta.recycle();
	}

	public void setGifResource(int rsid) {
		System.out.println("4444444444");
		releaseMovie();
		System.out.println("55555555");
		mMovie = Movie.decodeStream(getResources().openRawResource(rsid));
		System.out.println("setGifResource mMovie ="+mMovie);
	}

	public void setGifPath(String path) {
		releaseMovie();
		InputStream ins = null;
		try {
			final String asset = "file:///android_asset/";
			if (path.startsWith(asset)) {
				ins = getResources().getAssets().open(
						path.substring(asset.length()));
			} else {
				ins = new FileInputStream(new File(path));

			}
			mMovie = Movie.decodeStream(ins);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null)
				try {
					ins.close();
					ins = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void stop() {
		releaseMovie();
		invalidate();
	}

	public void pause() {
		if (mPauseMills > 0)
			return;
		mPauseMills = SystemClock.uptimeMillis();
		invalidate();
	}

	public boolean isPlaying() {
		return mStartMills > 0 && mPauseMills == 0;
	}

	public boolean isPause() {
		return mPauseMills > 0;
	}

	public void play() {
		if (mPauseMills > 0) {
			System.out.println("7777777777");
			mStartMills += SystemClock.uptimeMillis() - mPauseMills;
			mPauseMills = 0;
		}
		System.out.println("8888888888");
		invalidate();
	}

	public void play(int rsid) {
		System.out.println("22222222222222");
		setGifResource(rsid);
		System.out.println("3333333333");
		play();
		System.out.println("6666666666");
	}

	public void play(String path) {
		setGifPath(path);
		play();
	}

	private void releaseMovie() {
		mStartMills = mPauseMills = mLastRelativeMills = 0;
		if (mMovie == null)
			return;
		try {
			Method method = Movie.class.getDeclaredMethod("finalize", null);
			method.setAccessible(true);
			method.invoke(mMovie, null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		mMovie = null;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("mMovie ="+mMovie);
		if (mMovie == null) {
			super.requestLayout();
			return;
		}
		int dur = mMovie.duration();
		System.out.println("dur ="+dur);
		if (dur == 0)
			dur = 1000;
		if (mPauseMills == 0) {
			if (mStartMills == 0) {
				System.out.println("mStartMills1="+mStartMills);
				mStartMills = SystemClock.uptimeMillis();
				mLastRelativeMills = 0;
				System.out.println("mStartMills2="+mStartMills);
				super.requestLayout();
			} else {
				mLastRelativeMills = (int) ((SystemClock.uptimeMillis() - mStartMills) % dur);
			}
		}
		
		mMovie.setTime(mLastRelativeMills);
		// mMovie.draw(canvas, 0, 0);
		System.out.println("mLastRelativeMills ="+mLastRelativeMills);
		System.out.println(getWidth()+"--"+getHeight());
		System.out.println("mMovie.width() ="+mMovie.width());
		System.out.println("mMovie.height() ="+mMovie.height());
		System.out.println((getWidth() - mMovie.width()) / 2);
		System.out.println((getHeight() - mMovie.height()) / 2);
		mMovie.draw(canvas, (getWidth() - mMovie.width()) / 2,(getHeight() - mMovie.height()) / 2);
		
		System.out.println("draw 执行完毕");
		if (mPauseMills == 0)
			postInvalidateDelayed(dur / 5);
		System.out.println("dur / 5 ="+ dur / 5);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int width = resolveSize(
				mMovie == null ? DEFAULT_WIDHT : mMovie.width(),
				widthMeasureSpec);
		final int height = resolveSize(
				mMovie == null ? DEFAULT_HEIGHT : mMovie.height(),
				heightMeasureSpec);
		super.setMeasuredDimension(width, height);
	}

}// end class GifView