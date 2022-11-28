package com.xxxx.jd.dao;

import com.xxxx.jd.vo.UserRole;

import java.util.List;

public interface UserRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    Integer countUserRoleByUserId(Integer userId);

    Integer deleteUserRoleByUserId(Integer userId);

    Integer insertBatch(List<UserRole> userRoleList);
}