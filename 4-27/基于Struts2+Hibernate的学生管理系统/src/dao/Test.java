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

	
	// 一对多 添加
	public static Banji cla1(){
		 Banji cla1=new Banji();
 		cla1.setName("398班");  
 	    Student st1=new Student();
 	    st1.setName("小罗");
 	    Student st2=new Student();
 	    st2.setName("小蓉");
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
    	    // 获取事务
    	    session=fr.getSession();
    		
    	    // 开启会话
    	    transaction= session.beginTransaction();
    	    
    	    //    	    级联删除
//    	    User s=new User();
//    		s=(User)session.get(User.class,1);
//    		session.delete(s);
//    	    
    	    
//    	    query=session.createQuery("from Outbound where name=?");
//			query.setString(0, "长沙");
//			list = query.list();
//    	   query = session.createQuery("from Banji where id=?");
//       	   query.setInteger(0, 1);
//       	    Banji tbUser=(Banji) query.uniqueResult();
//    	    System.out.println(tbUser);
//    	    
    	    //    	    更新
//    	    Banji s=new Banji();
//    	    s.setId(5);
//    	    s.setName("小蓉a");
//    		session.update(s);
    	    
//   	                                                  添加
    	    
//    	    User s = (User)session.get(User.class,1);
//     	    Maintain st1=new Maintain();
//     	    st1.setContent("content");
//     	    st1.setUser(s);
//     	    session.save(st1);
//    	    s.getMaintains().add(st1);
//    	    session.save(s);
    	    session.save(cla1());
    		
    	    // 提交事务
    	    transaction.commit();  
    	    // 结束事务
    	    session.close(); 
    	}catch(Exception e){  
    	    e.printStackTrace();  
    	}
	}

}