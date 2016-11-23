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
	        for (int i = 1; i <= 4; i++) {  //�� drawable��ѭ����ȡ�����й��ɵ���Դ�ļ�
	            //������Դ���ƺ�Ŀ¼��ȡR.java�ж�Ӧ����ԴID  
	            int id = getResources().getIdentifier("test"+i,"drawable", getActivity().getPackageName());  
	            //������ԴID��ȡ��Drawable����  
	            Drawable drawable = getResources().getDrawable(id);  
	            //����֡��ӵ�AnimationDrawable��  
	            anim.addFrame(drawable, 300);  
	        }  
	        anim.setOneShot(false); //����Ϊloop  
	        imageView.setBackgroundDrawable(anim);  //����������ΪImageView����  
	        anim.start();   //��ʼ����  
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
