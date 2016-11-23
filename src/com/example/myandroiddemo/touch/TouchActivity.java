package com.example.myandroiddemo.touch;

import com.example.myandroiddemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Test activity for testing the different GestureDetectors.
 * 
 * @author Almer Thie (code.almeros.com) Copyright (c) 2013, Almer Thie
 *         (code.almeros.com)
 * 
 *         All rights reserved.
 * 
 *         Redistribution and use in source and binary forms, with or without
 *         modification, are permitted provided that the following conditions
 *         are met:
 * 
 *         Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *         Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 * 
 *         THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *         "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *         LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *         A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *         HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *         INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *         BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 *         OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 *         AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *         LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *         WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *         POSSIBILITY OF SUCH DAMAGE.
 *  @see 程序注册Ontouch 监听器处理手势变化操作，并注册 ScaleGestureDetector、RotateGestureDetector
 *  MoveGestureDetector、ShoveGestureDetector四个手势监听器来获取由于手势变化导致x，y，角度等的变化值，
 *  在ontouch方法里面利用Matrix来实现ImageView 图片的缩放与平移，旋转等操作,应该也可以那四个监听器来操作其他的View,只不过不用
 *  Matrix来实现
 */
public class TouchActivity extends Activity implements OnTouchListener {

	private Matrix mMatrix = new Matrix();//Matrix主要用于图片方面的操作
	private float mScaleFactor = .4f;
	private float mRotationDegrees = 0.f;
	private float mFocusX = 0.f;
	private float mFocusY = 0.f;
	private int mAlpha = 255;
	private int mImageHeight, mImageWidth;

	private ScaleGestureDetector mScaleDetector;
	private RotateGestureDetector mRotateDetector;
	private MoveGestureDetector mMoveDetector;
	private ShoveGestureDetector mShoveDetector;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch);

		// Determine the center of the screen to center 'earth'
		Display display = getWindowManager().getDefaultDisplay();
		mFocusX = display.getWidth() / 2f;
		mFocusY = display.getHeight() / 2f;

		// Set this class as touchListener to the ImageView
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnTouchListener(this);

		// Determine dimensions of 'earth' image
		Drawable d = this.getResources().getDrawable(R.drawable.basketball);
		mImageHeight = d.getIntrinsicHeight();
		mImageWidth = d.getIntrinsicWidth();

		// View is scaled and translated by matrix, so scale and translate
		// initially
		float scaledImageCenterX = (mImageWidth * mScaleFactor) / 2;
		float scaledImageCenterY = (mImageHeight * mScaleFactor) / 2;
		System.out.println("7777777:"+mScaleFactor+mScaleFactor);
		mMatrix.postScale(mScaleFactor, mScaleFactor);// 缩放 mScaleFactor倍
		/* 平移 */
		System.out.println("22222222:"+(mFocusX - scaledImageCenterX)+( mFocusY
				- scaledImageCenterY));
		mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY
				- scaledImageCenterY);
		imageView.setImageMatrix(mMatrix);

		// Setup Gesture Detectors
		mScaleDetector = new ScaleGestureDetector(getApplicationContext(),new ScaleListener());
		mRotateDetector = new RotateGestureDetector(getApplicationContext(),
				new RotateListener());
		mMoveDetector = new MoveGestureDetector(getApplicationContext(),
				new MoveListener());
		mShoveDetector = new ShoveGestureDetector(getApplicationContext(),
				new ShoveListener());
	}

	@SuppressWarnings("deprecation")
	public boolean onTouch(View v, MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		mRotateDetector.onTouchEvent(event);
		mMoveDetector.onTouchEvent(event);
		mShoveDetector.onTouchEvent(event);

		float scaledImageCenterX = (mImageWidth * mScaleFactor) / 2;
		float scaledImageCenterY = (mImageHeight * mScaleFactor) / 2;

		mMatrix.reset();
		mMatrix.postScale(mScaleFactor, mScaleFactor);//缩放
		//以scaledImageCenterX，scaledImageCenterY为中心，旋转角度为mRotationDegrees
		mMatrix.postRotate(mRotationDegrees, scaledImageCenterX,scaledImageCenterY);
		//沿着X轴方向平移mFocusX - scaledImageCenterX，沿着Y轴方向平移 mFocusY- scaledImageCenterY
		mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY- scaledImageCenterY);

		ImageView view = (ImageView) v;
		view.setImageMatrix(mMatrix);
		view.setAlpha(mAlpha);
		System.out.println("ontouch");
		return true; // indicate event was handled
	}

	@SuppressLint("NewApi")
	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@SuppressLint("NewApi")
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor(); // scale change since
														// previous event

			// Don't let the object get too small or too large.
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
			System.out.println("mScaleFactor"+mScaleFactor);
			return true;
		}
	}

	private class RotateListener extends
			RotateGestureDetector.SimpleOnRotateGestureListener {
		@Override
		public boolean onRotate(RotateGestureDetector detector) {
			mRotationDegrees -= detector.getRotationDegreesDelta();
			System.out.println("mRotationDegrees"+mRotationDegrees);
			return true;
		}
	}

	private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
		@Override
		public boolean onMove(MoveGestureDetector detector) {
			PointF d = detector.getFocusDelta();
			mFocusX += d.x;
			mFocusY += d.y;

			// mFocusX = detector.getFocusX();
			// mFocusY = detector.getFocusY();
			System.out.println("mFocusX"+mFocusX);
			System.out.println("mFocusY"+mFocusY);
			return true;
		}
	}

	private class ShoveListener extends
			ShoveGestureDetector.SimpleOnShoveGestureListener {
		@Override
		public boolean onShove(ShoveGestureDetector detector) {
			mAlpha += detector.getShovePixelsDelta();
			System.out.println("mAlpha=" + mAlpha);
			if (mAlpha > 255)
				mAlpha = 255;
			else if (mAlpha < 0)
				mAlpha = 0;

			return true;
		}
	}
}