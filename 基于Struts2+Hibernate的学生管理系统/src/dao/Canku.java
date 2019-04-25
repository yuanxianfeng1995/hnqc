 package dao;
 
 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
 import java.util.List;
 import javabean.HibernateSessionFactory;
 import javabean.Outbound;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
 import org.json.JSONObject;
 
 public  class Canku
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Outbound> list;
   
   public JSONObject query_Outbound(String sql)
   {
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     Unit unit = new Unit();
     JSONObject json = null;
     try {
       this.query = this.session.createQuery(sql);
       this.list = this.query.list();
       json = unit.jsonListSucces(this.list);
       this.transaction.commit();
       this.session.close();
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
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     Unit unit = new Unit();
     JSONObject json = null;
     try {
      this.query = this.session.createQuery(sql);
       this.query.setString("1", "%" + value + "%");
       this.list = this.query.list();
       json = unit.jsonListSucces(this.list);
       this.transaction.commit();
       this.session.close();
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
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       this.query = this.session.createQuery(sql);
       this.query.setString(0, value);
       Outbound tbUser = (Outbound)this.query.uniqueResult();
       JSONObject json2 = new JSONObject(tbUser);
       System.out.println(json2);
       json = unit.jsonSucces();
       json.put("data", json2);
       this.transaction.commit();
       this.session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }
     return json;
   }
   
   public JSONObject addOutbound(Outbound outbound)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     try {
       this.session.save(outbound);
       json = unit.jsonSucces();
       json.put("data", outbound);
       this.transaction.commit();
       this.session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }
     
     return json;
   }
   
   public JSONObject update(Outbound outbound)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     try {
       this.session.update(outbound);
       json = unit.jsonSucces();
       json.put("data", outbound);
       this.transaction.commit();
       this.session.close();
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
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     try {
       Outbound s = new Outbound();
       System.out.println(id);
       s = (Outbound)this.session.get(Outbound.class, Integer.valueOf(id));
       this.session.delete(s);
       json = unit.jsonSucces();
       json.put("data", s);
       this.transaction.commit();
       this.session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
       json = unit.jsonError();
       json.put("data", e);
     }
     return json;
   }
 }