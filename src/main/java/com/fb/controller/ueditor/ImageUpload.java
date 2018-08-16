package com.fb.controller.ueditor;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageUpload extends HttpServlet implements Servlet {
	static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Uploader up = new Uploader(request);
		up.setSavePath("upload");
		String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
		up.setAllowFiles(fileType);
		up.setMaxSize(10000); //单位KB
		try {
			up.upload();
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("出错了！");
		}
		response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
	}
}
