package com.web.modules.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITestService {
	public String getZipFilePath(HttpServletRequest request,HttpServletResponse response);
}
