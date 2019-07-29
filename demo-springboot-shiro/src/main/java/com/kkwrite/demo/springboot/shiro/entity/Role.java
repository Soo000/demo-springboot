package com.kkwrite.demo.springboot.shiro.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "pe_role")
@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = -7463058965217348718L;
	
	@Id
	private String id;
    private String name;
    private String description;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="pe_role_permission", 
    	joinColumns = {@JoinColumn(name = "role_id", referencedColumnName="id")}, 
    	inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<>();

    public Role() {
    }

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
    
}
