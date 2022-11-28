layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    let tableIns=table.render({
        // 数据表格的id
        id: 'saleChanceTable'
        // 容器元素的ID属性值
        ,elem: '#saleChanceList'
        // 容器的高度 full-差值
        , height: 'full-125'
        // 单元格最小的宽度
        , cellMinWidth: 95
        // 访问数据的url（后台的数据接口）
        , url: ctx + '/sale_chance/list'
        //开启分页
        , page: true
        // 默认每页显示的数量
        , limit: 10
        // 每页页数的可选项
        , limits: [10, 20, 30, 40, 50]
        // 设置头部工具栏
        , toolbar: '#toolbarDemo'
        //表头
        , cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列标题
            // sort：是否允许排序
            // fixed：固定列的位置
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'chanceSource', title: '机会来源', align: 'center'}
            , {field: 'customerName', title: '客户名称', align: 'center'}
            , {field: 'cgjl', title: '成功几率', align: 'center'}
            , {field: 'overview', title: '概要', align: 'center'}
            , {field: 'linkMan', title: '联系人', align: 'center'}
            , {field: 'linkPhone', title: '联系号码', align: 'center'}
            , {field: 'description', title: '描述', align: 'center'}
            , {field: 'createMan', title: '创建人', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'uname', title: '分配人', align: 'center'}
            , {field: 'assignTime', title: '分配时间', align: 'center'}
            , {field:'updateDate',title:'更新时间',align:'center'}
            , {field: 'state', title: '分配状态', align: 'center', templet: (d)=>{
                    if (d.state === 0) {
                        return "<div style='color: yellow'>未分配</div>";
                    }else if (d.state === 1) {
                        return "<div style='color: green'>已分配</div>"
                    }else{
                        return "<div style='color: red'>未知</div>"
                    }
                }}
            , {field: 'devResult', title: '开发状态', align: 'center',templet:(d)=> {
                    if (d.devResult === 0) {
                        return "<div style='color: yellow'>未开发</div>"
                    }else if (d.devResult===1){
                        return "<div style='color: orange'>开发中</div>"
                    }else if (d.devResult === 2) {
                        return "<div style='color: green'>开发成功</div>"
                    }else if (d.devResult === 3) {
                        return "<div style='color: red'>开发失败</div>"
                    }else{
                        return "<div style='color: blue'>未知</div>"
                    }
                }}
            , {title: '操作', templet: '#saleChanceListBar', fixed: 'right', align: 'center', minWidth: 150}
        ]]
    });

        /**
         * 搜索按钮的点击事件
         */
        $(".search_btn").click(()=>{
            /**
             * 表格重载
             * 多条件查询
             */
            tableIns.reload(
                {
                    //设置需要传递给后端的参数
                    where:{
                        //通过文本框、下拉框的值设置传递的参数
                        customerName:$("[name='customerName']").val(),
                        createMan:$("[name='createMan']").val(),
                        state:$('#state').val()
                    },
                    page:{
                        curr:1  //重新从第一页开始
                    }
                }
            )
        })

    /**
     * 监听头部工具栏
     *  格式：
     *      table.on('toolbar(数据表格的lay-filter属性值)',...
     */
    table.on('toolbar(saleChances)',(data)=>{
        //判断对应的事件类型
        if (data.event === 'add') {
            //添加操作
            openSaleChanceDialog();
        }else if (data.event === 'del') {
            //删除操作
            deleteSaleChance(data);
        }
    })

    /**
     * 打开添加/修改营销机会数据的窗口
     *      如果营销机会id为空，则为添加操作；否则为更新修改操作
     */
    function openSaleChanceDialog(saleChanceId) {
        let title='<h3>营销机会管理 - 添加营销机会</h3>';
        let url = ctx + '/sale_chance/toSaleChancePage';
        //判断saleChanceId是否为空
        if (saleChanceId != null && saleChanceId !== '') {
            title='<h3>营销机会管理 - 更新营销机会</h3>';
            url += '?saleChanceId=' + saleChanceId;
        }
        //iframe层
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['500px', '620px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }

    /**
     * 删除营销机会（删除多条记录）
     * @param data
     */
    function deleteSaleChance(data) {
        //获取数据表格中的行数据   table.checkStatus("数据表格id值");
        let checkStatus = table.checkStatus("saleChanceTable");
        console.log(checkStatus);
        //获取所有被选中的记录对应的数据
        let saleChanceData = checkStatus.data;

        //判断用户是否选择了要删除的记录（选中行数不能小于1）
        if (saleChanceData.length < 1) {
            layer.msg("请选择要删除的记录！",{icon: 5});
            return;
        }
        //询问用户是否确认删除
        layer.confirm("真的要删除选中记录吗？",{icon:3,title:'营销机会管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);

            let params = '';
            for (let i = 0; i < saleChanceData.length; i++) {
                if (i < saleChanceData.length - 1) {
                    params += 'ids='+saleChanceData[i].id + '&';
                }else{
                    params += 'ids='+saleChanceData[i].id;
                }
            }
            // console.log(params)
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url:ctx+'/sale_chance/delete',
                    data:params,
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
     * 监听行工具栏
     */
    table.on('tool(saleChances)', (data) => {
        // console.log(data)
        //判断类型
        if (data.event === 'edit') {    //编辑操作
            //得到营销机会的id
            let saleChanceId = data.data.id;
            //打开修改营销机会数据的窗口
            openSaleChanceDialog(saleChanceId);
        }else if (data.event === 'del') {   //删除操作
            //弹出确认框，询问用户是否确认删除
            layer.confirm('真的要删除选中记录吗？',{icon:3,title:'营销机会管理'},(confirm)=>{
                //关闭确认框
                layer.close(confirm);
                //发送ajax请求
                $.ajax(
                    {
                        type: 'post',
                        url: ctx + '/sale_chance/delete',
                        data:{
                            ids: data.data.id
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
    });
});
