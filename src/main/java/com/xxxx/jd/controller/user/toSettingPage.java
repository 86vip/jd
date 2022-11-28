package com.xxxx.jd.controller.user;

import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/toSettingPage")
public class toSettingPage extends HttpServlet {
    UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("userInfo", userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(req)));
        resp.sendRedirect("../user/setting.ftl");
    }
}
