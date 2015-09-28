package peng.zhang.mobilesafe01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

public abstract class BaseSetupActivity extends Activity {
	private GestureDetector detector;
	protected SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 实例化生成手势识别器
		sp=getSharedPreferences("config", MODE_PRIVATE);
		detector = new GestureDetector(this, new SimpleOnGestureListener() {
			/**
			 * SimpleOnGestureListener为GestureDetector内部类，注意需要将其导如包内
			 * 当手指在上面滑动时候调用
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//屏蔽斜着滑动
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "不能这样滑啊", 0).show();
					return true;
				}
				//屏蔽低速慢滑
				if(Math.abs(velocityX)<100){
					Toast.makeText(getApplicationContext(), "滑动得太慢了", 0).show();
				}
				if ((e2.getRawX() - e1.getRawX()) > 100) {
					// 从左向右滑动，显示上一个界面
					System.out.println("从左向右滑动，显示上一个界面");
					showPre();
					// 执行结束，不许要处理其他方法直接return掉，方法结束
					return true;
				}
				if ((e1.getRawX() - e2.getRawX()) > 200) {
					// 从右向左滑动，显示下一个界面
					System.out.println("从右向左滑动，显示下一个界面");
					showNext();
					return true;
				}
				// 若执行了第一个if那么由于return掉了，该方法就不会执行
				System.out.println("从右向左滑动，显示下一个界面1111");
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	public abstract void showNext();

	public abstract void showPre();

	/**
	 * 下一步点击事件
	 */
	public void next(View view) {
		showNext();
	}

	/**
	 * 上一步
	 * 
	 * @param view
	 */
	public void pre(View view) {
		showPre();
	}

	// 使用手势识别器
	@Override
	// 触发屏幕调用该方法
	public boolean onTouchEvent(MotionEvent event) {
		// 将滑动事件传递给手势识别器
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

}
