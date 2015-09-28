package peng.zhang.mobilesafe01.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
		/**
		 * 使用MD5加密算法
		 */
	
	public static String md5password(String password){
		try {
//			得到一个信息摘要器
			/**
			 * java.security.MessageDigest类用于为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。简单点说就是用
			 * 于生成散列码。信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值
			 */
			MessageDigest digest=MessageDigest.getInstance("md5");
			//通过执行输入信息得出固定长度哈希值返回结果
			byte[] result=digest.digest(password.getBytes());
			StringBuffer buffer=new StringBuffer();
			for(byte b: result){
				//把每一个byte与十六进制整数0xff（11111111）做与运行，取第八位，用0消除高位补码
				int number=b & 0xff;
				//将十进制整数转换为十六进制数字字符串形式表示
				String str=Integer.toHexString(number);
				if(str.length()==1){
					//如果十六进制字符串长度为1，那么前面加0表示
					buffer.append("0");
				}
				buffer.append(str);
			}
			//返回标准的md5加密结果
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
