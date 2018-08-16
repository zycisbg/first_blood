package com.fb.kit;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * 下载 工具类
 * 
 * @author sun
 * @date 2016年8月23日 上午10:38:53
 */
public class DownloadUtils {

	/**
	 * 下载文件到本地
	 * 
	 * @author sun
	 * @date 2016年8月23日 上午10:39:05
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static void download(String urlString, String filename)
			throws Exception {

		URL url = new URL(urlString);// 构造URL
		URLConnection con = url.openConnection();// 打开连接
		InputStream is = con.getInputStream();// 输入流
		String code = con.getHeaderField("Content-Encoding");
		if ((null != code) && code.equals("gzip")) {
			GZIPInputStream gis = new GZIPInputStream(is);
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = gis.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			gis.close();
			os.close();
			is.close();
		} else {
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		}
	}

}