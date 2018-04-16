package com.zj.web.modules.goods.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.JsonUtil;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.goods.service.IGoodsCategoryService;

/**
 * 商品类目管理
 * @author zyl
 * @date 2017-01-27
 *
 */
@Controller
@RequestMapping("goodsCategory")
public class GoodsCategoryController {
	private static final Logger log = LoggerFactory.getLogger(GoodsCategoryController.class);
	@Autowired
	private IGoodsCategoryService goodsCategoryService;
	@RequestMapping(value="selectListInit")
	public ModelAndView selectListInit(ModelAndView model,HttpServletRequest request,HttpServletResponse response){
		model.setViewName("goods/goodsCategoryList");
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
			List<Map> selectList = goodsCategoryService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", goodsCategoryService.selectCount(params));
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
			int rowCount = goodsCategoryService.insert(params);
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
			int rowCount = goodsCategoryService.deleteByPrimaryKey(params);
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
				Map oneMap = goodsCategoryService.selectOne(params);
				model.addObject("resultInfo", oneMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("------------------------------" + e.getMessage());
		}
		model.setViewName("goods/goodsCategoryEdit");
		return model;
	}
	@RequestMapping("addOrEdit")
	@ResponseBody
	public String addOrEdit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String returnJson = "";
		try {
			returnJson = goodsCategoryService.addOrEdit(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("addOrEdit********************************"+e.getMessage());
			returnJson = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return returnJson;
		}
	}
	/**
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getGoodsCategoryTreeJson")
	public String getGoodsCategoryTreeJson(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String treeJson = "";
		try {
			//查询所有的菜单
			treeJson = goodsCategoryService.getGoodsCategoryTreeJson(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getMenuTreeJson********************************"+e.getMessage());
		}finally{
			return treeJson;
		}
	} 
	/**
	 * 查询当前节点和下一子节点
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("getCurAndNextOneNode")
	public String getCurAndNextOneNode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			json = goodsCategoryService.getCurAndNextOneNode(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("getCurAndNextOneNode********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 
	/**
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("deleteAllChildrenNodebyId")
	public String deleteAllChildrenNodebyId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map params = ReflectUtil.transToMAP(request.getParameterMap());
		String json = "";
		try {
			json = goodsCategoryService.deleteAllChildrenNodebyId(params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("deleteAllChildrenNodebyId********************************"+e.getMessage());
			json = JsonUtil.getResultStatusJson("0", e.getMessage());
		}finally{
			return json;
		}
	} 
}
