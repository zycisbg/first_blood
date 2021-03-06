package com.fb.kit;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 惠农聚宝官网帐号体系的加解密方式
 * @author sun
 * @date 2016年8月23日 上午10:38:11
 */
public class HashCrypt {
	public static String SHA = "SHA";

	public static String MD5 = "MD5";

	private final static PasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();

	private final static PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

	public static String getDigestHash(String str) {
		return getDigestHash(str, "SHA");
	}

	public static String getDigestHash(String str, String hashType) {
		if (str == null)
			return null;
		return getDigestHash(str, hashType, null);
	}

	public static String getDigestHash(String str, String hashType, Object salt) {
		if (hashType == null) {
			hashType = SHA;
		}

		if (hashType.equals(MD5)) {
			return md5PasswordEncoder.encodePassword(str, salt);
		}

		return shaPasswordEncoder.encodePassword(str, salt);
	}

	public static void main(String[] args) {
//		System.out.println(getDigestHash("123abc"));
	}
}
