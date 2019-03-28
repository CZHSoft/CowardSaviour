package com.zzia.cowardsaviour.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import com.zzia.cowardsaviour.R;
import com.zzia.cowardsaviour.common.ActivityUtil;
import com.zzia.cowardsaviour.common.MediaPlayerHelper;
import com.zzia.cowardsaviour.common.PropertiesHelper;
import com.zzia.cowardsaviour.common.SoapHelper;
import com.zzia.cowardsaviour.common.SoapHelper.SoapCallBack;
import com.zzia.cowardsaviour.interFace.TabCallBack;

import android.R.string;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity implements TabCallBack {
    /** Called when the activity is first created. */
//	private MediaPlayerHelper mMediaPlayerHelper; 
	private TelephonyManager mTm;
	private PropertiesHelper mPropertiesHelper;
	private SoapHelper soapHelper;
	
	private TabHost tabHost;
	private Intent tabLoginIntent, tabSigninIntent, tabSettingIntent, tabAboutIntent;
	private ImageButton ibtnLogo;
	private ImageButton ibtnFun;
//	private TextView main_tab_new_message;//��ɫ����
	
	private String unString=null;
	private String pwString=null;
	private String uidString=null;
	private String imeString=null;
	private String verString=null;
	private String addString=null;
	private String memoString=null;
	private String xString=null;
	private String yString=null;
	private String serverString = null;
	
	private String signinString=null;
	
	public static Handler handler = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        
//        main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
//        main_tab_new_message.setVisibility(View.VISIBLE);
//        main_tab_new_message.setText("10");
        
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

				MainActivity.handler.sendMessage(msg1);
				
			}
			
			@Override
			public void getLoginResult(String result, boolean normal) {
				// TODO Auto-generated method stub
				
				if(normal)
				{
					uidString=result;
	
					Message msg1 = new Message();
					msg1.what = 1;
					
					MainActivity.handler.sendMessage(msg1);
				}
				else {
					
					Message msg1 = new Message();
					msg1.what = 11;
					msg1.obj = result;
					
					MainActivity.handler.sendMessage(msg1);
				}
				
			}
		});
        
        
//        mMediaPlayerHelper=new MediaPlayerHelper(this, R.raw.splash_mp3);
		
//        mMediaPlayerHelper.play();
        
		mTm = ((TelephonyManager)getSystemService("phone"));
		imeString=mTm.getDeviceId();
        
		ActivityUtil.resetActivity();
		ActivityUtil.addActivity(this);
        
		
		InitHandler();
		initView();
		initDialog();
		initData();
        
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
					
					reflashTab(R.id.main_tab_login);
					
					//tvLogininfo.setText(getStringDate()+" ��¼״̬:(LoginID)"+userNameString);
					
					break;
				case 11:
					
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					
					Log.i("Handler", "1");
					
					reflashTab(R.id.main_tab_login);
					
					break;
					
				case 2:
					Log.i("Handler", "2");
					
					reflashTab(R.id.main_tab_signin);
					
//					signinString="";
					
//					tvSIRes.setText(getStringDate()+" ǩ�����:"+signinString);
//					Toast.makeText(getApplicationContext(), signinString,
//							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}

		};
	}
    
    private void initView()
    {
    	ibtnLogo = ((ImageButton)findViewById(R.id.title_main_btn_logo));
        ibtnLogo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				mMediaPlayerHelper.play();
			}
		});
        ibtnFun=((ImageButton)findViewById(R.id.title_main_btn_fun));
        ibtnFun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;

        tabLoginIntent = new Intent().setClass(this, TabLoginActivity.class);
        spec=tabHost.newTabSpec("��¼").setIndicator("��¼").setContent(tabLoginIntent);
        tabHost.addTab(spec);
        
        tabSigninIntent=new Intent().setClass(this,TabSigninActivity.class);
        spec=tabHost.newTabSpec("ǩ��").setIndicator("ǩ��").setContent(tabSigninIntent);
        tabHost.addTab(spec);
        
        tabSettingIntent=new Intent().setClass(this, TabSettingActivity.class);
        spec=tabHost.newTabSpec("����").setIndicator("����").setContent(tabSettingIntent);
        tabHost.addTab(spec);
        
        tabAboutIntent=new Intent().setClass(this, TabAboutActivity.class);
        spec=tabHost.newTabSpec("����").setIndicator("����").setContent(tabAboutIntent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTabByTag("����");
//        tabHost.setCurrentTab(3);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				reflashTab(checkedId);
			}
		});
    }
    
    private void reflashTab(int checkedId)
    {
    	switch (checkedId) 
    	{
		case R.id.main_tab_login://
			
			if(uidString == null || uidString.length() <= 0)
			{
				tabLoginIntent.putExtra("LoginFlag", "false");
				tabLoginIntent.putExtra("LoginID", "");
				Toast.makeText(getApplicationContext(), "��ǰδ��¼��",
						Toast.LENGTH_LONG).show();
			}
			else
			{
				tabLoginIntent.putExtra("LoginFlag", "true");
				tabLoginIntent.putExtra("LoginID", uidString);
				Toast.makeText(getApplicationContext(), "��ǰ�ѵ�¼��",
						Toast.LENGTH_LONG).show();
			}
			
			tabLoginIntent.putExtra("LoginName", unString);
			tabLoginIntent.putExtra("PassWord", pwString);

			tabHost.setCurrentTabByTag("��¼");
			break;
		case R.id.main_tab_signin://
			
			if(uidString == null || uidString.length() <= 0)
			{
				tabSigninIntent.putExtra("LoginFlag", "false");
				tabSigninIntent.putExtra("LoginID", "");
				Toast.makeText(getApplicationContext(), "��ǰδ��¼��",
						Toast.LENGTH_LONG).show();
			}
			else
			{
				tabSigninIntent.putExtra("LoginFlag", "true");
				tabSigninIntent.putExtra("LoginID", uidString);
//				Toast.makeText(getApplicationContext(), "��ǰ�ѵ�¼��",
//						Toast.LENGTH_LONG).show();
				
				if(signinString == null || signinString.length() <= 0)
				{
					tabSigninIntent.putExtra("SigninRes", "");
				}
				else {
					tabSigninIntent.putExtra("SigninRes", signinString);
					
					Toast.makeText(getApplicationContext(), "�Ѿ�ǩ����",
					   Toast.LENGTH_LONG).show();
					
				}
			}
			
			tabSigninIntent.putExtra("LoginName", unString);
			tabSigninIntent.putExtra("PassWord", pwString);
			
			tabHost.setCurrentTabByTag("ǩ��");

			break;
		case R.id.main_tab_settings://
			
			tabSettingIntent.putExtra("LoginName", unString);
			tabSettingIntent.putExtra("PassWord", pwString);
			tabSettingIntent.putExtra("IME", imeString);
			tabSettingIntent.putExtra("Ver", verString);
			tabSettingIntent.putExtra("Address", addString);
			tabSettingIntent.putExtra("Memo", memoString);
			tabSettingIntent.putExtra("X", xString);
			tabSettingIntent.putExtra("Y", yString);
			tabSettingIntent.putExtra("UPDATE_SERVER", serverString);
			
			tabHost.setCurrentTabByTag("����");
//			Toast.makeText(getApplicationContext(), "������ã�",
//					Toast.LENGTH_LONG).show();
			break;
		case R.id.main_tab_about://
			tabHost.setCurrentTabByTag("����");
//			Toast.makeText(getApplicationContext(), "������ã�",
//					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
    }
    
    private void initData()
    {
    	if(mPropertiesHelper==null)
    	{
    		mPropertiesHelper = new PropertiesHelper();
    	}
    	
		Properties prop = null;
		
		try {
			prop=mPropertiesHelper.LoadConfig(this);
		} 
		catch(Exception e) 
		{
			// TODO: handle exception
			Toast.makeText(MainActivity.this, 
					"���������쳣��", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		if (prop != null) 
		{
			unString=prop.getProperty("LoginName");
			pwString=prop.getProperty("PassWord");
			verString=prop.getProperty("Ver");
			addString=prop.getProperty("Address");
			memoString=prop.getProperty("Memo");
			xString=prop.getProperty("X");
			yString=prop.getProperty("Y");
			serverString=prop.getProperty("UPDATE_SERVER");
			
		}
		else 
		{
			mPropertiesHelper.InitConfigFile(this);
			prop = mPropertiesHelper.LoadConfig(this);
			if(prop != null)
			{
				unString=prop.getProperty("LoginName");
				pwString=prop.getProperty("PassWord");
				verString=prop.getProperty("Ver");
				addString=prop.getProperty("Address");
				memoString=prop.getProperty("Memo");
				xString=prop.getProperty("X");
				yString=prop.getProperty("Y");
				serverString = prop.getProperty("UPDATE_SERVER");
			}
			else {
				Toast.makeText(MainActivity.this, 
						"���������쳣��", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		
		SoapHelper.UPDATE_SERVER= serverString;
    }
    
    private void initDialog()
    {
    	final EditText editText = new EditText(MainActivity.this);
    	
    	AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.czhoft_logo)
				.setTitle("���ʶ��ϵͳ").setMessage("�����������")
				.setView(editText).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(!editText.getText().toString().equals("kingo"))
						{
							Toast.makeText(MainActivity.this, 
									"�Ƿ��û������������", Toast.LENGTH_SHORT).show();
							
//							mMediaPlayerHelper.stop();
							
							finish();
						}
						
						//Toast.makeText(MainActivity.this, "������������ǣ�"+editText.getText(), Toast.LENGTH_SHORT).show();
					}
				}).create();
    	
//    	Window window = dialog.getWindow();  
//        WindowManager.LayoutParams lp = window.getAttributes();  
//    	lp.alpha = 1.0f;  
//    	lp.dimAmount =1.0f;
//    	window.setAttributes(lp); 
//
//    	WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//    	lp.dimAmount =0f;
//    	dialog.getWindow().setAttributes(lp);
 	
    	dialog.setCancelable(false);
    	dialog.show();

    }

	@Override
	public void onLogin() {
		// TODO Auto-generated method stub
		if((unString == null || unString.length()<=0) || (pwString==null || pwString.length()<=0))
		{
			Toast.makeText(MainActivity.this, 
					"�û��������벻��Ϊ��,�������õ��û���������!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		soapHelper.lnString=unString;
		soapHelper.pwString=pwString;
		soapHelper.key1String=soapHelper.md5(unString+pwString+"kingoonline");
		soapHelper.DoUserLogin();
		
//		Toast.makeText(MainActivity.this, 
//				"��¼!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSignin() {
		// TODO Auto-generated method stub
		
		if((uidString == null || uidString.length()<=0))
		{
			Toast.makeText(MainActivity.this, 
					"��¼״̬������Ҫ�������Ƿ��Ѿ����е�¼����!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String str8 = uidString + 
				imeString + 
				verString + 
				xString + 
				yString + 
				"kingoonline";
		
		String str9=soapHelper.md5(str8);
		
		soapHelper.addString=addString;
		soapHelper.memoString=memoString;
		soapHelper.imeString=imeString;
		soapHelper.verString=verString;
		soapHelper.xString=xString;
		soapHelper.yString=yString;
		soapHelper.key2String=str9;
		
		soapHelper.DoSignIn();

//		Toast.makeText(MainActivity.this, 
//				"ǩ��!", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSaveSetting(String ln,String pw,String un,
			String add,String memo,String ime,String ver,String x,String y,
			String server) {
		// TODO Auto-generated method stub
		
		if(mPropertiesHelper.SaveConfig(this, ln, pw, un, add, memo, ime, ver, x, y,server))
		{
			initData();
			
			reflashTab(R.id.main_tab_settings);
			
			Toast.makeText(MainActivity.this, 
					"���ñ���ɹ�!", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(MainActivity.this, 
					"���ñ���ʧ��!", Toast.LENGTH_SHORT).show();
		}
		
	}

    
}