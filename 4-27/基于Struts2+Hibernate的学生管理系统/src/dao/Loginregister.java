package dao;
import java.util.List;


import javabean.HibernateSessionFactory;
import javabean.Student;
import javabean.Userinfo;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Session;

public class Loginregister {
private Session session;
private Transaction transaction;
private Query query;
private List list;
HibernateSessionFactory fr=new HibernateSessionFactory();
public Loginregister()
{
	
}
public String saveInfo(Student a)
{
	String mess="error";
	try {
	session=fr.getSession();
	transaction= session.beginTransaction();
	session.save(a);
	
		transaction.commit();
		mess="success";
		session.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return mess;
	
}
public String savezhuce(Userinfo a)
{
	String mess="error";
	try {
	session=fr.getSession();
	transaction= session.beginTransaction();
	session.save(a);
	
		transaction.commit();
		mess="success";
		session.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return mess;
	
}
public List queryInfo(String name)

{
	try {
	session=fr.getSession();
	
	String sql="from Userinfo where name=?";
	query=session.createQuery(sql);
	query.setString(0, name);
	 list=query.list();
	transaction=session.beginTransaction();
	transaction.commit();
	session.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return list;
	
}

public List chaozhaoSd(String xuehao)

{
	try {
	session=fr.getSession();
	
	String sql="from Student where xuehao=?";
	query=session.createQuery(sql);
	query.setString(0, xuehao);
	 list=query.list();
	transaction=session.beginTransaction();
	transaction.commit();
	session.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return list;
	
}
public List Allcy()

{
	try {
	session=fr.getSession();
	String sql="from Student ";
	query=session.createQuery(sql);
	 list=query.list();
	transaction=session.beginTransaction();
	transaction.commit();
	session.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return list;
	
}
public boolean deteStudent(int id)
{
	try {
		session=fr.getSession();
		transaction=session.beginTransaction();
		Student s=new Student();
		s=(Student)session.get(Student.class,id);
		session.delete(s);
		transaction.commit();
		session.close();
		return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
}
public boolean updeteStudent(Student info)
{
	try {
		session=fr.getSession();
		transaction=session.beginTransaction();
		session.update(info);
		transaction.commit();
		session.close();
		return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
}
}
