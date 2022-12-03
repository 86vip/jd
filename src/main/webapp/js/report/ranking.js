layui.use(['table','layer',"form"],function(){
    let layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    let tableIns = table.render({
        elem: '#List',
        url: ctx + '/order/queryCustomerConsumeRanking',
        cellMinWidth: 95,
        height: "full-125",
        id: "Table",
        cols: [[
            {field: 'ranking', title: '排名', minWidth: 50, align: "center"},
            {field: 'id', title: '客户编号', minWidth: 50, align: "center"},
            {field: 'name', title: '客户姓名', minWidth: 50, align: "center"},
            {field: 'consume', title: '消费总金额(￥)', minWidth: 50, align: "center"}
        ]]
    });

});
