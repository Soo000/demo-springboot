package com.kkwrite.springboot.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.kkwrite.springboot.pojo.Demo;
import com.kkwrite.springboot.service.DemoService;

@Controller
@RequestMapping("/democtrl")
public class DemoCtrl {

	@Autowired
	private DemoService demoService;
	
	@ResponseBody
	@RequestMapping("/findbyname")
	public List<Demo> findByName(String name) {
		// arg0: 第几页， arg1: 每一个获取条数
		PageHelper.startPage(1, 2);
		return demoService.findByName(name);
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Demo saveDemo() {
		Demo demo = new Demo();
		demo.setName("wangke");
		demoService.saveDemo(demo);
		return demo;
	}
}
