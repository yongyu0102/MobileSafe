package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetuoThreeActivity extends BaseSetupActivity {
	EditText activity_three_setup_et_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three_setup);
		activity_three_setup_et_number=(EditText) findViewById(R.id.activity_three_setup_et_number);
		String phone=sp.getString("phone", null);
		activity_three_setup_et_number.setText(phone);
	}

	
	/**
	 * �����һ����ť������һ������
	 * @param view
	 */
	@Override
	public void showNext() {
		//�����һ������Ƿ��Ѿ����ð�ȫ���룬���û������ô������ֹͣ������һ��
				String phone=activity_three_setup_et_number.getText().toString().trim();
				if(TextUtils.isEmpty(phone)){
					Toast.makeText(SetuoThreeActivity.this, "�����ð�ȫ����", 1).show();
					return;
				}
				
				//Ӧ�ñ���һЩ ��ȫ����
				Editor editor=sp.edit();
				editor.putString("phone", phone);
				editor.commit();
		Intent intent=new Intent(SetuoThreeActivity.this,SetuoFourActivity.class);
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
		
		
		Intent intent=new Intent(SetuoThreeActivity.this,SetuoTwoActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}
	/**
	 * ����ѡ����ϵ�˵ĵ���¼������õ����������
	 */
	public void selectContact(View view){
		Intent intent =new Intent(SetuoThreeActivity.this,SelectContactActivity.class);
		startActivityForResult(intent, 0);
	}
	//ѡ����ϵ�˺�ѡ����ϵ�˽���������ص��ú���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data==null){
			return;
		}
		String phone=data.getStringExtra("phone").replace("-", "");
		activity_three_setup_et_number.setText(phone);
		
	}
}
