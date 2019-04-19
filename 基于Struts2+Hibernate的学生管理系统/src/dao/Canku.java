package dao;

import java.util.List;


import javax.xml.crypto.Data;


import javabean.HibernateSessionFactory;
import javabean.Outbound;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Array;
import org.json.JSONObject;



import daoImpi.CangkuImpi;
import dao.Unit;
public class Canku implements CangkuImpi{

	private Session session;
	private Transaction transaction;
	private Query query;
	private List<Outbound> list;
	HibernateSessionFactory fr=new HibernateSessionFactory();
	public Canku()
	{
		
	}
	/**
	 * @param args
	 */
	@Override
	public JSONObject query_Outbound(String sql) {
		// TODO Auto-generated method stub
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
		Unit unit=new Unit();
		JSONObject json=null;
		try {
			query=session.createQuery(sql);
			list=query.list();
			json=unit.jsonListSucces(list);
			transaction.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json=unit.jsonSucces();
			json.put("data", e);
		}
		return json;
	}
	@Override
	public JSONObject query_Outbound(String sql,String name,String value) {
		// TODO Auto-generated method stub
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
		JSONObject json=null;
		Unit unit=new Unit();
		System.out.println("from Outbound where "+name+"=?");
		try {
			query = session.createQuery("from Outbound where "+name+"=?");
			System.out.println("from Outbound where "+name+"=?");
			query.setString(0, value);
//			Outbound tbUser=(Outbound) query.uniqueResult();
			list = (List<Outbound>) query.uniqueResult();
			System.out.println(list);
			json=unit.jsonListSucces(list);
			transaction.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json=unit.jsonError();
			json.put("data", e);
		}
		return json;
	}
	@Override
	public JSONObject addOutbound(Outbound outbound) {
		// TODO Auto-generated method stub
		Unit unit=new Unit();
		JSONObject json=new JSONObject();
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
		try {
			session.save(outbound);
			json=unit.jsonSucces();
			json.put("data", outbound);
			transaction.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json=unit.jsonError();
				json.put("data", e);
			}
		
		return json;
	}
	@Override
	public JSONObject update(Outbound outbound) {
		// TODO Auto-generated method stub
		Unit unit=new Unit();
		JSONObject json=new JSONObject();
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
		try {
			session.update(outbound);
			json=unit.jsonSucces();
			json.put("data", outbound);
			transaction.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json=unit.jsonError();
				json.put("data", e);
			}
		
		return json;
	}
	@Override
	public JSONObject deldate(Outbound outbound) {
		// TODO Auto-generated method stub
		Unit unit=new Unit();
		JSONObject json=new JSONObject();
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
		try {
			session.delete(outbound);
			json=unit.jsonSucces();
			json.put("data", outbound);
			transaction.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json=unit.jsonError();
				json.put("data", e);
			}
		
		return json;
	}

	

}
