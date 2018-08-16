package com.fb.kit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * 可逆加密算法
 * @author sun
 * @date 2016年8月23日 上午10:39:46
 */
public class SecurityKit {

	/**
	 * 将二进制转换成16进制
	 * @author sun
	 * @date 2016年8月23日 上午10:39:53
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * @author sun
	 * @date 2016年8月23日 上午10:39:58
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 获取密钥
	 * @author sun
	 * @date 2016年8月23日 上午10:40:03
	 * @param strKey
	 * @return
	 */
	private static SecretKey generateKey(String strKey) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			generator.init(128, secureRandom);
			return generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加密 
	 * @author sun
	 * @date 2016年8月23日 上午10:40:13
	 * @param content	需要加密的内容
	 * @param password	加密密码
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(password));
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * @author sun
	 * @date 2016年8月23日 上午10:40:24
	 * @param content	 待解密内容
	 * @param password	解密密钥
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, generateKey(password));
			byte[] result = cipher.doFinal(parseHexStr2Byte(content));
			return new String(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String key = "123";
//		System.out.println(encrypt("111111", key));
//		System.out.println(decrypt(encrypt("111111", key), key));
	}

}
