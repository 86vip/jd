package com.xxxx.jd.controller.orderProduct;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.service.OrderProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/orderProduct/list")
public class list extends HttpServlet {
    private OrderProductService orderProductService = new OrderProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Map<String, Object> map = orderProductService.queryByOrderIdForTable(Integer.parseInt(req.getParameter("orderId")));
        resp.getWriter().write(JSON.toJSONString(map));

    }
}
