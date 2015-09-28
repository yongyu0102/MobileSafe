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
	 * 点击下一步按钮就如下一个界面
	 * @param view
	 */
	@Override
	public void showNext() {
		//点击下一步检测是否已经设置安全号码，如果没设置那么结束，停止进行下一步
				String phone=activity_three_setup_et_number.getText().toString().trim();
				if(TextUtils.isEmpty(phone)){
					Toast.makeText(SetuoThreeActivity.this, "请设置安全号码", 1).show();
					return;
				}
				
				//应该保持一些 安全号码
				Editor editor=sp.edit();
				editor.putString("phone", phone);
				editor.commit();
		Intent intent=new Intent(SetuoThreeActivity.this,SetuoFourActivity.class);
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
		
		
		Intent intent=new Intent(SetuoThreeActivity.this,SetuoTwoActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}
	/**
	 * 设置选择联系人的点击事件，设置点击方法函数
	 */
	public void selectContact(View view){
		Intent intent =new Intent(SetuoThreeActivity.this,SelectContactActivity.class);
		startActivityForResult(intent, 0);
	}
	//选择联系人后，选择联系人界面结束，回调该函数
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
