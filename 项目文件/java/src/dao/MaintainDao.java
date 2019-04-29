 package dao;
 import java.util.List;

 import javabean.HibernateSessionFactory;
 import javabean.Maintain;
import javabean.User;

 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

 public  class MaintainDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Maintain> list;

   public JSONObject paging_Maintain(int count,int i)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     int sum=i*30;
     System.out.println("mAIN sum-----"+sum);
     Unit unit = new Unit();
     JSONObject json = null;
     try {
       query = session.createQuery("from  Maintain order by id desc");
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
		   String hql = "select count(*) from Maintain";  
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

   public JSONObject list_Maintain(String sql, String value)
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

   public JSONObject query_Maintain(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       Maintain tbMaintain = (Maintain)query.uniqueResult();
       JSONObject json2 = new JSONObject(tbMaintain);
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

   public JSONObject addMaintain(Maintain Maintain)
   {
	   Unit unit = new Unit();
	     JSONObject json = new JSONObject();
	     session = HibernateSessionFactory.getSession();
	     transaction = session.beginTransaction();
	     try {
	       session.save(Maintain);
	       json = unit.jsonSucces();
	       JSONObject json2 = new JSONObject(Maintain);
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

   public JSONObject update(Maintain Maintain)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(Maintain);
       json = unit.jsonSucces();
       json.put("data", Maintain);
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
       Maintain s = new Maintain();
       System.out.println(id);
       s = (Maintain)session.get(Maintain.class, Integer.valueOf(id));
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
   public JSONObject deldateUserId(int id)
   {
	   System.out.println("deldateUserId-----id="+id);
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       System.out.println(id);
       String hql = "select p from Maintain as p where p.userId=?";
       Query query = session.createQuery(hql);
       query.setInteger(0, id);
       Maintain s = (Maintain)query.list().get(0);
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
