$(function () {
   var page = $("#rightListPage");
   var form = page.find("#saveOrUpdateForm");
   var subForm;
   var url = "/system/right";
   init();

    function init() {
        loadRightTree();
        loadBtnListener();
    }

    function loadRightTree() {
        ajaxUtil().get("/system/right/queryRights","type=1",function (response) {
            if(response.success){
                var treeData = response.data;
                var tree = page.find("#rightTree");
                tree.treeview({
                    data:treeData,
                    showCheckbox:true,
                })
                /*tree.treeview({
                    color:"white",
                    backColor:"#1E282C",
                    data: treeData,
                    onhoverColor:"#1E282C",
                    showBorder:false,
                    showCheckbox:true,
                });*/
            }

            tree.on('nodeSelected', function(event, data) {
                console.log(data);
                ajaxUtil().get("/system/right/getById","id=" + data.id,function (response) {
                    if(response.success){
                        var right = response.data;
                        formUtil().fill(form,right);
                    }
                })

                //移除子节点编辑
                page.find("#subRightDiv").html("");

            });
        })
    }

    function loadBtnListener() {
        form.find("[name='addBtn']").on("click",function () {
            saveOrUpdate(form);
        });

        form.find("[name='addSubRightBtn']").on("click",function () {
            var id = form.find("[name='id']").val();
            if(id == ''){
                layerUtil.warn("请选择节点！");
            }else {
                var subRightDiv = page.find("#subRightDivScript").html();
                page.find("#subRightDiv").html(subRightDiv);
                subForm = page.find("#subRightDiv #subForm");
                subForm.find("[name='pid']").val(form.find("[name='id']").val());
                var level = Number(form.find("[name='level']").val());
                var subLevel = level + 1;
                subForm.find("[name='level']").val(subLevel);
                subForm.find("[name='addBtn']").on("click",function () {
                    saveOrUpdate(subForm);
                })
            }
        })
        
        
    }

    function saveOrUpdate($form) {
        var method;
        var id = $form.find("[name='id']").val();
        if(id == ""){
            method = "post";
        }else{
            method = "put";
        }
        ajaxUtil().saveOrUpdate(url,$form.serialize(),method,function (response) {
            if(response.success){
                layerUtil.success(response.msg);
                loadRightTree();
            }else {
                layerUtil.fail(response.msg);
            }
        });
    }

});