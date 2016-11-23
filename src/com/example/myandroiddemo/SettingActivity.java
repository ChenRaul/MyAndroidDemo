package com.example.myandroiddemo;

import com.example.myandroiddemo.gifplay.AndroidGifDemoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener,  
Preference.OnPreferenceChangeListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencescreen_demo);
		//TODO继
		Preference preference = findPreference("preference");//获取Preference节点对象
	}
	@Override
	@Deprecated
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		System.out.println(preference.getKey());
		if(preference.getKey().equals("preference")){
			Toast.makeText(this, "你点击了Preference1", 0).show();
			Intent intent = new Intent(this, AndroidGifDemoActivity.class);
			startActivity(intent);
		}
		return true;
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		System.out.println(preference.getKey()+"1");
		if(preference.getKey().equals("preference")){
			Toast.makeText(this, "你点击了Preference2", 0).show();
//			Intent intent = new Intent(this, AndroidGifDemoActivity.class);
//			startActivity(intent);
		}
		return true;
	}
	@Override
	public boolean onPreferenceClick(Preference preference) {
		System.out.println(preference.getKey()+"2");
		if(preference.getKey().equals("preference")){
			Toast.makeText(this, "你点击了Preference3", 0).show();
//			Intent intent = new Intent(this, AndroidGifDemoActivity.class);
//			startActivity(intent);
		}
		return true;
	}
}
