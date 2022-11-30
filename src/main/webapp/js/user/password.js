layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单的submit监听
     *      form.on('submit(按钮元素的lay-filter属性值)',function(){
     *
     *      });
     */
    form.on('submit(saveBtn)', function (data) {
        //发送ajax请求
        $.ajax(
            {
                type: "post",
                url: ctx + "/user/updatePwd",
                data:{
                    oldPwd:data.field.old_password,
                    newPwd:data.field.new_password,
                    repeatPwd:data.field.again_password
                },
                success: function (result) {
                    //判断是否修改成功
                    if (result.code === 200) {
                        //成功修改密码后，清空cookie数据
                        layer.msg("用户密码修改成功，系统将在3秒后自动退出...", function () {
                            //清空cookie
                            $.removeCookie("userIdStr",{domain:"localhost",path:"/jd"});
                            $.removeCookie("userName",{domain:"localhost",path:"/jd"});
                            $.removeCookie("trueName", {domain: "localhost", path: "/jd"});
                            setTimeout(function () {
                                //父窗口跳转到登录页面
                                window.parent.location.href = ctx + '/index';
                            }, 2000);
                        });
                    }else{
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            }
        )
    });
});