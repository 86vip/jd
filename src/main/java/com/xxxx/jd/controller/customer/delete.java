package com.xxxx.jd.controller.customer;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.base.Result;
import com.xxxx.jd.base.ResultInfo;
import com.xxxx.jd.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/delete")
public class delete extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Object r = customerService.deleteByIds(req.getParameterValues("ids"));
        ResultInfo resultInfo;
        if (r instanceof String) {
            resultInfo = Result.fail((String) r);
        }else{
            resultInfo = Result.success("删除客户成功！");
        }
        resp.getWriter().write(JSON.toJSONString(resultInfo));
    }
}
