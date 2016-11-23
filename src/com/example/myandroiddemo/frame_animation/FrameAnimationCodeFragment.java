package com.example.myandroiddemo.frame_animation;

import com.example.myandroiddemo.R;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class FrameAnimationCodeFragment extends Fragment{

	private AnimationDrawable anim;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ImageView imageView = (ImageView) getActivity().findViewById(R.id.frame_ani_xml_frag_image);
//		imageView.setBackgroundResource(R.drawable.frame_animation_xml_demo);  
//        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();  
//        anim.start();
		 anim = new AnimationDrawable();  
	        for (int i = 1; i <= 4; i++) {  //从 drawable中循环获取命名有规律的资源文件
	            //根据资源名称和目录获取R.java中对应的资源ID  
	            int id = getResources().getIdentifier("test"+i,"drawable", getActivity().getPackageName());  
	            //根据资源ID获取到Drawable对象  
	            Drawable drawable = getResources().getDrawable(id);  
	            //将此帧添加到AnimationDrawable中  
	            anim.addFrame(drawable, 300);  
	        }  
	        anim.setOneShot(false); //设置为loop  
	        imageView.setBackgroundDrawable(anim);  //将动画设置为ImageView背景  
	        anim.start();   //开始动画  
	      //  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frameanimationxmlfragment, container,false);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		anim.stop();
		Toast.makeText(getActivity(), "FrameCodeAnimationFranment destory", 0).show();
		super.onDestroy();
	}
	
}
