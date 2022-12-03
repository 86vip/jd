package com.xxxx.jd.controller.orderProduct;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.OrderProductService;
import com.xxxx.jd.vo.OrderProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orderProduct/add")
public class add extends HttpServlet {
    private OrderProductService orderProductService = new OrderProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(Integer.valueOf(req.getParameter("oId")));
        orderProduct.setProductId(Integer.valueOf(req.getParameter("name")));
        try {
            orderProduct.setNumber(Integer.valueOf(req.getParameter("number")));
        } catch (Exception e) {
            e.printStackTrace();
            orderProduct.setNumber(null);
        }
        Object r = orderProductService.addOrderProduct(orderProduct);
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        } else {
            resultInfo = Result.success("添加产品成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
