 package dao;

 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
import java.util.HashSet;
 import java.util.List;
import java.util.Set;

import javabean.Commodity;
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

   public JSONObject query_Outbound(String sql)
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
       String commodityId=tbUser.getCommodityId();
       String[] a=commodityId.split(",");
       for (int i = 0; i < a.length; i++) {
    	   Commodity commodity=(Commodity)session.get(Commodity.class, Integer.valueOf(a[i]));
    	   JSONObject json3 = new JSONObject(commodity);
    	   jsonArray1.put(json3);
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

   public JSONObject addOutbound(Outbound Outbound,JSONArray equipmentDetailList)
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
