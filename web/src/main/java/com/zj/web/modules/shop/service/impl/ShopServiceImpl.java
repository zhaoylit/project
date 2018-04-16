package com.zj.web.modules.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.web.common.pagination.Page;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;
import com.zj.web.common.utils.ParamsUtil;
import com.zj.web.modules.shop.dao.ShopMapper;
import com.zj.web.modules.shop.service.IShopService;

/**
 * @describe  店铺 管理业务实现类
 * @author ZYL
 * @date 2017-07-26
 * @version 1.0
 */
@Service("shopService")
public class ShopServiceImpl implements IShopService {
	@Autowired
	private ShopMapper shopMapper;
	private static final String RETURN_PATH = (String) CustomizedPropertyConfigurer.getContextProperty("return_path");

	@Override
	public List<Map> selectList(Map params, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		//获取资源访问路径
		List<Map> selectList = shopMapper.selectList(params,new RowBounds(page.getStartItem(), page.getNumPerPage()));
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
		List<Map> selectList = shopMapper.selectList(params);
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
		Map oneMap = shopMapper.selectOne(params);
		if(oneMap != null){
			String iamgeUrl = ParamsUtil.nullDeal(oneMap, "imageUrl", "");
			if(!"".equals(iamgeUrl) && !iamgeUrl.contains(RETURN_PATH)){
				oneMap.put("imageUrl", RETURN_PATH + iamgeUrl);
			}
		}
		return shopMapper.selectOne(params);
	}
	
	@Override
	public int insert(Map params) throws Exception {
		// TODO Auto-generated method stub
		return shopMapper.insert(params);
	}

	@Override
	public int updateByPrimaryKeySelective(Map params) throws Exception {
		// TODO Auto-generated method stub
		return shopMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public int deleteByPrimaryKey(Map params) throws Exception {
		// TODO Auto-generated method stub
		String ids = ParamsUtil.nullDeal(params, "ids","");
		if("".equals(ids)){
			return 0;
		}
		String []idsArray = ids.split(",");
		return shopMapper.deleteByPrimaryKey(idsArray);
	}

	@Override
	public int selectCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return shopMapper.selectCount(params);
	}
}
