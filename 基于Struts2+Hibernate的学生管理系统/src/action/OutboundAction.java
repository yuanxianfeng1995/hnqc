package action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javabean.Outbound;
import javabean.Student;

import com.opensymphony.xwork2.ActionSupport;

import dao.Canku;
import dao.Loginregister;
import dao.Unit;

public class OutboundAction extends ActionSupport {
	public Outbound outbound(String manufacturer,String name,Double price,Date date,String remark){
		Outbound a=new Outbound();
		 a.setManufacturer(manufacturer);
		 a.setName(name);
		 a.setPrice(price);
		 a.setPurchasedDate(date);
		 a.setRemark(remark);
		return a;
	}
	public void  write() throws Exception {
		// TODO Auto-generated method stub
		 HttpServletResponse response=ServletActionContext.getResponse();
		 HttpServletRequest request=ServletActionContext.getRequest();
		 response.setContentType("text/html;charset=utf-8");  
		 //response.setCharacterEncoding("UTF-8");  
		 response.setHeader("content-type", "text/html;charset=UTF-8");
		 String method = request.getMethod();//得到请求URL地址时使用的方法
		 String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
		 PrintWriter out = response.getWriter(); 
		 Canku cabku=new Canku();
		 if(method.equals("GET")){
			JSONObject json=cabku.query_Outbound("from Outbound");
			
			 out.println(json); 
		 }if(method.equals("POST")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("purchased_date"));
			 Outbound a=outbound(json2.getString("manufacturer"),json2.getString("name"),json2.getDouble("price"),date,json2.getString("remark"));
			 System.out.println(json2.getString("name"));
			 JSONObject json=cabku.addOutbound(a);
			 out.println(json);
		 }if(method.equals("PUT")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("purchased_date"));
			 Outbound a=outbound(json2.getString("manufacturer"),json2.getString("name"),json2.getDouble("price"),date,json2.getString("remark"));
			 System.out.println(json2.getString("name"));
			 JSONObject json=cabku.update(a);
			 out.println(json);
		 }
		 out.flush();  
		 out.close();  
	}
	
}
