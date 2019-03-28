package com.zzia.cowardsaviour.interFace;

import java.util.HashMap;

public interface TabCallBack {
	void onLogin();
	void onSignin();
	void onSaveSetting(String ln,String pw,String un,
			String add,String memo,String ime,String ver,String x,String y,String server);
}
