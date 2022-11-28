layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听表单submit事件
     */
    form.on('submit(addOrUpdateSaleChance)',(data)=>{
        // console.log(data);
        //提交数据时的加载层
        let index = layer.msg("数据提交中，请稍后...", {
            icon: 16,
            time: false,  //不关闭
            shade: 0.8   //遮罩的透明度
        });
        let url = ctx + "/sale_chance/add";

        //通过营销机会id来判断当前需要执行添加操作还是修改操作
        //获取异常域中的id
        let saleChanceId = $('[name="id"]').val();
        if (saleChanceId != null && saleChanceId !== '') {
            url = ctx + '/sale_chance/update';
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

    /**
     * 加载指派人的下拉框
     */
    $.ajax(
        {
            type: 'get',
            url:ctx+'/user/queryAllSales',
            data:{},
            success:(data)=>{
                // console.log(data);
                if (data != null) {
                    //获取隐藏域中设置的指派人id
                    let assignManId = $('[name="man"]').val();
                    for (let i = 0; i < data.length; i++) {
                        let opt = '';
                        if (assignManId == data[i].id) {//assignManId是String类型  data[i].id是number类型 不要用===判断否则不会通过
                            //设置下拉选项并选中
                            opt = "<option value='" + data[i].id + "' selected>" + data[i].uname + "</option>";
                        }else{
                            //设置下拉选项
                            opt = "<option value='" + data[i].id + "'>" + data[i].uname + "</option>";
                        }
                        //将下拉项设置到下拉框中
                        $('#assignMan').append(opt)
                    }
                }
                //重新渲染下拉框的内容
                layui.form.render("select");
            }

        }
    )

});