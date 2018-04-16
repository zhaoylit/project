package com.zkkj.backend.web.controller.biz.flight;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.zkkj.backend.web.controller.BaseController;

@Controller
@RequestMapping("flight")
public class FlightsController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(FlightsController.class);

	/**
	 * 导入航班 
	 * @param file
	 * @param view
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="flightImport",method=RequestMethod.POST)
	public ModelAndView flightImport(@RequestParam(value="airportFile")MultipartFile file,ModelAndView view,HttpServletRequest request,HttpServletResponse response){
		if(file!=null){
		   view.setViewName(null);
		}
		
		String airportFile = file.getOriginalFilename();
		
		long size = file.getSize();
		
		if(airportFile==null||("").equals(airportFile)||size==0){
	       view.setViewName(null);
		}
	    return view;
	}
	
	
}
