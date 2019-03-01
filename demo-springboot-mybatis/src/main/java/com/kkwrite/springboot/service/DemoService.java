package com.kkwrite.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkwrite.springboot.mapper.DemoMapper;
import com.kkwrite.springboot.pojo.Demo;

@Service
public class DemoService {

	@Autowired
	private DemoMapper demoMapper;
	
	public Demo findById(Long id) {
		return demoMapper.getById(id);
	}
	
	public List<Demo> findByName(String name) {
		return demoMapper.likeName(name);
	}
	
	@Transactional
	public void saveDemo(Demo demo) {
		demoMapper.insert(demo);
	}
	
}
