<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'zhuce.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <body bgcolor="#CCCCFF">
        <s:form action="zhuceAction" method="post">
            <table align="center">
                <tr>
                 
                    <td width="40%">
                        <table border="2" bgcolor="#AABBCCDD" width="100%" align="center">
                            <tr>
                                <td colspan="2" align="center">
                                    <font color="yellow"><s:text name="请填写以下注册信息"/></font>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:textfield name="name" label="登录名"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <s:password name="word" label="密码" size="21"/>
                                </td>
                            </tr>
                             <tr>
                                <td colspan="2" align="center">
                                    <input type="submit" value="确定"/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="reset" value="清空"/>
                                </td>
                            </tr>
                            </table>
                            </td>
                            </tr>
                            </table>
                            </s:form>
                            
                          
  </body>
</html>
