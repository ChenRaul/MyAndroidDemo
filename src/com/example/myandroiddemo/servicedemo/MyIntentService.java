package com.example.myandroiddemo.servicedemo;

import android.app.IntentService;
import android.content.Intent;

/*�첽����IntentService
*Ĭ��Service�����������߳��ڵ� �������Service������һ����ʱ�����ͻ��������̣߳����ܵ���ANR��
*Ϊ�����ǿ�����Service���Լ��½��߳�ȥִ�к�ʱ����������Androidϵͳ������IntentService����Ľ����������⣬
* IntentService������һ�������߳�ȥ����û�onHandleIntent�ж���Ĳ�����
* ��Ҫע����Ƕ���ͬһ��IntentService�Ķ������(startService����)����ͬһ���߳��д���
* һ��ֻ��ִ��һ�������onHandleIntent���������ڲ�ͬIntentService��ͬʱ����
* �ڲ�ͬ���߳��д�������ÿ�������onHandleIntent�������Բ���ִ�С�
* 
* 
* IntentServiceֻ��Ҫ�ض���onHandleIntent����������һ���޲ι��캯��(xml�з���ע���ʼ��ʱʹ��)���ɡ�
*IntentService������onHandleIntentִ�н�������Զ��رա�
*
*
*IntentService������ResultReceiver һ��ʹ�ã�ResultReceiver��Ҫ��������Ϊ��Activity��service֮��ͨ�ţ�
*
*һ����Ҫ��Activity��������������:������������handler��handler������Ҫ�����̵߳��ã�������onReceiveResult�ص������в���ֱ�Ӳ���UI
*	private static Handler handler = new Handler(){  
    };  
    private ResultReceiver mResultReceiver = new ResultReceiver(handler){  
        @Override  
        protected void onReceiveResult(int resultCode, Bundle resultData) {  //resultCode���Լ�����Ϣ���Ͷ��壬resultData��������
            Toast.makeText(MainActivity.this, "receive " + resultCode, Toast.LENGTH_LONG).show();  
        }  
    };  
    
    Ҳ����private ResultReceiver mResultReceiver = new ResultReceiver(new Handler()){  
        @Override  
        protected void onReceiveResult(int resultCode, Bundle resultData) {  
            Toast.makeText(MainActivity.this, "receive " + resultCode, Toast.LENGTH_LONG).show();  
        }  
    }; 
*Ȼ��������Intentserviceʱ��ResultReceiver���ݸ�Intentservice����Intentservice��ͨ��mResultReceiver.send()������������Ϣ�ʹ��ݾ��������
*
*/
public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            System.out.println("IntentService1 Begin Sleep. " + "Thread name: " + Thread.currentThread().getName()
                               + ", Thread Id: " + Thread.currentThread().getId());
            Thread.sleep(10000);
            System.out.println("IntentService1 End. ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}