<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<script type="text/javascript" src="${ctx}/js/des/tripledes.js"></script>  
<script type="text/javascript" src="${ctx}/js/des/mode-ecb.js"></script>
<script type="text/javascript" src="${ctx}/js/des/md5.js"></script> 

<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script> 


<script type="text/javascript">
$(function(){
	$.ajaxSetup({     
	  contentType:"application/x-www-form-urlencoded;charset=utf-8",
	  complete:function(XMLHttpRequest,textStatus){
	      var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，    
	      var desc=XMLHttpRequest.getResponseHeader("desc"); //通过XMLHttpRequest取得响应头，sessionstatus，    
	      var ciphertext = "";  
	      if(desc != null){
	    	  ciphertext = decryptByDES(desc,"12345678")
	      }
	      if(sessionstatus=="noauth"){//ajax请求无权限 
	           //如果超时就处理 ，指定要跳转的页面
	           $.messager.alert('提示','${loginUser.userName }账户<br/>没有['+ciphertext+']<br/>的操作权限!','warning')
	      }else if(sessionstatus=="timeout"){//登录超时，父页面跳转到登录页面
	    	  window.parent.location.href = XMLHttpRequest.responseText;
	      }   
	    }
	});  
})

function encryptByDES(message, key) {  
    var keyHex = CryptoJS.enc.Utf8.parse(key);  
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {  
        mode: CryptoJS.mode.ECB,  
        padding: CryptoJS.pad.Pkcs7  
    });  
    return encrypted.toString();  
}  
function decryptByDES(ciphertext, key) {  
    var keyHex = CryptoJS.enc.Utf8.parse(key);  
    var decrypted = CryptoJS.DES.decrypt({  
        ciphertext: CryptoJS.enc.Base64.parse(ciphertext)  
    }, keyHex, {  
        mode: CryptoJS.mode.ECB,  
        padding: CryptoJS.pad.Pkcs7  
    });  
   
    return decrypted.toString(CryptoJS.enc.Utf8);  
}  
</script>