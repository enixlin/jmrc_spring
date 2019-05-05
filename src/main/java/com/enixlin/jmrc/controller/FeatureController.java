package com.enixlin.jmrc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.Feature;
import com.enixlin.jmrc.service.FeatureService;

@RestController
@RequestMapping("feature")
public class FeatureController { 
	
	@Autowired
	private FeatureService featureService;

	@RequestMapping("/getAllFeature")
	public Feature getAllFeaturs() {
		ArrayList< Feature> al=featureService.getAllFeaturs();
		Map<String, Feature> node=new HashMap<String, Feature>();
		for (int i = 0; i < al.size(); i++) {
			al.get(i).setChildren(new ArrayList<Feature>());
			node.put(Integer.toString(al.get(i).getId()), al.get(i));
		}
		
		// 传统的Map迭代方式
		for (Map.Entry<String, Feature> entry : node.entrySet()) {
			String parent_id=Integer.toString(entry.getValue().getParent_id());
			String id=entry.getKey();
			if(!id.equals(parent_id)) {
				//如果当前的节点中，id 与parent_id 不同，则在父节点插入当前节点
				node.get(parent_id).getChildren().add(entry.getValue());
			}
		}

		return node.get("1");
	}
}
