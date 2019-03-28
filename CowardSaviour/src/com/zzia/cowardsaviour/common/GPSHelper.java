package com.zzia.cowardsaviour.common;

import com.zzia.cowardsaviour.interFace.PosCallBack;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GPSHelper 
{
	private Context context;
	private double latitude=0.0;//纬度
	private double longitude =0.0;//经度
	
	private PosCallBack mPosCallBack;

	public void setmPosCallBack(PosCallBack mPosCallBack) {
		this.mPosCallBack = mPosCallBack;
	}
	
	public GPSHelper(Context parent) 
	{
		context=parent;

	}
	
	public void GetData()
	{
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location != null){
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				}
		}
		else
		{
			LocationListener locationListener = new LocationListener() {
				
				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
				// Provider被enable时触发此函数，比如GPS被打开
				@Override
				public void onProviderEnabled(String provider) {
					
				}
				
				// Provider被disable时触发此函数，比如GPS被关闭 
				@Override
				public void onProviderDisabled(String provider) {
					
				}
				
				//当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {   
						Log.e("Map", "Location changed : Lat: "  
						+ location.getLatitude() + " Lng: "  
						+ location.getLongitude());   
					}
				}

	
			};
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);   
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
			if(location != null){   
				latitude = location.getLatitude(); //经度   
				longitude = location.getLongitude(); //纬度
			}   
		}
	}

	public void GetPos()
	{
		//声明LocationManager对象
        LocationManager loctionManager;
        String contextService=Context.LOCATION_SERVICE;
        //通过系统服务，取得LocationManager对象
        loctionManager=(LocationManager) context.getSystemService(contextService);
        
        //通过GPS位置提供器获得位置
//        String provider=LocationManager.GPS_PROVIDER;
//        Location location = loctionManager.getLastKnownLocation(provider);
        
        //使用标准集合，让系统自动选择可用的最佳位置提供器，提供位置
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
        
        //从可用的位置提供器中，匹配以上标准的最佳提供器
        String provider = loctionManager.getBestProvider(criteria, true);
        
        //获得最后一次变化的位置
        Location location = loctionManager.getLastKnownLocation(provider);
        
        if(location!=null){
			double lat=location.getLatitude();
			double lng=location.getLongitude();
//			latLongString = "Lat(纬度): "+lat+"\nLong(经度): "+lng;
			mPosCallBack.onPos(lng, lat);
		}else{
//			latLongString="没找到位置";
		}
        
        
        //监听位置变化，2秒一次，距离10米以上
        loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}
	
    //位置监听器
    private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
		//当位置变化时触发
		@Override
		public void onLocationChanged(Location location) {
			if(location!=null){
				double lat=location.getLatitude();
				double lng=location.getLongitude();
//				latLongString = "Lat(纬度): "+lat+"\nLong(经度): "+lng;
				mPosCallBack.onPos(lng, lat);
			}else{
//				latLongString="没找到位置";
			}
		}
	};
}
