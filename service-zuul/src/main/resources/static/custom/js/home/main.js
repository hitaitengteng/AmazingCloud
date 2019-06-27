/**
 * Created by Administrator on 2017/11/8.
 */
$("#mainBox").layout({
        fit:true,
        border:false
})
$("#mean").menu('show',{
        left: 200,
        top: 100
})
$("#left01").accordion({
        border:false

})
$("#con").tabs({
        fit:true,
        border:false
})


function saveExit() {
        $.messager.confirm('退出确认','你是否退出系统？',function (data) {
            if (data){
                $.ajax({
                    url: "../../requestquit",
                    dataType: "text",//返回的数据类型json html等
                    type: "get", //请求方式post get
                    contentType:"application/json;charset=utf-8",
                    success: function (result) {
                        if(result==="SUCCESS"){
                            window.location.href = "../../quit";
                        }else{
                            alert(result);
                        }
                    },
                    error: function () {
                        alert("服务端未响应")
                    }
                });
            }
        })

}
function saveCanle() {
        $.messager.confirm('注销确认','你是否注销用户？',function (data) {
            if (data){
                $.ajax({
                    url: "../../requestquit",
                    dataType: "text",//返回的数据类型json html等
                    type: "get", //请求方式post get
                    contentType:"application/json;charset=utf-8",
                    success: function (result) {
                        if(result==="SUCCESS"){
                            window.location.href = "../../quit";
                        }else{
                            alert(result);
                        }
                    },
                    error: function () {
                        alert("服务端未响应")
                    }
                });
            }
        })
}
$("#left01  a").click(function () {
    //console.log("11111");
    var testVal=$(this).text();
    var thisUrl=$(this).attr('href');
    //console.log($(this));
    var target=$(this)[0].target;
    //console.log(target)
    if(target==="frameName") {
        openPage(testVal,thisUrl);
    }
});



function openPage(testVal,thisUrl){
    //已经存在的选择
    if ($("#con").tabs('exists', testVal)) {
        $("#con").tabs('select',testVal);
        //否则添加tab
    }else{
        var con = '<iframe scrolling="no" frameborder="0"  src="'+thisUrl+'" style="width:100%;height:100%;">';
        $('#con').tabs('add', {
            title: testVal,
            selected: true,
            closable: true,
            content: con
        });
    }
}

$("#con").tabs({
        onSelect:function (tit,ind) {
                if(ind==0){
                        $("#ifDiv").attr('src',"/page/home.html");
                }

        }
})
