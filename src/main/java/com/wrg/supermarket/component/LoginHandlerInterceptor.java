package com.wrg.supermarket.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginHandlerInterceptor
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/3/28 23:34
 * @Version 1.0
 **/
public class LoginHandlerInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Object user = request.getSession().getAttribute("loginUser");
//        if(user == null){
//            request.setAttribute("msg","没有权限请先登录");
//            request.getRequestDispatcher("/login.html").forward(request,response);
//            return false;
//            //未登录，返回登录页面
//        } else{
//            return true;
//            //已登录，放行请求
//        }
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
