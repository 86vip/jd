package com.xxxx.jd.dao;

import com.xxxx.jd.query.RoleQuery;
import com.xxxx.jd.vo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByParams(RoleQuery roleQuery);

    Role selectByRoleName(String roleName);

    List<Map<String, Object>> queryAllRoles(Integer userId);
}