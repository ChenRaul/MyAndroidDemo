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
		//�����ԣ��о�CustomTheme_gifMoviewViewStyle �ڴ˴���û��ʲô�������Կ���ȥ��arrts.xml�ļ��еĶ����Ķ���
		this(context, attrs, R.styleable.CustomTheme_gifMoviewViewStyle);
		System.out.println("R.styleable.CustomTheme_gifMoviewViewStyle+"+R.styleable.CustomTheme_gifMoviewViewStyle);
		System.out.println("63 �� ִ�����");
	}

	public GifMovieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setViewAttributes(context, attrs, defStyle);
		System.out.println("71 �� ִ�����");
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
		/*obtainStyledAttributes���෽���кü�����������ʵ����Resources.Theme�࣬�ֱ��ǣ�
     (1) obtainStyledAttributes( AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) : TypedArray
     (2) obtainStyledAttributes( int resid, int[] attrs)  : TypeArray
     (3) obtainStyledAttributes(int[] attrs) : TypeArray
	     �ڷ���(1)�����attrsȷ��Ҫ��ȡ��Щ���ԣ�Ȼ������ͨ������3��������ȡ����Ӧ������ֵ��
	     ����ֵ��ȡ�����ȼ��Ӹߵ���������set, defStyleAttr, defStyleRes. defStyleAttr��һ��reference, 
	     ��ָ��ǰTheme�е�һ��style, style��ʵ���Ǹ������Եļ��ϣ�
	     ���defStyleAttrΪ0������Theme��û���ҵ���Ӧ��style, �� �Ż᳢�Դ�defStyleRes��ȡ����ֵ��
     defStyleRes��ʾ����һ��style��id, ����Ϊ0ʱҲ��Ч������(2)��(3)�ֱ��ʾ��style��Theme���ȡ����ֵ��*/
	/*�����Զ����xml�ļ�����ʱ��һ��������TypedArray������˵�˴���attrs.xml�ļ���ǩ��GifMoviewView��������Լ��������磺gif��paused����������Լ��鿴attrs.xml�ļ�*/
		final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifMoviewView, defStyle,
				R.style.Widget_GifMoviewView);

		mMovieResourceId = array.getResourceId(R.styleable.GifMoviewView_gif, -1);
		mPaused = array.getBoolean(R.styleable.GifMoviewView_paused, false);

		array.recycle();

		if (mMovieResourceId != -1) {
			mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
			System.out.println("��ʼ��Movie�ɹ�");
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

/**ʲôʱ�����onMeasure����?
��������View�ĸ��ؼ�Ҫ���ø�View��ʱ�򣬸��ؼ��ᴫ������������View����widthMeasureSpec��heightMeasureSpec��
	������������View���Ի�ȡ�Ŀ�߳ߴ��ģʽ ��ϵ�int���ݡ�
	����ͨ��int mode = MeasureSpec.getMode(widthMeasureSpec)�õ�ģʽ��
	��int size = MeasureSpec.getSize(widthMeasureSpec)�õ��ߴ硣*/
//	MeasureSpec���󣬷�װ��layout���˵�������ҴӸ�view���ݸ���view��ÿ��MeasureSpec���������width��height�Ĺ��
//
//	MeasureSpec�������һ��size��һ��mode������mode����ȡ����������ֵ֮һ��
//
//	UNSPECIFIED��1073741824 [0x40000000]��δ�ӹ涨�ģ���ʾû�и���view����κι涨��
//	EXACTLY��0 [0x0]����ȷ�ģ���ʾ��viewΪ��viewȷ����ȷ�ĳߴ硣
//	AT_MOST��-2147483648 [0x80000000]����view������ָ���ĳߴ��ھ�����
	//����view����������ȷ��view�Ŀ�Ⱥ͸߶�,���������measure(int, int)�б����ã����뱻��д����ȷ����Ч�Ĳ���view�����ݡ�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println("����onMeasure ");
		if (mMovie != null) {
			int movieWidth = mMovie.width();
			int movieHeight = mMovie.height();

			/*
			 * Calculate horizontal scaling
			 */
			float scaleW = 1f;
			int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);
/*mode�������������ȡ �ֱ�Ϊ
����MeasureSpec.UNSPECIFIED û��ȷ���ߴ�,MeasureSpec.EXACTLY��ȷ�ߴ磬�����ǽ��ؼ���layout_width��layout_heightָ��Ϊ����������fill_parent,
	MeasureSpec.AT_MOST�����ߴ磬���ؼ���layout_width��layout_heightָ��ΪWRAP_CONTENTʱ���ؼ���Сһ�����ſؼ����ӿռ�����ݽ��б仯��
	��ʱ�ؼ��ߴ�ֻҪ���������ؼ���������ߴ缴��*/
			if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
				System.out.println("measureModeWidth != ");
				int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
				if (movieWidth > maximumWidth) {//��������Ŀ���ڸ�VieW�Ŀ�ȣ���Ҫ�Զ����Ŀ������С��scaleH��һ�����ӣ���С����movieWidth/scaleH,����Ĵ���������
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
					//��������ĸߴ��ڸ�VieW�Ŀ�ȣ���Ҫ�Զ����ĸ߽�����С��scaleW��һ�����ӣ���С����movieWidth/scaleH,����Ĵ���������
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
	//Ϊ�˷���һ�β��֣���Ҫ����requestLayout()������ͼȷ�Ų��ٵ����������������ʱ����������ͻ�����ͼ�Լ����á�
	//��view����chidView���óߴ��λ��ʱ������
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
	
//	onDraw����View�������֮��ʼ����
//	�����ʼ��ʱ�������ShowWindow�Լ�UpdateWindow����UpdateWindow�ᷢ��һ��WM_PAINT��Ϣ��ϵͳ���Ӷ�����OnDraw
//	postInvalidate()���ػ�ģ�Ҳ���ǵ���postInvalidate()��ϵͳ�����µ���onDraw������һ��
	//��ܲ�����Ʋ��ڻ����������ͼ���󣬲�����ҲΪ���ṩ��̨������ͼ�Ĺ��ܡ�ͨ������invalidate()�������ǿ�ƻ�����ͼ��
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
		//����ǵ�һ֡����¼��ʼʱ��
		if (mMovieStart == 0) {
			mMovieStart = now;
		}
		// ȡ��������ʱ�� 
		int dur = mMovie.duration();

		if (dur == 0) {
			dur = DEFAULT_MOVIEW_DURATION;
		}
		// �����Ҫ��ʾ�ڼ�֡
		mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
	}

	/**
	 * Draw current GIF frame
	 */
	private void drawMovieFrame(Canvas canvas) {
		 // ����Ҫ��ʾ��֡�����Ƽ���
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
