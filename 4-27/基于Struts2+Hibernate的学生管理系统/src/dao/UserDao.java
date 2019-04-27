 package dao;
 
 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
 import java.util.List;
 import javabean.HibernateSessionFactory;
 import javabean.User;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
 import org.json.JSONObject;
 
 public  class UserDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<User> list;
   
   public JSONObject query_User(String sql)
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
   
   public JSONObject list_User(String sql, String value)
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
   
   public JSONObject query_User(String sql, String value)
   {
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       this.query = this.session.createQuery(sql);
       this.query.setString(0, value);
       User tbUser = (User)this.query.uniqueResult();
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
   
   public JSONObject addUser(User User)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     try {
       this.session.save(User);
       json = unit.jsonSucces();
       json.put("data", User);
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
   
   public JSONObject update(User User)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     this.session = HibernateSessionFactory.getSession();
     this.transaction = this.session.beginTransaction();
     try {
       this.session.update(User);
       json = unit.jsonSucces();
       json.put("data", User);
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
       User s = new User();
       System.out.println(id);
       s = (User)this.session.get(User.class, Integer.valueOf(id));
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