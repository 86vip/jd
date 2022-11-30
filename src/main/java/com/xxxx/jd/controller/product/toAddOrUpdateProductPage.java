package com.xxxx.jd.controller.product;

import com.xxxx.jd.service.ProductService;
import com.xxxx.jd.vo.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/toAddOrUpdateProductPage")
public class toAddOrUpdateProductPage extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("product");
        String id = req.getParameter("productId");
        if (id != null) {
            req.getSession().setAttribute("product", productService.selectByPrimaryKey(Integer.parseInt(id)));
        }
        resp.sendRedirect("add_update.ftl");
    }
}
