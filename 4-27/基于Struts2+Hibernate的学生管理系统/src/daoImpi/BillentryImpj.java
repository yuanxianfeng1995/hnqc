package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Billentry;
// �ֿ����
public interface BillentryImpj {
	/**
	 * ��������sql����
	 */
	public JSONObject query_Billentry(String sql);
	
	/**
	 * ����������ҷ���list
	 */
	public JSONObject list_Billentry(String sql,String value);
	/**
	 * ����������ҷ��ض���
	 */
	public JSONObject query_Billentry(String sql,String value);
	/**
	 * ��ӳ��ⵥ
	 */
	public JSONObject addBillentry(Billentry Billentry);
	

	/**
	 * ���³��ⵥ
	 */
	public JSONObject update(Billentry Billentry);

	/**
	 * ɾ�����ⵥ
	 */
	public JSONObject deldate(int id);
}
