package javabean;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	private Integer id;
	private Banji banji;
	private String xuehao;
	private String name;
	private String sex;
	private String age;
	private String weight;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(String name) {
		this.name = name;
	}

	/** full constructor */
	public Student(Banji banji, String xuehao, String name, String sex,
			String age, String weight) {
		this.banji = banji;
		this.xuehao = xuehao;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.weight = weight;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Banji getBanji() {
		return this.banji;
	}

	public void setBanji(Banji banji) {
		this.banji = banji;
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

}