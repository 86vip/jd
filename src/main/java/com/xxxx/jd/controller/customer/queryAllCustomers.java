package com.xxxx.jd.controller.customer;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/queryAllCustomers")
public class queryAllCustomers extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(customerService.queryAllCustomers()));
    }
}
