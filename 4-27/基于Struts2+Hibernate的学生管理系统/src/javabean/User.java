package javabean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String sex;
	private String age;
	private Date birthday;
	private String remark;
	private String phone;
	private Set maintains = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String name) {
		this.name = name;
	}

	/** full constructor */
	public User(String name, String sex, String age, Date birthday,
			String remark, String phone, Set maintains) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
		this.remark = remark;
		this.phone = phone;
		this.maintains = maintains;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set getMaintains() {
		return this.maintains;
	}

	public void setMaintains(Set maintains) {
		this.maintains = maintains;
	}

}