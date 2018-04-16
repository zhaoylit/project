package com.zj.web.modules.content.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.modules.content.dao.CarouselFigureMapper;
import com.zj.web.modules.content.dao.IconFunMapper;
import com.zj.web.modules.content.service.ICarouselFigureService;
import com.zj.web.modules.content.service.IIconFunService;


@Service("iIconFunService")
public class IconFunServiceImpl implements IIconFunService {
	@Autowired
	private IconFunMapper iconFunMapper;
	private static final String RETURN_PATH = (String) CustomizedPropertyConfigurer.getContextProperty("return_path");

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		//获取资源访问路径
		List<Map> selectList = iconFunMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
		for(Map mm : selectList){
			String iamgeUrl = ParamsUtil.nullDeal(mm, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				mm.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return selectList;
	}

	@Override
	public List<Map> selectList(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> selectList = iconFunMapper.selectList(params);
		for(Map mm : selectList){
			String iamgeUrl = ParamsUtil.nullDeal(mm, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				mm.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return selectList;
	}


	@Override
	public Map selectOne(Map params) throws Exception {
		// TODO Auto-generated method stub
		Map oneMap = iconFunMapper.selectOne(params);
		if(oneMap != null){
			String iamgeUrl = ParamsUtil.nullDeal(oneMap, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				oneMap.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return iconFunMapper.selectOne(params);
	}
	
	@Override
	public int insert(Map params) throws Exception {
		// TODO Auto-generated method stub
		return iconFunMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) throws Exception {
		// TODO Auto-generated method stub
		return iconFunMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public int deleteByPrimaryKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids = ParamsUtil.nullDeal(params, "ids","");
		if("".equals(ids)){
			return 0;
		}
		String []idsArray = ids.split(",");
		return iconFunMapper.deleteByPrimaryKey(idsArray);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return iconFunMapper.selectCount(params);
	}

	@Override
	public int updateSort(Map params) throws Exception {
		// TODO Auto-generated method stub
		int rowCount = 0;
		String ids = ParamsUtil.nullDeal(params, "ids","");
		String idsArray[] = ids.split(",");
		for(int i = 0;i < idsArray.length ;i++){
			Map updateMap = new HashMap();
			updateMap.put("id", idsArray[i]);
			updateMap.put("order", i+1);
			rowCount += iconFunMapper.updateByPrimaryKeySelective(updateMap);
		}
		return rowCount;
	}
}
