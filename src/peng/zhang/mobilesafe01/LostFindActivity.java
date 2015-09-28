package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private SharedPreferences sp;
	TextView activity_lost_number;
	ImageView activity_lost_lock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean isSet=sp.getBoolean("seted", false);
		if(isSet){
			setContentView(R.layout.activity_lost_find);
			activity_lost_number=(TextView) findViewById(R.id.activity_lost_number);
			activity_lost_lock=(ImageView) findViewById(R.id.activity_lost_lock);
			//注意取值时候，传入默认值要是""就不会报空指针错误，如果null，可能包null错误
			String phone=sp.getString("phone", "");
			activity_lost_number.setText(phone);
			boolean protecting=sp.getBoolean("protecting", false);
			if(protecting){
				activity_lost_lock.setImageResource(R.drawable.lock);
			}else{
				activity_lost_lock.setImageResource(R.drawable.unlock);
			}
		}else{
			Intent intent =new Intent(LostFindActivity.this,SetuoOneActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	
	public void reEnterSetup(View view){
		Intent intent =new Intent(LostFindActivity.this,SetuoOneActivity.class);
		startActivity(intent);
		finish();
	}

}
