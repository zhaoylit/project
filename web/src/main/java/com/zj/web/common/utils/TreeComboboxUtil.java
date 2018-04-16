package com.zj.web.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class TreeComboboxUtil {
	private static List<Map> nodes; 
	private Integer selectId = null;
	private String textName = "name";
	public TreeComboboxUtil(List<Map> list,Integer selectId){
		this.nodes = list;
		this.selectId = selectId;
	}
	public String getJson(boolean isHasRoot,String textName){
		if(textName != null && !"".equals(textName)){
			this.textName = textName;
		}
		List<Map> treeList = new ArrayList<Map>();
		if(nodes != null && nodes.size() > 0){
			for (Map node : nodes) {  
				Integer id = Integer.parseInt(String.valueOf(node.get("id")));
				Integer pid = Integer.parseInt(String.valueOf(node.get("pid")));
				String name = String.valueOf(node.get(textName));
				if (pid == 0 || (selectId != null && selectId.equals(id))) {
					Map mm  = new HashMap();
					node.put("text", name);
					treeList.add(node);
					buildChildren(node);
				}  
			}  
		}
		if(isHasRoot){
			Map rootMap = new HashMap();
			rootMap.put("id", "0");
			rootMap.put("pid", "-1");
			rootMap.put("text", "根节点");
			rootMap.put("children", treeList);
			return JSONArray.fromObject(rootMap).toString();
		}else {
			return JSONArray.fromObject(treeList).toString();
		}
	}
	private void buildChildren(Map node){
		List<Map> children = getChildren(nodes,node);  
		if (!children.isEmpty()) {  
			List<Map> childList = new ArrayList<Map>();
			for (Map<String,String> child : children) {  
				String name = String.valueOf(child.get(textName));
				Map mm  = new HashMap();
				child.put("text", name);
				childList.add(child);
				buildChildren(child);
			}  
			node.put("children",childList);
		}   
	}   
	private List<Map> getChildren(List<Map> nodes,Map node){  
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
