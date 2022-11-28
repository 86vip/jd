layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单submit提交
     *  form.on('submit(按钮的lay-filter属性值）'...
     */
    form.on('submit(login)', function (data) {
        /*发送ajax请求，传递用户姓名与密码，执行用户登录操作*/
        $.ajax({
                type: "post",
                url: ctx + '/user/login',
                data: {
                    userName: data.field.username,
                    userPwd: data.field.password,
                },
                success: function (result) {
                    // result = JSON.parse(result);
                    // console.log(typeof result)
                    // console.log(result)
                    if (result.code === 200) {
                        //登录成功
                        /**
                         * 设置用户是登录状态
                         *  1.利用session会话
                         *      保存用户信息，如果会话存在，则用户是登录状态；否则是非登录状态
                         *      缺点：服务器关闭，会话则会失效
                         *  2.利用cookie
                         *      保存用户信息，cookie未失效，则用户是登录状态
                         */
                        layer.msg("登录成功!", function () {
                            //将用户信息设置到cookie中
                            //判断用户是否勾选记住密码，如果选中则设置cookie值7天有效
                            if ($("#rememberMe").prop('checked')) {//(Expires:2022-11-07T09:41:50.000Z)
                                $.cookie("userIdStr", result.result.userIdStr, {expires: 7});
                                $.cookie("userName", result.result.userName, {expires: 7});
                                $.cookie("trueName", result.result.trueName, {expires: 7});
                            } else {
                                //默认情况下关闭浏览器cookie即失效   (Expires:Session)
                                $.cookie("userIdStr", result.result.userIdStr);
                                $.cookie("userName", result.result.userName);
                                $.cookie("trueName", result.result.trueName);
                            }
                            //登录成功后，跳转到首页
                            window.location.href = ctx + '/main';
                        });
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            });
        return false;
    });
});