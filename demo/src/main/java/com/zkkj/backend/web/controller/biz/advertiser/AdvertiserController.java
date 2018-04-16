
package com.zkkj.backend.web.controller.biz.advertiser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.zkkj.backend.common.exception.DeviceException;
import com.zkkj.backend.common.util.GsonUtil;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.program.service.IAdvertiserService;
import com.zkkj.program.service.impl.AdvertiserServiceImpl;
import com.zkkj.program.util.Page;

@Controller
@RequestMapping("advertiser")
public class AdvertiserController {
	
	private IAdvertiserService service = new AdvertiserServiceImpl();
	
	@RequestMapping("advertiserListInit")
	public ModelAndView advertiserListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("advertiser/advertiserList");
		return model;
	}
	/**
	 * 查询广告列表json 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("advertiserList")
	@ResponseBody
	public void advertiserList(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		Map resultMap = new HashMap();	
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			List<Map> resultList = service.getAdvertiserList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", service.getAdvertiserListCount(params));
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("result", "0");
			resultMap.put("message",e.getMessage());
			System.out.println(e.getMessage());
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
			
			
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 查询广告列表json 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws DataBaseException 
	 */
	@RequestMapping("getAdvertiserJson")
	@ResponseBody
	public void getAdvertiserJson(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		List<Map> resultList = new ArrayList<Map>();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			List<Map> list = service.getAdvertiserList(params);
			resultList = list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			resultList = Collections.EMPTY_LIST;
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultList).getBytes("UTF-8"));
		}
	}
	
	/**
	 * 添加或者修改广告信息初始化
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addOrEditAdvertiserInit")
	public ModelAndView addOrEditInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String id = ParamsUtil.nullDeal(params, "id", "");
		if(!"".equals(id)){
			//查询单条广告商的数据
            model.addObject("resultInfo",service.getAdvertiserById(params));
		}
		model.setViewName("advertiser/advertiserEdit");
		return model;
	}
	/**
	 * 添加删除广告商
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws DataBaseException 
	 */
	@RequestMapping("addOrEditAdvertiser")
	@ResponseBody
	public void addOrEditAdvertiseriser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException, DataBaseException{
		
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		Map resultMap = new HashMap();
        try{
			int count = service.addOrEditAdvetiser(params);
			resultMap.put("result", count > 0 ? 1 : 0);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
			
			DataBaseException dataBaseException=new DataBaseException();
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	/**
	 * 删除广告
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("deleteAdvertiser")
	@ResponseBody
	public void deleteAdvertiser(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String ids = ParamsUtil.nullDeal(params, "ids", "");
		Map resultMap = new HashMap();
		try {
			if(!"".equals(ids)){
				List<String> list = new ArrayList<String>();
				String []array = ids.split(",");
				for(String ss : array){
					list.add(ss);
				}
				int count = service.deleteAdvertiser(list);
				resultMap.put("result",count > 0 ? 1 : 0);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result","0");
			resultMap.put("message", e.getMessage());
		}finally{
			response.getOutputStream().write(GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
}
