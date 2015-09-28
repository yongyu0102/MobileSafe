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
		"�ֻ�����","ͨѶ��ʿ","�������",
		"���̹���","����ͳ��","�ֻ�ɱ��",
		"��������","�߼�����","��������"
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
				case 7://����߼�����
					Intent intent7=new Intent(HomeActivity.this,AtoolsActivity.class);
					startActivity(intent7);
					break;
				case 8://������������
					Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
				case 0://�����ֻ���������
					showLostFindDialog();
					break;
					default:
					break;
				}
			}
					});
	}
	private void showLostFindDialog() {
		//����Ѿ��������룬��ô������������Ի���
		if(isSetPassWord()){
			showEnterDialog();
		}else{
			//û�������������������Ի���
			showSetPassWordDialog();
		}
	}
	/**
	 * ��������Ի���
	 */
	private EditText et_password;
	private EditText et_confirm;
	private Button bt_ok;
	private Button bt_cancel;
	private void showSetPassWordDialog() {
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//����ϵͳ�Ի���û���������ô��������Ҫ�Զ���һ���Ի���
		//�Զ����һ�������ʼۣ�Ȼ������س�View
		View view=View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		/**
		 *ֱ��ʹ��show�ķ���show���Ի���
		 * builder.setView(view);
		dialog=builder.show();
		 */
		//������Ҫ�ԶԻ�������������ã���������Ի����ڽ�����ʾ����
		dialog=builder.create();
		dialog.setView(view, 0, 0, 0, 0);//����߽�Ϊ0
		dialog.show();
		et_password=(EditText) view.findViewById(R.id.et_setup_password);
		et_confirm=(EditText)view. findViewById(R.id.et_setup_confirm);
		bt_ok=(Button) view.findViewById(R.id.bt_ok);
		bt_cancel=(Button) view.findViewById(R.id.bt_cancle);
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//���ȡ�����������Ի���
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				����ȡ��EditText����
				String passWord=et_password.getText().toString().trim();
				String passWordConf=et_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(passWord)||TextUtils.isEmpty(passWordConf)){
					//�������Ϊ�գ�������ʾ
					Toast.makeText(HomeActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}//�ж����������Ƿ�һ��
				if(passWord.equals(passWordConf)){
					//�����������һ������д洢
					Editor editor=sp.edit();
					editor.putString("passWord", MD5Utils.md5password(passWord));
					editor.commit();
					dialog.dismiss();
					Log.i(TAG, "һ�µĻ����ͱ������룬�ѶԻ�����������Ҫ�����ֻ�����ҳ��");
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					//�˴�����finish����Ҫ����������
				}else{
					//����������벻һ���������ʾ
					Toast.makeText(HomeActivity.this, "�������벻һ��", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
	}
	/**
	 * �����½����Ի���
	 */

	private void showEnterDialog() {
		AlertDialog.Builder builder=new Builder(HomeActivity.this);
		//����ϵͳ�Ի���û���������ô��������Ҫ�Զ���һ���Ի���
		//�Զ����һ�������ʼۣ�Ȼ������س�View
		//ע�ͣ���ͬ�����ļ��п�������ͬid������ͬһ�������ļ��в�������ͬiid
		View view=View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);
		/**
		 *ֱ��ʹ��show�ķ���show���Ի���
		 * builder.setView(view);
		dialog=builder.show();
		 */
		//������Ҫ�ԶԻ�������������ã���������Ի����ڽ�����ʾ����
		dialog=builder.create();
		dialog.setView(view, 0, 0, 0, 0);//����߽�Ϊ0
		dialog.show();
		et_password=(EditText) view.findViewById(R.id.et_setup_password);
		bt_ok=(Button) view.findViewById(R.id.bt_ok);
		bt_cancel=(Button) view.findViewById(R.id.bt_cancle);
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���ȡ���������Ի���
				dialog.dismiss();
				
			}
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				����ȡ��ȡ�����������
				String savedPassword=sp.getString("passWord", "");
				String password=et_password.getText().toString().trim();
				if(TextUtils.isEmpty(password)){
					//�����������Ϊ�գ�������ʾȻ��ֱ�ӷ���
					Toast.makeText(HomeActivity.this, "��½����Ϊ��", 1).show();
					return;
				}
				if(MD5Utils.md5password(password).equals(savedPassword)){
					//�����½���������뱣�������������е�½�������Ի���
					Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					//�˴�����finish����Ҫ����������
					dialog.dismiss();
				}else{
					//������������뱣�����벻�Ƚ�����ʾ��ֱ�ӷ���
					Toast.makeText(HomeActivity.this, "������������", 1).show();
					//��������������
					et_password.setText("");
					return;
				}
			
			}
		});
	}
	/**
	 * �ж��Ƿ��Ѿ���������
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
