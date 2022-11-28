package com.xxxx.jd.controller.user;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.model.UserModel;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;
import com.xxxx.jd.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/user/login")
public class login extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        //调用service层登录方法
        Object userModel = userService.userLogin(userName, userPwd);

        if (userModel instanceof UserModel) {
            //设置ResultInfo中result的值（将数据返回给请求）
            resultInfo.setResult(userModel);
        }
        if (userModel instanceof String) {
            resultInfo.setCode(300);
            resultInfo.setMsg((String) userModel);
        }

        response.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
