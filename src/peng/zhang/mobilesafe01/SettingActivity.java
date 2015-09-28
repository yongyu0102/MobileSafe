package peng.zhang.mobilesafe01;

import peng.zhang.mobilesafe01.ui.SettingItemView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingActivity extends Activity {
	private SettingItemView siv;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		siv=(SettingItemView) findViewById(R.id.siv_update);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean checked=sp.getBoolean("checked", false);
		if(checked){
			siv.setChecked(true);
//			siv.setDesc("�Զ������Ѿ�����");
		}else{
			siv.setChecked(false);
//			siv.setDesc("�Զ������Ѿ��ر�");
		}
		
		siv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor=sp.edit();
				//�ж��Ƿ�ѡ��
				if(siv.isChecked()){
					//�����ѡ����ô�ͽ���ȡ��
					siv.setChecked(false);
//					siv.setDesc("�Զ������Ѿ��ر�");
					editor.putBoolean("checked", false);
				}else{
					//�������ѡ��
					siv.setChecked(true);
//					siv.setDesc("�Զ������Ѿ�����");
					editor.putBoolean("checked", true);
				}
				editor.commit();
			}
		});
	
	}

}
