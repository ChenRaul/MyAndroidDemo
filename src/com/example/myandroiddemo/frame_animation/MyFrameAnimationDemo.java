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
 * @see Frame������һϵ��ͼƬ����һ����˳��չʾ�Ĺ��̣��ͷŵ�Ӱ�Ļ��ƺ����ƣ����ǳ�Ϊ��֡������
 * Frame�������Ա�������XML�ļ��У�Ҳ������ȫ����ʵ�֡�
 *�����������XML�ļ��У����ǿ��Է�����/res�µ�anim��drawableĿ¼��(/res/[anim | drawable]/filename.xml)��
 *�ļ���������Ϊ��ԴID�ڴ��������ã��������ȫ�ɱ���ʵ�֣�������Ҫʹ�õ�AnimationDrawable����
 * */
public class MyFrameAnimationDemo extends Activity implements TabListener{
	
	private ActionBar actionBar;
	private LinearLayout linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//���óɺ���
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.myframeanidemo);
		
		linearLayout = (LinearLayout) findViewById(R.id.frmae_ani_ll);
		actionBar = getActionBar();
		actionBar.setTitle("Frame����");
		//this.setTitle("dsfsdf");������Ĺ���һ��
		//Tab actionBarѡ�(һ���������ƽ����)��������������ʾ��λ�ö���һ��
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("XML��������").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("�����봴�� ����").setTabListener(this));
		
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(tab.getPosition() == 0){
			FrameAnimationXMLFragment myFragment = new FrameAnimationXMLFragment();
			FragmentManager manager = getFragmentManager();
	         FragmentTransaction transaction = manager.beginTransaction();
	         // ��Activity�е������滻�ɶ�Ӧѡ���Fragment
	         transaction.replace(R.id.frmae_ani_ll, myFragment);
	         transaction.commit();
		}else if(tab.getPosition() == 1){
			FrameAnimationCodeFragment myFragment = new FrameAnimationCodeFragment();
			FragmentManager manager = getFragmentManager();
	        FragmentTransaction transaction = manager.beginTransaction();
	         // ��Activity�е������滻�ɶ�Ӧѡ���Fragment
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
