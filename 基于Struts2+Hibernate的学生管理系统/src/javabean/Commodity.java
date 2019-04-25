package javabean;

import java.util.Date;

/**
 * Commodity entity. @author MyEclipse Persistence Tools
 */

public class Commodity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Billentry billentry;
	private Outbound outbound;
	private String no;
	private String name;
	private Integer number;
	private Double price;
	private Double money;
	private String manufacturer;
	private Date purchasedDate;
	private String remark;

	// Constructors

	/** default constructor */
	public Commodity() {
	}

	/** minimal constructor */
	public Commodity(String name) {
		this.name = name;
	}

	/** full constructor */
	public Commodity(Billentry billentry, Outbound outbound, String no,
			String name, Integer number, Double price, Double money,
			String manufacturer, Date purchasedDate, String remark) {
		this.billentry = billentry;
		this.outbound = outbound;
		this.no = no;
		this.name = name;
		this.number = number;
		this.price = price;
		this.money = money;
		this.manufacturer = manufacturer;
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

	public Billentry getBillentry() {
		return this.billentry;
	}

	public void setBillentry(Billentry billentry) {
		this.billentry = billentry;
	}

	public Outbound getOutbound() {
		return this.outbound;
	}

	public void setOutbound(Outbound outbound) {
		this.outbound = outbound;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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