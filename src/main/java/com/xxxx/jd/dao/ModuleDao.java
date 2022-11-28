package com.xxxx.jd.dao;

import com.xxxx.jd.model.TreeModel;
import com.xxxx.jd.vo.Module;

import java.util.List;

public interface ModuleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    List<TreeModel> queryAllModules();
}