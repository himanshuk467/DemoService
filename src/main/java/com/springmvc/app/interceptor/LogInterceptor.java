package com.springmvc.app.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static Logger logger = LogManager.getLogger(LogInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        long executionStartTime = System.currentTimeMillis();
        request.setAttribute("start-time", executionStartTime);
        String fishTag = UUID.randomUUID().toString();
        String user = "himanshu"; /* GET USER INFORMATION FROM SESSION */
        ThreadContext.put("requestId", fishTag);
        ThreadContext.put("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        long executionStartTime = (Long)request.getAttribute("start-time");
        long renderingStartTime = System.currentTimeMillis();
        request.setAttribute("rendering-start-time", renderingStartTime);
        long executionDuration = renderingStartTime - executionStartTime;
        ThreadContext.put("execution-duration", String.valueOf(executionDuration));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long renderingStartTime = (Long)request.getAttribute("rendering-start-time");
        long renderingEndTime = System.currentTimeMillis();
        long renderingDuration = renderingEndTime - renderingStartTime;
        ThreadContext.put("rendering-duration", String.valueOf(renderingDuration));
        logger.info("My interceptor handler message");
        ThreadContext.clearMap();
    }
}
