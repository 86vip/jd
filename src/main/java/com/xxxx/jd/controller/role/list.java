package com.xxxx.jd.controller.role;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.query.RoleQuery;
import com.xxxx.jd.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/role/list")
public class list extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setRoleName(req.getParameter("roleName"));
        Map<String, Object> map = roleService.queryByParamsForTable(roleQuery);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
