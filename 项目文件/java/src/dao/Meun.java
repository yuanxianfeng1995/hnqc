package dao;

import org.json.JSONArray;
import org.json.JSONObject;

// 菜单页
public class Meun {
public JSONObject JSONObject(){
	JSONObject CKGLCKD=new JSONObject("{'children':[], 'f_desc':'出库单', 'f_full_path':'/ROOT/CKGL/CKGL-CKD/', 'f_icon':null, 'f_id':'CKGL-CKD', 'f_name':'出库单', 'f_order':10, 'f_parent_id':'CKGL', 'f_parent_name':'仓库管理', 'f_parent_path':'/ROOT/CKGL/', 'f_remark':null, 'f_route_path':'/admin/warehouse/outbound', 'f_status':101, 'f_type':2, 'urlList':null}");
	JSONObject CKGLRKD=new JSONObject("{'children':[], 'f_desc':'入库单', 'f_full_path':'/ROOT/CKGL/CKGL-RKD/', 'f_icon':null, 'f_id':'CKGL-RKD', 'f_name':'入库单', 'f_order':20, 'f_parent_id':'CKGL', 'f_parent_name':'仓库管理', 'f_parent_path':'/ROOT/CKGL/', 'f_remark':null, 'f_route_path':'/admin/warehouse/billentry', 'f_status':101, 'f_type':2, 'urlList':null}");
	JSONObject CKGL=new JSONObject("{'children':[],'f_desc':'仓库管理', 'f_full_path':'/ROOT/CKGL/', 'f_icon':null, 'f_id':'CKGL', 'f_is_android':102, 'f_is_ios':102, 'f_is_web':101, 'f_name':'仓库管理', 'f_order':1005, 'f_parent_id':'ROOT', 'f_parent_name':'系统菜单', 'f_parent_path':'/ROOT/', 'f_remark':null, 'f_route_path':null, 'f_status':101, 'f_type':1, 'urlList':null}");
	JSONArray jsonArray1 = new JSONArray();
	jsonArray1.put(CKGLCKD);
	jsonArray1.put(CKGLRKD);
	CKGL.put("children", jsonArray1);

	JSONObject KFGL=new JSONObject("{'f_desc':'客户管理', 'f_full_path':'/ROOT/KFGL/', 'f_icon':null, 'f_id':'KFGL', 'f_name':'客户管理', 'f_order':1015, 'f_parent_id':'ROOT', 'f_parent_name':'系统菜单', 'f_parent_path':'/ROOT/', 'f_remark':null, 'f_route_path':null, 'f_status':101, 'f_type':1, 'urlList':null, 'children':''}");
	JSONObject KFGLKHTZ=new JSONObject("{'children':[], 'f_desc':'客户台账', 'f_full_path':'/ROOT/KFGL/KFGL-KHTZ/', 'f_icon':null, 'f_id':'KFGL-KHTZ', 'f_name':'客户台账', 'f_order':10, 'f_parent_id':'KFGL', 'f_parent_name':'客户管理', 'f_parent_path':'/ROOT/KFGL/', 'f_remark':null, 'f_route_path':'/admin/customer', 'f_status':101, 'f_type':2, 'urlList':null}");
	JSONArray jsonArray2 = new JSONArray();
	jsonArray2.put(KFGLKHTZ);
	KFGL.put("children", jsonArray2);

	JSONObject menuList=new JSONObject("{'menuList':'[]'}");
	JSONArray jsonArray3 = new JSONArray();
	jsonArray3.put(CKGL);
	jsonArray3.put(KFGL);
	menuList.put("menuList", jsonArray3);
	JSONObject ROOT=new JSONObject("{'code':'0','message':'系统处理成功','success': 'true','data':[]}");
	return ROOT.put("data", menuList);
}
}
