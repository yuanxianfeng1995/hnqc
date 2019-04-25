 package action;
 
 import com.opensymphony.xwork2.ActionSupport;
 import dao.Canku;
 import dao.Unit;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import javabean.Outbound;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.ServletActionContext;
 import org.json.JSONObject;
 
 
 public class OutboundAction extends ActionSupport
 {
   public Outbound outbound(String name, String manufacturer, Double price, Date purchasedDate, String remark, Integer number, String no, Double money, String addr)
   {
     Outbound a = new Outbound();
     a.setManufacturer(manufacturer);
     a.setName(name);
     a.setPrice(price);
     a.setPurchasedDate((Timestamp)purchasedDate);
     a.setNumber(number);
     a.setRemark(remark);
    a.setNo(no);
    a.setAddr(addr);
    a.setMoney(money);
    return a;
   }
   
   public void write()
     throws Exception
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
     Canku cabku = new Canku();
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
           json = cabku.query_Outbound("from Outbound where " + name + "=?", value);
         } else {
           String[] like = items[(items.length - 1)].split("_like");
           name = like[0].split("&")[1];
           value = like[(like.length - 1)].split("=")[1];
           json = cabku.list_Outbound("from Outbound where " + name + " like ?1", value);
         }
       } else {
         json = cabku.query_Outbound("from Outbound");
       }
       out.println(json);
     } else if (method.equals("POST")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = sdf.parse(json2.getString("purchasedDate"));       Outbound a = outbound(json2.getString("name"), json2.getString("manufacturer"), Double.valueOf(json2.getDouble("price")), (Timestamp)date, json2.getString("remark"), Integer.valueOf(json2.getInt("number")), json2.getString("no"), Double.valueOf(json2.getDouble("money")), json2.getString("addr"));
       JSONObject json = cabku.addOutbound(a);
       out.println(json);
     } else if (method.equals("PUT")) {
       Unit unit = new Unit();
       String str = unit.getRequestPayload(request);
       JSONObject json2 = new JSONObject(str);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = sdf.parse(json2.getString("purchasedDate"));
       Outbound a = outbound(json2.getString("name"), json2.getString("manufacturer"), Double.valueOf(json2.getDouble("price")), (Timestamp)date, json2.getString("remark"), Integer.valueOf(json2.getInt("number")), json2.getString("no"), Double.valueOf(json2.getDouble("money")), json2.getString("addr"));
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