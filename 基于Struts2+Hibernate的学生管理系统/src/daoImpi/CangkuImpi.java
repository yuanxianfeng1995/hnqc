package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Outbound;
// �ֿ����
public interface CangkuImpi {
	/**
	 * ��������sql����
	 */
	public JSONObject query_Outbound(String sql);
	
	/**
	 * �����������
	 */
	public JSONObject query_Outbound(String sql,String name,String value);
	/**
	 * ��ӳ��ⵥ
	 */
	public JSONObject addOutbound(Outbound outbound);
	

	/**
	 * ���³��ⵥ
	 */
	public JSONObject update(Outbound outbound);

	/**
	 * ɾ�����ⵥ
	 */
	public JSONObject deldate(Outbound outbound);
}
