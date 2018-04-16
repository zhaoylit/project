package com.zkkj.backend.web.controller.biz.index;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zkkj.backend.common.exception.DataBaseException;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.program.service.IUserBarcodeService;
import com.zkkj.program.service.impl.UserBarcodeServiceImpl;
import com.zkkj.program.util.Page;

@Controller
public class IndexController {
	IUserBarcodeService userBarcodeService = new UserBarcodeServiceImpl();
	@RequestMapping("initDemo")
	public ModelAndView initDemo(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		//查询进出站人数
		try {
			Map countMap = userBarcodeService.selectCount(null);
			if(countMap != null){
				int inn = Integer.parseInt(ParamsUtil.nullDeal(countMap, "inn", "0"));
        		int outt = Integer.parseInt(ParamsUtil.nullDeal(countMap, "outt", "0"));
        		model.addObject("inn", inn);
        		model.addObject("outt", outt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("---------------" + e.getMessage());
		}
		model.setViewName("statis/demo"); 
		return model;
	}
	//	查询进出站乘客列表
	@RequestMapping("getInnOrOutPersonJson")
	@ResponseBody
	public void getInnOrOutPersonJson(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			List<Map> resultList = userBarcodeService.selectBarcodeList(params);
			
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
}
