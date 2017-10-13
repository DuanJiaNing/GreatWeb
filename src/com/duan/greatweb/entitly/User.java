package com.duan.greatweb.entitly;

import com.duan.greatweb.dao.mapping.Mapping;

@Mapping("user")
public class User {

	@Mapping("id")
	private Integer id;

	@Mapping("name")
	private String name;

	@Mapping("password")
	private String password;

	@Mapping("state")
	private Integer state;

	@Mapping("age")
	private Integer age;

	public User() {
	}

	public User(Integer id, String name, String password, Integer state, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.state = state;
		this.age = age;
	}

	public User(String name, String password, Integer state, Integer age) {
		super();
		this.name = name;
		this.password = password;
		this.state = state;
		this.age = age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", state=" + state + ", age=" + age
				+ "]";
	}

}
