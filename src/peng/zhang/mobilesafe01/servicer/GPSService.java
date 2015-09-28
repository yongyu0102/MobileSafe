package peng.zhang.mobilesafe01.servicer;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service {
	private LocationManager lm;
	private MyLocationListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		lm=(LocationManager) getSystemService(LOCATION_SERVICE);
		/**
		 * List<String> providers=lm.getAllProviders();
		 for(String pv :providers){
			Log.d(TAG, pv);
		 }
		 */
		
		//Ϊ�����ṩ����������
				Criteria criteria=new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//���ò���ϸ����
		//criteria.setAccuracy(Criteria.ACCURACY_FINE);//����Ϊ��󾫶� 
		//criteria.setAltitudeRequired(false);//��Ҫ�󺣰���Ϣ 
		//criteria.setBearingRequired(false);//��Ҫ��λ��Ϣ 
		//criteria.setCostAllowed(true);//�Ƿ������� 
		//criteria.setPowerRequirement(Criteria.POWER_LOW);//�Ե�����Ҫ��
		//����λ�ø��¼���������Ҫ�������Ϊ��λ��ʽ������ʱ�䣨һ��һ���ӣ������¾��루һ��50�ף���������
	
		//���û�ȡ��õ�λ���ṩ��
		listener =new MyLocationListener();
		String provider=lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider,0, 0, listener);
		Log.d("Location", "Location0"+"��������");
	}
	//�������٣�ȡ��ע��
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lm.removeUpdates(listener);
		listener=null;
	}
	
	class  MyLocationListener implements LocationListener{

		/**
		 * ��λ�øı��ʱ��ص�
		 */
		@Override
		public void onLocationChanged(Location location) {
			Log.d("Location", "Location1"+"MyLocationListenerִ����");
			String longitude = "jj:" + location.getLongitude() + "\n";
			String latitude = "w:" + location.getLatitude() + "\n";
			String accuracy = "a" + location.getAccuracy() + "\n";
			Log.d("Location", "Location1"+longitude);
			//����׼����ת��Ϊ��������
			//ʵ����ModifyOffset��Ҫ����һ����������������������Ƕ�ȡ���ݿ���
//			InputStream in = null;
//			try {
//				in=getResources().getAssets().open("axisoffset.dat");
//				ModifyOffset offset=ModifyOffset.getInstance(in);
//				//����׼����ת��Ϊ��������
//				PointDouble double1=offset.s2c(new PointDouble(location.getLongitude(), location.getLatitude()));
//				//ת����ɣ�ȡ������
//				longitude ="j:" + offset.X+ "\n";
//				latitude =  "w:" +offset.Y+ "\n";
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//�Եõ��ľ�γ�Ƚ��б���
			SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();
		}
		/**
		 * ��״̬�����ı��ʱ��ص� ����--�ر� ���ر�--����
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * ĳһ��λ���ṩ�߿���ʹ����
		 */
		@Override
		public void onProviderEnabled(String provider) {
			
		}

		/**
		 * ĳһ��λ���ṩ�߲�����ʹ����
		 */
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	}
		

}
