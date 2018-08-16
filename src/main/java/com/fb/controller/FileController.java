package com.fb.controller;


import com.fb.commons.JfinalUtils;
import com.fb.interceptor.AuthExclusion;
import com.fb.kit.DateUtils;
import com.fb.kit.OssUploadKit;
import com.fb.kit.ToolsUtils;
import com.google.common.collect.Maps;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author sun
 *
 */
@SuppressWarnings("deprecation")
@ControllerBind(controllerKey = "/file")
public class FileController extends BaseController {
	
	/**
	 * 上传图片弹窗
	 */
	@Before(GET.class)
	@AuthExclusion
	public void upload(){
		HttpSession session = getSession();  //获取当前用户
		boolean isLogin = JfinalUtils.isLogin(session);
		if(!isLogin){
			redirect("/login.action");
			return;
		}
		Enumeration<String> pNames = getParaNames();
		while (pNames.hasMoreElements()) {
			String pkey = pNames.nextElement();
			keepPara(pkey);
		}
		getRequest().setAttribute("page_name", "裁剪上传图片 <span style='color:#FF0000; font-size:11px;'>版权：sunjs.org</span>");
		render("upload.html");
	}

	@Before(POST.class)
	@AuthExclusion
	public void fileUpload() {
		HttpSession session = getSession();  //获取当前用户
		boolean isLogin = JfinalUtils.isLogin(session);
		if(!isLogin){
			redirect("/login.action");
			return;
		}
		UploadFile ufile = getFile("fp", System.getProperty("java.io.tmpdir"));
		if (ufile == null) {
			Map<String, Object> res = Maps.newHashMap();
			res.put("code", 501);
			res.put("message", "参数不足或参数错误");
			Enumeration<String> pNames = getParaNames();
			while (pNames.hasMoreElements()) {
				String pkey = pNames.nextElement();
				res.put(pkey, getPara(pkey));
			}
			renderHtml("<script>window.parent.uploadHandler('"
					+ JsonKit.toJson(res) + "');</script>");
			return;
		}
		Map<String, Object> res = Maps.newHashMap();
		res.put("code", 200);
		res.put("message", "操作成功");

		res.put("uri", OssUploadKit.verifyImagePath(
				PropKit.use("config.properties"), ufile.getFile()));
		Enumeration<String> pNames = getParaNames();
		while (pNames.hasMoreElements()) {
			String pkey = pNames.nextElement();
			res.put(pkey, getPara(pkey));
		}
		renderHtml("<script>window.parent.uploadHandler('"
				+ JsonKit.toJson(res) + "');</script>");
	}
	
	/**
	 * 上传图片至本地
	 */
	@SuppressWarnings({ "rawtypes" })
	@Before(POST.class)
	@AuthExclusion
	public void fileUploadToLocal() {
		HttpSession session = getSession();  //获取当前用户
		boolean isLogin = JfinalUtils.isLogin(session);
		if(!isLogin){
			redirect("/login.action");
			return;
		}
		Map<String, Object> res = Maps.newHashMap();
		Integer code = 501;
		String uploadPath = null;
		try {
			String localPath = getRequest().getRealPath("/");
			// 得到上传的路径
			String upload = "/assets/upload/" + DateUtils.getDateFormat(DateUtils.DATE_FORMAT);
			String absolutePath = localPath + upload;
			String tempPath = getRequest().getRealPath("/") + "/assets/temp";
			// 检测创建文件夹
			ToolsUtils.fileExists(absolutePath);
			ToolsUtils.fileExists(tempPath);
			// 上传部分
			DiskFileUpload fu = new DiskFileUpload();
			// 设置最大文件尺寸，这里是4MB
			fu.setSizeMax(4194304);
			// 设置缓冲区大小，这里是4kb
			fu.setSizeThreshold(4096);
			// 设置临时目录：
			fu.setRepositoryPath(tempPath);
			// 得到所有的文件：
			List fileItems = fu.parseRequest(getRequest());
			Iterator i = fileItems.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (fi.isFormField() || ((String) fi.getFieldName()).equals("name")) {
					res.put(fi.getFieldName(), fi.getString());
				}
				if("id".equals(fi.getFieldName()) && isSpecialChar(fi.getString())){
					res.put("code", code);
					res.put("message", "操作失败，包含非法字符");
					break;
				}
				// 获得文件名，这个文件名包括路径：
				String fileName = fi.getName();
				if (!StringUtils.isEmpty(fileName)) {
					// 修改名字

					int begin = fileName.lastIndexOf(".");
					int end = fileName.length();
					// 得到文件后缀
					String lastName = fileName.substring(begin, end);
					// 得到系统时间 和随机数
					String newName = ToolsUtils.getUUID() + lastName;

					int idx = fileName.lastIndexOf(".");
					if (idx != -1){
						String ext = fileName.substring(idx+1).toUpperCase();
						ext = ext.toLowerCase( );
						if (!"jpg".equals(ext)&&!"png".equals(ext)&&!"jpeg".equals(ext)&&!"gif".equals(ext)){
							res.put("code", code);
							res.put("message", "只能上传jpg,png,jpeg,gif类型的文件!");
							break;
						}
					}
					if (fileName != null) {
						File file = new File(newName);
						File saveFile = new File(absolutePath, file.getName());
						// 把文件上传至目录中
						fi.write(saveFile);
						uploadPath = upload + "/" + file.getName();
						// 在这里可以记录用户和文件信息
						// ...
						// 写入文件a.jpg，你也可以从fileName中提取文件名：

						// fi.write(new File(upload + "a.jpg"));
						code = 200;
						res.put("code", code);
						res.put("message", "操作成功");
						res.put("uri", uploadPath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderHtml("<script>window.parent.uploadHandler('" + JsonKit.toJson(res) + "');</script>");
	}

	@Before(POST.class)
	@AuthExclusion
	public void fileUploadToCut() {
		HttpSession session = getSession();  //获取当前用户
		boolean isLogin = JfinalUtils.isLogin(session);
		if(!isLogin){
			redirect("/login.action");
			return;
		}
		String image = getPara("image");
		Integer dataX = getParaToInt("dataX");
		Integer dataY = getParaToInt("dataY");
		Integer dataWidth = getParaToInt("dataWidth");
		Integer dataHeight = getParaToInt("dataHeight");
//		String dataRotate = getPara("dataRotate");
		String localPath = getRequest().getRealPath("/");
		String url = abscut(localPath+image, dataX, dataY, dataWidth, dataHeight);
		renderText(url);
	}

	public static String abscut(final String srcImageFile, final int x, final int y, final int destWidth, final int destHeight) {
		Image img;
		ImageFilter cropFilter;
		// 读取源图像
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
			img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
			//替换原来的大图片
			ImageIO.write(tag, getExtention(srcImageFile), new File(srcImageFile));
			//讲缩略图提交至阿里云OSS
			String url = OssUploadKit.verifyImagePath(PropKit.use("config.properties"), new File(srcImageFile));
			ToolsUtils.deleteLocalFile(srcImageFile);
			return url;
			// 输出为文件
			// 再次进行缩放
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：提取文件名的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}

}