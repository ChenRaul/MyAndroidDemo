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
    	/*AnimatorSet用来组织动画，动画可以同时播放，顺序播放，也可以设定一定的延迟之后播放。
		playTogether()可以设置几组动画来同时播放。playSequentially() 可以设置几组动画来顺序播放。*/
    	/*动画效果采用的是 nineoldandroids-2.4.0.jar 包来实现的；
    	 * 一般来说，我们使用NineOldAndroids的属性动画时的代码大致是如下这样的:
    		ValueAnimator colorAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.3f);
    		colorAnim.setDuration(1000);
    		colorAnim.start();
    		这个动画会将myView （View的子类型）的宽度在1秒钟之内缩放到原始宽度的30%。
    	 *
    	 *
    	 *NineOldAndroids 库几乎完全兼容最新的Android 3.0 Property Animation API：
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
