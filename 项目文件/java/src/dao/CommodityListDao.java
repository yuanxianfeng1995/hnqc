 package dao;
 
 import daoImpi.CangkuImpi;
 import java.io.PrintStream;
 import java.util.List;
 import javabean.HibernateSessionFactory;
 import javabean.Commoditylist;
 import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.Transaction;
import org.json.JSONObject;
 
 public  class CommodityListDao
 {
   private Session session;
   private Transaction transaction;
   private Query query;
   private List<Commoditylist> list;

   public JSONObject paging_Commoditylist(int count,int i)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     int sum=i*30;
     System.out.println("sum-----"+sum);
     Unit unit = new Unit();
     JSONObject json = null;
     try {
       query = session.createQuery("from Commoditylist order by id desc");
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
		   String hql = "select count(*) from Commoditylist";  
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
   public JSONObject list_Commoditylist(String sql, String value)
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
   
   public JSONObject query_Commoditylist(String sql, String value)
   {
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     JSONObject json = null;
     Unit unit = new Unit();
     try {
       query = session.createQuery(sql);
       query.setString(0, value);
       Commoditylist tbUser = (Commoditylist)query.uniqueResult();
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
   
   public int addCommoditylist(Commoditylist Commoditylist)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     int id=0;
     System.out.println("addCommoditylist ---"+Commoditylist);
     try {  
       id = (Integer)session.save(Commoditylist);
       System.out.println("id ---"+id);
       transaction.commit();
       session.close();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     
     return id;
   }
   
   public JSONObject update(Commoditylist Commoditylist)
   {
     Unit unit = new Unit();
     JSONObject json = new JSONObject();
     session = HibernateSessionFactory.getSession();
     transaction = session.beginTransaction();
     try {
       session.update(Commoditylist);
       json = unit.jsonSucces();
       json.put("data", Commoditylist);
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
       Commoditylist s = new Commoditylist();
       System.out.println(id);
       s = (Commoditylist)session.get(Commoditylist.class, Integer.valueOf(id));
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