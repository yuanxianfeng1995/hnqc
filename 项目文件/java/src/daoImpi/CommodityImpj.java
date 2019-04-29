package daoImpi;


import java.util.List;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import javabean.Outbound;
// ≤÷ø‚π‹¿Ì
public interface CommodityImpj {
	  public abstract JSONObject query_Outbound(String paramString);
	  
	  public abstract JSONObject list_Outbound(String paramString1, String paramString2);
	  
	  public abstract JSONObject query_Outbound(String paramString1, String paramString2);
	  
	  public abstract JSONObject addOutbound(Outbound paramOutbound);
	  
	  public abstract JSONObject update(Outbound paramOutbound);
	  
	  public abstract JSONObject deldate(int paramInt);
}
