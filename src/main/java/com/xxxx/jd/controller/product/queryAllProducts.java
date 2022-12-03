package com.xxxx.jd.controller.product;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/queryAllProducts")
public class queryAllProducts extends HttpServlet {
    private ProductService productService = new ProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(productService.queryAllProducts()));
    }
}
