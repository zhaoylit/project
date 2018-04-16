package com.zj.web.modules.shop.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.shop.service.IShopService;
import com.zj.web.modules.user.service.IUserService;

/**
 * 商家管理
 * 
 * @author zyl
 *
 */
@Controller
@RequestMapping(value="shop")
public class ShopController {
	private static final Logger log = LoggerFactory.getLogger(ShopController.class);
	@Autowired
	private IShopService shopService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="selectListInit")
	public ModelAndView selectListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("shop/shopList");
		return model;
	}
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="selectList")
	public void selectList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			Integer pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			Integer pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setNumPerPage(pageSize);
			page.setPageNum(pageNo);
			List<Map> selectList = shopService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", shopService.selectCount(params));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg",e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes(Charset.forName("UTF-8")));
		}
	}
	@RequestMapping("insert")
	public void insert(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int rowCount = shopService.insert(params);
			if(rowCount == 0){
				resultMap.put("result", "0");
				resultMap.put("message","操作失败");
				return;
			}
			resultMap.put("result", "1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("--------------------------------" + e.getMessage());
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	}
	@RequestMapping("deleteByPrimaryKey")
	public void deleteByPrimaryKey(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int rowCount = shopService.deleteByPrimaryKey(params);
			if(rowCount == 0){
				resultMap.put("result", "0");
				resultMap.put("message","操作失败");
				return;
			}
			resultMap.put("result", "1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("--------------------------------" + e.getMessage());
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	}
	
	@RequestMapping("addOrEditInit")
	public ModelAndView addOrEditInit(ModelAndView model ,HttpServletRequest request,HttpServletResponse response){
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String id  = ParamsUtil.nullDeal(params, "id", "");
			if(!"".equals(id)){
				//查询编辑的信息
				Map oneMap = shopService.selectOne(params);
				model.addObject("data", oneMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("------------------------------" + e.getMessage());
		}
		model.setViewName("shop/shopEdit");
		return model;
	}
	@RequestMapping("addOrEdit")
	public void addOrEdit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			String id = ParamsUtil.nullDeal(params, "id", "");
			//处理复选框
			String service[] = request.getParameterValues("service");
			if(service != null && service.length  > 0){
				params.put("service", StringUtils.join(service, ","));
			}
			int rowCount = 0;
			if(!"".equals(id)){
				//更新
				rowCount = shopService.updateByPrimaryKeySelective(params);
			}else{
				rowCount = shopService.insert(params);
			}
			if(rowCount == 0){
				resultMap.put("result", "0");
				resultMap.put("message","操作失败");
				return;
			}
			resultMap.put("result", "1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("--------------------------------" + e.getMessage());
			resultMap.put("result", "0");
			resultMap.put("message", "系统异常");
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	}
	@RequestMapping(value="selectBusinessListInit")
	public ModelAndView selectBusinessListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("shop/businessList");
		return model;
	}
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="selectBusinessList")
	public void selectBusinessList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			Integer pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			Integer pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setNumPerPage(pageSize);
			page.setPageNum(pageNo);
			params.put("userType", "2");
			List<Map> selectList = userService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", userService.selectCount(params));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("result", "0");
			resultMap.put("msg",e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes(Charset.forName("UTF-8")));
		}
	}
}
