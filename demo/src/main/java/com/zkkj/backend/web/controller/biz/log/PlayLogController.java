package com.zkkj.backend.web.controller.biz.log;

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
import com.zkkj.backend.common.util.ReflectUtil;
import com.zkkj.program.service.IDeviceService;
import com.zkkj.program.service.IPlayLogService;
import com.zkkj.program.service.IProgramService;
import com.zkkj.program.service.IResourceService;
import com.zkkj.program.service.impl.DeviceServiceImpl;
import com.zkkj.program.service.impl.PlayLogServiceImpl;
import com.zkkj.program.service.impl.ProgramServiceImpl;
import com.zkkj.program.service.impl.ResourceServiceImpl;
import com.zkkj.program.util.Page;
import com.zkkj.program.util.ParamsUtil;

@Controller
@RequestMapping("playLog")
public class PlayLogController {
	
	IPlayLogService playLogService = new PlayLogServiceImpl();
	IDeviceService deviceService = new DeviceServiceImpl();
	IProgramService programService = new ProgramServiceImpl();
	IResourceService resourceService = new ResourceServiceImpl();
	
	/**
	 * 播放日志列表页面初始化
	 * 
	 */
	@RequestMapping("getPlayLogInit")
	public ModelAndView advertListInit(ModelAndView model,
			HttpServletRequest request, HttpServletResponse response) {
		model.setViewName("log/log");
		return model;
	}
	
	/**
	 * 查询用户列表json
	 * @throws DataBaseException 
	 */
	@RequestMapping("playLogList")
	@ResponseBody
	public void userList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, DataBaseException {
		Map resultMap = new HashMap();
		try {
			Map params = ReflectUtil.transToMAP(request.getParameterMap());
			int  pageNo = Integer.parseInt(ParamsUtil.nullDeal(params, "page", "1"));
			int  pageSize = Integer.parseInt(ParamsUtil.nullDeal(params, "rows", "10"));
			Page page = new Page();
			page.setPageNum(pageNo);
			page.setNumPerPage(pageSize);
			
			List<Map> resultList = playLogService.getPlayLogList(params, page);
			resultMap.put("rows", resultList != null && resultList.size() > 0 ? resultList : Collections.EMPTY_LIST);
			resultMap.put("total", playLogService.getPlayLogCount(null));
		} catch (Exception e) {
			resultMap.put("result", "0");
			resultMap.put("message", e.getMessage());
			System.out.println(e.getMessage());
			//发生异常存储到异常表
			DataBaseException dataBaseException=new DataBaseException();	
			dataBaseException.setExceptionCode("SE_DB_ERROR");
			dataBaseException.setStackTrace(e.getStackTrace());
			throw dataBaseException;
		} finally {
			response.getOutputStream().write(
					GsonUtil.getGson().toJson(resultMap).getBytes("UTF-8"));
		}
	}
	
}
