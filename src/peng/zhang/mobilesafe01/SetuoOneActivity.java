package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class SetuoOneActivity extends BaseSetupActivity {
	//����һ������ʶ����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//super���ø����Ա�����ķ�����ʵ�ֺ����ĸ�д
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_setup);
	}
	
	@Override
	public void showNext() {
		Intent intent=new Intent(SetuoOneActivity.this,SetuoTwoActivity.class);
		startActivity(intent);
		finish();
		//Ҫ����finish()����startActivity(intent);����ִ�У�
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}
	@Override
	public void showPre() {
		
	}
	
	
}
