package action;

import javabean.Student;
import javabean.Userinfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.Loginregister;

public class RegisterAction extends ActionSupport {
	private String xuehao;
	private String name;
	private String sex;
	private String age;
	private String weight;
	private String id;
private String message="error";


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
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Student student()
{
	int c=Integer.valueOf(getId());
	Student a=new Student();
	a.setId(c);
	a.setXuehao(getXuehao());
	 a.setName(getName());
	 a.setSex(getSex());
	 a.setAge(getAge());
	 a.setWeight(getWeight());
	return a;
	
}
@Override
public String execute() throws Exception {
	// TODO Auto-generated method stub
	Loginregister dao=new Loginregister();
	String ri=dao.saveInfo(student());
	if(ri.equals("success"))
	{
		message="success";
	}else
	{
		message="error";
	}
	return message;
}

}
