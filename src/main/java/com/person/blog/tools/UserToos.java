package com.person.blog.tools;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class UserToos {

    public String getUsername(HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        Cookie cookie = getCookie(cookies,"adminnameCookie");
        if (cookie == null){
            String adminnameSession = request.getSession().getAttribute("adminnameSession").toString();
            return adminnameSession;
        }
        return cookie.getValue();
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
