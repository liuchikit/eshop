$(function () {
    homePage().init();
});
var homePage = function () {
    var page = $("#homePage");
    var tabDiv = page.find("#tabDiv");
    var tabContentDiv = page.find("#tabContentDiv");

    return{
        init:function () {
            this.loadBtnListener();
            this.loadMenu();
        },

        loadBtnListener:function () {
            page.find("#logoutBtn").on("click",function () {
                window.location.href = "/logout";
            })
        },

        /**
         * 加载菜单
         */
        loadMenu:function () {
            var me = this;
            ajaxUtil().get("/system/right/queryRights","type=2",function (response) {
                if(response.success){
                    var treeData = response.data;
                    var tree = page.find("#treeView");
                    tree.treeview({
                        data:treeData,
                    })
                   /* tree.treeview({
                        color:"white",
                        backColor:"#1E282C",
                        data: treeData,
                        onhoverColor:"#1E282C",
                        showBorder:false,
                    });*/
                }

                tree.on('nodeSelected', function(event, data) {
                    var url = data.url;
                    if(url != null){
                        tabUtil().addTab(data.id,data.text,data.url);
                    }
                });
            })
        },

    }
};
