package com.xxxx.jd.controller.index;

import com.xxxx.jd.service.PermissionService;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/main"})
public class main extends HttpServlet {
    private UserService userService = new UserService();
    private PermissionService permissionService = new PermissionService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("main.ftl");
    }
}
