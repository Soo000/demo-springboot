package com.kkwrite.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.kkwrite.springboot.pojo.Demo;

public interface DemoMapper {

	@Select("select * from demo where id = #{id}")
	public Demo getById(long id);
	
	@Select("select * from demo where name = #{name}")
	public List<Demo> likeName(String name);
	
	@Select("select name from demo where id = #{id}")
	public String getNameById(Long id);
	
	@Insert("insert into demo (name) value (#{name})")
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	public void insert(Demo demo);
}
