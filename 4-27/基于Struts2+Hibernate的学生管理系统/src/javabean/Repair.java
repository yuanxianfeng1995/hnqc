package javabean;

import java.util.Date;

/**
 * Repair entity. @author MyEclipse Persistence Tools
 */

public class Repair implements java.io.Serializable {

	// Fields

	private Integer id;
	private String license;
	private String mileage;
	private Date compulsoryInsurance;
	private String content;
	private Integer userId;
	private String userName;

	// Constructors

	/** default constructor */
	public Repair() {
	}

	/** full constructor */
	public Repair(String license, String mileage, Date compulsoryInsurance,
			String content, Integer userId, String userName) {
		this.license = license;
		this.mileage = mileage;
		this.compulsoryInsurance = compulsoryInsurance;
		this.content = content;
		this.userId = userId;
		this.userName = userName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}