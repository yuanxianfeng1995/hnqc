package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Billentry;
// �ֿ����
public interface BillentryImpi {
	/**
	 * ��������sql����
	 */
	public JSONObject query_Billentry(String sql);
	
	/**
	 * �����������
	 */
	public JSONObject query_Billentry(String sql,String value);
	
	/**
	 * �����������
	 */
	public Billentry retrun_Billentry(String sql,String value);
	/**
	 * ��ӳ��ⵥ
	 */
	public JSONObject addBillentry(Billentry billentry);
	

	/**
	 * ���³��ⵥ
	 */
	public JSONObject update(Billentry billentry);

	/**
	 * ɾ�����ⵥ
	 */
	public JSONObject deldate(Billentry billentry);
}
