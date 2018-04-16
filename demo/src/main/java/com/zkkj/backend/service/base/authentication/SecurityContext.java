package com.zkkj.backend.service.base.authentication;

public interface SecurityContext {

   public Subject getSubject();
   public void setSubject(Subject subject);
   
}
