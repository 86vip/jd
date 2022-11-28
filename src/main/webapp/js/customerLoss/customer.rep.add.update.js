layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 监听表单submit事件
     */
    form.on('submit(addOrUpdateCustomerRep)',(data)=>{
        //提交数据时的加载层
        let index = layer.msg("数据提交中，请稍后...", {
            icon: 16,
            time: false,  //不关闭
            shade: 0.8   //遮罩的透明度
        });
        let url = ctx + "/customer_rep/add";

        if ($('[name="id"]').val()) {
            //更新操作
            url = ctx + '/customer_rep/update';
        }

        //发送ajax请求
        $.post(url,data.field,(result)=>{
            //判断操作是否成功
            if (result.code === 200) {
                layer.msg("操作成功！", {icon: 6});
                //关闭加载层
                layer.close(index);
                //关闭弹出层
                layer.closeAll("iframe");
                //刷新父窗口
                parent.location.reload();
            }else{
                layer.msg(result.msg,{icon: 5});
            }
        });
        //阻止表单提交
        return false;
    })


    /**
     * 关闭弹出层
     */
    $('#closeBtn').click(()=>{
        //当你在iframe页面关闭自身时
        //得到当前iframe层的索引
        let index = parent.layer.getFrameIndex(window.name);
        //执行关闭
        parent.layer.close(index);
    })

});