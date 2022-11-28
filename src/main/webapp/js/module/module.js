layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
    treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#munu-table',
        url: ctx+'/module/list',
        toolbar: "#toolbarDemo",
        treeDefaultClose:true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if(d.grade==1){
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 180, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(munu-table)',(data)=>{
        if (data.event === 'expand') {
            //全部展开
            treeTable.expandAll("#munu-table");
        }else if(data.event==='fold'){
            //全部折叠
            treeTable.foldAll("#munu-table");
        }else if (data.event === 'add') {
            openAddModuleDialog(0, -1);
        }
    })

    /**
     * 监听行工具栏
     */
    table.on('tool(munu-table)',(data)=>{
        if (data.event === 'add') {
            //添加子项
            //判断当前的层级（如果是第三层级，就不能添加）
            if (data.data.grade == 2) {
                layer.msg("暂不支持添加四级菜单！", {icon: 5});
                return;
            }
            //一级、二级菜单 grade=当前层级+1  parentId=当前资源id
            openAddModuleDialog(data.data.grade + 1, data.data.id);
        }else if (data.event === 'edit') {
            openUpdateModuleDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteModule(data.data.id);
        }
    })

    /**
     * 打开添加资源的对话框
     * @param number
     * @param number2
     */
    function openAddModuleDialog(grade, parentId) {
        let title = "<h3>资源管理 - 添加资源</h3>";
        let url = ctx + "/module/toAddModulePage?grade=" + grade + '&parentId=' + parentId;

        layui.layer.open(
            {
                type: 2,
                title: title,
                content:url,
                area: ['700px', '450px'],
                maxmin:true
            }
        )
    }

    /**
     * 删除资源
     * @param id
     */
    function deleteModule(id) {
        layer.confirm('您确定要删除该记录吗？',{icon:3,title:'资源管理'},(yes)=>{
            $.post(ctx+'/module/delete',{id:id},(result)=>{
                if (result.code === 200) {
                    layer.msg("删除成功！", {icon: 6});
                    window.location.reload();
                }else{
                    layer.msg(result.msg, {icon: 5});
                }
            })
        })
    }

    /**
     * 打开更新资源的对话框
     * @param id
     */
    function openUpdateModuleDialog(id) {
        let title = '<h3>资源管理 - 更新资源</h3>';
        let url = ctx + "/module/toUpdateModulePage?id=" + id;
        layui.layer.open(
            {
                type: 2,
                title: title,
                content:url,
                area: ['700px', '450px'],
                maxmin:true
            }
        )
    }

});