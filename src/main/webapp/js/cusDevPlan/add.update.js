layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(()=>{
        //得到iframe层的索引
        let index = parent.layer.getFrameIndex(window.name);
        //关闭
        parent.layer.close(index);
    })

    /**
     * 表单submit监听
     */
    form.on('submit(addOrUpdateCusDevPlan)',(data)=>{
        //提交数据时的等待加载层
        let wait = top.layer.msg("数据提交中，请稍后...", {
            icon: 16,    //图标
            time: false, //不关闭
            shade: 0.8   //设置遮罩的透明度
        });

        //得到所有表单元素的值
        let formData = data.field;

        //请求的地址
        let url = ctx + '/cus_dev_plan/add';

        if ($('[name="id"]').val()) {
            url = ctx + '/cus_dev_plan/update';
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
                //刷新父窗口，重新加载数据
                parent.location.reload();
            }else{
                //提示失败
                layer.msg(result.msg, {icon: 5});
            }
        });
        //阻止表单提交
        return false;
    })
});