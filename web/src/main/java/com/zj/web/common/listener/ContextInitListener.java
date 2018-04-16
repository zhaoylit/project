package com.zj.web.common.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.w3c.dom.css.CSSStyleSheet;

import com.zj.web.common.utils.CssParserUtil;
import com.zj.web.common.utils.CustomizedPropertyConfigurer;


@Component
public class ContextInitListener implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				//资源访问路径
				String return_path = (String) CustomizedPropertyConfigurer.getContextProperty("return_path");
				String data_path = (String) CustomizedPropertyConfigurer.getContextProperty("data_path");
				String iconCssFilePath = data_path + "css/icon.css";
				InputStream inputStream = new FileInputStream(new File(iconCssFilePath));
				//修改图标样式文件的访问地址
				CSSStyleSheet updateSheet = CssParserUtil.updateUrl(inputStream, return_path);
				if(updateSheet != null){
					CssParserUtil.saveShellToFile(updateSheet, iconCssFilePath);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
        }  
	}
}
