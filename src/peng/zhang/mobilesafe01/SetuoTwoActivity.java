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
	 * ��ȡ�ֻ�sim����Ϣ
	 */
	private TelephonyManager tm;
	/**
	 * ����һ��SettingItemView
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
					//����Ѿ�ѡ����ô���Ϊ��ѡ��״̬
					siv_setup2_sim.setChecked(false);
					editor.putString("sim", null);
				}else{
					//���ûѡ�У���ô�������ѡ��
					siv_setup2_sim.setChecked(true);
					//����sim�����к�
					String sim=tm.getSimSerialNumber();
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		
		});
	}
	
	/**
	 * �����һ����ť������һ������
	 * @param view
	 */

	@Override
	public void showNext() {
		//������ֻ�simѡ��û��ѡ��û���а󶨣���ô�������أ�ֹͣ������һ��
		String sim=sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)){
			Toast.makeText(this, "�ֻ�sim��û�а󶨣���ѡ��", 1).show();
			return;
		}
		Intent intent=new Intent(SetuoTwoActivity.this,SetuoThreeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}
	/**
	 * �����һ����ť������һ������
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
