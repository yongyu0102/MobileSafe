package peng.zhang.mobilesafe01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.exception.AfinalException;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import peng.zhang.mobilesafe01.utils.StreamTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	protected static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int JSON_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int URL_ERROR =4 ;
	private TextView tv_splash_version;
	private TextView tv_update_info;
	//��������������Ϣ
	private String description;
	//�������ص�ַ
	private String apkurl;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version=(TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾��"+getVersionName());
		tv_update_info=(TextView) findViewById(R.id.tv_update_info);
		//����������������������������ʽ���Ϊ��ȫ�ɼ�
		//������0.2͸���ȱ�Ϊ��ȫ�ɼ�
		AlphaAnimation aa=new AlphaAnimation(0.2f, 1.0f);
		//����ʱ��
		aa.setDuration(500);
		//ָ���������
		//findViewById(R.id.rl_root_splash)�Ǹ�������ʵ���ص���һ��View
		findViewById(R.id.rl_root_splash).startAnimation(aa);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean checked=sp.getBoolean("checked", false);
		if(checked){
			//���������������ô�������
			checkUpdate();
		}else{
			//���������ֱ�ӽ��������棬����֮ǰ����˯��2S
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}
	}
	//ʵ��Handler���������̷߳��͵���Ϣ����UI
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			//�����ص���Ϣ
			switch(msg.what){
			case SHOW_UPDATE_DIALOG:// ��ʾ�����ĶԻ���
				Log.i(TAG, "��ʾ�����ĶԻ���");
				showUpdateDialog();
				break;
			case ENTER_HOME:// ������ҳ��
				enterHome();
				break;
			case URL_ERROR:// URL����
				enterHome();
				Toast.makeText(getApplicationContext(), "URL����", 0).show();
				break;
			case NETWORK_ERROR:// �����쳣
				enterHome();
				Toast.makeText(SplashActivity.this, "�����쳣", 0).show();
				break;
			case JSON_ERROR:// JSON��������
				enterHome();
				Toast.makeText(SplashActivity.this, "JSON��������", 0).show();
				break;
			default:
				break;
			}
		}
	};
	/*
	 * ��д����������ܴ��룬����Ƿ����°汾������о�����
	 * ����Ҫ��������������Ҫ�������߳�
	 */
	private void checkUpdate() {
		new Thread(){
			public void run() {
				/*
				 * URL uri=new URL("http://127.0.0.1/updateinfo.html");
				 * ����http��ip��ַ��ͬ�������ܸı䣬�����ⲿ�ִ�����Ҫ���ȥ�γɵ��������
				 * ������ڸĶ���ά���������ڹ���Ŀ¼values�´���config xml�ļ�,Ȼ���ڴ�����
				 * ��������
				 */
				//�������߳̿�ʼ������֮ǰ��¼ʱ��
				long starTime=System.currentTimeMillis();
				Message mes=Message.obtain();
			try {
				//��ȡ��Ϣ
				URL url=new URL(getString(R.string.serverurl));
				Log.d(TAG, "�����ɹ�1");
				System.out.println(1);
				//����
				try {
					Log.d(TAG, "�����ɹ�2");
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					System.out.println(2);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(2000);
					System.out.println(3);
					//��ȡ��Ӧ��
					int code=conn.getResponseCode();
					System.out.println(4);
					System.out.print(code);
					if(code==200){
						Log.d(TAG, "�����ɹ�");
						//�����Ӧ�����200�����ɹ�
						//��ȡ������
						InputStream is=conn.getInputStream();
						/*
						 * ��������ת�����ַ�����ʽ���ڴ�ʹ�õ��������ߣ��������Ѿ�д�õĹ��ܴ���
						 * ��src�´���peng.zhang.mobilesafe01.utils���߰���StreamTools.java���
						 * �࿽����ȥ
						 */
						String result=StreamTools.readFromStream(is);
						//��ӡ�����ע��������Ҫ��дȨ��
						Log.d(TAG, "�����ɹ�"+result);
						//JSON����
						try {
							/*
							 * JSONObject obj = new JSONObject(result);
							 * // �õ��������İ汾��Ϣ
							 * String version = (String) obj.get("version");
							 * 	description = (String) obj.get("description");
							 * apkurl = (String) obj.get("apkurl");
							 */
							JSONObject obj=new JSONObject(result);
							// �õ��������İ汾��Ϣ
							String version=obj.getString("version");
							description=obj.getString("description");
						    apkurl=obj.getString("apkurl");
						    //У���Ƿ����°汾
						    if(getVersionName().equals(version)){
						    	//�汾һ�£�û���°汾��������ҳ��
						    	mes.what=ENTER_HOME;
						    }else{
						    	//�汾��һ�£����°汾�����������Ի�����Ϊ�����Ի��򣬸���ҳ�棬
						    	//��Ҫ�����߳��н��У�������Ҫ�õ�Handler,���ҷ�װһ��Message
						    	mes.what=SHOW_UPDATE_DIALOG;
						    }
						    
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//JSON��������
							mes.what=JSON_ERROR;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//��������
					mes.what=NETWORK_ERROR;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				//URL����
				mes.what=URL_ERROR;
			}finally{
				//��¼���ؽ���ʱ��
				long endTime=System.currentTimeMillis();
				long spendTime=endTime-starTime;
				if(spendTime<2000){
					try {
						//��������ʱ��2s
						Thread.sleep(2000-spendTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
				handler.sendMessage(mes);
			}
			};
		}.start();
	}
	/**
	 * ��̬�õ�Ӧ�ó���İ汾��
	 */
	private String getVersionName(){
		//��ȡpm���ڹ����ֻ�apk������apk�ڲ�����Menifest�ļ�
		PackageManager pm=getPackageManager();
		//�õ�ָ��apk�����嵥�ļ���Ϣ
		try{
			//��ȡ������Ϣ����ȡ�����а�����Manifest�ļ���Ϣ
			PackageInfo info =pm.getPackageInfo(getPackageName(), 0);
			//getPackgeName()����Ӧ�ó���������ڶ�������Ϊ�����������Դ�0����
			return info.versionName;//�õ�Ӧ�ó���汾��Ϣ
		}catch(NameNotFoundException e){
			e.printStackTrace();
			return "";
		}
	}
	
	private void enterHome() {
		// TODO Auto-generated method stub
		Intent intent =new Intent(SplashActivity.this,HomeActivity.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
	}
	/*
	 * ���������Ի���
	 */
	private void showUpdateDialog() {
		// ���������Ի���
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("������ʾ");
		builder.setMessage(description);
		//ǿ������
		//builder.setCancelable(false);
		//���ÿ�ѡ������
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				//���ü��������ȡ������������,�رնԻ���
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("����", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ����app�����滻��װ
				//�����ж�SD���Ƿ����
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//���ڿ�ʼ����	
				//ʹ�õ�������Դ���afinal��Դ��ܽ�������
				FinalHttp finalhttp=new FinalHttp();
				finalhttp.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath()+
						"/mobilesafe012.0.apk", new AjaxCallBack<File>() {
							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								// ���ش����ӡ���ش�������
								t.printStackTrace();
								//����ʧ�ܽ�����ʾ
								Toast.makeText(getApplicationContext(), "����ʧ��", 1).show();
								super.onFailure(t, errorNo, strMsg);
							}

							@Override
							public void onLoading(long count, long current) {
								super.onLoading(count, current);
								//��ʾ���ؽ��ȣ�����Ҫ��xml�ļ��ж���һ��TextView������ʾ
								//���㵱ǰ���ؽ��ȱȷֱ�
								tv_update_info.setVisibility(View.VISIBLE);
								int progress=(int) (current*100/count);
								//����textview����
								tv_update_info.setText("��ǰ���ؽ��ȣ�"+progress);
							}

							@Override
							public void onSuccess(File t) {
								super.onSuccess(t);
								//���سɹ���װapk
								installAPK(t);
							}

							private void installAPK(File t) {
								//������װ��ͼ��ϵͳ�Դ���ͼ��������ͼ���г���װ
								Intent intent=new Intent();
								intent.setAction("android.intent.action.VIEW");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
								startActivity(intent);
							}
					
						});
				}else{
					//�����ڷ��أ���������ʾ
					Toast.makeText(getApplicationContext(), "SD�������ڣ��밲װ������", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		builder.setNegativeButton("�´�����", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// �����Ի��򣬽�����ҳ�棬
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}
}
