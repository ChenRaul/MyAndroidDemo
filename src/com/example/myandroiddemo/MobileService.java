package com.example.myandroiddemo;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Xml;

public class MobileService {  
	  
    public static String getLocation(String number,Context context) throws Exception {  
  
//        URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode="+number+"&userID=");  
       URL url = new URL("http://m.ip138.com/mobile.asp?mobile="+number);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setConnectTimeout(5 * 1000); 
        conn.setRequestProperty("Charset", "UTF-8");
        System.out.println(conn.getResponseCode());
        String string = null;
        String result = "";
        if(conn.getResponseCode()==200){
        	
        	new InputStreamReader(conn.getInputStream(), "UTF-8");  //设置编码  
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
           
            while(null != (line=br.readLine())){
                result += line;
            }
            System.out.println(result);
//        	InputStream is= conn.getInputStream();
////                	String string = convertStreamToString(is);
////                    System.out.println(string);
//            string = inputStream2String(is);
//         //   readData(is, "GBK");
//            System.out.println(string);
////                    <?xml version="1.0" encoding="utf-8"?>
////                    <string xmlns="http://WebXml.com.cn/">
////                    	15583697261：四川 南充 四川联通GSM卡
////                    </string>
//           
        }
        
        return result;  
    }
    /*将InputStream转换成String,可以看打印结果*/
    public static String convertStreamToString(InputStream is) {   
	   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
	   StringBuilder sb = new StringBuilder();   
	   String line = null; 
	   int j =0;
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line); 
                j++;
            }  
            System.out.println("j ="+j);
        } catch (IOException e) {   
            e.printStackTrace();   
        } 
        
        return sb.toString();   
    }   
    /*将InputStream转换成String，可以看打印结果*/
    public static String inputStream2String(InputStream   is)   throws   IOException{ 
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
        int i=-1; 
        int j = 0;
        while((i=is.read())!=-1){ 
        	baos.write(i); 
        	j++;
        } 
        System.out.println("j ="+j);
       return   baos.toString(); 
    }
     static class StreamUtil {  
    	   public static  byte[] load(InputStream in) throws IOException{  
    	       ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	       byte b [] = new byte[1024];  
    	       int len = -1;  
    	       while((len=in.read(b))!=-1){  
    	           baos.write(b,0,len);  
    	       }  
    	       baos.close(); 
    	       return baos.toByteArray();  
    	   }  
    	}  
}
