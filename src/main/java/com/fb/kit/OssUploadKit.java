package com.fb.kit;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.fb.core.Const;
import com.jfinal.kit.Prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ali oss 资源上传工具类
 * @author sun
 *
 */
public class OssUploadKit {
	
	public static String verifyImagePath(Prop config, File file){
		String domain = config.get("alioss.url");
		String key = image(config, file);
		String aliossUrl = "";
		// 上传成功结果返回
		if (key.toLowerCase().endsWith(".jpg") || key.toLowerCase().endsWith(".jpeg") || key.toLowerCase().endsWith(".webp") || key.toLowerCase().endsWith(".png") || key.toLowerCase().endsWith(".bmp")) {
			aliossUrl = domain + "/" + key;
		} else {
			aliossUrl = domain + "/" + key;
		}
		return aliossUrl;
	}
	
	public static String image(Prop config, File file){
		String temp = file.getName();
		String suffix = Const.BLANK;
		if (temp.lastIndexOf(".") > -1) {
			suffix = temp.substring(temp.lastIndexOf(".")).toLowerCase();
		}
		String key = ToolsUtils.getUploadPath(config.get("alioss.dir"), suffix);
		InputStream content = null;
		try {
			content = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
		
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(file.length());
		// 文件上传
		OSSClient client = new OSSClient(config.get("alioss.endpoint"), config.get("alioss.accesskey"), config.get("alioss.secret"));
		try {
			client.putObject(config.get("alioss.bucket"), key, content, meta);
		}  catch(Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			file.deleteOnExit();
		}
		return key;
	}
}
