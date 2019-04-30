 package dao;


 import java.util.List;

import javabean.Commoditylist;
 import javabean.HibernateSessionFactory;
 import javabean.Outbound;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

 public  class Canku
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Outbound> list;
   /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		     Unit unit = new Unit();
		     Query query=null;
		     List<Outbound> list=null;
		     JSONObject json = null;
      try {
        query = session.createQuery("from Outbound");
        query.setFirstResult(30);
        query.setMaxResults(60);
        list = query.list();
        json = unit.jsonListSucces(list,36);
        System.out.println("json-------"+json);
   	    // 提交事务
   	    transaction.commit();  
   	    // 结束事务
   	    session.close(); 
   	}catch(Exception e){  
   	    e.printStackTrace();  
	}
   	}
   public JSONObject paging_Outbound(int count,int i)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     int sum=i*30;
     System.out.println("sum-----"+sum);
     Unit unit = new Unit();
     JSONObject json = null;
     try {
       query = session.createQuery("from Outbound order by id desc");
       query.setFirstResult(sum);
       query.setMaxResults(sum+30);
       list = query.list();
       json = unit.jsonListSucces(list,count);
       System.out.println("json-------"+json);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return json;
	 }
   public int count(){
	   session = HibernateSessionFactory.getSession();
	     transaction = session.beginTransaction();
	     int count=0;
	   try {
		   String hql = "select count(*) from Outbound";  
	       Query query2 = session.createQuery(hql);
	       Object obj=query2.uniqueResult();
	       Long lobj=(Long) obj;
	       count=lobj.intValue();
	       transaction.commit();
	       session.close();
	     }
	     catch (Exception e) {
	       e.printStackTrace();
	     }
	   return count;
   }
   public JSONObject list_Outbound(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     Unit unit = new Unit();
     JSONObject json = null;
     try {
      query = session.createQuery(sql);
       query.setString("1", "%" + value + "%");
       list = query.list();
       json = unit.jsonListSucces(list);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonSucces();
       json.put("data", e);
     }
     return json;
   }

   public JSONObject query_Outbound(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     JSONArray jsonArray1 = new JSONArray();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       Outbound tbUser = (Outbound)query.uniqueResult();
       String CommoditylistId=tbUser.getCommodityId();
       if(CommoditylistId!=null){
    	   if(CommoditylistId.indexOf(",")!=-1){
	    	   String[] a=CommoditylistId.split(",");
	           for (int i = 0; i < a.length; i++) {
	        	   Commoditylist Commoditylist=(Commoditylist)session.get(Commoditylist.class, Integer.valueOf(a[i]));
	        	   JSONObject json3 = new JSONObject(Commoditylist);
	        	   jsonArray1.put(json3);
	    	   }
    	   }else{
    		   Commoditylist Commoditylist=(Commoditylist)session.get(Commoditylist.class, Integer.valueOf(CommoditylistId));
        	   JSONObject json3 = new JSONObject(Commoditylist);
        	   jsonArray1.put(json3);
    	   }
       }
       JSONObject json2 = new JSONObject(tbUser);
       json2.put("equipmentDetailList", jsonArray1);
       json = unit.jsonSucces();
       json.put("data", json2);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }
     return json;
   }

   public JSONObject addOutbound(Outbound Outbound)
   {
	   Unit unit = new Unit();
	     JSONObject json = new JSONObject();
	     session = HibernateSessionFactory.getSession();
	     transaction = session.beginTransaction();
	     try {
	       session.save(Outbound);
	       json = unit.jsonSucces();
	       json.put("data", Outbound);
	       transaction.commit();
	       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }

     return json;
   }

   public JSONObject update(Outbound Outbound)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(Outbound);
       json = unit.jsonSucces();
       json.put("data", Outbound);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }

     return json;
   }

   public JSONObject deldate(int id)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       Outbound s = new Outbound();
       System.out.println(id);
       s = (Outbound)session.get(Outbound.class, Integer.valueOf(id));
       session.delete(s);
       json = unit.jsonSucces();
       json.put("data", s);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }
     return json;
   }
 }
