package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Outbound;
// 仓库管理
public interface CangkuImpi {
	/**
	 * 输入完整sql查找
	 */
	public JSONObject query_Outbound(String sql);
	
	/**
	 * 添加出库单
	 */
	public JSONObject addOutbound(Outbound outbound);
	

	/**
	 * 更新出库单
	 */
	public JSONObject update(Outbound outbound);

	/**
	 * 删除出库单
	 */
	public JSONObject deldate(Outbound outbound);
}
