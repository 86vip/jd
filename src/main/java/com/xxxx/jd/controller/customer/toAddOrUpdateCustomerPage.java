package com.xxxx.jd.controller.customer;

import com.xxxx.jd.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/toAddOrUpdateCustomerPage")
public class toAddOrUpdateCustomerPage extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("customer");
        String id = req.getParameter("customerId");
        if (id != null) {
            req.getSession().setAttribute("customer", customerService.selectByPrimaryKey(Integer.parseInt(id)));
        }
        resp.sendRedirect("add_update.ftl");
    }
}
