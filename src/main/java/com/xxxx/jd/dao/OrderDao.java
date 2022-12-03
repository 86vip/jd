package com.xxxx.jd.dao;

import com.xxxx.jd.query.OrderQuery;
import com.xxxx.jd.vo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Map<String, Object>> selectByParams(OrderQuery orderQuery);

    Map<String, Object> selectById(Integer id);

    Integer deleteBatch(String[] ids);

    List<Map<String, Object>> queryCustomerConsumeRanking();

    List<Map<String, Object>> countOrderState();
}