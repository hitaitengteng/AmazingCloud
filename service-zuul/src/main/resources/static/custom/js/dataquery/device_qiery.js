layui.use(['jquery','layer','element'],function() {
    window.jQuery = window.$ = layui.jquery;
    window.layer = layui.layer;
});
//设置日期时间输入框的默认值为当前时间
$(function()
{
    var curr_time=new Date();
    var strDate=curr_time.getFullYear()+"-";
    strDate +=curr_time.getMonth()+1+"-";
    strDate +=curr_time.getDate()+"-";
    strDate +=" "+curr_time.getHours()+":";
    strDate +=curr_time.getMinutes()+":";
    strDate +=curr_time.getSeconds();
    $("#startTime").datetimebox("setValue",strDate);
    $("#endTime").datetimebox("setValue",strDate);
});


/*$(function() {
    $('#tt').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
});*/
//类型选择下拉框
$('#contentIndex').combobox({
    multiple: false,
    editable: false,
    onClick: function (contentIndex) {
        console.log(contentIndex.value);
        var section = $('#section').combobox('getValue');
        fillContentTOCombobox(section,contentIndex.value);
    }
});

//工段下拉选择框
$('#section').combobox({
    multiple: false,
    editable: false,
    required:true,
    onClick: function (section) {
        console.log(section.value);
        var contentIndex = $('#contentIndex').combobox('getValue');
        fillContentTOCombobox(section.value,contentIndex);
    },
    onLoadSuccess:function(){
        $('#section').combobox('setValue','');
    }
});

//控制项目参数
$('#content').combobox({
    multiple: false,
    editable: false,
    required:true,
});

/**
 * 填充控制项目下拉选择框的值
 * @param sectionValue 选择的类型选择的值
 * @param contentIndex 选择的工段
 */
function fillContentTOCombobox(sectionValue,contentIndex){
    $.ajax({
        url: "../../../findDeviceBySectionAndContentIndex",
        dataType: "json",    //返回的数据类型 json html 等
        type: "POST", //请求方式 post get
        data: {
            section: sectionValue,
            contentIndex:contentIndex
        }, //传递form的表单参数
        success: function (result) {
            console.log(result);
            if (result.length !=0){
                var jsonobject = function () {
                    return {
                        value:"",
                        text:""
                    }
                };
                var data = [];
                for(var i=0;i<result.length;i++){
                    var tempData = new jsonobject();
                    tempData.value = result[i].id;
                    tempData.text = result[i].desc;
                    data.push(tempData);
                }
                $('#content').combobox('clear');
                $('#content').combobox('loadData',data);
            }else {
                $('#content').combobox('clear');
                switch (contentIndex){
                    case "1":layer.msg($('#section').combobox('getText')+"不存在设备设定值");break;
                    case "2":layer.msg($('#section').combobox('getText')+"不存在设备启停控制值");break;
                    case "3":layer.msg($('#section').combobox('getText')+"不存在设备启停反馈值");break;
                }
            }
        },
        error: function () {
            layer.msg("服务器未响应");
        }
    });
}


//实时面积图
var onlinedom=document.getElementById("onlinechart");
var onlinechart=echarts.init(onlinedom);
var onlineoptions= {
    tooltip: {
        trigger: 'axis'
    },
    dataZoom: [{
        type:'slider',
        xAxisIndex:0,
        height:'15',
        bottom:'55',
        show: true,
        start: 0,
        end: 100,
        handleStyle:{ /*手柄的样式*/
            width:'20',
            color:"#085ABF",
            borderColor:"#085ABF"
        },
        backgroundColor:"#f7f7f7", /*背景 */
        dataBackground:{ /*数据背景*/
            lineStyle:{
                color:"#dfdfdf"
            },
            areaStyle:{
                color:"#dfdfdf"
            }
        },
        fillerColor:"#FFEFBE", /*被start和end遮住的背景*/
        labelFormatter:function (value,params) { /*拖动时两端的文字提示*/
            var str = "";
            if(params.length > 4){
                str = params.substring(0,4)+"…";
            }else {
                str = params;
            }
            return str;
        }
    }],
    legend: {
        data:[],
        position: 'vertical',
        x: 'left',
        y: 'top'
    },
    grid: {
        show:true,
        y: 40,
        backgroundColor: 'rgba(0,0,0,0)',
        borderWidth: 1,
        borderColor: '#FFFFFF',
    },
    toolbox: {
        x: 'right',
        feature: {
            saveAsImage: {},
            restore:{},
            dataView:{}
        }
    },
    xAxis: [{
        type: 'category',
        name:'日期',
        boundaryGap: false,
        data: ""
    }],

    yAxis: {
        type: 'value',
        splitLine: {
            lineStyle: {
                color: ['#EEEEEE']
            }
        },
        axisTick: {
            show: false
        },
        axisLine: {
            lineStyle: {
                color: '#50697A'
            }
        },
        axisLabel: {
            margin: 10,
            textStyle: {
                color:'#50697A',
                fontSize: 13
            }
        }
    },

    series: [{
        type:'line',
        data: "",
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(16, 79, 193,1)'
                }, {
                    offset: 1,
                    color: 'rgba(125, 178, 244,1)'
                }], false)
            }
        },
        itemStyle: {
            normal: {
                color: 'rgba(16, 79, 193,1)'
            }
        },
        lineStyle: {
            normal: {
                width: 0
            }
        }
    }]
};
if (onlineoptions && typeof onlineoptions === "object") {
    onlinechart.setOption(onlineoptions, true);
}

//历史曲线图绘制
Highcharts.setOptions({
    lang: {
        printChart: '打印图表',
        downloadJPEG: '下载 JPEG 文件',
        downloadPDF: '下载 PDF   文件',
        downloadPNG: '下载 PNG  文件',
        downloadSVG: '下载 SVG  文件',
        downloadCSV: '下载 CSV  文件',
        downloadXLS: '下载 XLS   文件'
    },
    navigation: {
        buttonOptions:{
            height:6
        },
        menuItemStyle: {
            padding: '2px 5px'
        }
    },
});
// 默认的导出菜单选项，是一个数组
var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
var  offlineoptions = {
    credits: {
        href:'javascript:void(0)',
        text: '中复神鹰'
    },
    exporting: {
        filename: "",
        csv: {
            dateFormat: '%Y-%m-%d'
        },
        buttons: {
            contextButton: {
                // 自定义导出菜单项目及顺序
                menuItems: [
                    dafaultMenuItem[0],//打印图标
                    dafaultMenuItem[1],//分割线
                    dafaultMenuItem[2],//打印PNG
                    dafaultMenuItem[3],//打印JPEG
                    dafaultMenuItem[4],//下载PDF
                    dafaultMenuItem[5],//打印SVG
                    dafaultMenuItem[6],//分割线
                    dafaultMenuItem[7],//下载CSV
                    dafaultMenuItem[8]//下载XLS
                ]
            }
        }
    },
    title: {
        align:'left',
        text: "",
        style:{
            fontSize:'15px'
        }
    },
    scrollbar : {
        enabled:true
    },
    xAxis: {
        type: 'category',
        dateTimeLabelFormats: {
            millisecond: '%H:%M:%S.%L',
            second: '%H:%M:%S',
            minute: '%H:%M',
            hour: '%H:%M',
            day: '%m-%d',
            week: '%m-%d',
            month: '%Y-%m',
            year: '%Y'
        },
        max:5
    },
    yAxis: {
        tickPositions: [0, 10, 20, 30,40,50], // 指定竖轴坐标点的值
        labels: {
            overflow: 'justify'
        },
        lineWidth: 1
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
    series: [{
        name: "",
    }],
    responsive: {
        rules: [{
            condition: {
                maxWidth: 600
            },
            chartOptions: {
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom'
                }
            }
        }]
    }
}
// 图表初始化函数
var offlinedom = document.getElementById("offlinechart");
var offlinechart = Highcharts.chart(offlinedom, offlineoptions);

//点击查询按钮的响应函数
function queryDeviceStatus() {
    var tag = $('#content').combobox('getValue');//获取请求的标签号
    var startTIme = $('#startTime').datetimebox('getValue');//获取请求的开始时间
    var endTime = $('#endTime').datetimebox('getValue');//获取请求的结束时间
    if(tag ===""){
        layer.msg("控制项目不能为空");
        return;
    }

    //获取时间戳
    var sdate = new Date(startTIme);
    var stime = sdate.getTime();
    var edate = new Date(endTime);
    var etime = edate.getTime();
    if(stime===etime){
        layer.msg("输入参数错误，起始时间与结束时间没有间隔");
        return;
    }
    if(stime>etime){
        layer.msg("输入参数错误，起始时间大于结束时间");
        return;
    }

    //ajax请求
    $.ajax({
        url: "../../../hbaseserver/findMulValFromHbase",
        async: false,
        dataType: "json",    //返回的数据类型 json html 等
        type: "POST", //请求方式 post get
        data: {
            tag: tag,
            startTIme:startTIme,
            endTime:endTime
        }, //传递form的表单参数
        success: function (result) {

        },

        error: function () {
            layer.msg("服务器错误");
        }
    });
}
function getData() {
    var rows = [];
    for (var i = 1; i <= 800; i++) {
        rows.push({
            time:i,
            tag:i,
            desc:i,
            address:i,
            type:i,
            statusvalue:i
        });
    }
    return rows;
}


function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
        data = {
            total: data.length,
            rows: data
        }
    }

    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        showPageList:false,
        pageSize:20,
        showRefresh: false,
        displayMsg:'',
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}