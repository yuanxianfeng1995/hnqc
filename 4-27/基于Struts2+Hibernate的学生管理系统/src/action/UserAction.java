 package action;
 

import com.opensymphony.xwork2.ActionSupport;



import dao.Canku;
 import dao.Unit;
import dao.UserDao;

 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.text.SimpleDateFormat;
import java.util.ArrayList;
 import java.util.Date;
import java.util.List;
import java.util.Set;

import javabean.Commodity;
import javabean.HibernateSessionFactory;
 import javabean.User;


 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
 
 
 public class UserAction extends ActionSupport
 {
	 /**
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Session session;
			Transaction transaction;
			Query query;
			List list;
			HibernateSessionFactory fr=new HibernateSessionFactory();
	   	try{  
	   	    // 获取事务
	   	    session=fr.getSession();
	   		
	   	    // 开启会话
	   	    transaction= session.beginTransaction();
	   	    
	   	    //    	    级联删除
//	   	    Banji s=new Banji();
//	   		s=(Banji)session.get(Banji.class,6);
//	   		session.delete(s);
	   	    
	   	    
//	   	    query=session.createQuery("from  User where id=?");
//				query.setInteger(0, 1);
//					
//				User tbUser = (User)query.uniqueResult();
//			       JSONObject json2 = new JSONObject(query.uniqueResult());
//			  
//			       json2.put("equipmentDetailList", commodity);
//			       System.out.println(json2);
//	   	   query = session.createQuery("from Banji where id=?");
//	      	   query.setInteger(0, 5);
//				User tbUser=(User) query.uniqueResult();
//	   	    System.out.println(tbUser);

	   	    //    	    更新
//	   	    Banji s=new Banji();
//	   	    s.setId(5);
//	   	    s.setName("小蓉a");
//	   		session.update(s);
	   	    
//	  	                                                  添加
//	   	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	      Date date = sdf.parse("2018-02-04");  
	     // Commodity(String name, String manufacturer, Double price, Date purchasedDate, String remark, Integer number, String no, Double money)
//	      Commodity a= Commodity("name", "manufacturer", 15.2,date,"remark", 1, "no", 222.2);
//	        session.save(a);
	   		
	   	    // 提交事务
	   	    transaction.commit();  
	   	    // 结束事务
	   	    session.close(); 
	   	}catch(Exception e){  
	   	    e.printStackTrace();  
	   	}
		}
   public void write() throws Exception
   {
     HttpServletResponse response = ServletActionContext.getResponse();
     HttpServletRequest request = ServletActionContext.getRequest();
     response.setContentType("text/html;charset=utf-8");
     
     response.setHeader("content-type", "text/html;charset=UTF-8");
     String method = request.getMethod();
     String queryString = null;
     if (request.getQueryString() != null) {
       queryString = URLDecoder.decode(request.getQueryString(), "utf-8");
     }
     PrintWriter out = response.getWriter();
     System.out.println("queryString:------" + queryString);
     UserDao cabku = new UserDao();
     if (method.equals("GET")) {
       JSONObject json = null;
       String[] items = queryString.split("pageNo=0");
       String name = null;
       String value = null;
       if (items[(items.length - 1)].indexOf("orderBy") == -1) {
         if (items[(items.length - 1)].indexOf("_like") == -1) {
           String like = items[(items.length - 1)];
           String a = like.split("&")[1];
          value = a.split("=")[1];
           name = a.split("=")[0];
           json = cabku.query_User("from User where " + name + "=?", value);
         } else {
           String[] like = items[(items.length - 1)].split("_like");
           name = like[0].split("&")[1];
           value = like[(like.length - 1)].split("=")[1];
           json = cabku.list_User("from User where " + name + " like ?1", value);
         }
       } else {
         json = cabku.query_User("from User");
       }
       out.println(json);
     } else if (method.equals("POST")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       System.out.println("json2 ---"+json2);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
       Date date = sdf.parse(json2.getString("birthday"));  
       User a =new User(json2.getString("name"),json2.getString("sex"),json2.getString("age"),date,json2.getString("remark"),
    		   json2.getString("phone"),json2.getString("level"),null);
      System.out.println("POST------");
      JSONObject json = cabku.addUser(a);
       out.println(json);
     } else if (method.equals("PUT")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = sdf.parse(json2.getString("birthday"));
       
//       User(String name, String sex, String age, Date birthday,
//   			String remark, String phone, String level, Set maintains) {
       User a =new User(json2.getString("name"),json2.getString("sex"),json2.getString("age"),date,json2.getString("remark"),
    		   json2.getString("phone"),json2.getString("level"),null);
        if ((queryString != null) || (queryString != "")) {
         a.setId(Integer.valueOf(Integer.parseInt(queryString)));
       }
       JSONObject json3 = new JSONObject(a);
       System.out.println("json3:-----" + json3);
       JSONObject json = cabku.update(a);
       out.println(json);
     } else {
       JSONObject json = cabku.deldate(Integer.parseInt(queryString));
       out.println(json);
     }
     out.flush();
     out.close();
   }
 }