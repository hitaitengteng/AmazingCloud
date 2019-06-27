var chart  = null;				// 折线图
var history=false;
var type = 1;					// 0:电类传感器；1:FBG温度；2：FBG应力；3：CCD
var subType=5;					// 机床信息类型 0:轴坐标 1:轴功率 2:轴负荷 5:未选择
var picName = "传感器数值";
var danwei = '\u2103';

$(function() {
    setAxisTitle();
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});

	// 折线图插件
	chart = Highcharts.chart('container', {
		credits : {
			enabled : false
			//去掉highcharts网站url
		},
		chart : {
			type : 'spline',
			animation : Highcharts.svg,
			plotBorderWidth : 1,
			spacingLeft : 0,
			marginRight : 10
		},
		title : {
			text : null
		},
		xAxis : {
			type : 'datetime',
			tickPixelInterval : 150,
		},
		yAxis : {
			title : {
				text : picName
			},
			plotLines : [ {
				value : 0,
				width : 0.5,
				color : '#808080'
			} ]
		},
		tooltip : {
			formatter : function() {
                var temp=setAxisTitle();
				return '<b>' + this.series.name + '</b><br/>'
					+ Highcharts.numberFormat(this.y, 2) + temp[1];
			}
		},
		legend : {//图例，也就是右上角图标
			//enabled: false
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'right',
			borderWidth : 0
		},
		exporting : {
            enabled : false
        },
		series : [ {
			name : picName,
			data : (function() {
				var data = [], time = (new Date()).getTime(), i;
				for (i = -8; i <= 0; i += 1) {
					data.push({
						x : time + i * 1000,
						y : 0
					});
				}
				return data;
			}()),
			color : '#FF3333'
		} ]
	});		// 折线图插件完结
});

function setAxisTitle() {
    switch(type) {
        case 0:
            picName = "电类温度";				// 电类温度查询页面
            danwei = '\u2103';
            break;
        case 1:
            picName = "FBG温度";				// FBG温度查询页面
            danwei = '\u2103';
            break;
        case 2:
            picName = "FBG应力";				// FBG应力查询页面
            danwei = '\u2103';
            break;
        case 3:
            picName = "CCD位移";				// CCD查询页面
            danwei = "mm";
            break;
        case 4:								// 功率查询页面
			switch (subType){
				case 0:picName = "坐标";danwei = "mm";break;
				case 1:picName = "功率";danwei = "A";break;
				case 2:picName = "负荷";danwei = "%";break;
				default:picName = "未选择";danwei = "";break;
			}
            break;
        case 5:								// 历史查询页面
            picName = "未选择";
            danwei = "";
            break;
        default:							// 崩坏的情况
            picName = "未选择";
            danwei = "";
            break;
    }
    return new Array(picName,danwei);
}


// jfz test
var json = {};
$(document).ready(function() {
	var title = {
		text: '历史数据查询'
	};
	// var subtitle = {
	// 	text: '数据'
	// };

    var credits = {
        enabled : false //去掉highcharts网站url
    };

	var xAxis = {
		categories: ['0']
	};

	var yAxis = {
		title: {
			text: 'Values'
		},
		plotLines: [{
			value: 0,
			width: 1,
			color: '#808080'
		}]
	};

	var tooltip = {
		valueSuffix: '' // 数值后缀
	}

	var legend = {
		layout: 'vertical',
		align: 'right',
		verticalAlign: 'middle',
		borderWidth: 0
	};

	var series =  [
		{
			name: '0',
			data: [0],
		}
	];

	json.title = title;
	// json.subtitle = subtitle;
    json.credits = credits;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.tooltip = tooltip;
	json.legend = legend;
	json.series = series;
    // json.exporting = exporting;

	//$('#container1').highcharts(json);
});