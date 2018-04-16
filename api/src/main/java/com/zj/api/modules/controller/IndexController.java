package com.zj.api.modules.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zj.api.common.utils.CodeEnum;
import com.zj.api.common.utils.CommonsUtil;
import com.zj.api.common.utils.JacksonUtil;
import com.zj.api.common.utils.JsonInput;
import com.zj.api.common.utils.ReflectUtil;
import com.zj.api.modules.service.IndexService;

/**
 *   收额 轮播 噶婚礼
 *   
 * @author zyl
 *
 */
@Controller
@RequestMapping(value="index")
public class IndexController {
	@Autowired
	private IndexService indexService;
	
	/**
	 * 查询首页轮播图信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="getCarouselFigure")
	public void getCarouselFigure(JsonInput input,HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map> corouseFigureList = null;
		try {
			input = JacksonUtil.readValue(request.getParameter("params"), JsonInput.class);
			Map params = input.getData();
			corouseFigureList = indexService.getCarouselFigure(params);
			input.getData().put("content", corouseFigureList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			corouseFigureList = Collections.EMPTY_LIST;
			input.setCode(CodeEnum.SYSTEM_ERROR.getCode());
			input.setMsg(CodeEnum.SYSTEM_ERROR.getMsg());
		}finally{
			
			response.getOutputStream().write(JSONObject.fromObject(input).toString().getBytes(Charset.forName("GBK")));
		}
	}
}