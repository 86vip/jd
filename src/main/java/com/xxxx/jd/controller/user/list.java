package com.xxxx.jd.controller.user;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.query.UserQuery;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/list")
public class list extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(req.getParameter("userName"));
        userQuery.setEmail(req.getParameter("email"));
        userQuery.setPhone(req.getParameter("phone"));
        Map<String,Object> list=userService.queryByParamsForTable(userQuery);
        resp.getWriter().write(JSON.toJSONString(list));
    }
}
