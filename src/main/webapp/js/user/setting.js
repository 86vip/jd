layui.use(['form', 'layer','formSelects'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        formSelects = layui.formSelects;

    /**
     * 表单submit监听
     */
    form.on('submit(addOrUpdateUser)',(data)=>{
        //提交数据时的等待加载层
        let wait = top.layer.msg("数据提交中，请稍后...", {
            icon: 16,    //图标
            time: false, //不关闭
            shade: 0.8   //设置遮罩的透明度
        });

        //得到所有表单元素的值
        let formData = data.field;

        console.log(formData);
        //请求的地址
        let url = ctx + '/user/add';

        //如果用户id非空，执行更新操作
        if ($('[name="id"]').val()) {
            url = ctx + '/user/update';
        }

        $.post(url,formData,(result)=>{
            //判断执行结果
            if (result.code === 200) {
                //提示成功
                top.layer.msg("操作成功！",{icon: 6});
                //关闭等待加载层
                top.layer.close(wait);
                //关闭弹出层
                layer.closeAll('iframe');
                //刷新窗口，重新加载数据
                parent.location.reload();
            }else{
                //提示失败
                layer.msg(result.msg, {icon: 5});
            }
        });
        //阻止表单提交
        return false;
    })

    /**
     * 加载角色下拉框
     *
     * 配置远程搜索，请求头，请求参数，请求类型
     *
     * formSelects.config(ID,Options,isJson)
     *
     */
  /*  let userId = $('[name="id"]').val();
    formSelects.config('selectId', {
        type: 'post',
        searchUrl: ctx + '/role/queryAllRoles?userId='+userId,
        keyName: 'roleName',
        keyVal: 'id'
    }, true);*/
});