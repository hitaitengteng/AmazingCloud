layui.use(['jquery','layer','element'],function() {
    window.jQuery = window.$ = layui.jquery;
    window.layer = layui.layer;
});
var raw_slik_json={
    //纺丝机房风机
    "y6_engine_room": ["y6_bangai11","y6_bangai12"],
    //主管道计量泵
    "y6_main_pump": ["y6_sa79","y6_ao76"],
    //热保温水
    "y6_hot_water": [
        "y6_ai01"
    ],
    //冷保温水
    "y6_cold_water": [
        "y6_ai02"
    ],
    //原液主管道
    "y6_raw_main_pipe": [
        "y6_ai03",
        "y6_ai20",
        "y6_ai26",
        "y6_ao76"
    ],
    //支管道计量泵
    "y6_branch_pump": [
        "y6_jlai01",
        "y6_jlai02",
        "y6_jlai03",
        "y6_jlai04",
        "y6_jlai05",
        "y6_jlai06",
        "y6_jlai07",
        "y6_jlai08",
        "y6_jlai09",
        "y6_jlai10",
        "y6_jlai11",
        "y6_jlai12",
        "y6_jlai13",
        "y6_jlai14",
        "y6_jlai15",
        "y6_jlai16",
        "y6_jlai17",
        "y6_jlai18",
        "y6_jlai19",
        "y6_jlai20",
        "y6_jlai21",
        "y6_jlai22",
        "y6_jlai23",
        "y6_jlai24",
        "y6_jlai25",
        "y6_jlai26",
        "y6_jlai27",
        "y6_jlai28",
        "y6_jlai29",
        "y6_jlai30",
        "y6_jlai31",
        "y6_jlai32",
        "y6_jlai33",
        "y6_jlai34",
        "y6_jlai35",
        "y6_jlai36",
        "y6_jlai37",
        "y6_jlai38",
        "y6_jlai39",
        "y6_jlai40"
    ],
    //喷头
    "y6_nozzle": [
        "y6_rt09",
        "y6_rt10",
        "y6_rt11",
        "y6_rt12",
        "y6_rt13",
        "y6_rt14",
        "y6_rt15",
        "y6_rt16",
        "y6_rt17",
        "y6_rt18",
        "y6_rt19",
        "y6_rt20",
        "y6_rt21",
        "y6_rt22",
        "y6_rt23",
        "y6_rt24",
        "y6_rt25",
        "y6_rt26",
        "y6_rt27",
        "y6_rt28",
        "y6_rt29",
        "y6_rt30",
        "y6_rt31",
        "y6_rt32",
        "y6_rt33",
        "y6_rt34",
        "y6_rt35",
        "y6_rt36",
        "y6_rt37",
        "y6_rt38",
        "y6_rt39",
        "y6_rt40",
        "y6_rt41",
        "y6_rt42",
        "y6_rt43",
        "y6_rt44",
        "y6_rt45",
        "y6_rt46",
        "y6_rt47",
        "y6_rt48"
    ],
    //空气牵伸机驱动
    "y6_air_draft_drive": ["y6_qdai01"],
    //凝固浴
    "y6_coagulation_bath": [
        "y6_ai27",
        "y6_ai81"
    ],
    //流量阀门
    "y6_flux_valve": [
        "y6_ai28",
        "y6_ai29",
        "y6_ai30",
        "y6_ai31",
        "y6_ai32",
        "y6_ai33",
        "y6_ai34",
        "y6_ai35",
        "y6_ai36",
        "y6_ai37",
        "y6_ai38",
        "y6_ai39",
        "y6_ai40",
        "y6_ai41",
        "y6_ai42",
        "y6_ai43",
        "y6_ai44",
        "y6_ai45",
        "y6_ai46",
        "y6_ai47",
        "y6_ai48",
        "y6_ai49",
        "y6_ai50",
        "y6_ai51",
        "y6_ai52",
        "y6_ai53",
        "y6_ai54",
        "y6_ai55",
        "y6_ai56",
        "y6_ai57",
        "y6_ai58",
        "y6_ai59",
        "y6_ai60",
        "y6_ai61",
        "y6_ai62",
        "y6_ai63",
        "y6_ai64",
        "y6_ai65",
        "y6_ai66",
        "y6_ai67"
    ],
    //凝固浴循环水槽
    "y6_coagulation_bath_flume": [
        "y6_ai21",
        "y6_rt03",
        "y6_bangao05",
        "y6_bangao06",
        "y6_bangao09",
        "y6_sa92"
    ],
    //水洗风机
    "y6_water_wash_air_machine": ["y6_bangai13","y6_bangai14","y6_bangai15"],
    //水牵风机
    "y6_water_draft_air_machine": ["y6_bangai16","y6_bangai17","y6_bangai18"],
    //水洗驱动
    "y6_water_wash_drive": [
        "y6_qdai02",
        "y6_qdai03",
        "y6_qdai04",
        "y6_qdai05",
        "y6_qdai06"
    ],
    //水洗机
    "y6_water_machine": [
        "y6_ai04",
        "y6_ai05",
        "y6_ai06",
        "y6_ai07"
    ],
    //水牵驱动
    "y6_water_draft_drive": [
        "y6_qdai07",
        "y6_qdai08",
        "y6_qdai09"
    ],
    //水牵机
    "y6_water_draft_machine": [
        "y6_ai09",
        "y6_ai10",
        "y6_rt05",
        "y6_rt06",
        "y6_bangao01",
        "y6_bangao02",
        "y6_bangao03",
        "y6_bangao04"
    ],
    // 水冷机
    "y6_water_cold_machine": [
        "y6_rt54"
    ],
    //水洗水槽
    "y6_water_wash_water_flume": [
        "y6_ai22",
        "y6_ao06",
        "y6_sa74"
    ],
    //牵伸水槽,
    "y6_draft_water_water_flume": [
        "y6_ai23",
        "y6_ai69",
        "y6_ai70"
    ],
    //冷却水水槽
    "y6_cold_water_flume": [
        "y6_ai24",
        "y6_ao08",
        "y6_sa75"
    ],
    //脱盐水调节阀
    "y6_demineralized_water_valve": [
        "y6_ao53",
        "y6_ao54",
        "y6_ao55",
        "y6_ao56",
        "y6_sa51",
        "y6_sa52",
        "y6_sa53",
        "y6_sa54",
        "y6_ao73",
        "y6_sa55",
        "y6_ai72",
        "y6_sa91"
    ],
    //牵伸水调节阀
    "y6_draft_water_valve": [
        "y6_ao58",
        "y6_ao59",
        "y6_sa56",
        "y6_sa57",
        "y6_ai69",
        "y6_ai70",
        "y6_sa70",
        "y6_sa71",
        "y6_ao50",
        "y6_ao51",
        "y6_ao60",
        "y6_ao61",
        "y6_sa58",
        "y6_sa59",
        "y6_ai69",
        "y6_ai70"
    ],
    //冷却水调节阀
    "y6_cold_water_valve": ["y6_ao02"],
    //搅拌
    "y6_stir":[
        "y6_bangai10",
        "y6_bangao10"
    ],
    //浓邮寄存储槽
    "y6_oil_store_slot":["y6_ai12"],
    //油槽
    "y6_oil_slot":[
        "y6_bangao11",
        "y6_bangao12"
    ],
    //上油驱动
    "y6_oil_drive":["y6_qdai10"],
    //干燥调节阀
    "y6_dry_adjust_valve":[
        "y6_ao57",
        "y6_sa78",
        "y6_sa62",
        "y6_sa63",
        "y6_sa64",
        "y6_sa65",
        "y6_ti62",
        "y6_au62",
        "y6_au63",
        "y6_au64",
        "y6_au65",
        "y6_ao65",
        "y6_ao66",
        "y6_ao67",
        "y6_ao68",
        "y6_pd62",
        "y6_pd63",
        "y6_pd64",
        "y6_pd65",
        "y6_pi62",
        "y6_pi63",
        "y6_pi64",
        "y6_pi65"
    ],
    //干燥机
    "y6_dry_machine":[
        "y6_ai13",
        "y6_ai14",
        "y6_ai15",
        "y6_ai19",
        "y6_rt58",
        "y6_rt59",
        "y6_rt60",
        "y6_rt61",
        "y6_qdai11",
        "y6_qdai12",
        "y6_qdai13",
        "y6_qdai14"
    ],
    //蒸汽主管道
    "y6_vapour_main_pump":[
        "y6_ai71",
        "y6_sa90"
    ],
    //烘干冷却水
    "y6_dry_cold_water":[
        "y6_rt55",
        "y6_ao74",
        "y6_sa05",
        "y6_au05",
        "y6_pi05",
        "y6_ti05",
        "y6_pd05",
    ],
    //蒸汽调节阀
    "y6_vapour_adjust_valve":[
        "y6_sa66",
        "y6_sa67",
        "y6_sa68",
        "y6_ao69",
        "y6_ao70",
        "y6_ao71",
        "y6_pi66",
        "y6_pi67",
        "y6_pi68",
        "y6_ti66",
        "y6_ti67",
        "y6_ti68",
        "y6_pd66",
        "y6_pd67",
        "y6_pd68"
    ],
    //压缩空气罐
    "y6_compress_air_pump":[
        "y6_ai08",
        "y6_ai75",
    ],
    //稳压气包
    "y6_press":[
        "y6_ai16",
        "y6_ai17"
    ],
    //蒸汽气包
    "y6_vapour":[
        "y6_ai18",
    ],
    //蒸牵机
    "y6_vapour_machine":[
        "y6_ai16",
        "y6_ai17",
        "y6_ai18",
        "y6_ai08"
    ],
    //干燥一、二风机
    "y6_dry_wind_machine":["y6_bangai19","y6_bangai20"],
    //蒸牵一、二风机
    "y6_vapour_wind_machine":["y6_bangai21","y6_bangai22"],
    //蒸牵冷却水
    "y6_vapour_cold_water":[
        "y6_rt56",
        "y6_ao75",
        "y6_sa06",
        "y6_au06",
        "y6_pi06",
        "y6_ti06",
        "y6_pd06"
    ],
    //蒸牵驱动
    "y6_vapour_drive":[

    ]
}

//全局变量,保存当前所有选中的需要查询的工序过程的tag
var global_y6_tag=[];
//全局变量，定时器
var timer="";
var timerflag = false;//默认定时器关闭
//全局变量,当前页码
var pageNUm=1;

$("#tableData").datagrid({
    title:"数据列表",
    iconCls:"icon-left02",
    fitColumns:true,
    striped:true,
    pagination:true,
    pageSize:8,
    width:'100%',
    rownumbers:true,
    nowrap:true,
    height:'auto',
    checkOnSelect:false,
    toolbar: '#tabelOperate',
    columns:[[
        /* {
             checkbox:true,
             field:'no',
             width:100,
             align:'center'
         },*/
        {
            field:'tag',
            title:'标签',
            width:100,
            align:'center'



        },
        {
            field:'desc',
            title:'描述',
            width:100,
            align:'center'



        },
        {
            field:'address',
            title:'I/O ADDRESS',
            width:100,
            align:'center'



        },
        {
            field:'type',
            title:'数据类型',
            width:100,
            align:'center'



        }, {
            field:'time',
            title:'时间',
            width:100,
            align:'center'



        },
        {
            field:'value',
            title:'当前值',
            width:100,
            align:'center'



        },
        {
            field:"opr",
            title:'操作',
            width:100,
            align:'center',
            formatter:function (val,row) {
                return '<a class="easyui-linkbutton" href="javascript:void(0)" onclick=obj.online('+'\"'+row.tag+'\"'+','+'\"'+row.desc+'\"'+')>查看</a>';
            }

        }
    ]]
})

$("#tt_one").datagrid({
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);//将强求参数封装到全局数组中去
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据
    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);//将强求参数封装到全局数组中去
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }
});
$("#tt_two").datagrid({
    /**
     *
     * @param index 行的索引
     * @param rowData 行的内容
     */
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);//将强求参数封装到全局数组中去
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据
    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);//将强求参数封装到全局数组中去
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }

});

$("#tt_three").datagrid({
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据

    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }
});


$("#tt_four").datagrid({
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据

    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }
});
$("#tt_five").datagrid({
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据
    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }
});
$("#tt_six").datagrid({
    onSelect:function (index, rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = true;
        fillUpTable();//向后台请求数据

    },
    onUnselect:function (index,rowData) {
        var fieldCollect=findBlurFileldByRegex(rowData.name1);
        addTagToglobal_y6_tag(fieldCollect);
        window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
        timerflag = false;
        fillUpTable();//向后台请求数据

    }
});


/**
 * 通过正则表达式匹配出大类字段 表行元素的内容（只有一格，里面是dom元素组成的字符串）
 * @param blurFieldString
 * @returns {*|string} 以逗号分隔组成的具体控制项目的tag
 */
function findBlurFileldByRegex(blurFieldString) {
    var matchReg=/(?<=AA_).*?(?=_BB)/;//取出以AA_开头,_BB结尾的字符串,不包含这两者
    var blurField=(blurFieldString.match(matchReg)[0]);
    var fieldCollect=raw_slik_json[blurField].join(",");
    //console.log(fieldCollect);
    //alert(fieldCollect);
    return fieldCollect;
}

/**
 * 检验全局数组变量中是否存在该标签，若存在则删除，否则添加进来
 * @param fieldCollect
 */
function addTagToglobal_y6_tag(fieldCollect) {
    if(global_y6_tag.length===0&&fieldCollect!=''){
        global_y6_tag.push(fieldCollect);
    }else{
        var flag=validateIsExistsContent(global_y6_tag,fieldCollect);
        if(!flag&&fieldCollect!=''){
            global_y6_tag.push(fieldCollect);
        }else {
            global_y6_tag.remove(fieldCollect);

        }
    }
}

/**
 * 判断全局数组中是否存在此元素
 * @param global_y6_tag
 */
function validateIsExistsContent(global_y6_tag,fieldCollect) {
    var flag=false;
    for(var i=0;i<global_y6_tag.length;i++){
        if(global_y6_tag[i]===fieldCollect){
            flag=true;
        }
    }
    return flag;

}

/**
 * 像后台提交请求
 * @param global_y6_tag 请求参数数组
 */
function fillUpTable() {
    //console.log(pageNUm);
    var str=global_y6_tag.join(",");
    var tranData = str.split(",");
    var dataLength=tranData.length;
    if(dataLength<=8){
        ajaxRequest(tranData,dataLength);
    }else {
        var tempData=[];
        var count =1;
        for(var i=(pageNUm-1)*8;i<dataLength;i++){
            if(count<=8){
                count++;
                tempData.push(tranData[i]);
            }else {
                count=1;break;
            }
        }
        ajaxRequest(tempData,dataLength);
    }
    if (timerflag){
        timer=window.setTimeout(fillUpTable, 2000);//1秒更新一次
    }
}

/**
 * 向表格中填充数据
 * @param data
 */
var requestdataTag=null;
function ajaxRequest(data,dataLength) {
    /*$('#tableData').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData(data));*/
    requestdataTag = new copyArr(data);
    var dataGrid = $("#tableData");
    var data1 = getData(data);
    dataGrid.datagrid('loadData', data1);
    var p = dataGrid.datagrid('getPager');
    $(p).pagination({
        total:dataLength,
        pageNumber:pageNUm,
        showPageList:false,
        showRefresh: false,
        pageSize: 8,//每页显示的记录条数，默认为10
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '',
        onSelectPage:function(pageNumber, pageSize){
            console.log("点击下一页");
            console.log(pageNumber);
            pageNUm=pageNumber;
            window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
            fillUpTable();

        }
    });
}

/**
 * 模拟填充数据
 * @returns {Array}
 */
var values = null;
function getData(data) {
    var rows = [];
    var flag = false;
    $.ajax({
        url: "../../../findAllContentByTags",
        async: false,
        dataType: "json",    //返回的数据类型 json html 等
        type: "POST", //请求方式 post get
        data: {
            tags: data.join(',')
        }, //传递form的表单参数
        success: function (result) {
            //console.log(result);
            $.ajax({
                url: "../../../dataQuery/currentValues",
                async: false,
                dataType: "json",    //返回的数据类型 json html 等
                type: "POST", //请求方式 post get
                data: {
                    currtime:getCurrDateExcludeYMD(),
                    tags: data.join(',')
                }, //传递form的表单参数
                success: function (data) {
                    console.log(data);
                    values = data;
                }
            });

            flag =true;
            var tempdata = result.content;
            var tempvalues = values;
            console.log(tempvalues);
            if(data!="" &&tempdata!=null &&tempvalues.success){
                for (var i = 0; i < data.length; i++) {
                    var temptag = tempdata[i].tag;
                    console.log(tempvalues.data[temptag]);
                    rows.push({
                        tag:temptag,//标签
                        desc:tempdata[i].desc,//描述
                        address:tempdata[i].address,//地址
                        type:tempdata[i].type,//数据类型
                        time:getCurrDate(),//当前时间
                        value:tempvalues.data[temptag],//当前zhi
                    });
                }
            }else {
                layer.msg("查询内容为空，请检查你的查询条件");
            }
        },
        error: function () {
            window.clearTimeout(timer);//去掉定时器，点击请求的时候表格不自动更新
            layer.msg("服务器未响应");
        }
    });

    if (flag){
        //console.log(rows);
        return rows;
    }
}

var interval=null;
obj={
    // 查看
    online:function (tag,desc) {
        clearInterval(interval);
        //alert(id);
        $("#onlineChart").dialog({
            title:desc+"",
            closed:false,
            onResize:function(){
                $(this).dialog('center');
            }
        });
        var date = [];
        var randomData = [];
        /*for(var i = 0; i<30; i++){
            date.push(getCurrDate());
            randomData.push(Math.floor(Math.random(20,30)*100));
        }*/

        var dom=document.getElementById("chart");
        myChart=echarts.init(dom);
        var options= {
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
                data: date,
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
                data: randomData,
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
        if (options && typeof options === "object") {
            myChart.setOption(options, true);
        }


        var len = date.length;
        interval = setInterval(function(){
            var xaxis = function () {
                return{
                    type: 'category',
                    boundaryGap: false,
                    data: []
                }
            };

            var dataseries = function () {
                return {
                    name: desc,
                    type: 'line',
                    data: [],
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
                }
            };

            $.ajax({
                url: "../../../dataQuery/currentValue",
                //async: false,
                dataType: "jsonp",    //返回的数据类型 json html 等
                type: "GET", //请求方式 post get
                data: {
                    tag: tag
                }, //传递form的表单参数
                jsonp:"callback",
                jsonpCallback: "getMessage",
                success: function (data) {
                    console.log(data);
                    randomData.push(data.data);
                    date.push(getCurrDate());
                    if(randomData.length == 30&&date.length == 30){
                        randomData.shift();
                        date.shift();
                    }

                    var axis = new xaxis();
                    axis.data=date;
                    var Axis = []; //准备存放横坐标的数据
                    Axis.push(axis);
                    options.xAxis = Axis;

                    var series = new dataseries();
                    series.data=randomData;
                    var Series = [];
                    Series.push(series);
                    options.series = Series;
                    myChart.setOption(options);


                }
            });
        }, 2000)

    },
    //导出Excel
    exportExcel:function () {
        $.ajax({
            url: "",
            dataType: "json",    //返回的数据类型 json html 等
            type: "POST", //请求方式 post get
            data: {
                section_tag: requestdataTag
            }, //传递form的表单参数
            success: function (result) {
                alert("SUCCESS");
            },
            error: function () {
                layer.msg("服务器未响应");
            }
        });
    }
}

function getMessage(jsonp){

}

//打印全局数组
function printglobal_y6_tag(global_y6_tag) {
    if (global_y6_tag.length>=2){
        var temp=global_y6_tag.join(",");
        console.log(temp);
    }
    console.log(global_y6_tag);
}

/**
 * 首先可以给js的数组对象定义一个函数，用于查找指定的元素在数组中的位置，即索引
 * @param val
 * @returns {number}
 */
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

/**
 * 然后使用通过得到这个元素的索引，使用js数组自己固有的函数去删除这个元素：
 * @param val
 */
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

/**
 * 拷贝数组
 * @param arr
 * @returns {Array}
 */
function copyArr(arr) {
    var res = []
    for (var i = 0; i < arr.length; i++) {
        res.push(arr[i])
    }
    return res
}

function getCurrDate() {
    var curr_time=new Date();
    var strDate=curr_time.getFullYear()+"-";
    strDate +=curr_time.getMonth()+1+"-";
    strDate +=curr_time.getDate()+"-";
    strDate +=" "+curr_time.getHours()+":";
    strDate +=curr_time.getMinutes()+":";
    strDate +=curr_time.getSeconds();
    return strDate;
}

function getCurrDateExcludeYMD() {
    var curr_time=new Date();
    var strDate =curr_time.getHours()+":";
    strDate +=curr_time.getMinutes()+":";
    strDate +=curr_time.getSeconds();
    return strDate;
}