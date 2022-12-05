package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.base.BaseQuery;
import com.xxxx.jd.dao.OrderDao;
import com.xxxx.jd.dao.OrderProductDao;
import com.xxxx.jd.dao.ProductDao;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.vo.Order;
import com.xxxx.jd.vo.OrderProduct;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderProductService {
    private BaseQuery baseQuery = new BaseQuery();
    public Map<String, Object> queryByOrderIdForTable(Integer orderId) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(baseQuery.getPage(), baseQuery.getLimit());
        //得到对应分页对象
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(orderProductDao.selectByOrderId(orderId));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Object addOrderProduct(OrderProduct orderProduct) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        Order order = orderDao.selectByPrimaryKey(orderProduct.getOrderId());
        if (orderProduct.getOrderId() == null || order == null) {
            return "待添加订单不存在！";
        }
        if (orderProduct.getProductId() == null || productDao.selectByPrimaryKey(orderProduct.getProductId()) == null) {
            return "待添加产品不存在！";
        }
        if (orderProduct.getNumber() == null||orderProduct.getNumber()<1||orderProduct.getNumber()>9999) {
            return "产品数量不合法！";
        }

        orderProduct.setIsValid(1);
        order.setUpdateDate(new Date());

        if (orderProductDao.insertSelective(orderProduct) < 1||orderDao.updateByPrimaryKeySelective(order)<1) {
            return "添加订单产品失败！";
        }
        sqlSession.commit();
        sqlSession.close();

        return null;
    }

    public OrderProduct selectByPrimaryKey(Integer id) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        OrderProduct orderProduct = orderProductDao.selectByPrimaryKey(id);
        sqlSession.close();
        return orderProduct;
    }

    public Object updateOrderProduct(OrderProduct orderProduct) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);

        Order order = orderDao.selectByPrimaryKey(orderProduct.getOrderId());
        if (orderProduct.getId() == null || orderProductDao.selectByPrimaryKey(orderProduct.getId()) == null) {
            return "待修改对象不存在！";
        }
        if (orderProduct.getOrderId() == null || order == null) {
            return "待添加订单不存在！";
        }
        if (orderProduct.getProductId() == null || productDao.selectByPrimaryKey(orderProduct.getProductId()) == null) {
            return "待添加产品不存在！";
        }
        if (orderProduct.getNumber() == null||orderProduct.getNumber()<1||orderProduct.getNumber()>9999) {
            return "产品数量不合法！";
        }

        order.setUpdateDate(new Date());

        if (orderProductDao.updateByPrimaryKeySelective(orderProduct) < 1||orderDao.updateByPrimaryKeySelective(order)<1) {
            return "修改订单产品失败！";
        }
        sqlSession.commit();
        sqlSession.close();

        return null;
    }

    public Object deleteById(Integer id) {
        SqlSession sqlSession = SessionUtils.getSession();
        OrderProductDao orderProductDao = sqlSession.getMapper(OrderProductDao.class);
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);

        OrderProduct orderProduct = orderProductDao.selectByPrimaryKey(id);
        if (id == null || orderProduct==null) {
            return "待删除订单产品不存在！";
        }
        Order order = orderDao.selectByPrimaryKey(orderProduct.getOrderId());
        order.setUpdateDate(new Date());

        if (orderProductDao.deleteByPrimaryKey(id)<1||orderDao.updateByPrimaryKeySelective(order)<1) {
            return "删除订单产品失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }
}
