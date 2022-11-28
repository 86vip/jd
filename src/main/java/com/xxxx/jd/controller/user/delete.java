package com.xxxx.jd.controller.user;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/user/delete")
public class delete extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Object result = userService.deleteByIds(req.getParameterValues("ids"));
        ResultInfo resultInfo;
        if (result instanceof String) {
            resultInfo = Result.fail((String) result);
        }else{
            resultInfo = Result.success();
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
