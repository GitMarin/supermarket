Vue.prototype.$post = function(_this,data,callback,url,msg){
    axios.post(url,data)
        .then(function (response) {
            var result=response.data;
            if(result.rtnFlag=="9999"){//请求成功
                callback(result);
            }else if(response.data.rtnFlag=="1001"){//未登录
                //_this.loginDgVisible=true;
            }else{
                _this.$bdmsg({
                    type:"toast",
                    msgType:"error",
                    message: msg+'失败！'+response.data.rtnMsg
                });
            }
        }).catch(function (error) {
        // handle error
        console.log(error);
    }).then(function () {
        // always executed
    });
};

Vue.prototype.$get = function(_this,callback,url,msg){
    axios.get(url)
        .then(function (response) {
            var result=response.data;
            if(result.rtnFlag=="9999"){//请求成功
                callback(result);
            }else if(response.data.rtnFlag=="1001"){//未登录
                _this.loginDgVisible=true;
            }else{
                _this.$bdmsg({
                    type:"toast",
                    msgType:"error",
                    message: msg+'失败！'+response.data.rtnMsg
                });
            }
        }).catch(function (error) {
        // handle error
        console.log(error);
    }).then(function () {
        // always executed
    });
};


Vue.prototype.$dateFormatter = function(str){//默认返回yyyy-MM-dd HH-mm-ss
    if(!str){
        return null;
    }
    var hasTime = arguments[1] != false ? true : false;//可传第二个参数false，返回yyyy-MM-dd
    var d = new Date(str);
    var year = d.getFullYear();
    var month = (d.getMonth()+1)<10 ? '0'+(d.getMonth()+1) : (d.getMonth()+1);
    var day = d.getDate()<10 ? '0'+d.getDate() : d.getDate();
    var hour = d.getHours()<10 ? '0'+d.getHours() : d.getHours();
    var minute = d.getMinutes()<10 ? '0'+d.getMinutes() : d.getMinutes();
    var second = d.getSeconds()<10 ? '0'+d.getSeconds() : d.getSeconds();
    if(hasTime){
        return [year, month, day].join('-') + " " + [hour, minute, second].join(':');
    }else{
        return [year, month, day].join('-');
    }
};


Vue.prototype.$fetchRequestParmValue=function(url,paras) {
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

Vue.prototype.$filterText=function(txt) {
    txt = txt.replace(/(\n)/g, "");
    txt = txt.replace(/(\t)/g, "");
    txt = txt.replace(/(\r)/g, "");
    txt = txt.replace(/<\/?[^>]*>/g, "");
    txt = txt.replace(/\s*/g, "");
    if(txt.length>200){
        return txt.substr(0,200)+"......";
    }else{
        return txt;
    }
}

Vue.prototype.$compareDate=function(s1) {
    var curTime = new Date();
    return (curTime<(new Date(s1.replace(/-/g,"\/"))));
}


