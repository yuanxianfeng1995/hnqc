package javabean;

import java.util.Date;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String xuehao;
	private String name;
	private String sex;
	private String age;
	private String weight;
	private Date birthday;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String xuehao, String name, String sex, String age,
			String weight) {
		this.xuehao = xuehao;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.weight = weight;
	}

	/** full constructor */
	public User(String xuehao, String name, String sex, String age,
			String weight, Date birthday) {
		this.xuehao = xuehao;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.weight = weight;
		this.birthday = birthday;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getXuehao() {
		return this.xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
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

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}