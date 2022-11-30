package com.xxxx.jd.controller.product;

import com.alibaba.fastjson.JSON;
import com.xxxx.jd.query.ProductQuery;
import com.xxxx.jd.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/product/list")
public class list extends HttpServlet {
    private ProductService productService = new ProductService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        ProductQuery productQuery = new ProductQuery();
        productQuery.setName(req.getParameter("name"));
        productQuery.setMerchant(req.getParameter("merchant"));
        productQuery.setType(req.getParameter("type"));
        Map<String, Object> map = productService.queryByParamsForTable(productQuery);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
