/**
 * Created by xiexw on 2017/8/14.
 */
window.onload = function () {
    
};

function getDataAjax() {
    HEY.ajax({
        type: "post",
        url: "music/getMusic.do",
        dataType: "json",
        data: {
            "id": 2
        },
        timeout: 1000,
        // complete: function () {
        //     alert("发送完成");
        // },
        success: function (j) {
            var result = j.data;
            document.getElementById("id").innerHTML = result.id;
            document.getElementById("name").innerHTML = result.name;
            document.getElementById("album").innerHTML = result.album;
        },
        error: function (msg) {
            alert(msg);
        },
        settimeout: function () {
            alert("请求超时");
        }
    });
}