package peng.zhang.mobilesafe01.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		//��ȡ�Ѿ������sim�����к�
		sp=context.getSharedPreferences("config",Context.MODE_PRIVATE );
		boolean protecting=sp.getBoolean("protecting", false);
		if(protecting){
			String savedSim=sp.getString("sim", "")+"afu";
			//��ȡ��ǰ�ֻ�sim�����к�
			tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String currentSim=tm.getSimSerialNumber();
			//�Ƚϵ�ǰ���Ѿ�ȡ����sim��
			if(savedSim.equals(currentSim)){
				//��ȣ�Ϊͬһ���ֻ���
			}else{
				//���ȣ����ֻ�����ͬ
				Log.d("BootCompleteReceiver", "�ֻ������");
				Toast.makeText(context, "�ֻ������", 1).show();
				String phone=sp.getString("phone", "");
				SmsManager.getDefault().sendTextMessage(phone, null, "SIM�����", null, null);
			}
		}
		
	}

}
