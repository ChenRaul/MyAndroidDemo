package com.example.customview.mylistview;

import com.example.myandroiddemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

public class MyListView extends ListView implements OnTouchListener,OnGestureListener{

	private GestureDetector gestureDetector;  
    private OnDeleteListener listener;  
    private View deleteButton;  
    private ViewGroup itemLayout;  
    private int selectedItem;  
  
    private boolean isDeleteShown;//删除键是否显示
	public MyListView(Context context) {
		super(context);
		gestureDetector = new GestureDetector(getContext(), this);  
        setOnTouchListener(this);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(getContext(), this);  
        setOnTouchListener(this);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		 if (!isDeleteShown) { 
			 //找到被点击的listView的行
	            selectedItem = pointToPosition((int) e.getX(), (int) e.getY());  
	        }  
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(!isDeleteShown && Math.abs(velocityX)> Math.abs(velocityY)){
			//加载删除按钮布局
			//inflate()方法一般接收两个参数，第一个参数就是要加载的布局id，第二个参数是指给该布局的外部再嵌套一层父布局，如果不需要就直接传null。
			deleteButton = LayoutInflater.from(getContext()).inflate(R.layout.cuetom_listview_button, null);
			//找到 删除按钮需要出现的 那一行，也就是custom_listview_item.xml中的RelativeLayout，
			//因为每一行都是 custom_listview_item.xml定义的一个布局，其根View是RelativeLayout，也就是为什么后面设置删除按钮的位置和大小要用RelativeLayout
			itemLayout = (ViewGroup) getChildAt(selectedItem-getFirstVisiblePosition());
			//设置 删除按钮在点击的Listview 的那一行中的大小
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//删除按钮在点击的Listview 的那一行中的右边
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);//垂直居中
			//将删除按钮添加到 点击的Listview 的那一行中去
			itemLayout.addView(deleteButton, params);
			isDeleteShown = true;
			
			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					itemLayout.removeView(deleteButton);//移除删除按钮
					deleteButton = null;
					isDeleteShown = false;
					//TODO,删除选中的这一行
					listener.onDelete(selectedItem);
				}
			});
			
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//如果删除按钮已经显示了，就将它移除掉，如果删除按钮没有显示，就使用GestureDetector来处理当前手势。
		System.out.println("OnTouch");
		System.out.println("ontouch:isDeleteShown="+isDeleteShown);
		if(isDeleteShown){
			itemLayout.removeView(deleteButton);//移除删除按钮
			deleteButton = null;
			isDeleteShown = false;
			return false;
		}else{
			return gestureDetector.onTouchEvent(event);
		}
		
	}
	 public void setOnDeleteListener(OnDeleteListener l) {  
	        listener = l;  
	 }
	 public interface OnDeleteListener{
		 void onDelete(int index);
	 }
}
