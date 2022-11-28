package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.ModuleDao;
import com.xxxx.jd.dao.PermissionDao;
import com.xxxx.jd.dao.RoleDao;
import com.xxxx.jd.query.RoleQuery;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.vo.Permission;
import com.xxxx.jd.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

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

    public List<Map<String, Object>> queryAllRoles(String userId) {
        SqlSession sqlSession = SessionUtils.getSession();
        RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
        if ("".equals(userId)) {
            userId = "0";
        }
        List<Map<String, Object>> list = roleDao.queryAllRoles(Integer.parseInt(userId));
        sqlSession.close();
        return list;
    }

    public Object addGrant(String roleId, String[] mIds) {
        SqlSession sqlSession = SessionUtils.getSession();
        PermissionDao permissionDao = sqlSession.getMapper(PermissionDao.class);
        ModuleDao moduleDao = sqlSession.getMapper(ModuleDao.class);
        //1、通过角色id查询对应的权限记录
        Integer count = permissionDao.countPermissionByRoleId(roleId);
        //2、如果权限记录存在，则删除全部权限
        if (count > 0&&permissionDao.deletePermissionByRoleId(roleId) != count) {
                return "角色授权失败！";
        }
        //3、如果有选择权限，则添加权限记录
        if (mIds!=null&&mIds.length > 0) {
            //定义permission集合
            List<Permission> permissionList = new ArrayList<>();
            //遍历资源id数组
            for (String mId : mIds) {
                if (mId == "") {
                    mId = "0";
                }
                Permission permission = new Permission();
                permission.setRoleId(Integer.valueOf(roleId));
                permission.setModuleId(Integer.valueOf(mId));
                permission.setAclValue(moduleDao.selectByPrimaryKey(Integer.parseInt(mId)).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());

                permissionList.add(permission);
            }
            //执行批量添加操作，判断受影响的行数
            if (permissionDao.insertBatch(permissionList) != permissionList.size()) {
                return "角色授权失败！";
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }
}
