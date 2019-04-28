package dao;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import javabean.HibernateSessionFactory;
import javabean.Outbound;

import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

public class Unit {
//	{
//		code: "0"
//		data: {totalCount: 10, pageSize: 30, pageNo: 0, orderBy: "f_no desc",…}
//		message: "处理成功。"
//		success: true
//	
//
	public JSONObject jsonListSucces(List data){
		JSONObject jsonObject = new JSONObject("{'code':'0','message':'处理成功','success': 'true'}");
		JSONObject items = new JSONObject("{'items':[],'pageNo':0,'orderBy':'id desc'}");
		items.put("items", data);
		items.put("pageSize", data.size());
		items.put("totalCount", data.size());
		jsonObject.put("data", items);
		return jsonObject;
	}
	public JSONObject jsonListSucces(List data,int count){
		JSONObject jsonObject = new JSONObject("{'code':'0','message':'处理成功','success': 'true'}");
		JSONObject items = new JSONObject("{'items':[],'pageNo':0,'orderBy':'id desc'}");
		items.put("items", data);
		items.put("pageSize", 30);
		items.put("totalCount", count);
		jsonObject.put("data", items);
		return jsonObject;
	}
public JSONObject jsonSucces(){
	JSONObject jsonObject = new JSONObject("{'code':'0','message':'处理成功','success': 'true'}");
	return jsonObject;
}
public JSONObject jsonError(){
	JSONObject jsonObject = new JSONObject("{'code':'-1','message':'处理失败','success': 'false'}");
	return jsonObject;
}
public String getRequestPayload(HttpServletRequest request) throws IOException {
    return IOUtils.toString(request.getReader());
}
}
