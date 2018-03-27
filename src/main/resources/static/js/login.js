$(function () {
    var page = $("#loginPage");
    var form = page.find("#loginForm");
    var url =  "/login";
    init();

    function init() {
        loadBtnListener();
    }

    function loadBtnListener() {
        form.find("[name='loginBtn']").on("click",function () {
            login();
        })
    }

    function login() {

      /*  var response = ajaxUtil().syncPost("login",form.serialize());
        if(response.success){
            window.location.href = "/home";
        }*/
        ajaxUtil().post("/login",form.serialize(),function (response) {
            if(response.success){
                window.location.href = "/home";
            }else {
                layerUtil.fail(response.msg);
            }
        })

    }


});

