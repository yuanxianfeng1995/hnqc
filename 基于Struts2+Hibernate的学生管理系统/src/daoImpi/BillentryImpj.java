package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Billentry;
// 仓库管理
public interface BillentryImpj {
	/**
	 * 输入完整sql查找
	 */
	public JSONObject query_Billentry(String sql);
	
	/**
	 * 输入参数查找返回list
	 */
	public JSONObject list_Billentry(String sql,String value);
	/**
	 * 输入参数查找返回对象
	 */
	public JSONObject query_Billentry(String sql,String value);
	/**
	 * 添加出库单
	 */
	public JSONObject addBillentry(Billentry Billentry);
	

	/**
	 * 更新出库单
	 */
	public JSONObject update(Billentry Billentry);

	/**
	 * 删除出库单
	 */
	public JSONObject deldate(int id);
}
