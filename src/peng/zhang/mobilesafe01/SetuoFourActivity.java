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
		//��ʼ��ʱ���CheckBox״̬
		Boolean protecting=sp.getBoolean("protecting", false);
		//����CheckBox״̬�仯
		if(protecting){
			cb.setText("�ֻ������ѿ���");
			cb.setChecked(true);
		}else{
			cb.setText("�ֻ������ѹر�");
			cb.setChecked(false);
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor=sp.edit();
				if(isChecked){
					//Ϊtrue��ôΪѡ��״̬���ֻ������Ѿ�����
					cb.setText("�ֻ������ѿ���");
					editor.putBoolean("protecting", true);
				}else{
					//Ϊfalse��ûѡ�У��ֻ������ر�
					cb.setText("�ֻ������ѹر�");
					editor.putBoolean("protecting", false);
				}
				editor.commit();
				
			}
		});
	}

	/**
	 * �����һ����ť������LostFindActivity����
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
	 * �����һ����ť������һ������
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
