$(function(){
    $('#bootstrap-data-table-export').datagrid({
        url: getUrl(),
        title: 'vehicle',
        method: 'post',
        iconCls: 'icon-save',
        dataType : "json",
        minWidth : 1400,
        fitColumns: true,
        striped: true, //奇偶行颜色不同
        singleSelect:true,
        collapsible:true,
        pageSize:15,
        pageList: [15,30,50],
        pagination:true,
        rownumbers:true,
        columns:[[
            {field:'vin',title:'VIN',width:160,align:'center'},
            {field:'deviceId',title:'deviceId',width:340,align:'center'},
            {field:'data.locLat',title:'locLat',width:150,align:'center',
            formatter : function (value, rec) {
                return rec.data['locLat'];
            }},
            {field:'data.locLong',title:'locLong',width:150,align:'center',
            formatter : function (value, rec) {
                return rec.data['locLong'];
            }},
            {field:'data.mileage',title:'mileage',width:80,align:'center',
            formatter : function (value, rec) {
                return rec.data['mileage'];
            }},
            {field:'data.fuelLevel',title:'fuelLevel',width:150,align:'center',
            formatter : function (value, rec) {
                return rec.data['fuelLevel'];
            }},
            {field:'data.doorOpened',title:'doorOpened',width:80,align:'center',
            formatter : function (value, rec) {
                return rec.data['doorOpened'];
            }},
            {field:'data.timestamp',title:'timestamp',width:100,align:'center',
            formatter : function (value, rec) {
                return rec.data['timestamp'];
            }}
        ]],
    });//datagrid
});
//$('.datagrid-cell').css('font-size','8px');//更改的是datagrid中的数据

//$('.datagrid-header .datagrid-cell span ').css('font-size','36px');datagrid中的列名称
//('.panel-title ').css('font-size','36px'); 标题
//$('.datagrid-pager').css('display','none');分页工具栏
//获取表单数据并拼接url中的查询参数
function getUrl(){
    var formArray=$("#vehicleForm").serializeArray();
    var url=url_ip_port+'/vehicle/telematicsData?';
    $.each(formArray,function(i,item){
        url=url+item.name+"="+item.value+"&";
    });
    return url;
}
//为查询按钮绑定点击事件
function Search(){
    var url=getUrl();
    $('#bootstrap-data-table-export').datagrid('options').url=url;
    $("#bootstrap-data-table-export").datagrid('reload'); 
}