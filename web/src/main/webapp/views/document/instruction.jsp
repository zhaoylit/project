<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div style="width:90%;margin:10px 0 0 10px;font-size:12px;">
		<table>
			<tr>
				<td style="width:200px;">请求地址：</td>
				<td>http://101.200.61.19:8090/api/</td>
			</tr>
			<tr>
				<td>示例接口（首页轮播图）名称：</td>
				<td>index/getCarouselFigure.do</td>
			</tr>
			<tr>
				<td>请求参数为json格式</td>
				<td>参数名称为params</td>
			</tr>
			<tr>
				<td>参数：</td>
				<td>
					<table>
						<tr>
							<td>ip：</td>
							<td>请求地址</td>
						</tr>
						<tr>
							<td>version：</td>
							<td>版本号</td>
						</tr>
						<tr>
							<td>osType：</td>
							<td>操作系统，android/ios</td>
						</tr>
						<tr>
							<td>mobile：</td>
							<td>手机型号，例如：sunsang</td>
						</tr>
						<tr>
							<td>data：</td>
							<td>
								<table>
									<tr>
										<td>app_key：</td>
										<td>密钥key</td>
									</tr>
									<tr>
										<td>timestamp：</td>
										<td>当前时间戳，格式：1499505706973</td>
									</tr>
									<tr>
										<td>sign：</td>
										<td>由app_key,app_secret,timestamp和其他参数通过签名方法生成的字符串</td>
									</tr>
									<tr>
										<td>platform：</td>
										<td>android</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>完整请求路径：</td>
				<td>http://101.200.61.19:8090/api/index/getCarouselFigure.do?params={"ip":"192.168.0.1","version":"1.0","osType":"android","mobile":"sansungG9200","data":{"app_key":"21242797912","sign":"3C64615D8711DB062425A2F9C03BFD3B","timestamp":"1499505706973"}}
				</td>
			</tr>
			<tr>
				<td>返回结果：</td>
				<td>
					<table>
						<tr>
							<td>请求成功：code=200</td>
							<td>
								<textarea style="width:700px;height:200px;padding:0px;font-size:12px;">
{
    "code": 200,
    "data": {
        "app_key": "21242797912",
        "sign": "FDB0838CB034B9C6E850B37146EAAFE1",
        "timestamp": "1501429276881",
        "content": [
            {
                "id": 10,
                "platform": "all",
                "addedTime": "2017-07-30 10:10:00",
                "order": 2,
                "status": 1,
                "imageUrl": "http://101.200.61.19:8080/lunbotu/1500649016046.jpg",
                "clickUrl": "www.baidu.com"
            }
        ]
    },
    "ip": "",
    "mobile": "",
    "msg": "OK",
    "osType": "",
    "version": "1.0"
}
								</textarea>
							</td>
						</tr>
						<tr>
							<td>请求失败：code<>200</td>
							<td>
								code=500：系统错误</br>
								code=201：非法请求</br>
								code=202：缺少必要参数</br>
								code=203：签名错误</br>
								code=204：请求超时</br>
								code=205：app_key错误</br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					签名工具类：
				</td>
				<td>
					<textarea style="width:700px;height:600px;padding:0px;font-size:12px;">
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.security.GeneralSecurityException;  
import java.security.MessageDigest;  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Date;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
/** 
 * 签名工具类 
 * 
 */  
public class ParamSignUtils {  
      
    public static void main(String[] args)  
    {  
        HashMap<String, String> signMap = new HashMap<String, String>();  
        signMap.put("a","01");  
        signMap.put("c","02");  
        signMap.put("b","03");  
        signMap.put("timestamp","1499151036573");
        
        String secret="test";  
        HashMap SignHashMap=ParamSignUtils.sign(signMap, secret);  
        System.out.println("SignHashMap:"+SignHashMap);  
        
        
        List<String> ignoreParamNames=new ArrayList<String>();  
        ignoreParamNames.add("a");  
        HashMap SignHashMap2=ParamSignUtils.sign(signMap,ignoreParamNames, secret);
        System.out.println("SignHashMap2:"+SignHashMap2);  
    }  
  
    public static HashMap<String, String> sign(Map<String, String> paramValues,  
            String secret) {  
        return sign(paramValues, null, secret);  
    }  
  
    /** 
     * @param paramValues 
     * @param ignoreParamNames 
     * @param secret 
     * @return 
     */  
    public static HashMap<String, String> sign(Map<String, String> paramValues,  
            List<String> ignoreParamNames, String secret) {  
        try {  
            HashMap<String, String> signMap = new HashMap<String, String>();  
            StringBuilder sb = new StringBuilder();  
            List<String> paramNames = new ArrayList<String>(paramValues.size());  
            paramNames.addAll(paramValues.keySet());  
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {  
                for (String ignoreParamName : ignoreParamNames) {  
                    paramNames.remove(ignoreParamName);  
                }  
            }  
            Collections.sort(paramNames);  
            sb.append(secret);  
            for (String paramName : paramNames) {  
                sb.append(paramName).append(paramValues.get(paramName));  
            }  
            sb.append(secret);  
            byte[] md5Digest = getMD5Digest(sb.toString());  
            String sign = byte2hex(md5Digest);  
            signMap.put("appParam", sb.toString());  
            signMap.put("appSign", sign);  
            return signMap;  
        } catch (IOException e) {  
            throw new RuntimeException("加密签名计算错误", e);  
        }  
          
    }  
  
    public static String utf8Encoding(String value, String sourceCharsetName) {  
        try {  
            return new String(value.getBytes(sourceCharsetName), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            throw new IllegalArgumentException(e);  
        }  
    }  
  
    private static byte[] getSHA1Digest(String data) throws IOException {  
        byte[] bytes = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("SHA-1");  
            bytes = md.digest(data.getBytes("UTF-8"));  
        } catch (GeneralSecurityException gse) {  
            throw new IOException(gse);  
        }  
        return bytes;  
    }  
  
    private static byte[] getMD5Digest(String data) throws IOException {  
        byte[] bytes = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            bytes = md.digest(data.getBytes("UTF-8"));  
        } catch (GeneralSecurityException gse) {  
            throw new IOException(gse);  
        }  
        return bytes;  
    }  
  
  
    private static String byte2hex(byte[] bytes) {  
        StringBuilder sign = new StringBuilder();  
        for (int i = 0; i < bytes.length; i++) {  
            String hex = Integer.toHexString(bytes[i] & 0xFF);  
            if (hex.length() == 1) {  
                sign.append("0");  
            }  
            sign.append(hex.toUpperCase());  
        }  
        return sign.toString();  
    }  
  
}
					</textarea>  
				</td>
			</tr>
		</table>
	</div>
</body>
</html>