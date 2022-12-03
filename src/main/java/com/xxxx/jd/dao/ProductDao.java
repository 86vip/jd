package com.xxxx.jd.dao;

import com.xxxx.jd.query.ProductQuery;
import com.xxxx.jd.vo.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByParams(ProductQuery productQuery);

    int deleteBatch(String[] ids);

    List<Map<String, Object>> queryAllProducts();
}