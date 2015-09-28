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
		//��ȡ�㲥��Ϣ��intent��Я�����ж�������
		//intent.getExtras()����һ��buandle��������
		Object [] objects=(Object[]) intent.getExtras().get("pdus");
		//ȡ��ÿһ������
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		for(Object b :objects){
			@SuppressWarnings("deprecation")
			SmsMessage sms =SmsMessage.createFromPdu((byte[]) b);
			//��ȡ�����ߺ���
			String adress=sms.getOriginatingAddress();
			//��ȡ��Ϣ����
			String message=sms.getMessageBody();
			//ÿȡ��һ����Ϣ���ݻ�ȡ���ݽ����߼��ж��봦��
			String safaNumber=sp.getString("phone", "");
			//����ģ������ȡ���������ʵ�������5554ǰ����1555554�����������жϣ�ȡ���������
			//��ȫ���뼴��Ϊ�ǰ�ȫ���뷢�͹�����
			Log.d(TAG, adress);
			if(adress.contains(safaNumber)){
				if("#*location*#".equals(message)){
					Log.d(TAG, "�õ��ֻ���GPS");
					//���ܵ���������GPS����
					
					Intent intent1=new Intent(context,GPSService.class);
					context.startService(intent1);
					//��ȡGPS�����ȡ��λ����Ϣ
				
					SharedPreferences sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
					String location=sp.getString("lastlocation", null);
					Log.d("Location", "Location2"+location);
					if(TextUtils.isEmpty(location)){
						SmsManager.getDefault().sendTextMessage(adress, null, "geting lastlocation... ", null, null);
					}else{
						SmsManager.getDefault().sendTextMessage(adress, null,  location, null, null);
					}
					//��ֹ���ż�������
					abortBroadcast();
				}else if ("#*alarm*#".equals(message)){
					//���ű���Ӱ��
					Log.i(TAG, "���ű���Ӱ��");
					
					
				//ͨ����Դ�ļ����ò�����
					MediaPlayer player= MediaPlayer.create(context, R.raw.ylzs);
					//����Ϊѭ������
					player.setLooping(false);
					//��������Ϊ���
					player.setVolume(1.0f, 1.0f);
					player.start();
					abortBroadcast();
				}else if ("#*wipedata*#".equals(message)){
					//Զ���������
					Log.i(TAG, "Զ���������");
					abortBroadcast();
				}else if("#*lockscreen*#".equals(message)){
					//Զ������
					Log.i(TAG, "Զ������");
					abortBroadcast();
				}
			}
			
		}
		
		

	}

}
