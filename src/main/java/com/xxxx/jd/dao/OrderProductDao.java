package com.xxxx.jd.dao;

import com.xxxx.jd.vo.OrderProduct;

import java.util.List;
import java.util.Map;

public interface OrderProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    OrderProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderProduct record);

    int updateByPrimaryKey(OrderProduct record);

    List<Map<String,Object>> selectByOrderId(Integer orderId);

    Integer deleteBatch(String[] ids);

    Integer countByOrderId(String[] ids);
}