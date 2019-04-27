package javabean;

import java.util.Date;

/**
 * Maintain entity. @author MyEclipse Persistence Tools
 */

public class Maintain implements java.io.Serializable {

	// Fields

	private MaintainId id;
	private String license;
	private String mileage;
	private Date compulsoryInsurance;
	private String content;

	// Constructors

	/** default constructor */
	public Maintain() {
	}

	/** minimal constructor */
	public Maintain(MaintainId id) {
		this.id = id;
	}

	/** full constructor */
	public Maintain(MaintainId id, String license, String mileage,
			Date compulsoryInsurance, String content) {
		this.id = id;
		this.license = license;
		this.mileage = mileage;
		this.compulsoryInsurance = compulsoryInsurance;
		this.content = content;
	}

	// Property accessors

	public MaintainId getId() {
		return this.id;
	}

	public void setId(MaintainId id) {
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

}