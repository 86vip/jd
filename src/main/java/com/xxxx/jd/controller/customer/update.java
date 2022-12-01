package com.xxxx.jd.controller.customer;

import com.alibaba.fastjson.JSON;
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

@WebServlet("/customer/update")
public class update extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(req.getParameter("id")));
        customer.setName(req.getParameter("name"));
        customer.setPhone(req.getParameter("phone"));
        customer.setAddress(req.getParameter("address"));
        Object r = customerService.updateCustomer(customer);
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        } else {
            resultInfo = Result.success("更新客户成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
