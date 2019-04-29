package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.User;
// 仓库管理
public interface CustomerImpi {
	/**
	 * 输入完整sql查找
	 */
	public JSONObject query_User(String sql);
	
	/**
	 * 输入参数查找
	 */
	public JSONObject query_User(String sql,String value);
	
	/**
	 * 输入参数查找
	 */
	public User retrun_User(String sql,String value);
	/**
	 * 添加出库单
	 */
	public JSONObject addUser(User user);
	

	/**
	 * 更新出库单
	 */
	public JSONObject update(User user);

	/**
	 * 删除出库单
	 */
	public JSONObject deldate(User user);
}
