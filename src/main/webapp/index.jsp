<%@ page import="com.xxxx.jd.utils.LoginUserUtil" %>
<%@ page import="com.xxxx.jd.vo.User" %>
<%@ page import="com.xxxx.jd.service.UserService" %><%
    //获取cookie中的用户id
    Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
    //根据用户id查询用户对象
    UserService userService = new UserService();
    User user = userService.selectByPrimaryKey(userId);
    if (user != null) {
        response.sendRedirect("/jd/main");
    } else {
        response.sendRedirect("/jd/index");
    }
%>