package com.example.myandroiddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


/**
 * This is a View class that wraps Android {@link Movie} object and displays it.
 * You can set GIF as a Movie object or as a resource id from XML or by calling
 * {@link #setMovie(Movie)} or {@link #setMovieResource(int)}.
 * <p>
 * You can pause and resume GIF animation by calling {@link #setPaused(boolean)}.
 * <p>
 * The animation is drawn in the center inside of the measured view bounds.
 * 
 * @author Sergey Bakhtiarov
 */

public class GifMovieView extends View {

	private static final int DEFAULT_MOVIEW_DURATION = 1000;

	private int mMovieResourceId;
	private Movie mMovie;

	private long mMovieStart;
	private int mCurrentAnimationTime = 0;
	
	/**
	 * Position for drawing animation frames in the center of the view.
	 */
	private float mLeft;
	private float mTop;

	/**
	 * Scaling factor to fit the animation within view bounds.
	 */
	private float mScale;

	/**
	 * Scaled movie frames width and height.
	 */
	private int mMeasuredMovieWidth;
	private int mMeasuredMovieHeight;

	private volatile boolean mPaused = false;
	private boolean mVisible = true;

	public GifMovieView(Context context) {
		this(context, null);
	}

	public GifMovieView(Context context, AttributeSet attrs) {
		//经测试，感觉CustomTheme_gifMoviewViewStyle 在此处并没有什么作用所以可以去掉arrts.xml文件中的对它的定义
		this(context, attrs, R.styleable.CustomTheme_gifMoviewViewStyle);
		System.out.println("R.styleable.CustomTheme_gifMoviewViewStyle+"+R.styleable.CustomTheme_gifMoviewViewStyle);
		System.out.println("63 行 执行完毕");
	}

	public GifMovieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setViewAttributes(context, attrs, defStyle);
		System.out.println("71 行 执行完毕");
	}

	@SuppressLint("NewApi")
	private void setViewAttributes(Context context, AttributeSet attrs, int defStyle) {

		/**
		 * Starting from HONEYCOMB have to turn off HW acceleration to draw
		 * Movie on Canvas.
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		/*obtainStyledAttributes这类方法有好几个，真正的实现是Resources.Theme类，分别是：
     (1) obtainStyledAttributes( AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) : TypedArray
     (2) obtainStyledAttributes( int resid, int[] attrs)  : TypeArray
     (3) obtainStyledAttributes(int[] attrs) : TypeArray
	     在方法(1)里根据attrs确定要获取哪些属性，然后依次通过其余3个参数来取得相应的属性值，
	     属性值获取的优先级从高到低依次是set, defStyleAttr, defStyleRes. defStyleAttr是一个reference, 
	     它指向当前Theme中的一个style, style其实就是各种属性的集合，
	     如果defStyleAttr为0或者在Theme中没有找到相应的style, 则 才会尝试从defStyleRes获取属性值，
     defStyleRes表示的是一个style的id, 当它为0时也无效。方法(2)和(3)分别表示从style或Theme里获取属性值。*/
	/*当有自定义的xml文件属性时，一般采用这个TypedArray，比如说此处在attrs.xml文件标签中GifMoviewView定义的有自己的属性如：gif，paused，详情可以自己查看attrs.xml文件*/
		final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifMoviewView, defStyle,
				R.style.Widget_GifMoviewView);

		mMovieResourceId = array.getResourceId(R.styleable.GifMoviewView_gif, -1);
		mPaused = array.getBoolean(R.styleable.GifMoviewView_paused, false);

		array.recycle();

		if (mMovieResourceId != -1) {
			mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
			System.out.println("初始化Movie成功");
		}
	}

	public void setMovieResource(int movieResId) {
		this.mMovieResourceId = movieResId;
		mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
		System.out.println("11111111111111");
		requestLayout();
		System.out.println("222222222222");
	}

	public void setMovie(Movie movie) {
		this.mMovie = movie;
		requestLayout();
	}

	public Movie getMovie() {
		return mMovie;
	}

	public void setMovieTime(int time) {
		mCurrentAnimationTime = time;
		invalidate();
	}

	public void setPaused(boolean paused) {
		this.mPaused = paused;

		/**
		 * Calculate new movie start time, so that it resumes from the same
		 * frame.
		 */
		if (!paused) {
			mMovieStart = android.os.SystemClock.uptimeMillis() - mCurrentAnimationTime;
		}

		invalidate();
	}

	public boolean isPaused() {
		return this.mPaused;
	}

/**什么时候调用onMeasure方法?
　　当子View的父控件要放置该View的时候，父控件会传递两个参数给View——widthMeasureSpec和heightMeasureSpec。
	这两个参数是View可以获取的宽高尺寸和模式 混合的int数据。
	可以通过int mode = MeasureSpec.getMode(widthMeasureSpec)得到模式，
	用int size = MeasureSpec.getSize(widthMeasureSpec)得到尺寸。*/
//	MeasureSpec对象，封装了layout规格说明，并且从父view传递给子view。每个MeasureSpec对象代表了width或height的规格。
//
//	MeasureSpec对象包含一个size和一个mode，其中mode可以取以下三个数值之一：
//
//	UNSPECIFIED，1073741824 [0x40000000]，未加规定的，表示没有给子view添加任何规定。
//	EXACTLY，0 [0x0]，精确的，表示父view为子view确定精确的尺寸。
//	AT_MOST，-2147483648 [0x80000000]，子view可以在指定的尺寸内尽量大。
	//测量view及其内容来确定view的宽度和高度,这个方法在measure(int, int)中被调用，必须被重写来精确和有效的测量view的内容。
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println("进入onMeasure ");
		if (mMovie != null) {
			int movieWidth = mMovie.width();
			int movieHeight = mMovie.height();

			/*
			 * Calculate horizontal scaling
			 */
			float scaleW = 1f;
			int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);
/*mode共有三种情况，取 分别为
　　MeasureSpec.UNSPECIFIED 没有确定尺寸,MeasureSpec.EXACTLY精确尺寸，当我们将控件的layout_width或layout_height指定为具体数或者fill_parent,
	MeasureSpec.AT_MOST。最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，
	此时控件尺寸只要不超过父控件允许的最大尺寸即可*/
			if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
				System.out.println("measureModeWidth != ");
				int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
				if (movieWidth > maximumWidth) {//如果动画的宽大于父VieW的宽度，则要对动画的宽进行缩小，scaleH是一个因子，缩小就是movieWidth/scaleH,后面的代码就是这个
					scaleW = (float) movieWidth / (float) maximumWidth;
				}
			}

			/*
			 * calculate vertical scaling
			 */
			float scaleH = 1f;
			int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);

			if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
				System.out.println("measureModeHeight != ");
				int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
				if (movieHeight > maximumHeight) {
					//如果动画的高大于父VieW的宽度，则要对动画的高进行缩小，scaleW是一个因子，缩小就是movieWidth/scaleH,后面的代码就是这个
					scaleH = (float) movieHeight / (float) maximumHeight;
				}
			}

			/*
			 * calculate overall scale
			 */
			mScale = 1f / Math.max(scaleH, scaleW);
			System.out.println("mScale = "+ mScale);
			System.out.println("movieWidth = "+ movieWidth);
			System.out.println("movieHeight = "+ movieHeight);
			mMeasuredMovieWidth = (int) (movieWidth * mScale);
			mMeasuredMovieHeight = (int) (movieHeight * mScale);

			setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);

		} else {
			/*
			 * No movie set, just set minimum available size.
			 */
			setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
		}
	}
	//为了发起一次布局，需要调用requestLayout()。当视图确信不再调整分配给它的区域时，这个方法就会由视图自己调用。
	//在view给其chidView设置尺寸和位置时被调用
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		/*
		 * Calculate left / top for drawing in center
		 */
		System.out.println("----"+getWidth()+"----"+mMeasuredMovieWidth+"----"+getHeight()+"----"+mMeasuredMovieHeight);
		mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
		mTop = (getHeight() - mMeasuredMovieHeight) / 2f;
		mVisible = getVisibility() == View.VISIBLE;
	}
	
//	onDraw是在View初化完成之后开始调用
//	程序初始化时，会调用ShowWindow以及UpdateWindow，而UpdateWindow会发送一个WM_PAINT消息给系统，从而调用OnDraw
//	postInvalidate()是重绘的，也就是调用postInvalidate()后系统会重新调用onDraw方法画一次
	//框架不会绘制不在绘制区域的视图对象，并且它也为你提供后台绘制视图的功能。通过调用invalidate()，你可以强制绘制视图。
	@Override
	protected void onDraw(Canvas canvas) {
		if (mMovie != null) {
			if (!mPaused) {
				updateAnimationTime();
				drawMovieFrame(canvas);
				invalidateView();
			} else {
				drawMovieFrame(canvas);
			}
		}
	}
	
	/**
	 * Invalidates view only if it is visible.
	 * <br>
	 * {@link #postInvalidateOnAnimation()} is used for Jelly Bean and higher.
	 * 
	 */
	@SuppressLint("NewApi")
	private void invalidateView() {
		if(mVisible) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				postInvalidateOnAnimation();
			} else {
				invalidate();
			}
		}
	}

	/**
	 * Calculate current animation time
	 */
	private void updateAnimationTime() {
		long now = android.os.SystemClock.uptimeMillis();
		//如果是第一帧，记录起始时间
		if (mMovieStart == 0) {
			mMovieStart = now;
		}
		// 取出动画的时长 
		int dur = mMovie.duration();

		if (dur == 0) {
			dur = DEFAULT_MOVIEW_DURATION;
		}
		// 算出需要显示第几帧
		mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
	}

	/**
	 * Draw current GIF frame
	 */
	private void drawMovieFrame(Canvas canvas) {
		 // 设置要显示的帧，绘制即可
		mMovie.setTime(mCurrentAnimationTime);

		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScale, mScale);
		System.out.println("draw.mScale="+mScale);
		System.out.println("draw.mLeft="+mLeft);
		System.out.println("draw.mTop="+mTop);
		mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
		canvas.restore();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onScreenStateChanged(int screenState) {
		super.onScreenStateChanged(screenState);
		mVisible = screenState == SCREEN_STATE_ON;
		invalidateView();
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}
}
