package com.xxxx.jd.service;

import com.xxxx.jd.dao.ModuleDao;
import com.xxxx.jd.dao.PermissionDao;
import com.xxxx.jd.model.TreeModel;
import com.xxxx.jd.utils.SessionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ModuleService {

    public List<TreeModel> queryAllModules(String roleId) {
        SqlSession sqlSession = SessionUtils.getSession();
        ModuleDao moduleDao = sqlSession.getMapper(ModuleDao.class);
        PermissionDao permissionDao = sqlSession.getMapper(PermissionDao.class);
        List<TreeModel> treeModelList = moduleDao.queryAllModules();
        //查询指定角色已经授权过的资源列表（查询角色拥有的资源id）
        List<Integer> permissionIds = permissionDao.queryRoleHasModuleIdsByRoleId(Integer.parseInt(roleId));
        //判断改角色是否有资源
        if (permissionIds != null && permissionIds.size() > 0) {
            //循环所有资源
            treeModelList.forEach(treeModel -> {
                //判断已授权的资源id是否包含了该资源的id
                if(permissionIds.contains(treeModel.getId())){
                    treeModel.setChecked(true);
                }
            });
        }
        return treeModelList;
    }
}
