package action;

import javabean.Userinfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.Loginregister;

public class zhuceAction extends ActionSupport {
	private String name;
	private String word;
	private String message="error";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String password) {
		this.word = password;
	}
	public Userinfo userinfo()
	{
		 Userinfo a=new Userinfo();
		 a.setName(getName());
		 a.setWord(getWord());
		return a;
		
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Loginregister dao=new Loginregister();
		String ri=dao.savezhuce(userinfo());
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


