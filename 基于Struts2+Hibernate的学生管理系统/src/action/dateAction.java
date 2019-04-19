package action;



import java.util.List;

import javabean.Student;

import com.opensymphony.xwork2.ActionSupport;

import dao.Loginregister;

public class dateAction extends ActionSupport {
private String xuehao;
private String massege;
public String getXuehao() {
	return xuehao;
}

public void setXuehao(String xuehao) {
	this.xuehao = xuehao;
}

@Override
public String execute() throws Exception {
	// TODO Auto-generated method stub
	int c=Integer.valueOf(getXuehao());
	Loginregister dao=new Loginregister();
	List list=dao.chaozhaoSd(getXuehao());
	if(list==null)
	{
		return massege="error";
	}
	else
	{
		for(int i=0;i<list.size();i++)
		{
			
			Student k=new Student();
		k=(Student) list.get(i);
		
	boolean a=dao.deteStudent(k.getId());
	if(a==true)
	{
		return massege="success";
	}
	else
	{
		return massege="error";
	}
		}
}
	return massege;
}
}
