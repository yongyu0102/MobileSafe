package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AtoolsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	
	/**
	 * ��������ѯ�������
	 * @param view
	 */
	public void numberQuery(View view){
		Intent intent=new Intent(AtoolsActivity.this,NumberAddressQuery.class);
		startActivity(intent);
		finish();
	}

}
