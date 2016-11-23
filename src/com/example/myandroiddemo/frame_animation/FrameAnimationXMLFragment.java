package com.example.myandroiddemo.frame_animation;

import com.example.myandroiddemo.R;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FrameAnimationXMLFragment extends Fragment{

	private AnimationDrawable anim;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ImageView imageView = (ImageView) getActivity().findViewById(R.id.frame_ani_xml_frag_image);
		imageView.setBackgroundResource(R.drawable.frame_animation_xml_demo);  
         anim = (AnimationDrawable) imageView.getBackground();  
        anim.start();
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
		super.onDestroy();
	}
	
}
