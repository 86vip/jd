layui.use(['table', 'layer'], function () {
    let layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    let tableIns = table.render({
        // 数据表格id属性值
        id: 'dataDicTable'
        // 容器元素的ID属性值
        , elem: '#dataDicList'
        // 容器的高度 full-差值
        , height: 'full-125'
        // 单元格最小的宽度
        , cellMinWidth: 95
        // 访问数据的URL（后台的数据接口）
        , url: ctx + '/data_dic/list'
        // 开启分页
        , page: true
        // 默认每页显示的数量
        , limit: 10
        // 每页页数的可选项
        , limits: [10, 20, 30, 40, 50]
        // 开启头部工具栏
        , toolbar: '#toolbarDemo'
        // 表头
        , cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'dataDicName', title: '字典名', align: 'center'}
            , {field: 'dataDicValue', title: '字典值', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'updateDate', title: '修改时间', align: 'center'}
            , {title: '操作', templet: '#dataDicListBar', fixed: 'right', align: 'center', minWidth: 150}
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
                    dataDicName:$("[name='dataDicName']").val(),
                    dataDicValue:$("[name='dataDicValue']").val(),
                },
                page:{
                    curr:1  //重新从第一页开始
                }
            }
        )
    })

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(dataDic)',(data)=>{
        if (data.event === 'add') {
            openAddOrUpdateDataDicDialog();
        }else if (data.event === 'del') {
            deleteDataDics(data);
        }
    })

    /**
     * 监听行工具栏
     */
    table.on('tool(dataDic)',(data)=>{
        if (data.event === 'edit') {
            //打开添加、修改角色的对话框
            openAddOrUpdateDataDicDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteDataDic(data.data.id);
        }
    })

    /**
     * 单删字典
     * @param id
     */
    function deleteDataDic(id) {
        layer.confirm('真的要删除选中记录吗？',{icon:3,title:'字典管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url: ctx + '/data_dic/delete',
                    data:{
                        ids: id
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
     * 批量删除字典
     * @param data
     */
    function deleteDataDics(data) {
        //获取数据表格中的行数据   table.checkStatus("数据表格id值");
        let checkStatus = table.checkStatus("dataDicTable");
        //获取所有被选中的记录对应的数据
        let datadicData = checkStatus.data;
        //判断用户是否选择了要删除的记录（选中行数不能小于1）
        if (datadicData.length < 1) {
            layer.msg("请选择要删除的记录！",{icon: 5});
            return;
        }
        //询问用户是否确认删除
        layer.confirm("真的要删除选中记录吗？",{icon:3,title:'字典管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);

            let params = '';
            for (let i = 0; i < datadicData.length; i++) {
                if (i < datadicData.length - 1) {
                    params += 'ids='+datadicData[i].id + '&';
                }else{
                    params += 'ids='+datadicData[i].id;
                }
            }
            // console.log(params)
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url:ctx+'/data_dic/delete',
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
     * 打开添加字典页面
     */
    function openAddOrUpdateDataDicDialog(dataDicId) {
        let url = ctx + '/data_dic/toAddOrUpdateDataDicPage';
        let title = '<h3>字典管理 - 添加字典</h3>';
        if (dataDicId != null && dataDicId != '') {
            title='<h3>字典管理 - 更新字典</h3>';
            url += '?dataDicId=' + dataDicId;
        }
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['550px', '260px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }
});