layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    let tableIns=table.render({
        // 数据表格的id
        id: 'Table'
        // 容器元素的ID属性值
        ,elem: '#List'
        // 容器的高度 full-差值
        , height: 'full-125'
        // 单元格最小的宽度
        , cellMinWidth: 95
        // 访问数据的url（后台的数据接口）
        , url: ctx + '/orderProduct/list?orderId='+$('[name="id"]').val()
        //开启分页
        , page: true
        // 默认每页显示的数量
        , limit: 10
        // 每页页数的可选项
        , limits: [10, 20, 30, 40, 50]
        // 设置头部工具栏
        , toolbar: '#toolbarDemo'
        // 解析分页数据
        , parseData:function(res){
            let result;
            if (this.page.curr) {
                result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
            } else {
                result = res.data.slice(0, this.limit);
            }
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.count, //解析数据长度
                "data": result //解析数据列表
            }
        }
        //表头
        , cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列标题
            // sort：是否允许排序
            // fixed：固定列的位置
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'productName', title: '产品名', align: 'center'}
            , {field: 'number', title: '数量', align: 'center'}
            , {field: 'sumPrice', title: '产品总价', align: 'center'}
            , {title: '操作', templet: '#toolDemo', fixed: 'right', align: 'center', minWidth: 50}
        ]]
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(orderProduct)',(data)=>{
        if(data.event==='add'){
            openAddOrUpdateOrderProductDialog();
        }else if (data.event === 'delivery') {
            updateOrderState(1);
        }else if (data.event === 'finish') {
            updateOrderState(2);
        }
    })


    function updateOrderState(state) {
        //弹出确认框，询问用户
        layer.confirm("您确认要执行该操作吗？",{icon:3,title:'订单管理'},(yes)=>{
                let oid = $('[name="id"]').val();
                //发送ajax请求
                $.post(ctx+'/order/updateOrderState',{id:oid,state:state},(result)=>{
                    if (result.code === 200) {
                        layer.msg("更新成功！", {icon: 6});
                        //关闭窗口
                        layer.closeAll('iframe');
                        //刷新父页面
                        parent.location.reload();
                    }else{
                        layer.msg(result.msg, {icon: 5});
                    }
                })
            }
        )

    }

    /**
     * 监听行工具栏
     */
    table.on('tool(orderProduct)',(data)=>{
        if (data.event === 'edit') {
            openAddOrUpdateOrderProductDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteOrderProduct(data.data.id);
        }
    })



    function deleteOrderProduct(id) {
        //弹出确认框，询问用户是否删除
        layer.confirm("真的要删除该项产品吗？",{icon:3,title:'订单管理'},(yes)=>{
            //发送ajax请求
            $.post(
                ctx+'/orderProduct/delete',{id: id},(result)=>{
                    if (result.code === 200) {
                        layer.msg("删除成功！", {icon: 6});
                        //刷新数据表格
                        tableIns.reload();
                        parent.location.reload();
                    }else{
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            )
        })
    }

    function openAddOrUpdateOrderProductDialog(id) {
        let title = '<h3>订单管理 - 添加产品</h3>';
        let url = ctx + '/orderProduct/toAddOrUpdateOrderProductPage?orderId='+$('[name="id"]').val();

        //判断传入的id是否为空，（空表示添加操作，非空表示更新操作）
        if (id != null && id != '') {
            //更新操作
            title = '<h3>订单管理 - 更改产品</h3>';
            url += '&orderProductId=' + id;
        }

        //iframe层
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['500px', '280px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }
});
