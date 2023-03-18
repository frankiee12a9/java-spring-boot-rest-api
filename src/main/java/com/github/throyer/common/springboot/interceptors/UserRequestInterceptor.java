package com.github.throyer.common.springboot.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import com.github.throyer.common.springboot.domain.management.entity.UserRequestInfo;
import com.github.throyer.common.springboot.domain.management.service.UserRequestHandlerService;

public class UserRequestInterceptor implements HandlerInterceptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserRequestInterceptor.class);

  @Autowired
  private UserRequestHandlerService userRequestHandlerService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
    request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); 
    String ip = request.getRemoteAddr();
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("WL-Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("HTTP_CLIENT_IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getRemoteAddr();  
    }

    var requestInfo = new UserRequestInfo("", "", "", "", "", "");
    // requestInfo.setIpAddress(ip);
    // requestInfo.setGreeting("Hi!! I'm USER");
    // requestInfo.setField0(request.getContextPath());
    // requestInfo.setField1(request.getProtocol());
    // requestInfo.setField2(request.getQueryString());

    LOGGER.info("> UserRequestInterceptor: Opps... SOMETHING INTERCEPTED...." + requestInfo);

    // userRequestHandlerService.userRequestStored(requestInfo);

    return true;
  }
}
