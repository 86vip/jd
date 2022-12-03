package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.CustomerDao;
import com.xxxx.jd.query.CustomerQuery;
import com.xxxx.jd.utils.PhoneUtil;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.utils.StringUtils;
import com.xxxx.jd.vo.Customer;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {

    public Map<String, Object> queryByParamsForTable(CustomerQuery customerQuery) {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());
        //得到对应分页对象
        PageInfo<Customer> pageInfo = new PageInfo<>(customerDao.selectByParams(customerQuery));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Customer selectByPrimaryKey(Integer id) {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        Customer customer = customerDao.selectByPrimaryKey(id);
        sqlSession.close();
        return customer;
    }

    public Object addCustomer(Customer customer) {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        if (StringUtils.isBlank(customer.getName())) {
            return "客户姓名不能为空！";
        }
        if (!PhoneUtil.isMobile(customer.getPhone())) {
            return "联系号码格式不正确！";
        }
        if (StringUtils.isBlank(customer.getAddress())) {
            return "联系地址不能为空！";
        }

        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());

        if (customerDao.insertSelective(customer) < 1) {
            return "客户添加失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object updateCustomer(Customer customer) {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        if (customer.getId() == null || customerDao.selectByPrimaryKey(customer.getId()) == null) {
            return "待更新记录不存在！";
        }
        if (StringUtils.isBlank(customer.getName())) {
            return "客户姓名不能为空！";
        }
        if (!PhoneUtil.isMobile(customer.getPhone())) {
            return "联系号码格式不正确！";
        }
        if (StringUtils.isBlank(customer.getAddress())) {
            return "联系地址不能为空！";
        }

        customer.setUpdateDate(new Date());

        if (customerDao.updateByPrimaryKeySelective(customer) < 1) {
            return "更新客户失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object deleteByIds(String[] ids) {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        if (ids == null || ids.length == 0) {
            return "待删除记录不存在！";
        }
        if (customerDao.deleteBatch(ids) != ids.length) {
            return "删除客户失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public List<Map<String, Object>> queryAllCustomers() {
        SqlSession sqlSession = SessionUtils.getSession();
        CustomerDao customerDao = sqlSession.getMapper(CustomerDao.class);
        List<Map<String, Object>> list = customerDao.queryAllCustomers();
        sqlSession.close();
        return list;
    }
}
