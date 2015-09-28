package peng.zhang.mobilesafe01.ui;

import peng.zhang.mobilesafe01.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	/**
	 * �����Զ������Ͽؼ���������������TextView ������һ��CheckBox,����һ��View
	 * @author Administrator
	 *
	 */
	private TextView tv_title;
	private TextView tv_decrip;
	private CheckBox cb;
	String title;
	String desc_on;
	String desc_off;
	
	/**
	 * ��ʼ������
	 * @param context
	 */
	public void initView(Context context){
		//thisΪ�ò�����ͼ�ĸ�����ͼ�������ǽ��ò��ּ��ص�SetingItemView�У�����ֱ��ʹ��this
		//��һ�������ļ�---��View ���Ҽ�����SettingItemView
		View.inflate(context, R.layout.setting_item_view, this);
		//ʵ������ͼ�ڲ��ؼ�
		tv_title=(TextView) findViewById(R.id.title_activity_setting);
		tv_decrip=(TextView) findViewById(R.id.descrip_activity_setting);
		cb=(CheckBox) findViewById(R.id.cb_activity_setting);
		
	}

	public SettingItemView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		 initView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		 initView(context);
	}
	/**
	 * �������������Ĺ��췽����ʹ�ò����ļ���ʱ�����
	 * @param context
	 * @param attrs
	 */

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 initView(context);
		 //��ȥ�ؼ�����
		 title=attrs.getAttributeValue("http://schemas.android.com/apk/res/peng.zhang.mobilesafe01", "title1");
		 desc_on=attrs.getAttributeValue("http://schemas.android.com/apk/res/peng.zhang.mobilesafe01", "desc_on");
		 desc_off=attrs.getAttributeValue("http://schemas.android.com/apk/res/peng.zhang.mobilesafe01", "desc_off");
		 tv_title.setText(title);
		 setDesc(desc_off);
	}

	public SettingItemView(Context context) {
		super(context);
		 initView(context);
	}
	
	/**
	 * У����Ͽؼ��Ƿ�ѡ��
	 */
	public boolean  isChecked(){
		return cb.isChecked();
	}
	
	/**
	 * ������Ͽؼ���״̬
	 */
	
	public void setChecked(boolean checked){
		if(checked){
			tv_decrip.setText(desc_on);
		}else{
			tv_decrip.setText(desc_off);
		}
		cb.setChecked(checked);
	}
	
	/**
	 * ���� ��Ͽؼ���������Ϣ
	 */
	
	public void setDesc(String text){
		tv_decrip.setText(text);
	}
	

}
