package action;

import java.io.PrintWriter;

import java.net.URLDecoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javabean.Billentry;
import javabean.Billentry;

import com.opensymphony.xwork2.ActionSupport;

import dao.BillentryDao;
import dao.Canku;
import dao.Unit;

public class BillentryAction extends ActionSupport {
	public Billentry Billentry(String manufacturer,String name,Double price,Date date,String remark){
		Billentry a=new Billentry();
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
		 // response.setCharacterEncoding("UTF-8");  
		 response.setHeader("content-type", "text/html;charset=UTF-8");
		 String method = request.getMethod();//得到请求URL地址时使用的方法
		 String queryString=null;
		 if(request.getQueryString()!=null){
			 queryString = URLDecoder.decode(request.getQueryString(),"utf-8"); //得到请求的URL地址中附带的参数
		 }
		 PrintWriter out = response.getWriter(); 
		 System.out.println(queryString);
		 BillentryDao cabku=new BillentryDao();
		 if(method.equals("GET")){
			JSONObject json=null;
			String items[]=queryString.split("pageNo=0");
			String name=null;
			String value=null;
			if(items[items.length-1].indexOf("orderBy")==-1){
				String[] like=items[items.length-1].split("_like");
				name=like[0].split("&")[1];
				value=like[like.length-1].split("=")[1];
				json=cabku.query_Billentry("from Billentry where "+name+"=?",value);
			}else{
				json=cabku.query_Billentry("from Billentry");
			}
			 out.println(json); 
		 }if(method.equals("POST")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("purchased_date"));
			 Billentry a=Billentry(json2.getString("manufacturer"),json2.getString("name"),json2.getDouble("price"),date,json2.getString("remark"));
			 System.out.println(json2.getString("name"));
			 JSONObject json=cabku.addBillentry(a);
			 out.println(json);
		 }if(method.equals("PUT")){
			 Unit unit=new Unit();
			 String  str=unit.getRequestPayload(request);
			 JSONObject json2=new JSONObject(str);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			 Date date=sdf.parse(json2.getString("purchased_date"));
			 Billentry a=Billentry(json2.getString("manufacturer"),json2.getString("name"),json2.getDouble("price"),date,json2.getString("remark"));
			 System.out.println(json2.getString("name"));
			 if(queryString!=null || queryString!=""){
				 a.setId(Integer.parseInt(queryString));
			 }
			 JSONObject json=cabku.update(a);
			 out.println(json);
		 }if(method.equals("DELETE")){
			Billentry Billentry=cabku.retrun_Billentry("from Billentry where id=?",queryString);
			JSONObject json=cabku.deldate(Billentry);
			out.println(json);
		 }
		 out.flush();  
		 out.close();  
	}
	
}
