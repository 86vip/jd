package com.xxxx.jd;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jd1")
public class servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置request作用域数据
        req.setAttribute("msg","你好 freeMarker!");
        //请求转发到指定的模板页面
        req.getRequestDispatcher("f01.ftl").forward(req,resp);
    }
}
