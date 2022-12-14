layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    /**
     * 加载数据表格
     */
    let tableIns = table.render({
        id: 'userTable'
        // 容器元素的ID属性值
        , elem: '#userList'
        // 容器的高度 full-差值
        , height: 'full-125'
        // 单元格最小的宽度
        , cellMinWidth: 95
        // 访问数据的URL（后台的数据接口）
        , url: ctx + '/user/list'
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
            , {field: 'userName', title: '用户名称', align: 'center'}
            , {field: 'trueName', title: '真实姓名', align: 'center'}
            , {field: 'email', title: '用户邮箱', align: 'center'}
            , {field: 'phone', title: '用户号码', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center',templet: "<div>{{layui.util.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'updateDate', title: '修改时间', align: 'center',templet: "<div>{{layui.util.toDateString(d.updateDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {title: '操作', templet: '#userListBar', fixed: 'right', align: 'center', minWidth: 150}
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
                    userName:$("[name='userName']").val(),  //用户名
                    email:$("[name='email']").val(),        //邮箱
                    phone:$("[name='phone']").val()         //手机号
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
    table.on('toolbar(users)',(data)=>{
        if (data.event === 'add') {
            //打开添加、修改用户的对话框
            openAddOrUpdateUserDialog();
        }else if (data.event === 'del') {
            deleteUsers(data);
        }
    })

    /**
     * 删除多条用户记录
     * @param data
     */
    function deleteUsers(data) {
        //获取数据表格中的行数据   table.checkStatus("数据表格id值");
        let checkStatus = table.checkStatus("userTable");
        // console.log(checkStatus);
        //获取所有被选中的记录对应的数据
        let userData = checkStatus.data;

        //判断用户是否选择了要删除的记录（选中行数不能小于1）
        if (userData.length < 1) {
            layer.msg("请选择要删除的记录！",{icon: 5});
            return;
        }
        //询问用户是否确认删除
        layer.confirm("真的要删除选中记录吗？",{icon:3,title:'用户管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);

            let params = '';
            for (let i = 0; i < userData.length; i++) {
                if (i < userData.length - 1) {
                    params += 'ids='+userData[i].id + '&';
                }else{
                    params += 'ids='+userData[i].id;
                }
            }
            // console.log(params)
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url:ctx+'/user/delete',
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
    table.on('tool(users)',(data)=>{
        if (data.event === 'edit') {
            //打开添加、修改用户的对话框
            openAddOrUpdateUserDialog(data.data.id);
        }else if (data.event === 'del') {
            deleteUser(data.data.id);
        }
    })

    /**
     * 删除单条用户记录
     * @param id
     */
    function deleteUser(id) {
        layer.confirm('真的要删除选中记录吗？',{icon:3,title:'用户管理'},(confirm)=>{
            //关闭确认框
            layer.close(confirm);
            //发送ajax请求
            $.ajax(
                {
                    type: 'post',
                    url: ctx + '/user/delete',
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
     * 打开添加、修改用户的对话框
     */
    function openAddOrUpdateUserDialog(id) {
        let title = '<h3>用户管理 - 添加用户</h3>';
        let url = ctx + '/user/toAddOrUpdateUserPage';
        if (id != null && id != '') {
            title = '<h3>用户管理 - 更新用户</h3>';
            url += '?id=' + id;
        }
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
});