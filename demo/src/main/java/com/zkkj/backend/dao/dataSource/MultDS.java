package com.zkkj.backend.dao.dataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 有此注解的Mapper类连接的是外部数据库
 * @author Huangwn
 *
 */
@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited

public @interface MultDS {
  String value();
}
