function drawToast(message){
	var alert = document.getElementById("toast");
	if (alert == null){
		var toastHTML = '<div id="toast">' + message + '</div>';
		document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	}
	else{
		alert.style.opacity = .9;
	}
	setTimeout(function(){
		var obj = document.getElementById("toast");
		obj.style.opacity = 0;
		//删除元素
		$("#toast").remove(); 
	},1000);
}