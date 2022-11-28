package com.xxxx.jd.controller.role;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.RoleService;
import com.xxxx.jd.vo.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/role/add")
public class add extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Role role = new Role();
        role.setRoleName(req.getParameter("roleName"));
        role.setRoleRemark(req.getParameter("roleRemark"));
        Object result = roleService.addRole(role);
        ResultInfo resultInfo;
        if (result instanceof String) {
            resultInfo = Result.fail((String) result);
        }else{
            resultInfo = Result.success();
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
