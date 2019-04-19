package javabean;

import java.util.Date;

/**
 * Billentry entity. @author MyEclipse Persistence Tools
 */

public class Billentry implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String manufacturer;
	private Double price;
	private Date purchasedDate;
	private String remark;

	// Constructors

	/** default constructor */
	public Billentry() {
	}

	/** minimal constructor */
	public Billentry(String name) {
		this.name = name;
	}

	/** full constructor */
	public Billentry(String name, String manufacturer, Double price,
			Date purchasedDate, String remark) {
		this.name = name;
		this.manufacturer = manufacturer;
		this.price = price;
		this.purchasedDate = purchasedDate;
		this.remark = remark;
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

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getPurchasedDate() {
		return this.purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}