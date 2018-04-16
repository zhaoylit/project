package com.zkkj.backend.dao.dataSource;
/**
 * 多数据源枚举类型，dataSource对应spring-mybatis.xml里的bean
 * @author Huangwn
 *
 */
public enum DataSourceEnum {

	LAIQUDB("laiqudbDataSource"), LAIQUBASE("laiquBaseDataSource"),SWANDB("swandbDataSource");

	public final String dataSource;

	private DataSourceEnum(String dbname) {
		dataSource = dbname;
	}
}
