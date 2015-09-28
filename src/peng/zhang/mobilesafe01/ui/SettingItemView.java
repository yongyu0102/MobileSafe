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
	 * 我们自定义的组合控件，它里面有两个TextView ，还有一个CheckBox,还有一个View
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
	 * 初始化布局
	 * @param context
	 */
	public void initView(Context context){
		//this为该布局视图的父类视图，由于是将该布局加载到SetingItemView中，所以直接使用this
		//把一个布局文件---》View 并且加载在SettingItemView
		View.inflate(context, R.layout.setting_item_view, this);
		//实例话视图内部控件
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
	 * 带有两个参数的构造方法，使用布局文件的时候调用
	 * @param context
	 * @param attrs
	 */

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 initView(context);
		 //过去控件属性
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
	 * 校验组合控件是否选中
	 */
	public boolean  isChecked(){
		return cb.isChecked();
	}
	
	/**
	 * 设置组合控件的状态
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
	 * 设置 组合控件的描述信息
	 */
	
	public void setDesc(String text){
		tv_decrip.setText(text);
	}
	

}
