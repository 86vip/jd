package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.OrderDao;
import com.xxxx.jd.dao.OrderProductDao;
import com.xxxx.jd.query.OrderQuery;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.utils.StringUtils;
import com.xxxx.jd.vo.Order;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class OrderService {

    public Map<String, Object> queryByParamsForTable(OrderQuery orderQuery) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(orderQuery.getPage(), orderQuery.getLimit());
        //得到对应分页对象
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(orderDao.selectByParams(orderQuery));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Map<String, Object> selectByPrimaryKey(Integer id) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Map<String, Object> map = orderDao.selectById(id);
        sqlSession.close();
        return map;
    }

    public Object addOrder(Order order) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        /* 1、参数校验 */
        if (order.getCustomerId() == null) {
            return "订单客户姓名不能为空！";
        }
        if (StringUtils.isBlank(order.getAddress())) {
            return "送货地址不能为空！";
        }
        /* 2、设置默认值 */
        order.setIsValid(1);
        order.setState(0);
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());

        /* 3、执行操作，判断受影响的行数 */
        if (orderDao.insertSelective(order) < 1) {
            return "订单添加失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object deleteByIds(String[] ids) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        if (ids == null || ids.length == 0) {
            return "待删除记录不存在！";
        }
        Integer count = orderProductDao.countByOrderId(ids);
        if (orderDao.deleteBatch(ids) != ids.length||orderProductDao.deleteBatch(ids)!=count) {
            return "删除订单失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object updateOrder(Order order) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        if (order.getId() == null || orderDao.selectByPrimaryKey(order.getId()) == null) {
            return "待更新记录不存在！";
        }
        if (order.getState() == null) {
            return "订单状态不能为空！";
        }

        order.setUpdateDate(new Date());

        if (orderDao.updateByPrimaryKeySelective(order) < 1) {
            return "更新订单状态失败！";
        }
        sqlSession.commit();
        sqlSession.close();

        return null;
    }

    public Map<String,Object> queryCustomerConsumeRanking() {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        List<Map<String, Object>> list = orderDao.queryCustomerConsumeRanking();
        sqlSession.close();
        Map<String, Object> map = new HashMap<>();
        map.put("count",list.size());
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");
        return map;
    }

    public Map<String, Object> countOrderState01() {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        List<Map<String, Object>> dataList = orderDao.countOrderState();
        List<String> data1 = new ArrayList<>();
        List<Integer> data2 = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            dataList.forEach(data->{
                data1.add(data.get("state").toString());
                data2.add(Integer.valueOf(data.get("count").toString()));
            });
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data1", data1);
        map.put("data2", data2);
        return map;
    }

    public Map<String,Object> countOrderState02() {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        List<Map<String, Object>> dataList = orderDao.countOrderState();
        List<String> data1 = new ArrayList<>();
        List<Map<String,Object>> data2 = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            dataList.forEach(data->{
                data1.add(data.get("state").toString());
                Map<String, Object> map = new HashMap<>();
                map.put("name", data.get("state"));
                map.put("value", data.get("count"));
                data2.add(map);
            });
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data1", data1);
        map.put("data2", data2);
        return map;
    }
}
