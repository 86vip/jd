layui.use(['table','layer'],function() {
    let layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    let tableIns = table.render({
        id: 'roleTable'
        // 容器元素的ID属性值
        , elem: '#roleList'
        // 容器的高度 full-差值
        , height: 'full-125'
        // 单元格最小的宽度
        , cellMinWidth: 95
        // 访问数据的URL（后台的数据接口）
        , url: ctx + '/role/list'
        // 开启分页
        , page: true
        // 默认每页显示的数量
        , limit: 10
        // 每页页数的可选项
        , limits: [10, 20, 30, 40, 50]
        // 开启头部工具栏
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
        // 表头
        , cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'roleName', title: '角色名称', align: 'center'}
            , {field: 'roleRemark', title: '角色备注', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center',templet: "<div>{{layui.util.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'updateDate', title: '修改时间', align: 'center',templet: "<div>{{layui.util.toDateString(d.updateDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {title: '操作', templet: '#roleListBar', fixed: 'right', align: 'center', minWidth: 150}
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
                    roleName:$("[name='roleName']").val(),  //角色名称
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
    table.on('toolbar(roles)',(data)=>{
        if (data.event === 'add') {     //添加操作
            //打开添加、修改角色的对话框
            openAddOrUpdateRoleDialog();
        }else if (data.event === 'grant') {     //授权操作
            //获取数据表格中的行数据   table.checkStatus("数据表格id值");
            let checkStatus = table.checkStatus("roleTable");
            //打开授权的对话框
            openAddGrantDialog(checkStatus.data);
        }
    })

    /**
     * 打开授权页面
     * @param data
     */
    function openAddGrantDialog(data) {
        //判断是否选择了角色记录
        if (data.length === 0) {
            layer.msg("请选择要授权的角色！", {icon: 5});
            return;
        }
        if (data.length > 1) {
            layer.msg("暂不支持多角色授权！", {icon: 5});
            return;
        }
        let url = ctx + '/module/toAddGrantPage?roleId=' + data[0].id;
        let title = '<h3>角色管理 - 角色授权</h3>';
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['600px', '400px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }

    /**
     * 监听行工具栏
     */
    table.on('tool(roles)',(data)=>{
        if (data.event === 'edit') {
            //打开添加、修改角色的对话框
            openAddOrUpdateRoleDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteRole(data.data.id);
        }
    })

    /**
     * 删除角色
     * @param id
     */
    function deleteRole(id) {
        layer.confirm('真的要删除选中记录吗？',{icon:3,title:'角色管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url: ctx + '/role/delete',
                    data:{
                        roleId: id
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
     * 打开添加、修改角色的对话框
     */
    function openAddOrUpdateRoleDialog(roleId) {
        let title = '<h3>角色管理 - 添加角色</h3>';
        let url = ctx + '/role/toAddOrUpdateRolePage';

        //如果roleId不为空，则表示更新角色
        if (roleId != null && roleId != '') {
            title='<h3>角色管理 - 更新角色</h3>';
            url += '?roleId=' + roleId;
        }
        layui.layer.open(
            {
                //类型 iframe层
                type:2,
                //标题
                title: title,
                //宽高
                area: ['480px', '260px'],
                //url地址
                content: url,
                //可以最大化与最小化
                maxmin:true
            }
        );
    }

});