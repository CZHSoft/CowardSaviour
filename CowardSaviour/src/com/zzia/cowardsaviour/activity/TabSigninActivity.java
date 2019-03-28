package com.zzia.cowardsaviour.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.zzia.cowardsaviour.R;
import com.zzia.cowardsaviour.common.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TabSigninActivity extends BaseActivity {

	private Intent intent=null;
	
	private String loginNameString=null;
	private String passWordsString=null;
	
	private String loginFlag=null;
	private String loginId=null;

	private String signinRes=null;
	
	private TextView tvLoginStatus;
	private TextView tvLoginId;
	private TextView tvRes;
	private Button btnSignin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_signin);
		
		tvLoginStatus = ((TextView)findViewById(R.id.tab_signin_tv_status));
		tvLoginId = ((TextView)findViewById(R.id.tab_signin_tv_loginid));
		tvRes = ((TextView)findViewById(R.id.tab_signin_tv_operate));
		
		btnSignin = ((Button)findViewById(R.id.tab_signin_btn_signin));
		
		btnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ActivityUtil.getActivityByName("MainActivity").onSignin();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intent = getIntent();
		
		loginNameString = intent.getStringExtra("LoginName");
		passWordsString = intent.getStringExtra("PassWord");
		
		loginFlag = intent.getStringExtra("LoginFlag");
		
		if(loginFlag.equals("true"))
		{
			loginId=intent.getStringExtra("LoginID");
			tvLoginStatus.setText(String.format("当前登录状态:%s","已登录"));
			tvLoginId.setText(String.format("当前登录ID:%s",loginId));
			
			signinRes=intent.getStringExtra("SigninRes");
			
			if(signinRes.equals(""))
			{
				tvRes.setText(String.format("操作结果:%s",signinRes));
			}
			else {
				tvRes.setText(String.format("操作结果:%s(更新时间:%S)",signinRes,getStringDate()));
			}
			
		}
		else 
		{
			loginId=intent.getStringExtra("LoginID");
			tvLoginStatus.setText(String.format("当前登录状态:%s","未登录"));
			tvLoginId.setText(String.format("当前登录ID:%s","空"));
		}
	}
	
	public String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
}
