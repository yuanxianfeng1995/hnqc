 package action;
 

import com.opensymphony.xwork2.ActionSupport;





import dao.MaintainDao;
 import dao.Unit;


 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.text.SimpleDateFormat;

 import java.util.Date;



 import javabean.Maintain;



 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.ServletActionContext;

import org.json.JSONObject;
 
 
 public class MaintainAction extends ActionSupport
 {
	
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
     MaintainDao cabku = new MaintainDao();
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
           json = cabku.query_Maintain("from Maintain where " + name + "=?", value);
         } else {
           String[] like = items[(items.length - 1)].split("_like");
           name = like[0].split("&")[1];
           value = like[(like.length - 1)].split("=")[1];
           json = cabku.list_Maintain("from Maintain where " + name + " like ?1", value);
         }
       } else {
    	   String[] pageNo = queryString.split("pageNo=");
      	 int count=cabku.count();
      	 json=cabku.paging_Maintain(count,Integer.valueOf(pageNo[1]));
      
       }
       out.println(json);
     } else if (method.equals("POST")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       System.out.println("json2 ---"+json2);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = sdf.parse(json2.getString("compulsoryInsurance"));
       Maintain a =new Maintain(json2.getString("license"),json2.getString("mileage"),date,json2.getString("content"),json2.getInt("userId"),json2.getString("userName"));
      JSONObject json = cabku.addMaintain(a);
       out.println(json);
     } else if (method.equals("PUT")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = sdf.parse(json2.getString("compulsoryInsurance"));
       
//   	public Maintain(User user, String license, String mileage,
//			Date compulsoryInsurance, String content) {
       Maintain a =new Maintain(json2.getString("license"),json2.getString("mileage"),date,json2.getString("content"),json2.getInt("userId"),json2.getString("userName"));
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