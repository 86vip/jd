package com.xxxx.jd.service;

import com.xxxx.jd.dao.UserDao;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.vo.User;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

public class UserService {


    public User queryUserById(int i) {
        UserDao userDao = SessionUtils.getSession().getMapper(UserDao.class);
        return userDao.selectByPrimaryKey(i);
    }
}
