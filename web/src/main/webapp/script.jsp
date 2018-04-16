<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/easyui/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="${ctx}/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/easyui/jquery.datagrid.extend.js"></script>

<script type="text/javascript" src="${ctx}/js/des/tripledes.js"></script>  
<script type="text/javascript" src="${ctx}/js/des/mode-ecb.js"></script>
<script type="text/javascript" src="${ctx}/js/des/md5.js"></script> 

<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script> 
<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script> 


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

//json格式化和压缩
function format(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */  
    var indentChar = '    ';   
    if(/^\s*$/.test(txt)){   
        alert('数据为空,无法格式化! ');   
        return;   
    }   
    try{var data=eval('('+txt+')');}   
    catch(e){   
        alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');   
        return;   
    };   
    var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;   
       
    var notify=function(name,value,isLast,indent/*缩进*/,formObj){   
        nodeCount++;/*节点计数*/  
        for (var i=0,tab='';i<indent;i++ )tab+=indentChar;/* 缩进HTML */  
        tab=compress?'':tab;/*压缩模式忽略缩进*/  
        maxDepth=++indent;/*缩进递增并记录*/  
        if(value&&value.constructor==Array){/*处理数组*/  
            draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/  
            for (var i=0;i<value.length;i++)   
                notify(i,value[i],i==value.length-1,indent,false);   
            draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/  
        }else   if(value&&typeof value=='object'){/*处理对象*/  
                draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/  
                var len=0,i=0;   
                for(var key in value)len++;   
                for(var key in value)notify(key,value[key],++i==len,indent,true);   
                draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/  
            }else{   
                    if(typeof value=='string')value='"'+value+'"';   
                    draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);   
            };   
    };   
    var isLast=true,indent=0;   
    notify('',data,isLast,indent,false);   
    return draw.join('');   
}  
</script>