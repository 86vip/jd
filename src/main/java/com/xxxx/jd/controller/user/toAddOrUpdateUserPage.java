package com.xxxx.jd.controller.user;

import com.xxxx.jd.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/toAddOrUpdateUserPage")
public class toAddOrUpdateUserPage extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("userInfo");
        String id = req.getParameter("id");
        if (id != null) {
            req.getSession().setAttribute("userInfo", userService.selectByPrimaryKey(Integer.parseInt(id)));
        }
        resp.sendRedirect("add_update.ftl");
    }
}
