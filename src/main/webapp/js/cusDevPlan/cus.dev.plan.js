layui.use(['table','layer'],function(){
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
        // 访问数据的url（后台的数据接口）    设置flag参数，表示查询的是客户开发计划数据
        , url: ctx + '/sale_chance/list?flag=1'
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
            , {field:'updateDate',title:'更新时间',align:'center'}
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
            , {title: '操作', templet: '#op', fixed: 'right', align: 'center', minWidth: 150}
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
                    devResult:$('#devResult').val()
                },
                page:{
                    curr:1  //重新从第一页开始
                }
            }
        )
    })

    /**
     * 行工具栏监听
     */
    table.on('tool(saleChances)',(data)=>{
        if (data.event === 'dev') {         //开发
            openCusDevPlanDialog('计划项数据开发', data.data.id);
        }else if (data.event === 'info') {  //详情
            openCusDevPlanDialog('计划项数据维护', data.data.id);
        }
    })

    /**
     * 打开计划项开发或详情页面
     * @param title
     * @param id
     */
    function openCusDevPlanDialog(title, id) {
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['750px', '550px'],
                //url地址
                content: ctx+'/cus_dev_plan/toCusDevPlanPage?id='+id,
                //可以最大化与最小化
                maxmin:true
            }
        )
    }

});
