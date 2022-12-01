package com.xxxx.jd.dao;

import com.xxxx.jd.query.CustomerQuery;
import com.xxxx.jd.vo.Customer;

import java.util.List;

public interface CustomerDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> selectByParams(CustomerQuery customerQuery);

    Integer deleteBatch(String[] ids);
}