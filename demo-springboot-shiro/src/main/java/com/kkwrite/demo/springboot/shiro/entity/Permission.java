package com.kkwrite.demo.springboot.shiro.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "pe_permission")
@Entity
public class Permission implements Serializable {

	private static final long serialVersionUID = 8669483718104331635L;
	
	@Id
	private String id;
	private String name;
    private String code;
    private String description;
    
    public Permission(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
