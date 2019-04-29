package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Billentry;
// 仓库管理
public interface BillentryImpi {
	/**
	 * 输入完整sql查找
	 */
	public JSONObject query_Billentry(String sql);
	
	/**
	 * 输入参数查找
	 */
	public JSONObject query_Billentry(String sql,String value);
	
	/**
	 * 输入参数查找
	 */
	public Billentry retrun_Billentry(String sql,String value);
	/**
	 * 添加出库单
	 */
	public JSONObject addBillentry(Billentry billentry);
	

	/**
	 * 更新出库单
	 */
	public JSONObject update(Billentry billentry);

	/**
	 * 删除出库单
	 */
	public JSONObject deldate(Billentry billentry);
}
