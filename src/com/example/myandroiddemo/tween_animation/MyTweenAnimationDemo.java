package com.example.myandroiddemo.tween_animation;

import com.example.myandroiddemo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @see Tween动画是操作某个View控件让其展现出旋转、渐变、移动、缩放的这么一种转换过程，我们成为补间动画。
 * 我们可以以XML形式定义动画，也可以编码实现。
*如果以XML形式定义一个动画，我们按照动画的定义语法完成XML，并放置于/res/anim目录下，文件名可以作为资源ID被引用；
*如果由编码实现，我们需要使用到Animation对象。
*每个动画都可以选择不同的插值器，这样就会让组件有不同的效果产生
 * */
public class MyTweenAnimationDemo extends Activity implements OnClickListener{
	
	private int currAngle = 0;
	private ImageView rotateimageView;
	private Button alphabtn;
	private Button scalebtn;
	private Button translatebtn;
	private Button  multiplebtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 回去再写自定义的动画
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytweenanidemo);
		setTitle("TweenAnimation Demo");
		rotateimageView = (ImageView) findViewById(R.id.tween_image);
		rotateimageView.setOnClickListener(this);
        alphabtn = (Button) findViewById(R.id.tween_alpha_btn);
        scalebtn = (Button) findViewById(R.id.tween_scale_btn);
        translatebtn = (Button) findViewById(R.id.tween_translate_btn);
        multiplebtn = (Button) findViewById(R.id.tween_multiple_btn);
        alphabtn.setOnClickListener(this);
        scalebtn.setOnClickListener(this);
        translatebtn.setOnClickListener(this);
        multiplebtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == rotateimageView){
			//xml文件定义Tween Rotate动画
//			Animation animation = AnimationUtils.loadAnimation(this, R.anim.tween_rotate_animation_xml_demo);  
//			rotateimageView.startAnimation(animation);
			//TODO 利用ListView来多试用几种插值器，包括自定义的
			//通过代码来创建Tween Rotate动画，以此类推
			//逆时针转动：0- -360度，顺时针：0-360度
			Animation anim = new RotateAnimation(currAngle, currAngle - 360, Animation.RELATIVE_TO_SELF, 0.5f,  
	                Animation.RELATIVE_TO_SELF, 0.5f);  
//	        /** 匀速插值器 */  
//	        LinearInterpolator lir = new LinearInterpolator();  
	        anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.LinearInterpolator));  
	        anim.setDuration(1000);  
	        /** 动画完成后不恢复原状 */  
	       // anim.setFillAfter(true);当旋转角度不是360的整数倍时，设置这个才有想效果
	        rotateimageView.startAnimation(anim); 
		}else if(v == alphabtn){
			//xml 文件定义
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_alpha_animation_demo);
			//Animation anim = new AlphaAnimation(0, (float) 1.0);
			anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.CycleInterpolator));
			//anim.setDuration(1000);
			v.startAnimation(anim);
			
		}else if(v == scalebtn){
			//scale动画有三种方法可以调用
			//Animation anim = new ScaleAnimation(0, 200, 0, 100);
		//	Animation anim=new ScaleAnimation(1, 2,1, 2, 0.5f, 0.5f);
//			Animation anim=new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//			anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.DecelerateInterpolator));
//			anim.setDuration(1000);
			//xml文件定义
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_scale_animation_demo);
			v.startAnimation(anim);
		}else if(v == translatebtn){
//			Animation anim = new TranslateAnimation(0, 100, 0, 100);
//			anim.setDuration(1000);
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_translate_animation_demo);
			v.startAnimation(anim);
		}else if(v == multiplebtn){
			//代码定义两组动画(多个也是一样的)
//			AnimationSet animset = new AnimationSet(true);//true表示定义的几组动画都公用一个插值器
//			Animation ranim = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f,  
//	                Animation.RELATIVE_TO_SELF, 0.5f);
//			ranim.setDuration(3000);
//			animset.addAnimation(ranim);
//			Animation sanim = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//			sanim.setDuration(3000);
//			animset.addAnimation(sanim);
//			animset.setDuration(3000);
			//xml文件定义
			Animation animset = AnimationUtils.loadAnimation(this, R.anim.tween_set_animation_demo);
			v.startAnimation(animset);
		}
	}
	
}
