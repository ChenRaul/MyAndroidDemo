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
 * ArrayAdapter<T>  ������һ�����飬֧�ַ��Ͳ���
 *	SimpleAdapter  ��������xml�ж���Ŀؼ���Ӧ������
 *	SimpleCursorAdapter  �������α�õ�������
 *	BaseAdapter  ͨ�õĻ���������
 * 
 * BaseAdapter��AndroidӦ�ó����о����õ��Ļ���������������������Ҫ��;�ǽ�һ�����ݴ�����ListView��Spinner��Gallery��GridView��UI��ʾ���
 * Android ListView��������ʱ����Ҫ��ʾ��΢���ӵĽ���ʱ������Ҫ�����Զ���һ��adapter������adapter��Ҫ�̳�BaseAdapter��
 * �������еķ���.Android��Adapter����ʵ���ǰ�����Դ�󶨵�ָ����View�ϣ�Ȼ���ٷ��ظ�View���������������View
 * ����ListView�е�ĳһ��item�����ﷵ������View���������ǵ�Adapter�е�getView�������صġ������ͻ������������
 * ������һ��һ����ʾ��ListView�еġ�*/
public class MyListViewBaseAdapter extends BaseAdapter{

	private Effectstype effect;
	 private LayoutInflater mInflater;//�õ�һ��LayoutInfalter�����������벼�� /*���캯��*/ 
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
		//����listview����������listView�ж����У�һ���Ǹ����������������������ݸ���,
		//����˴�����������arrayList������ֱ�ӷ���arrayList.size();
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// ����ListView����λ�÷���View
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//ViewHolder ��Ҫ��������ʾListViewһ��������Щ���
		ViewHolder viewHolder ;
		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item,null);
            viewHolder = new ViewHolder();
           /*�õ������ؼ��Ķ���*/                    
            viewHolder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
            viewHolder.text = (TextView) convertView.findViewById(R.id.ItemText);
            viewHolder.bt = (Button) convertView.findViewById(R.id.ItemButton);
            convertView.setTag(viewHolder);//��ViewHolder����                
        }
		else{
			viewHolder = (ViewHolder)convertView.getTag();//ȡ��ViewHolder����          
       }
		   /*����TextView��ʾ�����ݣ������Ǵ���ڶ�̬�����е�����*/             
		viewHolder.title.setText(arrayList.get(position).get("ItemTitle").toString());
		viewHolder.text.setText(arrayList.get(position).get("ItemText").toString());
		    /*ΪButton��ӵ���¼�*/             
		viewHolder.bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Toast.makeText(context, "�����˰�ť" + position, 0).show();
                	//ȡ�õ����ĶԻ������
                	 NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(context);
                	 switch (position){
                	 //ͨ���޸�effect���仯�ɲ�ͬ�Ķ���ģʽ
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
                     .withTitle("�Ի���Demo")               //.withTitle(null)  no title
                     .withTitleColor("#FFFFFF")         //def ������д��û��Ĭ��ֵ
                     .withDividerColor("#11000000")        //def ������д��û��Ĭ��ֵ
                     .withMessage("This is a modal Dialog"+ "\n"+" hhahahhahhaahhah.")     //.withMessage(null)  no Msg
                     .withMessageColor("#FFFFFFFF")    //def  | withMessageColor(int resid)
                     .withDialogColor("#FFE74C3C")       //def  | withDialogColor(int resid)     //def
                     .withIcon(context.getResources().getDrawable(R.drawable.icon))  //������д��û��Ĭ��ֵ
                    //���öԻ�����津��ȡ���Ի���Ĺ����Ƿ���Ч����������˰�ť��һ������Ϊȡ��  ��˹�����ҪҪ��
                     .isCancelableOnTouchOutside(true)     //def    | isCancelable(true)
                     .withDuration(700)         //def  ������д��û��Ĭ��ֵ
                     .withEffect(effect)         //def Effectstype.Slidetop
                     .withButton1Text("OK")         //def gone
//                     .withButton2Text("Cancel")       //def gone
                     //.setCustomView(R.layout.dialog_custom_view,v.getContext())   //.setCustomView(View or ResId,context)
                     .setButton1Click(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Toast.makeText(v.getContext(), "������OK��ť", Toast.LENGTH_SHORT).show();
                         }
                     })
//                     .setButton2Click(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View v) {
//                             Toast.makeText(v.getContext(), "������Cancel��ť", Toast.LENGTH_SHORT).show();
//                         }
//                     })
                     
                     .show();
                	 System.out.println("show()����ִ������");
                }
		 });
		 return convertView;
	}
	/*��ſؼ�*/ 
	public final class ViewHolder{
	    public TextView title;
	    public TextView text;
	    public Button   bt;
	    }

}
