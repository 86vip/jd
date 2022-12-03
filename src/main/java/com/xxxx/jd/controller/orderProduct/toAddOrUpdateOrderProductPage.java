package com.xxxx.jd.controller.orderProduct;

import com.xxxx.jd.service.OrderProductService;
import com.xxxx.jd.vo.OrderProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/orderProduct/toAddOrUpdateOrderProductPage")
public class toAddOrUpdateOrderProductPage extends HttpServlet {
    private OrderProductService orderProductService = new OrderProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("orderProduct");
        String oId = req.getParameter("orderId");
        if (Objects.equals(oId, "")) {
            oId = "0";
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(Integer.valueOf(oId));

        String opId = req.getParameter("orderProductId");
        if (opId != null) {
            orderProduct.setId(Integer.valueOf(opId));
            req.getSession().setAttribute("orderProduct", orderProductService.selectByPrimaryKey(Integer.parseInt(opId)));
        } else {
            req.getSession().setAttribute("orderProduct", orderProduct);
        }
        resp.sendRedirect("add_update.ftl");
    }
}
