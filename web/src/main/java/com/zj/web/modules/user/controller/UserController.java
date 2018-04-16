package com.zj.web.modules.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.common.utils.ReflectUtil;
import com.zj.web.modules.user.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private IUserService userService;
	/**
	 * 列表初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectListInit")
	public ModelAndView userList(ModelAndView m,HttpServletRequest request,HttpServletResponse response){
		m.setViewName("/user/userList");
		return m;
	}
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/selectList")
	@ResponseBody
	public void selectList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			Integer pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			Integer pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setNumPerPage(pageSize);
			page.setPageNum(pageNo);
			List<Map> selectList = userService.selectList(params, page);
			resultMap.put("rows", selectList);
			resultMap.put("total", userService.selectCount(params));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("--------------------------" + e.getMessage());
		}finally{
			response.getOutputStream().write(JSONObject.fromObject(resultMap).toString().getBytes());
		}
	}
}
