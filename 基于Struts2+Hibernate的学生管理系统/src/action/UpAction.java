package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import javabean.Student;

import com.opensymphony.xwork2.ActionSupport;

import dao.Loginregister;

public class UpAction extends ActionSupport{
private String xuehao;
private String massege;
private HttpServletRequest request;
public String getXuehao() {
	return xuehao;
}

public void setXuehao(String xuehao) {
	this.xuehao = xuehao;
}

@Override
public String execute() throws Exception {
	// TODO Auto-generated method stub
	request=ServletActionContext.getRequest();
	Loginregister dao=new Loginregister();
	List list=dao.chaozhaoSd(getXuehao());
	if(list==null)
	{
		return ERROR;
	}
	else
	{
		
		 request.getSession().setAttribute("studens", list);
			return SUCCESS;
	
}
}
}
