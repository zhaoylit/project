package com.zj.web.modules.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.modules.goods.dao.GoodsServicesMapper;
import com.zj.web.modules.goods.service.IGoodsServicesService;


@Service("goodsServicesService")
public class GoodsServicesServiceImpl implements IGoodsServicesService {
	@Autowired
	private GoodsServicesMapper goodsServicesMapper;
	private static final String RETURN_PATH = (String) CustomizedPropertyConfigurer.getContextProperty("return_path");

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		//获取资源访问路径
		List<Map> selectList = goodsServicesMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
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
		List<Map> selectList = goodsServicesMapper.selectList(params);
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
		Map oneMap = goodsServicesMapper.selectOne(params);
		if(oneMap != null){
			String iamgeUrl = ParamsUtil.nullDeal(oneMap, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				oneMap.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return goodsServicesMapper.selectOne(params);
	}
	
	@Override
	public int insert(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsServicesMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsServicesMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public int deleteByPrimaryKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids = ParamsUtil.nullDeal(params, "ids","");
		if("".equals(ids)){
			return 0;
		}
		String []idsArray = ids.split(",");
		return goodsServicesMapper.deleteByPrimaryKey(idsArray);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsServicesMapper.selectCount(params);
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
			rowCount += goodsServicesMapper.updateByPrimaryKeySelective(updateMap);
		}
		return rowCount;
	}
}
