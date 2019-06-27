function OnKeyUp(_e) {
    var e=_e?_e:window.event;
    if(event.keyCode == 13){
        var username = document.getElementById("username");
        var password = document.getElementById("password");
        if(username.value== ''){
            alert("请您输入你的用户名");
        }else if(password.value==''){
            alert("等等！ 您还没有输入您的密码呢！！！")
        }else {
            login();
        }
    }
}



//当点击鼠标事件发生时
function login() {
    $.ajaxSetup({
        contentType : 'application/json'
    });
    var data = $("#loginForm").serializeArray(); //自动将form表单封装成json
    data=convertToJson(data);
    console.log(data);
    $.ajax({
        url: "../haslogin",
        dataType: "text",    //返回的数据类型 json html 等
        type: "post", //请求方式 post get
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(data), //传递form的表单参数
        success: function (result) {
            if(result==="SUCCESS"){
                window.location.href = "../haslogin2";
            }else{
                alert(result);
            }
        },
        error: function () {
            alert("服务端未响应")
        }
    });
}

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