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


/**
 * 将表单对象转为json对象
 * @param formValues
 * @returns
 */
function convertToJson(formValues) {
    var result = {};
    for(var formValue,j=0;j<formValues.length;j++) {
        formValue = formValues[j];
        var name = formValue.name;
        var value = formValue.value;
        if (name.indexOf('.') < 0) {
            if(result[name]) {
                result[name] = result[name] + "," + value;
            } else {
                result[name] = value;
            }
            continue;
        } else {
            var simpleNames = name.split('.');
            // 构建命名空间
            var obj = result;
            for ( var i = 0; i < simpleNames.length - 1; i++) {
                var simpleName = simpleNames[i];
                if (simpleName.indexOf('[') < 0) {
                    if (obj[simpleName] == null) {
                        obj[simpleName] = {};
                    }
                    obj = obj[simpleName];
                } else { // 数组
                    // 分隔
                    var arrNames = simpleName.split('[');
                    var arrName = arrNames[0];
                    var arrIndex = parseInt(arrNames[1]);
                    if (obj[arrName] == null) {
                        obj[arrName] = []; // new Array();
                    }
                    obj = obj[arrName];
                    multiChooseArray = result[arrName];
                    if (obj[arrIndex] == null) {
                        obj[arrIndex] = {}; // new Object();
                    }
                    obj = obj[arrIndex];
                }
            }

            if(obj[simpleNames[simpleNames.length - 1]] ) {
                var temp = obj[simpleNames[simpleNames.length - 1]];
                obj[simpleNames[simpleNames.length - 1]] = temp;
            }else {
                obj[simpleNames[simpleNames.length - 1]] = value;
            }

        }
    }
    return result;
}

/*对应的图标部分*/
var dom = document.getElementById("e_container");
var myChart = echarts.init(dom);

option = {
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:[],
        position: 'vertical',
        x: 'left',
        y: 'top'
    },
    grid: {
        y: 40,
        backgroundColor: 'rgba(0,0,0,0)',
        borderWidth: 1,
        borderColor: '#ccc',
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
        boundaryGap: false,
        data: []
    }],
    dataZoom: [{
        start: 30,
        end: 70,
        left: 'center',
        backgroundColor: 'rgba(0,0,0,0)',       // 背景颜色
        dataBackgroundColor: '#eee',            // 数据背景颜色
        fillerColor: 'rgba(144,197,237,0.2)',   // 填充颜色
        handleColor: 'rgba(70,130,180,0.8)'     // 手柄颜色
    }, {
        type: 'inside'
    }],

    yAxis: [{
        type: 'value'
    }],

    series: []
};

/*巡查项目组合框*/
var ycontentName;
$('#ynum').combobox({
    method: 'post',
    valueField: 'id',
    textField: 'name',
    multiple: false,
    editable: false,
    panelHeight: 'auto',
   /* onLoadSuccess:function(){
        $('#ynum').combobox('clear');
    },*/
    onSelect:function (ynum) {
        var title;
        if(ycontentName !== ""){
            title = ynum.id+':'+ycontentName+':'+'变化图';
        }else {
            title = ycontent.name+':'+'变化图'
        }
        myChart.setOption({
            title: {
                text: title,
            }
        });
    },

    onUnselect:function (ynum) {
        myChart.setOption({
            title: {
                text: '',
            }
        });
    }
});

/*巡查项目具体内容*/
$('#ycontent').combobox({
    method: 'post',
    valueField: 'id',
    textField: 'name',
    multiple: false,
    editable: false,
    panelHeight: 'auto',
   /* onLoadSuccess:function(){
        $('#ycontent').combobox('clear');
    },*/
    onSelect:function (ycontent) {
        var yid = $("#ynum").combobox('getValue');
        ycontentName = ycontent.name;
        var title;
        if(yid !== ""){
            title = yid+':'+ycontent.name+':'+'变化图';
        }else {
            title = ycontent.name+':'+'变化图'
        }
        myChart.setOption({
            title: {
                text: title,
            }
        });
    },
    onUnselect:function (ycontent) {
        ycontentName = ycontent.name;
        myChart.setOption({
            title: {
                text: '',
            }
        });
    }
});

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

function finddata() {
    $.ajaxSetup({
        contentType : 'application/json'
    });
    var data = $("#updatecharts").serializeArray();
    data=convertToJson(data);
    console.log(data);
    $.ajax({
        url: "../../hbaseconsumer/findByTimeRang",
        dataType: "text",    //返回的数据类型 json html 等
        type: "post", //请求方式 post get
        data: JSON.stringify(data), //传递form的表单参数
        success: function (data) {
            var array = function () {
                return {
                    name: '',
                    type: 'line',
                    data: []
                }
            };

            var xaxis = function () {
                return{
                    type: 'category',
                    boundaryGap: false,
                    data: []
                }
            };
            var item = JSON.parse(data);
            var time = [];
            for(i in item.Time){
                time.push(item.Time[i]);
            }
            var value= [];
            for (var i in item.Value){
                value.push(item.Value[i]);
            }

            var it = new array();
            var axis = new xaxis();
            axis.data = time;
            it.name = item.index;
            it.data = value;
            //legend、series中的值在这里动态注入
            var legends = [];//准备存放图例数据
            var Series = [];
            var Axis = []; //准备存放横坐标的数据

            legends.push(item.index);
            Axis.push(axis);
            Series.push(it);

            option.legend.data = legends;
            option.xAxis = Axis;
            option.series = Series;
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }

        },
        error: function () {
            alert("服务端未响应")
        }
    });
}