package com.xxxx.jd.controller.role;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/role/addGrant")
public class addGrant extends HttpServlet {
    private RoleService roleService = new RoleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Object r = roleService.addGrant(req.getParameter("roleId"), req.getParameterValues("mIds"));
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        } else {
            resultInfo = Result.success("角色授权成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
