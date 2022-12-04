layui.use(['layer', 'echarts'], function () {
    let $ = layui.jquery,
        echarts = layui.echarts;

    /**
     * 发送ajax请求，获取柱状图所需的数据
     */
    $.ajax({
        type: 'get',
        url:ctx+'/order/countOrderState01',
        dataType:'json',
        success:(data)=>{
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('make'));
            // 指定图表的配置项和数据
            let option = {
                // 标题
                title: {
                    text: '实时订单状态',
                    left: 'center'
                },
                // 提示框
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: data.data1
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: data.data2,
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })

    /**
     * 发送ajax请求，查询饼状图所需的数据
     */
    $.ajax(
        {
            type:'get',
            url:ctx+'/order/countOrderState02',
            dataType: 'json',
            success:(data)=>{
                // 基于准备好的dom，初始化echarts实例
                let myChart = echarts.init(document.getElementById('make02'));

                // 指定图表的配置项和数据
                let option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        left: 'center',
                        top: 'bottom',
                        data: data.data1
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: '半径模式',
                            type: 'pie',
                            radius: [20, 110],
                            center: ['25%', '50%'],
                            roseType: 'radius',
                            label: {
                                show: false
                            },
                            emphasis: {
                                label: {
                                    show: true
                                }
                            },
                            data: data.data2
                        },
                        {
                            name: '面积模式',
                            type: 'pie',
                            radius: [30, 110],
                            center: ['75%', '50%'],
                            roseType: 'area',
                            data: data.data2
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        }
    )
});
