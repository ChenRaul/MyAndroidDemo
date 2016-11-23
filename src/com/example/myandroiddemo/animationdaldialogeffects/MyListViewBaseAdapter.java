package com.example.myandroiddemo.animationdaldialogeffects;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myandroiddemo.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/* 
 * ArrayAdapter<T>  用来绑定一个数组，支持泛型操作
 *	SimpleAdapter  用来绑定在xml中定义的控件对应的数据
 *	SimpleCursorAdapter  用来绑定游标得到的数据
 *	BaseAdapter  通用的基础适配器
 * 
 * BaseAdapter就Android应用程序中经常用到的基础数据适配器，它的主要用途是将一组数据传到像ListView、Spinner、Gallery及GridView等UI显示组件
 * Android ListView界面中有时候需要显示稍微复杂的界面时，就需要我们自定义一个adapter，而此adapter就要继承BaseAdapter，
 * 重新其中的方法.Android中Adapter类其实就是把数据源绑定到指定的View上，然后再返回该View，而返回来的这个View
 * 就是ListView中的某一行item。这里返回来的View正是由我们的Adapter中的getView方法返回的。这样就会容易理解数据
 * 是怎样一条一条显示在ListView中的。*/
public class MyListViewBaseAdapter extends BaseAdapter{

	private Effectstype effect;
	 private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/ 
	 private ArrayList<HashMap<String, Object>> arrayList;
	private Context context;
	 public MyListViewBaseAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList) {
	     this.mInflater = LayoutInflater.from(context);
	     this.arrayList = arrayList;
	     this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//返回listview的行数，即listView有多少行，一般是根据数据数据来返回其数据个数,
		//例如此处的数据来自arrayList，所以直接返回arrayList.size();
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// 根据ListView所在位置返回View
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//ViewHolder 主要是用来表示ListView一行中有哪些组件
		ViewHolder viewHolder ;
		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item,null);
            viewHolder = new ViewHolder();
           /*得到各个控件的对象*/                    
            viewHolder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
            viewHolder.text = (TextView) convertView.findViewById(R.id.ItemText);
            viewHolder.bt = (Button) convertView.findViewById(R.id.ItemButton);
            convertView.setTag(viewHolder);//绑定ViewHolder对象                
        }
		else{
			viewHolder = (ViewHolder)convertView.getTag();//取出ViewHolder对象          
       }
		   /*设置TextView显示的内容，即我们存放在动态数组中的数据*/             
		viewHolder.title.setText(arrayList.get(position).get("ItemTitle").toString());
		viewHolder.text.setText(arrayList.get(position).get("ItemText").toString());
		    /*为Button添加点击事件*/             
		viewHolder.bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Toast.makeText(context, "你点击了按钮" + position, 0).show();
                	//取得单例的对话框对象
                	 NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(context);
                	 switch (position){
                	 //通过修改effect来变化成不同的动画模式
		                   case 0:
		                	   System.out.println("1111111111");
		                	   effect=Effectstype.Fadein;
		                	   System.out.println("2222222222");
		                	   break;
		                   case 1:effect=Effectstype.Slideright;break;
		                   case 2:effect=Effectstype.Slideleft;break;
		                   case 3:effect=Effectstype.Slidetop;break;
		                   case 4:effect=Effectstype.SlideBottom;break;
		                   case 5:effect=Effectstype.Newspager;break;
		                   case 6:effect=Effectstype.Fall;break;
		                   case 7:effect=Effectstype.Sidefill;break;
		                   case 9:effect=Effectstype.Fliph;break;
		                   case 10:effect=Effectstype.Flipv;break;
		                   case 11:effect=Effectstype.RotateBottom;break;
		                   case 12:effect=Effectstype.RotateLeft;break;
		                   case 13:effect=Effectstype.Slit;break;
		                   case 8:effect=Effectstype.Shake;break;
		                   
		                   default: break;
                	 }
                	 dialogBuilder
                     .withTitle("对话框Demo")               //.withTitle(null)  no title
                     .withTitleColor("#FFFFFF")         //def 必须填写，没有默认值
                     .withDividerColor("#11000000")        //def 必须填写，没有默认值
                     .withMessage("This is a modal Dialog"+ "\n"+" hhahahhahhaahhah.")     //.withMessage(null)  no Msg
                     .withMessageColor("#FFFFFFFF")    //def  | withMessageColor(int resid)
                     .withDialogColor("#FFE74C3C")       //def  | withDialogColor(int resid)     //def
                     .withIcon(context.getResources().getDrawable(R.drawable.icon))  //必须填写，没有默认值
                    //设置对话框界面触摸取消对话框的功能是否有效，如果设置了按钮的一个功能为取消  则此功能需要要了
                     .isCancelableOnTouchOutside(true)     //def    | isCancelable(true)
                     .withDuration(700)         //def  必须填写，没有默认值
                     .withEffect(effect)         //def Effectstype.Slidetop
                     .withButton1Text("OK")         //def gone
//                     .withButton2Text("Cancel")       //def gone
                     //.setCustomView(R.layout.dialog_custom_view,v.getContext())   //.setCustomView(View or ResId,context)
                     .setButton1Click(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Toast.makeText(v.getContext(), "你点击了OK按钮", Toast.LENGTH_SHORT).show();
                         }
                     })
//                     .setButton2Click(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View v) {
//                             Toast.makeText(v.getContext(), "你点击了Cancel按钮", Toast.LENGTH_SHORT).show();
//                         }
//                     })
                     
                     .show();
                	 System.out.println("show()函数执行完了");
                }
		 });
		 return convertView;
	}
	/*存放控件*/ 
	public final class ViewHolder{
	    public TextView title;
	    public TextView text;
	    public Button   bt;
	    }

}
