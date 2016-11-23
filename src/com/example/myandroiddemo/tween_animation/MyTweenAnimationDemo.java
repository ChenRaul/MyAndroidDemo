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
 * @see Tween�����ǲ���ĳ��View�ؼ�����չ�ֳ���ת�����䡢�ƶ������ŵ���ôһ��ת�����̣����ǳ�Ϊ���䶯����
 * ���ǿ�����XML��ʽ���嶯����Ҳ���Ա���ʵ�֡�
*�����XML��ʽ����һ�����������ǰ��ն����Ķ����﷨���XML����������/res/animĿ¼�£��ļ���������Ϊ��ԴID�����ã�
*����ɱ���ʵ�֣�������Ҫʹ�õ�Animation����
*ÿ������������ѡ��ͬ�Ĳ�ֵ���������ͻ�������в�ͬ��Ч������
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
		// TODO ��ȥ��д�Զ���Ķ���
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
			//xml�ļ�����Tween Rotate����
//			Animation animation = AnimationUtils.loadAnimation(this, R.anim.tween_rotate_animation_xml_demo);  
//			rotateimageView.startAnimation(animation);
			//TODO ����ListView�������ü��ֲ�ֵ���������Զ����
			//ͨ������������Tween Rotate�������Դ�����
			//��ʱ��ת����0- -360�ȣ�˳ʱ�룺0-360��
			Animation anim = new RotateAnimation(currAngle, currAngle - 360, Animation.RELATIVE_TO_SELF, 0.5f,  
	                Animation.RELATIVE_TO_SELF, 0.5f);  
//	        /** ���ٲ�ֵ�� */  
//	        LinearInterpolator lir = new LinearInterpolator();  
	        anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.LinearInterpolator));  
	        anim.setDuration(1000);  
	        /** ������ɺ󲻻ָ�ԭ״ */  
	       // anim.setFillAfter(true);����ת�ǶȲ���360��������ʱ���������������Ч��
	        rotateimageView.startAnimation(anim); 
		}else if(v == alphabtn){
			//xml �ļ�����
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_alpha_animation_demo);
			//Animation anim = new AlphaAnimation(0, (float) 1.0);
			anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.CycleInterpolator));
			//anim.setDuration(1000);
			v.startAnimation(anim);
			
		}else if(v == scalebtn){
			//scale���������ַ������Ե���
			//Animation anim = new ScaleAnimation(0, 200, 0, 100);
		//	Animation anim=new ScaleAnimation(1, 2,1, 2, 0.5f, 0.5f);
//			Animation anim=new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//			anim.setInterpolator(AndroidInterpolatorTypes.getInterpolator(AndroidInterpolatorTypes.DecelerateInterpolator));
//			anim.setDuration(1000);
			//xml�ļ�����
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_scale_animation_demo);
			v.startAnimation(anim);
		}else if(v == translatebtn){
//			Animation anim = new TranslateAnimation(0, 100, 0, 100);
//			anim.setDuration(1000);
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.tween_translate_animation_demo);
			v.startAnimation(anim);
		}else if(v == multiplebtn){
			//���붨�����鶯��(���Ҳ��һ����)
//			AnimationSet animset = new AnimationSet(true);//true��ʾ����ļ��鶯��������һ����ֵ��
//			Animation ranim = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f,  
//	                Animation.RELATIVE_TO_SELF, 0.5f);
//			ranim.setDuration(3000);
//			animset.addAnimation(ranim);
//			Animation sanim = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//			sanim.setDuration(3000);
//			animset.addAnimation(sanim);
//			animset.setDuration(3000);
			//xml�ļ�����
			Animation animset = AnimationUtils.loadAnimation(this, R.anim.tween_set_animation_demo);
			v.startAnimation(animset);
		}
	}
	
}
