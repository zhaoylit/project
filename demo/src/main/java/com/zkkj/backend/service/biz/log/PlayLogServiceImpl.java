package com.zkkj.backend.service.biz.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkkj.backend.common.util.CustomizedPropertyConfigurer;
import com.zkkj.backend.common.util.ParamsUtil;
import com.zkkj.backend.common.util.StringUtils;
import com.zkkj.backend.service.biz.advertiser.IAdvertiserService;
import com.zkkj.backend.service.common.BaseService;
import com.zkkj.platform.advert.AdvertManageService;
import com.zkkj.platform.advertiser.AdvertiserManageService;
import com.zkkj.platform.base.common.DateUtil;
import com.zkkj.platform.log.PlayLogManageService;
import com.zkkj.platform.user.UserManageService;

/**
 * 用户管理业务实现层
 * 
 * @author
 *
 */
@Service("playLogService")
public class PlayLogServiceImpl extends BaseService implements IPlayLogService {

	@Autowired
	private IAdvertiserService advertiserService;

	@Override
	public List<Map<String, String>> getLog(Map params) throws Exception {
		// TODO Auto-generated method stub
		PlayLogManageService playLogService = (PlayLogManageService) getService("playLogManageService");
		AdvertiserManageService advertiserManageService = (AdvertiserManageService)getService("advertiserManageService");
		List<Map<String, String>> resultList =playLogService.getPlayLog(params);
		if(resultList==null&&resultList.equals("")){
			return Collections.emptyList();
		}else{
			return resultList;
		}
		
	}
		/*List<Map<String, String>> resultList = playLogService
				.getPlayLog(params);
		for (int i = 0; i < resultList.size(); i++) {
			Map mm = resultList.get(i);
			// 根据广告rowKey查询一条广告rowKey
			try {
				String advertRowKey = resultList.get(i).get("adId");
				Map advertMap = advertService.getAdvertById(advertRowKey);

				// 根据广告商查广告商name
				String advertiserId = ParamsUtil.nullDeal(advertMap,
						"advertiserId", "");

				if (!"".equals(advertiserId)) {
					Map advertiserMap = advertiserService.getAdvertiserById(advertiserId);
					mm.put("advertiserName",
							advertiserMap.get("advertiserName"));
				}
			} catch (Exception e) {
			}
			// 根据设备ID查询一条设备
			String deviceId = resultList.get(i).get("deviceId");

			Map deviceIdMap = new HashMap();

			deviceIdMap.put("deviceId", deviceId);
			List<Map<String, String>> deviceMap = advertService
					.getDeviceInfoByParams(deviceIdMap);
			if (deviceMap != null && deviceMap.size() > 0) {
				mm.put("airportName", deviceMap.get(0).get("airportName"));
				mm.put("airportCode", deviceMap.get(0).get("airportCode"));
				mm.put("viproom", deviceMap.get(0).get("viproom"));
				mm.put("playType", deviceMap.get(0).get("playType"));
				mm.put("deviceId", deviceMap.get(0).get("deviceId"));
			}
			System.out.println("6----------------------------------------------"+ resultList);
		}*/


	@Override
	public int getpalyLogListCount(Map params) throws Exception {
		PlayLogManageService playLogService = (PlayLogManageService) getService("playLogManageService");
		int rowsCount = playLogService.getPlayLogCount(params);
		return rowsCount;
	}


	@Override
	public Map getLogById(String rowKey) throws Exception {
		PlayLogManageService playLogService = (PlayLogManageService) getService("playLogManageService");
		Map resultMap = playLogService.getLogById(rowKey);
		String path = (String)CustomizedPropertyConfigurer.getContextProperty("return_path_z");//图片访问地址
		String upload_path=(String)CustomizedPropertyConfigurer.getContextProperty("advert_upload_path");//资源路径
		String file1 = ParamsUtil.nullDeal(resultMap, "path1","");
		String file2 = ParamsUtil.nullDeal(resultMap, "path2","");
		resultMap.put("path1", path +upload_path+ file1);
		resultMap.put("path2", path +upload_path+ file2);
		return resultMap;
	}

}
