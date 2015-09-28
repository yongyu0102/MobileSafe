package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberAddressQuery extends Activity {
	
	private static final String TAG = "NumberAddressQuery";
	private EditText nubmber_addrss_query_input;
	private TextView nubmber_addrss_query_result;
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);
		nubmber_addrss_query_input=(EditText) findViewById(R.id.nubmber_addrss_query_input);
		nubmber_addrss_query_result=(TextView) findViewById(R.id.nubmber_addrss_query_result);
	}
		/**
		 * 点击查询按钮进行查询
		 */
		
		public void numberAddressQuery(View view){
			String phoneNumber=nubmber_addrss_query_input.getText().toString().trim();
			if(TextUtils.isEmpty(phoneNumber)){
				Toast.makeText(NumberAddressQuery.this, "请输入电话号码", 1).show();
				return;
			}else{
				Log.d(TAG, "您要查询的电话号码为=="+phoneNumber);
			}
		}

}
