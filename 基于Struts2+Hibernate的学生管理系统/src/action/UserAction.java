package action;

import java.io.PrintWriter;
import java.net.URLDecoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javabean.User;

import com.opensymphony.xwork2.ActionSupport;

import dao.UserDao;
import dao.Unit;
import dao.UserDao;

public class UserAction extends ActionSupport {
	public User User(String name,Date birthday,String age,String sex,String phone,String remark){
		User a=new User();
		 a.setName(name);
		 a.setBirthday(birthday);
		 a.setAge(age);
		 a.setSex(sex);
		 a.setPhone(phone);
		 a.setRemark(remark);
		return a;
	}
	public void  write() throws Exception {
		// TODO Auto-generated method stub
		 HttpServletResponse response=ServletActionContext.getResponse();
		 HttpServletRequest request=ServletActionContext.getRequest();
		 response.setContentType("text/html;charset=utf-8");  
		 // response.setCharacterEncoding("UTF-8");  
		 response.setHeader("content-type", "text/html;charset=UTF-8");
		 String method = request.getMethod();//得到请求URL地址时使用的方法
		 String queryString=null;
		 if(request.getQueryString()!=null){
			 queryString = URLDecoder.decode(request.getQueryString(),"utf-8"); //得到请求的URL地址中附带的参数
		 }
		 PrintWriter out = response.getWriter(); 
		 System.out.println("queryString:------"+queryString);
		 UserDao cabku=new UserDao();
		 if(method.equals("GET")){
			JSONObject json=null;
			String items[]=queryString.split("pageNo=0");
			String name=null;
			String value=null;
			if(items[items.length-1].indexOf("orderBy")==-1){
				if(items[items.length-1].indexOf("_like")==-1){
					String like=items[items.length-1];
					String a=like.split("&")[1];
					value=a.split("=")[1];
					name=a.split("=")[0];
					json=cabku.query_User("from User where "+name+"=?",value);
				}else {
					String[] like=items[items.length-1].split("_like");
					name=like[0].split("&")[1];
					value=like[like.length-1].split("=")[1];
					json=cabku.list_User("from User where "+name+" like ?1",value);
				}
			}else{
				json=cabku.query_User("from User");
			}
			out.println(json); 
		 }else if(method.equals("POST")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("birthday"));
			 User a=User(json2.getString("name"),date,json2.getString("age"),json2.getString("sex"),json2.getString("phone"),json2.getString("remark"));
			 JSONObject json=cabku.addUser(a);
			 out.println(json);
		 }else if(method.equals("PUT")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("birthday"));
			 User a=User(json2.getString("name"),date,json2.getString("age"),json2.getString("sex"),json2.getString("phone"),json2.getString("remark"));
			 if(queryString!=null || queryString!=""){
				 a.setId(Integer.parseInt(queryString));
			 }
			 JSONObject json3=new JSONObject(a);
			 System.out.println("json3:-----"+json3);
			 JSONObject json=cabku.update(a);
			 out.println(json);
		 }else{
			 JSONObject json=cabku.deldate(Integer.parseInt(queryString));
			 out.println(json);
		 }
		 out.flush();  
		 out.close();  
	}
	
}
