package action;
import java.util.List;
import javabean.Userinfo;
import dao.Loginregister;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String username;
private String password;
private String message="error";
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public LoginAction()
{
	
}


@Override
public String execute() throws Exception {
	// TODO Auto-generated method stub
	
	Loginregister dao=new Loginregister();
   List list=dao.queryInfo(getUsername());
   if(list.size()>0)
   {
	for(int i=0;i<list.size();i++)
	{
		 Userinfo a=new Userinfo();
		a=(Userinfo) list.get(i);
		
	
	  if(this.getUsername().equals(a.getName()))
	  {
		  if(this.getPassword().equals(a.getWord()))
		  {
			  
			  message="success" ;  
		  }else
			  
		  {
			  addFieldError(password, "ÃÜÂë´íÎó");
			  message="input";
		  }
		  
	  }else
	   	{
	   		message="error" ;
	   	}	  	
	}
   }else
   {
	   addFieldError(username, "ÓÃ»§ÃûÎ´×¢²á");
	   message="input";
   }
	return message;
}

}
