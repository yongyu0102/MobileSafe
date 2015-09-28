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
		
		//为内容提供者设置条件
				Criteria criteria=new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//设置参数细化：
		//criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置为最大精度 
		//criteria.setAltitudeRequired(false);//不要求海拔信息 
		//criteria.setBearingRequired(false);//不要求方位信息 
		//criteria.setCostAllowed(true);//是否允许付费 
		//criteria.setPowerRequirement(Criteria.POWER_LOW);//对电量的要求
		//设置位置更新监听器，需要传入参数为定位方式、更新时间（一般一分钟），更新距离（一般50米），监听器
	
		//设置获取最好的位置提供器
		listener =new MyLocationListener();
		String provider=lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider,0, 0, listener);
		Log.d("Location", "Location0"+"启动服务");
	}
	//服务销毁，取消注册
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lm.removeUpdates(listener);
		listener=null;
	}
	
	class  MyLocationListener implements LocationListener{

		/**
		 * 当位置改变的时候回调
		 */
		@Override
		public void onLocationChanged(Location location) {
			Log.d("Location", "Location1"+"MyLocationListener执行了");
			String longitude = "jj:" + location.getLongitude() + "\n";
			String latitude = "w:" + location.getLatitude() + "\n";
			String accuracy = "a" + location.getAccuracy() + "\n";
			Log.d("Location", "Location1"+longitude);
			//将标准坐标转换为火星坐标
			//实例化ModifyOffset需要输入一个输入流，这个输入流既是读取数据库流
//			InputStream in = null;
//			try {
//				in=getResources().getAssets().open("axisoffset.dat");
//				ModifyOffset offset=ModifyOffset.getInstance(in);
//				//将标准坐标转换为火星坐标
//				PointDouble double1=offset.s2c(new PointDouble(location.getLongitude(), location.getLatitude()));
//				//转换完成，取出坐标
//				longitude ="j:" + offset.X+ "\n";
//				latitude =  "w:" +offset.Y+ "\n";
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//对得到的经纬度进行保存
			SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();
		}
		/**
		 * 当状态发生改变的时候回调 开启--关闭 ；关闭--开启
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * 某一个位置提供者可以使用了
		 */
		@Override
		public void onProviderEnabled(String provider) {
			
		}

		/**
		 * 某一个位置提供者不可以使用了
		 */
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	}
		

}
