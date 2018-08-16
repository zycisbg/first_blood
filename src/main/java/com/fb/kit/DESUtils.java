package com.fb.kit;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * @Description: TODO(DES算法加解密)
 * @author sun
 * @date 2015年12月16日 下午2:57:47
 * 
 */
@SuppressWarnings("restriction")
public class DESUtils {

	private static final String KEY = "chinaxxx";//八位

	public DESUtils() {
	}

	private static byte[] desEncrypt(byte plainText[]) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(KEY.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(1, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	private static byte[] desDecrypt(byte encryptText[]) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(KEY.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(2, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * <ul>
	 * <b>加密</b>
	 * <li>若需将加密信息作为URL参数传递，须使用该方法进行加密</li>
	 * <li>因为DES加密串中的特殊字符无法在URL中传递</li>
	 * </ul>
	 * 
	 * @param input
	 *            需要进行加密的信息明文
	 * @return 经过处理的密文（已过滤掉特殊字符）
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String input) throws Exception {
		return java.net.URLEncoder.encode(base64Encode(desEncrypt(input
				.getBytes())));
	}

	/**
	 * <ul>
	 * <b>加密</b>
	 * <li>若加密信息作为服务器端参数进行传递（如:server端返回值）采用该算法</li>
	 * <li>因为DES加密串中的特殊字符需在URL中传递,因此没有必要进行数据处理</li>
	 * </ul>
	 * 
	 * @param input
	 *            需要进行加密的信息明文
	 * @return 信息密文（特殊字符未过滤）
	 * @throws Exception
	 */
	public static String encryptNoParse(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	/**
	 * <ul>
	 * <b>解密</b>
	 * <li>采用约定的密钥进行解密</li>
	 * </ul>
	 * 
	 * @param input
	 *            信息密文
	 * @return 信息明文
	 * @throws Exception
	 */
	public static String decrypt(String input) throws Exception {
		byte result[] = base64Decode(input);
		return new String(desDecrypt(result));
	}

	private static String base64Encode(byte s[]) {
		if (s == null) {
			return null;
		} else {
			BASE64Encoder b = new BASE64Encoder();
			return b.encode(s);
		}
	}

	private static byte[] base64Decode(String s) throws IOException {
		if (s == null) {
			return null;
		} else {
			BASE64Decoder decoder = new BASE64Decoder();
			byte b[] = decoder.decodeBuffer(s);
			return b;
		}
	}

	/**
	 * String2Unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String convert(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	public static String revert(String str) {
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1) {
			return str;
		}
		StringBuffer sb = new StringBuffer(1000);

		for (int i = 0; i < str.length();) {
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++) {
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar) {
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
					break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		
		//无格式传输 (加解密)
//		System.out.println(DESUtils.encryptNoParse("1"));// 服务端之间进行传递使用
//		System.out.println(DESUtils.decrypt(DESUtils.encryptNoParse("1")));
		
		//普通url传输 (加解密)
//		System.out.println(DESUtils.encrypt("1"));// 作为普通Url进行使用
//		System.out.println(DESUtils.decrypt(URLDecoder.decode(DESUtils.encrypt("1"))));
		
	}
}
