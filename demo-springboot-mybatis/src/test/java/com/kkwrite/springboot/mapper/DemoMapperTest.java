package com.kkwrite.springboot.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkwrite.springboot.pojo.Demo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoMapperTest {

	@Autowired
	private DemoMapper demoMapper;
	
	@Test
	public void getByIdTest() {
		Demo demo = demoMapper.getById(1);
		Assert.assertNotEquals(demo, null);
	}
	
}
