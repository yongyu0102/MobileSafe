package peng.zhang.mobilesafe01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectContactActivity extends Activity {
	private ListView list_select_contact;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		list_select_contact=(ListView) findViewById(R.id.list_select_contact);
		//为了防止在点击list单条内容时，外部数据发生变化，故将其final
		final List<Map<String, String>> data = getContactInfo();
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.contact_item_view, new String[] { "name", "phone" },
				new int[] { R.id.contact_item_view_name,
						R.id.contact_item_view_phone });
		 list_select_contact.setAdapter(adapter);
		 list_select_contact.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//根据list实例和位置position得到map
				Map map=data.get(position);
				Intent intent=new Intent();
				String phone=(String) map.get("phone");
				intent.putExtra("phone", phone);
				setResult(0, intent);
				//点击选择后结束页面
				finish();
				
			}
		});
	}
	
	/**
	 * 读取手机联系人
	 * 
	 * @return
	 */
	private List<Map<String, String>> getContactInfo() {
		// 把所有的联系人存下
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// 得到一个内容解析器
		ContentResolver resolver = getContentResolver();
		// 获取手机联系人数据库存放联系人id的表路径
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		// 只查询raw_contacts表的名字为contact_id列
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		while (cursor.moveToNext()) {
			// 因为只有一列所以直接取出从即刻，因为是从0开始，所以所以位置直接传0即可
			String contact_id = cursor.getString(0);
			if (contact_id != null) {
				// 具体的某一个联系人存下
				Map<String, String> map = new HashMap<String, String>();
				// 如果查询的id不为null，那么接着查询对应表data的id对应的"data1","mimetype"列
				// Cursor dataCursor=resolver.query(uriData, new
				// String[]{"data1","mimetype" }, "contact_id=?"
				// , new String []{"contact_id"}, null);错误代码
				Cursor dataCursor = resolver.query(uriData, new String[] {
						"data1", "mimetype" }, "contact_id=?",
						new String[] { contact_id }, null);
				// 将查询结果取出
				while (dataCursor.moveToNext()) {
					// 取出第一列结果
					String data1 = dataCursor.getString(0);
					// 取出第二列结果
					String mimetype = dataCursor.getString(1);
					// Log.d("MainActivity",
					// "data1=="+data1+"==mimetype=="+mimetype);
					if ("vnd.android.cursor.item/name".equals(mimetype)) {
						// 联系人的姓名
						map.put("name", data1);
					} else if ("vnd.android.cursor.item/phone_v2"
							.equals(mimetype)) {
						// 联系人电话
						map.put("phone", data1);
					}
				}
				list.add(map);
				dataCursor.close();
			}
		}
		cursor.close();
		return list;
	}

}
