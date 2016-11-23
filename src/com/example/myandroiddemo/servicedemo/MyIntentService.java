package com.example.myandroiddemo.servicedemo;

import android.app.IntentService;
import android.content.Intent;

/*异步服务IntentService
*默认Service是运行在主线程内的 ，如果在Service内运行一个耗时操作就会阻塞主线程，可能导致ANR，
*为此我们可以在Service中自己新建线程去执行耗时操作，不过Android系统引入了IntentService方便的解决了这个问题，
* IntentService会启动一个工作线程去完成用户onHandleIntent中定义的操作，
* 需要注意的是对于同一个IntentService的多次请求(startService调用)，在同一个线程中处理，
* 一次只会执行一个请求的onHandleIntent函数。对于不同IntentService的同时请求，
* 在不同的线程中处理，所以每个请求的onHandleIntent函数可以并发执行。
* 
* 
* IntentService只需要重定义onHandleIntent函数并定义一个无参构造函数(xml中服务注册初始化时使用)即可。
*IntentService服务在onHandleIntent执行结束后会自动关闭。
*
*
*IntentService经常和ResultReceiver 一起使用，ResultReceiver主要是用来作为在Activity和service之间通信，
*
*一般需要在Activity中向下面这样做:但无论是哪种handler，handler都必须要在主线程调用，这样在onReceiveResult回调函数中才能直接操作UI
*	private static Handler handler = new Handler(){  
    };  
    private ResultReceiver mResultReceiver = new ResultReceiver(handler){  
        @Override  
        protected void onReceiveResult(int resultCode, Bundle resultData) {  //resultCode是自己的消息类型定义，resultData则是数据
            Toast.makeText(MainActivity.this, "receive " + resultCode, Toast.LENGTH_LONG).show();  
        }  
    };  
    
    也可以private ResultReceiver mResultReceiver = new ResultReceiver(new Handler()){  
        @Override  
        protected void onReceiveResult(int resultCode, Bundle resultData) {  
            Toast.makeText(MainActivity.this, "receive " + resultCode, Toast.LENGTH_LONG).show();  
        }  
    }; 
*然后在启动Intentservice时将ResultReceiver传递给Intentservice，在Intentservice中通过mResultReceiver.send()方法来传递消息和传递具体的数据
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