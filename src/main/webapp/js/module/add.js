layui.use(['form', 'layer'], () => {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 表单submit监听
     */
    form.on("submit(addModule)",(data)=>{
        //弹出加载层
        let index = top.layer.msg("数据提交中，请稍候", {
            icon: 16,
            time: false,
            shade: 0.8
        });
        //发送ajax请求
        $.post(ctx + "/module/add", data.field,
            (result) => {
                if (result.code === 200) {
                    setTimeout(() => {
                        top.layer.close(index);
                        top.layer.msg("操作成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }, 500);
                } else {
                    layer.msg(result.msg, {icon: 5});
                }
            });
        return false;
    })

    /**
     * 关闭弹出层
     */
    $('#closeBtn').click(()=>{
        //得到iframe层的索引
        let index = parent.layer.getFrameIndex(window.name);
        //关闭
        parent.layer.close(index);
    })
});