package com.person.blog.blog.controller.index;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.person.blog.blog.domain.BlogAdmin;
import com.person.blog.blog.service.impl.BlogAdminServiceImpl;
import com.person.blog.common.JsonResult;
import com.person.blog.tools.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiong
 * @since 2019-05-13
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    JsonResult jsonResult;

    @Autowired
    BlogAdminServiceImpl blogAdminServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RequestTools requestTools;


    /**
     * 后台用户验证登录
     * @param adminname
     * @param password
     * @param rememberMe
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "adminLogin",method = RequestMethod.POST)
    public String adminLogin(@RequestParam("adminname") String adminname, @RequestParam("password") String password,@RequestParam("vrifyCode") String vrifyCode, @RequestParam("rememberMe") boolean rememberMe,
                             HttpServletRequest request, HttpServletResponse response){
        if (adminname.isEmpty()||password.isEmpty()){
            return jsonResult.error(202,"必填字段不能为空！");
        }
        if (request.getSession().getAttribute("vrifyCode") == null){
            return jsonResult.error(202,"验证码已过期，请重新获取！");
        }
        System.out.println(vrifyCode+"-"+request.getSession().getAttribute("vrifyCode").toString());
        if (!vrifyCode.equals(request.getSession().getAttribute("vrifyCode").toString())){
            return jsonResult.error(202,"验证码不正确！");
        }
        BlogAdmin blogAdmin = blogAdminServiceImpl.getOne(new QueryWrapper<BlogAdmin>().eq("adminname",adminname));
        if (blogAdmin != null){
            String adminPassword = password+blogAdmin.getSalt();
            String md5Password = DigestUtils.md5DigestAsHex(adminPassword.getBytes());
            if (md5Password.equals(blogAdmin.getPassword())){
                if (rememberMe){
                    Cookie cookie=new Cookie("adminnameCookie",adminname);
                    cookie.setMaxAge(604800);
                    cookie.setPath("admin/**");
                    response.addCookie(cookie);
                }else{
                    request.getSession().setAttribute("adminnameSession",adminname);
                }
                redisTemplate.opsForValue().set(adminname,requestTools.getIpAddress(request));
                return jsonResult.ok("登录成功！","登录成功！");
            }
        }
        return jsonResult.error(204,"用户名或密码不正确！");
    }

}

