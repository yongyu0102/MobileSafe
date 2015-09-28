package peng.zhang.mobilesafe01.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
		/**
		 * ʹ��MD5�����㷨
		 */
	
	public static String md5password(String password){
		try {
//			�õ�һ����ϢժҪ��
			/**
			 * java.security.MessageDigest������ΪӦ�ó����ṩ��ϢժҪ�㷨�Ĺ��ܣ��� MD5 �� SHA �㷨���򵥵�˵������
			 * ������ɢ���롣��ϢժҪ�ǰ�ȫ�ĵ����ϣ�����������������С�����ݣ�����̶����ȵĹ�ϣֵ
			 */
			MessageDigest digest=MessageDigest.getInstance("md5");
			//ͨ��ִ��������Ϣ�ó��̶����ȹ�ϣֵ���ؽ��
			byte[] result=digest.digest(password.getBytes());
			StringBuffer buffer=new StringBuffer();
			for(byte b: result){
				//��ÿһ��byte��ʮ����������0xff��11111111���������У�ȡ�ڰ�λ����0������λ����
				int number=b & 0xff;
				//��ʮ��������ת��Ϊʮ�����������ַ�����ʽ��ʾ
				String str=Integer.toHexString(number);
				if(str.length()==1){
					//���ʮ�������ַ�������Ϊ1����ôǰ���0��ʾ
					buffer.append("0");
				}
				buffer.append(str);
			}
			//���ر�׼��md5���ܽ��
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
