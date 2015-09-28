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
	//定义下载描述信息
	private String description;
	//定义下载地址
	private String apkurl;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version=(TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本号"+getVersionName());
		tv_update_info=(TextView) findViewById(R.id.tv_update_info);
		//添加启动动画，让启动界面由朦胧渐变为完全可见
		//渐变由0.2透明度变为完全可见
		AlphaAnimation aa=new AlphaAnimation(0.2f, 1.0f);
		//渐变时间
		aa.setDuration(500);
		//指定渐变对象
		//findViewById(R.id.rl_root_splash)是跟布局其实返回的是一个View
		findViewById(R.id.rl_root_splash).startAnimation(aa);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean checked=sp.getBoolean("checked", false);
		if(checked){
			//如果进行升级，那么检查升级
			checkUpdate();
		}else{
			//如果不升级直接进入主界面，进入之前进行睡眠2S
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}
	}
	//实现Handler处理处理子线程发送的消息更新UI
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			//处理返回的消息
			switch(msg.what){
			case SHOW_UPDATE_DIALOG:// 显示升级的对话框
				Log.i(TAG, "显示升级的对话框");
				showUpdateDialog();
				break;
			case ENTER_HOME:// 进入主页面
				enterHome();
				break;
			case URL_ERROR:// URL错误
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();
				break;
			case NETWORK_ERROR:// 网络异常
				enterHome();
				Toast.makeText(SplashActivity.this, "网络异常", 0).show();
				break;
			case JSON_ERROR:// JSON解析出错
				enterHome();
				Toast.makeText(SplashActivity.this, "JSON解析出错", 0).show();
				break;
			default:
				break;
			}
		}
	};
	/*
	 * 编写检查升级功能代码，检查是否有新版本了如果有就升级
	 * 升级要请求网络所以需要开启子线程
	 */
	private void checkUpdate() {
		new Thread(){
			public void run() {
				/*
				 * URL uri=new URL("http://127.0.0.1/updateinfo.html");
				 * 由于http中ip地址不同主机可能改变，所以这部分代码需要抽出去形成单独代码块
				 * 方便后期改动及维护，所以在工程目录values下创建config xml文件,然后在代码中
				 * 进行引用
				 */
				//在启动线程开始，下载之前记录时间
				long starTime=System.currentTimeMillis();
				Message mes=Message.obtain();
			try {
				//获取消息
				URL url=new URL(getString(R.string.serverurl));
				Log.d(TAG, "联网成功1");
				System.out.println(1);
				//联网
				try {
					Log.d(TAG, "联网成功2");
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					System.out.println(2);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(2000);
					System.out.println(3);
					//获取响应码
					int code=conn.getResponseCode();
					System.out.println(4);
					System.out.print(code);
					if(code==200){
						Log.d(TAG, "联网成功");
						//如果响应码等于200联网成功
						//获取输入流
						InputStream is=conn.getInputStream();
						/*
						 * 将输入流转换成字符串形式。在此使用第三方工具，即别人已经写好的功能代码
						 * 在src下创建peng.zhang.mobilesafe01.utils工具包将StreamTools.java这个
						 * 类拷贝进去
						 */
						String result=StreamTools.readFromStream(is);
						//打印结果，注意联网需要编写权限
						Log.d(TAG, "联网成功"+result);
						//JSON解析
						try {
							/*
							 * JSONObject obj = new JSONObject(result);
							 * // 得到服务器的版本信息
							 * String version = (String) obj.get("version");
							 * 	description = (String) obj.get("description");
							 * apkurl = (String) obj.get("apkurl");
							 */
							JSONObject obj=new JSONObject(result);
							// 得到服务器的版本信息
							String version=obj.getString("version");
							description=obj.getString("description");
						    apkurl=obj.getString("apkurl");
						    //校验是否有新版本
						    if(getVersionName().equals(version)){
						    	//版本一致，没有新版本，进入主页面
						    	mes.what=ENTER_HOME;
						    }else{
						    	//版本不一致，有新版本，弹出升级对话框，因为弹出对话框，更新页面，
						    	//需要在主线程中进行，所以需要用到Handler,并且封装一个Message
						    	mes.what=SHOW_UPDATE_DIALOG;
						    }
						    
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//JSON解析出错
							mes.what=JSON_ERROR;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//联网出错
					mes.what=NETWORK_ERROR;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				//URL出错
				mes.what=URL_ERROR;
			}finally{
				//记录下载结束时间
				long endTime=System.currentTimeMillis();
				long spendTime=endTime-starTime;
				if(spendTime<2000){
					try {
						//定义休眠时间2s
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
	 * 动态得到应用程序的版本号
	 */
	private String getVersionName(){
		//获取pm用于管理手机apk，其中apk内部包含Menifest文件
		PackageManager pm=getPackageManager();
		//得到指定apk功能清单文件信息
		try{
			//获取包的信息，获取了所有包含了Manifest文件信息
			PackageInfo info =pm.getPackageInfo(getPackageName(), 0);
			//getPackgeName()返回应用程序包名，第二个参数为附加条件可以传0即可
			return info.versionName;//得到应用程序版本信息
		}catch(NameNotFoundException e){
			e.printStackTrace();
			return "";
		}
	}
	
	private void enterHome() {
		// TODO Auto-generated method stub
		Intent intent =new Intent(SplashActivity.this,HomeActivity.class);
		startActivity(intent);
		//关闭当前页面
		finish();
	}
	/*
	 * 弹出升级对话框
	 */
	private void showUpdateDialog() {
		// 设置升级对话框
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("升级提示");
		builder.setMessage(description);
		//强制升级
		//builder.setCancelable(false);
		//设置可选不升级
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				//设置监听，点击取消进入主界面,关闭对话框
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("升级", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载app并且替换安装
				//首先判断SD卡是否存在
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//存在开始下载	
				//使用第三方开源框架afinal开源框架进行下载
				FinalHttp finalhttp=new FinalHttp();
				finalhttp.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath()+
						"/mobilesafe012.0.apk", new AjaxCallBack<File>() {
							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								// 下载错误打印下载错误日子
								t.printStackTrace();
								//下载失败进行提示
								Toast.makeText(getApplicationContext(), "下载失败", 1).show();
								super.onFailure(t, errorNo, strMsg);
							}

							@Override
							public void onLoading(long count, long current) {
								super.onLoading(count, current);
								//显示下载进度，故需要在xml文件中定义一个TextView进行显示
								//换算当前下载进度比分别
								tv_update_info.setVisibility(View.VISIBLE);
								int progress=(int) (current*100/count);
								//设置textview内容
								tv_update_info.setText("当前下载进度："+progress);
							}

							@Override
							public void onSuccess(File t) {
								super.onSuccess(t);
								//下载成功安装apk
								installAPK(t);
							}

							private void installAPK(File t) {
								//启动安装意图（系统自带意图）发送意图进行程序安装
								Intent intent=new Intent();
								intent.setAction("android.intent.action.VIEW");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
								startActivity(intent);
							}
					
						});
				}else{
					//不存在返回，并弹出提示
					Toast.makeText(getApplicationContext(), "SD卡不存在，请安装后再试", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		builder.setNegativeButton("下次升级", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 消掉对话框，进入主页面，
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}
}
