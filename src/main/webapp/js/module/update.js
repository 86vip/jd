layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听表单submit
     */
    form.on("submit(updateModule)",(data)=>{
        //弹出loading
        let index = top.layer.msg("数据提交中，请稍候", {icon: 16, time: false, shade: 0.8});
        // 发送ajax请求
        $.post(ctx + '/module/update', data.field, (result) => {
            if (result.code === 200) {
                setTimeout(() => {
                    top.layer.close(index);
                    top.layer.msg("操作成功！");
                    layer.closeAll("iframe");
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