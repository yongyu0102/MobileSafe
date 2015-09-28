package peng.zhang.mobilesafe01;

import peng.zhang.mobilesafe01.utils.MD5Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	protected static final String TAG = "HomeActivity";
	private GridView list_home;
	private MyAdapter adapter;
	SharedPreferences sp;
	AlertDialog dialog;
	private  static String[] names={
		"手机防盗","通讯卫士","软件管理",
		"进程管理","流量统计","手机杀毒",
		"缓存清理","高级工具","设置中心"
	};
	
	private static int[] ids = {
		R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
		R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		list_home=(GridView) findViewById(R.id.list_home);
		adapter=new MyAdapter();
		list_home.setAdapter(adapter);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		list_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 7://计入高级工具
					Intent intent7=new Intent(HomeActivity.this,AtoolsActivity.class);
					startActivity(intent7);
					break;
				case 8://进入设置中心
					Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
				case 0://进入手机防盗界面
					showLostFindDialog();
					break;
					default:
					break;
				}
			}
					});
	}
	private void showLostFindDialog() {
		//如果已经设置密码，那么进入输入密码对话框
		if(isSetPassWord()){
			showEnterDialog();
		}else{
			//没设置密码进入设置密码对话框
			showSetPassWordDialog();
		}
	}
	/**
	 * 设置密码对话框
	 */
	private EditText et_password;
	private EditText et_confirm;
	private Button bt_ok;
	private Button bt_cancel;
	private void showSetPassWordDialog() {
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//由于系统对话框没有输入框，那么下面我们要自定义一个对话框
		//自定义个一个布局问价，然后将其加载成View
		View view=View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		/**
		 *直接使用show的方法show出对话框
		 * builder.setView(view);
		dialog=builder.show();
		 */
		//下面需要对对话框进行属性设置，让其充满对话框，在进行显示代码
		dialog=builder.create();
		dialog.setView(view, 0, 0, 0, 0);//距离边界为0
		dialog.show();
		et_password=(EditText) view.findViewById(R.id.et_setup_password);
		et_confirm=(EditText)view. findViewById(R.id.et_setup_confirm);
		bt_ok=(Button) view.findViewById(R.id.bt_ok);
		bt_cancel=(Button) view.findViewById(R.id.bt_cancle);
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击取消，则消除对话框
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				首先取出EditText内容
				String passWord=et_password.getText().toString().trim();
				String passWordConf=et_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(passWord)||TextUtils.isEmpty(passWordConf)){
					//如果输入为空，进行提示
					Toast.makeText(HomeActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
					return;
				}//判断输入密码是否一致
				if(passWord.equals(passWordConf)){
					//如果输入密码一致则进行存储
					Editor editor=sp.edit();
					editor.putString("passWord", MD5Utils.md5password(passWord));
					editor.commit();
					dialog.dismiss();
					Log.i(TAG, "一致的话，就保存密码，把对话框消掉，还要进入手机防盗页面");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					//此处不能finish掉，要保留主界面
				}else{
					//如果输入密码不一致则进行提示
					Toast.makeText(HomeActivity.this, "输入密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
	}
	/**
	 * 输入登陆密码对话框
	 */

	private void showEnterDialog() {
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//由于系统对话框没有输入框，那么下面我们要自定义一个对话框
		//自定义个一个布局问价，然后将其加载成View
		//注释：不同布局文件中可以有相同id，但是同一个布局文件中不许有相同iid
		View view=View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);
		/**
		 *直接使用show的方法show出对话框
		 * builder.setView(view);
		dialog=builder.show();
		 */
		//下面需要对对话框进行属性设置，让其充满对话框，在进行显示代码
		dialog=builder.create();
		dialog.setView(view, 0, 0, 0, 0);//距离边界为0
		dialog.show();
		et_password=(EditText) view.findViewById(R.id.et_setup_password);
		bt_ok=(Button) view.findViewById(R.id.bt_ok);
		bt_cancel=(Button) view.findViewById(R.id.bt_cancle);
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击取消，消掉对话框
				dialog.dismiss();
				
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				首先取出取出保存的密码
				String savedPassword=sp.getString("passWord", "");
				String password=et_password.getText().toString().trim();
				if(TextUtils.isEmpty(password)){
					//如果输入密码为空，进行提示然后直接返回
					Toast.makeText(HomeActivity.this, "登陆密码为空", 1).show();
					return;
				}
				if(MD5Utils.md5password(password).equals(savedPassword)){
					//如果登陆输入密码与保存密码相等则进行登陆，消掉对话框
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					//此处不能finish掉，要保留主界面
					dialog.dismiss();
				}else{
					//如果输入密码与保存密码不等进行提示，直接返回
					Toast.makeText(HomeActivity.this, "输入密码有误", 1).show();
					//并消除错误密码
					et_password.setText("");
					return;
				}
			
			}
		});
	}
	/**
	 * 判断是否已经设置密码
	 * @return
	 */
	private boolean isSetPassWord() {
		String passWord=sp.getString("passWord", null);
//		if(TextUtils.isEmpty(passWord)){
//			return false;
//		}else{
//			return true;
//		}
		return !TextUtils.isEmpty(passWord);
	}

	
	private class  MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=View.inflate(HomeActivity.this, R.layout.list_item_home, null);
			ImageView iv_item=(ImageView) view.findViewById(R.id.iv_item);
			TextView tv_item=(TextView) view.findViewById(R.id.tv_item);
			tv_item.setText(names[position]);
			iv_item.setImageResource(ids[position]);
			return view;
		}
		
	}
}
