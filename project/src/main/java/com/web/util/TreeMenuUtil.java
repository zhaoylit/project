package com.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeMenuUtil {
	private StringBuffer html = new StringBuffer();  
    private List<Map> nodes;  
      
    public TreeMenuUtil(List<Map> nodes){  
        this.nodes = nodes;  
    }  
      
    public String buildMenuTree(){  
        for (Map node : nodes) {  
        	Integer isGroup = Integer.parseInt(String.valueOf(node.get("isGroup")));  
            Integer id = Integer.parseInt(String.valueOf(node.get("id")));  
            Integer pid = Integer.parseInt(String.valueOf(node.get("pid")));  ;  
            String name = String.valueOf(node.get("menuName"));  
            String url  = String.valueOf(node.get("funUrl"));    
            String iconCls  = String.valueOf(node.get("iconCls"));    
            if (pid == 0) {  
                html.append("<div title=\""+name+"\" data-options=\"iconCls:'"+iconCls+"'\" style=\"overflow:auto;padding:10px;\">");
                Menubuild(node);  
                html.append("</div>");
            }  
        }  
        return html.toString();  
    }  
    public String buildFunTree(){  
    	html.append("<ul class=\"easyui-tree\" data-options=\"animate:true,state:'closed'\" >"); 
    	for (Map node : nodes) {  
        	Integer isGroup = Integer.parseInt(String.valueOf(node.get("isGroup")));  
            Integer id = Integer.parseInt(String.valueOf(node.get("id")));  
            Integer pid = Integer.parseInt(String.valueOf(node.get("pid")));  ;  
            String name = String.valueOf(node.get("funName"));  
            String url  = String.valueOf(node.get("funUrl"));    
            String iconCls  = String.valueOf(node.get("iconCls"));    
            if (pid == 0) {  
            	 html.append("<li  data-options=\"iconCls:'"+iconCls+"',attributes:{'id':'"+id+"','url':'"+url+"'} \">");
                 html.append("<span>"+name+"</span>");
                 Funbuild(node);  
                 html.append("</li>");
            }  
        }  
        html.append("</ul>");
        return html.toString();  
    }  
    private void Menubuild(Map node){  
        List<Map> children = getChildren(node);  
        if (!children.isEmpty()) {  
            html.append("<ul class=\"easyui-tree\" data-options=\"animate:true\">");  
            for (Map<String,String> child : children) {  
            	Integer isGroup = Integer.parseInt(String.valueOf(child.get("isGroup")));  
            	Integer id = Integer.parseInt(String.valueOf(child.get("id")));  
                Integer pid = Integer.parseInt(String.valueOf(child.get("pid")));  ;  
                String name = String.valueOf(child.get("menuName"));  
                String url  = String.valueOf(child.get("funUrl"));    
                String iconCls  = String.valueOf(child.get("iconCls"));
                html.append("<li  data-options=\"iconCls:'"+iconCls+"',attributes:{'id':'"+id+"','url':'"+url+"','isGroup':'"+isGroup+"'} \">");
                html.append("<span>"+name+"</span>");
                Menubuild(child); 
                html.append("</li>");
            }  
            html.append("</ul>");  	
        }   
    }  
    private void Funbuild(Map node){  
    	List<Map> children = getChildren(node);  
        if (!children.isEmpty()) {  
            html.append("<ul>");  
            for (Map<String,String> child : children) {  
            	Integer isGroup = Integer.parseInt(String.valueOf(child.get("isGroup")));  
            	Integer id = Integer.parseInt(String.valueOf(child.get("id")));  
                Integer pid = Integer.parseInt(String.valueOf(child.get("pid")));  ;  
                String name = String.valueOf(child.get("funName"));  
                String url  = String.valueOf(child.get("funUrl"));    
                String iconCls  = String.valueOf(child.get("iconCls"));
                html.append("<li  data-options=\"iconCls:'"+iconCls+"',attributes:{'id':'"+id+"','url':'"+url+"'} \">");
                html.append("<span>"+name+"</span>");	
                Funbuild(child); 
                html.append("</li>");
            }  
            html.append("</ul>");  
        }   
    }   
    private List<Map> getChildren(Map node){  
        List<Map> children = new ArrayList<Map>();  
        Integer id = StringUtil.isNorBlank(node.get("id")+"") ? Integer.parseInt(String.valueOf(node.get("id"))) : null;  
        for (Map child : nodes) {
        	Integer pid = StringUtil.isNorBlank(child.get("pid")+"") ? Integer.parseInt(String.valueOf(child.get("pid"))) : null;
            if (id.equals(pid)) {
                children.add(child);  
            }  
        }  
        return children;  
    }  
}
