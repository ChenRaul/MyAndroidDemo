package com.example.myandroiddemo.tween_animation;

import android.view.animation.*;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class AndroidInterpolatorTypes {
	
	//������Դ�ļ�������@android:anim/accelerate_decelerate_interpolator	
	public static  int AccelerateDecelerateInterpolator = 1;//�ȼ����ټ���
	//@android:anim/accelerate_interpolator	
	public static int AccelerateInterpolator = 2;	//����
	//@android:anim/anticipate_interpolator	
	public static int AnticipateInterpolator = 3;	//�Ȼ���һС��Ȼ�����ǰ��
	//@android:anim/anticipate_overshoot_interpolator
	public static int AnticipateOvershootInterpolator = 4;//����һ�������ϳ����յ�һС���ٻص��յ�
	//@android:anim/bounce_interpolator
	public static int BounceInterpolator = 5;	//	���׶ε���Ч��
	//@android:anim/cycle_interpolator
	public static int CycleInterpolator = 6;	//	�����˶�
	//@android:anim/decelerate_interpolator	
	public static int DecelerateInterpolator = 7;	//����
	//@android:anim/linear_interpolator	
	public static int LinearInterpolator = 8;	//����
	//@android:anim/overshoot_interpolator	
	public static int OvershootInterpolator = 9;//���ٵ����յ㲢����һС�����ص��յ�
	
	public static Interpolator getInterpolator(int i){
		if(i == AccelerateDecelerateInterpolator){
			AccelerateDecelerateInterpolator adr = new AccelerateDecelerateInterpolator();
			return adr;
		}else if(i == AccelerateInterpolator){
			//�����Լ�����factor�����ʣ�Ҳ���Բ����ã�
			//AccelerateInterpolator ar = new AccelerateInterpolator();
			AccelerateInterpolator ar = new AccelerateInterpolator(3);
			return ar;
		}else if(i == AnticipateInterpolator){
			//�����Լ�������ʼ����˵���������������Ҳ���Բ�����
			//AnticipateInterpolator ar = new AnticipateInterpolator();
			AnticipateInterpolator ar = new AnticipateInterpolator(4);
			return ar;
		}else if(i == AnticipateOvershootInterpolator){
			//�����Լ�ѡ�����ò�ͬ�� float tension, float extraTension�Ĳ���ֵ��Ҳ���Բ�����
			AnticipateOvershootInterpolator aor = new AnticipateOvershootInterpolator(4, 2);
			return aor;
		}else if(i == BounceInterpolator){
			BounceInterpolator bi = new BounceInterpolator();
			return bi;
		}else if(i == CycleInterpolator){
			//��������ѭ�������ڸ��� cycles
			CycleInterpolator ci = new CycleInterpolator(3);
			return ci;
		}else if(i == DecelerateInterpolator){
			//�������ü��ٵ����ʣ�Ҳ���Բ����ã�ʹ��Ĭ��ֵ
			DecelerateInterpolator di = new DecelerateInterpolator(2);
			return di;
		}else if(i == LinearInterpolator){
			 /** ���ٲ�ֵ���� */
			LinearInterpolator lir = new LinearInterpolator();
			return lir;
		}else if(i == OvershootInterpolator){
			//�������ó����յ���������������ֵ��Ҳ���Բ�����
			OvershootInterpolator oi = new OvershootInterpolator(4);
			return oi;
		}
		return null;
		
	}
}
