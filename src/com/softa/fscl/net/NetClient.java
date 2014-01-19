package com.softa.fscl.net;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;


public class NetClient {
	public static void post(String url,String param,NetCompliteListener listener){
//		httpclient解决中文乱码
		String result = null;
		try {
			HttpClient client=new DefaultHttpClient();
			HttpPost httppost=new HttpPost(url);
			StringEntity entity=new StringEntity(param,"utf-8");
			 httppost.getParams().setParameter("Content-Type", "application/json; encoding=utf-8");
			 httppost.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
			 httppost.setEntity(entity);
			HttpResponse res= client.execute(httppost);
			result=	NetClient.Is2String(res.getEntity().getContent());
					httppost.abort();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
//		try {
//			URL postUrl=new URL(url);
//			URLConnection conn=postUrl.openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//	        conn.setRequestProperty("accept", "*/*");
//	        conn.setRequestProperty("connection", "Keep-Alive");
//	        conn.setRequestProperty("Content-Type","application/json; encoding=utf-8");
//	        conn.setRequestProperty("charset", "utf-8");
//	        System.out.println(param);
//			OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream());
//			osw.write(param);
//			osw.flush();
//			InputStream is=conn.getInputStream();
//			result=Is2String(is);
//			osw.close();
//			is.close();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		listener.onComplite(result);
	}
	public static String  get(String url,String param,NetCompliteListener listener){
		String result = null;
		try {
			URL postUrl=new URL(url+"?"+param);
			URLConnection conn=postUrl.openConnection();
			InputStream is=conn.getInputStream();
			result=Is2String(is);
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			 result=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(listener!=null){
		listener.onComplite(result);
		}
		return result;
	}
	
	public static void main(String[] args) {
		post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN", "{}", new NetCompliteListener() {
			
			@Override
			public void onComplite(String result) {
				 
				
			}
		});
		
		get("http://www.baidu.com", "",new NetCompliteListener() {
			
			@Override
			public void onComplite(String result) {
				System.out.println(result);
			}
		});
	}
	/**
	 * inputStream 2 String
	 * @param is
	 * @return
	 */
	public static String Is2String(InputStream is,String ...charset){
		ByteArrayOutputStream bao=new ByteArrayOutputStream();
		
		int len=-1;
		byte[]buffer=new byte[1024];
		try {
			while((len=is.read(buffer))!=-1){
				bao.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		try {
			return  bao.toString(charset.length==0?"utf-8":charset[0]);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

public interface NetCompliteListener {
 void onComplite(String result);
}

}
