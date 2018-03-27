$(function () {
   var page = $("#roleListPage");
   var queryForm = page.find("#queryForm");
   var table = page.find("#roleListTable");
   var datatable;
   var url = "/system/role";
   var listUrl = "/system/role/list";
   init();

    function init() {
        loadTable(true);
        loadBtnListener();
    }

    function loadBtnListener() {
        queryForm.find("[name='queryBtn']").on("click",function () {
            loadTable(false);
        });

        page.find("[name='addBtn']").on("click",function () {
            saveOrUpdate();
        })
        
        
    }
    
    function fnCreatedRow(rowDom, rowData, rowNum) {
        $(rowDom).on("click",function () {
            console.log(rowData);
        });

        $(rowDom).find("[name='updateBtn']").on("click",function () {
            saveOrUpdate(rowData.id);
        });

        $(rowDom).find("[name='redateRightBtn']").on("click",function () {
            saveOrUpdate(rowData.id);
        });

        $(rowDom).find("[name='updateBtn']").on("click",function () {
            saveOrUpdate(rowData.id);
        });

    }

    function saveOrUpdate(id) {
        var title;
        var method;
        if(id != null){
            title = "编辑";
            method = "put";
        }else {
            method = "post";
            title = "新增";
        }

        layerUtil.open(title,page.find("#saveOrUpdateDiv").html(),
            function (layero, index) {
                var form = layero.find("#saveOrUpdateForm");
                if(id != null){
                    ajaxUtil().get(url + "/getById","id=" + id,function (response) {
                        var role = response.data;
                        formUtil().fill(form,role);
                    });
                }
            },
            function (index,layero) {
                var form = layero.find("#saveOrUpdateForm");
                ajaxUtil().saveOrUpdate(url,form.serialize(),method,function (response) {
                    if(response.success){
                        layerUtil.success(response.msg);
                    }else {
                        layerUtil.fail(response.msg);
                    }
                });
            },{area: ['400px', 'auto'],})
    }
    
    

    function loadTable(flag) {

        var columns = [
            { data: 'id', title: 'ID',visible:false},
          /*  {   title: '序号', data: null, render: function (data, type, row, meta) {
                return meta.settings._iDisplayStart + meta.row + 1;
                }
            },*/
            { data: 'roleName', title: '角色名称'},
            { data: 'roleCode', title: '角色编码'},
            { data: 'roleDesc', title: '角色描述'},
            { data: 'status', title: '是否有效'},
            {title:'操作',width:'250px',data:function () {
                return page.find(".btn-div").html();
            }}
        ];
        if(flag){
            datatable = tableUtil.load(table,listUrl + "?" + queryForm.serialize(),null,columns,
                {
                    fnCreatedRow: fnCreatedRow
                }
            );
        }else {
            tableUtil.reload(datatable,listUrl + "?" + queryForm.serialize());
        }

    };

});