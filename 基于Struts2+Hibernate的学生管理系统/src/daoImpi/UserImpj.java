package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.User;
// �ֿ����
public interface UserImpj {
	/**
	 * ��������sql����
	 */
	public JSONObject query_User(String sql);
	
	/**
	 * ����������ҷ���list
	 */
	public JSONObject list_User(String sql,String value);
	/**
	 * ����������ҷ��ض���
	 */
	public JSONObject query_User(String sql,String value);
	/**
	 * ��ӳ��ⵥ
	 */
	public JSONObject addUser(User User);
	

	/**
	 * ���³��ⵥ
	 */
	public JSONObject update(User User);

	/**
	 * ɾ�����ⵥ
	 */
	public JSONObject deldate(int id);
}
