package com.example.myandroiddemo.frame_animation;

import com.example.myandroiddemo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * @see Frame动画是一系列图片按照一定的顺序展示的过程，和放电影的机制很相似，我们称为逐帧动画。
 * Frame动画可以被定义在XML文件中，也可以完全编码实现。
 *如果被定义在XML文件中，我们可以放置在/res下的anim或drawable目录中(/res/[anim | drawable]/filename.xml)，
 *文件名可以作为资源ID在代码中引用；如果由完全由编码实现，我们需要使用到AnimationDrawable对象。
 * */
public class MyFrameAnimationDemo extends Activity implements TabListener{
	
	private ActionBar actionBar;
	private LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置成横屏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.myframeanidemo);
		
		linearLayout = (LinearLayout) findViewById(R.id.frmae_ani_ll);
		actionBar = getActionBar();
		actionBar.setTitle("Frame动画");
		//this.setTitle("dsfsdf");与上面的功能一样
		//Tab actionBar选项卡(一般横屏或者平板用)，横屏与竖屏显示的位置都不一样
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("XML创建动画").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("纯代码创建 动画").setTabListener(this));
		
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(tab.getPosition() == 0){
			FrameAnimationXMLFragment myFragment = new FrameAnimationXMLFragment();
			FragmentManager manager = getFragmentManager();
	         FragmentTransaction transaction = manager.beginTransaction();
	         // 将Activity中的内容替换成对应选择的Fragment
	         transaction.replace(R.id.frmae_ani_ll, myFragment);
	         transaction.commit();
		}else if(tab.getPosition() == 1){
			FrameAnimationCodeFragment myFragment = new FrameAnimationCodeFragment();
			FragmentManager manager = getFragmentManager();
	        FragmentTransaction transaction = manager.beginTransaction();
	         // 将Activity中的内容替换成对应选择的Fragment
	        transaction.replace(R.id.frmae_ani_ll, myFragment);
	        transaction.commit();
		}
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
}
