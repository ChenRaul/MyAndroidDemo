package com.example.myandroiddemo.animationdaldialogeffects;

import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/*
 * Copyright 2014 litao
 * https://github.com/sd6352051/NiftyDialogEffects
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
    	/*AnimatorSet������֯��������������ͬʱ���ţ�˳�򲥷ţ�Ҳ�����趨һ�����ӳ�֮�󲥷š�
		playTogether()�������ü��鶯����ͬʱ���š�playSequentially() �������ü��鶯����˳�򲥷š�*/
    	/*����Ч�����õ��� nineoldandroids-2.4.0.jar ����ʵ�ֵģ�
    	 * һ����˵������ʹ��NineOldAndroids�����Զ���ʱ�Ĵ������������������:
    		ValueAnimator colorAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.3f);
    		colorAnim.setDuration(1000);
    		colorAnim.start();
    		��������ὫmyView ��View�������ͣ��Ŀ����1����֮�����ŵ�ԭʼ��ȵ�30%��
    	 *
    	 *
    	 *NineOldAndroids �⼸����ȫ�������µ�Android 3.0 Property Animation API��
			AnimatorSet animatorSet = new AnimatorSet(); 
			animatorSet.playTogether( 
			    ObjectAnimator.ofFloat(myView, "rotationX", 0, 360), 
			    ObjectAnimator.ofFloat(myView, "rotationY", 0, 180), 
			    ObjectAnimator.ofFloat(myView, "rotation", 0, -90), 
			    ObjectAnimator.ofFloat(myView, "translationX", 0, 90), 
			    ObjectAnimator.ofFloat(myView, "translationY", 0, 90), 
			    ObjectAnimator.ofFloat(myView, "scaleX", 1, 1.5f), 
			    ObjectAnimator.ofFloat(myView, "scaleY", 1, 0.5f), 
			    ObjectAnimator.ofFloat(myView, "alpha", 1, 0.25f, 1) 
			); 
			animatorSet.setDuration(5 * 1000).start();
    	 * */
        getAnimatorSet().playTogether( ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration));
        System.out.println("66666666666");
    }
}
