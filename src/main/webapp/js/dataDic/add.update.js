layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

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

    /**
     * 监听表单submit事件
     */
    form.on('submit(addOrUpdateDataDic)',(data)=>{
        //提交数据时的加载层
        let index = layer.msg("数据提交中，请稍后...", {
            icon: 16,
            time: false,  //不关闭
            shade: 0.8   //遮罩的透明度
        });
        let url = ctx + "/data_dic/add";

        //判断隐藏域中是否存在角色id
        let dataDicId = $('[name="id"]').val();
        if (dataDicId != null && dataDicId != '') {
            //更新操作
            url = ctx + '/data_dic/update';
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
});