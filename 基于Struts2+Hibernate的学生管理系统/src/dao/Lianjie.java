package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
public class Lianjie {
static {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static Connection getcon() 
{
	Connection conn=null;
	try {
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf8","root","123456");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return conn;
	
}
}
