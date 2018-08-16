package com.fb.controller;

import com.alibaba.fastjson.JSONObject;
import com.fb.commons.JfinalUtils;
import com.fb.interceptor.AuthExclusion;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.upload.UploadFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author shi.g.s
 */
@SuppressWarnings("deprecation")
@ControllerBind(controllerKey = "/uploadToOss")
public class UploadImageToOssController extends BaseController
{

    private final static String UPLOAD_PATH = "/upload";
    private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");

    private static int imgCount = 0;

    @Before(GET.class)
    @AuthExclusion
    public void uploadHtml(){
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
        render("image_upload_toOss.html");
    }

    /**
     * 上传图片
     */
    @Before(POST.class)
    @AuthExclusion
    public void imageUpload() {
        HttpSession session = getSession();  //获取当前用户
        boolean isLogin = JfinalUtils.isLogin(session);
        if(!isLogin){
            redirect("/login.action");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        //上传标的资料类型()
        String fileName = getPara("fileName");
        String path = getPara("path");
        /**
         * 新保存的位置
         */
        final String upload = UPLOAD_PATH + "/" + path;
        final String absolutePath = getRequest().getRealPath(upload);
        /**
         * 得到文件
         */
        UploadFile uploadFile = getFile();//JFinal规定getFile()必须最先执行
        File file = uploadFile.getFile();

        if (uploadFile != null) {
            if (imgCount > 300)// 300为文件上传最大数目
                imgCount = 0;

            imgCount++;

            /**
             * 保存到本地
             */
            Map<String, Object> res = toLocal(file, upload, absolutePath,false,fileName);
            /**
             * 上传oss
             */
            String url = toCut(getRequest().getRealPath("/") + res.get("uri"), 0, 0, 0, 0, res.get("savePath").toString());
            //返回任意数据即代表上传成功
            jsonObject.put("fileName", fileName);
            jsonObject.put("upload", upload);
            jsonObject.put("filePath", url);
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("error", "未选择文件");
            jsonObject.put("status", "error");
        }
        renderJson(jsonObject.toJSONString());
    }
}