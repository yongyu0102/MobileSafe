package peng.zhang.mobilesafe01;

import peng.zhang.mobilesafe01.ui.SettingItemView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import peng.zhang.mobilesafe01.BaseSetupActivity;

public class SetuoTwoActivity extends BaseSetupActivity {
	
	/**
	 * 读取手机sim的信息
	 */
	private TelephonyManager tm;
	/**
	 * 声明一个SettingItemView
	 */
	private SettingItemView siv_setup2_sim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_setup);
		siv_setup2_sim=(SettingItemView) findViewById(R.id.siv_setup2_sim);
		tm=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String sim=sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			siv_setup2_sim.setChecked(false);
		}else{
			siv_setup2_sim.setChecked(true);
		}
		siv_setup2_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor=sp.edit();
				if(siv_setup2_sim.isChecked()){
					//如果已经选择那么点击为非选中状态
					siv_setup2_sim.setChecked(false);
					editor.putString("sim", null);
				}else{
					//如果没选中，那么点击进行选中
					siv_setup2_sim.setChecked(true);
					//保存sim卡序列号
					String sim=tm.getSimSerialNumber();
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		
		});
	}
	
	/**
	 * 点击下一步按钮就如下一个界面
	 * @param view
	 */

	@Override
	public void showNext() {
		//如果绑定手机sim选项没有选择，没进行绑定，那么结束返回，停止进入下一项
		String sim=sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			Toast.makeText(this, "手机sim卡没有绑定，请选择", 1).show();
			return;
		}
		Intent intent=new Intent(SetuoTwoActivity.this,SetuoThreeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}
	/**
	 * 点击上一步按钮进入上一个界面
	 * @param view
	 */
	@Override
	public void showPre() {
		Intent intent=new Intent(SetuoTwoActivity.this,SetuoOneActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}
}
