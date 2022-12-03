package com.xxxx.jd.controller.order;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.OrderService;
import com.xxxx.jd.vo.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/updateOrderState")
public class updateOrderState extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Order order = new Order();
        order.setId(Integer.valueOf(req.getParameter("id")));
        order.setState(Integer.valueOf(req.getParameter("state")));
        Object r = orderService.updateOrder(order);
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        }else{
            resultInfo = Result.success("更新订单状态成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
