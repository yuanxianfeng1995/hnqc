 package dao;

 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
import java.util.HashSet;
 import java.util.List;
import java.util.Set;

import javabean.Commodity;
 import javabean.HibernateSessionFactory;
 import javabean.Billentry;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

 public  class BillentryDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Billentry> list;

   public JSONObject query_Billentry(String sql)
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

   public JSONObject list_Billentry(String sql, String value)
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

   public JSONObject query_Billentry(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     JSONArray jsonArray1 = new JSONArray();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       Billentry tbUser = (Billentry)query.uniqueResult();
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

   public JSONObject addBillentry(Billentry Billentry,JSONArray equipmentDetailList)
   {
	   Unit unit = new Unit();
	     JSONObject json = new JSONObject();
	     session = HibernateSessionFactory.getSession();
	     transaction = session.beginTransaction();
	     try {
	       session.save(Billentry);
	       json = unit.jsonSucces();
	       json.put("data", Billentry);
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

   public JSONObject update(Billentry Billentry)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(Billentry);
       json = unit.jsonSucces();
       json.put("data", Billentry);
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
       Billentry s = new Billentry();
       System.out.println(id);
       s = (Billentry)session.get(Billentry.class, Integer.valueOf(id));
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
