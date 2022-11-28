package com.xxxx.jd.controller.module;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/module/toAddGrantPage")
public class toAddGrantPage extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("roleId",req.getParameter("roleId"));
        resp.sendRedirect("../role/grant.ftl");
    }
}
