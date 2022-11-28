package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.RoleDao;
import com.xxxx.jd.dao.UserDao;
import com.xxxx.jd.query.RoleQuery;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.vo.Role;
import com.xxxx.jd.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RoleService {
    public Map<String, Object> queryByParamsForTable(RoleQuery roleQuery) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(roleQuery.getPage(), roleQuery.getLimit());
        //得到对应分页对象
        PageInfo<Role> pageInfo = new PageInfo<>(roleDao.selectByParams(roleQuery));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Object addRole(Role role) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        //参数校验
        if (StringUtils.isBlank(role.getRoleName())) {
            return "角色名称不能为空！";
        }
        //通过角色名称查询角色记录
        Role temp = roleDao.selectByRoleName(role.getRoleName());
        if (temp != null) {
            return "角色名称已存在，请重新输入！";
        }
        //设置默认值
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);

        //执行添加操作，判断受影响的行数
        if (roleDao.insertSelective(role) < 1) {
            return "角色添加失败";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Role selectByPrimaryKey(Integer roleId) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        Role role = roleDao.selectByPrimaryKey(roleId);
        sqlSession.close();
        return role;
    }

    public Object updateRole(Role role) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        /* 1、参数校验 */
        if (role.getId() == null || roleDao.selectByPrimaryKey(role.getId()) == null) {
            return "待更新记录不存在！";
        }
        if (StringUtils.isBlank(role.getRoleName())) {
            return "角色名称不能为空！";
        }
        Role temp = roleDao.selectByRoleName(role.getRoleName());
        if (temp != null && !temp.getId().equals(role.getId())) {
            return "角色名称已存在，请重新输入！";
        }
        /* 2、设置默认值 */
        role.setUpdateDate(new Date());

        /* 3、执行更新操作，判断受影响的行数 */
        if (roleDao.updateByPrimaryKeySelective(role) < 1) {
            return "更新角色失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object deleteById(String roleId) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        if (roleId == null) {
            return "待删除记录不存在！";
        }
        Role role = roleDao.selectByPrimaryKey(Integer.valueOf(roleId));
        if (role == null) {
            return "待删除记录不存在！";
        }
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        if (roleDao.updateByPrimaryKeySelective(role) < 1) {
            return "删除角色记录失败！";
        }
        return null;
    }
}
