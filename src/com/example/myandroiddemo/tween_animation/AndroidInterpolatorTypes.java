package com.example.myandroiddemo.tween_animation;

import android.view.animation.*;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class AndroidInterpolatorTypes {
	
	//动画资源文件引用是@android:anim/accelerate_decelerate_interpolator	
	public static  int AccelerateDecelerateInterpolator = 1;//先加速再减速
	//@android:anim/accelerate_interpolator	
	public static int AccelerateInterpolator = 2;	//加速
	//@android:anim/anticipate_interpolator	
	public static int AnticipateInterpolator = 3;	//先回退一小步然后加速前进
	//@android:anim/anticipate_overshoot_interpolator
	public static int AnticipateOvershootInterpolator = 4;//在上一个基础上超出终点一小步再回到终点
	//@android:anim/bounce_interpolator
	public static int BounceInterpolator = 5;	//	最后阶段弹球效果
	//@android:anim/cycle_interpolator
	public static int CycleInterpolator = 6;	//	周期运动
	//@android:anim/decelerate_interpolator	
	public static int DecelerateInterpolator = 7;	//减速
	//@android:anim/linear_interpolator	
	public static int LinearInterpolator = 8;	//匀速
	//@android:anim/overshoot_interpolator	
	public static int OvershootInterpolator = 9;//快速到达终点并超出一小步最后回到终点
	
	public static Interpolator getInterpolator(int i){
		if(i == AccelerateDecelerateInterpolator){
			AccelerateDecelerateInterpolator adr = new AccelerateDecelerateInterpolator();
			return adr;
		}else if(i == AccelerateInterpolator){
			//可以自己设置factor加速率，也可以不设置；
			//AccelerateInterpolator ar = new AccelerateInterpolator();
			AccelerateInterpolator ar = new AccelerateInterpolator(3);
			return ar;
		}else if(i == AnticipateInterpolator){
			//可以自己设置起始点后退的张力、拉力数，也可以不设置
			//AnticipateInterpolator ar = new AnticipateInterpolator();
			AnticipateInterpolator ar = new AnticipateInterpolator(4);
			return ar;
		}else if(i == AnticipateOvershootInterpolator){
			//可以自己选择设置不同的 float tension, float extraTension的参数值，也可以不设置
			AnticipateOvershootInterpolator aor = new AnticipateOvershootInterpolator(4, 2);
			return aor;
		}else if(i == BounceInterpolator){
			BounceInterpolator bi = new BounceInterpolator();
			return bi;
		}else if(i == CycleInterpolator){
			//必须设置循环的周期个数 cycles
			CycleInterpolator ci = new CycleInterpolator(3);
			return ci;
		}else if(i == DecelerateInterpolator){
			//可以设置减速的速率，也可以不设置，使用默认值
			DecelerateInterpolator di = new DecelerateInterpolator(2);
			return di;
		}else if(i == LinearInterpolator){
			 /** 匀速插值器， */
			LinearInterpolator lir = new LinearInterpolator();
			return lir;
		}else if(i == OvershootInterpolator){
			//可以设置超出终点后的张力、拉力的值，也可以不设置
			OvershootInterpolator oi = new OvershootInterpolator(4);
			return oi;
		}
		return null;
		
	}
}
