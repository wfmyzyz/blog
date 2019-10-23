package com.person.blog.Interceptot;

import com.person.blog.tools.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor implements HandlerInterceptor {

    @Autowired
    RequestTools requestTools;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("token","helloword");
        if (true){
            return true;
        }
        response.setContentType("text/html;charset=UTF-8");
        Cookie[] cookies =  request.getCookies();
        if (cookies !=null && cookies.length>0){
            Cookie cookie = getCookie(cookies,"adminnameCookie");
            if (cookie!=null){
                if (!requestTools.getIpAddress(request).equals(redisTemplate.opsForValue().get(cookie.getValue()))){
                    cookie.setMaxAge(-1);
                    response.getWriter().println("<script>alert('您的账号在另一地登录，您被迫下线！');window.location.href='/admin/login.html';</script>");
                    return false;
                }
                return true;
            }
        }
        Object adminname = request.getSession().getAttribute("adminnameSession");
        if (adminname == null){
            response.getWriter().println("<script>alert('您尚未登录，请先登录！');window.location.href='/admin/login.html';</script>");
            return false;
        }
        if (!requestTools.getIpAddress(request).equals(redisTemplate.opsForValue().get(adminname.toString()))){
            request.getSession().removeAttribute("adminnameSession");
            response.getWriter().println("<script>alert('您的账号在另一地登录，您被迫下线！');window.location.href='/admin/login.html';</script>");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private Cookie getCookie(Cookie[] cookies,String str){
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(str)){
                return cookie;
            }
        }
        return null;
    }
}
