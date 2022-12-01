package com.xxxx.jd.controller.customer;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.query.CustomerQuery;
import com.xxxx.jd.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/customer/list")
public class list extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        CustomerQuery customerQuery = new CustomerQuery();
        customerQuery.setName(req.getParameter("name"));
        customerQuery.setPhone(req.getParameter("phone"));
        customerQuery.setAddress(req.getParameter("address"));
        Map<String, Object> map = customerService.queryByParamsForTable(customerQuery);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
