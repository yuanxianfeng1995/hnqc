package action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import dao.Meun;

public class IndexAction extends ActionSupport {

	public void  write() throws Exception {
	 HttpServletResponse response=ServletActionContext.getResponse();
	 response.setContentType("text/html;charset=utf-8");  
	 //response.setCharacterEncoding("UTF-8");  
	 response.setHeader("content-type", "text/html;charset=UTF-8");
	 PrintWriter out = response.getWriter(); 
	 Meun meun=new Meun();
	 JSONObject a=meun.JSONObject(); 
	 out.println(a); 
	 out.flush();  
	 out.close();  
	}
}
