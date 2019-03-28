package com.zzia.cowardsaviour.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.zzia.cowardsaviour.R;
import com.zzia.cowardsaviour.common.ActivityUtil;
import com.zzia.cowardsaviour.interFace.TabCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TabLoginActivity extends BaseActivity {

	private Intent intent=null;
	
	private String loginNameString=null;
	private String passWordsString=null;
	
	private String loginFlag=null;
	private String loginId=null;
	
	private TextView tvLoginStatus;
	private TextView tvLoginId;
	private Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_login);
		
		tvLoginStatus= ((TextView)findViewById(R.id.tab_login_tv_status));
		tvLoginId=((TextView)findViewById(R.id.tab_login_tv_loginid));
		
		btnLogin = ((Button)findViewById(R.id.tab_login_btn_login));
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ActivityUtil.getActivityByName("MainActivity").onLogin();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intent = getIntent();
		
		String date_nowString=getStringDate();
		
		loginNameString = intent.getStringExtra("LoginName");
		passWordsString = intent.getStringExtra("PassWord");
		
		loginFlag = intent.getStringExtra("LoginFlag");
		
		if(loginFlag.equals("true"))
		{
			loginId=intent.getStringExtra("LoginID");
			tvLoginStatus.setText(String.format("当前登录状态:%s(更新时间:%s)","已登录",date_nowString));
			tvLoginId.setText(String.format("当前登录ID:%s",loginId));
		}
		else {
			loginId=intent.getStringExtra("LoginID");
			tvLoginStatus.setText(String.format("当前登录状态:%s(更新时间:%s)","未登录",date_nowString));
			tvLoginId.setText(String.format("当前登录ID:%s","空"));
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
	
}
