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
  
    private boolean isDeleteShown;//ɾ�����Ƿ���ʾ
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
			 //�ҵ��������listView����
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
			//����ɾ����ť����
			//inflate()����һ�����������������һ����������Ҫ���صĲ���id���ڶ���������ָ���ò��ֵ��ⲿ��Ƕ��һ�㸸���֣��������Ҫ��ֱ�Ӵ�null��
			deleteButton = LayoutInflater.from(getContext()).inflate(R.layout.cuetom_listview_button, null);
			//�ҵ� ɾ����ť��Ҫ���ֵ� ��һ�У�Ҳ����custom_listview_item.xml�е�RelativeLayout��
			//��Ϊÿһ�ж��� custom_listview_item.xml�����һ�����֣����View��RelativeLayout��Ҳ����Ϊʲô��������ɾ����ť��λ�úʹ�СҪ��RelativeLayout
			itemLayout = (ViewGroup) getChildAt(selectedItem-getFirstVisiblePosition());
			//���� ɾ����ť�ڵ����Listview ����һ���еĴ�С
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//ɾ����ť�ڵ����Listview ����һ���е��ұ�
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);//��ֱ����
			//��ɾ����ť��ӵ� �����Listview ����һ����ȥ
			itemLayout.addView(deleteButton, params);
			isDeleteShown = true;
			
			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					itemLayout.removeView(deleteButton);//�Ƴ�ɾ����ť
					deleteButton = null;
					isDeleteShown = false;
					//TODO,ɾ��ѡ�е���һ��
					listener.onDelete(selectedItem);
				}
			});
			
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//���ɾ����ť�Ѿ���ʾ�ˣ��ͽ����Ƴ��������ɾ����ťû����ʾ����ʹ��GestureDetector������ǰ���ơ�
		System.out.println("OnTouch");
		System.out.println("ontouch:isDeleteShown="+isDeleteShown);
		if(isDeleteShown){
			itemLayout.removeView(deleteButton);//�Ƴ�ɾ����ť
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
