package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class SetuoOneActivity extends BaseSetupActivity {
	//定义一个手势识别器
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//super调用父类成员函数的方法，实现函数的复写
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_setup);
	}
	
	@Override
	public void showNext() {
		Intent intent=new Intent(SetuoOneActivity.this,SetuoTwoActivity.class);
		startActivity(intent);
		finish();
		//要求在finish()或者startActivity(intent);后面执行；
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		
	}
	@Override
	public void showPre() {
		
	}
	
	
}
