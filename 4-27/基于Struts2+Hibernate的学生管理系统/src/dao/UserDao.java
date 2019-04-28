 package dao;
 import java.util.List;

 import javabean.HibernateSessionFactory;
 import javabean.User;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

 public  class UserDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<User> list;

   public JSONObject query_User(String sql)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     Unit unit = new Unit();
     JSONObject json = null;
     try {
       query = session.createQuery(sql);
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

   public JSONObject list_User(String sql, String value)
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

   public JSONObject query_User(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       User tbUser = (User)query.uniqueResult();
       JSONObject json2 = new JSONObject(tbUser);
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

   public JSONObject addUser(User User)
   {
	   Unit unit = new Unit();
	     JSONObject json = new JSONObject();
	     session = HibernateSessionFactory.getSession();
	     transaction = session.beginTransaction();
	     try {
	       session.save(User);
	       json = unit.jsonSucces();
	       json.put("data", User);
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

   public JSONObject update(User User)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(User);
       json = unit.jsonSucces();
       json.put("data", User);
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
       User s = new User();
       System.out.println(id);
       s = (User)session.get(User.class, Integer.valueOf(id));
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
