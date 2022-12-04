package com.xxxx.jd.base;

import com.xxxx.jd.dao.UserDao;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = {"/main.ftl","/main","/main/*","/main*"})
public class LoginFilter implements Filter {
    private UserService userService = new UserService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie((HttpServletRequest) servletRequest);
        //判断用户id或者数据库中是否存在该用户记录
        if (userId == null || userService.selectByPrimaryKey(userId)==null) {
            //抛出未登录异常
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/jd/index");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
