$(()=>{
    //加载树形结构
    loadModuleData();
})

//定义树型结构对象
let zTreeObj;

function loadModuleData() {
    //zTree的参数配置
    let setting={
        //使用复选框
        check:{
            enable: true
        },
        //使用简单的json数据
        data:{
            simpleData:{
                enable: true
            }
        },
        //绑定回调函数
        callback:{
            //onCheck函数：当checkbox被选中或取消选中时触发的函数
            onCheck:zTreeOnCheck,
        }
    }
    //通过ajax查询资源列表
    $.ajax(
        {
            type: 'get',
            url: ctx + '/module/queryAllModules',
            data:{
                roleId: $('[name="roleId"]').val()
            },
            dataType: 'json',
            success: (data) => {
                //ajax成功查询到的数据
                //加载渲染zTree树插件
                zTreeObj = $.fn.zTree.init($('#test1'), setting, data);
            }
        }
    );
}

/**
 * 当checkbox被选中或取消选中时触发的函数
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnCheck(event, treeId, treeNode) {
    //getCheckedNodes(checked): 获取所有被勾选的节点集合
    //checked==true表示获取勾选的节点，checked==false表示获取未勾选的节点
    let nodes = zTreeObj.getCheckedNodes(true);
    //判断是否有选中的节点
    if (nodes.length > 0) {
        //遍历选中的节点
        let mIds = '';
        for (let i = 0; i < nodes.length; i++) {
            if (i !== nodes.length - 1) {
                mIds += 'mIds=' + nodes[i].id+'&';
            }else{
                mIds += 'mIds=' + nodes[i].id;
            }
        }
        //获取需要授权的角色id
        let roleId = $('[name="roleId"]').val();

        //发送ajax请求，执行角色的授权操作
        $.ajax(
            {
                type:'post',
                url: ctx + '/role/addGrant',
                data: mIds + '&roleId=' + roleId,   //请求体
                dataType: 'json',
                success:(data)=>{
                    console.log(data)
                }
            }
        )
    }
}
