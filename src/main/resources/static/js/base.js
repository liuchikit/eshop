var ajaxUtil = function () {
    return{
        get:function (url,params,successFunc) {
            ajaxUtil().ajax(url,params,"get","true","json",successFunc);
        },
        syncGet:function (url,params) {
            var result;
            ajaxUtil().ajax(url,params,"get","false","json",function (response) {
                result = response;
            });
            return result;
        },
        syncPost:function (url,params) {
            ajaxUtil().ajax(url,params,"post","false","json",function (data, textStatus, jqXHR) {
                return data;
            });
        },
        saveOrUpdate:function(url,params,method,successFunc){
            return ajaxUtil().ajax(url,params,method,"true","json",successFunc);
        },
        post:function (url,params,successFunc) {
            return ajaxUtil().ajax(url,params,"post","true","json",successFunc);
        },
        delete:function (url,params) {
            return ajaxUtil().ajax(url,params,"delete","true","json");
        },
        put:function (url,params) {
            return ajaxUtil().ajax(url,params,"put","true","json",successFunc);
        },
        toPage:function (url,params) {
            return ajaxUtil().ajax(url,params,"get","true","data")
        },
        ajax:function (url,params,type,async,dataType,successFunc) {
            var result = {};
            $.ajax({
                url:url,
                type:type,
                async:async,
                dataType:dataType,
                data:params,
                beforeSend:function () {
                    
                },
                success:function (data, textStatus, jqXHR) {
                   if($.isFunction(successFunc)){
                       successFunc(data, textStatus, jqXHR);
                   }
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {

                },
                always:function () {

                }
            });
        },
    }
};

/**
 * layer工具类，统一样式
 * @type {{load, success, fail, warn, alert, version}}
 */
var layerUtil = function () {

    var LAYER_INDEX = 400;

    /**
     * 弹窗
     * @param msg    内容
     * @param title  标题
     * @param type   icon类型 默认皮肤可以传入0-6 http://wiki.ppmoney.com/pages/viewpage.action?pageId=15384818
     * @param option 配置项
     * @param event   事件
     * @return {*}
     */
    function alert(msg, title, type, option, event) {
        var defAlertSetting = {icon: type, title: title};
        var useOption = $.extend({}, defAlertSetting, option);
        return layer.alert(msg, useOption, event);
    }

    /**
     * 自定义弹窗默认配置
     * @type {{shadeClose: boolean, type: number, area: [*], zIndex: number, btn: [*]}}
     */
    var defOpenSetting = {
        shadeClose: false,  //点击空白处关闭true 不关闭false，默认false
        type: 1,
        area: ['600px', 'auto'], //宽高
        zIndex: LAYER_INDEX,            //默认值太大，导致select2下拉不出现
        btn: ['提交', '取消']
    };

    return {

        /**
         * 操作成功提示
         * @param msg     内容
         * @param option  配置项
         * @param event   事件
         * @returns {*}
         */
        success: function (msg, option, event) {
            var defSetting = {icon: 1, time: 2000};
            var useOption = $.extend({}, defSetting, option);
            return layer.msg(msg, useOption, event);
        },

        /**
         * 操作失败提示
         * @param msg     内容
         * @param title   标题
         * @param option  配置项
         * @param event   事件
         * @returns {*}
         */
        fail: function (msg, title, option, event) {
            if (!title) title = '错误';
            return alert(msg, title, 5, option, event);
        },

        /**
         * 警告提示
         * @param msg     内容
         * @param title   标题
         * @param option  配置项
         * @param event   事件
         * @returns {*}
         */
        warn: function (msg, title, option, event) {
            if (!title) title = '警告';
            return alert(msg, title, 0, option, event);
        },

        /**
         * 普通提示
         * @param msg     内容
         * @param option  配置项
         * @param event   事件
         * @returns {*}
         */
        alert: function (msg, option, event) {
            return alert(msg, '提示', 1, option, event)
        },

        /**
         * 弹出确认框
         * @param msg     内容
         * @param event   事件
         * @param option  配置项
         * @returns {*}
         */
        confirm: function (msg, event, option) {
            // 3是问号
            var defSetting = {title: '提示', icon: 3};
            var useOption = $.extend({}, defSetting, option);
            return layer.confirm(msg, useOption, event);
        },

        /**
         * 自定义窗口
         * @param title    标题
         * @param content   内容
         * @param successFun  初始化事件
         * @param yesFun      确认按钮事件/第一个按钮事件
         * @param option      配置项
         * @return {*}
         */
        open: function (title, content, successFun, yesFun, option) {
            option = $.extend({}, option, {title: title, content: content, success: successFun, yes: yesFun});
            var useOption = $.extend({}, defOpenSetting, option);
            return layer.open(useOption);
        },

        version: 1.0
    }

}();

/**
 * 等待加载工具类
 * @type {{showLoading, hideLoading, hideAllLoading}}
 */
var loadingUtil = function () {
    return {
        /**
         * 显示loading
         * @param timeout 默认值90s
         */
        showLoading: function (timeout) {
            if (!timeout){
                timeout = 600 * 1000;
            }
            return layer.load(0, {shade: false, time: timeout});
        },

        /**
         * 隐藏loading
         * @param index loading框的索引
         */
        hideLoading: function (index) {
            layer.close(index);
        },

        /**
         * 隐藏所有loading
         */
        hideAllLoading: function () {
            layer.closeAll('loading');
        }
    };
}();

var tableUtil = function () {

    var table;

    /** datatable 默认配置  **/
    var defaultSetting = {
        paging: true,
        searching: false,
        serverSide: false,
        ordering: false,
        scrollX: true,
        scrollY: false,
        pageLength: 15,
        pagingType: 'full_numbers',
        lengthMenu: [5, 10, 15, 20, 50, 100],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        fnRowCallback: null, //行事件
    };

    return {

        /**
         * 初始化datables
         * @param $obj        table dom对象
         * @param url        请求地址
         * @param columns    字段配置
         * @param setting    datatable 设置
         * @param params    请求参数 预留
         */
        load: function ($obj, url,params,columns,setting) {
            var nowSetting = $.extend({}, defaultSetting);
            nowSetting.columns = columns;

            if (url) { // 自己传数据源
                nowSetting.serverSide = true;
                nowSetting.ajax = {
                    type: 'get',
                    url: url,
                    async: true
                };

            }
            setting = $.extend({}, nowSetting, setting);

            table = $obj.DataTable(setting);


            return table;
        },
        /**
         * 根据url重新加载数据
         * @param $datatable datables对象
         * @param url  get请求url，需要拼接参数
         */
        reload: function (table, url) {
            table.ajax.url(url).load();
        },

        /**
         * 重置查询表单
         * @param $form 表单对象
         * @param selectDef 重置下拉对象的默认值
         */
        resetQueryForm: function ($form) {
            $form[0].reset();

            $form.find('select').each(function (idx, ele) {
                $(ele).val(null).trigger('change');
            });

            $form.find('input[type="hidden"]').each(function (idx, ele) {
                $(ele).val('');
            });

            //todo 扩展其他控件
        },

        version: 1.0
    }

}();

var formUtil = function () {


    return{
        fill:function (form,data) {
            for(var i in data) {
                var obj = form.find("[name=" + i + "]");
                if (obj && obj.length > 0) {
                    var type = obj[0].type;
                    if (type == "text" || type == "hidden") {
                        obj.val(data[i]);
                    } else if (type == "radio") {
                        obj = form.find("[name=" + i + "][value = " + data[i] + "]");
                        obj.prop("checked", true);
                    }
                }
            }
        }
    }
};

var systemUtil = function () {
    return {
        getCurrentPage:function (selector) {
            var tabContent = $("#tabContentDiv");
            var tabPage = tabContent.find(".tab-pane.active");
            return tabPage;
        },

        openTab:function (tabId,name,url) {

        }
    }
};

var tabUtil = function () {

    var topWindow = $(window.parent.document);
    var tabDiv = topWindow.find("#tabDiv");
    var tabContentDiv = topWindow.find("#tabContentDiv");

    return{

        /**
         * 打开tab页
         * @param id
         * @param text
         * @param url
         */
        addTab:function (id,text,url) {
            var me = this;
            var tabId = "tab-" + id;
            var tabContentId = "tab-content-" + id;

            //若果tab页已经存在，则直接显示
            if(this.checkTabExist(tabContentId)){
                tabDiv.find("#"+ tabId + "").tab("show");
                return;
            }
            tabContentDiv.find(".active").removeClass("active");
            var tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='fa fa-times-circle'></span></li>";
            tabTemplate.replace( /#\{href\}/g,tabContentId).replace( /#\{label\}/g, text );

            if(tabContentDiv.find("#"+ tabContentId +"").length > 0){
                tabContentDiv.find("#"+ tabContentId +"").attr("")
            }

            $.ajax({
                url:url,
                success:function (content) {
                    var tab = "<li id=" + tabId + "><a href='#" + tabContentId + "' data-toggle='tab'>" + text + "<i class='fa fa-times'></i></a></li>";
                    tabDiv.append(tab);
                    //添加tab内容
                    var tabContent = "<div id='" + tabContentId + "' class='tab-pane active'>" + content +
                        "</div>";
                    tabContentDiv.append(tabContent);

                    //给tab标签注册事件
                    tabDiv.find("i").on("click",function () {
                        var tabId = $(this).parent().parent().attr("id");
                        tabUtil().closeTab(tabId);
                    });
                    tabDiv.tab("refresh");
                },
            });
        },

        /**
         * 关闭tab页
         */
        closeTab:function (tabId) {
            var tabContentId = tabDiv.find("#"+ tabId +"").find("a").attr("href").replace(/#/,"");
            tabDiv.find("#" + tabId + "").remove();
            tabContentDiv.find("#" + tabContentId + "").remove();
        },

        /**
         * 检查tab页是否存在
         * @param tabContentId
         * @returns {boolean}
         */
        checkTabExist:function (tabContentId) {
            return tabContentDiv.find("#" + tabContentId + "").length > 0;
        },

        /**
         * 加载按钮监听器
         */
        loadBtnListener:function () {

        }
    }
}