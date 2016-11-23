package com.example.myandroiddemo.animationdaldialogeffects;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myandroiddemo.R;


/*����Ч�����õ��� nineoldandroids-2.4.0.jar ������
 * 
 * һ����˵������ʹ��NineOldAndroids�����Զ���ʱ�Ĵ������������������:
	ValueAnimator colorAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.3f);
	colorAnim.setDuration(1000);
	colorAnim.start();
	��������ὫmyView ��View�������ͣ��Ŀ����1����֮�����ŵ�ԭʼ��ȵ�30%��
 * 
 *  �����jar����ʹ�÷��������ϲ�ѯʹ��
 *  http://codekk.com/open-source-project-analysis/detail/Android/Mr.Simple/NineOldAnimations%20Դ�����
 * */
public class AnimationEffectsDialogActivity extends Activity{

    private Effectstype effect;
    private ListView mListView;
    private ArrayList<HashMap<String, Object>> arrayList;
    private HashMap<String, Object> map;
    private String[] textString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationeaffectssdialog);
        
        mListView = (ListView) findViewById(R.id.listview);
        textString=new String[]{"Fade IN","Slide Right","Slide Left","Slide Top","Slide Bottom","NewsPaper","Fall",
        		"Side Fall","Shake","3D Flip(horizontal)","3D Flip(vertical)","3D Rotate Bottom",
        		"3D Rotate Left","3D Slit"};
        arrayList = new ArrayList<HashMap<String,Object>>();
        for(int i= 0;i< textString.length;i++){
        	initData(textString[i]);
        }
        MyListViewBaseAdapter mAdapter = new MyListViewBaseAdapter(this,arrayList);//�õ�һ��MyAdapter����
        
        //ΪListView��Adapter /*ΪListView��ӵ���¼�*/ 
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
        	/*��Button������ListView�Ľ��㣬��Ҫ��Button����Ϊû�н��㡣���÷ǳ��򵥣�
        	 * ֻ�轫���������е���ƥ���layout xml�ļ����˴����ֽ�listview_item.xml���е�Button��ǩ��
        	 * ����һ�У�android:focusable=��false������Ϳ�����,����listView�ĵ��ʱ��Ҳ�ͻ������*/
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    	Toast.makeText(AnimationEffectsDialogActivity.this, "������ListView��Ŀ"+arg2, 0).show();
                    }
                });

    }
    private void initData(String string){
    	HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("ItemTitle", "����Ч����");  
        map.put("ItemText",string);  
        arrayList.add(map);
    }
//    public void dialogShow(View v){
//        NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
//
////        switch (v.getId()){
////            case R.id.fadein:effect=Effectstype.Fadein;break;
////            case R.id.slideright:effect=Effectstype.Slideright;break;
////            case R.id.slideleft:effect=Effectstype.Slideleft;break;
////            case R.id.slidetop:effect=Effectstype.Slidetop;break;
////            case R.id.slideBottom:effect=Effectstype.SlideBottom;break;
////            case R.id.newspager:effect=Effectstype.Newspager;break;
////            case R.id.fall:effect=Effectstype.Fall;break;
////            case R.id.sidefall:effect=Effectstype.Sidefill;break;
////            case R.id.fliph:effect=Effectstype.Fliph;break;
////            case R.id.flipv:effect=Effectstype.Flipv;break;
////            case R.id.rotatebottom:effect=Effectstype.RotateBottom;break;
////            case R.id.rotateleft:effect=Effectstype.RotateLeft;break;
////            case R.id.slit:effect=Effectstype.Slit;break;
////            case R.id.shake:effect=Effectstype.Shake;break;
////        }
//
//        dialogBuilder
//                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
//                .withTitleColor("#FFFFFF")                                  //def
//                .withDividerColor("#11000000")                              //def
//                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
//                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
//                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
//                .withIcon(getResources().getDrawable(R.drawable.icon))
//                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
//                .withDuration(700)                                          //def
//                .withEffect(effect)                                         //def Effectstype.Slidetop
//                .withButton1Text("OK")                                      //def gone
//                .withButton2Text("Cancel")                                  //def gone
//                .setCustomView(R.layout.dialog_custom_view,v.getContext())         //.setCustomView(View or ResId,context)
//                .setButton1Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setButton2Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();
//
//    }



    }
