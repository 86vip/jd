package com.xxxx.jd.dao;

import com.xxxx.jd.vo.Permission;

import java.util.List;

public interface PermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Integer> queryRoleHasModuleIdsByRoleId(Integer roleId);

    Integer countPermissionByRoleId(String roleId);

    Integer deletePermissionByRoleId(String roleId);

    Integer insertBatch(List<Permission> permissionList);
}