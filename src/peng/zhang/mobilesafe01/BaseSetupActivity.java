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
		// ʵ������������ʶ����
		sp=getSharedPreferences("config", MODE_PRIVATE);
		detector = new GestureDetector(this, new SimpleOnGestureListener() {
			/**
			 * SimpleOnGestureListenerΪGestureDetector�ڲ��࣬ע����Ҫ���䵼�����
			 * ����ָ�����滬��ʱ�����
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//����б�Ż���
				if(Math.abs(e2.getRawY()-e1.getRawY())>100){
					Toast.makeText(BaseSetupActivity.this, "������������", 0).show();
					return true;
				}
				//���ε�������
				if(Math.abs(velocityX)<100){
					Toast.makeText(getApplicationContext(), "������̫����", 0).show();
				}
				if ((e2.getRawX() - e1.getRawX()) > 100) {
					// �������һ�������ʾ��һ������
					System.out.println("�������һ�������ʾ��һ������");
					showPre();
					// ִ�н���������Ҫ������������ֱ��return������������
					return true;
				}
				if ((e1.getRawX() - e2.getRawX()) > 200) {
					// �������󻬶�����ʾ��һ������
					System.out.println("�������󻬶�����ʾ��һ������");
					showNext();
					return true;
				}
				// ��ִ���˵�һ��if��ô����return���ˣ��÷����Ͳ���ִ��
				System.out.println("�������󻬶�����ʾ��һ������1111");
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	public abstract void showNext();

	public abstract void showPre();

	/**
	 * ��һ������¼�
	 */
	public void next(View view) {
		showNext();
	}

	/**
	 * ��һ��
	 * 
	 * @param view
	 */
	public void pre(View view) {
		showPre();
	}

	// ʹ������ʶ����
	@Override
	// ������Ļ���ø÷���
	public boolean onTouchEvent(MotionEvent event) {
		// �������¼����ݸ�����ʶ����
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

}
