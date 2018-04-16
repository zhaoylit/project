package com.zj.web.modules.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.modules.goods.dao.GoodsResourceMapper;
import com.zj.web.modules.goods.service.IGoodsResourceService;

/**
 * @describe  商品管理业务实现类 
 * @author ZYL
 * @date 2017-07-26
 * @version 1.0
 */
@Service("goodsResourceService")
public class GoodsResouceServiceImpl implements IGoodsResourceService {
	@Autowired
	private GoodsResourceMapper goodsResourceMapper;
	private static final String RETURN_PATH = (String) CustomizedPropertyConfigurer.getContextProperty("return_path");

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		//获取资源访问路径
		List<Map> selectList = goodsResourceMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
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
		List<Map> selectList = goodsResourceMapper.selectList(params);
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
		Map oneMap = goodsResourceMapper.selectOne(params);
		if(oneMap != null){
			String iamgeUrl = ParamsUtil.nullDeal(oneMap, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				oneMap.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return goodsResourceMapper.selectOne(params);
	}
	
	@Override
	public int insert(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsResourceMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsResourceMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public int deleteByPrimaryKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids = ParamsUtil.nullDeal(params, "ids","");
		if("".equals(ids)){
			return 0;
		}
		String []idsArray = ids.split(",");
		return goodsResourceMapper.deleteByPrimaryKey(idsArray);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return goodsResourceMapper.selectCount(params);
	}
}
