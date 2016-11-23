package com.example.myandroiddemo.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/*通过AIDL服务传递java的基本数据类型
 * 
 * 要实现AIDL服务跨进程的步骤： 首先服务端建立一个aidl后缀名的文件，
 * 		系统然后会在gen文件下自动生成一个与这个文件名字相同的.java文件（包括包名）；写一个继承Service类的类文件，在该类文件中
 * 定义一个内嵌类，这个内嵌类要继承.Stub类，.Stub类的名字与aidl文件的名字一样，然后在这个内嵌类中复写aidl文件中的方法；
 * 同时在配置文件中向这样设置该服务：
 *  <service android:name="com.example.myandroiddemo.servicedemo.MyAidlService">
            <intent-filter >
                <action android:name="com.example.myandroiddemo.servicedemo.IMyService"/>
            </intent-filter>
    </service>
 * 具体写法如此例子。
 * 	然后再客户端中：要将自动生成的与aidl文件相同的名字的.java文件（连同包一起）复制到客户端中的java源工程目录下面，然后
 * bind， 具体写法参考MyAidlServiceActivity 文件
 * 
 * */
public class MyAidlService extends Service {
	/*IMyService.Stub是根据IMyService.aidl文件自动生成的，一般不需要了解这个类
	 * 的内容，只需要编写一个继承它的类就可以了*/
	public class MyServiceImpl extends IMyService.Stub{
		@Override
		public String getValue() throws RemoteException {
				return "我是一个AIDL  Service，Service的跨进程通信:";
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MyServiceImpl();//此处必须返回上面的MyServiceImpl类的对象
	}

}
