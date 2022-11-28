package com.xxxx.jd.controller.index;

import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/main"})
public class main extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //根据用户id查询用户对象
        User user = userService.selectByPrimaryKey(userId);
        //设置session作用域（request作用域只适用与一次请求，如果用request作用域，多次跳转页面会失效）
        request.getSession().setAttribute("user", user);
        //通过用户id查询当前登录用户的资源列表（查询对应资源的权限码）
//        List<String> permissions = permissionService.queryUserHasPermissionByUserId(userId);
        //将集合设置到session作用域中
//        request.getSession().setAttribute("permissions", permissions);
        response.sendRedirect("main.ftl");
    }
}
