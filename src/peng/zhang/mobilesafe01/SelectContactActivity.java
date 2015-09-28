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
		//Ϊ�˷�ֹ�ڵ��list��������ʱ���ⲿ���ݷ����仯���ʽ���final
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
				//����listʵ����λ��position�õ�map
				Map map=data.get(position);
				Intent intent=new Intent();
				String phone=(String) map.get("phone");
				intent.putExtra("phone", phone);
				setResult(0, intent);
				//���ѡ������ҳ��
				finish();
				
			}
		});
	}
	
	/**
	 * ��ȡ�ֻ���ϵ��
	 * 
	 * @return
	 */
	private List<Map<String, String>> getContactInfo() {
		// �����е���ϵ�˴���
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// �õ�һ�����ݽ�����
		ContentResolver resolver = getContentResolver();
		// ��ȡ�ֻ���ϵ�����ݿ�����ϵ��id�ı�·��
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		// ֻ��ѯraw_contacts�������Ϊcontact_id��
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		while (cursor.moveToNext()) {
			// ��Ϊֻ��һ������ֱ��ȡ���Ӽ��̣���Ϊ�Ǵ�0��ʼ����������λ��ֱ�Ӵ�0����
			String contact_id = cursor.getString(0);
			if (contact_id != null) {
				// �����ĳһ����ϵ�˴���
				Map<String, String> map = new HashMap<String, String>();
				// �����ѯ��id��Ϊnull����ô���Ų�ѯ��Ӧ��data��id��Ӧ��"data1","mimetype"��
				// Cursor dataCursor=resolver.query(uriData, new
				// String[]{"data1","mimetype" }, "contact_id=?"
				// , new String []{"contact_id"}, null);�������
				Cursor dataCursor = resolver.query(uriData, new String[] {
						"data1", "mimetype" }, "contact_id=?",
						new String[] { contact_id }, null);
				// ����ѯ���ȡ��
				while (dataCursor.moveToNext()) {
					// ȡ����һ�н��
					String data1 = dataCursor.getString(0);
					// ȡ���ڶ��н��
					String mimetype = dataCursor.getString(1);
					// Log.d("MainActivity",
					// "data1=="+data1+"==mimetype=="+mimetype);
					if ("vnd.android.cursor.item/name".equals(mimetype)) {
						// ��ϵ�˵�����
						map.put("name", data1);
					} else if ("vnd.android.cursor.item/phone_v2"
							.equals(mimetype)) {
						// ��ϵ�˵绰
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
