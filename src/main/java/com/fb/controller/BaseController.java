package com.fb.controller;
//                         _ooOoo_  
//                        o8888888o  
//                        88" . "88  
//                        (| -_- |)  
//                         O\ = /O  
//                     ____/`---'\____  
//                   .   ' \\| |// `.  
//                    / \\||| : |||// \  
//                  / _||||| -:- |||||- \  
//                    | | \\\ - /// | |  
//                  | \_| ''\---/'' | |  
//                   \ .-\__ `-` ___/-. /  
//                ___`. .' /--.--\ `. . __  
//             ."" '< `.___\_<|>_/___.' >'"".  
//            | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//              \ \ `-. \_ __\ /__ _/ .-` / /  
//      ======`-.____`-.___\_____/___.-`____.-'======  
//                         `=---='  
//
//      .............................................  
//               佛祖保佑             永无BUG 
//       佛曰:  
//               写字楼里写字间，写字间里程序员；  
//               程序人员写程序，又拿程序换酒钱。  
//               酒醒只在网上坐，酒醉还来网下眠；  
//               酒醉酒醒日复日，网上网下年复年。  
//               但愿老死电脑间，不愿鞠躬老板前；  
//               奔驰宝马贵者趣，公交自行程序员。  
//               别人笑我忒疯癫，我笑自己命太贱；  
//               不见满街漂亮妹，哪个归得程序员？ 

import com.fb.core.Const;
import com.fb.kit.CommonUtils;
import com.fb.kit.ErrorUtils;
import com.fb.kit.ToolsUtils;
import com.fb.util.ImageUploadUtil;
import com.google.common.collect.Maps;
import com.jfinal.core.Controller;
import com.jfinal.ext.kit.PageSort;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller 父类 BaseController
 * @author sun
 * @date 2016年7月20日 上午10:33:07
 */
public class BaseController extends Controller {

	protected boolean isGet() {
		return Const.Request.REQUEST_GET.equalsIgnoreCase(getRequest().getMethod());
	}

	protected boolean isPost() {
		return Const.Request.REQUEST_POST.equalsIgnoreCase(getRequest().getMethod());
	}
	
	public void setMsg(String msgType, String msgContent) {
		getSession().setAttribute(Const.MSG_TYPE, msgType);
		getSession().setAttribute(Const.MSG_CONTENT, msgContent);
	}
	
	/**消息提醒
	 * 
	 * @author sun
	 * @date 2016年10月14日 上午10:51:36
	 * @param msgType	类型
	 * @param codes		错误提示信息code码，支持多个，以,逗号分割
	 */
	public void setMsg(String msgType, String codes, boolean isRedirect) {
		if(isRedirect){
			/** redirect跳转，需要将提示信息存入session域 **/
			setSessionAttr(Const.MSG_TYPE, msgType);
			setSessionAttr(Const.MSG_CONTENT, ErrorUtils.getMessage(codes));
		}else{
			setAttr(Const.MSG_TYPE, msgType);
			setAttr(Const.MSG_CONTENT, ErrorUtils.getMessage(codes));
		}
	}
	
	/**
	 * redirect 跳转
	 * @author sun
	 * @date 2016年10月12日 下午1:31:47
	 * @param path
	 * @param method
	 */
	public void redirect(String path, String method) {
		if(StringUtils.isEmpty(method)){
			method = "search";/** 默认跳转到list列表页 **/
		}
		super.redirect(getAttr("ctx") + path + "/" + method + getAttr("suffix"));
	}
	
	/**
	 * Gritter 通知窗口插件
	 * @author sun
	 * @date 2016年8月22日 下午4:09:07
	 * @param type	类型(不可为空)， 包含	gritter-default、gritter-info、gritter-warning、gritter-error、gritter-success、gritter-center
	 * @param title	通知窗口标题
	 * @param content	通知内容(不可为空)
	 * @param headImage	头像
	 * @param closeTime	窗口关闭时间，毫秒, 这块限制的至少2秒关闭
	 */
	public void setGritter(String type, String title, String content, String headImage, Integer closeTime, Boolean flag) {
		if(!StringUtils.isEmpty(type) && !StringUtils.isEmpty(content)){
			setAttr(CommonUtils.Gritter.GRITTER_TYPE, type);
			setAttr(CommonUtils.Gritter.GRITTER_TITLE, title);
			setAttr(CommonUtils.Gritter.GRITTER_CONTENT, content);
			setAttr(CommonUtils.Gritter.GRITTER_IMAGE, type.equals(CommonUtils.Gritter.GRITTER_TYPE_CENTER)?null:headImage);
			setAttr(CommonUtils.Gritter.GRITTER_TIME, closeTime==null?2000:closeTime);
			setAttr(CommonUtils.Gritter.GRITTER_LIGHT, flag!=null && flag?"gritter-light":"");
		}
	}
	
	/**
	 * 分页封装
	 * @author sun
	 * @date 2016年8月4日 下午5:16:13
	 * @param orderBy
	 * @param order
	 * @return
	 */
	protected PageSort pageSort(String orderBy, String order){
		String pageModelName = "pageSort";//页面name对象名称
		PageSort pageSort = getModel(PageSort.class, pageModelName);
		//获取分页
		int pageNumber = getParaToInt("pageNumber", Const.PAGE_NUMBER);
		int pageSize = getParaToInt("pageSize", Const.PAGE_SIZE);
		pageSort.setPageNumber(pageNumber);
		pageSort.setPageSize(pageSize);
		setAttr(pageModelName, pageSort);
		if(StringUtils.isEmpty(pageSort.getOrderBy()) && StringUtils.isEmpty(pageSort.getOrder()) && !StringUtils.isEmpty(orderBy) && !StringUtils.isEmpty(order)){
			pageSort.setOrderBy(orderBy);
			pageSort.setOrder(order);
		}
		return pageSort;
	}

	/**
	 * 保存文件到本地
	 * @param file
	 * @param upload
	 * @param absolutePath
     * @return
     */
	public static Map<String, Object> toLocal(File file, final String upload, String absolutePath){
		return toLocal(file,upload,absolutePath,true,null);
	}

	public static Map<String, Object> toLocal(File file, final String upload, String absolutePath,boolean flag,String fileName){
		Map<String, Object> res = Maps.newHashMap();
		Integer code = 501;
		String uploadPath = null;

		FileOutputStream out = null;
		InputStream is = null;
		try {
			if(flag && StringUtils.isBlank(fileName)){
				fileName = file.getName();
				fileName = ToolsUtils.getName(fileName);
			}
			if (fileName != null && !fileName.equals("")) {
				int idx = fileName.lastIndexOf(".");
				if (idx != -1){
					String ext = fileName.substring(idx+1).toUpperCase();
					ext = ext.toLowerCase( );
					if (!"jpg".equals(ext)&&!"png".equals(ext)&&!"jpeg".equals(ext)&&!"gif".equals(ext)){
						res.put("code", code);
						res.put("message", "只能上传jpg,png,jpeg,gif类型的文件!");
						return res;
					}
				}
				/**
				 * 没有则新建目录
				 */
				ToolsUtils.mkdir(absolutePath);
				/**
				 * 保存新文件
				 */
				final String savefile = absolutePath + "/" + fileName;

				File savePath = new File(savefile);
				if (!savePath.isDirectory()) savePath.createNewFile();
				is = new FileInputStream(file);
				out = new FileOutputStream(savePath);

				byte[] buffer = new byte[2048];
				int x = 0;
				while ((x = is.read(buffer)) != -1)
				{
					out.write(buffer, 0, x);
				}
				is.close();
				out.close();

				code = 200;
				uploadPath = upload + "/" + fileName;
				res.put("id", "image");
				res.put("savePath", upload.substring(1));
				res.put("code", code);
				res.put("message", "操作成功");
				res.put("uri", uploadPath);
			} else {
				res.put("id", "image");
				res.put("code", 500);
				res.put("message", "操作失败！文件为空");
				res.put("uri", uploadPath);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(is!=null){try {is.close();} catch (IOException e) {e.printStackTrace();}}
			if(out!=null){try {out.close();} catch (IOException e) {e.printStackTrace();}}
		}
		return res;
	}

	/**
	 * 上传裁剪后的文件或原文件到oss
	 * @param srcImageFile
	 * @param x
	 * @param y
	 * @param destWidth
	 * @param destHeight
	 * @param savePath
     * @return
     */
	public static String toCut(final String srcImageFile, final int x, final int y, final int destWidth, final int destHeight,String savePath) {
		Image img;
		ImageFilter cropFilter;
		// 读取源图像
		BufferedImage bi;
		try {
			if(x==0 && y==0 && destHeight==0 && destWidth==0){
				//原始图片直接上传oss
			}else{
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
				ImageIO.write(tag, ToolsUtils.getExtention(srcImageFile), new File(srcImageFile));
			}
			//缩略图提交至阿里云OSS
			String url = ImageUploadUtil.saveFileToOss(savePath, new File(srcImageFile));
			//删除本地图片
			//ToolsUtils.deleteLocalFile(srcImageFile);
			return url;
			// 输出为文件
			// 再次进行缩放
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	/**
			* 判断是否含有特殊字符
	*
			* @param str
	* @return true为包含，false为不包含
	*/
	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}


	protected String encodeFileName(String fileName) throws UnsupportedEncodingException {
		String userAgent = getRequest().getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			//非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		return fileName;
	}
}
