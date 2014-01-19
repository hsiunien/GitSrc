package com.softa.fscl.net;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.google.gson.Gson;
import com.softa.fscl.models.BaseRequestEntity;
import com.softa.fscl.utils.CommDef;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class WebServiceClient {

	String url="http://192.168.123.1:8089/";
	//String url = "http://10.211.55.4:8089/";
	private WebServiceListener mListener;
	private String requestName = "WebServiceJson.asmx?wsdl";
	private String actionName = "HelloWorld";

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommDef.HANDLER_NET_START:
				mListener.onRequestStart();
				break;
			case CommDef.HANDLER_NET_COMPLETE:
				
				mListener.onComplete((String)msg.obj);
				break;
			case CommDef.HANDLER_NET_ERROR:
				mListener.onNetError((String)msg.obj);
				break;
			default:
				break;
			}

		};
	};

	public WebServiceClient(WebServiceListener webServiceListener,
			String requestName) {
		this.mListener = webServiceListener;
		this.requestName = requestName;
	}

	public WebServiceClient(WebServiceListener webServiceListener) {
		this.mListener = webServiceListener;
	}

	public void setRequestName(String name) {
		this.requestName = name;
	}

	public interface WebServiceListener {
		void onRequestStart();
		void onNetError(String msg);
		void onComplete(String resultJson);
	}

 
 
	/**
	 * @param actionName
	 *            请求方法名称
	 * @param paramName
	 *            参数比如 user 对应stringuser
	 * @param entity
	 */
	public void requestService(final String actionName, String paramName,
			BaseRequestEntity entity) {
		this.actionName = actionName;
		if (entity != null && paramName != null) {
			Gson gson = new Gson();
			String json = gson.toJson(entity);
			 new Thread(new NetClientThread(handler, paramName, json)).start();
		} else {
			new Thread(new NetClientThread(handler, null, null)).start();
		}

	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	class NetClientThread implements Runnable {
		private Handler mHandler;
		private String pName;
		private String pValue;
		public NetClientThread(Handler handler,String pName,String pValue) {
			mHandler = handler;
			this.pName=pName;
			this.pValue=pValue;
		}
		@Override
		public void run() {
			Message msg=handler.obtainMessage();
			msg.what=CommDef.HANDLER_NET_START;
			handler.sendMessage(msg);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			SoapObject soapObject = new SoapObject("http://tempuri.org/",
					actionName);//
			envelope.bodyOut = soapObject;
			envelope.dotNet = true;
			if(pName!=null){
			soapObject.addProperty(pName,pValue);
			}
			HttpTransportSE transport = new HttpTransportSE(url + requestName);
			try {
				transport.call(null, envelope);

				SoapObject object = (SoapObject) envelope.bodyIn;
				 msg=handler.obtainMessage();
				 msg.what=CommDef.HANDLER_NET_COMPLETE;
				msg.obj= object.getPropertyAsString(0);
				handler.sendMessage(msg);
			} catch (IOException e) {
				 msg=handler.obtainMessage();
				 msg.what=CommDef.HANDLER_NET_ERROR;
				msg.obj= "网络错误";
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
		 
		}
	}
}
