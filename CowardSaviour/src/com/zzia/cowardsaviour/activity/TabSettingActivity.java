package com.zzia.cowardsaviour.activity;

import java.util.HashMap;
import java.util.Properties;

import com.zzia.cowardsaviour.R;
import com.zzia.cowardsaviour.common.ActivityUtil;
import com.zzia.cowardsaviour.common.GPSHelper;
import com.zzia.cowardsaviour.common.PropertiesHelper;
import com.zzia.cowardsaviour.interFace.PosCallBack;
import com.zzia.cowardsaviour.interFace.TabCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TabSettingActivity extends BaseActivity 
{

	private Intent intent=null;

	private GPSHelper gpsHelper=null;
	
	//private String imeString;
	private String userNameString;
	
	private Button btnSavePro;
	private Button btnGetPos;
	
	private EditText etUN;
	private EditText etPW;
	private EditText etIME;
	private EditText etVer;
	private EditText etAdd;
	private EditText etMemo;
	private EditText etX;
	private EditText etY;
	private EditText etServer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_setting);
		
		gpsHelper=new GPSHelper(this);
		gpsHelper.setmPosCallBack(new PosCallBack() {
			
			@Override
			public void onPos(double longitude, double latitude) {
				// TODO Auto-generated method stub
				etX.setText(String.valueOf(longitude));
				etY.setText(String.valueOf(latitude));
			}
		});
		
		initView();
		
		//initData();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intent = getIntent();
		
		etUN.setText(intent.getStringExtra("LoginName"));
		etPW.setText(intent.getStringExtra("PassWord"));
		etIME.setText(intent.getStringExtra("IME"));
		etVer.setText(intent.getStringExtra("Ver"));
		etAdd.setText(intent.getStringExtra("Address"));
		etMemo.setText(intent.getStringExtra("Memo"));
		etX.setText(intent.getStringExtra("X"));
		etY.setText(intent.getStringExtra("Y"));
		etServer.setText(intent.getStringExtra("UPDATE_SERVER"));
	}
	
	private void initView()
	{

		btnSavePro = ((Button)findViewById(R.id.tab_setting_btn_SavePro));
		
		btnGetPos = ((Button)findViewById(R.id.tab_setting_btn_GetPos));
		btnGetPos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gpsHelper.GetPos();
			}
		});
		
		etUN=((EditText)findViewById(R.id.etUserName));
		
		etPW=((EditText)findViewById(R.id.etPW));
		
		etIME=((EditText)findViewById(R.id.etIME));
		//etIME.setText(imeString);
		
		etVer=((EditText)findViewById(R.id.etVer));
		
		etAdd=((EditText)findViewById(R.id.etAddress));

		etMemo=((EditText)findViewById(R.id.etMemo));
		
		etX=((EditText)findViewById(R.id.etX));
		
		etY=((EditText)findViewById(R.id.etY));
		
		etServer=((EditText)findViewById(R.id.etServer));
		
		btnSavePro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				if(mPropertiesHelper.SaveConfig(getApplicationContext(),
//						etUN.getText().toString(), etPW.getText().toString(), userNameString, 
//						etAdd.getText().toString(), etMemo.getText().toString(), 
//						etIME.getText().toString(), etVer.getText().toString(), 
//						etX.getText().toString(), etY.getText().toString()))
//				{
//					Toast.makeText(getApplicationContext(), "设置保存成功！",
//					Toast.LENGTH_LONG).show();
//				}
				
				ActivityUtil.getActivityByName("MainActivity").onSaveSetting(
						etUN.getText().toString(), 
						etPW.getText().toString(), 
						"", 
						etAdd.getText().toString(), 
						etMemo.getText().toString(), 
						etIME.getText().toString(),
						etVer.getText().toString(), 
						etX.getText().toString(), 
						etY.getText().toString(),
						etServer.getText().toString());
				
			}
		});
		
	}

	private void initData()
	{
		
	
//		if (prop != null) 
//		{
//			etUN.setText(prop.getProperty("LoginName"));
//			etPW.setText(prop.getProperty("PassWord"));
//			etVer.setText(prop.getProperty("Ver"));
//			etAdd.setText(prop.getProperty("Address"));
//			etMemo.setText(prop.getProperty("Memo"));
//			etX.setText(prop.getProperty("X"));
//			etY.setText(prop.getProperty("Y"));
//		}
//		else 
//		{
//			mPropertiesHelper.InitConfigFile(this);
//			prop = mPropertiesHelper.LoadConfig(this);
//			if(prop != null)
//			{
//				etUN.setText(prop.getProperty("LoginName"));
//				etPW.setText(prop.getProperty("PassWord"));
//				etVer.setText(prop.getProperty("Ver"));
//				etAdd.setText(prop.getProperty("Address"));
//				etMemo.setText(prop.getProperty("Memo"));
//				etX.setText(prop.getProperty("X"));
//				etY.setText(prop.getProperty("Y"));
//			}
//		}
	}
}
