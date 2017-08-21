/**
 * Created by xiexw on 2017/8/14.
 */
(function (hey) {
    hey.ajax = function (owner) {
        //请求地址
        var url = "http://" + window.location.host + "/" + owner.url;
        //请求类型
        var type = owner.type.toUpperCase() || "GET";
        //返回值类型
        var dataType = owner.dataType || "";
        //请求参数
        var data = owner.data || "";
        //同步/异步
        var async = owner.async || true;
        //请求超时设置
        var timeout = owner.timeout || 0;

        //请求发送回调
        var complete = owner.complete;
        //成功回调
        var success = owner.success;
        //失败回调
        var error = owner.error;
        //超时回调
        var settimeout = owner.settimeout;
        
        //出错信息
        var errMsg = "请求出错：";

        //创建XHR对象
        var XHR;
        if (window.XMLHttpRequest) {
            XHR = new XMLHttpRequest();
        } else {
            //IE
            XHR = new ActiveXObject("Microsoft.XMLHTTP");
        }

        //请求句柄
        XHR.onreadystatechange = function () {
            if (XHR.readyState == 2) {
                //发送完成
                if (complete) {
                    complete();
                }
            } else if (XHR.readyState == 4) {
                //返回结果判断
                if (XHR.status != 0 && XHR.response.status != "500") {
                    switch (dataType) {
                        case "json":
                            success(XHR.response);
                            break;
                        case "xml":
                            success(XHR.responseXML);
                            break;
                        default:
                            success(XHR.responseText);
                            break;
                    }
                } else {
                    error(errMsg + XHR.response.msg);
                }
            }
        };

        //请求参数转为URL字符串
        var params = "";
        var index = 0;
        if (data != "") {
            for (key in data) {
                index++;
                if (index > 1) {
                    //一个以上加入连接符
                    params += "&" + key + "=" + data[key];
                } else {
                    params += key + "=" + data[key];
                }
            }
        }

        //GET方法则拼接URL
        if (type == "GET") {
            url += (params != "" ? "?" + params : "");
        }

        //XHR创建连接
        XHR.open(type, url, async);

        //返回值类型
        XHR.responseType = dataType;

        //超时设置
        XHR.timeout = timeout;
        XHR.ontimeout = function () {
            if (settimeout) {
                settimeout();
            }
        };

        //POST方法带参数
        var postData = null;
        if (type == "POST") {
            //另设请求头
            XHR.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            postData = params;
        }

        //XHR请求发送
        XHR.send(postData);
    }
}(window.HEY = {}));