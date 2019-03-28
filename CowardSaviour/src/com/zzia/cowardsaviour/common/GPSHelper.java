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
	private double latitude=0.0;//γ��
	private double longitude =0.0;//����
	
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
				
				// Provider��״̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
				// Provider��enableʱ�����˺���������GPS����
				@Override
				public void onProviderEnabled(String provider) {
					
				}
				
				// Provider��disableʱ�����˺���������GPS���ر� 
				@Override
				public void onProviderDisabled(String provider) {
					
				}
				
				//������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ���� 
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
				latitude = location.getLatitude(); //����   
				longitude = location.getLongitude(); //γ��
			}   
		}
	}

	public void GetPos()
	{
		//����LocationManager����
        LocationManager loctionManager;
        String contextService=Context.LOCATION_SERVICE;
        //ͨ��ϵͳ����ȡ��LocationManager����
        loctionManager=(LocationManager) context.getSystemService(contextService);
        
        //ͨ��GPSλ���ṩ�����λ��
//        String provider=LocationManager.GPS_PROVIDER;
//        Location location = loctionManager.getLastKnownLocation(provider);
        
        //ʹ�ñ�׼���ϣ���ϵͳ�Զ�ѡ����õ����λ���ṩ�����ṩλ��
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//�߾���
        criteria.setAltitudeRequired(false);//��Ҫ�󺣰�
        criteria.setBearingRequired(false);//��Ҫ��λ
        criteria.setCostAllowed(true);//�����л���
        criteria.setPowerRequirement(Criteria.POWER_LOW);//�͹���
        
        //�ӿ��õ�λ���ṩ���У�ƥ�����ϱ�׼������ṩ��
        String provider = loctionManager.getBestProvider(criteria, true);
        
        //������һ�α仯��λ��
        Location location = loctionManager.getLastKnownLocation(provider);
        
        if(location!=null){
			double lat=location.getLatitude();
			double lng=location.getLongitude();
//			latLongString = "Lat(γ��): "+lat+"\nLong(����): "+lng;
			mPosCallBack.onPos(lng, lat);
		}else{
//			latLongString="û�ҵ�λ��";
		}
        
        
        //����λ�ñ仯��2��һ�Σ�����10������
        loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}
	
    //λ�ü�����
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
		//��λ�ñ仯ʱ����
		@Override
		public void onLocationChanged(Location location) {
			if(location!=null){
				double lat=location.getLatitude();
				double lng=location.getLongitude();
//				latLongString = "Lat(γ��): "+lat+"\nLong(����): "+lng;
				mPosCallBack.onPos(lng, lat);
			}else{
//				latLongString="û�ҵ�λ��";
			}
		}
	};
}
