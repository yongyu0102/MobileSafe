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
//			siv.setDesc("自动升级已经开启");
		}else{
			siv.setChecked(false);
//			siv.setDesc("自动升级已经关闭");
		}
		
		siv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor=sp.edit();
				//判断是否选中
				if(siv.isChecked()){
					//如果被选中那么就进行取消
					siv.setChecked(false);
//					siv.setDesc("自动升级已经关闭");
					editor.putBoolean("checked", false);
				}else{
					//否则进行选中
					siv.setChecked(true);
//					siv.setDesc("自动升级已经开启");
					editor.putBoolean("checked", true);
				}
				editor.commit();
			}
		});
	
	}

}
