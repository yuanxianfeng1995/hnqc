package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.User;
// 仓库管理
public interface UserImpj {
	/**
	 * 输入完整sql查找
	 */
	public JSONObject query_User(String sql);
	
	/**
	 * 输入参数查找返回list
	 */
	public JSONObject list_User(String sql,String value);
	/**
	 * 输入参数查找返回对象
	 */
	public JSONObject query_User(String sql,String value);
	/**
	 * 添加出库单
	 */
	public JSONObject addUser(User User);
	

	/**
	 * 更新出库单
	 */
	public JSONObject update(User User);

	/**
	 * 删除出库单
	 */
	public JSONObject deldate(int id);
}
