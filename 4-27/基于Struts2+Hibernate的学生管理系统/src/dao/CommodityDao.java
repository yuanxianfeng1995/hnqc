 package dao;
 
 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
 import java.util.List;
 import javabean.HibernateSessionFactory;
 import javabean.Commodity;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONObject;
 
 public  class CommodityDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Commodity> list;

   public JSONObject query_Commodity(String sql)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     Unit unit = new Unit();
     JSONObject json = null;
     try {
    	  query = session.createQuery(sql);
          query.setFetchSize(0);
          query.setMaxResults(30);
          list = query.list();
          Query query2 = session.createQuery("select count(*) "+sql);
          Object obj=query2.uniqueResult();
          Long lobj=(Long)obj;
          int count=lobj.intValue();
          json = unit.jsonListSucces(list,count);
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
   
   public JSONObject list_Commodity(String sql, String value)
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
   
   public JSONObject query_Commodity(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       Commodity tbUser = (Commodity)query.uniqueResult();
       JSONObject json2 = new JSONObject(tbUser);
       System.out.println(json2);
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
   
   public JSONObject addCommodity(Commodity Commodity)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     System.out.println("addCommodity ---"+Commodity);
     try {
       session.save(Commodity);
       json = unit.jsonSucces();
       json.put("data", Commodity);
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
   
   public JSONObject update(Commodity Commodity)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(Commodity);
       json = unit.jsonSucces();
       json.put("data", Commodity);
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
       Commodity s = new Commodity();
       System.out.println(id);
       s = (Commodity)session.get(Commodity.class, Integer.valueOf(id));
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