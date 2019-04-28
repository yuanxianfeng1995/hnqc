package javabean;

import java.util.Date;

/**
 * Maintain entity. @author MyEclipse Persistence Tools
 */

public class Maintain implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String license;
	private String mileage;
	private Date compulsoryInsurance;
	private String content;

	// Constructors

	/** default constructor */
	public Maintain() {
	}

	/** full constructor */
	public Maintain(User user, String license, String mileage,
			Date compulsoryInsurance, String content) {
		this.user = user;
		this.license = license;
		this.mileage = mileage;
		this.compulsoryInsurance = compulsoryInsurance;
		this.content = content;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getMileage() {
		return this.mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public Date getCompulsoryInsurance() {
		return this.compulsoryInsurance;
	}

	public void setCompulsoryInsurance(Date compulsoryInsurance) {
		this.compulsoryInsurance = compulsoryInsurance;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}