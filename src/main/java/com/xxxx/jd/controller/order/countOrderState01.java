package com.xxxx.jd.controller.order;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/countOrderState01")
public class countOrderState01 extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(orderService.countOrderState01()));
    }
}
