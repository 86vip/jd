package com.xxxx.jd.controller.order;

import com.xxxx.jd.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/toOrderEditPage")
public class toOrderEditPage extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("order");
        String sid = req.getParameter("id");
        if (sid.equals("")) {
            sid = "0";
        }
        Integer id = Integer.valueOf(sid);
        req.getSession().setAttribute("order", orderService.selectByPrimaryKey(id));
        resp.sendRedirect("edit.ftl");
    }
}
