package com.zkkj.backend.service.biz.notice;

import java.util.List;
import java.util.Map;

import com.zkkj.backend.entity.biz.NoticePerson;

public interface PersonNoticeService {
	/**
	 * 获得所有的航班公告
	 * @return
	 */
	public List<Map<String, Object>> getAllPersonNoticeList(Map<String, Object> params);
    /**
     * 根据正则表达式行健过滤查询
     * @param filterList
     * @return
     */
	public List<Map<String, String>> getPersonNoticeListByRegexRowFilter(String regex);
	/**
	 * 根据子字符串行健过滤查询
	 * @param regex
	 * @return
	 */
	public List<Map<String, String>> getPersonNoticeListBySubRowFilter(String substr);

   /**
    * 根据行健精确查询
    * @param rowKey
    * @return
    */
	public Map<String, String> getPersonNoticeByKey(String rowKey);
   /**
    * 增加航班公告
    * @param notice
    * @return
    */
	public int addPersonNotice(NoticePerson notice);
    /**
     * 修改航班公告
     * @param notice
     * @return
     */
	public int updatePersonNotice(Map<String, Object> person);
	
	public int deletePersonNotice(String rowKey);
	
	public Integer getNoticePersonListByTotal(Map<String, Object> persion);
   
}
