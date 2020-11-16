package cn.echohawk.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/debug")
	public Map<String, String> debug() {
		int i = 0;
		Map<String, String> map = new HashMap<>();
		map.put("code", "200");
		map.put("msg", "测试方法成功");
		return map;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(new String[]{"1","2","3","4","5","6"}));
		System.out.println(JSON.toJSONString(list));
		List<String> choose = list.subList(0,3);
		System.out.println(choose.get(1).hashCode() +" "+ list.get(1).hashCode());
		System.out.println(JSON.toJSONString(choose));
		System.out.println(JSON.toJSONString(list));
		list.set(1, "8");
		System.out.println(JSON.toJSONString(choose));
		System.out.println(JSON.toJSONString(list));
	}
}
