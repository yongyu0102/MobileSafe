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
		//读取已经保存的sim卡序列号
		sp=context.getSharedPreferences("config",Context.MODE_PRIVATE );
		boolean protecting=sp.getBoolean("protecting", false);
		if(protecting){
			String savedSim=sp.getString("sim", "")+"afu";
			//读取当前手机sim卡序列号
			tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String currentSim=tm.getSimSerialNumber();
			//比较当前和已经取出的sim号
			if(savedSim.equals(currentSim)){
				//相等，为同一个手机卡
			}else{
				//不等，则手机卡不同
				Log.d("BootCompleteReceiver", "手机卡变更");
				Toast.makeText(context, "手机卡变更", 1).show();
				String phone=sp.getString("phone", "");
				SmsManager.getDefault().sendTextMessage(phone, null, "SIM卡变更", null, null);
			}
		}
		
	}

}
