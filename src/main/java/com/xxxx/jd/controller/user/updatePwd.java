package com.xxxx.jd.controller.user;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.UserService;
import com.xxxx.jd.utils.LoginUserUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/updatePwd")
public class updatePwd extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        //通过request对象，获取设置在cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用Service层修改密码的功能，得到ResultInfo对象
        Object o = userService.updatePassWord(userId, req.getParameter("oldPwd"), req.getParameter("newPwd"), req.getParameter("repeatPwd"));
        ResultInfo resultInfo;
        if (o instanceof String) {
            resultInfo=Result.fail((String) o);
        }else{
            resultInfo = Result.success();
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
