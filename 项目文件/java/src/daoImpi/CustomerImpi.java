package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.User;
// �ֿ����
public interface CustomerImpi {
	/**
	 * ��������sql����
	 */
	public JSONObject query_User(String sql);
	
	/**
	 * �����������
	 */
	public JSONObject query_User(String sql,String value);
	
	/**
	 * �����������
	 */
	public User retrun_User(String sql,String value);
	/**
	 * ��ӳ��ⵥ
	 */
	public JSONObject addUser(User user);
	

	/**
	 * ���³��ⵥ
	 */
	public JSONObject update(User user);

	/**
	 * ɾ�����ⵥ
	 */
	public JSONObject deldate(User user);
}
