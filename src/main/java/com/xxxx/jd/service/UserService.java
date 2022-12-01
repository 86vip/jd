package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.UserDao;
import com.xxxx.jd.dao.UserRoleDao;
import com.xxxx.jd.model.UserModel;
import com.xxxx.jd.query.UserQuery;
import com.xxxx.jd.utils.*;
import com.xxxx.jd.vo.User;
import com.xxxx.jd.vo.UserRole;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Stream;

public class UserService {

    public User selectByPrimaryKey(Integer userId) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.selectByPrimaryKey(userId);
        sqlSession.close();
        return user;
    }


    public Object userLogin(String userName, String userPwd) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //1、参数判断，判断用户姓名、用户密码是否为空
        if (StringUtils.isBlank(userName)) {
            return "用户姓名不能为空！";
        }
        if (StringUtils.isBlank(userPwd)) {
            return "用户密码不能为空！";
        }

        //2、调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user = userDao.queryUserByName(userName);

        //3、判断用户对象是否为空
        if (user == null) {
            return "用户名不存在！";
        }

        //4、判断密码是否正确，比较客户端传递的用户密码与数据库中查询的用户对象中的用户密码
        if (!checkUserPwd(userPwd, user.getUserPwd())) {
            return "用户密码不正确！";
        }
        sqlSession.close();
        //返回构建的用户信息对象
        return buildUserInfo(user);
    }

    /**
     * 构建需要返回给客户端的用户信息对象
     *
     * @param user
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        //设置加密的用户id
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 密码判断
     * 先将客户端传递的密码加密，再与数据库中查询到的密码作比较
     */
    private boolean checkUserPwd(String userPwd, String pwd) {
        //将客户端传递的密码加密
        userPwd = Md5Util.encode(userPwd);
        //比对加密后的密码  ??? !=就报错 ???
        return Objects.equals(userPwd, pwd);
    }

    public Object updateUser(User user) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //参数校验
        if (user.getId() == null || userDao.selectByPrimaryKey(user.getId()) == null) {
            return "待更新记录不存在！";
        }
        String check = checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), user.getId());
        if (check != null) {
            return check;
        }
        //设置参数的默认值
        user.setUpdateDate(new Date());

        //执行更新操作，判断受影响的行数
        if (userDao.updateByPrimaryKeySelective(user) != 1) {
            return "用户更新失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        /* 用户角色关联 */
        check = relationUserRole(user.getId(), user.getRoleIds());
        return check;
    }

    /**
     * 参数校验
     * 用户名userName     非空且不能重复
     * 邮箱email         非空
     * 手机号phone        非空且格式正确
     *
     * @param userName
     * @param email
     * @param phone
     */
    private String checkUserParams(String userName, String email, String phone, Integer userId) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //用户名
        if (StringUtils.isBlank(userName))
            return "用户名不能为空！";

        //添加操作：根据用户名查询用户表，如果查到了说明该用户名重复
        //更新操作：根据用户名查询用户，根据查询到的用户id与当前id比对，如果不是同一个id不可修改，更新失败
        User temp = userDao.queryUserByName(userName);
        sqlSession.close();
        if (temp != null && !(temp.getId().equals(userId))) {
            return "用户名已存在，请重新输入！";
        }

        //邮箱
        if (StringUtils.isBlank(email)) {
            return "用户邮箱不能为空！";
        }

        //手机号
        if (!PhoneUtil.isMobile(phone)) {
            return "手机号格式不正确！";
        }
        sqlSession.close();
        return null;
    }

    public Object updatePassWord(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //通过用户id查询用户记录，返回用户对象
        User user = userDao.selectByPrimaryKey(userId);

        //判断用户对象是否存在
        if (null == user) {
            return "待更新记录不存在";
        }

        //参数校验
        String check = checkPasswordParams(user, oldPwd, newPwd, repeatPwd);
        if (check != null) {
            return check;
        }

        //设置用户的新密码
        user.setUserPwd(Md5Util.encode(newPwd));

        //执行更新操作，判断受影响的行数
        if (userDao.updateByPrimaryKeySelective(user) < 1) {
            return "修改密码失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }


    private String checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        //判断原始密码是否为空
        if (StringUtils.isBlank(oldPwd)) {
            return "原始密码不能为空！";
        }
        //判断原始密码是否正确（查询的用户对象中的用户密码是否与原始密码一致）
        if (!user.getUserPwd().equals(Md5Util.encode(oldPwd))) {
            return "原始密码不正确！";
        }
        //判断新密码是否为空
        if (StringUtils.isBlank(newPwd)) {
            return "新密码不能为空！";
        }
        //判断新密码是否与原始密码一致（不允许新密码与原始密码相同）
        if (oldPwd.equals(newPwd)) {
            return "新密码不能与原始密码相同！";
        }
        //判断确认密码是否为空
        if (StringUtils.isBlank(repeatPwd)) {
            return "确认密码不能为空！";
        }
        //判断确认密码是否与新密码一致
        if (!newPwd.equals(repeatPwd)) {
            return "确认密码与新密码不一致";
        }
        return null;
    }


    public Map<String, Object> queryByParamsForTable(UserQuery userQuery) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(userQuery.getPage(), userQuery.getLimit());
        //得到对应分页对象
        PageInfo<User> pageInfo = new PageInfo<>(userDao.selectByParams(userQuery));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Object addUser(User user) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        /* 1、参数校验 */
        String check = checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), null);
        if (check != null) {
            return check;
        }

        /* 2、设置参数的默认值 */
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));

        /* 3、执行添加操作，判断受影响的行数 */
        if (userDao.insertSelective(user) != 1) {
            return "用户添加失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        /* 用户角色关联 */
        check = relationUserRole(user.getId(), user.getRoleIds());
        return check;
    }

    public Object deleteByIds(String[] ids) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        UserRoleDao userRoleDao = sqlSession.getMapper(UserRoleDao.class);
        if (ids == null || ids.length == 0) {
            return "待删除记录不存在！";
        }
        //Stream.of(ids).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new)
        if (userDao.deleteBatch(ids) != ids.length) {
            return "用户删除失败！";
        }

        //遍历用户id数组
        for (String userId : ids) {
            //通过用户id查询对应的用户角色记录数量
            Integer count = userRoleDao.countUserRoleByUserId(Integer.parseInt(userId));
            //判断是否有用户角色记录
            if (count > 0) {
                //如果用户角色记录存在，则删除用户对应的角色记录
                if (userRoleDao.deleteUserRoleByUserId(Integer.parseInt(userId)) != count) {
                    return "用户删除失败！";
                }
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    /**
     * 用户角色关联
     * 添加、更新用户操作：
     * 判断用户对应的角色记录是否存在，先将原来用户所有的角色都删除，然后在根据roleids重新加入
     * 删除用户操作：
     * 删除指定用户在t_user_role表中的相关记录
     *
     * @param userId  用户id
     * @param roleIds 角色id
     */
    private String relationUserRole(Integer userId, String roleIds) {
        SqlSession sqlSession = SessionUtils.getSession();
        UserRoleDao userRoleDao = sqlSession.getMapper(UserRoleDao.class);
        //通过用户id查询用户角色记录数
        Integer count = userRoleDao.countUserRoleByUserId(userId);

        //判断是否有用户角色记录
        if (count > 0 && userRoleDao.deleteUserRoleByUserId(userId) != count) {
            //如果用户角色记录存在，则删除用户对应的角色记录
            return "用户角色分配失败！";
        }

        //判断角色id是否存在，存在则给用户添加相对应的用户角色记录
        if (StringUtils.isNotBlank(roleIds)) {
            //将用户角色记录设置到集合中，待会批量添加
            List<UserRole> userRoleList = new ArrayList<>();
            //将角色id字符串转换成数组
            String[] roleIdsArray = roleIds.split(",");
            //遍历角色id数组，设置每个用户角色对象并放到数组中
            for (String roleId : roleIdsArray) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                //放到集合中
                userRoleList.add(userRole);
            }
            //批量添加用户角色记录
            if (userRoleDao.insertBatch(userRoleList) != userRoleList.size()) {
                return "用户角色分配失败！";
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

}
