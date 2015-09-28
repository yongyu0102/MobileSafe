package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
public class SetuoFourActivity extends BaseSetupActivity {
	private SharedPreferences sp;
	private CheckBox cb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_four_setup);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		cb=(CheckBox) findViewById(R.id.activity_four_cb);
		//初始化时检测CheckBox状态
		Boolean protecting=sp.getBoolean("protecting", false);
		//监听CheckBox状态变化
		if(protecting){
			cb.setText("手机防盗已开启");
			cb.setChecked(true);
		}else{
			cb.setText("手机防盗已关闭");
			cb.setChecked(false);
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor=sp.edit();
				if(isChecked){
					//为true那么为选中状态，手机防盗已经开启
					cb.setText("手机防盗已开启");
					editor.putBoolean("protecting", true);
				}else{
					//为false，没选中，手机防盗关闭
					cb.setText("手机防盗已关闭");
					editor.putBoolean("protecting", false);
				}
				editor.commit();
				
			}
		});
	}

	/**
	 * 点击下一步按钮就如下LostFindActivity界面
	 * @param view
	 */
	@Override
	public void showNext() {
		Editor editor=sp.edit();
		editor.putBoolean("seted", true);
		editor.commit();
		Intent intent=new Intent(SetuoFourActivity.this,LostFindActivity.class);
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
		Intent intent=new Intent(SetuoFourActivity.this,SetuoThreeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}
