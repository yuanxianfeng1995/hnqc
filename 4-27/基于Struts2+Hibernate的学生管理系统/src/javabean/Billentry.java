package javabean;

import java.util.Date;

/**
 * Billentry entity. @author MyEclipse Persistence Tools
 */

public class Billentry implements java.io.Serializable {

	// Fields

	private Integer id;
	private String no;
	private String name;
	private Integer number;
	private double price;
	private double money;
	private String manufacturer;
	private String addr;
	private Date purchasedDate;
	private String making;
	private String handle;
	private String remark;
	private String commodityId;

	// Constructors

	/** default constructor */
	public Billentry() {
	}

	/** minimal constructor */
	public Billentry(String name) {
		this.name = name;
	}

	/** full constructor */
	public Billentry(String no, String name, Integer number, double price,
			double money, String manufacturer, String addr, Date purchasedDate,
			String making, String handle, String remark, String commodityId) {
		this.no = no;
		this.name = name;
		this.number = number;
		this.price = price;
		this.money = money;
		this.manufacturer = manufacturer;
		this.addr = addr;
		this.purchasedDate = purchasedDate;
		this.making = making;
		this.handle = handle;
		this.remark = remark;
		this.commodityId = commodityId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getPurchasedDate() {
		return this.purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getMaking() {
		return this.making;
	}

	public void setMaking(String making) {
		this.making = making;
	}

	public String getHandle() {
		return this.handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

}