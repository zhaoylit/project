/*
SWFObject v2.2 <http://code.google.com/p/swfobject/> 
is released under the MIT License <http://www.opensource.org/licenses/mit-license.php> 
*/
;var swfobject=function(){var D="undefined",r="object",S="Shockwave Flash",W="ShockwaveFlash.ShockwaveFlash",q="application/x-shockwave-flash",R="SWFObjectExprInst",x="onreadystatechange",O=window,j=document,t=navigator,T=false,U=[h],o=[],N=[],I=[],l,Q,E,B,J=false,a=false,n,G,m=true,M=function(){var aa=typeof j.getElementById!=D&&typeof j.getElementsByTagName!=D&&typeof j.createElement!=D,ah=t.userAgent.toLowerCase(),Y=t.platform.toLowerCase(),ae=Y?/win/.test(Y):/win/.test(ah),ac=Y?/mac/.test(Y):/mac/.test(ah),af=/webkit/.test(ah)?parseFloat(ah.replace(/^.*webkit\/(\d+(\.\d+)?).*$/,"$1")):false,X=!+"\v1",ag=[0,0,0],ab=null;
if(typeof t.plugins!=D&&typeof t.plugins[S]==r){ab=t.plugins[S].description;if(ab&&!(typeof t.mimeTypes!=D&&t.mimeTypes[q]&&!t.mimeTypes[q].enabledPlugin)){T=true;
X=false;ab=ab.replace(/^.*\s+(\S+\s+\S+$)/,"$1");ag[0]=parseInt(ab.replace(/^(.*)\..*$/,"$1"),10);ag[1]=parseInt(ab.replace(/^.*\.(.*)\s.*$/,"$1"),10);
ag[2]=/[a-zA-Z]/.test(ab)?parseInt(ab.replace(/^.*[a-zA-Z]+(.*)$/,"$1"),10):0;}}else{if(typeof O.ActiveXObject!=D){try{var ad=new ActiveXObject(W);if(ad){ab=ad.GetVariable("$version");
if(ab){X=true;ab=ab.split(" ")[1].split(",");ag=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)];}}}catch(Z){}}}return{w3:aa,pv:ag,wk:af,ie:X,win:ae,mac:ac};
}(),k=function(){if(!M.w3){return;}if((typeof j.readyState!=D&&j.readyState=="complete")||(typeof j.readyState==D&&(j.getElementsByTagName("body")[0]||j.body))){f();
}if(!J){if(typeof j.addEventListener!=D){j.addEventListener("DOMContentLoaded",f,false);}if(M.ie&&M.win){j.attachEvent(x,function(){if(j.readyState=="complete"){j.detachEvent(x,arguments.callee);
f();}});if(O==top){(function(){if(J){return;}try{j.documentElement.doScroll("left");}catch(X){setTimeout(arguments.callee,0);return;}f();})();}}if(M.wk){(function(){if(J){return;
}if(!/loaded|complete/.test(j.readyState)){setTimeout(arguments.callee,0);return;}f();})();}s(f);}}();function f(){if(J){return;}try{var Z=j.getElementsByTagName("body")[0].appendChild(C("span"));
Z.parentNode.removeChild(Z);}catch(aa){return;}J=true;var X=U.length;for(var Y=0;Y<X;Y++){U[Y]();}}function K(X){if(J){X();}else{U[U.length]=X;}}function s(Y){if(typeof O.addEventListener!=D){O.addEventListener("load",Y,false);
}else{if(typeof j.addEventListener!=D){j.addEventListener("load",Y,false);}else{if(typeof O.attachEvent!=D){i(O,"onload",Y);}else{if(typeof O.onload=="function"){var X=O.onload;
O.onload=function(){X();Y();};}else{O.onload=Y;}}}}}function h(){if(T){V();}else{H();}}function V(){var X=j.getElementsByTagName("body")[0];var aa=C(r);
aa.setAttribute("type",q);var Z=X.appendChild(aa);if(Z){var Y=0;(function(){if(typeof Z.GetVariable!=D){var ab=Z.GetVariable("$version");if(ab){ab=ab.split(" ")[1].split(",");
M.pv=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)];}}else{if(Y<10){Y++;setTimeout(arguments.callee,10);return;}}X.removeChild(aa);Z=null;H();
})();}else{H();}}function H(){var ag=o.length;if(ag>0){for(var af=0;af<ag;af++){var Y=o[af].id;var ab=o[af].callbackFn;var aa={success:false,id:Y};if(M.pv[0]>0){var ae=c(Y);
if(ae){if(F(o[af].swfVersion)&&!(M.wk&&M.wk<312)){w(Y,true);if(ab){aa.success=true;aa.ref=z(Y);ab(aa);}}else{if(o[af].expressInstall&&A()){var ai={};ai.data=o[af].expressInstall;
ai.width=ae.getAttribute("width")||"0";ai.height=ae.getAttribute("height")||"0";if(ae.getAttribute("class")){ai.styleclass=ae.getAttribute("class");}if(ae.getAttribute("align")){ai.align=ae.getAttribute("align");
}var ah={};var X=ae.getElementsByTagName("param");var ac=X.length;for(var ad=0;ad<ac;ad++){if(X[ad].getAttribute("name").toLowerCase()!="movie"){ah[X[ad].getAttribute("name")]=X[ad].getAttribute("value");
}}P(ai,ah,Y,ab);}else{p(ae);if(ab){ab(aa);}}}}}else{w(Y,true);if(ab){var Z=z(Y);if(Z&&typeof Z.SetVariable!=D){aa.success=true;aa.ref=Z;}ab(aa);}}}}}function z(aa){var X=null;
var Y=c(aa);if(Y&&Y.nodeName=="OBJECT"){if(typeof Y.SetVariable!=D){X=Y;}else{var Z=Y.getElementsByTagName(r)[0];if(Z){X=Z;}}}return X;}function A(){return !a&&F("6.0.65")&&(M.win||M.mac)&&!(M.wk&&M.wk<312);
}function P(aa,ab,X,Z){a=true;E=Z||null;B={success:false,id:X};var ae=c(X);if(ae){if(ae.nodeName=="OBJECT"){l=g(ae);Q=null;}else{l=ae;Q=X;}aa.id=R;if(typeof aa.width==D||(!/%$/.test(aa.width)&&parseInt(aa.width,10)<310)){aa.width="310";
}if(typeof aa.height==D||(!/%$/.test(aa.height)&&parseInt(aa.height,10)<137)){aa.height="137";}j.title=j.title.slice(0,47)+" - Flash Player Installation";
var ad=M.ie&&M.win?"ActiveX":"PlugIn",ac="MMredirectURL="+O.location.toString().replace(/&/g,"%26")+"&MMplayerType="+ad+"&MMdoctitle="+j.title;if(typeof ab.flashvars!=D){ab.flashvars+="&"+ac;
}else{ab.flashvars=ac;}if(M.ie&&M.win&&ae.readyState!=4){var Y=C("div");X+="SWFObjectNew";Y.setAttribute("id",X);ae.parentNode.insertBefore(Y,ae);ae.style.display="none";
(function(){if(ae.readyState==4){ae.parentNode.removeChild(ae);}else{setTimeout(arguments.callee,10);}})();}u(aa,ab,X);}}function p(Y){if(M.ie&&M.win&&Y.readyState!=4){var X=C("div");
Y.parentNode.insertBefore(X,Y);X.parentNode.replaceChild(g(Y),X);Y.style.display="none";(function(){if(Y.readyState==4){Y.parentNode.removeChild(Y);}else{setTimeout(arguments.callee,10);
}})();}else{Y.parentNode.replaceChild(g(Y),Y);}}function g(ab){var aa=C("div");if(M.win&&M.ie){aa.innerHTML=ab.innerHTML;}else{var Y=ab.getElementsByTagName(r)[0];
if(Y){var ad=Y.childNodes;if(ad){var X=ad.length;for(var Z=0;Z<X;Z++){if(!(ad[Z].nodeType==1&&ad[Z].nodeName=="PARAM")&&!(ad[Z].nodeType==8)){aa.appendChild(ad[Z].cloneNode(true));
}}}}}return aa;}function u(ai,ag,Y){var X,aa=c(Y);if(M.wk&&M.wk<312){return X;}if(aa){if(typeof ai.id==D){ai.id=Y;}if(M.ie&&M.win){var ah="";for(var ae in ai){if(ai[ae]!=Object.prototype[ae]){if(ae.toLowerCase()=="data"){ag.movie=ai[ae];
}else{if(ae.toLowerCase()=="styleclass"){ah+=' class="'+ai[ae]+'"';}else{if(ae.toLowerCase()!="classid"){ah+=" "+ae+'="'+ai[ae]+'"';}}}}}var af="";for(var ad in ag){if(ag[ad]!=Object.prototype[ad]){af+='<param name="'+ad+'" value="'+ag[ad]+'" />';
}}aa.outerHTML='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+ah+">"+af+"</object>";N[N.length]=ai.id;X=c(ai.id);}else{var Z=C(r);Z.setAttribute("type",q);
for(var ac in ai){if(ai[ac]!=Object.prototype[ac]){if(ac.toLowerCase()=="styleclass"){Z.setAttribute("class",ai[ac]);}else{if(ac.toLowerCase()!="classid"){Z.setAttribute(ac,ai[ac]);
}}}}for(var ab in ag){if(ag[ab]!=Object.prototype[ab]&&ab.toLowerCase()!="movie"){e(Z,ab,ag[ab]);}}aa.parentNode.replaceChild(Z,aa);X=Z;}}return X;}function e(Z,X,Y){var aa=C("param");
aa.setAttribute("name",X);aa.setAttribute("value",Y);Z.appendChild(aa);}function y(Y){var X=c(Y);if(X&&X.nodeName=="OBJECT"){if(M.ie&&M.win){X.style.display="none";
(function(){if(X.readyState==4){b(Y);}else{setTimeout(arguments.callee,10);}})();}else{X.parentNode.removeChild(X);}}}function b(Z){var Y=c(Z);if(Y){for(var X in Y){if(typeof Y[X]=="function"){Y[X]=null;
}}Y.parentNode.removeChild(Y);}}function c(Z){var X=null;try{X=j.getElementById(Z);}catch(Y){}return X;}function C(X){return j.createElement(X);}function i(Z,X,Y){Z.attachEvent(X,Y);
I[I.length]=[Z,X,Y];}function F(Z){var Y=M.pv,X=Z.split(".");X[0]=parseInt(X[0],10);X[1]=parseInt(X[1],10)||0;X[2]=parseInt(X[2],10)||0;return(Y[0]>X[0]||(Y[0]==X[0]&&Y[1]>X[1])||(Y[0]==X[0]&&Y[1]==X[1]&&Y[2]>=X[2]))?true:false;
}function v(ac,Y,ad,ab){if(M.ie&&M.mac){return;}var aa=j.getElementsByTagName("head")[0];if(!aa){return;}var X=(ad&&typeof ad=="string")?ad:"screen";if(ab){n=null;
G=null;}if(!n||G!=X){var Z=C("style");Z.setAttribute("type","text/css");Z.setAttribute("media",X);n=aa.appendChild(Z);if(M.ie&&M.win&&typeof j.styleSheets!=D&&j.styleSheets.length>0){n=j.styleSheets[j.styleSheets.length-1];
}G=X;}if(M.ie&&M.win){if(n&&typeof n.addRule==r){n.addRule(ac,Y);}}else{if(n&&typeof j.createTextNode!=D){n.appendChild(j.createTextNode(ac+" {"+Y+"}"));
}}}function w(Z,X){if(!m){return;}var Y=X?"visible":"hidden";if(J&&c(Z)){c(Z).style.visibility=Y;}else{v("#"+Z,"visibility:"+Y);}}function L(Y){var Z=/[\\\"<>\.;]/;
var X=Z.exec(Y)!=null;return X&&typeof encodeURIComponent!=D?encodeURIComponent(Y):Y;}var d=function(){if(M.ie&&M.win){window.attachEvent("onunload",function(){var ac=I.length;
for(var ab=0;ab<ac;ab++){I[ab][0].detachEvent(I[ab][1],I[ab][2]);}var Z=N.length;for(var aa=0;aa<Z;aa++){y(N[aa]);}for(var Y in M){M[Y]=null;}M=null;for(var X in swfobject){swfobject[X]=null;
}swfobject=null;});}}();return{registerObject:function(ab,X,aa,Z){if(M.w3&&ab&&X){var Y={};Y.id=ab;Y.swfVersion=X;Y.expressInstall=aa;Y.callbackFn=Z;o[o.length]=Y;
w(ab,false);}else{if(Z){Z({success:false,id:ab});}}},getObjectById:function(X){if(M.w3){return z(X);}},embedSWF:function(ab,ah,ae,ag,Y,aa,Z,ad,af,ac){var X={success:false,id:ah};
if(M.w3&&!(M.wk&&M.wk<312)&&ab&&ah&&ae&&ag&&Y){w(ah,false);K(function(){ae+="";ag+="";var aj={};if(af&&typeof af===r){for(var al in af){aj[al]=af[al];}}aj.data=ab;
aj.width=ae;aj.height=ag;var am={};if(ad&&typeof ad===r){for(var ak in ad){am[ak]=ad[ak];}}if(Z&&typeof Z===r){for(var ai in Z){if(typeof am.flashvars!=D){am.flashvars+="&"+ai+"="+Z[ai];
}else{am.flashvars=ai+"="+Z[ai];}}}if(F(Y)){var an=u(aj,am,ah);if(aj.id==ah){w(ah,true);}X.success=true;X.ref=an;}else{if(aa&&A()){aj.data=aa;P(aj,am,ah,ac);
return;}else{w(ah,true);}}if(ac){ac(X);}});}else{if(ac){ac(X);}}},switchOffAutoHideShow:function(){m=false;},ua:M,getFlashPlayerVersion:function(){return{major:M.pv[0],minor:M.pv[1],release:M.pv[2]};
},hasFlashPlayerVersion:F,createSWF:function(Z,Y,X){if(M.w3){return u(Z,Y,X);}else{return undefined;}},showExpressInstall:function(Z,aa,X,Y){if(M.w3&&A()){P(Z,aa,X,Y);
}},removeSWF:function(X){if(M.w3){y(X);}},createCSS:function(aa,Z,Y,X){if(M.w3){v(aa,Z,Y,X);}},addDomLoadEvent:K,addLoadEvent:s,getQueryParamValue:function(aa){var Z=j.location.search||j.location.hash;
if(Z){if(/\?/.test(Z)){Z=Z.split("?")[1];}if(aa==null){return L(Z);}var Y=Z.split("&");for(var X=0;X<Y.length;X++){if(Y[X].substring(0,Y[X].indexOf("="))==aa){return L(Y[X].substring((Y[X].indexOf("=")+1)));
}}}return"";},expressInstallCallback:function(){if(a){var X=c(R);if(X&&l){X.parentNode.replaceChild(l,X);if(Q){w(Q,true);if(M.ie&&M.win){l.style.display="block";
}}if(E){E(B);}}a=false;}}};}();

/*
insertAtCaret
Released under the MIT License <http://www.opensource.org/licenses/mit-license.php> 
*/

(function($){
	$.fn.extend({
		insertAtCaret: function(myValue){
			var $t=$(this)[0];
			if (document.selection) {
				this.focus();
				sel = document.selection.createRange();
				sel.text = myValue;
				this.focus();
			}
			else 
				if ($t.selectionStart || $t.selectionStart == '0') {
					var startPos = $t.selectionStart;
					var endPos = $t.selectionEnd;
					var scrollTop = $t.scrollTop;
					$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
					this.focus();
					$t.selectionStart = startPos + myValue.length;
					$t.selectionEnd = startPos + myValue.length;
					$t.scrollTop = scrollTop;
				}
				else {
					this.value += myValue;
					this.focus();
				}
		}
	})	
})(jQuery);

/*
voicewo
Copyright (c) 2014 yufan
Email yufan1029@qq.com
Released under the MIT License <http://www.opensource.org/licenses/mit-license.php> 
*/

(function($) {
	
	//flash DOM
	function thisMovie(movieName) {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			return window[movieName];
		}
		else {
			return document[movieName];
		}
	}
	
	//version
	var version = '0.1';
	
	//language pack	
	var lang = {
		startspeak			: '请开始说话',
		nospeak				: '没有听到说话',
		nettimeout			: '网络连接超时',
		flashdisablemic		: 'Flash禁用麦克风',
		nomic				: '没有麦克风设备',
		wait				: '请稍候',
		cancel				: '取消',
		ok					: '确定',
		retry				: '重试',
		clouderror			: '云端错误'
	}
	
	// These methods can be called by adding them as the first argument in the voicewo plugin call	
	var methods = {
		
		init : function(options) {
			
			return this.each(function() {
				
				// Create a reference to the jQuery DOM object
				var $this = $(this);
				
				// Clone the original DOM object
				var $clone = $this.clone();
				
				// Setup the default options
				var settings = $.extend({
					// Options
					id       		: $this.attr('id'),		// The ID of the DOM object
					buttonImage 	: 'btn.png',  			// The path to an image to use for the Flash browse button					
					swf      		: 'voicewo.swf',  		// The path to the voicewo SWF file
					outputId		: 'content',			// The ID of the DOM object to output for voicewo result
					width			: 92,					// The width of the browse button
					height			: 25,					// The height of the browse button	
					preventCaching  : true,					// Adds a random value to the Flash URL to prevent caching of it (conflicts with existing parameters)
					debug           : false,              	// Turn on voicewo debugging mode
					tipPositon		: 'bottom',				// The positon of the tip for the Flash browse button
					
					// Events
					overrideEvents  : []             		// (Array) A list of default event handlers to skip
					/*
					onInit									// Triggered when init
					onFallback								// Triggered when fallback
					onTip									// Triggered when tip
					onRecording								// Triggered when recording
					onOutput         						// Triggered when output
					onRestart								// Triggered when restart
					onCancel								// Triggered when cancel
					onSwfready								// Triggered when swfready
					onDestroy								// Triggered when destroy
					*/					
				}, options);
				
				// Detect if Flash is available
				var playerVersion  = swfobject.getFlashPlayerVersion();
				var flashInstalled = (playerVersion.major >= 9);
				
				if (flashInstalled) {
					
					// Add the settings to the elements data object
					$this.data('voicewo', settings);
					
					// The wrapper
					var $wrapper = $('#' + settings.id);
					$wrapper.css({'position': 'relative', 'width': settings.width, 'height': settings.height});
					
					// Create the swf
					var $swf = $('<span />', {'id': settings.id + '-swf'});
					$wrapper.append($swf);
					
					// Update the flash url if needed
					if (settings.preventCaching) {
						settings.swf = settings.swf + (settings.swf.indexOf("?") < 0 ? "?" : "&") + "preventswfcaching=" + new Date().getTime();
					}
					swfobject.embedSWF(settings.swf, settings.id + '-swf', settings.width, settings.height, '9.0.0', null, {id: settings.id, debug: settings.debug}, {wmode: 'transparent', menu: 'true', allowScriptAccess: 'always', quality: 'high'}, {id: settings.id + '-swf', name: settings.id + '-swf', styleclass: settings.id + '-swf', style: 'position: absolute; z-index:1'});
					$swf = $('#' + settings.id + '-swf');
					
					// Create the button
					var $button = $('<div />', {
						'id'    : settings.id + '-button',
						'css'   : {
									'width'    			: settings.width + 'px',							
									'height'   			: settings.height + 'px',
									'line-height' 		: settings.height + 'px',
									'background-image' 	: "url('" + settings.buttonImage + "')"
								  }						
					});
					$wrapper.append($button);
					$button = $('#' + settings.id + '-button');
					
					//Create the tip
					var $tip = '<div id="' + settings.id + '-vc-tip" class="vc-tip vc-tip-' + settings.tipPositon + '"><span class="vc-tip-arrow"><em>◆</em><i>◆</i></span><div class="vc-tip-content"></div></div>';
					$wrapper.append($tip);
					$tip = $('#' + settings.id + '-vc-tip');
					
					//Hide all tip if not click the tip
					$(document).click(function(event){
						if($(event.target).closest('#' + settings.id).length <= 0) {
							$('#' + settings.id).voicewo('cancel');
						}
					});
					
					// Call the user-defined init event handler
					if (settings.onInit) settings.onInit.call(this);
					
				} else {

					// Call the fallback function
					if (settings.onFallback) settings.onFallback.call(this);

				}				
			});
			
		},
			
		// tip
		tip : function(type, code) {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
					
				// Call the default event handler
				if ($.inArray('onTip', settings.overrideEvents) < 0) {
					var html;
					switch (type) {
						case 'recording':
							html = '<div class="vc-tip-content-recording"><div class="vc-tip-content-recording-width"></div></div><div class="vc-tip-content-title">' + lang.startspeak + '</div><div class="vc-tip-content-btn-box"><a class="vc-tip-content-btn vc-tip-content-btn-cancel" href="javascript:$(\'#'+settings.id+'\').voicewo(\'cancel\');">' + lang.cancel + '</a></div>';
							break;
						case 'recoging':
							html = '<div class="vc-tip-content-recoging"></div><div class="vc-tip-content-title">' + lang.wait + '</div><div class="vc-tip-content-btn-box"><a class="vc-tip-content-btn vc-tip-content-btn-cancel" href="javascript:$(\'#'+settings.id+'\').voicewo(\'cancel\');">' + lang.cancel + '</a></div>';
							break;
						case 'error':
							html = '<div class="vc-tip-content-title">' + lang[code] + '</div><div class="vc-tip-content-btn-box"><a class="vc-tip-content-btn vc-tip-content-btn-cancel" href="javascript:$(\'#'+settings.id+'\').voicewo(\'restart\');">' + lang.retry + '</a></div>';					
							break;
						case 'error-debug':
							html = '<div class="vc-tip-content-title">' + code + '</div><div class="vc-tip-content-btn-box"><a class="vc-tip-content-btn vc-tip-content-btn-cancel" href="javascript:$(\'#'+settings.id+'\').voicewo(\'restart\');">' + lang.retry + '</a></div>';					
							break;												
						case 'init':
							$this.find('.vc-tip').hide();
							$this.find('.vc-tip-content').html('');
							return true;
							break;						
					}
					$this.find('.vc-tip-content').html(html);
					
					var wrapper_h = $this.outerHeight(true);
					var wrapper_w = $this.outerWidth(true);
					var tip = $this.find('.vc-tip');
					var tip_h = $this.find('.vc-tip').outerHeight(true);
					var tip_w = $this.find('.vc-tip').outerWidth(true);
					
					if($this.find('.vc-tip').hasClass('vc-tip-top')) {
						tip.css({
							'bottom' : (wrapper_h+6)+'px',
							'left'	 : parseInt((wrapper_w-tip_w)/2)+'px'
						});
					}else if($this.find('.vc-tip').hasClass('vc-tip-bottom')) {
						tip.css({
							'top'	 : (wrapper_h+6)+'px',
							'left'	 : parseInt((wrapper_w-tip_w)/2)+'px'
						});
					}else if($this.find('.vc-tip').hasClass('vc-tip-left')) {
						tip.css({
							'right'	 : (wrapper_w+6)+'px',
							'top'	 : parseInt((wrapper_h-tip_h)/2)+'px'
						});
					}else if($this.find('.vc-tip').hasClass('vc-tip-right')) {
						tip.css({
							'left'	 : (wrapper_w+6)+'px',
							'top'	 : parseInt((wrapper_h-tip_h)/2)+'px'
						});
					}
					tip.show();
				}
				
				// Call the user-defined event handler
				if (settings.onTip) settings.onTip.call(this, type, code);				
				
				delete settings;				
				return true;
			});	
					
		},
		
		// recording
		recording : function(value) {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
					
				// Call the default event handler
				if ($.inArray('onRecording', settings.overrideEvents) < 0) {
					value = value * 2;
					(value > 100) ? value = 100 : value = parseInt(value);
					$this.find('.vc-tip-content-recording-width').animate({'width': value+'%'}, 20);
				}
				
				// Call the user-defined event handler
				if (settings.onRecording) settings.onRecording.call(this, value);

				delete settings;				
				return true;
			});
			
		},
		
		// output
		output : function(value) {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
				
				// Call the default event handler
				if ($.inArray('onOutput', settings.overrideEvents) < 0) {
					$('#' + settings.outputId).insertAtCaret(value);
				}
				
				// Call the user-defined event handler
				if (settings.onOutput) settings.onOutput.call(this, value);
				
				delete settings;				
				return true;
			});
			
		},		
		
		// restart
		restart : function() {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
				
				// Call the default event handler
				if ($.inArray('onRestart', settings.overrideEvents) < 0) {
					//Call flash restart interface
					if($('#' + settings.id).data('swfready')=='true') thisMovie(settings.id + '-swf').doRestart();
				}
				
				// Call the user-defined event handler
				if (settings.onRestart) settings.onRestart.call(this);

				delete settings;
				return true;				
			});

		},		
		
		// cancel
		cancel : function() {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
					
				// Call the default event handler
				if ($.inArray('onCancel', settings.overrideEvents) < 0) {
					//Call flash cancel interface
					if($('#' + settings.id).data('swfready')=='true') thisMovie(settings.id + '-swf').doCancel();
					
					//Init tip
					$('#' + settings.id).voicewo('tip','init','');
				}
				
				// Call the user-defined event handler
				if (settings.onCancel) settings.onCancel.call(this);
				
				delete settings;
				return true;
			});

		},
		
		// swfready
		swfready : function() {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
					
				// Call the default event handler
				if ($.inArray('onSwfready', settings.overrideEvents) < 0) {
					// set data
					$('#' + settings.id).data('swfready','true');
				}
				
				// Call the user-defined event handler
				if (settings.onSwfready) settings.onSwfready.call(this);
				
				delete settings;
				return true;				
			});

		},		
		
		// Revert the DOM object back to its original state
		destroy : function() {
			
			this.each(function() {
				// Create a reference to the jQuery DOM object
				var $this        = $(this),
					settings     = $this.data('voicewo');
					
				// Call the default event handler
				if ($.inArray('onDestroy', settings.overrideEvents) < 0) {
					$('#' + settings.id).find('.vc-tip').remove();
					$('#' + settings.id).find('.btn-swf').remove();
					$this.removeData('voicewo');
					$this.removeData('swfready');
				}

				// Call the user-defined event handler
				if (settings.onDestroy) settings.onDestroy.call(this);
				
				delete settings;
				return true;				
			});

		}		
		
	}
	
	$.fn.voicewo = function(method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('The method ' + method + ' does not exist in $.voicewo');
		}

	}	
	
})(jQuery);	