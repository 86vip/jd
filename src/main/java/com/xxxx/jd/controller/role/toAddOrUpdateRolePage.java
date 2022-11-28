package com.xxxx.jd.controller.role;

import com.xxxx.jd.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/role/toAddOrUpdateRolePage")
public class toAddOrUpdateRolePage extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("role");
        String id = req.getParameter("roleId");
        if (id != null) {
            req.getSession().setAttribute("role", roleService.selectByPrimaryKey(Integer.parseInt(id)));
        }
        resp.sendRedirect("add_update.ftl");
    }
}
