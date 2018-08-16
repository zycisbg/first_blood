package com.fb.interceptor;

import com.jfinal.handler.Handler;
import com.jfinal.log.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器，过滤特殊url
 * @author sun
 *
 */
public class UrlSkipHandler extends Handler {
	protected static final Logger LOG = Logger.getLogger(UrlSkipHandler.class);
	
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		//自定义某种格式   不交予 jfinal 处理
        if (target.indexOf(".jsp")==-1 && target.indexOf("/websocket")==-1 && target.indexOf("/authImage")==-1
				&& target.indexOf("/ImageUpload")==-1 && target.indexOf("/ScrawlUp")==-1
				&& target.indexOf("/FileUp")==-1 && target.indexOf("/GetRemoteImage")==-1
				&& target.indexOf("/ImageManager")==-1 && target.indexOf("/GetMovie")==-1) {
            nextHandler.handle(target, request, response, isHandled);
        }else{
        	//放行Jsp，不交由jfinal
        	LOG.info(target);
        }
	}

}
