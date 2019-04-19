package action;

import javabean.Student;

import com.opensymphony.xwork2.ActionSupport;

import dao.Loginregister;

public class UpAction2 extends ActionSupport {
	private String id;
	private String xuehao;
	private String name;
	private String sex;
	private String age;
	private String weight;
	private Student info;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Student info()
	{
		
		info=new Student();
		Integer c=Integer.parseInt(getId());
		info.setId(c);
		info.setXuehao(getXuehao());
		info.setName(getName());
		info.setSex(getSex());
		info.setAge(getAge());
		info.setWeight(getWeight());
		return info;
		
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Loginregister dao=new Loginregister();
		boolean a=dao.updeteStudent(info());
		if(a==true)
		{
			return SUCCESS;
		}else
		{
			return ERROR;
		}
	}
}
