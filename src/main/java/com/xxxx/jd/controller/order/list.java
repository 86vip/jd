package com.xxxx.jd.controller.order;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.query.CustomerQuery;
import com.xxxx.jd.query.OrderQuery;
import com.xxxx.jd.service.OrderService;
import com.xxxx.jd.vo.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@WebServlet("/order/list")
public class list extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OrderQuery orderQuery = new OrderQuery();
        if (req.getParameter("id") != null && !Objects.equals(req.getParameter("id"), "")) {
            orderQuery.setId(Integer.valueOf(req.getParameter("id")));
        }
        orderQuery.setCustomerName(req.getParameter("customerName"));
        if (req.getParameter("state") != null && !Objects.equals(req.getParameter("state"), "")) {
            orderQuery.setState(Integer.valueOf(req.getParameter("state")));
        }
        Map<String, Object> map = orderService.queryByParamsForTable(orderQuery);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
