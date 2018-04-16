package com.zj.web.modules.goods.controller;

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
import com.zj.web.modules.goods.service.IGoodsService;
import com.zj.web.modules.goods.service.IGoodsServicesService;
import com.zj.web.modules.shop.service.IShopService;

@Controller
@RequestMapping(value="goods")
public class GoodsController {
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsServicesService goodsServicesService;
	@Autowired
	private IShopService shopService;
	@RequestMapping(value="selectListInit")
	public ModelAndView selectListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("goods/goodsList");
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
			List<Map> selectList = goodsService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", goodsService.selectCount(params));
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
			int rowCount = goodsService.insert(params);
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
			int rowCount = goodsService.deleteByPrimaryKey(params);
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
				Map oneMap = goodsService.selectOne(params);
				model.addObject("data", oneMap);
			}
			//查询商品服务信息
			Map queryMap = new HashMap();
			queryMap.put("goodsId", id);
			List<Map> goodsServicesList = goodsServicesService.selectList(queryMap);	
			model.addObject("goodsServices",goodsServicesList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("------------------------------" + e.getMessage());
		}
		model.setViewName("goods/goodsEdit");
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
				rowCount = goodsService.updateByPrimaryKeySelective(params);
			}else{
				rowCount = goodsService.insert(params);
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
	@RequestMapping(value="selectShopListInit")
	public ModelAndView selectBusinessListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("goods/shopList");
		return model;
	}
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="selectShopList")
	public void selectShopList(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
}
