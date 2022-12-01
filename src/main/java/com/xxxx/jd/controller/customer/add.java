package com.xxxx.jd.controller.customer;

import com.alibaba.fastjson.JSON;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.CustomerService;
import com.xxxx.jd.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/add")
public class add extends HttpServlet {
    CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Customer customer = new Customer();
        customer.setName(req.getParameter("name"));
        customer.setPhone(req.getParameter("phone"));
        customer.setAddress(req.getParameter("address"));
        Object r = customerService.addCustomer(customer);
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        } else {
            resultInfo = Result.success("添加客户成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));

    }
}
