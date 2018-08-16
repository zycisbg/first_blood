package com.fb.util;

import com.fb.kit.OSSUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ImageUploadUtil {

	private final static String UPLOAD_PATH = "/upload";
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 保存文件到OSS
	 * @param savePath
	 * @param savefile
	 * @param fileName
	 */
	public static void saveFileToOss(String savePath,String savefile,String fileName){
		OSSUtils ossUtils = null;
		InputStream inputUpload = null;
		try{

			File fileUpload = new File(savefile);
			inputUpload = new FileInputStream(fileUpload);		
			
			//文件存储到OSS
			ossUtils = new OSSUtils();
			ossUtils.getOSSClient(OSSUtils.hnjbBucket,fileName);
			ossUtils.fileUploadWithDirectory(savePath+"/", inputUpload);
					
			ossUtils.shutDownOss();
			inputUpload.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(inputUpload!=null){try {inputUpload.close();} catch (IOException e) {e.printStackTrace();}}
			if(ossUtils!=null){ossUtils.shutDownOss();};
		}		
	}

	/**
	 * 保存文件到OSS
	 * @param savePath
	 * @param fileUpload
	 */
	public static String saveFileToOss(String savePath,File fileUpload){
		OSSUtils ossUtils = null;
		InputStream inputUpload = null;
		try{

			//File fileUpload = new File(savefile);
			inputUpload = new FileInputStream(fileUpload);

			//文件存储到OSS
			ossUtils = new OSSUtils();
			ossUtils.getOSSClient(OSSUtils.hnjbBucket,fileUpload.getName());
			ossUtils.fileUploadWithDirectory(savePath+"/", inputUpload);

			ossUtils.shutDownOss();
			inputUpload.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(inputUpload!=null){try {inputUpload.close();} catch (IOException e) {e.printStackTrace();}}
			if(ossUtils!=null){ossUtils.shutDownOss();};
		}
		return savePath+"/"+fileUpload.getName();
	}
	
	/**
	 * 保存文件到OSS
	 * @param savePath
	 */
	public static boolean saveFileToOssByStream(String savePath,InputStream inputUpload,String fileName){
		OSSUtils ossUtils = null;
		try{
			//文件存储到OSS
			ossUtils = new OSSUtils();
			ossUtils.getOSSClient(OSSUtils.hnjbBucket,fileName);
			ossUtils.fileContentUpload(savePath+"/", inputUpload,"GBK");

			ossUtils.shutDownOss();
			inputUpload.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(inputUpload!=null){try {inputUpload.close();} catch (IOException e) {e.printStackTrace();}}
			if(ossUtils!=null){ossUtils.shutDownOss();};
		}
	}
}
