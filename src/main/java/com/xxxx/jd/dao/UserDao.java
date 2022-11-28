package com.xxxx.jd.dao;

import com.xxxx.jd.query.UserQuery;
import com.xxxx.jd.vo.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryUserByName(String userName);

    List<User> selectByParams(UserQuery userQuery);

    int deleteBatch(String[] ids);

}