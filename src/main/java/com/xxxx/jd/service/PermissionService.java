package com.xxxx.jd.service;

import com.xxxx.jd.dao.PermissionDao;
import com.xxxx.jd.utils.SessionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PermissionService {
    public List<String> queryUserHasPermissionByUserId(Integer userId) {
        SqlSession sqlSession = SessionUtils.getSession();
        PermissionDao permissionDao = sqlSession.getMapper(PermissionDao.class);
        List<String> list = permissionDao.queryUserHasPermissionByUserId(userId);
        sqlSession.close();
        return list;
    }
}
