package javabean;

import java.util.HashSet;
import java.util.Set;

/**
 * Banji entity. @author MyEclipse Persistence Tools
 */

public class Banji implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String word;
	private Set students = new HashSet(0);

	// Constructors

	/** default constructor */
	public Banji() {
	}

	/** minimal constructor */
	public Banji(String name) {
		this.name = name;
	}

	/** full constructor */
	public Banji(String name, String word, Set students) {
		this.name = name;
		this.word = word;
		this.students = students;
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

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Set getStudents() {
		return this.students;
	}

	public void setStudents(Set students) {
		this.students = students;
	}

}