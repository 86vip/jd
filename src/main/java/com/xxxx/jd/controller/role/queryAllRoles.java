package com.xxxx.jd.controller.role;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/role/queryAllRoles")
public class queryAllRoles extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(roleService.queryAllRoles(req.getParameter("userId"))));
    }
}
