<%@ page language="java" contentType="text/html; charset=UTF-8"  import="com.zj.web.common.utils.*"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String return_path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path");
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<link href="<%=path %>/plug/sortable/app.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.sortable-drag{ 
	border : 1px solid yellow;
}
</style>
<script src="<%=path %>/plug/sortable/Sortable.js"></script>
<ul id="imageSort" style="width:99%;margin:10px 0 0 0;">
	<c:forEach items="${data}" var="bean" varStatus="idx">
		<li style="width: 210px;float: left; margin-left: 20px;" data-id="${bean.id}">
			<img src="${bean.imageUrl }" width="200" height="100" style="margin: 5px 10px 10px 5px;"/>
		</li>
	</c:forEach>
</ul>
<input type="hidden" id="orderIds" value=""/>
<script type="text/javascript">
$(function(){
	var el = document.getElementById('imageSort');
	var sortable = Sortable.create(el, {
		group: "words",
		animation: 150,/*滑动速度*/
		disabled: false,/* 为true时，拖拽不可用*/
		chosenClass: "sortable-chosen",/*选中时的样式*/
		dragClass: "sortable-drag", /*拖拽是的样式*/
		dataIdAttr: 'data-id', /*数据id*/
		store: {
			get: function (sortable) {
				var order = localStorage.getItem(sortable.options.group);
				return order ? order.split('|') : [];
			},
			set: function (sortable) {
				var order = sortable.toArray();
				var ids =  order.join(',');
				$("#orderIds").val(ids);
			}
		},
		onAdd: function (evt){ console.log('onAdd.foo:', [evt.item, evt.from]); },
		onUpdate: function (evt){ 
			//更新后触发
			var itemEl = evt.item;
		},
		onRemove: function (evt){ console.log('onRemove.foo:', [evt.item, evt.from]); },
		onStart:function(evt){ console.log('onStart.foo:', [evt.item, evt.from]);},
		onSort:function(evt){ console.log('onStart.foo:', [evt.item, evt.from]);},
		onEnd: function(evt){ 
			//拖拽完毕
			var oldIndex = evt.oldIndex;  // element's old index within parent
			var newIndex = evt.newIndex;
		}
	});
})
</script>