package com.zj.web.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
	private static List<Map> nodes; 
	public TreeUtil(List<Map> list){
		this.nodes = list;
	}
	
	public static List<Map> buildTree(){
		List<Map> treeList = null;
		if(nodes != null && nodes.size() > 0){
			treeList = new ArrayList<Map>();
			for (Map node : nodes) {  
				Integer pid = Integer.parseInt(String.valueOf(node.get("pid")));
				if (pid == 0) {  
					treeList.add(node);
					buildChildren(node);
				}  
			} 
			Map rootMap = new HashMap();
			rootMap.put("id", "0");
			rootMap.put("pid", "-1");
			rootMap.put("text", "根节点");
			rootMap.put("children", treeList); 
		}
		return treeList == null ? Collections.EMPTY_LIST : treeList; 
	}
	private static void buildChildren(Map node){
		List<Map> children = getChildren(nodes,node);  
		if (!children.isEmpty()) {  
			List<Map> childList = new ArrayList<Map>();
			for (Map<String,String> child : children) {  
				childList.add(child);
				buildChildren(child);
			}  
			node.put("children",childList);
		}   
	}   
	private static List<Map> getChildren(List<Map> nodes,Map node){
        List<Map> children = new ArrayList<Map>();  
        String id = ParamsUtil.nullDeal(node, "id", "");
        for (Map child : nodes) {
        	String pid = ParamsUtil.nullDeal(child, "pid", "");
            if (!"".equals(id) && id.equals(pid)) {
                children.add(child);
            }  
        }  
        return children;  
    }
}
