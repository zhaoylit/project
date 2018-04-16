package com.zkkj.backend.dao.dataSource;

import org.aspectj.lang.ProceedingJoinPoint;
/**
 * 
 * @author Huangwn
 *
 */
public class MultipleDataSourceAspectAdvice {
	
	private final String defaultDataSource = DataSourceEnum.LAIQUBASE.dataSource;
	private final String secondDataSource = DataSourceEnum.LAIQUDB.dataSource;
	
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
	
		Class<?>[] in = jp.getTarget().getClass().getInterfaces();
		boolean tag = in[0].isAnnotationPresent(MultDS.class);
		
     if (tag){
    	 MultDS  ds = (MultDS)in[0].getAnnotation(MultDS.class);
    	  String dataSource = ds.value();
    	   if(dataSource!=null)
    		   MultipleDataSource.setDataSourceKey(DataSourceEnum.valueOf(dataSource.toUpperCase()).dataSource);
    	   else
    		   MultipleDataSource.setDataSourceKey(secondDataSource);
        }
		else
        	MultipleDataSource.setDataSourceKey(defaultDataSource);
        
        return jp.proceed();
    }

}
