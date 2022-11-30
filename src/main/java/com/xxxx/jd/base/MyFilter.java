package com.xxxx.jd.base;

import com.xxxx.jd.service.PermissionService;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.vo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class MyFilter implements Filter {
    private UserService userService = new UserService();
    private PermissionService permissionService = new PermissionService();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute("ctx", "/jd");
        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie((HttpServletRequest) servletRequest);
        //根据用户id查询用户对象
        User user = userService.selectByPrimaryKey(userId);
        //设置session作用域（request作用域只适用与一次请求，如果用request作用域，多次跳转页面会失效）
        servletRequest.setAttribute("user", user);
        //通过用户id查询当前登录用户的资源列表（查询对应资源的权限码）
        List<String> permissions = permissionService.queryUserHasPermissionByUserId(userId);
        //将集合设置到session作用域中
        servletRequest.setAttribute("permissions", permissions);
        // 把请求传回过滤链
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
