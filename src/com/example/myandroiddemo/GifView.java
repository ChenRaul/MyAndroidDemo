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
 * ����Ŀ����gif�������ֻ��������⣬������ģ������û�����⣬���Ұ�������Ū��һ���������κβ����豸��û�����⣬û��Ū���ԭ��
 * android gif display control view. lightweight!very very easy to use! you can
 * use it for free!
 * gif��������  �����Լ�����·�� ����ָ��ͼƬ
 * ���ýӿ� play(int rsid) rsid�ǲ�����Դ��id ����play(String path) path ����Դ���ŵ�·��
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
		 * Android�Զ�������ʱTypedArray��ʹ�÷���:
		 * http://blog.csdn.net/eyu8874521/article/details/8552534
		 * 
		 * ������Ŀ��Կ���
		 * Android ���Զ���ؼ�������(attr.xml,declare-styleable,TypedArray)�ķ�����ʹ��:
		 * http://blog.csdn.net/jincf2011/article/details/6344678
		 * */
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.GifView, defStyle, 0);
		/*���layout xml�ļ���������gifResource���ԣ���rsid��ֵ��R�ļ���gifResource���Ե�ֵһ����
		 * ���ڴ˴�����������ֵ�ǣ�lf:gifResource="@drawable/test"����rsid��ʱ����R.drawable.test*/
		int rsid = ta.getResourceId(R.styleable.GifView_gifResource, -1);
		System.out.println("rsid = "+ rsid);
		if (rsid != -1) {//��ʾdrawable�ļ��ж�ȡ�����ļ�
			setGifResource(rsid);
		} else {//���û�ж�ȡ�����ʹ����õ�Ĭ��·����ȥ��ȡgifͼƬ�������Լ������ֶ�·��,����˵����play(int rsid)����
			//play(String path)���������Ϳ����ˣ����������������ṩ�������߲���gif�Ľӿ�
			//����layout xml�в�û������gifPath������ԣ����Ե�gifResourceû������ʱ��pathҲ�ǿ�
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
		
		System.out.println("draw ִ�����");
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