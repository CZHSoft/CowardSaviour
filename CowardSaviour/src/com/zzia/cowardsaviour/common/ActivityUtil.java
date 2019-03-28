package com.zzia.cowardsaviour.common;

import java.util.ArrayList;

import com.zzia.cowardsaviour.interFace.TabCallBack;


/**
 * π§æﬂ¿‡
 * @author 
 *
 */
public class ActivityUtil {

	private static ArrayList<TabCallBack> allActivity;
	
	public static void resetActivity() {
		allActivity=null;
		allActivity = new ArrayList<TabCallBack>();
	}
	
	public static void addActivity(TabCallBack iw) {
		allActivity.add(iw);
	}
	
	public static TabCallBack getActivityByName(String name) {
		System.out.println("ssss"+allActivity);
		for (TabCallBack iw : allActivity) {
			if (iw.getClass().getName().indexOf(name) >= 0) {
				return iw;
			}
		}
		return null;
	}
}
