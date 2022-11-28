package com.xxxx.jd.controller.module;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.model.TreeModel;
import com.xxxx.jd.service.ModuleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/module/queryAllModules")
public class queryAllModules extends HttpServlet {
    ModuleService moduleService = new ModuleService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        List<TreeModel> list=moduleService.queryAllModules(req.getParameter("roleId"));
        resp.getWriter().write(JSON.toJSONString(list));
    }
}
