package com.zzia.cowardsaviour.common;

import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.widget.Toast;

public class SoapHelper {
	
	public static String UPDATE_SERVER = "http://ww.kingosoft.com:801";
	//public static String UPDATE_SERVER = "http://113.247.233.82:801/";
	private static final String METHODNAME = "UserLogin";
	private static final String NAMESPACE = "http://e.kingosoft.com/";
	private static String URL = UPDATE_SERVER + "/Android/Login.asmx";
	private static String URL2 = UPDATE_SERVER + "/Android/SPT/PersonSignin.asmx";
	public String lnString;
	public String pwString;
	public String key1String;

	public String res1String;

	public String unString;
	public String addString;
	public String memoString;
	public String imeString;
	public String verString;
	public String xString;
	public String yString;
	public String key2String;

	public String res2String;

	public interface SoapCallBack {
		public void getLoginResult(String result, boolean normal);

		public void getSignInResult(String result, boolean normal);
	}

	private SoapCallBack mSoapCallBack;

	public void setOnSoapCallBack(SoapCallBack soapCallBack) {
		this.mSoapCallBack = soapCallBack;
	}

	public String UserLogin(String paramString1, String paramString2,
			String paramString3) {
		SoapObject localSoapObject = new SoapObject("http://e.kingosoft.com/",
				"UserLogin");
		localSoapObject.addProperty("UID", paramString1);
		localSoapObject.addProperty("password", paramString2);
		localSoapObject.addProperty("key", paramString3);
		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(
				110);
		localSoapSerializationEnvelope.dotNet = true;
		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
		
		URL = String.format("%s/Android/Login.asmx", UPDATE_SERVER);
		
		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
		try {
			localHttpTransportSE.call("http://e.kingosoft.com/UserLogin",
					localSoapSerializationEnvelope);

			String str = ((SoapObject) localSoapSerializationEnvelope.bodyIn).getProperty(0).toString();
			
			return str;
		} catch (SoapFault localSoapFault) {
			localSoapFault.printStackTrace();
			return null;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
			return null;
		} catch (XmlPullParserException localXmlPullParserException) {
			localXmlPullParserException.printStackTrace();
			System.out.print(localXmlPullParserException.getMessage());
			return null;
		}
		
	}

	public String SignIn(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7, String paramString8) {
		SoapObject localSoapObject = new SoapObject("http://e.kingosoft.com/",
				"SignIn");
		localSoapObject.addProperty("loginId", paramString1);
		localSoapObject.addProperty("addr", paramString2);
		localSoapObject.addProperty("memo", paramString3);
		localSoapObject.addProperty("IME", paramString4);
		localSoapObject.addProperty("Ver", paramString5);
		localSoapObject.addProperty("positionX", paramString6);
		localSoapObject.addProperty("positionY", paramString7);
		localSoapObject.addProperty("key", paramString8);
		SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(
				110);
		localSoapSerializationEnvelope.dotNet = true;
		localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
		
		URL2 = String.format("%s/Android/SPT/PersonSignin.asmx", UPDATE_SERVER);
		
		HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL2);
		try {
			localHttpTransportSE.call("http://e.kingosoft.com/SignIn",
					localSoapSerializationEnvelope);
			String str = ((SoapObject) localSoapSerializationEnvelope.bodyIn)
					.getProperty(0).toString();
//			SoapPrimitive result = (SoapPrimitive) localSoapSerializationEnvelope.getResponse();
			
			return str;
		} catch (SoapFault localSoapFault) {
			localSoapFault.printStackTrace();
			
			return null;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
			return null;
		} catch (XmlPullParserException localXmlPullParserException) {
			localXmlPullParserException.printStackTrace();
		}
		return null;
	}

	public String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	private class GetUserLogin implements Runnable {
		@Override
		public void run() {
			try {
				res1String = UserLogin(lnString, pwString, key1String);
				Log.i("UserLogin", res1String);
				unString = res1String;

				mSoapCallBack.getLoginResult(unString, true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				mSoapCallBack.getLoginResult("µÇÂ¼Ê§°Ü£¬Çë¼ì²é·þÎñÆ÷¶Ë¿Ú¡¢µØÖ·...", false);
				
			}
		}
	}

	public void DoUserLogin() {
		new Thread(new GetUserLogin()).start();
	}

	private class GetSignIn implements Runnable {
		@Override
		public void run() {
			try {
				res2String = SignIn(unString, addString, memoString, imeString,
						verString, xString, yString, key2String);
				Log.i("SignIn", res2String);
				mSoapCallBack.getSignInResult(res2String, true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void DoSignIn() {
		new Thread(new GetSignIn()).start();
	}

}
