package com.zzia.cowardsaviour.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.zzia.cowardsaviour.R;
import com.zzia.cowardsaviour.common.PropertiesHelper;
import com.zzia.cowardsaviour.common.SoapHelper;
import com.zzia.cowardsaviour.common.SoapHelper.SoapCallBack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_old extends Activity {

	private SoapHelper soapHelper;
	private TelephonyManager mTm;
	private PropertiesHelper mPropertiesHelper;
	
	private String imeString;
	private String userNameString;
	private String signinString;
	private Button btnLogin;
	private Button btnSignIn;
	private Button btnSavePro;
	
	private EditText etUN;
	private EditText etPW;
	private EditText etIME;
	private EditText etVer;
	private EditText etAdd;
	private EditText etMemo;
	private EditText etX;
	private EditText etY;
	
	private TextView tvLogininfo;
	private TextView tvSIRes;
	
	public static Handler handler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_old);
		
		userNameString="";
		
		InitHandler();
		
		soapHelper=new SoapHelper();
		soapHelper.setOnSoapCallBack(new SoapCallBack() {
			
			@Override
			public void getSignInResult(String result, boolean normal) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), result,
//						Toast.LENGTH_LONG).show();
				
				signinString=result;
				
				Message msg1 = new Message();
				msg1.what = 2;
				MainActivity_old.handler.sendMessage(msg1);
				
			}
			
			@Override
			public void getLoginResult(String result, boolean normal) {
				// TODO Auto-generated method stub
				userNameString=result;

				
				Message msg1 = new Message();
				msg1.what = 1;
				MainActivity_old.handler.sendMessage(msg1);
				
			}
		});
		
		mTm = ((TelephonyManager)getSystemService("phone"));
		imeString=mTm.getDeviceId();
		
		btnLogin = ((Button)findViewById(R.id.btnLogin));
		btnSignIn = ((Button)findViewById(R.id.btnSignIn));
		btnSavePro = ((Button)findViewById(R.id.tab_setting_btn_SavePro));
		
		etUN=((EditText)findViewById(R.id.etUserName));
		
		etPW=((EditText)findViewById(R.id.etPW));
		
		etIME=((EditText)findViewById(R.id.etIME));
		etIME.setText(imeString);
		
		etVer=((EditText)findViewById(R.id.etVer));
		
		etAdd=((EditText)findViewById(R.id.etAddress));

		etMemo=((EditText)findViewById(R.id.etMemo));
		
		etX=((EditText)findViewById(R.id.etX));
		
		etY=((EditText)findViewById(R.id.etY));
		
		tvLogininfo= ((TextView)findViewById(R.id.tvLoginInfo));
		
		tvSIRes= ((TextView)findViewById(R.id.tvSIRes));
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String str1 = etUN.getText().toString() + etPW.getText().toString() + "kingoonline";
				String str3 = soapHelper.md5(str1);
				
				Log.i("un", etUN.getText().toString());
				Log.i("pw", etPW.getText().toString());
				Log.i("key", str3);
				
				
				soapHelper.lnString=etUN.getText().toString();
				soapHelper.pwString=etPW.getText().toString();
				soapHelper.key1String=soapHelper.md5(str3);
				soapHelper.DoUserLogin();
				
			}
		});
		
		btnSignIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String str8 = userNameString + 
						imeString + 
						etVer.getText().toString() + 
						etX.getText().toString() + 
						etY.getText().toString() + 
						"kingoonline";
				
				String str9=soapHelper.md5(str8);
				
				soapHelper.addString=etAdd.getText().toString();
				soapHelper.memoString=etMemo.getText().toString();
				soapHelper.imeString=etIME.getText().toString();
				soapHelper.verString=etVer.getText().toString();
				soapHelper.xString=etX.getText().toString();
				soapHelper.yString=etY.getText().toString();
				soapHelper.key2String=str9;
				
				soapHelper.DoSignIn();
				
			}
		});
		
		btnSavePro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mPropertiesHelper.SaveConfig(getApplicationContext(),
						etUN.getText().toString(), etPW.getText().toString(), userNameString, 
						etAdd.getText().toString(), etMemo.getText().toString(), 
						etIME.getText().toString(), etVer.getText().toString(), 
						etX.getText().toString(), etY.getText().toString(),""))
				{
					Toast.makeText(getApplicationContext(), "设置保存成功！",
					Toast.LENGTH_LONG).show();
				}
				

				
			}
		});
		
		mPropertiesHelper = new PropertiesHelper();
		Properties prop = null;
		
		try {
			prop=mPropertiesHelper.LoadConfig(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		if (prop != null) 
		{
			etUN.setText(prop.getProperty("LoginName"));
			etPW.setText(prop.getProperty("PassWord"));
			etVer.setText(prop.getProperty("Ver"));
			etAdd.setText(prop.getProperty("Address"));
			etMemo.setText(prop.getProperty("Memo"));
			etX.setText(prop.getProperty("X"));
			etY.setText(prop.getProperty("Y"));
		}
		else 
		{
			mPropertiesHelper.InitConfigFile(this);
			prop = mPropertiesHelper.LoadConfig(this);
			if(prop != null)
			{
				etUN.setText(prop.getProperty("LoginName"));
				etPW.setText(prop.getProperty("PassWord"));
				etVer.setText(prop.getProperty("Ver"));
				etAdd.setText(prop.getProperty("Address"));
				etMemo.setText(prop.getProperty("Memo"));
				etX.setText(prop.getProperty("X"));
				etY.setText(prop.getProperty("Y"));
			}
		}
	}
	

	/**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
	public String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	private void InitHandler() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) 
				{
				case 1:
					Log.i("Handler", "1");
					tvLogininfo.setText(getStringDate()+" 登录状态:(LoginID)"+userNameString);
					break;
				case 2:
					Log.i("Handler", "2");
					tvSIRes.setText(getStringDate()+" 签到结果:"+signinString);
					Toast.makeText(getApplicationContext(), signinString,
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}

			}

		};
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{

			if(event.getRepeatCount() == 0)
			{
			   Toast.makeText(getApplicationContext(), "长按退出！",
						Toast.LENGTH_SHORT).show();
			   
			}
			else
			{
				finish();
			}
			return true;
		}
		else if(keyCode == KeyEvent.KEYCODE_HOME)
		{

		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
