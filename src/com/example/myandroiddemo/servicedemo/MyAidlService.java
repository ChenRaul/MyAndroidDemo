package com.example.myandroiddemo.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/*ͨ��AIDL���񴫵�java�Ļ�����������
 * 
 * Ҫʵ��AIDL�������̵Ĳ��裺 ���ȷ���˽���һ��aidl��׺�����ļ���
 * 		ϵͳȻ�����gen�ļ����Զ�����һ��������ļ�������ͬ��.java�ļ���������������дһ���̳�Service������ļ����ڸ����ļ���
 * ����һ����Ƕ�࣬�����Ƕ��Ҫ�̳�.Stub�࣬.Stub���������aidl�ļ�������һ����Ȼ���������Ƕ���и�дaidl�ļ��еķ�����
 * ͬʱ�������ļ������������ø÷���
 *  <service android:name="com.example.myandroiddemo.servicedemo.MyAidlService">
            <intent-filter >
                <action android:name="com.example.myandroiddemo.servicedemo.IMyService"/>
            </intent-filter>
    </service>
 * ����д��������ӡ�
 * 	Ȼ���ٿͻ����У�Ҫ���Զ����ɵ���aidl�ļ���ͬ�����ֵ�.java�ļ�����ͬ��һ�𣩸��Ƶ��ͻ����е�javaԴ����Ŀ¼���棬Ȼ��
 * bind�� ����д���ο�MyAidlServiceActivity �ļ�
 * 
 * */
public class MyAidlService extends Service {
	/*IMyService.Stub�Ǹ���IMyService.aidl�ļ��Զ����ɵģ�һ�㲻��Ҫ�˽������
	 * �����ݣ�ֻ��Ҫ��дһ���̳�������Ϳ�����*/
	public class MyServiceImpl extends IMyService.Stub{
		@Override
		public String getValue() throws RemoteException {
				return "����һ��AIDL  Service��Service�Ŀ����ͨ��:";
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MyServiceImpl();//�˴����뷵�������MyServiceImpl��Ķ���
	}

}
