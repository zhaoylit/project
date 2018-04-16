<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
  $(function() {
    $(".sortable").sortable({
    cursor: "move",
    items :"li",                        //只是li可以拖动
    opacity: 0.6,                       //拖动时，透明度为0.6
    revert: true,                       //释放时，增加动画
    update : function(event, ui){       //更新排序之后
        alert($(this).sortable("toArray"));
    }
   });
 });
</script>
<ul class="sortable">
  <li class="ui-state-default"  id="1">第1项</li>
  <li class="ui-state-default"  id="2" >第2项</li>
  <li class="ui-state-default"  id="3">第3项</li>
</ul>
