package peng.zhang.mobilesafe01.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.TextView;

public class FocusedTextView extends TextView {
	
	/**
	 * �Զ���TextView������һ�������н���
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */

	public FocusedTextView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FocusedTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��ʵTextView����û�н��㣬��ֻ����ƭ��Android������Ϊ�Ѿ��н�����
	 */
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
