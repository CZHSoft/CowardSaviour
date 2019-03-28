package com.zzia.cowardsaviour.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.R.string;
import android.content.Context;
import android.util.Log;

//写入配置：
//Properties prop = new Properties();
//prop.put("prop1", "abc");
//prop.put("prop2", 1);
//prop.put("prop3", 3.14);
//saveConfig(this, "/sdcard/config.dat", prop);
//
//读取配置：
//Properties prop = loadConfig(this, "/sdcard/config.dat");
//String prop1 = prop.get("prop1");

public class PropertiesHelper 
{
	public Properties LoadConfig(Context context) {
		
		Properties properties = new Properties();
		
		String propertiesPath = context.getFilesDir().getPath().toString()
				+ "/app.properties";
		
		Log.i("LoadConfig", propertiesPath);
		
		FileInputStream s=null;
		
		try 
		{
			
			s = new FileInputStream(propertiesPath);
			Log.i("LoadConfig", "1");
			properties.load(s);
			Log.i("LoadConfig", "2");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Log.i("LoadConfig", "fail");
			properties=null;
		}
		finally
		{
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}

	public boolean SaveConfig(Context context,
			String ln,String pw,String un,
			String add,String memo,String ime,String ver,String x,String y,
			String server){
		
		String propertiesPath = context.getFilesDir().getPath().toString()
				+ "/app.properties";
		Properties prop = new Properties();
		prop.put("LoginName", ln);
		prop.put("PassWord", pw);
		prop.put("UserName", un);
		prop.put("Address", add);
		prop.put("Memo", memo);
		prop.put("Ime", ime);
		prop.put("Ver", ver);
		prop.put("X", x);
		prop.put("Y", y);
		prop.put("UPDATE_SERVER", server);
		
		FileOutputStream s=null;
		
		try {
			s = new FileOutputStream(propertiesPath, false);
			prop.store(s, "");
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean InitConfigFile(Context context) {
		String propertiesPath = context.getFilesDir().getPath().toString()
				+ "/app.properties";

		Properties prop = new Properties();
		prop.put("LoginName", "登录名");
		prop.put("PassWord", "密码");
		prop.put("UserName", "");
		prop.put("Address", "地址");
		prop.put("Memo", "备注信息");
		prop.put("Ime", "");
		prop.put("Ver", "1.07");
		prop.put("X", "经度");
		prop.put("Y", "维度");
		prop.put("UPDATE_SERVER", "http://222.240.230.42:801");
		
		FileOutputStream s=null;
		
		try {
			s = new FileOutputStream(propertiesPath, false);
			prop.store(s, "");
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
