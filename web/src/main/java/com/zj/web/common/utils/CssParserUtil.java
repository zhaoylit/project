package com.zj.web.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.parser.CSSOMParser;
    
public class CssParserUtil {
    private final static Logger log = Logger.getLogger(CssParserUtil.class);
    /**
     * 打印样式文件内容
     * @param filePath 样式本地文件路径
     * @param selectorText  属性名称
     * @return
     */
    public static boolean showCssText(InputStream inStream){
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return false;
            }
            
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取样式名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                System.out.println(cssText);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("showCssText***************************************"+e.getMessage());
            return false;
        }
         return false;
    }
    /**
     * 检查样式属性是否存在
     * @param filePath 样式本地文件路径
     * @param selectorText  属性名称
     * @return
     */
    public static boolean checkSelectorText(InputStream inStream,String selectorText){
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return false;
            }
            
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取样式名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                
                if(selectorText.equals(selectorText_)){
                    return true;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("checkSelectorText***************************************"+e.getMessage());
            return false;
        }
         return false;
    }
    /**
     * 查询样式文件中选择器名称下面的属性是否存在
     * @param inStream css文件输入流
     * @param selectorText 选择器名称
     * @param property  属性名称
     * @return
     */
    public static boolean checkCssProperty(InputStream inStream,String selectorText,String property){
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return false;
            }
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                
                if(selectorText.equals(selectorText_)){
                    CSSStyleDeclaration ss =  ((CSSStyleRule)rule).getStyle();
                    String propertyValue = ss.getPropertyValue(property);
                    if("".equals(propertyValue) || propertyValue == null){
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("checkCssProperty***************************************"+e.getMessage());
            return false;
        }
        return false;
    }
    
    /**
     * 添加新样式
     * @param inStream css文件输入流
     * @param selectorText 选择器名称
     * @param property  属性名称
     * @return
     */
    public static CSSStyleSheet insertRule(InputStream inStream,String rule){
        CSSStyleSheet sheet = null;
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return null;
            }
            sheet.insertRule(rule, rules.getLength());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("insertRule***************************************"+e.getMessage());
            return null;
        }
        return sheet;
    }
    /**
     * 更新选择器样式
     * @param inStream css文件输入流
     * @param selectorText 选择器名称
     * @param property  属性名称
     * @return
     */
    public static CSSStyleSheet updateRule(InputStream inStream,String selectorText,String ruleText){
        CSSStyleSheet sheet = null;
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return null;
            }
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                if(selectorText.equals(selectorText_)){
                    ((CSSStyleRule)rule).getStyle().setCssText(ruleText);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("updateRule***************************************"+e.getMessage());
            return null;
        }
        return sheet;
    }
    /**
     * 删除选择器样式
     * @param inStream css文件输入流
     * @param selectorText 选择器名称
     * @param property  属性名称
     * @return
     */
    public static CSSStyleSheet deleteRule(InputStream inStream,String selectorText){
        CSSStyleSheet sheet = null;
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return null;
            }
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                
                if(selectorText.equals(selectorText_)){
                    sheet.deleteRule(i);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("deleteRule***************************************"+e.getMessage());
            return null;
        }
        return sheet;
    }
    /**
     * 添加或者更新选择器属性
     * @param inStream  文件输入流
     * @param selectorText 选择器名称
     * @param propertyName 添加或者更新的属性名称
     * @param propertyValue 属性值
     * @param priority  优先级   例如 "important",或者空
     * @return
     */
    public static CSSStyleSheet addRuleProperty(InputStream inStream,String selectorText,String propertyName,String propertyValue,String priority){
        CSSStyleSheet sheet = null;
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return null;
            }
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                
                if(selectorText.equals(selectorText_)){
                    CSSStyleDeclaration cd =  ((CSSStyleRule)rule).getStyle();
                    cd.setProperty(propertyName, propertyValue, priority);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("addRuleProperty***************************************"+e.getMessage());
            return null;
        }
        return sheet;
    }
    /**
     * 更新样式地址
     * @param inStream css文件输入流
     * @param url 访问地址
     * @return
     */
    public static CSSStyleSheet updateUrl(InputStream inStream,String url){
        CSSStyleSheet sheet = null;
        try {
            InputSource source = new InputSource();
            source.setByteStream(inStream);
            source.setEncoding("UTF-8");
            final CSSOMParser parser = new CSSOMParser();
            sheet = parser.parseStyleSheet(source, null, null);
            CSSRuleList rules = sheet.getCssRules();
            if(rules.getLength() == 0 ){
                return null;
            }
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSRule rule = rules.item(i);
                //获取选择器名称
                String selectorText_ = ((CSSStyleRule) rule).getSelectorText();
                //获取样式内容
                String cssText = ((CSSStyleRule)rule).getCssText();
                //获取样式定义
                CSSStyleDeclaration ctd = ((CSSStyleRule)rule).getStyle();
                String cssStyleText = ctd.getCssText();
                String sub = cssStyleText.substring(cssStyleText.indexOf("url("), cssStyleText.indexOf("/", cssStyleText.indexOf("//") + 2) + 1);
                String cssStyleText_ = cssStyleText.replace(sub, "url(" + url);
                
                ((CSSStyleRule)rule).getStyle().setCssText(cssStyleText_);
                //添加属性
                /* CSSStyleDeclaration ctd = ((CSSStyleRule)rule).getStyle();
                ctd.setProperty("12", "12", "1");*/
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error("updateRule***************************************"+e.getMessage());
            return null;
        }
        return sheet;
    }
    
    /**
     * 保存sheet到文件
     * @param sheet
     * @param savePath
     */
    public static void saveShellToFile(CSSStyleSheet sheet,String savePath){
		try {
			FileOutputStream out = new FileOutputStream(new File(savePath));
			out.write(sheet.getCssRules().toString().getBytes());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("saveShellToFile------------------------------" + e.getMessage());
		}
    }
    public static void main(String[] args) throws IOException {
//        URL url = new URL("https://g.alicdn.com/tm/login/3.0.5/login_pc.css");
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//      InputStream inStream = connection.getInputStream();
        
        InputStream inStream = new FileInputStream(new File("D://data/css/icon.css"));
//        CSSStyleSheet sheet = addRuleProperty(inStream,".icon-cut","height","100%","important");
        
        
//        CSSStyleSheet sheet = insertupdateUrlRule(inStream,".div{width:100px;height:200px;}");
        
//        CSSStyleSheet sheet = updateRule(inStream,".div","height:100px;");
        
//        String cssRule = sheet.getCssRules().toString();
//        FileOutputStream out = new FileOutputStream("D://data/css/icon.css");
//        out.write(cssRule.getBytes());
//        out.close();
    	  CSSStyleSheet updateUrl = updateUrl(inStream,"http://192.168.1.107:8080/");
    	  if(updateUrl != null){
    		saveShellToFile(updateUrl,"D://data/css/icon.css");
    	  }
//        checkSelectorText(inStream,".table");
//        checkCssProperty(inStream,".table","height");
          
        
    }
}