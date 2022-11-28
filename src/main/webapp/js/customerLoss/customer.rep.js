layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
        
    // 暂缓列表展示
    var  tableIns = table.render({
        elem: '#customerRepList',
        url : ctx+'/customer_rep/list?lossId='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerRepListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'measure', title: '暂缓措施',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerRepListBar"}
        ]]
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(customerReps)',(data)=>{
        if (data.event === 'add') {
            openAddOrUpdateCustomerReprDialog();
        }else if (data.event === 'confirm') {
            updateCustomerLossState();
        }
    })

    /**
     * 更新流失客户的流失状态
     */
    function updateCustomerLossState() {
        layer.confirm('确定标记当前用户为确认流失吗？',{icon:3,title:'客户流失管理'},(confirm)=> {
            //关闭确认框
            layer.close(confirm);
            layer.prompt({title: '请输入流失原因', formType: 2}, (text, index) => {
                layer.close(index);
                /**
                 * 发送请求给后台，更新指定流失客户的流失状态
                 *  1、指定流失客户    流失客户id（隐藏域获取）
                 *  2、流失原因      输入框获取
                 */
                $.ajax(
                    {
                        type:'post',
                        url:ctx+'/customer_loss/updateCustomerLossStateById',
                        data: {
                            id: $('[name="id"]').val(),
                            lossReason: text
                        },
                        dataType:'json',
                        success:(result)=>{
                            if (result.code === 200) {
                                layer.msg("确认流失成功！",{icon: 6});
                                layer.closeAll("iframe");
                                parent.location.reload();
                            }else{
                                layer.msg(result.msg,{icon: 5})
                            }
                        }
                    }
                )
            });
        })
    }


    /**
     * 监听行工具栏
     */
    table.on('tool(customerReps)',(data)=>{
        if (data.event === 'edit') {
            openAddOrUpdateCustomerReprDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteCustomerRepr(data.data.id);
        }
    })

    /**
     * 删除暂缓数据
     * @param id
     */
    function deleteCustomerRepr(id) {
        layer.confirm('真的要删除选中记录吗？',{icon:3,title:'暂缓管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url: ctx + '/customer_rep/delete',
                    data:{
                        id: id
                    },
                    success:(result)=>{
                        if (result.code === 200) {
                            layer.msg("删除成功！",{icon: 6});
                            //刷新表格
                            tableIns.reload();
                        }else{
                            layer.msg(result.msg,{icon: 5})
                        }
                    }
                }
            )
        })
    }


    /**
     * 打开添加、更新暂缓数据的页面
     */
    function openAddOrUpdateCustomerReprDialog(id) {
        let title = '<h3>暂缓管理 - 添加暂缓数据</h3>';
        let url = ctx + '/customer_rep/toAddOrUpdateCustomerReprPage?lossId='+$('[name="id"]').val();
        if (id != null && id != '') {
            title = '<h3>暂缓管理 - 更新暂缓数据</h3>';
            url += '&id=' + id;
        }
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['500px', '200px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }

});
