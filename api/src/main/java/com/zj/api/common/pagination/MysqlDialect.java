package com.zj.api.common.pagination;

public class MysqlDialect extends Dialect{
	/* (non-Javadoc) 
     * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, int, int) 
     */  
    @Override  
    public String getLimitString(String sql, int offset, int limit) {  
  
        sql = sql.trim();  
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);  
        pagingSelect.append(sql);  
        if(offset > 0){
        	pagingSelect.append(" limit ").append(offset).append(",").append(limit);
        }else{
        	pagingSelect.append(" limit ").append(limit);
        }
          
        return pagingSelect.toString();  
    }  
}
