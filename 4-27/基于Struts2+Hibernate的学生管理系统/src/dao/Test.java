package dao;


import java.util.List;

import javabean.Banji;
import javabean.HibernateSessionFactory;
import javabean.Maintain;
import javabean.Outbound;
import javabean.Student;
import javabean.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Test {

	
	// һ�Զ� ���
	public static Banji cla1(){
		 Banji cla1=new Banji();
 		cla1.setName("398��");  
 	    Student st1=new Student();
 	    st1.setName("С��");
 	    Student st2=new Student();
 	    st2.setName("С��");
 	   st1.setBanji(cla1);
	    st2.setBanji(cla1);
	    cla1.getStudents().add(st1);
	    cla1.getStudents().add(st2);
	    return cla1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session;
		Transaction transaction;
		Query query;
		List list;
		HibernateSessionFactory fr=new HibernateSessionFactory();
    	try{  
    	    // ��ȡ����
    	    session=fr.getSession();
    		
    	    // �����Ự
    	    transaction= session.beginTransaction();
    	    
    	    //    	    ����ɾ��
//    	    User s=new User();
//    		s=(User)session.get(User.class,1);
//    		session.delete(s);
//    	    
    	    
//    	    query=session.createQuery("from Outbound where name=?");
//			query.setString(0, "��ɳ");
//			list = query.list();
//    	   query = session.createQuery("from Banji where id=?");
//       	   query.setInteger(0, 1);
//       	    Banji tbUser=(Banji) query.uniqueResult();
//    	    System.out.println(tbUser);
//    	    
    	    //    	    ����
//    	    Banji s=new Banji();
//    	    s.setId(5);
//    	    s.setName("С��a");
//    		session.update(s);
    	    
//   	                                                  ���
    	    
//    	    User s = (User)session.get(User.class,1);
//     	    Maintain st1=new Maintain();
//     	    st1.setContent("content");
//     	    st1.setUser(s);
//     	    session.save(st1);
//    	    s.getMaintains().add(st1);
//    	    session.save(s);
    	    session.save(cla1());
    		
    	    // �ύ����
    	    transaction.commit();  
    	    // ��������
    	    session.close(); 
    	}catch(Exception e){  
    	    e.printStackTrace();  
    	}
	}

}