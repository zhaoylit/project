package com.zkkj.chat.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.zkkj.chat.util.Page;


public interface IBaseDao<T,PK extends Serializable> {
	/**  
     * 插入对象
     */
	int insert(String statementName,T obj);
	/**  
     * 根据主键删除对象
     */
	int delete(String statementName, PK primaryKey);
	/**  
     * 根据条件删除
     */	
	int deleteByParam(String statementName,Map obj);
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	int update(String statementName, T entity);
	/**  
     * 根据条件更新
     */
	int updateByParam(String statementName,Object obj);
	
	T get(String statementName,PK primaryKey);
	/**  
     * 查询单条数据
     */
	T selectOneByParam(String statementName,Map obj);
	/**
	 * 查询全部
	 * @param statementName
	 * @return
	 */
	List<T> selectList(String statementName);
	/**  
     * 查询多条不分页
     */
	List<T> selectList(String statementName,Map obj);
	/**  
     * 查询多条分页
     */
	List<T> selectList(String statementName,Map obj,Page page);
	/**  
     * 查询总记录数
     */
	int getCount(String statementName,Object obj);
	 /**  
     * 批量插入  
     * @param list  
     */    
    int insertBatch(String statementName, final List<T> list);    
        
    /**  
     * 批量修改  
     * @param list  
     */    
    int updateBatch(String statementName, final List<T> list);    
        
    /**  
     * 批量删除  
     * @param list  
     */    
    int deleteBatch(String statementName, final List<PK> list);
}
