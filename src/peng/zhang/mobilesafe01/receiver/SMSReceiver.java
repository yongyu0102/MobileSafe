package peng.zhang.mobilesafe01.receiver;

import peng.zhang.mobilesafe01.R;
import peng.zhang.mobilesafe01.servicer.GPSService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		//获取广播信息的intent中携带所有短信内容
		//intent.getExtras()返回一个buandle类型数据
		Object [] objects=(Object[]) intent.getExtras().get("pdus");
		//取出每一条短信
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		for(Object b :objects){
			@SuppressWarnings("deprecation")
			SmsMessage sms =SmsMessage.createFromPdu((byte[]) b);
			//获取发送者号码
			String adress=sms.getOriginatingAddress();
			//获取短息内容
			String message=sms.getMessageBody();
			//每取出一条短息根据获取内容进行逻辑判断与处理
			String safaNumber=sp.getString("phone", "");
			//由于模拟器获取号码会在真实号码比如5554前加入1555554等数据所以判断，取出号码包含
			//安全号码即认为是安全号码发送过来的
			Log.d(TAG, adress);
			if(adress.contains(safaNumber)){
				if("#*location*#".equals(message)){
					Log.d(TAG, "得到手机的GPS");
					//接受到短信启动GPS服务
					
					Intent intent1=new Intent(context,GPSService.class);
					context.startService(intent1);
					//获取GPS服务存取的位置信息
				
					SharedPreferences sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
					String location=sp.getString("lastlocation", null);
					Log.d("Location", "Location2"+location);
					if(TextUtils.isEmpty(location)){
						SmsManager.getDefault().sendTextMessage(adress, null, "geting lastlocation... ", null, null);
					}else{
						SmsManager.getDefault().sendTextMessage(adress, null,  location, null, null);
					}
					//终止短信继续传播
					abortBroadcast();
				}else if ("#*alarm*#".equals(message)){
					//播放报警影音
					Log.i(TAG, "播放报警影音");
					
					
				//通过资源文件调用播放器
					MediaPlayer player= MediaPlayer.create(context, R.raw.ylzs);
					//设置为循环播放
					player.setLooping(false);
					//设置声音为最大
					player.setVolume(1.0f, 1.0f);
					player.start();
					abortBroadcast();
				}else if ("#*wipedata*#".equals(message)){
					//远程清除数据
					Log.i(TAG, "远程清除数据");
					abortBroadcast();
				}else if("#*lockscreen*#".equals(message)){
					//远程锁屏
					Log.i(TAG, "远程锁屏");
					abortBroadcast();
				}
			}
			
		}
		
		

	}

}
