var cncInfoType = [{id: 0, text: "轴坐标"},
    {id: 1, text: "轴功率"},
    {id: 2, text: "轴负荷"}];
var cncAxisType = [];

var monitorArray = [];
var type = 1;					// 0:电类传感器；1:FBG温度；2：FBG应力；3：CCD
var subType = 5;					// 机床信息类型 0:轴坐标 1:轴功率 2:轴负荷
var figurenum = 1;


$(function () {
    // 当天日期
    var dateNow = new Date();
    var monthNow = dateNow.getMonth() + 1;
    var strDateNow = dateNow.getDate();
    if (monthNow >= 1 && monthNow <= 9) {
        monthNow = "0" + monthNow;
    }
    if (strDateNow >= 0 && strDateNow <= 9) {
        strDateNow = "0" + strDateNow;
    }
    var currentdate = dateNow.getFullYear() + "-" + monthNow + "-" + strDateNow;
    $('#date').combobox('setText', currentdate); // 默认设置当天日期
    $('#equipmentId').combobox({									// 设备组合框
        url: 'equipment/getAllEquip',
//        valueField:'value',// 注意，这个是num不是vaule和后台ComboVo。num对应
        valueField: (type == 4) ? 'num' : 'value',						// 在功率页面的时候返回编号
        textField: 'text',
        panelHeight: 'auto',
        editable: false,
        onChange: function (newValue, oldValue) {							// 组合框变化响应函数
            if (type == 4) {													// 查询机床信息的情况
                $("#infoTypeId").combobox('clear');						// 清空信息类型
                $("#axisTypeId").combobox('clear');						// 清空轴类型
                $("#infoTypeId").combobox("loadData", cncInfoType);		// 加载信息类型
            }
            else {															// 查询传感器信息的情况
                $("#channelId").combobox('clear');						// 清空通道
                $("#monitorId").combobox('clear');						// 清空传感器
                $("#sensorTypeId").combobox('clear');					// 清空传感器类型
                $.ajax({													// 加载所有的传感器
                    cache: false,
                    url: "channel/getSensorTypeByEquip",
                    data: {equipmentId: newValue},
                    type: "POST",
                    dataType: "json",
                    success: function (result) {
                        console.log(result);								// 将查询数据库的结果放到传感器类型组合框
                        $("#sensorTypeId").combobox("loadData", result);
                    }
                });

                $.ajax({													// 加载所有的传感器
                    cache: false,
                    url: "compensatorEquipment/getCompeansatorByEquip",
                    data: {equipmentId: newValue},
                    type: "POST",
                    dataType: "json",
                    success: function (result) {
                        console.log(result);								// 将查询数据库的结果放到传感器类型组合框
                        $("#CompeansatorId").combobox("loadData", result);
                    }
                });
            }
        }
    });

    $('#infoTypeId').combobox({												// 信息类型组合框
        url: '',
        valueField: 'text',
        textField: 'text',
        panelHeight: 'auto',
        editable: false,
        onChange: function (newValue, oldValue) {								// 组合框变化响应函数
            $("#axisTypeId").combobox('clear');								// 清空轴类型

            var selectEquipment = $('#equipmentId').combobox('getValue');	// 获取选择的机床编号
            var selectInfo;
            switch ($('#infoTypeId').combobox('getText')) {					// 获取选择的信息类型
                case "轴坐标":
                    selectInfo = "POS";
                    if (!history) chart.yAxis[0].setTitle({text: "坐标"});
                    subType = 0;
                    break;
                case "轴功率":
                    selectInfo = "PWR";
                    if (!history) chart.yAxis[0].setTitle({text: "功率"});
                    subType = 1;
                    break;
                case "轴负荷":
                    selectInfo = "FU";
                    if (!history) chart.yAxis[0].setTitle({text: "负荷"});
                    subType = 2;
                    break;
                default      :
                    break;
            }
            if (!history) chart.redraw();						//
            cncAxisType = [														// 初始化轴类型数组
                {id: 0, num: selectEquipment + "-X" + selectInfo, text: selectEquipment + "-X" + selectInfo},
                {id: 1, num: selectEquipment + "-Y" + selectInfo, text: selectEquipment + "-Y" + selectInfo},
                {id: 2, num: selectEquipment + "-Z" + selectInfo, text: selectEquipment + "-Z" + selectInfo},
                {id: 3, num: selectEquipment + "-MA" + selectInfo, text: selectEquipment + "-MA" + selectInfo}
            ];
            cncAxisType.unshift({text:"全选/反选",num:"全选/反选",value:"-1"});
            $("#axisTypeId").combobox("loadData", cncAxisType);				// 加载轴类型
        }
    });

    $('#axisTypeId').combobox({												// 轴类型组合框
        valueField: 'text',
        textField: 'text',
        panelHeight: 'auto',
        multiple: true,
        editable: false,
        onChange:function (newValue){
            var select = $('#axisTypeId');
            var valueField = select.combobox("options").valueField;
            if($.inArray("全选/反选", newValue)!=-1) {
                if (!newValue[valueField]) {
                    var data = select.combobox("getData");
                    var values = select.combobox("getValues");
                    var selectVaues = [];
                    if (data.length!= values.length) {
                        data.reduce(function (prev, current, index, array) {
                            selectVaues.push(current[valueField]);
                        }, selectVaues);
                    } else {
                        selectVaues.push(newValue[valueField]);
                    }
                    selectVaues .splice(0,1);
                    select.combobox('setValues', selectVaues);
                }
            }
        }
    });

    $('#sensorTypeId').combobox({							// 传感器类型组合框
        url: '',
//        valueField:'value',//注意，这个是num不是vaule和后台ComboVo。num对应
        valueField: 'num',
        textField: 'text',
        panelHeight: 'auto',
        editable: false,
        onChange: function (newValue, oldValue) {			// 组合框变化响应函数
            switch ($('#sensorTypeId').combobox('getText')) {	// 获取当前选择的传感器类型
                case "DS18B20":
                    type = 0;
                    if (!history) chart.yAxis[0].setTitle({text: "DS18B20"});
                    break;
                case "FBG温度传感器":
                    type = 1;
                    if (!history) chart.yAxis[0].setTitle({text: "FBG温度"});
                    break;
                case "FBG应力传感器":
                    type = 2;
                    if (!history) chart.yAxis[0].setTitle({text: "FBG应力"});
                    break;
                case "CCD位移传感器":
                    type = 3;
                    if (!history) chart.yAxis[0].setTitle({text: "CCD位移"});
                    break;
                case "环境温度":
                    type =5;
                    if (!history) chart.yAxis[0].setTitle({text: "环境温度"});
                    break;
                default:
                    break;
            }
            attention();
            if (!history) chart.redraw();						// 功率表重画
            var equipmentTempid = $('#equipmentId').combobox("getValues").toString();
            $("#channelId").combobox('clear');				// 清空通道
            $("#monitorId").combobox('clear');				// 清空传感器
            $.ajax({
                cache: false,
                url: "channel/getChannelByEquip",			// 根据设备类型来获取通道类型
                data: {equipmentId: equipmentTempid, channelType: (type + 1)},
                type: "POST",
                dataType: "json",
                success: function (result) {
                    console.log(result);					// 将通道信息加载到通道组合框
                    $("#channelId").combobox("loadData", result);
                }
            });
        }
    });

    $('#channelId').combobox({								// 通道类型组合框
        valueField: 'value',//注意，这个是num不是vaule和后台ComboVo。num对应
        textField: 'text',
        panelHeight: 'auto',
        editable: false,
        onChange: function (newValue, oldValue) {				// 组合框变化响应函数
            $("#monitorId").combobox('clear');				// 清空传感器
            $.ajax({
                cache: false,
                url: "monitor/getMonitorByChannel",			// 根据通道类型来加载传感器编号
                data: {channelId: newValue},
                type: "POST",
                dataType: "json",
                success: function (result) {					// 加载传感器编号到传感器编号组合框
                    var temparr=result;
                    temparr.unshift({text:"全选/反选",num:"全选/反选",value:"-1"});
                    $('#monitorId').combobox("loadData", temparr);
                }
            });
        }
    });

    $('#monitorId').combobox({								// 传感器编号组合框
        valueField: 'num',//注意，这个是num不是vaule和后台ComboVo。num对应
        textField: 'text',
        panelHeight: 'auto',
        multiple: true,
        editable: false,
        onChange:function (newValue){
            var select = $('#monitorId');
            var valueField = select.combobox("options").valueField;
            if($.inArray("全选/反选", newValue)!=-1) {
                if (!newValue[valueField]) {
                    var data = select.combobox("getData");
                    var values = select.combobox("getValues");
                    var selectVaues = [];
                    if (data.length!= values.length) {
                        data.reduce(function (prev, current, index, array) {
                            selectVaues.push(current[valueField]);
                        }, selectVaues);
                    } else {
                        selectVaues.push(newValue[valueField]);
                    }
                    selectVaues .splice(0,1);
                    select.combobox('setValues', selectVaues);
                }
            }
        }
    });

    $('#CompeansatorId').combobox({
        valueField: 'value',//
        textField: 'text',
        panelHeight: 'auto',
        editable: false,
        onChange: function (newValue, oldValue) {
            $("#CompStatus").text("工作正常")
        }
    });

    // $('#selectmodel').combobox("loadData",[{"123":"123"}]);


});


function search(id) {										// 查询按键响应函数
    var selectArray;										// 查询传感器编号数组
    if (type == 4) {											// 查询机床信息类型的时候
        selectArray = $('#axisTypeId').combobox('getValues');
    }
    else {													// 其他情况下获取传感器信息
        selectArray = $('#monitorId').combobox('getValues');
    }

    console.log("selectArray:" + selectArray);
    var series = chart.series;								// 获取当前表格的所有系列
    while (series.length > 0) {								// 清空所有的折线图
        series[0].remove(false);
    }

    var time = (new Date()).getTime();						// 获取当前时间
    var eachfirst = true; // 第一个系列的曲线用红色
    $.each(selectArray, function (n, value) {
        if (_.contains(monitorArray, value)) {
            // 监控数组中包含需要查询的值说明已经建立webSocket连接，不需要重新建立。不包含则新建webSocket
        } else {
            console.log("value:" + value);
            var webSocket = new WebSocket('ws://' + socketPath + '/websocket/' + value);

            webSocket.onerror = function (event) {			// socket错误响应事件
                onError(event);
            };
            webSocket.onopen = function (event) {			// socket开启响应事件
                onOpen(event);
            };
            webSocket.onmessage = function (event) {		// socket接收响应事件
                onMessage(event, value);
            };
        }

        if(eachfirst)
        {
            chart.addSeries({									// 将获取到的系列加到表格中
                name: '编号：' + value,
                data: (function () {
                    var data = [],
                        i;
                    for (i = -8; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: null
                        });
                    }
                    return data;
                }()),
                color: '#FF3333'
            });
            eachfirst = false;
        }
        else{
            chart.addSeries({									// 将获取到的系列加到表格中
                name: '编号：' + value,
                data: (function () {
                    var data = [],
                        i;
                    for (i = -8; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: null
                        });
                    }
                    return data;
                }())
            });
        }


    });
    monitorArray = _.union(selectArray, monitorArray);		// 两个数组就并集
}

function onOpen(event) {
}

function onError(event) {
    alert(event.data);
}

function onMessage(event, num) {
    console.log(event.data);
    var json = JSON.parse(event.data);
    var series = chart.series;
    $.each(series, function (n, serie) {
        var name = serie.name;
        if (('编号：' + json.num) == name) {								// 收到数据的时候增加数据点
            serie.addPoint([json.date, parseFloat(json.value)], true, true);
        }
        // if(type==4){												// 如果是功率页面
        // 	var i=0;
        // 	for(i=0;i<4;i++){										// 获取各轴的功率表并将数据写入至功率表
        // 		if(json.num==monitorArray[i]){					// 判断系列是否对应
        // 			if (chart1.series) {							// 判断系列是否为空
        // 				var left = chart1.series[i].points[0]; 	// 功率表数值改变
        // 				left.update(parseFloat(json.value),false);	// 更新功率表
        // 				chart1.redraw();							// 功率表重画
        // 			}
        // 		}
        // 	}
        // }
    });

}

function attention(){
    if (type == "3") {$('#nav').show();}//显示 div层；
    else {$('#nav').hide();}//隐藏 div层；
}

// 新版历史数据查询
function findhistory(id) {
    var selectArray; // 查询传感器编号数组
    if (type == 4) {											// 查询机床信息类型的时候
        selectArray = $('#axisTypeId').combobox('getValues');
    }
    else {													// 其他情况下获取传感器信息
        selectArray = $('#monitorId').combobox('getValues');
    }

    

    // 清空图表
    json.xAxis.categories = [0];
    json.series = [{name: "Waiting", data: [0]}];
    chart = $('#container').highcharts(json);
    // chart.xAxis  = json.xAxis;
    // chart.series = json.series;
    // id集合获取
    var selectArraydouhao = "";
    for (var n = 0; n < selectArray.length; n++) {
        if (n !== (selectArray.length - 1))
            selectArraydouhao = selectArraydouhao + selectArray[n] + ",";
        else {
            selectArraydouhao = selectArraydouhao + selectArray[n];
        }
    }
    console.log(selectArraydouhao);
    // 时间获取
    var selectTime = $('#date').combobox('getText');//选择的时间
    // 请求数据
    $.ajax({
        cache: false,
        url: 'historysearch/find',
        //data:{date:selectTime, onlyid:selectArray[0]},
        data: {date: selectTime, idArray: selectArraydouhao},
        type: "POST",
        dataType: "json",
        //async: false, // 关闭异步调用
        success: function (result) {
            //console.log(result.dateArray);
            //console.log(result.valuesArray[0]);
            //console.log(result.valuesArray[0][0]);
            var floatvalueArray = [];
            for (var i = 0; i < result.valuesArray.length; i++) {
                floatvalueArray[i] = [];
                for (var j = 0; j < result.valuesArray[0].length; j++) {
                    floatvalueArray[i][j] = parseFloat(result.valuesArray[i][j]);
                }
            }
            //console.log(floatvalueArray[0][0]);
            // Y轴曲线数据
            json.series = [{name: selectArray[0], data: floatvalueArray[0], color: '#FF3333'}];
            for (var k = 1; k < floatvalueArray.length; k++) {
                var temp = {name: selectArray[k], data: floatvalueArray[k]};
                json.series.push(temp);
            }
            // 横坐标标注-时间
            json.xAxis.categories = result.dateArray;
            chart = $('#container').highcharts(json);
            // chart.series = json.series;
            // chart.xAxis  = json.xAxis;

            // chart.title=json.title;
            // chart.credits =json.credits;
            // chart.yAxis=json.yAxis;
            // chart.tooltip=json.tooltip;
            // chart.legend=json.legend;

        }
    });
}
// jfz-end